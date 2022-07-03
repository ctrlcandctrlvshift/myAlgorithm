package BinaryTreeAll;

//import sun.reflect.generics.tree.Tree;

import javax.sound.sampled.DataLine.Info;
import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * @author zhang
 * @ClassName: BinaryTreeAll.BinaryTree
 * @Package PACKAGE_NAME
 * @Description: 二叉树
 * @date 2021/12/1416:58
 */
public class BinaryTree {
    public static class Node{
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //先序遍历 递归
    public static void pre(Node head){
        if (head==null){
            return;
        }
        System.out.println(head.value);
        pre(head.left);
        pre(head.right);
    }
    public static void in(Node head){
        if (head==null){
            return;
        }

        in(head.left);
        System.out.println(head.value);
        in(head.right);
    }
    public static void pos(Node head){
        if (head==null){
            return;
        }
        pos(head.left);
        pos(head.right);
        System.out.println(head.value);
    }





    //先序遍历
    //栈：
    //1.弹出即打印
    //2.如有右，压入右
    //3.如有左，压入左
    public static void pre1(Node head){
        System.out.println("先序遍历：");
        if (head!=null){
            Stack<Node> stack=new Stack<>();
            stack.add(head);
            while (!stack.isEmpty()){
                //弹出即打印
                head=stack.pop();
                System.out.println(head.value + " ");
                //有右节点 压入  先右的原因是 栈是处理后压入的节点
                if (head.right!=null){
                    stack.push(head.right);
                }
                //有左节点 压入
                if (head.left!=null){
                    stack.push(head.left);
                }
            }
        }
        System.out.println(" ");
    }

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
  //144
    public static List<Integer> preorderTraversal(TreeNode root) {
        if (root==null){
            return new ArrayList<>();
        }
        Deque<TreeNode> stack=new LinkedList<>();
        List<Integer> list=new ArrayList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode cur=stack.pop();
            list.add(cur.val);
            if (cur.right!=null){
                stack.push(cur.right);
            }
            if (cur.left!=null){
                stack.push(cur.left);
            }
        }
        return list;
    }

    //后序遍历
    //栈：
    //1.弹出 再压入新栈
    //2.如有左，压入左
    //3.如有右，压入右
    //弹出新栈
    public static void pos1(Node head){
        System.out.println("后序遍历：");
        if (head!=null){
            Stack<Node> stack=new Stack<>();
            Stack<Node>stack1=new Stack<>();
            stack.add(head);
            while (!stack.isEmpty()){
                head=stack.pop();
                stack1.push(head);
                //先左再右  再反过来
                if (head.left!=null){
                    stack.push(head.right);
                }
                if (head.right!=null){
                    stack.push(head.left);
                }
            }
            while (!stack1.isEmpty()){
                System.out.println(stack1.pop().value + " ");
            }

        }
    }
    //中序遍历
    //栈：
    //1.整条左边界依次入栈
    //2. 1操作无法继续 弹出节点 打印 来到弹出节点的右节点 继续执行1操作
    public static void  in1(Node head){
        System.out.println("中序遍历：");
        if (head!=null){
            Stack<Node> stack=new Stack<>();
            while (!stack.isEmpty()||head !=null){
                if (head!=null){
                    stack.push(head);
                    head=head.left;
                }else {
                 head=stack.pop();
                 System.out.println(head.value + " ");
                 head=head.right;
                }
            }
        }
    }


    public static List<Integer> inorderTraversal(TreeNode root) {
        if (root==null){
            return new ArrayList<>();
        }
        Deque<TreeNode> stack=new LinkedList<>();
        List<Integer> list=new ArrayList<>();
        TreeNode cur=root;
        while (cur!=null){
            stack.push(cur);
            cur=cur.left;
        }
        while (!stack.isEmpty()) {
            cur = stack.pop();
            list.add(cur.val);
            cur = cur.right;
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
        }
        return list;
    }
    //后序遍历2
    public static void pos2(Node h){
        System.out.println("后序遍历：");
        if (h!=null){
            Stack<Node> stack=new Stack<>();
            Node c=null;
            stack.push(h);
            while (!stack.isEmpty()){
                c=stack.peek();
                //h是标记  标记左右是否完成
                //新节点 先处理左边
             if (c.left!=null&&h!=c.left&&h!=c.right){
                 stack.push(c.left);
                 //处理右边
             }else if (c.right!=null&&h!=c.right){
                 stack.push(c.right);
                 //处理头
             }else {
                 System.out.println(stack.pop().value + " ");
                 //h标记处理结束的节点
                 h=c;
             }
            }
        }





    }

