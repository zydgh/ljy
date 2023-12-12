package com.ljy.leetcode.job;

import java.util.*;

/**
 * @ClassName Solution
 * @Description
 * @Author wangxh
 * @Date2023/12/7 14:08
 **/
public class Solution {


    /**
     * 单调栈
     * 739. 每日温度
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {

        int[] temp = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < temperatures.length; i++) {
            int t = temperatures[i];
            while (!stack.empty() && t > temperatures[stack.peek()]) {
                int j = stack.pop();
                temp[j] = i - j;
            }
            stack.push(temperatures[i]);
        }
        return temp;
    }


    public int[] nextLargerNodes(ListNode head) {

        List<Integer> list = new ArrayList<>();
        Stack<Integer[]> stack = new Stack<>();
        int index = -1;
        ListNode p = head;
        while (p != null) {
            ++index;
            list.add(0);
            int t = p.val;
            while (!stack.empty() && stack.peek()[0] < t) {
                Integer[] j = stack.pop();
                list.set(j[1], t);
            }
            stack.push(new Integer[]{t, index});
            p = p.next;
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    /**
     * 503. 下一个更大元素 II
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        for (int i = 0; i < n * 2; i++) {
            int t = nums[i];
            while (!stack.empty() && t > nums[stack.peek()]) {
                int j = stack.pop();
                ans[j] = nums[i % n];
            }
            stack.push(i % n);
        }
        return ans;
    }

    /**
     * 496. 下一个更大元素 I
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums2.length; i++) {
            int t = nums2[i];
            while (!stack.empty() && t > stack.peek()) {
                stack.pop();
            }
            map.put(t, stack.isEmpty() ? -1 : stack.peek());
            stack.push(t);
        }
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = map.get(nums1[i]);
        }
        return ans;
    }

    /**
     * 456. 132 模式
     *
     * @param nums
     * @return
     */
    public boolean find132pattern(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int k = Integer.MIN_VALUE;
        for (int i = nums.length - 1; i >= 0; --i) {
            int temp = nums[i];
            if (temp < k) {
                return true;
            }
            while (!stack.empty() && temp > stack.peek()) {
                k = stack.pop();
            }
            if (temp > k) {
                stack.push(temp);
            }
        }
        return false;
    }

    /**
     * 1124. 表现良好的最长时间段
     *
     * @param hours
     * @return
     */
    public int longestWPI(int[] hours) {
        int n = hours.length;
        int ans = 0;
        //前缀和
        int[] s = new int[n + 1];
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for (int i = 1; i <= n; i++) {
            s[i] = s[i - 1] + (hours[i-1] > 8 ? 1 : -1);
            if (s[i] < s[stack.peek()]) {
                stack.push(i);
            }
        }
        for (int i = n; i > 0; --i) {
            while (!stack.empty() && s[i] > s[stack.peek()]) {
                ans = Math.max(ans, i-stack.pop());
            }
        }
        return ans;
    }
}