package com.ljy.leetcode.daily;

import java.util.*;

/**
 * @author ljy
 */
public class solution {
    private int ans;

    /**
     * 2023-12-05
     * 2477. 到达首都的最少油耗
     *
     * @param roads
     * @param seats
     * @return
     * @Da
     */
    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length + 1;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : roads) {
            int x = e[0], y = e[1];
            g[x].add(y); // 记录每个点的邻居
            g[y].add(x);
        }

        dfs(0, -1, g, seats);
        return ans;
    }

    private int dfs(int x, int fa, List<Integer>[] g, int seats) {
        int size = 1;
        for (int y : g[x]) {
            if (y != fa) { // 递归子节点，不能递归父节点
                size += dfs(y, x, g, seats); // 统计子树大小
            }
        }
        if (x > 0) { // x 不是根节点
            ans += (size - 1) / seats + 1; // ceil(size/seats)
        }
        return size;
    }

    /**
     * 2023/12/07
     * 1466. 重新规划路线
     *
     * @param n
     * @param connections
     * @return
     */
    public int minReorder(int n, int[][] connections) {
        List<int[]>[] e = new List[n];
        for (int i = 0; i < n; i++) {
            e[i] = new ArrayList<int[]>();
        }
        for (int[] edge : connections) {
            e[edge[0]].add(new int[]{edge[1], 1});
            e[edge[1]].add(new int[]{edge[0], 0});
        }
        return dfs(0, -1, e);
    }

    public int dfs(int x, int parent, List<int[]>[] e) {
        int res = 0;
        for (int[] edge : e[x]) {
            if (edge[0] == parent) {
                continue;
            }
            res += edge[1] + dfs(edge[0], x, e);
        }
        return res;
    }

    /**
     * 2023/12/08
     * 2008. 出租车的最大盈利
     *
     * @param n
     * @param rides
     * @return
     */
    public long maxTaxiEarnings(int n, int[][] rides) {
        List<int[]>[] groups = new ArrayList[n + 1];
        for (int[] r : rides) {
            int start = r[0], end = r[1], tips = r[2];
            if (groups[end] == null) {
                groups[end] = new ArrayList<>();
            }
            groups[end].add(new int[]{start, end - start + tips});
        }
        long[] memo = new long[n + 1];
        for (int i = 2; i <= n; i++) {
            memo[i] = memo[i-1];
            if (groups[i] != null) {
                for (int[] p : groups[n]) {
                    memo[i] = Math.max(memo[i], memo[p[0]] + p[1]);
                }
            }
        }
        return memo[n];
//        Arrays.fill(memo, -1);
//        //递归
//        return dfs(n,memo, groups);
    }


    private long dfs(int n, long[] memo, List<int[]>[] groups) {
        if (n == 1) {
            return 0;
        }
        if (memo[n] != -1) {
            return memo[n];
        }
        long res = dfs(n - 1, memo, groups);
        if (groups[n] != null) {
            for (int[] p : groups[n]) {
                res = Math.max(res, dfs(p[0], memo, groups) + p[1]);
            }
        }
        return memo[n]=res;
    }


    /**
     * 2048. 下一个更大的数值平衡数
     * @param n
     * @return
     */
        public int nextBeautifulNumber(int n) {
            int i = n+1;
            while (true) {
                if (isBeautifulNumber(i)){
                    break;
                }
                i++;
            }
            return i;
        }

        private boolean isBeautifulNumber(int n){
            Map<Integer, Integer> map = new HashMap<>();
            String s = String.valueOf(n);
            for (int i = 0; i< s.length(); i++) {
                int temp = s.charAt(i) - '0';
                Integer orDefault = map.getOrDefault(temp, 0);
                map.put(temp, orDefault+1);
            }
            for (int key : map.keySet()) {
                Integer integer = map.get(key);
                if (integer != key) {
                    return false;
                }
            }
            return true;
        }
}
