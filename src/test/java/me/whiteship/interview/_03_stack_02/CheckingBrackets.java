package me.whiteship.interview._03_stack_02;

import java.util.Stack;

import org.junit.jupiter.api.Test;

public class CheckingBrackets {

    @Test
    public void test() {
        CheckingBrackets checkingBrackets = new CheckingBrackets();
        System.out.println(checkingBrackets.check("[{1 + 2 * (2 + 2)} - (1 - 3)]"));
        System.out.println(checkingBrackets.check("[{1 + 2 * (2 + 2)} - [1 - 3)]"));
        System.out.println(checkingBrackets.check("((())"));
        System.out.println(checkingBrackets.check("(()))"));
        System.out.println(checkingBrackets.check("{{()}}"));
    }

    /**
     * TODO 주어진 수식의 괄호짝이 맞는지 확인하는 코드를 작성하라.
     *  예) [{1 + 2 * (2 + 2)} - (1 - 3)]   => true
     *  예) [{1 + 2 * (2 + 2)} - [1 - 3)]   => false
     */
    private boolean check(String mathExpression) {
        Stack<Character> stack = new Stack<>();

        char[] array = mathExpression.toCharArray();

        for (int i = 0; i < array.length; i++) {
            char s = array[i];

            switch (s) {
            case '[':
            case '{':
            case '(':
                stack.push(s);

                break;
            case ']':
                if (stack.empty())
                    return false;
                if (stack.peek() != '[')
                    return false;
                stack.pop();
                break;
            case '}':
                if (stack.empty())
                    return false;
                if (stack.peek() != '{')
                    return false;
                stack.pop();
                break;
            case ')':
                if (stack.empty())
                    return false;
                if (stack.peek() != '(')
                    return false;
                stack.pop();
                break;
            default:
                break;
            }
        }

        if (!stack.empty()) {
            return false;
        }
        
        return true;
    }
}
