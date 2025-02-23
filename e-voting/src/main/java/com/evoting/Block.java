package com.evoting;

public class Block {
    private String data;
    private String previousHash;
    private long timestamp;
    private String hash;

    // Constructor for creating a new block
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }

    // Constructor for loading a block from the database
    public Block(String data, String previousHash, long timestamp, String hash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.hash = hash;
    }

    // Calculate the hash of the block
    public String calculateHash() {
        return StringUtil.applySha256(previousHash + data + timestamp);
    }

    // Getters
    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }
}