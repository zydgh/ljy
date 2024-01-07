package com.ljy.leetcode.daily._2024._01;

import com.ljy.leetcode.job.ListNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Solution
 * @Description
 * @Author wangxh
 * @Date2023/12/31 17:07
 **/
public class Solution {
    /**
     * 2024-01-01
     * 1599. 经营摩天轮的最大利润
     *
     * @param customers
     * @param boardingCost
     * @param runningCost
     * @return
     */
    public static int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
        int ans = -1;
        int n = customers.length;
        int maxMoney = 0;
        int current = 0;
        int op = 0;
        if (n == 0 || boardingCost * 4 - runningCost <= 0) {
            return -1;
        }
        for (int i = 0; i < n; i++) {
            op++;
            current += customers[i];
            int min = Math.min(current, 4);
            int money = boardingCost * min - runningCost;
            if (money > 0) {
                maxMoney += money;
                ans = op;
            }
            current -= min;

        }
        if (current == 0) {
            return ans;
        }
        if (current > 0) {
            int a = current / 4;
            int b = current % 4;
            if (a >= 1) {
                maxMoney += a * (boardingCost * 4 - runningCost);
                current -= a * 4;
                ans += a;
            }
            int money = boardingCost * b - runningCost;
            if (money > 0) {
                maxMoney += money;
                current -= b;
                ans++;
            }
        }
        return maxMoney > 0 ? ans : -1;
    }

    /**
     * 2024-01-02
     * 466. 统计重复个数
     *
     * @param s1
     * @param n1
     * @param s2
     * @param n2
     * @return
     */
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        if (n1 == 0) {
            return 0;
        }
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        //经历多少s1
        int cout1 = 0;
        //经历多少s2
        int cout2 = 0;
        int l1 = s1.length();
        int l2 = s2.length();
        int p = 0;
        return cout2 / n2;
    }


    /**
     * 2024-01-03
     * 2487. 从链表中移除节点
     *
     * @param head
     * @return
     */
    public ListNode removeNodes(ListNode head) {
        if (head == null) {
            return null;
        }
        head.next = removeNodes(head.next);
        if (head.next != null && head.val < head.next.val) {
            return head.next;
        }
        return head;
    }

    /**
     * 2024-01-06
     * 1944. 队列中可以看到的人数
     *
     * @param heights
     * @return
     */
    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] ans = new int[n];
        int[] st = new int[n];
        int top = -1;
        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && st[top] < heights[i]) {
                top--;
                ans[i]++;
            }
            if (top >= 0) { // 还可以再看到一个人
                ans[i]++;
            }
            st[++top] = heights[i];
        }
        return ans;
    }

    /**
     * 2024-01-06
     * 2807. 在链表中插入最大公约数
     *
     * @param head
     * @return
     */
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        ListNode p = head;
        while (p != null && p.next != null) {
            int gcd = gcd(p.val, p.next.val);
            ListNode node = new ListNode(gcd, p.next);
            p.next = node;
            p = node.next;
        }
        return head;
    }


    /**
     * 求最大公约数 （欧几里得算法）辗转相除
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }


    /**
     * 2024-01-07
     * 383. 赎金信
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public static boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }
        int[] c = new int[26];
        for (char cc :magazine.toCharArray()) {
            c[cc - 'a']++;
        }
        for (char c1:ransomNote.toCharArray()) {
            c[c1-'a']--;
            if (c[c1-'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}