const express = require("express");
const path = require("path");
const { createProxyMiddleware } = require("http-proxy-middleware");
const crypto = require("crypto");
const app = express();
const port = 8080;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Serve static files from the "public" directory
app.use(express.static(path.join(__dirname, "public")));

// Log requests to static files
app.use((req, res, next) => {
  console.log(`Request URL: ${req.url}`);
  next();
});

// Blockchain implementation
class Block {
  constructor(index, timestamp, data, previousHash = "") {
    this.index = index;
    this.timestamp = timestamp;
    this.data = data;
    this.previousHash = previousHash;
    this.hash = this.calculateHash();
  }

  calculateHash() {
    return crypto
      .createHash("sha256")
      .update(
        this.index +
          this.timestamp +
          JSON.stringify(this.data) +
          this.previousHash
      )
      .digest("hex");
  }
}

class Blockchain {
  constructor() {
    this.chain = [this.createGenesisBlock()];
  }

  createGenesisBlock() {
    return new Block(0, "01/01/2023", "Genesis Block", "0");
  }

  getLatestBlock() {
    return this.chain[this.chain.length - 1];
  }

  addBlock(newBlock) {
    newBlock.previousHash = this.getLatestBlock().hash;
    newBlock.hash = newBlock.calculateHash();
    this.chain.push(newBlock);
  }
}

const votingBlockchain = new Blockchain();
const votedVoterIds = new Set();

// Serve the main HTML file
app.get("/", (req, res) => {
  console.log("Serving index.html");
  res.sendFile(path.join(__dirname, "public", "index.html"));
});

// Serve the vote page
app.get("/vote", (req, res) => {
  console.log("Serving vote.html");
  res.sendFile(path.join(__dirname, "public", "vote.html"));
});

// Serve the view results page with password protection
app.get("/viewresults", (req, res) => {
  console.log("Serving viewresults.html");
  res.sendFile(path.join(__dirname, "public", "viewresults.html"));
});

// Handle view results request with password verification
app.post("/api/results", (req, res) => {
  const { password } = req.body;
  if (password === "resultsxyz") {
    console.log("Fetching results");
    const results = votingBlockchain.chain.reduce((acc, block) => {
      if (block.data.party) {
        acc[block.data.party] = (acc[block.data.party] || 0) + 1;
      }
      return acc;
    }, {});
    res.json(results);
  } else {
    res.status(401).send("Unauthorized: Incorrect password");
  }
});

// Serve the sign out page
app.get("/signout", (req, res) => {
  console.log("Serving signout.html");
  res.sendFile(path.join(__dirname, "public", "signout.html"));
});

// Handle vote submission
app.post("/vote", (req, res) => {
  const { voterId, party } = req.body;
  if (votedVoterIds.has(voterId)) {
    res.send("Only one vote allowed.");
  } else {
    console.log(`Received vote: ${voterId} for ${party}`);
    const newBlock = new Block(
      votingBlockchain.chain.length,
      new Date().toISOString(),
      { voterId, party }
    );
    votingBlockchain.addBlock(newBlock);
    votedVoterIds.add(voterId);
    res.send("Vote cast successfully!");
  }
});

app.listen(port, () => {
  console.log(`Frontend server is running at http://127.0.0.1:${port}`);
});
