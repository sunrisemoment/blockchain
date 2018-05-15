package com.jinzhao.blockchain;

import com.jinzhao.blockchain.Block;
import com.jinzhao.blockchain.BlockChain;

import junit.framework.TestCase;

public class BlockChainTest extends TestCase {
    private BlockChain blockChain;

    protected void setUp() throws Exception {
        super.setUp();
        blockChain = new BlockChain();
        blockChain.addBlock("block1");
        blockChain.addBlock("block2");
        blockChain.addBlock("block3");
        blockChain.addBlock("block4");
        blockChain.addBlock("block5");
    }

    public void testGetBlockAt() {
        assertEquals(blockChain.getBlockAt(1).getData(), "block1");
        assertEquals(blockChain.getBlockAt(2).getData(), "block2");
        assertEquals(blockChain.getBlockAt(3).getData(), "block3");
        assertEquals(blockChain.getBlockAt(4).getData(), "block4");
        assertEquals(blockChain.getBlockAt(5).getData(), "block5");
        assertNull(blockChain.getBlockAt(6));
    }

    public void testHashPointer() {
        Block block1 = blockChain.getBlockAt(1);
        Block block2 = blockChain.getBlockAt(2);
        assertEquals(block1.getHash(), block2.getPre().hash);

        Block block5 = blockChain.getBlockAt(5);
        assertEquals(blockChain.getHead().hash, block5.getHash());
    }

    public void testTamperData() {
        // Tamper with block3
        Block block3 = blockChain.getBlockAt(3);
        block3.setData("block3 has been modified by bad guy!");

        // To make the story consistent, continue to modify block4 and bock5
        Block block4 = blockChain.getBlockAt(4);
        block4.getPre().hash = block3.getHash();

        Block block5 = blockChain.getBlockAt(5);
        block5.getPre().hash = block4.getHash();

        block3 = blockChain.getBlockAt(3);
        block4 = blockChain.getBlockAt(4);
        assertEquals(block3.getHash(), block4.getPre().hash);
        assertEquals(block4.getHash(), block5.getPre().hash);

        // We assume that head is remembered by the user of the blockchain, so it can't
        // be tampered with. The head is used to verify the data in the chain.
        assertTrue(blockChain.getHead().hash != block5.getHash());
    }

}
