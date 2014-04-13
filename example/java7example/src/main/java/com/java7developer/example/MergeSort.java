package com.java7developer.example;

public class MergeSort {
	public static void main(String[] args) {
		int[] numbers = new int[] { 1, 5, 2, 3, 6, 3, 6, 7, 8, 9 };
		merge(numbers);
		// sort(0, 5, 5, numbers);
		display(numbers);
	}

	public static void display(int[] numbers) {
		for (int number : numbers)
			System.err.print(number + " ");
		System.err.println();
	}

	public static void merge(int[] numbers) {
		merge(0, numbers.length, numbers);
	}

	public static void merge(int index, int count, int[] numbers) {
		int mid = count / 2;
		int rightCount = mid;

		if (mid == 0) {
			return;
		}

		merge(index, mid, numbers);
		if ((count & 1) == 1) {
			rightCount += 1;
		}
		merge(mid + index, rightCount, numbers);
		sort(index, mid + index, rightCount, numbers);
	}

	public static void sort(int left, int right, int rightCount, int[] numbers) {
		int start = left;
		int leftCount = right - left;
		int[] tmp = new int[leftCount + rightCount];
		int i = 0;
		for (; leftCount != 0 && rightCount != 0;) {
			if (numbers[left] < numbers[right]) {
				tmp[i++] = numbers[left++];
				leftCount--;
			} else {
				tmp[i++] = numbers[right++];
				rightCount--;
			}
		}
		
		
		while (rightCount != 0) {
			tmp[i++] = numbers[right++];
			rightCount--;
		}
		
		
		while (leftCount != 0) {
			tmp[i++] = numbers[left++];
			leftCount--;
			}
		}
		
		System.arraycopy(tmp, 0, numbers, start, tmp.length);
	}
}
