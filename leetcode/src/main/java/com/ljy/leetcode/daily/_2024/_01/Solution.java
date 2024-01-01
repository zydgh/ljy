package com.ljy.leetcode.daily._2024._01;

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
}