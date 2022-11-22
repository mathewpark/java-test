package me.whiteship.interview._03_stack_03;

import java.util.Stack;

public class InfixToPostfix {

    public static void main(String[] args) {
        InfixToPostfix infixToPostfix = new InfixToPostfix();
        String postfix = infixToPostfix.convert("(1+2)*3");
        System.out.println(postfix.equals("12+3*"));
        System.out.println(infixToPostfix.convert("1+2*3").equals("123*+"));
    }

    /**
     * FIXME
     * 
     * TODO 인픽스를 포스트픽스로 변환하기
     * (1+2)*3 -> 12+3*
     * 5 / 2 -> 52/
     * (5-(2+1))*9 -> 521+-9*
     * 3+(4*5)-9/2 -> 345*+92/-
     * NOTE: 23 - 18 = 5
     * 
     * 1+2+3+4+5+6+7*8+9 -> 12+3+4+5+6+78*+9+
     * 
     * 1+(2+3+4+5)+6+7+8 -> 123+4+5++6+7+8+
     * 
     * 1+(2+3*4+5)+6+7+8 -> 1234*+5++6+7+8+
     */
    private String convert(String infix) {
        Stack<Character> stack = new Stack<>();

        char[] charArr = infix.toCharArray();

        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < charArr.length; i++) {
            char c = charArr[i];

            if (Character.isDigit(c)) {
                sb.append(c);
            }

            switch (c) {
            case '(':
                stack.push(c);
                break;
            case ')':
                while (stack.peek() != '(') {
                    char temp = stack.pop();

                    if (Character.isDigit(temp)) {
                        continue;
                    }

                    if (temp == '+' || temp == '-' || temp == '*' || temp == '/') {
                        sb.append(temp);
                    }
                }

                if (!stack.empty()) {
                    stack.pop();
                }

                break;
            case '+':
            case '-':
                if (Character.isDigit(stack.peek()) && stack.size() == 1) {
                    stack.pop();
                    sb.append(c);
                } else {
                    stack.push(c);
                }

                break;
            case '*':
            case '/':
                break;
            }
        }

       return null; 
    }
}
