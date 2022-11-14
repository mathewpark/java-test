package me.whiteship.interview._01_array_03;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ThreeSumPjw {

    public static void main(String[] args) {
        ThreeSumPjw threeSum = new ThreeSumPjw();
        System.out.println(Arrays.toString(threeSum.solution(new int[]{2, 3, 5, 7, 9}, 21)));
    }

    /**
     * TODO 숫자로 구성된 배열 numbers와 target 숫자 하나가 주어졌을 때 numbers 배열에 들어있는 세 수를 더해 target 숫자를 만들 수 있는 인덱스 세개를 찾는 함수를 작성하라.
     *  예) numbers = [2, 3, 5, 7, 9] target = 21, 21을 만들 수 있는 5와 7과 9의 인덱스인 2, 3, 4를 리턴해야 한다.
     *
     * numbers 배열에 중복되는 숫자는 없으며 target 숫자를 만들 수 있는 조합은 하나 뿐이라고 가정해도 좋다.
     * @param numbers
     * @param target
     * @return
     */
    private int[] solution(int[] numbers, int target) {
        return numbers;
//
//        Map<Integer, int[]> map = new HashMap<>();
//
//        for (int i = 0; i < numbers.length; i++) {
//            int wanted = target - numbers[i];
//
//            if (map.containsKey(wanted)
//                    && map.get(wanted)[0] != i
//                    && map.get(wanted)[1] != i) {
//
//                int[] prefix = map.get(wanted);
//
//                int[] result = new int[3];
//                result[0] = prefix[0];
//                result[1] = prefix[1];
//                result[2] = i;
//
//                return result;
//            }
//
//            map.put(numbers[i], i);
//        }
//
//        return null;
    }

}
