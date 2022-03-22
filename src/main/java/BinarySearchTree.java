import javax.sound.sampled.DataLine.Info;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author zhang
 * @ClassName: BinarySearchTree
 * @Package PACKAGE_NAME
 * @Description: 二叉查找树
 * @date 2022/3/1718:40
 */
public class BinarySearchTree {
    public static class TreeNode {
     int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums==null||nums.length==0){return null;}

        return processBST(nums,0,nums.length-1);

    }
    public static TreeNode processBST(int []nums ,int left,int right){
        if (left==right){
            return new TreeNode(nums[left]);
        }
        if (left>nums.length){
            return null;
        }
        if (right<0){
            return null;
        }
        if (left>right){
            return null;
        }
        int mid=(left+right)/2;
        TreeNode cur=new TreeNode(nums[mid]);
        TreeNode l=processBST(nums,left,mid-1);
        TreeNode r=processBST(nums,mid+1,right);
        cur.left=l;
        cur.right=r;
        return cur;
    }
    //优化用快慢指针找中间数
     public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
     public static TreeNode sortedListToBST(ListNode head) {
          if (head==null){
              return null;
          }
          ArrayList <Integer>list =new ArrayList<>();
          while (head!=null){
              list.add(head.val);
              head=head.next;
          }
          return processListBST(list,0,list.size()-1);
         }
     public static TreeNode processListBST(ArrayList <Integer>nums ,int left,int right){
        if (left==right){
            return new TreeNode(nums.get(left));
        }
        if (left>nums.size()){
            return null;
        }
        if (right<0){
            return null;
        }
        if (left>right){
            return null;
        }
        int mid=(left+right)/2;
        TreeNode cur=new TreeNode(nums.get(mid));
        TreeNode l=processListBST(nums,left,mid-1);
        TreeNode r=processListBST(nums,mid+1,right);
        cur.left=l;
        cur.right=r;
        return cur;
    }


    //给定一个整数n，返回结构上唯一的BST(二叉搜索树)的数目，该树的n个节点的值从1到n都是唯一的。
    public static int numTrees(int n) {
        if (n==0){
            return 0;
        }
        return processNumT(n);
    }
    //n 指的是长度
    public static int processNumT(int n){
        if (n==0){
            return 1;
        }
        if (n==1){
            return 1;
        }
        int ans=0;
        for (int i = 1; i <=n ; i++) {
            int l=processNumT(i-1);
            int r=processNumT(n-i);
            ans+=l*r;
        }
        return ans;
    }

    public static int numTreesDp(int n) {
        if (n==0){
            return 0;
        }
        int [] dp=new int[n+1];
        dp[0]=1;
        dp[1]=1;
        for (int i = 2; i <=n ; i++) {
            for (int j = 1; j <=i ; j++) {
                int l= dp[j-1];
                int r= dp[i-j];
                dp[i]+=l*r;
            }
        }
        return dp[n];
    }

//给定一个整数n，返回所有结构上唯一的BST(二叉搜索树)，它有n个节点的唯一值从1到n。按任意顺序返回答案
public static List<TreeNode> generateTrees(int n) {
        if (n==0){
            return null;
        }
        return processTree(1,n);
}
    public static List<TreeNode> processTree(int left,int right){
        if (left==right){
            List<TreeNode> ans=new ArrayList<TreeNode>();
            ans.add(new TreeNode(left));
            return ans;
        }
        if (left>right){
            List<TreeNode> ans=new ArrayList<TreeNode>();
            ans.add(null);
            return ans;
        }
        List<TreeNode> ans=new ArrayList<TreeNode>();
        for (int i = left; i <=right ; i++) {
            List<TreeNode> l=processTree(left,i-1);
            List<TreeNode> r=processTree(i+1,right);
            for (TreeNode lcur:l){
                for (TreeNode rcur:r){
                    TreeNode head=new TreeNode(i);
                    head.left=lcur;
                    head.right=rcur;
                    ans.add(head);
                }
            }
        }
        return ans;
    }

//给定二叉搜索树(BST)的根，返回树中任意两个不同节点值之间的最小差值。
    public static class InfoDiff{
        int min;
        int max;
        int diff;

    public InfoDiff(int min, int max, int diff) {
        this.min = min;
        this.max = max;
        this.diff = diff;
    }
}
public static int minDiffInBST(TreeNode root) {
        if (root==null){
            return 0;
        }
        return processDiff(root).diff;
}
public static InfoDiff processDiff(TreeNode node){
        if (node==null){
            return null;
        }
        int max=0;
        int min=0;

        int diff=0;

        InfoDiff l=processDiff(node.left);
        InfoDiff r=processDiff(node.right);
        int ldiff=l==null?Integer.MAX_VALUE:node.val-l.max;
        int rdiff=r==null?Integer.MAX_VALUE:r.min-node.val;
        max=r==null?node.val:r.max;
        min=l==null?node.val:l.min;
        int bDiff=Integer.MAX_VALUE;
        if (l==null&&r!=null){
            bDiff=r.diff;
        }else if (r==null&&l!=null){
            bDiff=l.diff;
        }else if(r!=null&&l!=null){
            bDiff=Math.min(r.diff,l.diff);
    }
        diff=Math.min(Math.min(ldiff,rdiff),bDiff);
        return new InfoDiff(min,max,diff);

}


