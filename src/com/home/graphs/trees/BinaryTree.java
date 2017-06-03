package com.home.graphs.trees;

public class BinaryTree {

    private static class Node {
        Node left;
        Node right;
        Node parent;
        int value;

        Node(int value) {
            this.value = value;
        }

        Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
        }

        boolean isRoot() {
            return this.parent == null;
        }
    }

    private Node root;

    private long size = 0;

    public long size() {
        return this.size;
    }

    public Node find(int value) {
        Node node = this.root;

        while (true) {
            // if value was not found
            if (node == null) {
                return null;
            }

            if (value > node.value) {
                node = node.right;
            } else if (value < node.value) {
                node = node.left;
            } else {
                return node;
            }
        }
    }

    public void add(int value) {
        size++;

        if (this.root == null) {
            this.root = new Node(value);
            return;
        }

        Node current = this.root;

        while (true) {
            if (value > current.value) {
                if (current.right == null) {
                    current.right = new Node(value, current);
                    return;
                } else {
                    current = current.right;
                }
            } else {
                if (current.left == null) {
                    current.left = new Node(value, current);
                    return;
                } else {
                    current = current.left;
                }
            }
        }
    }

    public boolean remove(int value) {
        if (this.root == null) {
            return false;
        }

        Node foundNode = this.find(value);

        return foundNode != null && this.removeNode(foundNode);

    }

    private boolean removeNode(Node node) {
        if (node == null || this.root == null) {
            return false;
        }

        Node nodeToBeDeleted = getSuccessor(node);

        if (node == nodeToBeDeleted) {
            nodeToBeDeleted = getPredecessor(node);
        }

        // root has no children
        if (nodeToBeDeleted.isRoot()) {
            this.root = null;
            return true;
        }

        node.value = nodeToBeDeleted.value;

        if (nodeToBeDeleted.left == null && nodeToBeDeleted.right == null) {
            if (nodeToBeDeleted.parent.left == nodeToBeDeleted) {
                nodeToBeDeleted.parent.left = null;
            } else {
                nodeToBeDeleted.parent.right = null;
            }
            return true;
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

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.add(10);
        tree.add(20);
        tree.add(30);
        tree.add(40);
        tree.add(50);
        System.out.println(tree.size());
    }
}