    //宽度遍历
    public static void level(Node head){
        if (head==null){
            return;
        }
        Queue<Node>queue=new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()){
            Node cur=queue.poll();
            System.out.println(cur.value + " ");
            if (cur.left!=null){
                queue.add(cur.left);
            }
            if (cur.right!=null){
                queue.add(cur.right);
            }
        }
    }

//给定二叉树的根，返回给定树的最大宽度。
//树的最大宽度是所有级别之间的最大宽度。
//一层的宽度被定义为末端节点(最左和最右的非空节点)之间的长度，其中在一个完整的二叉树中出现的末端节点之间的空节点也被计算到长度计算中。
//它保证了答案将在一个32位有符号整数的范围内。
    static int dfsmax=0;
    public static int widthOfBinaryTree(TreeNode root) {
        if (root==null){
            return 0;
        }
        HashMap<Integer, Integer>  map=new HashMap<>();
        dfs(root,1,1,map);
        return dfsmax;
    }
    public static void dfs(TreeNode root,int level,int p,HashMap<Integer, Integer> map){
        if (root==null){
            return ;
        }
        if (!map.containsKey(level)){
            map.put(level,p);
        }
        dfsmax=Math.max(dfsmax,p-map.get(level)+1);
        dfs(root.left,level+1,2*p,map);
        dfs(root.right,level+1,2*p+1,map);
    }
    public static int widthOfBinaryTree2(TreeNode root){
        if (root==null){
            return 0;
        }
        HashMap <TreeNode,Integer> map=new HashMap<>();
        HashMap<TreeNode,Integer> val=new HashMap<>();
        Queue<TreeNode> queue=new LinkedList<>();
        int curLevel=1;
        int curMin=1;
        int max=0;
        queue.add(root);
        map.put(root,1);
        val.put(root,1);
        while(!queue.isEmpty()){
            TreeNode curNode=queue.poll();
            int level=map.get(curNode);
            int p=val.get(curNode);
            if (level!=curLevel){
                curLevel=level;
                curMin=p;
            }
            if (curNode.left!=null){
                queue.add(curNode.left);
                map.put(curNode.left,level+1);
                val.put(curNode.left,2*p);
            }
            if (curNode.right!=null){
                queue.add(curNode.right);
                map.put(curNode.right,level+1);
                val.put(curNode.right,2*p+1);
            }
            max=Math.max(max,p-curMin+1);
        }
        return max;
    }

    //求二叉树最大宽度 使用HashMap
    public static int maxWidthUseMap(Node head){
        if (head!=null){
            return 0;
        }
        Queue <Node> queue=new LinkedList<>();
        queue.add(head);
        //节点在哪一层
        HashMap<Node,Integer> levelMap = new HashMap<>();
        levelMap.put(head,1);
        //统计哪一层的节点
        int curLevel=1;
        //当前层宽度多少
        int curLevelNodes=0;
        int max=0;

        while (!queue.isEmpty()){
            Node cur =queue.poll();
            //当前节点在第几层
            int curNodeLevel=levelMap.get(cur);
            //宽度遍历
            if (cur.left!=null){
                levelMap.put(cur.left,curNodeLevel+1);
                queue.add(cur.left);
            }
            if (cur.right!=null){
                levelMap.put(cur.right,curNodeLevel+1);
                queue.add(cur.right);
            }
            //当前节点层数与当前统计层数一样  开始统计
            if (curNodeLevel==curLevel){
                curLevelNodes++;
                //如果不一样 上一层统计完毕
            }else {
                max=Math.max(max,curLevelNodes);
                //统计下一层
                curLevel++;
                //重置统计数为1 为1是因为已经进入下一层的节点了
                curLevelNodes=1;
            }
        }
        //最后一层没有新层结算  需要结算一次
        max=Math.max(max,curLevelNodes);
        return max;
    }

    //求二叉树最大宽度 不使用HashMap
    public static int maxWithNoMap(Node head){
        if (head==null){
            return 0;
        }
        Queue<Node> queue=new LinkedList<>();
        queue.add(head);
        //当前层的最后的节点
        Node curEnd=head;
        //下一层最右的节点
        Node nextEnd=null;
        int max=0;
        //当前层的宽度统计
        int curLevelNodes=0;
        while (!queue.isEmpty()){
            Node cur =queue.poll();
            if (cur.left!=null){
                queue.add(cur.left);
                //当前层保证下一层的数是在最右边
                nextEnd=cur.left;
            }
            if (cur.right!=null){
                queue.add(cur.right);
                //当前层保证下一层的数是在最右边
                nextEnd=cur.right;
            }
            //统计当层
            curLevelNodes++;
            //当前节点是当前层最后节点  统计结束
            if (cur==curEnd){
                max=Math.max(max,curLevelNodes);
                //重置统计
                curLevelNodes=0;
                curEnd=nextEnd;
            }

        }
        return max;
    }

    public static void pos3(Node h){
        if (h!=null){
         Stack<Node> stack=new Stack<>();
         stack.push(h);
         Node c;
         while (!stack.isEmpty()){
             c=stack.peek();
             if (c.left!=null&&h!=c.left&&h!=c.right){
                 stack.push(c.left);
             }else if (c.right!=null&&h!=c.right){
                 stack.push(c.right);
             }else {
                 System.out.println(stack.pop().value);
                 h=c;
             }
         }
        }
    }

    //先序序列化
    public static Queue<String> preSerial(Node head){
        Queue<String> queue=new LinkedList<>();
        pres(head,queue);
        return queue;
    }
    public static void pres(Node head,Queue<String> queue){
        if (head==null){
            //需要把null加上
            queue.add(null);
        }else {
            //ps：中序后序序列化  代码基本一致 只是add放置位置不同
         queue.add(String.valueOf(head.value));
         pres(head.left,queue);
         pres(head.right,queue);
        }
    }





    //先序反序列化
    public static Node bulidByPreQueue(Queue<String> queue){
        if (queue==null||queue.size()==0){
            return null;
        }
        return preb(queue);
    }
    public static Node preb(Queue<String> queue){
        String value =queue.poll();
        if (value==null){
            return null;
        }
        //ps：反序列化后序中序代码一直 仅是新建的node位置放置不一致
        //头
        Node head=new Node(Integer.valueOf(value));
        //左
        head.left=preb(queue);
        //右
        head.right=preb(queue);
        //全部建好 返回
        return head;
    }

    //以宽度序列化
    public static Queue<String> levelSerial(Node head){
        Queue<String> ans=new LinkedList<>();
        if (head==null){
            ans.add(null);
        }else {
            ans.add(String.valueOf(head.value));
            Queue<Node> queue=new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()){
                head=queue.poll();
                if (head.left!=null){
                    ans.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                }else {
                    //为空的也要加进去
                    ans.add(null);
                }
                if (head.right!=null){
                    ans.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                }else {
                    ans.add(null);
                }
            }

        }

        return ans;

    }
    //以宽度反序列化
    public static Node buildByLevelQueue(Queue<String>levelList){
        if (levelList.isEmpty()){
            return null;
        }
        Node head=generateNode(levelList.poll());
        Queue<Node> queue=new LinkedList<>();
        if (head!=null){
            queue.add(head);
        }
        //指针
        Node node=null;
        while (!queue.isEmpty()){
            node=queue.poll();
            node.left=generateNode(levelList.poll());
            node.right=generateNode(levelList.poll());
            //以上新建好左右节点 push进队列 进行宽度遍历
            if (node.left!=null){
                queue.add(node.left);
            }
            if (node.right!=null){
                queue.add(node.right);
            }
        }
        return head;

    }
    public static Node generateNode(String value){
        if (value==null){
            return null;
        }
        return new Node(Integer.valueOf(value));


    }

    //练习
    public static Queue<String>CopyOflevelSerial2(Node head){
        Queue<String> levelList=new LinkedList<>();
        if (head==null){
           levelList.add(null);
        }else {
            Queue<Node> queue = new LinkedList<>();
            levelList.add(String.valueOf(head.value));
            queue.add(head);
            while (!queue.isEmpty()) {
                head = queue.poll();
                if (head.left != null) {
                    levelList.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                } else {
                    levelList.add(null);
                }
                if (head.right != null) {
                    levelList.add(String.valueOf(head.right));
                    queue.add(head.right);
                } else {
                    levelList.add(null);
                }
            }
        }
        return levelList;
    }
    public static Node CopyOfbuildByLevelQueue(Queue<String>levelList){
        if (levelList==null||levelList.isEmpty()){
            return null;
        }
        Node head=generateNode(levelList.poll());
        Queue<Node> queue=new LinkedList<>();
        queue.add(head);
        Node node=null;
        while (!queue.isEmpty()){
            node=queue.poll();
            node.left=generateNode(levelList.poll());
            node.right=generateNode(levelList.poll());
            if (node.left!=null){
                queue.add(node.left);
            }
            if (node.right!=null){
                queue.add(node.right);
            }
        }
        return head;
    }

    public static class InfoBST{
        boolean isBST;
        int max;
        int min;

        public InfoBST(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }
    public static boolean isBST(Node head){
        if (head==null){
            return true;
        }
        InfoBST bst=processBST(head);
        return bst.isBST;
    }
    public static InfoBST processBST(Node head){
        if (head==null){
            return new InfoBST(true,0,0);
        }

        InfoBST left=processBST(head.left);
        InfoBST right=processBST(head.right);

        if (left.isBST&&right.isBST){
            int max=left.max;
            int min=right.min;
            if (max>head.value||min<head.value){
                return new InfoBST(false,0,0);
            }else {
                max=Math.max(Math.max(max,min),head.value);
                min=Math.min(Math.min(max,min),head.value);
                return new InfoBST(true,max,min);
            }
        }else {
            return new InfoBST(false,0,0);
        }

    }

    //序列化是将一个数据结构或对象转换成一个位序列的过程，以便将其存储在文件或内存缓冲区中，或通过网络连接链路传输，
    // 以便稍后在同一或另一个计算机环境中重新构建。
    //设计一个算法来序列化和反序列化一个二叉树。 对序列化/反序列化算法的工作方式没有限制。
    // 您只需要确保二叉树可以序列化为一个字符串，这个字符串可以反序列化为原始的树结构。
    //说明:输入/输出格式与LeetCode序列化二叉树的方式相同。 你不一定要遵循这种格式，所以请富有创造力，自己想出不同的方法。
// Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        if (root==null){
            return null;
        }
        Queue <TreeNode>queue=new LinkedList();
        queue.add(root);
        StringBuilder sb=new StringBuilder();
        while (!queue.isEmpty()){
            TreeNode cur=queue.poll();
            if (cur.val==Integer.MIN_VALUE){
                sb.append("/"+"*");
                continue;
            }
            sb.append("/"+cur.val);

            if (cur.left==null){
                queue.add(new TreeNode(Integer.MIN_VALUE));
            }else {
                queue.add(cur.left);
            }
            if (cur.right==null){
                queue.add(new TreeNode(Integer.MIN_VALUE));
            }else {
                queue.add(cur.right);
            }
        }
        return sb.toString();
    }
    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        if (data==null){
            return null;
        }
        Queue <Integer>val =new LinkedList<>();
        Queue<TreeNode> queue=new LinkedList<>();
        int start=0;
        for (int i = 1; i <data.length();i++) {
            if (data.charAt(i)=='/'){
                    String s=data.substring(start+1,i);
                    start=i;
                    if (s.equals("*")){
                        val.add(null);
                        continue;
                    }
                    val.add(Integer.parseInt(s));
            }
        }
        TreeNode head=new TreeNode(val.poll());
        queue.add(head);
        TreeNode cur=null;
        while (!queue.isEmpty()){
             cur=queue.poll();
             cur.left=generateNode(val.poll());
             cur.right=generateNode(val.poll());
             if (cur.left!=null){
                 queue.add(cur.left);
             }
             if (cur.right!=null){
                 queue.add(cur.right);
             }
        }
