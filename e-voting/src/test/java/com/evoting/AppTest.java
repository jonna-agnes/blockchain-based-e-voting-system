package com.evoting;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class AppTest {

    @Before
    public void setup() {
        Database.setupDatabase();
        // Clear the blockchain before each test
        Database.clearBlockchain();
    }

    @Test
    public void testCastVote() {
        Database.castVote("V001", "A");
        assertTrue(Database.hasVoted("V001"));
    }

    @Test
    public void testBlockchain() {
        Database.castVote("V002", "B");
        List<Block> blockchain = Database.getBlockchain();
        assertFalse(blockchain.isEmpty());
        assertEquals("Voter V002 voted for B", blockchain.get(blockchain.size() - 1).getData());
    }

    @Test
    public void testGetResults() {
        Database.castVote("V003", "A");
        Database.castVote("V004", "B");
        String results = Database.getResults();
        assertTrue(results.contains("Party A: 1 votes"));
        assertTrue(results.contains("Party B: 1 votes"));
    }
}