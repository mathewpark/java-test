package me.whiteship.interview._02_list_01;

import org.junit.jupiter.api.Test;

public class LinkedList {

    private LinkedNode head;
    private LinkedNode tail;

    @Test
    public void test() {
        LinkedList list = new LinkedList();
        list.add(new LinkedNode(1));
        list.add(new LinkedNode(2));
        list.add(new LinkedNode(3));
        list.add(new LinkedNode(4));
        list.add(new LinkedNode(5));
        list.add(new LinkedNode(6));

        // list.print();
        list.reverse2();

        list.print();
    }

    private void print() {
        LinkedNode nodeToPrint = this.head;
        while(nodeToPrint != null) {
            System.out.println(nodeToPrint.number);
            nodeToPrint = nodeToPrint.next;
        }
    }

    private void add(LinkedNode node) {
        if (head == null) {
            head = node;
            tail = node;
        } else if (tail != null) {
            tail.next = node;
            tail = tail.next;
        }
    }

    private void reverse2() {
        this.tail = this.head;
        my(null, this.head, this.head.next);
    }

    private void my(LinkedNode prev, LinkedNode current, LinkedNode next) {
        if (next == null) {
            this.head = current;
            current.next = prev;
            return;
        }

        LinkedNode toNext = next.next;

        current.next = prev;

        my(current, next, toNext);
    }

    /**
     * TODO 단일 연결 리스트를 뒤집는 함수를 구현하라.
     *  예) 1 -> 2 -> 3 -> 4 -> 5  =>  5 -> 4 -> 3 -> 2 -> 1
     * 
     * 1 -><- 2 / 3 -> 4 -> 5
     * @return
     */
    private void reverse() {
        LinkedNode current = this.head;
        LinkedNode prev = null;
        LinkedNode next = null;

        while (current != null) {
            next = current.next;

            current.next = prev;
            prev = current;
            current = next;
        }

        this.tail = this.head;
        this.head = prev;
    }
}
