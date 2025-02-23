package com.evoting;

public class Voter {
    private String voterId;
    private String aadhaarNumber;

    public Voter(String voterId, String aadhaarNumber) {
        this.voterId = voterId;
        this.aadhaarNumber = aadhaarNumber;
    }

    // Check if the voter has already voted
    public boolean hasVoted() {
        for (Block block : Blockchain.getBlockchain()) {
            if (block.getData().contains("Voter ID: " + voterId)) {
                return true; // Voter has already voted
            }
        }
        return false; // Voter has not voted yet
    }

    // Cast a vote by adding a new block to the blockchain
    public boolean castVote(String party) {
        // Prevent double voting
        if (hasVoted()) {
            System.out.println("Voter " + voterId + " has already voted.");
            return false;
        }

        // Create vote data
        String voteData = "Voter ID: " + voterId + ", Aadhaar: " + aadhaarNumber + ", Party: " + party;

        // Add the vote as a new block to the blockchain
        Block newBlock = new Block(voteData, Blockchain.getLatestBlock().getHash());
        Blockchain.addBlock(newBlock);

        System.out.println("Vote cast successfully by Voter ID: " + voterId);
        return true;
    }

    // Getters for voter details
    public String getVoterId() {
        return voterId;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }
}