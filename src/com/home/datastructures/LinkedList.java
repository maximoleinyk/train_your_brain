package com.home.datastructures;

import com.home.Printable;

public class LinkedList implements Printable {

    private static class Entry {
        int value;
        Entry next;

        Entry(int value) {
            this.value = value;
        }
    }

    private Entry head;

    public boolean detectAndRemove() {
        if (head == null || head.next == null) {
            return false;
        }

        Entry slow = head;
        Entry fast = head;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                removeLoop(slow, head);
                return true;
            }
        }

        return false;
    }

    private void removeLoop(Entry slow, Entry head) {
        Entry pointer1 = head;
        Entry pointer2 = slow;

        while (true) {
            while (pointer2.next != slow) {
                if (pointer2.next == pointer1) {
                    pointer2.next = null;
                    return;
                }

                pointer2 = pointer2.next;
            }

            pointer2 = slow;
            pointer1 = pointer1.next;
        }
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

        current.next = head.next;
    }

    public void reverse() {
        if (this.head == null) {
            return;
        }

        Entry prev = null;
        Entry current = head;

        while (current.next != null) {
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
        list.add(6);
        list.add(7);

        list.addLoop();
        list.detectAndRemove();
    }
}
