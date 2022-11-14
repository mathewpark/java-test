package me.whiteship.interview._01_array_04;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SortByArrayPjw {

    public static void main(String[] args) {
        List<String> names = new LinkedList<>();
        names.add("keesun");

        SortByArrayPjw sortByArray = new SortByArrayPjw();
        System.out.println(Arrays.toString(sortByArray.solution(new int[]{2, 4, 1, 5, 6, 9})));
    }

    /**
     * TODO 1부터 100 까지의 숫자 중에 50개의 랜덤한 숫자가 들어있는 배열이 있다.
     *  이 배열을 O(n)의 시간 복잡도로 정렬하라.
     * @param numbers
     * @return
     */
    private int[] solution(int[] numbers) {
        return mergeSort(numbers, 0, numbers.length-1);
    }

    private int[] mergeSort(int[] numbers, int startIndex, int endIndex) {
        if ( startIndex == endIndex ) {
            return new int[] { numbers[startIndex] };
        }

        int base = (startIndex + endIndex) / 2;

        if ( base == startIndex ) {
            if (numbers[startIndex] < numbers[endIndex]) {
                return new int[] { numbers[startIndex], numbers[endIndex] };
            } else {
                return new int[] { numbers[endIndex], numbers[startIndex] };
            }
        } else {
            int[] firstArr = mergeSort(numbers, startIndex, base);
            int[] secondArr = mergeSort(numbers, base+1, endIndex);
            return merge(firstArr, secondArr);
        }
    }

    private int[] merge(int[] firstArr, int[] secondArr) {
        int[] result = new int[firstArr.length + secondArr.length];

        int[] copy = null;
        int firstIdx = 0;
        int secondIdx = 0;
        for (int i = 0; i < result.length; i++) {

            if (firstArr.length == firstIdx && secondArr.length != secondIdx) {
                copy = Arrays.copyOfRange(secondArr, secondIdx, secondArr.length);
            }

            if (secondArr.length == secondIdx && firstArr.length != firstIdx) {
                copy = Arrays.copyOfRange(firstArr, firstIdx, firstArr.length);
            }

            if (copy != null) {
                for (int j = 0 ; j < copy.length; j++) {
                    result[i++] = copy[j];
                }

                break;
            }

            if (firstArr[firstIdx] < secondArr[secondIdx]) {
                result[i] = firstArr[firstIdx++];
            } else {
                result[i] = secondArr[secondIdx++];
            }
        }

        return result;
    }
}
