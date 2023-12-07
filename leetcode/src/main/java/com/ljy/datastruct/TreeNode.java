package com.ljy.datastruct;

/**
 * @ClassName TreeNode
 * @Description
 * @Author wangxh
 * @Date2023/5/19 12:26
 **/
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}