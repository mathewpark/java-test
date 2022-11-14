package me.whiteship.interview._01_array_02;

public class ReverseStringPjw {

    public static void main(String[] args) {
        ReverseStringPjw reverseString = new ReverseStringPjw();
        System.out.println(reverseString.solution1("Hello".toCharArray()));
    }

    /**
     * TODO 주어진 문자열을 거꾸로 뒤집은 문자열을 만드는 함수를 작성하라.
     *  예) hello => olleh
     *  예) happy new year => reay wen yppah
     *
     * @param message
     * @return
     */
    private static char[] solution1(char[] message) {

        char[] result = new char[message.length];
        for (int i = 0; i < message.length; i++) {
            char ch = message[i];
            result[i] = message[message.length - i - 1];
        }

        return result;
    }

    /**
     * TODO 주어진 문자열을 거꾸로 뒤집은 문자열을 만드는 함수를 작성하라.
     *  예) hello => olleh
     *  예) happy new year => reay wen yppah
     *
     * @param message
     * @return
     */
    private static char[] solution(char[] message) {
        return message;
    }
}
