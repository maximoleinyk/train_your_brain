package com.home;

import java.util.*;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class RedBlackTree implements Printable {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Node root = null;
    private long size = 0;

    public long size() {
        return this.size;
    }

    public boolean isValid() {
        if (this.root == null) {
            return true;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(this.root);
        List<Integer> numberOfBlackNodesInEachPath = new LinkedList<>();

        while (!stack.isEmpty()) {
            Node n = stack.pop();

            if (n.right != null) {
                stack.push(n.right);
            } else {
                numberOfBlackNodesInEachPath.add(this.countNodes(n));
            }

            if (n.left != null) {
                stack.push(n.left);
            } else {
                numberOfBlackNodesInEachPath.add(this.countNodes(n));
            }
        }

        Integer[] paths = new Integer[numberOfBlackNodesInEachPath.size()];
        numberOfBlackNodesInEachPath.toArray(paths);

        for (int k = 0; k < paths.length - 1; k++) {
            int blackCountFirst = paths[k];
            int blackCountSecond = paths[k + 1];

            if (blackCountFirst != blackCountSecond) {
                return false;
            }
        }

        return true;
    }

    private int countNodes(Node n) {
        int i = 0;

        Node current = n;

        while (current != null) {
            if (current.isBlack()) {
                i++;
            }
            current = current.parent;
        }

        return i;
    }

    public void add(int value) {
        Node z = this.insertNode(value);

        while (z.isRed() && z.isParentRed()) {
            if (z.isLineLeft()) {
                if (z.isRightUncleBlack()) {
                    this.colorParentAndGrandparent(z);
                    this.rotateRight(z.parent);
                } else {
                    this.colorRightUncle(z);
                    z = z.parent.parent;
                }
            } else if (z.isLineRight()) {
                if (z.isLeftUncleBlack()) {
                    this.colorParentAndGrandparent(z);
                    this.rotateLeft(z.parent);
                } else {
                    this.colorLeftUncle(z);
                    z = z.parent.parent;
                }
            } else if (z.isTriangleRight()) {
                if (z.isLeftUncleBlack()) {
                    this.rotateRight(z);
                    z = z.right;
                } else {
                    this.colorLeftUncle(z);
                    z = z.parent.parent;
                }
            } else if (z.isTriangleLeft()) {
                if (z.isRightUncleBlack()) {
                    this.rotateLeft(z);
                    z = z.left;
                } else {
                    this.colorRightUncle(z);
                    z = z.parent.parent;
                }
            }
        }

        if (z.isRed() && z.isRoot()) {
            z.color = BLACK;
        }

        this.size++;
    }

    public boolean remove(int value) {
        if (this.root == null) {
            return false;
        }

        Node n = this.find(value);

        if (n == null) {
            return false;
        }

        boolean result = this.deleteNode(n);

        this.size--;

        return result;
    }

    private boolean deleteNode(Node n) {
        if (n.left != null && n.right != null) {
            Node inOrderSuccessor = this.getInorderSuccessor(n);
            n.value = inOrderSuccessor.value;
            return this.deleteNode(inOrderSuccessor);
        }
        return this.deleteOneOrZero(n);
    }

    private boolean deleteOneOrZero(Node n) {
        Node child = n.left != null ? n.left : n.right;

        // delete leaf
        if (n.isRed()) {
            if (n.isLeftNode()) {
                n.parent.left = null;
            } else {
                n.parent.right = null;
            }
            return true;
        }

        // delete black node that has only one red child
        if (child != null) {
            n.value = child.value;
            if (n.left == child) {
                n.left = null;
            } else {
                n.right = null;
            }
            return true;
        }

        return this.deleteCase1(n, false);
    }

    private boolean deleteCase1(Node n, boolean propagated) {
        if (n.isRoot()) {
            if (!propagated) {
                this.root = null;
            }
            return true;
        }

        return this.deleteCase2(n, propagated);
    }

    private boolean deleteCase2(Node n, boolean propagated) {
        Node sibling = n.isLeftNode() ? n.parent.right : n.parent.left;

        if (!n.isParentRed() && sibling != null && sibling.isRed() && sibling.isBothChildrenBlack()) {
            sibling.color = BLACK;
            n.parent.color = RED;

            if (n.isLeftNode()) {
                this.rotateLeft(sibling);
            } else {
                this.rotateRight(sibling);
            }
        }

        return this.deleteCase3(n, propagated);
    }

    private boolean deleteCase3(Node n, boolean propagated) {
        Node sibling = n.isLeftNode() ? n.parent.right : n.parent.left;

        if (n.parent.isBlack() && sibling.isBlack() && sibling.isBothChildrenBlack()) {
            sibling.color = RED;

            if (!propagated) {
                if (n.isLeftNode()) {
                    n.parent.left = null;
                } else {
                    n.parent.right = null;
                }
            }

            return this.deleteCase1(n.parent, true);
        }

        return this.deleteCase4(n, propagated);
    }

    private boolean deleteCase4(Node n, boolean propagated) {
        Node sibling = n.isLeftNode() ? n.parent.right : n.parent.left;

        if (n.isParentRed() && sibling.isBlack() && sibling.isBothChildrenBlack()) {
            n.parent.color = BLACK;

            if (n.isLeftNode()) {
                n.parent.right.color = RED;
                if (!propagated) {
                    n.parent.left = null;
                }
            } else {
                n.parent.left.color = RED;
                if (!propagated) {
                    n.parent.right = null;
                }
            }

            return true;
        }

        return this.deleteCase5(n, propagated);
    }

    private boolean deleteCase5(Node n, boolean propagated) {
        Node sibling = n.isLeftNode() ? n.parent.right : n.parent.left;

        if (n.isLeftNode() && sibling.isBlack() && sibling.left != null && sibling.left.isRed() && (sibling.right == null || sibling.right.isBlack())) {
            sibling.color = RED;
            sibling.left.color = BLACK;
            this.rotateRight(sibling.left);
        } else if (n.isRightNode() && sibling.isBlack() && sibling.right != null && sibling.right.isRed() && (sibling.left == null || sibling.left.isBlack())) {
            sibling.color = RED;
            sibling.right.color = BLACK;
            this.rotateLeft(sibling.right);
        }

        return this.deleteCase6(n, propagated);
    }

    private boolean deleteCase6(Node n, boolean propagated) {
        Node sibling = n.isLeftNode() ? n.parent.right : n.parent.left;

        if (n.isLeftNode() && sibling.isBlack() && sibling.right != null && sibling.right.isRed()) {
            sibling.color = sibling.parent.color;
            n.parent.color = BLACK;
            sibling.right.color = BLACK;
            this.rotateLeft(sibling);
            if (!propagated) {
                n.parent.left = null;
            }
        } else if (n.isRightNode() && sibling.isBlack() && sibling.left != null && sibling.left.isRed()) {
            sibling.color = sibling.parent.color;
            n.parent.color = BLACK;
            sibling.left.color = BLACK;
            this.rotateRight(sibling);
            if (!propagated) {
                n.parent.right = null;
            }
        }

        return true;
    }

    private Node getInorderSuccessor(Node node) {
        if (node == null) {
            return null;
        }

        Node successor = this.getSuccessor(node);
        Node predecessor = this.getPredecessor(node);

        return successor != null ? successor : predecessor;
    }

    private Node insertNode(int value) {
        if (this.root == null) {
            this.root = new Node(value, BLACK);
            return this.root;
        }

        Node newNode = new Node(value);
        Node next = this.root;

        while (true) {
            if (value > next.value) {
                if (next.right == null) {
                    next.right = newNode;
                    newNode.parent = next;
                    return newNode;
                }
                next = next.right;
            } else {
                if (next.left == null) {
                    next.left = newNode;
                    newNode.parent = next;
                    return newNode;
                }
                next = next.left;
            }
        }
    }

    private void colorParentAndGrandparent(Node z) {
        z.parent.color = BLACK;
        z.parent.parent.color = RED;
    }

    private void colorRightUncle(Node z) {
        this.colorParentAndGrandparent(z);

        if (z.parent.parent.right != null) {
            z.parent.parent.right.color = BLACK;
        }
    }

    private void colorLeftUncle(Node z) {
        this.colorParentAndGrandparent(z);

        if (z.parent.parent.left != null) {
            z.parent.parent.left.color = BLACK;
        }
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

    private Node find(int value) {
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

    public void print() {
        this.print(this.root, true);
        System.out.println("");
    }

    private void print(Node node, boolean isLeft) {
        if (node == null) {
            return;
        }

        this.print(node.left, true);
        System.out.print((isLeft ? "left " : "right ") + node.value + " ");
        this.print(node.right, false);
    }

    private static class Node {
        int value;
        Node left = null;
        Node right = null;
        Node parent = null;
        boolean color;

        Node(int value) {
            this.value = value;
            this.color = RED;
        }

        Node(int value, boolean black) {
            this(value);
            this.color = black;
        }

        boolean isBlack() {
            return this.color == BLACK;
        }

        boolean isRed() {
            return this.color == RED;
        }

        boolean isParentRed() {
            return this.parent != null && this.parent.isRed();
        }

        boolean isRightUncleBlack() {
            return this.parent.parent.right == null || this.parent.parent.right.isBlack();
        }

        boolean isLeftUncleBlack() {
            return this.parent.parent.left == null || this.parent.parent.left.isBlack();
        }

        boolean isTriangleLeft() {
            return this.isRightNode() && this.parent.parent.left == this.parent;
        }

        boolean isTriangleRight() {
            return this.isLeftNode() && this.parent.parent.right == this.parent;
        }

        boolean isLineRight() {
            return this.isRightNode() && this.parent.parent.right == this.parent;
        }

        boolean isLineLeft() {
            return this.isLeftNode() && this.parent.parent.left == this.parent;
        }

        boolean isRoot() {
            return this.parent == null;
        }

        public String toString() {
            return "" + this.value + (this.isBlack() ? "b" : "r");
        }

        public boolean isBothChildrenBlack() {
            return (this.left == null || this.left.isBlack()) && (this.right == null || this.right.isBlack());
        }

        public boolean isRightNode() {
            return this.parent.right == this;
        }

        public boolean isLeftNode() {
            return this.parent.left == this;
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        int size = 1000;

        for (int j = 0; j < size; j++) {
            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                int value = ThreadLocalRandom.current().nextInt(0, 50);
                array[i] = value;
                tree.add(value);
            }

            for (int k = 0; k < size; k++) {
                tree.remove(array[k]);
            }

            System.out.println(tree.size());
        }
    }
}
