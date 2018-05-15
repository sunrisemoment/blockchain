
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jinzhao.blockchain;

/**
 * @author Jin Zhao
 *
 */
public class BlockChain {
    private HashPointer head; // head points to the last block in the chain

    public HashPointer getHead() {
        return head;
    }

    public BlockChain() {
        Block genesisBlock = new Block(null, 0, new String("The very first block!"));
        head = new HashPointer();
        head.ref = genesisBlock;
        head.hash = genesisBlock.getHash();
    }

    public void addBlock(String data) {
        HashPointer pointer = new HashPointer();
        pointer.ref = head.ref;
        pointer.hash = head.hash;

        Block block = new Block(pointer, head.ref.getPos() + 1, data);
        head.ref = block;
        head.hash = block.getHash();
    }

    public Block getBlockAt(int pos) {
        HashPointer pointer = head;
        while (pointer != null) {
            int currentPos = pointer.ref.getPos();
            if (currentPos == pos) {
                return pointer.ref;
            }
            if (currentPos < pos) {
                return null;
            }
            pointer = pointer.ref.getPre();
        }
        return null;
    }

    public void traverse() {
        Block current = head.ref;
        System.out.print("Head:");
        System.out.print(" Hash:" + current.getHash());
        System.out.println(" Data:" + current.getData());

        HashPointer pointer = current.getPre();
        while (pointer != null) {
            System.out.println("");
            System.out.println("Current Block: ");
            System.out.println(current);
            System.out.println("Previous Block: ");
            System.out.print("Hash:" + pointer.ref.getHash());
            System.out.println(" Data:" + pointer.ref.getData());
            current = pointer.ref;
            pointer = pointer.ref.getPre();
        }
    }

    public static void main(String[] args) {
        BlockChain blockChain = new BlockChain();
        blockChain.addBlock("block1");
        blockChain.addBlock("block2");
        blockChain.addBlock("block3");
        blockChain.addBlock("block4");
        blockChain.addBlock("block5");
        blockChain.traverse();
    }
}
