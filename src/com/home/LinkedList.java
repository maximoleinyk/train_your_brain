package com.home;

public class LinkedList implements Printable {

    private static class Entry {
        int value;
        Entry next;

        Entry(int value) {
            this.value = value;
        }
    }

    private Entry head;

    public boolean hasLoop(LinkedList list) {
        if (list.head == null || list.head.next == null) {
            return false;
        }

        Entry slow = list.head;
        Entry fast = list.head.next;

        while (slow.next != null) {
            if (slow == fast) {
                return true;
            }

            slow = slow.next;

            if (fast == null || fast.next == null) {
                return false;
            }

            fast = fast.next.next;
        }

        return false;
    }

    public void add(int i) {
        if (head == null) {
            head = new Entry(i);
            return;
        }

        Entry current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = new Entry(i);
    }

    public void addLoop() {
        Entry current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = head;
    }

    public void reverse() {
        if (this.head == null) {
            return;
        }

        Entry prev = null;
        Entry current = head;

        while(current.next != null) {
            Entry n = current.next;
            current.next = prev;
            prev = current;
            current = n;
        }
        current.next = prev;

        this.head = current;
    }

    public void print() {
        if (this.head == null) {
            return;
        }

        Entry current = head;

        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println("List does not contain infinite loop: " + list.hasLoop(list));
        list.addLoop();
        System.out.println("List contains infinite loop: " + list.hasLoop(list));
    }
}
