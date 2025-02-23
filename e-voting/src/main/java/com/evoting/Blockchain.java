package com.evoting;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private static List<Block> blockchain = new ArrayList<>();

    // Create the genesis block
    static {
        blockchain.add(new Block("Genesis Block", "0"));
    }

    // Add a new block to the blockchain
    public static void addBlock(Block newBlock) {
        blockchain.add(newBlock);
    }

    // Get the latest block in the blockchain
    public static Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    // Get the entire blockchain
    public static List<Block> getBlockchain() {
        return blockchain;
    }

    // Clear the blockchain (for testing purposes)
    public static void clear() {
        blockchain.clear();
        // Re-add the genesis block after clearing
        blockchain.add(new Block("Genesis Block", "0"));
    }

    // Check if the blockchain is valid
    public static boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            // Check if the current block's hash is valid
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            // Check if the previous hash matches
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }
}