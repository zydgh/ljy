package com.ljy.datastruct;



/**
 * ST 表是用于解决 可重复贡献问题 的数据结构。
 * 可重复贡献问题 是指对于运算 \operatorname{opt}，满足 x\operatorname{opt} x=x，
 * 则对应的区间询问就是一个可重复贡献问题。例如，最大值有 \max(x,x)=x，gcd 有 \operatorname{gcd}(x,x)=x，
 * 所以 RMQ 和区间 GCD 就是一个可重复贡献问题。像区间和就不具有这个性质，如果求区间和的时候采用的预处理区间重叠了，
 * 则会导致重叠部分被计算两次，这是我们所不愿意看到的。另外，\operatorname{opt} 还必须满足结合律才能使用 ST 表求解。
 *
 * @ClassName ST
 * @Description
 * @Author wangxh
 * @Date2023/12/6 11:29
 **/
public class ST {

    public static TreeNode constructMaximumBinaryTree(int[] nums) {
        int n = nums.length;
        int k = (int) (Math.log(n) / Math.log(2));
        int[][] dp = new int[n][k + 1];

        for (int j = 0; j < k + 1; j++) {
            int i = 0;
            while (i + (1 << j) - 1 < n) {
                if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int l = dp[i][j - 1];
                    int r = dp[i + (1 << j - 1)][j - 1];

                    if (nums[l] < nums[r]){
                        dp[i][j] = r;
                    }else {
                        dp[i][j] = l;
                    }
                }
                i += 1;
            }
        }
        return build(nums, 0, n - 1, dp);
    }

    private static int query(int l, int r, int[] nums, int[][] dp) {
        int range = r - l + 1;
        int k = (int) (Math.log(range) / Math.log(2));
        int a = dp[l][k];
        int b = dp[r - (1 << k) + 1][k];
        if (nums[a] > nums[b]) {
            return a;
        }
        return b;
    }

    private static TreeNode build(int[] nums, int l, int r, int[][] dp) {
        if (l > r) {
            return null;
        }
        int k = query(l, r, nums, dp);
        TreeNode root = new TreeNode(nums[k]);
        root.left = build(nums, l, k - 1, dp);
        root.right = build(nums, k + 1, r, dp);
        return root;
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 6, 0, 5};
        TreeNode treeNode = constructMaximumBinaryTree(nums);
        System.out.println(treeNode);
    }
}
