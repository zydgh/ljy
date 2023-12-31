package com.ljy.leetcode.daily;

import com.ljy.datastruct.TreeNode;

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
            memo[i] = memo[i - 1];
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
        return memo[n] = res;
    }


    /**
     * 2048. 下一个更大的数值平衡数
     *
     * @param n
     * @return
     */
    public int nextBeautifulNumber(int n) {
        int i = n + 1;
        while (true) {
            if (isBeautifulNumber(i)) {
                break;
            }
            i++;
        }
        return i;
    }

    private boolean isBeautifulNumber(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        String s = String.valueOf(n);
        for (int i = 0; i < s.length(); i++) {
            int temp = s.charAt(i) - '0';
            Integer orDefault = map.getOrDefault(temp, 0);
            map.put(temp, orDefault + 1);
        }
        for (int key : map.keySet()) {
            Integer integer = map.get(key);
            if (integer != key) {
                return false;
            }
        }
        return true;
    }

    /**
     * 2023/12/13
     * 2697. 字典序最小回文串
     *
     * @param s
     * @return
     */
    public String makeSmallestPalindrome(String s) {
        char[] charArray = s.toCharArray();
        for (int i = 0; i < s.length() / 2; i++) {
            char c = charArray[i];
            char d = charArray[s.length() - i - 1];
            if (c != d) {
                charArray[i] = charArray[s.length() - i - 1] = (char) Math.max(c, d);
            }
        }

        return new String(charArray);
    }

    /**
     * 2023/12/13
     * 2132. 用邮票贴满网格图
     *
     * @param grid
     * @param stampHeight
     * @param stampWidth
     * @return
     */
    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        int m = grid.length;
        int n = grid[0].length;

        // 1. 计算 grid 的二维前缀和
        int[][] s = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                s[i + 1][j + 1] = s[i + 1][j] + s[i][j + 1] - s[i][j] + grid[i][j];
            }
        }

        // 2. 计算二维差分
        // 为方便第 3 步的计算，在 d 数组的最上面和最左边各加了一行（列），所以下标要 +1
        int[][] d = new int[m + 2][n + 2];
        for (int i2 = stampHeight; i2 <= m; i2++) {
            for (int j2 = stampWidth; j2 <= n; j2++) {
                int i1 = i2 - stampHeight + 1;
                int j1 = j2 - stampWidth + 1;
                if (s[i2][j2] - s[i2][j1 - 1] - s[i1 - 1][j2] + s[i1 - 1][j1 - 1] == 0) {
                    d[i1][j1]++;
                    d[i1][j2 + 1]--;
                    d[i2 + 1][j1]--;
                    d[i2 + 1][j2 + 1]++;
                }
            }
        }

        // 3. 还原二维差分矩阵对应的计数矩阵（原地计算）
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                d[i + 1][j + 1] += d[i + 1][j] + d[i][j + 1] - d[i][j];
                if (grid[i][j] == 0 && d[i + 1][j + 1] == 0) {
                    return false;
                }
            }
        }
        return true;
    }


    public TreeNode reverseOddLevels(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque();
        queue.offer(root);
        boolean flag = false;
        while (!queue.isEmpty()) {
            List<TreeNode> nodes = new ArrayList<>();
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                TreeNode poll = queue.poll();
                if (flag) {
                    nodes.add(poll);
                }
                if (poll.left != null) {
                    queue.offer(poll.left);
                    queue.offer(poll.right);
                }
            }
            if (false) {
                int left = 0, right = nodes.size() - 1;
                while (left < right) {
                    nodes.get(left).val = nodes.get(left).val ^nodes.get(right).val;
                    nodes.get(right).val = nodes.get(right).val ^nodes.get(left).val;
                    nodes.get(left).val = nodes.get(left).val ^nodes.get(right).val;
                    left++;
                    right--;
                }
            }
        }
        return root;
    }

    /**
     * 2023-12-31
     * 1154. 一年中的第几天
     * @param date
     * @return
     */
    public int dayOfYear(String date) {
        int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
        String s = date.substring(0, 4);
        Integer value = Integer.valueOf(s);
        //判断是否闰年
        if (value%400==0||(value%4==0&&value%100!=0)){
            days[1]=29;
        }
        int count=0;
        String s1 = date.substring(5, 7);
        String s2 = date.substring(8);
        for (int i=0; i<Integer.valueOf(s1)-1;i++){
            count+=days[i];
        }

        return count+Integer.valueOf(s2);
    }
}