//给定一棵二叉树的根，返回任意一棵二叉搜索树(BST)中所有键的最大和。
//
//假设BST定义如下:
//节点的左子树只包含键值小于该节点键值的节点。
//节点的右子树只包含键值大于该节点键值的节点。
//左右子树都必须是二叉搜索树。

    public static class InfoSumBST{
        boolean isBST;
        int sum;
        int max;
        int min;

        public InfoSumBST(boolean isBST, int sum, int max, int min) {
            this.isBST = isBST;
            this.sum = sum;
            this.max = max;
            this.min = min;
        }
    }

    static  int sumMax=0;
    public static int maxSumBST(TreeNode root) {
        if (root==null){
            return 0;
        }
        processMaxSumBST(root);
        return sumMax;
    }
    public  static InfoSumBST processMaxSumBST(TreeNode node){
        if (node==null){
            return new InfoSumBST(true,0,Integer.MIN_VALUE,Integer.MAX_VALUE);
        }
        boolean isBST=false;
        int sum=0;
        int max=0;
        int min=0;
        InfoSumBST left=processMaxSumBST(node.left);
        InfoSumBST right=processMaxSumBST(node.right);
        if (left.isBST&&right.isBST){
            if (left.max<node.val&&right.min>node.val){
                sum=left.sum+right.sum+node.val;
                min=Math.min(left.min,node.val);
                max=Math.max(right.max,node.val);
                isBST=true;
            }
        }
        sumMax=Math.max(sumMax,sum);
        return new InfoSumBST(isBST,sum,max,min);
    }

//给定二叉搜索树的根，返回具有相同节点值的平衡二叉搜索树。如果有多个答案，返回其中任何一个。
//如果二叉搜索树的每个节点的两个子树的深度相差不大于1，则二叉搜索树是平衡的。
    public static TreeNode balanceBST(TreeNode root) {
        List<Integer> list=new ArrayList<>();
        sortTreeNodeBST(root,list);
        return getBalanceTree(list,0,list.size()-1);
}
public static void sortTreeNodeBST(TreeNode node,List<Integer> list){
        if (node==null){
            return ;
        }

        sortTreeNodeBST(node.left,list);
        list.add(node.val);
        sortTreeNodeBST(node.right,list);
}


public static TreeNode getBalanceTree(List<Integer> list,int left,int right){
    if (left==right){
        return new TreeNode(list.get(left));
    }
    if (left>right){
        return null;
    }
    int mid=(left+right)/2;
    TreeNode treeNode=new TreeNode(list.get(mid));
    TreeNode l=getBalanceTree(list,left,mid-1);
    TreeNode r=getBalanceTree(list,mid+1,right);

    treeNode.left=l;
    treeNode.right=r;

    return treeNode;
}





    public static void main(String[] args) {
//        int []nums={1,2,3};
//        TreeNode ans=sortedArrayToBST(nums);
//        System.out.print(ans.left.val);
//        System.out.print(ans.val);
//        System.out.print(ans.right.val);

//        ListNode node1=new ListNode(1);
//        ListNode node2=new ListNode(2);
//        ListNode node3=new ListNode(3);
//        node1.next=node2;
//        node2.next=node3;
//
//        TreeNode ans=sortedListToBST(node1);
//
//        System.out.print(ans.left.val);
//        System.out.print(ans.val);
//        System.out.print(ans.right.val);
//
//        System.out.println(numTrees(4));
//        System.out.println(numTreesDp(4));

//        List<TreeNode> ans=generateTrees(3);
//        TreeNode node1=new TreeNode(4);
//        TreeNode node2=new TreeNode(8);
//        TreeNode node3=new TreeNode(6);
//        TreeNode node4=new TreeNode(1);
//        TreeNode node5=new TreeNode(9);
//        TreeNode node6=new TreeNode(-5);
//        TreeNode node7=new TreeNode(-3);
//        TreeNode node8=new TreeNode(4);
//        TreeNode node9=new TreeNode(10);
//        node1.left=node2;
//        node2.left=node3;
//        node2.right=node4;
//        node3.left=node5;
//        node4.left=node6;
//        node4.right=node8;
//        node6.right=node7;
//        node8.right=node9;

        TreeNode node10=new TreeNode(1);
        TreeNode node11=new TreeNode(2);
        TreeNode node12=new TreeNode(3);
      //  TreeNode node13=new TreeNode(20);

        node11.left=node10;
        node11.right=node12;

        TreeNode ans=balanceBST(node11);


       // System.out.println(maxSumBST(node10));


    }

}