return head;
    }
    public static TreeNode generateNode(Integer c){
        if (c==null){
            return null;
        }
        return new TreeNode(c);

    }


    //打印一棵二叉树 (未完成)
//    public static void printInOrder(Node head,int height,String to,int len){
//        if (head==null){
//            return;
//        }
//        printInOrder(head.right,height+1,"v",len);
//        String val=to+head.value+to;
//        int lenM = val.length();
//        int lenL=(len-lenM)/2;
//        int lenR=len-lenM-lenL;
//
//    }

    //给任意节点找出其中序的后续节点
    //思路一：通过父节点找到头节点 再通过中序遍历加入到数组中 最后找到节点的后继节点
    //思路二：有右树：节点的右节点的最后一个左节点是后继节点
    //无右树：找到某一节点是父节点的左节点  该父节点是后继节点   左完成下一个必然是头节点  如果找不到左节点
    //那么该节点是整棵树最右节点 无后继节点
    public static class Node1<V>{
        V value;
        Node1<V> right;
        Node1<V> left;
        Node1 parent;

        public Node1(V value) {
            this.value = value;
        }
    }
    public static void in2(Node1 head){
        if (head==null){
            return;
        }
        Stack<Node1> stack= new Stack<>();
        while (!stack.isEmpty()||head!=null){
            if (head!=null){
                stack.push(head);
                head=head.left;
            }else {
                head=stack.pop();
                System.out.println(head.value+ " ");
                head=head.right;
            }
        }
    }
    //后继
    public static Node1 getSuccessorNode(Node1 node){
        if (node ==null){
            return null;
        }
        //有右子树  其右节点的最左节点是后继节点
        if (node.right!=null){
            return getLeftMost(node.right);
        }else {
            Node1 parent=node.parent;
            while (parent!=null&&parent.right==node){
                node=parent;
                parent=node.parent;
            }
            return parent;
        }

    }
    public static Node1 getLeftMost(Node1 node){
        if (node==null){
            return node;
        }
        while (node.left!=null){
            node=node.left;
        }
        return node;
    }

    //前驱
    public static Node1 getSuccessorPreNode(Node1 node){
        if (node==null){
            return null;
        }
        if (node.left!=null){
            return getRightMost(node.left);
        }else {
            Node1 parent=node.parent;
            while (parent!=null&&parent.left==node){
                node=parent;
                parent=node.parent;
            }
            return parent;
        }
    }
    public static Node1 getRightMost(Node1 node){
        if (node==null){
            return node;
        }
        while (node.right!=null){
            node=node.right;
        }
        return node;
    }


    //题目：请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开，
    //此时折痕是凹下去的，即折痕突起的方向指向纸条背面。如果从纸条的下边
    //向上方连续对折2次，压出新折痕展开，此时有三条折痕，从上到下依次是下折痕，下折痕和上折痕
    //对折n次  从上到下打印折痕方向
    // 例：n=1 打印：dowm
    //n=2 打印：down down up  （上凹下凸）

    //i是节点的层数  N一共层数  down ==true 凹 down==false 凸
    //头部为凹  左子树为凹  右子树为凸
    //递归模拟(中序遍历)
    public static void printProcess(int i,int N,boolean down){
        if (i>N){
            return;
        }
        printProcess(i+1,N,true);
        System.out.println(down ? "凹" : "凸");
        printProcess(i+1,N,false);
    }

    //已知一棵有n个节点的二叉树的根，其中每个节点都有node. val硬币。 整棵树总共有n个硬币。
    //在一次移动中，我们可以选择两个相邻的节点并将一枚硬币从一个节点移动到另一个节点。 移动
    // 可以是从父结点移动到子结点，或者从子结点移动到父结点。
    //返回使每个节点拥有一枚硬币所需的最小移动数。

    public static class InfoDC{
        int steps;
        int rest;

        public InfoDC(int steps, int rest) {
            this.steps = steps;
            this.rest = rest;
        }
    }
    public static int distributeCoins(TreeNode root) {
        if (root==null){
            return 0;
        }
        return processCoins(root).steps;

    }
    public static InfoDC processCoins(TreeNode root){
        if (root==null){
            return new InfoDC(0,0);
        }

        InfoDC l=processCoins(root.left);
        InfoDC r=processCoins(root.right);

        int step=0;
        int rest=root.val-1;

        if (l.rest>=0){
            step+=l.rest;
            rest+=l.rest;
        }else {
            step-=l.rest;
            rest+=l.rest;
        }
        if (r.rest>=0){
            step+=r.rest;
            rest+=r.rest;
        }else {
            step-=r.rest;
            rest+=r.rest;
        }

        step=step+r.steps+l.steps;
        return new InfoDC(step,rest);
    }


    public static void main(String[] args) {
        TreeNode node1=new TreeNode(0);
        TreeNode node2=new TreeNode(3);
        TreeNode node3=new TreeNode(0);
        node1.left=node2;
        node1.right=node3;

        System.out.println(distributeCoins(node1));


    }



    //递归套路
    //1.假设以X节点为头，假设可以向X左树和右树要任何信息
    //2.在上一步的假设下，讨论以X为头节点的树，得到答案的可能性（最重要）
    //3.列出所有可能性后，确定到底需要向左树和右树要什么样的信息
    //4.把左树信息和右树信息求全集，就是任何一棵树都需要返回的信息S
    //5.递归函数都返回S，每一棵子树都这么要求
    //6.写代码，在代码考虑如把左树的信息和右树的信息整合出整棵树的信息


    //求树的高度以及是否平衡
    public static class Info{
        public boolean isBalanced;
        public int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }
    public static Info process2(Node X){
        if (X==null){
            return new Info(true,0);
        }
        Info leftInfo=process2(X.left);
        Info rightInfo=process2(X.right);
        //哪个树高选哪个 再加1
        int height=Math.max(leftInfo.height,rightInfo.height)+1;
        boolean isBalanced=true;
        //左平？ 右平？ |左高-右高|<2？
        if (!leftInfo.isBalanced||!rightInfo.isBalanced||Math.abs(leftInfo.height- rightInfo.height)<2){
            isBalanced=false;
        }
        return new Info(isBalanced,height);
    }

    //求二叉树的两点最大距离
    //与X节点是否有关
    //无关：max（左最大距离，右最大距离）
    //有关：左高+右高+1
    public static class Info2{
        //最大距离
        public int maxDistance;
        public int height;

        public Info2(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }
    public static Info2 process3(Node X){
        if (X==null){
            return new Info2(0,0);
        }
        Info2 leftInfo=process3(X.left);
        Info2 rightInfo=process3(X.right);
        int height=Math.max(leftInfo.height,rightInfo.height)+1;
        int maxDistance=Math.max(
                Math.max(leftInfo.maxDistance,rightInfo.maxDistance),
                leftInfo.height+rightInfo.height+1);
        return new Info2(maxDistance,height);
    }


    //给定一棵二叉树的头节点head
    //返回这颗二叉树中最大的二叉搜索子树有多少节点
    //X无关 max（左是搜索二叉树的size 右是搜索二叉树的size）
    //X有关 左是搜索  右是搜索  且左Max<X  右Min>X
    public static class Info3{
        int maxSubBSTSzie;
        boolean isBST;
        int min;
        int max;

        public Info3(int maxSubBSTSzie, boolean isBST, int min, int max) {
            this.maxSubBSTSzie = maxSubBSTSzie;
            this.isBST = isBST;
            this.min = min;
            this.max = max;
        }
    }
    public static Info3 process4(Node X){
        if (X==null){
            return null;
        }
        Info3 leftInfo=process4(X.left);
        Info3 rightInfo=process4(X.right);

        int min=X.value;
        int max=X.value;

        if (leftInfo!=null){
             min=Math.max(min,leftInfo.min);
             max=Math.max(max,leftInfo.max);
        }
        if (rightInfo!=null){
            min=Math.max(min,rightInfo.min);
            max=Math.max(max,rightInfo.max);
        }

        int maxSubBSTSzie=0;
        if (leftInfo!=null){
            maxSubBSTSzie=leftInfo.maxSubBSTSzie;
        }
        if (rightInfo!=null){
            maxSubBSTSzie=Math.max(maxSubBSTSzie,rightInfo.maxSubBSTSzie);
        }
        boolean isBST=false;
        if ((leftInfo==null?true:leftInfo.isBST)&&
                (rightInfo==null?true:rightInfo.isBST)&&
                (leftInfo==null?true:leftInfo.max<X.value)&&
                (rightInfo==null?true:rightInfo.min>X.value)) {

            maxSubBSTSzie=(leftInfo==null?0:leftInfo.maxSubBSTSzie)
                    +(rightInfo==null?0:rightInfo.maxSubBSTSzie)
                    +1;
            isBST=true;
        }
        return new Info3(maxSubBSTSzie,isBST,min,max);
    }


//1.X来 X.happy+其下属所有节点不来的happy总值
//2.X不来 0+  其下属所有节点不来或者来的happy总值的max

//    题目二
//            派对的最大快乐值
//    员工信息的定义如下：
//    class Employee {
//        public int happy;1 这名员工可以带来的快乐值
//        List-Employee= subordinates;11 这名员工有哪些直接下级
//        公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、
//        没有环的
//        多叉树，
//        树的头节点是公司唯一的老板．
//        除老板之外的每个员工都有唯一的直接上级。
//        叶节点是没有
//        任何下属的基层员 工(subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级，
//        这个公司现在要办party.
//                你可以决定哪些员工来，哪些员工不来。但是要遵循如下规则。
//                1如果某个员工来了
//，那么这个员工的所有直接下级都不能来
//2派对的整体快乐值是所有到场员工快乐值的累加
//3.你的目标是让派对的整体快乐值尽量大
//        给定一棵多叉树的头节点boss，请返回派对的最大快乐值。

    public static class Employee{
        public int happy;
        public List<Employee> nexts;

    public Employee(int happy, List<Employee> nexts) {
        this.happy = happy;
        this.nexts = nexts;
    }

}
    public static class InfoH{
        //来的时候最大值
        int yes;
        //不来的时候最大值
        int no;

        public InfoH(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }
    public static InfoH processHappy(Employee X){
        if (X.nexts.isEmpty()){
            return new InfoH(X.happy,0);
        }
        int yes=X.happy;
        int no=0;
        for (Employee next:X.nexts) {
            InfoH nextInfo=processHappy(next);
            //自身加所有no
            yes+=nextInfo.no;
            no+=Math.max(nextInfo.yes,nextInfo.no);
        }
        return new InfoH(yes,no);
    }


    public static InfoH process(Employee e){
        if (e.nexts.isEmpty()){
            return new InfoH(e.happy,0);
        }
        int yes=e.happy;
        int no=0;
        for (Employee e1:e.nexts) {
            InfoH nextInfo=process(e1);
            yes+=nextInfo.no;
            no+=Math.max(nextInfo.yes,nextInfo.no);
        }


return new InfoH(yes,no);

    }


//叉树节点间的最大距离问题
//从二叉树的节点a出发，可以向上或者向下走，但沿途的节点只能经过一次，到达节点b时路
//径上的节点个数叫作a到b的距离，那么二叉树任何两个节点之间都有距离，求整棵树上的最
//大距离。
    public static class InfoMax{
        int height;
        int maxDistance;

    public InfoMax(int height, int maxDistance) {
        this.height = height;
        this.maxDistance = maxDistance;
    }
}
public static InfoMax process(Node node){
        if (node==null){
            return new InfoMax(0,0);
        }
        InfoMax infoLeft=process(node.left);
        InfoMax infoRight=process(node.right);
        int p1=infoLeft.maxDistance;
        int p2=infoRight.maxDistance;
        int p3=infoLeft.height+1+infoRight.height;
        int maxDistance=Math.max(p3,Math.max(p1,p2));
        int height=Math.max(infoLeft.height,infoRight.height)+1;
        return new InfoMax(height,maxDistance);
}





















}
