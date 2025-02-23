package com.evoting;

import static spark.Spark.*;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Initialize the database and blockchain
        Database.setupDatabase();

        // Homepage route
        get("/", (req, res) -> {
            return "Welcome to Blockchain E-Voting System!<br>" +
                   "<a href='/vote'>Cast Vote</a><br>" +
                   "<a href='/viewresults'>View Results</a><br>" +
                   "<a href='/signout'>Sign Out</a>";
        });

        // Vote casting route
        get("/vote", (req, res) -> {
            return "<form action='/vote' method='post'>" +
                   "Voter ID: <input type='text' name='voterId'><br>" +
                   "Party: <select name='party'>" +
                   "<option value='Party A'>Party A</option>" +
                   "<option value='Party B'>Party B</option>" +
                   "<option value='Party C'>Party C</option>" +
                   "</select><br>" +
                   "<input type='submit' value='Cast Vote'>" +
                   "</form>";
        });

        // Handle vote submission
        post("/vote", (req, res) -> {
            String voterId = req.queryParams("voterId");
            String party = req.queryParams("party");

            // Check if the voter has already voted
            if (Database.hasVoted(voterId)) {
                return "You have already voted. <a href='/'>Return to homepage</a>";
            } else {
                // Cast the vote and add it to the blockchain
                Database.castVote(voterId, party);
                return "Vote cast successfully! <a href='/'>Return to homepage</a>";
            }
        });

        // View Results route
        get("/viewresults", (req, res) -> {
            return "<form action='/viewresults' method='post'>" +
                   "Enter Password: <input type='password' name='password'>" +
                   "<input type='submit' value='View Results'>" +
                   "</form>";
        });

        // Handle password submission for viewing results
        post("/viewresults", (req, res) -> {
            String password = req.queryParams("password");
            if ("resultsxyz".equals(password)) {
                // Display vote results from the database
                String results = Database.getResults();

                // Display blockchain data for transparency
                StringBuilder blockchainData = new StringBuilder("<h2>Blockchain Data:</h2>");
                List<Block> blockchain = Database.getBlockchain();
                for (Block block : blockchain) {
                    blockchainData.append("<p>Block Hash: ").append(block.getHash()).append("</p>")
                                  .append("<p>Data: ").append(block.getData()).append("</p>")
                                  .append("<p>Timestamp: ").append(block.getTimestamp()).append("</p>");
                }

                return results + blockchainData.toString();
            } else {
                return "Incorrect password. <a href='/viewresults'>Try again</a>";
            }
        });

        // Sign Out route
        get("/signout", (req, res) -> {
            return "You have been signed out. <a href='/'>Return to homepage</a>";
        });
    }
}