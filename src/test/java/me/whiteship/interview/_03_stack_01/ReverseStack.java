package me.whiteship.interview._03_stack_01;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.Test;

public class ReverseStack {

    @Test
    public void test() {
        Stack<Integer> numbers = new Stack();
        numbers.push(1);
        numbers.push(2);
        numbers.push(3);

        System.out.println(numbers);
        ReverseStack reverseStack = new ReverseStack();
        reverseStack.solution2(numbers);
        System.out.println(numbers);
    }

    // TODO 스택을 뒤집는 코드를 작성하라.
    private void solution(Stack<Integer> stack) {
        Queue<Integer> reversed = new LinkedBlockingQueue<>();

        while (!stack.empty()) {
            reversed.offer(stack.pop());
        }

        while (!reversed.isEmpty()) {
            stack.push(reversed.poll());
        }
    }

    /**
     * FIXME
     * @param stack
     */
    private void solution2(Stack<Integer> stack) {
        if (stack.empty()) {
            return;
        }

        // int temp = stack.pop();
        // int temp = recursive(stack);
        int temp = stack.pop();
        solution2(stack);
        stack.push(temp);
    }

}
