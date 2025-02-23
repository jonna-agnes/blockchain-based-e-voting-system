package com.evoting;

import static spark.Spark.*;

import java.util.List;

public class Server {
    public static void main(String[] args) {
        // Initialize the database
        Database.setupDatabase();

        // Define REST API endpoints
        port(4567); // Spark runs on port 4567 by default

        // Endpoint to cast a vote
        post("/castVote", (req, res) -> {
            String voterId = req.queryParams("voterId");
            String party = req.queryParams("party");

            if (voterId == null || party == null) {
                res.status(400); // Bad request
                return "Voter ID and Party are required!";
            }

            if (Database.hasVoted(voterId)) {
                res.status(403); // Forbidden
                return "Voter " + voterId + " has already voted!";
            }

            Database.castVote(voterId, party);
            return "Vote cast successfully for Voter ID: " + voterId;
        });

        // Endpoint to get voting results
        get("/results", (req, res) -> {
            return Database.getResults();
        });

        // Endpoint to view the blockchain
        get("/blockchain", (req, res) -> {
            StringBuilder blockchainData = new StringBuilder();
            List<Block> blockchain = Database.getBlockchain();
            for (Block block : blockchain) {
                blockchainData.append("Block Data: ").append(block.getData())
                              .append(", Previous Hash: ").append(block.getPreviousHash())
                              .append(", Hash: ").append(block.getHash())
                              .append("\n");
            }
            return blockchainData.toString();
        });
    }
}