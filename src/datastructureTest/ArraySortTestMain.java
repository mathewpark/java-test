package datastructureTest;

import java.util.Arrays;

public class ArraySortTestMain {
	public static void main(String[] args) {
		String[] a = { "bbb", "AAA", "DDD", "똠방각하", "CCC", "aaa" };

		// 정렬
		Arrays.sort(a, String.CASE_INSENSITIVE_ORDER);

		// 배열 순서 거꾸로
		reverseArrayString(a);

		// 순서 뒤집어진 "문자열 배열"을 "문자열"로 변환 후 출력
		System.out.println(Arrays.toString(a));
		// 출력 결과: [똠방각하, DDD, CCC, bbb, aaa, AAA]
	}
	
	public static void reverseArrayString(String[] array) {
		String temp;

		for (int i = 0; i < array.length / 2; i++) {
			temp = array[i];
			array[i] = array[(array.length - 1) - i];
			array[(array.length - 1) - i] = temp;
		}
	}
}
