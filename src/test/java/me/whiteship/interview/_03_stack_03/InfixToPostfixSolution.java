package me.whiteship.interview._03_stack_03;

import java.util.Stack;

import org.junit.jupiter.api.Test;

public class InfixToPostfixSolution {

    @Test
    public void test() {
        InfixToPostfixSolution infixToPostfix = new InfixToPostfixSolution();
        String postfix = infixToPostfix.convert("(1+2)*3");
        System.out.println(postfix.equals("12+3*"));
        System.out.println(infixToPostfix.convert("1+2*3").equals("123*+"));
        System.out.println(infixToPostfix.convert("(5-(2+1))*9"));
        System.out.println(infixToPostfix.convert("1+(2+3*4+5)+6+7+8"));
        System.out.println(infixToPostfix.convert("1+(2+3+4+5)+6+7+8"));
    }

    private int precedence(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        }

        return 0;
    }

    private String convert(String infix) {
        infix = infix.trim();
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < infix.length(); i++) {
             char c = infix.charAt(i);

             if (Character.isDigit(c)) {
                 result.append(c);
             } else if (c == '(') {
                 stack.push(c);
             } else if (c == ')') {
                 while (!stack.isEmpty() && stack.peek() != '(') {
                     result.append(stack.pop());
                 }
                 stack.pop();
             } else {
                 while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                     result.append(stack.pop());
                 }
                 stack.push(c);
             }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new IllegalArgumentException("Illegal infix expression");
            }
            result.append(stack.pop());
        }
        return result.toString();
    }
}
