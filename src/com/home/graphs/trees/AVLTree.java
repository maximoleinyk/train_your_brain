package com.home.graphs.trees;

import java.util.concurrent.ThreadLocalRandom;

public class AVLTree {

    private static final class Node {
        int value;
        Node parent;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }

        Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
        }

        public String toString() {
            return Integer.toString(this.value);
        }

        public boolean isLeftLeft() {
            return this.parent != null && this.parent.left == this && this.parent.parent != null && this.parent.parent.left == this.parent;
        }

        public boolean isRightRight() {
            return this.parent != null && this.parent.right == this && this.parent.parent != null && this.parent.parent.right == this.parent;
        }

        public boolean hasRightRight() {
            return this.right != null && this.right.right != null;
        }

        public boolean hasRightLeft() {
            return this.right != null && this.right.left != null;
        }

        public boolean hasLeftLeft() {
            return this.left != null && this.left.left != null;
        }

        public boolean hasLeftRight() {
            return this.left != null && this.left.right != null;
        }

        public boolean isRoot() {
            return this.parent == null;
        }

        public boolean isLeftRight() {
            return this.parent != null && this.parent.right == this && this.parent.parent != null && this.parent.parent.left == this.parent;
        }

        public boolean isRightLeft() {
            return this.parent != null && this.parent.left == this && this.parent.parent != null && this.parent.parent.right == this.parent;
        }
    }

    private Node root;
    private int size;

    private void add(int value) {
        Node insertedNode = this.insertValue(value);
        Node node = insertedNode.parent;

        while (node != null) {
            int leftLevel = this.getMaxDepth(node.left, 0);
            int rightLevel = this.getMaxDepth(node.right, 0);

            if (Math.abs(leftLevel - rightLevel) > 1) {
                if (insertedNode.isLeftLeft()) {
                    rotateRight(insertedNode.parent);
                } else if (insertedNode.isLeftRight()) {
                    rotateLeft(insertedNode);
                    node = insertedNode;
                } else if (insertedNode.isRightRight()) {
                    rotateLeft(insertedNode.parent);
                } else if (insertedNode.isRightLeft()) {
                    rotateRight(insertedNode);
                    node = insertedNode;
                }
            }
            node = node.parent;
        }
        size++;
    }

    private boolean remove(int value) {
        if (this.root == null) {
            return false;
        }

        Node foundNode = this.findNode(value);

        if (foundNode == null) {
            return false;
        }

        Node node = this.removeNode(foundNode);

        while (node != null) {
            int leftLevel = this.getMaxDepth(node.left, 0);
            int rightLevel = this.getMaxDepth(node.right, 0);

            if (Math.abs(leftLevel - rightLevel) > 1) {
                if (leftLevel < rightLevel) { // value was removed from the left sub tree
                    // RL
                    if (node.hasRightLeft()) {
                        rotateRight(node.right.left);
                        node = node.right;
                    }
                    // RR
                    else if (node.hasRightRight()) {
                        rotateLeft(node.right);
                    }
                } else { // value was removed from the right sub tree
                    // LR
                    if (node.hasLeftRight()) {
                        rotateLeft(node.left.right);
                        node = node.left;
                    }
                    // LL
                    else if (node.hasLeftLeft()) {
                        rotateRight(node.left);
                    }
                }
            }
            node = node.parent;
        }
        size--;

        return true;
    }

    private Node findNode(int value) {
        if (this.root == null) {
            return null;
        }

        Node current = this.root;

        while (current != null) {
            if (current.value == value) {
                return current;
            }

            if (value > current.value) {
                current = current.right;
            } else {
                current = current.left;
            }
        }

        return null;
    }

    private Node getPredecessor(Node node) {
        if (node == null) {
            return null;
        }

        Node current = node.left;

        while (current != null) {
            if (current.right == null) {
                return current;
            }

            current = current.right;
        }

        return node;
    }

    private Node removeNode(Node node) {
        if (node == null) {
            return null;
        }

        Node nodeToBeDeleted = getSuccessor(node);

        if (node == nodeToBeDeleted) {
            nodeToBeDeleted = getPredecessor(node);
        }

        if (nodeToBeDeleted.isRoot()) {
            this.root = null;
            return null;
        }

        node.value = nodeToBeDeleted.value;

        if (nodeToBeDeleted.left == null && nodeToBeDeleted.right == null) {
            if (nodeToBeDeleted.parent.left == nodeToBeDeleted) {
                nodeToBeDeleted.parent.left = null;
            } else {
                nodeToBeDeleted.parent.right = null;
            }
            return nodeToBeDeleted.parent;
        }

        return removeNode(nodeToBeDeleted);
    }

    private Node getSuccessor(Node node) {
        if (node == null) {
            return null;
        }

        Node current = node.right;

        while (current != null) {
            if (current.left == null) {
                return current;
            }
            current = current.left;
        }

        return node;
    }

    private int getMaxDepth(Node node, int level) {
        if (node == null) {
            return level;
        }

        level++;

        int leftLevel = getMaxDepth(node.left, level);
        int rightLevel = getMaxDepth(node.right, level);

        return leftLevel > rightLevel ? leftLevel : rightLevel;
    }

    private void rotateRight(Node z) {
        Node rightSubTree = z.right;
        Node grandparent = z.parent.parent;
        z.right = z.parent;

        if (grandparent == null) {
            this.root = z;
        } else {
            if (z.parent.parent.left == z.parent) {
                z.parent.parent.left = z;
            } else {
                z.parent.parent.right = z;
            }
        }
        z.right.left = rightSubTree;
        if (rightSubTree != null) {
            rightSubTree.parent = z.right;
        }
        z.right.parent = z;
        z.parent = grandparent;
    }

    private void rotateLeft(Node z) {
        Node leftSubTree = z.left;
        Node grandparent = z.parent.parent;
        z.left = z.parent;

        if (grandparent == null) {
            this.root = z;
        } else {
            if (z.parent.parent.right == z.parent) {
                z.parent.parent.right = z;
            } else {
                z.parent.parent.left = z;
            }
        }
        z.left.right = leftSubTree;
        if (leftSubTree != null) {
            leftSubTree.parent = z.left;
        }
        z.left.parent = z;
        z.parent = grandparent;
    }

    private Node insertValue(int value) {
        if (this.root == null) {
            this.root = new Node(value);
            return this.root;
        }

        Node current = this.root;

        while (true) {
            if (value > current.value) {
                if (current.right == null) {
                    current.right = new Node(value, current);
                    return current.right;
                } else {
                    current = current.right;
                }
            } else {
                if (current.left == null) {
                    current.left = new Node(value, current);
                    return current.left;
                } else {
                    current = current.left;
                }
            }
        }
    }

    private int size() {
        return this.size;
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        int size = 100;

        for (int j = 0; j < size; j++) {
            int[] array = new int[30];

            for (int i = 0; i < 30; i++) {
                int value = ThreadLocalRandom.current().nextInt(0, 50);
                array[i] = value;
                tree.add(value);
            }

            for (int k = 0; k < 30; k++) {
                System.out.println(tree.size());
                tree.remove(array[k]);
            }
        }
    }
}
