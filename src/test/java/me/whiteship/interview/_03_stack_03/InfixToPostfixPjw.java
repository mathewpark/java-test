package me.whiteship.interview._03_stack_03;

import java.util.Stack;

import org.junit.jupiter.api.Test;

public class InfixToPostfixPjw {

    @Test
    public void test() {
        // String str = "(5-(2+1))*9";
        String str = "1+(2+3*4+5)+6+7+8";
        // String str = "1+2+3+4+5+6+7+8";
        // String str = "1+2+3+4+5+6+7*8+9";

        StringBuilder sb = new StringBuilder();

        Stack<Character> stk = new Stack<>();

        int len = str.length();
        for(int i = 0; i < len; i++) {  // 괄호를 먼저 없앤다
            char ch = str.charAt(i);
            int priority = getPriority(ch);

            switch(ch) {
                case '+' :
                case '-' :
                case '*' :
                case '/' :
                    while(!stk.isEmpty() && getPriority(stk.peek()) >= priority) {
                        sb.append(stk.pop());
                    }
                    stk.push(ch);
                    break;
                case '(' :
                    stk.push(ch);
                    break;
                case ')' :
                    while(!stk.isEmpty() && stk.peek() != '(') {
                        sb.append(stk.pop());
                    }
                    stk.pop();  // '(' 제거
                    break;
                default :  // operand
                    sb.append(ch);
            }
        }

        while(!stk.isEmpty()) {
            sb.append(stk.pop());
        }
        System.out.println(sb.toString());
    }

    public static int getPriority(char ch) {
        switch(ch) {
            case '*' :
            case '/' :
                return 2;
            case '+' :
            case '-' :
                return 1;
            default :
                return 0;
        }
    }
}
