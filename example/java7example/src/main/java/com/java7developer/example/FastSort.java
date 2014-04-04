package com.java7developer.example;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class FastSort {
	public static void main(String[] args) {
		int[] nums = new int[] { 5, 4, 6, 2, 8, 7, 9, 1, 3 };
		
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new ForkJoinFastSort(nums, 0, 8));
		for (int i : nums) {
			System.err.print(i);
		}
	}

	/*
	 * fork/join
	 */
	@SuppressWarnings("serial")
	static class ForkJoinFastSort extends RecursiveAction {
		private int[] nums;
		private int start;
		private int end;

		ForkJoinFastSort(int[] nums, int start, int end) {
			this.nums = nums;
			this.start = start;
			this.end = end;
		}

		@Override
		protected void compute() {
			int i = start;
			int j = end;
			int swap = -1;

			while (i < j) {
				while (i < j && nums[i] <= nums[j])
					j--;
				if (i < j) {
					swap = nums[i];
					nums[i] = nums[j];
					nums[j] = swap;
				}
				while (i < j && nums[i] < nums[j])
					i++;
				if (i < j) {
					swap = nums[i];
					nums[i] = nums[j];
					nums[j] = swap;
				}
			}
			List<ForkJoinTask<Void>> tasks = new LinkedList<>();

			if (i - start > 1) {
				tasks.add(new ForkJoinFastSort(nums, 0, i - 1));
			}
			if (end - j > 1) {
				tasks.add(new ForkJoinFastSort(nums, j + 1, end));
			}
			invokeAll(tasks);
		}
	}

	/*
	 * normal
	 */
	public static void sort(int[] nums, int start, int end) {
		int i = start;
		int j = end;
		int swap = -1;
		
		while (i < j) {
			while (i < j && nums[i] <= nums[j])
				j--;
			if (i < j) {
				swap = nums[i];
				nums[i] = nums[j];
				nums[j] = swap;
				display(nums);
			}
			while (i < j && nums[i] < nums[j])
				i++;
			if (i < j) {
				swap = nums[i];
				nums[i] = nums[j];
				nums[j] = swap;
				display(nums);
			}
		}
		if (i - start > 1) { // 递归调用，把key前面的完成排序
			sort(nums, start, i - 1);
		}
		if (end - j > 1) {
			sort(nums, j + 1, end); // 递归调用，把key后面的完成排序
		}
	}

	public static void display(int[] nums) {
		for (int i : nums) {
			System.err.print(i);
		}
		System.err.println();
	}

}
