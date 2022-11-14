package me.whiteship.interview._00_intro;

import java.util.HashSet;
import java.util.Set;

/**
 * 퀴즈 1) 유일한 숫자 찾기
 *
 * numbers라는 int형 배열이 있다. 해당 배열에 들어있는 숫자들은 오직 한 숫자를 제외하고는 모두 두번씩 들어있다. 오직 한 번만 등장하는 숫자를 찾는 코드를 작성하라.
 */
public class Pjw00 {
    public static void main(String[] args) {
        int[] arr = { 1, 1, 2, 2, 3, 4, 4, 5, 5 };
        System.out.println(test(arr));
    }

    public static int test(int[] arr) {
        // int[] arr = { 1, 1, 2, 2, 3, 4, 4, 5, 5 };
        // return 3;

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];

            if (set.contains(Integer.valueOf(val))) {
                set.remove(Integer.valueOf(val));
                continue;
            }

            set.add(Integer.valueOf(val));
        }

        return set.stream().findFirst().get();
    }
}
