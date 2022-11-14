package me.whiteship.interview._02_list_04;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class LinkedList {

    private LinkedNode head;
    private LinkedNode tail;

    @Test
    public void test() {
        LinkedList list = new LinkedList();
        list.add(new LinkedNode(1));
        list.add(new LinkedNode(2));
        LinkedNode node3 = new LinkedNode(3);
        list.add(node3);
        list.add(new LinkedNode(4));
        list.add(new LinkedNode(5));
        list.add(node3);

//        list.print();
        System.out.println(list.hasCircle());
    }

    /**
     * TODO 주어진 연결 리스트가 원형 연결 리스트인지 단일 연결 리스트인지 확인하는 함수를 구현하라.
     *  예) 1 -> 2 -> 3 -> 1   => true
     *  예) 1 -> 2 -> 3 -> 2   => true
     *  예) 1 -> 2 -> 3        => false
     * @return
     */
    private boolean hasCircle() {
        Set<LinkedNode> set = new HashSet<>();

        LinkedNode n = this.head;

        set.add(n);

        while (n.next != null) {
            LinkedNode target = n.next;
            
            if (set.contains(target)) {
                return true;
            }
            
            set.add(target);
            n = n.next;
        }

        return false;
    }

    private boolean hasCircle2() {
        
        return false;
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

}
