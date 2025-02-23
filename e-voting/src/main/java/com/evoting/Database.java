package com.evoting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    // Method to connect to the SQLite database
    public static Connection connect() throws SQLException {
        String url = "jdbc:sqlite:voting.db";
        System.out.println("Connecting to the database...");
        return DriverManager.getConnection(url);
    }

    // Method to set up the database (create tables if they don't exist)
    public static void setupDatabase() {
        String createVotesTable = "CREATE TABLE IF NOT EXISTS votes (" +
            "voter_id VARCHAR(5) PRIMARY KEY, " +
            "party VARCHAR(1), " +
            "has_voted BOOLEAN DEFAULT FALSE)";

        String createBlockchainTable = "CREATE TABLE IF NOT EXISTS blockchain (" +
            "block_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "previous_hash TEXT, " +
            "data TEXT, " +
            "timestamp INTEGER, " +
            "hash TEXT)";

        System.out.println("Attempting to create the 'votes' and 'blockchain' tables...");

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createVotesTable);
            stmt.execute(createBlockchainTable);
            System.out.println("'votes' and 'blockchain' tables created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating the tables:");
            e.printStackTrace();
        }
    }

    // Check if a voter has already voted
    public static boolean hasVoted(String voterId) {
        String sql = "SELECT has_voted FROM votes WHERE voter_id = ?";
        System.out.println("Checking if voter " + voterId + " has already voted...");

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, voterId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                boolean hasVoted = rs.getBoolean("has_voted");
                System.out.println("Voter " + voterId + " has voted: " + hasVoted);
                return hasVoted;
            }
        } catch (SQLException e) {
            System.err.println("Error checking if voter " + voterId + " has voted:");
            e.printStackTrace();
        }
        return false;
    }

    // Cast a vote by inserting into the database and blockchain
    public static void castVote(String voterId, String party) {
        String sqlVotes = "INSERT INTO votes (voter_id, party, has_voted) VALUES (?, ?, ?)";
        System.out.println("Casting vote for Voter ID: " + voterId + ", Party: " + party);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlVotes)) {
            pstmt.setString(1, voterId);
            pstmt.setString(2, party);
            pstmt.setBoolean(3, true); // Mark as voted
            pstmt.executeUpdate();
            System.out.println("Vote cast successfully for Voter ID: " + voterId);

            // Add vote to the blockchain
            String voteData = "Voter " + voterId + " voted for " + party;
            addBlockToBlockchain(voteData, conn);
        } catch (SQLException e) {
            System.err.println("Error casting vote for Voter ID: " + voterId);
            e.printStackTrace();
        }
    }

    // Add a block to the blockchain
    private static void addBlockToBlockchain(String data, Connection conn) {
        String sqlBlockchain = "INSERT INTO blockchain (previous_hash, data, timestamp, hash) VALUES (?, ?, ?, ?)";
        String previousHash = getLastBlockHash(conn);
        Block newBlock = new Block(data, previousHash);

        try (PreparedStatement pstmt = conn.prepareStatement(sqlBlockchain)) {
            pstmt.setString(1, newBlock.getPreviousHash());
            pstmt.setString(2, newBlock.getData());
            pstmt.setLong(3, newBlock.getTimestamp());
            pstmt.setString(4, newBlock.getHash());
            pstmt.executeUpdate();
            System.out.println("Block added to blockchain: " + newBlock.getHash());
        } catch (SQLException e) {
            System.err.println("Error adding block to blockchain:");
            e.printStackTrace();
        }
    }

    // Get the hash of the last block in the blockchain
    private static String getLastBlockHash(Connection conn) {
        String sql = "SELECT hash FROM blockchain ORDER BY block_id DESC LIMIT 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getString("hash");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving last block hash:");
            e.printStackTrace();
        }
        return "0"; // Genesis block has no previous hash
    }

    // Retrieve voting results from the database
    public static String getResults() {
        StringBuilder results = new StringBuilder();
        String sql = "SELECT party, COUNT(*) AS votes FROM votes GROUP BY party";
        System.out.println("Retrieving voting results...");
    
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String party = rs.getString("party");
                int voteCount = rs.getInt("votes");
                results.append("Party ").append(party).append(": ").append(voteCount).append(" votes\n");
                System.out.println("Party " + party + ": " + voteCount + " votes");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving voting results:");
            e.printStackTrace();
        }
        return results.toString();
    }

    public static void clearBlockchain() {
    String sql = "DELETE FROM blockchain";
    try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
        stmt.execute(sql);
        System.out.println("Blockchain cleared successfully.");
    } catch (SQLException e) {
        System.err.println("Error clearing blockchain:");
        e.printStackTrace();
    }
    }

    // Retrieve blockchain data for transparency
    public static List<Block> getBlockchain() {
        List<Block> blockchain = new ArrayList<>();
        String sql = "SELECT * FROM blockchain ORDER BY block_id";
        System.out.println("Retrieving blockchain data...");

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String previousHash = rs.getString("previous_hash");
                String data = rs.getString("data");
                long timestamp = rs.getLong("timestamp");
                String hash = rs.getString("hash");
                blockchain.add(new Block(data, previousHash, timestamp, hash));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving blockchain data:");
            e.printStackTrace();
        }
        return blockchain;
    }

    
}