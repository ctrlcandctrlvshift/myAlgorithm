import org.w3c.dom.Node;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhang
 * @ClassName: Test2
 * @Package PACKAGE_NAME
 * @Description:
 * @date 2022/4/29:40
 */
public class Test2 {

    //长度为n的链表的每个节点都包含一个额外的随机指针，该指针可以指向链表中的任何节点，也可以为空。
    //构造列表的深层副本。 深度复制应该恰好由n个全新的节点组成，其中每个新节点的值都被设置为对应的原始节点的值。
    // 新节点的下一个指针和随机指针都应该指向复制列表中的新节点，以便原始列表和复制列表中的指针表示相同的列表状态。
    // 新列表中的指针都不应该指向原始列表中的节点。
    //例如，如果原始链表中有两个节点X和Y，其中X.random——> Y，那么对于复制的链表中相应的两个节点X和Y，则有X.random——> Y。
    //返回被复制的链表的头。
    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    public static Node copyRandomList(Node head) {
        if (head==null){
            return null;
        }
        Node cur=head;
        Node next=null;
        while (cur!=null){
            next=cur.next;
            Node node=new Node(cur.val);
                cur.next=node;
                node.next=next;
                cur=next;
        }
        cur=head;
        while (cur!=null){
            cur.next.random=cur.random!=null?cur.random.next:null;
            cur=cur.next.next;
        }
        cur=head;
        Node newHead=cur.next;
        while (cur!=null){
            next=cur.next;
            if (cur.next.next==null){
                cur.next=null;
                next.next=null;
            }else {
                cur.next=cur.next.next;
                next.next=next.next.next;
            }
            cur=cur.next;
        }
        return newHead;
    }


      static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int x) {
          val = x;
          next = null;
      }
  }

//给定链表的头，判断链表中是否有一个循环。
//如果链表中有某个节点可以通过连续跟随下一个指针再次到达，那么链表中就存在一个循环。
// 在内部，pos用于表示tail的下一个指针所连接的节点的索引。 注意pos不是作为参数传递的。
//如果链表中有一个循环，则返回true。 否则,返回false。

public static boolean hasCycle(ListNode head) {
        if (head==null){
            return false;
        }
        ListNode fast=head.next;
        ListNode slow=head;
        while (fast!=null&&fast.next!=null&&fast.next.next!=null){
            if (fast==slow){
                return true;
            }
            fast=fast.next.next;
            slow=slow.next;
        }
        return false;
}
//给定链表的头，返回循环开始的节点。 如果没有循环，则返回null。
//如果链表中有某个节点可以通过连续跟随下一个指针再次到达，那么链表中就存在一个循环。 在内部，pos用于表示tail的下一个指针所连接的节点的索引(0索引)。 如果没有循环，则为-1。 注意pos不是作为参数传递的。
//请勿修改链表。
public static ListNode detectCycle(ListNode head) {
    if (head==null||head.next==null||head.next.next==null){
        return null;
    }
    ListNode fast=head.next.next;
    ListNode slow=head.next;
    while (slow!=fast){
        if (fast==null||fast.next==null||fast.next.next==null){
            return null;
        }
        fast=fast.next.next;
        slow=slow.next;
    }
    fast=head;
    while (slow!=fast){
        slow=slow.next;
        fast=fast.next;
    }

    return fast;
}

    //给定一个整数数组nums包含n + 1个整数，其中每个整数都在[1,n]的范围内。
    //nums中只有一个重复的数字，返回这个重复的数字。
    //您必须在不修改数组nums的情况下解决这个问题，并且只使用常量的额外空间。
    public static int findDuplicate(int[] nums) {
        if (nums==null){
            return -1;
        }
        for (int n:nums){
            if (nums[Math.abs(n)-1]<0) {
                return Math.abs(n);
            }else {
                nums[Math.abs(n)-1]*=-1;
            }
        }
        return -1;
    }
//二分
    public static int findDuplicate2(int[] nums) {
        if (nums==null){
            return -1;
        }

        return 0;
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s==null||s.length()==0){
            return 0;
        }
        HashMap<Character, Integer> map=new HashMap<>();
        int max=Integer.MIN_VALUE;
        int n=s.length();
        int near=0;
        for (int i = 0; i <n ; i++) {
            char cur=s.charAt(i);
            if (map.containsKey(cur)){
                int index=map.get(cur);
                if (index<near){
                    max=Math.max(max,i-near+1);
                }else {
                    near=index+1;
                }
            }else {
                max=Math.max(max,i-near+1);
            }
            map.put(cur,i);
        }

        return max;
    }



    //给定两棵二叉树的根root和subRoot，如果有根的子树与subRoot的节点值相同，则返回true，否则返回false。
    //二叉树的子树是由树中的一个节点和该节点的所有后代组成的树。这棵树也可以被认为是它自己的一个子树。

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
    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root==null||subRoot==null){
            return false;
        }
        StringBuilder sb=new StringBuilder();
        nodeToString(root,sb);
        String rootStr=sb.toString();
        sb=new StringBuilder();
        nodeToString(subRoot,sb);
        String subRootStr=sb.toString();
        return rootStr.contains(subRootStr);
    }

    public static void nodeToString(TreeNode node,StringBuilder sb){
        if (node==null){
            sb.append("#");
            return;
        }
        sb.append("$"+node.val);
        nodeToString(node.left,sb);
        nodeToString(node.right,sb);
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs==null||strs.length==0) {
            return "";
        }
        int maxLen=0;
        boolean noSame=false;
        for (int i = 0; i <strs[0].length() ; i++) {
            char firstStrChar=strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i>strs[j].length()-1){
                    noSame=true;
                    break;
                }
                if (firstStrChar!=strs[j].charAt(i)){
                    noSame=true;
                }
            }
            if (noSame){
                break;
            }
                maxLen++;
        }
        return maxLen==0?"":strs[0].substring(0,maxLen);
    }



    //496
    //数组中某个元素x的下一个更大的元素是同一个数组中x右边的第一个更大的元素。
    //给定两个不同的0索引整数数组nums1和nums2，其中nums1是nums2的子集。
    //对于每个0 <= i < nums1。 长度，找到索引j使nums1[i] == nums2[j]，并确定nums2中nums2[j]的下一个更大的元素。
    // 如果没有下一个更大的元素，那么这个查询的答案是-1。
    //返回长度为nums1的数组。 长度，使ans[i]是上面描述的下一个更大的元素。
//    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
//
//    }

    //给定一个由温度表示每日温度的整数数组，返回一个数组answer，
    // 其中answer[i]是第i天之后需要等待的天数，
    // 以获得更高的温度。如果未来没有这样的一天，请保留answer[i] == 0。
    public static int[] dailyTemperatures(int[] temperatures) {
        if (temperatures==null||temperatures.length==0){
            return null;
        }
        int [] result=new int[temperatures.length];
        if (temperatures.length==1){
            return  result;
        }
        //
        Stack<Integer> stackIndex=new Stack<>();
        stackIndex.push(0);
        for (int i = 1; i <temperatures.length ; i++) {
            while (!stackIndex.isEmpty()&&temperatures[i]>temperatures[stackIndex.peek()]){
                result[stackIndex.peek()]=i-stackIndex.peek();
                stackIndex.pop();
            }
            stackIndex.push(i);
        }
        return  result;

    }



    //503
    public static int[] nextGreaterElements(int[] nums) {
        if (nums==null|| nums.length==0){
            return null;
        }
        int []res =new int[nums.length];
        Arrays.fill(res,-1);
        if (res.length==1){
            return res;
        }
        Stack<Integer> stack=new Stack<>();
        stack.push(0);
        for (int i = 1; i <2*nums.length ; i++) {
            while (!stack.isEmpty()&&nums[stack.peek()]<nums[i>=nums.length?i-nums.length:i]){
                res[stack.peek()]=nums[i>=nums.length?i-nums.length:i];
                stack.pop();
            }
            if (i<nums.length){
                stack.push(i);
            }
        }
        return res;
    }

    //962
    //整数数组nums中的斜坡是一对(i, j)，
    // 其中i &lt;J and nums[i] &lt;= nums[J]。
    // 匝道的宽度为j - i。给定一个整数数组nums，
    // 返回匝道的最大宽度，单位为nums。如果nums中没有ramp，则返回0。

    public static int maxWidthRamp(int[] nums) {
        // 先构造单调递减栈
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[stack.peek()]) {
                stack.push(i);
            }
        }
        // 从右往左遍历，与单调栈做比较
        int dis = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                dis = Math.max(dis, i - stack.peek());
                stack.pop();
            }
        }
        return dis;
    }
    //84
    //给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
    //求在该柱状图中，能够勾勒出来的矩形的最大面积。
    //以下是柱状图的示例，其中每个柱子的宽度为 1，给定的高度为 [2,1,5,6,2,3]。
    //图中阴影部分为所能勾勒出的最大矩形面积，其面积为 10 个单位。
    public static int largestRectangleArea(int[] heights) {
        if (heights==null||heights.length==0){
            return 0;
        }
        int len=heights.length;
        int []leftw=new int[len];
        int []rightw=new int[len];

        Stack <Integer> stack=new Stack<>();
        stack.push(0);

        for (int i = 1; i <len ; i++) {
            while (!stack.isEmpty()&&heights[stack.peek()]>heights[i]){
                leftw[stack.peek()]=i-stack.peek()-1;
                stack.pop();
            }
            stack.push(i);
        }
        while (!stack.isEmpty()){
            leftw[stack.peek()]=len-stack.peek()-1;
            stack.pop();
        }
        stack.push(len-1);
        for (int i = len-2; i >=0 ; i--) {
            while (!stack.isEmpty()&&heights[stack.peek()]>heights[i]){
                rightw[stack.peek()]=stack.peek()-i-1;
                stack.pop();
            }
            stack.push(i);
        }
        while (!stack.isEmpty()){
            rightw[stack.peek()]=stack.peek();
            stack.pop();
        }
        int max=0;
        for (int i = 0; i <len; i++) {
            int leftArea=leftw[i]*heights[i];
            int rightArea=rightw[i]*heights[i];
            int midArea=heights[i];
            max=Math.max(max,leftArea+rightArea+midArea);
        }
        return max;
    }
    //85
    public static int maximalRectangle(char[][] matrix) {
        if (matrix==null||matrix.length==0){
            return 0;
        }

        int max=0;
        int []heights=new int[matrix[0].length];
        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j <matrix[0].length ; j++) {
                heights[j]=matrix[i][j]=='0'?0:heights[j]+1;
            }
            max=Math.max(max,largestRectangleArea(heights));
        }
        return max;
    }
    //316
    public static  String removeDuplicateLetters(String s) {
        if(s==null||s.length()==0){
            return null;
        }
        HashSet <Character>set=new HashSet();
        Stack <Character> stack=new Stack<>();
        int [] last_index=new int[26];

        for (int i = 0; i <s.length() ; i++) {
            last_index[s.charAt(i)-'a']=i;
        }
        //维持栈的单调性
        for (int i = 0; i <s.length() ; i++) {
            char cur=s.charAt(i);
            if (!set.contains(cur)){
             while (!stack.isEmpty()&&stack.peek()>cur&&last_index[stack.peek()-'a']>i){
                    set.remove(stack.pop());
                }
                set.add(cur);
                stack.push(cur);
            }
        }
        StringBuilder sb=new StringBuilder();
        for(char c:stack){
            sb.append(c);
        }
        return sb.toString();

    }
    //581
    public static int findUnsortedSubarray(int[] nums) {
        if (nums==null||nums.length==0||nums.length==1){
            return 0;
        }

        Stack <Integer> stack=new Stack<>();
        stack.push(0);
        int start=Integer.MAX_VALUE;
        int end=0;
        int last=Integer.MIN_VALUE;
        for (int i = 1; i <nums.length ; i++) {
            while (!stack.isEmpty()&&nums[stack.peek()]>nums[i]){
                start=Math.min(start,stack.peek());
                end=i;
                last=Math.max(last,nums[stack.peek()]);
                stack.pop();
            }
            if (!stack.isEmpty()&&nums[stack.peek()]==nums[i]){
                if (start==Integer.MAX_VALUE){
                    start=stack.peek();
                }
            }
            if (last>nums[i]){
                end=i;
            }
            stack.push(i);
        }
        return end==0?0:end-start+1;
    }

    //17
    public static List<String> letterCombinations(String digits) {
        if (digits==null||digits.length()==0){
            return new ArrayList<>();
        }
        HashMap<Integer, List<String>> map=new HashMap<>();
        int last=0;
        for (int i = 2; i <=9 ; i++) {
            if (i==7||i==9){
                List<String> list=new ArrayList<>();
                for (int j = 0; j <4 ; j++) {
                    char c=(char)('a'+last+j);
                    String s=String.valueOf(c);
                    list.add(s);
                }
                map.put(i,list);
                last+=4;
                continue;
            }
            List<String> list=new ArrayList<>();
            for (int j = 0; j <3 ; j++) {
                char c=(char) ('a'+last+j);
                String s=String.valueOf(c);
                list.add(s);
            }
            last+=3;
            map.put(i,list);
        }

        List<String> res=new ArrayList<>();
        int a=0;
        int first=-1;
        while (a<digits.length()){
            if (digits.charAt(a)!='1'||digits.charAt(a)!='0'){
                first=Integer.parseInt(String.valueOf(digits.charAt(a)));
                a++;
                break;
            }
            a++;
        }
        if (first==-1){
            return res;
        }
        int second=-1;
        while (a<digits.length()){
            if (digits.charAt(a)!='1'||digits.charAt(a)!='0'){
                second=Integer.parseInt(String.valueOf(digits.charAt(a)));
                a++;
                break;
            }
            a++;
        }
        if (second==-1){
            return map.get(first);
        }
        res=process(map.get(first),map.get(second),0,new ArrayList<>());
        while (a<digits.length()){
            if (digits.charAt(a)!='1'||digits.charAt(a)!='0'){
                int c=Integer.parseInt(String.valueOf(digits.charAt(a)));
                res=process(res,map.get(c),0,new ArrayList<>());
            }
            a++;
        }
        return res;
    }
    public static List<String> process(List<String> list1,List<String>list2,int index,List<String>list){
        if (index==list1.size()){
            return list;
        }
        for (int i = 0; i <list2.size() ; i++) {
         list.add(list1.get(index)+list2.get(i));
        }
        return process(list1,list2,index+1,list);
    }

    //20
    public static boolean isValid(String s) {
        if (s==null||s.length()==0){
            return false;
        }
        Stack<Character> stack=new Stack<>();
        for (int i = 0; i <s.length() ; i++) {
            char c=s.charAt(i);
            switch (c){
                case '(':
                    stack.push('(');
                    break;
                case ')':
                    if (stack.isEmpty()||stack.peek()!='('){
                        return false;
                    }
                    stack.pop();
                    break;
                case '[':
                    stack.push('[');
                    break;
                case ']':
                    if (stack.isEmpty()||stack.peek()!='['){
                        return false;
                    }
                    stack.pop();
                    break;
                case '{':
                    stack.push('{');
                    break;
                case '}':
                    if (stack.isEmpty()||stack.peek()!='{'){
                        return false;
                    }
                    stack.pop();
                    break;
                default:break;
            }
        }
        if (!stack.isEmpty()){
            return false;
        }
        return true;
    }
    //21
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1==null&&list2==null){
            return null;
        }
        if (list1!=null&&list2==null){
            return list1;
        }
        if (list1==null&&list2!=null){
            return list2;
        }
        int f;
        if (list1.val>=list2.val){
            f=list2.val;
            list2=list2.next;
        }else {
            f=list1.val;
            list1=list1.next;
        }
        ListNode res=new ListNode(f);
        ListNode cur=res;

        while (list1!=null&&list2!=null){
            ListNode next=new ListNode();
            if (list1.val>=list2.val){
                next.val=list2.val;
                cur.next=next;
                cur=next;
                list2=list2.next;
            }else {
                next.val=list1.val;
                cur.next=next;
                cur=next;
                list1=list1.next;
            }
        }
        while (list1!=null){
            ListNode next=new ListNode();
            next.val=list1.val;
            cur.next=next;
            cur=next;
            list1=list1.next;
        }
        while (list2!=null){
            ListNode next=new ListNode();
            next.val=list2.val;
            cur.next=next;
            cur=next;
            list2=list2.next;
        }

        return res;
    }

    //22
    public static List<String> generateParenthesis(int n) {
        if (n==0){
            return null;
        }
        List<String> res=new ArrayList<>();
        processParenthesis(2*n,0,new StringBuilder(),res);
        return res;
    }
    public static void processParenthesis(int n,int index,StringBuilder sb,List<String> res){
        if (index==n){
            if (isParent(sb.toString())){
                res.add(sb.toString());
            }
            return;
        }
        sb.append("(");
        processParenthesis(n,index+1,sb,res);
        sb.deleteCharAt(sb.length()-1);

        sb.append(")");
        processParenthesis(n,index+1,sb,res);
        sb.deleteCharAt(sb.length()-1);
    }
    public static boolean isParent(String s){
        int counter=0;
        for (int i = 0; i <s.length() ; i++) {
            char c =s.charAt(i);
            switch (c){
                case '(':counter++;break;
                case ')':counter--;if(counter<0){return false;}break;
            }
        }
        return counter==0?true:false;
    }
    //34
    public static int[] searchRange(int[] nums, int target) {
        if (nums==null||nums.length==0){
            return new int[]{-1,-1};
        }
        int left=0;
        int right=nums.length-1;
        int mid=(left+right)/2;
        while (left<=right){
            int i=nums[mid];
            if (i<target){
                left=mid+1;
                mid=(right+left)/2;
            }else if (i==target){
                int l=mid-1;
                int r=mid+1;
                while (l>=0&&nums[l]==target){
                    l--;
                }
                while (r<nums.length&&nums[r]==target){
                    r++;
                }
                return new int[]{l+1,r-1};
            }else {
                right=mid-1;
                mid=(right+left)/2;
            }
        }
        return new int[]{-1,-1};
    }
    //46
    public static List<List<Integer>> permute(int[] nums) {
        if (nums==null||nums.length==0){
            return null;
        }
        List<List<Integer>> res=new ArrayList<>();
        processList(res,nums,0,new ArrayList<>(),new HashSet<>());
        return res;
    }
    public static void processList(List<List<Integer>> res,int []nums,int index,List<Integer> list,HashSet<Integer> set){
        if (index==nums.length){
            List<Integer> newOne=new ArrayList<>(list);
            res.add(newOne);
            return;
        }
        for (int i = 0; i <nums.length ; i++) {
            if (!set.contains(i)){
                list.add(nums[i]);
                set.add(i);
                processList(res,nums,list.size(),list,set);
                list.remove(list.size()-1);
                set.remove((Integer)i);
            }
        }

    }
    //33
    public static int search(int[] nums, int target) {
        if (nums==null||nums.length==0){
            return 0;
        }
        int l=0;
        int r=nums.length-1;
        int max=0;
        while (l<r){
            int m=(l+r)/2;
            if (nums[m]>nums[l]&&nums[m]>nums[r]){
                max=m;
                l=m;
            }else {
                max=m;
                r=m;
            }
        }
        int res=-1;
        if (target<=nums[max]&&target>=nums[0]){
            res=bSearch(nums,0,max,target);
        }else {
            res=bSearch(nums,max+1,nums.length-1,target);
        }
        return res;
    }
    public static int bSearch(int []nums,int l,int r,int target){
        int index=-1;
        while (l<=r){
            int m=(l+r)/2;
            if (target>nums[m]){
                l=m+1;
            }else if (nums[m]==target){
                index=m;
                break;
            }else if (target<nums[m]){
                r=m-1;
            }
        }
        return index;
    }


    public static boolean isBadVersion(int version){
        return false;
    }
    //278
    public static int firstBadVersion(int n) {
        int r=n;
        int l=1;
        while (l<r){
            int m=l+(r-l)/2;
            if (!isBadVersion(m)){
                l=m+1;
            }else{
                r=m;
            }
        }
        return r;
    }

    //658
    public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        if (arr==null||arr.length==0||k==0){
            return null;
        }
        List<Integer> res=new ArrayList<>();
        int l=0;
        int r=arr.length-1;
        int index=arr.length-1;
        while (l<=r){
            int m=l+(r-l)/2;
            if (arr[m]>x){
                index=m;
                r=m-1;
            }else if (arr[m]==x){
                index=m;
                break;
            }else if (arr[m]<x){
                l=m+1;
            }
        }
        int rc=0;
        int lc=1;
        while (res.size()<k){
            int rval=index+rc<arr.length?arr[index+rc]:Integer.MAX_VALUE/2;
            int lval=index-lc>=0?arr[index-lc]:Integer.MAX_VALUE/2;
            if (Math.abs(rval-x)<Math.abs(lval-x)){
                res.add(rval);
                rc++;
            }else if (Math.abs(rval-x)==Math.abs(lval-x)){
                lc++;
                res.add(lval);
            }else if (Math.abs(rval-x)>Math.abs(lval-x)){
                res.add(lval);
                lc++;
            }
        }
        Collections.sort(res);
        return res;
    }

    //9434
    public static void rotate(int[][] matrix) {
        if (matrix==null||matrix.length==0||matrix[0].length==0){
            return;
        }
        
        //倒置
        int len=matrix.length;
        for (int i = 0; i <len ; i++) {
            for (int j = i+1; j <len ; j++) {
                int temp=matrix[i][j];
                matrix[i][j]=matrix[j][i];
                matrix[j][i]=temp;
            }
        }

        //反射
        for (int i = 0; i <len ; i++) {
            for (int l=0,r=len-1; l<r ; r--,l++) {
                int temp=matrix[i][l];
                matrix[i][l]=matrix[i][r];
                matrix[i][r]=temp;
            }
        }
    }


    //54
    public static List<Integer> spiralOrder(int[][] matrix) {
        if (matrix==null||matrix.length==0||matrix[0].length==0){
            return null;
        }
        List<Integer> res=new ArrayList<>();
        int row=matrix.length;
        int col=matrix[0].length;

        int sRow=0;
return null;
    }


    //3Sum
    //15
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res=new ArrayList<>();
        if (nums==null||nums.length<3){
            return res;
        }
        Arrays.sort(nums);
        for (int i = 0; i <nums.length-2 ; i++) {
            int cur=nums[i];
            int l=i+1;
            int r=nums.length-1;
            if (i>0&&cur==nums[i-1]){
                continue;
            }
            if (nums[i+1]>0&&nums[r]>0&&cur>0){
                break;
            }
            if (nums[i+1]<0&&nums[r]<0&&cur<0){
                break;
            }
            while (l<r){
                if (l!=i+1&&nums[l-1]==nums[l]){
                    l++;
                    continue;
                }
                if (r+1<nums.length&&nums[r]==nums[r+1]){
                    r--;
                    continue;
                }
                if (cur+nums[l]+nums[r]==0){
                    //浪费空间
                    List<Integer> list=new ArrayList<>();
                    list.add(cur);
                    list.add(nums[l]);
                    list.add(nums[r]);
                    res.add(list);
                    l++;
                    r--;
                }else if (cur+nums[l]+nums[r]<0){
                    l++;
                }else if (cur+nums[l]+nums[r]>0){
                    r--;
                }
            }

        }
        return res;
    }


    //16
    public static int threeSumClosest(int[] nums, int target) {
        if (nums==null||nums.length<3){
            return 0;
        }
        Arrays.sort(nums);
        int closest=target;
        int minDiff=Integer.MAX_VALUE;
        for (int i = 0; i <nums.length-2 ; i++) {
            int cur=nums[i];
            int l=i+1;
            int r=nums.length-1;
            while (l<r){
                int sum=nums[l]+nums[r]+cur;
                int diff=Math.abs(sum-target);
                if (minDiff>diff){
                    minDiff=diff;
                    closest=sum;
                }
                if (minDiff==0){
                    return closest;
                }
                if (sum>target){
                    r--;
                }else {
                    l++;
                }
            }
        }
        return closest;
    }

    //49
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res=new ArrayList<>();
        if (strs==null||strs.length==0){
            return res;
        }
        int len=strs.length;
        HashMap<String, List<String>> map=new HashMap<>();
        for (int i = 0; i <len ; i++) {
            String cur=strs[i];
           char []charArray=cur.toCharArray();
           Arrays.sort(charArray);
           String tmp=String.copyValueOf(charArray);
           if (map.containsKey(tmp)){
               List list=map.get(tmp);
               list.add(cur);
               map.put(tmp,list);
           }else {
               List<String> list=new ArrayList<>();
               list.add(cur);
               map.put(tmp,list);
           }
        }
        Set<String> set=map.keySet();
        for(String s:set){
            res.add(map.get(s));
        }
        return res;
    }

    //53
    public static int maxSubArray(int[] nums) {
        int n = nums.length;
        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;
        for(int i=0; i<n; i++){
            int firstOption = nums[i];
            int secondOption = nums[i] + currentSum;

            currentSum = Math.max(firstOption, secondOption);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }



    //55
    public static boolean canJump(int[] nums) {
        if (nums==null){
            return false;
        }
        if (nums.length==0||nums.length==1){
            return true;
        }
        if (nums[0]>=nums.length){
            return true;
        }
        for (int i = 0; i <=nums[0] ; i++) {
            boolean isOk=process(nums,i,0);
            if (isOk){
                return true;
            }
        }
        return false;
    }
    //step 必行的步数
    public static boolean process(int []nums,int step,int curIndex){
        if (curIndex+step>nums.length-1){
            return false;
        }
        if (step==0&&curIndex!=nums.length-1){
            return false;
        }
        //下一个位置
        int nextCur=step+curIndex;
        //下一步可走步数
        int nextStep=nums[step+curIndex];
        if (nextCur+nextStep>=nums.length-1){
            return true;
        }
        for (int i = 0; i <=nextStep ; i++) {
            boolean isOk=process(nums,i,nextCur);
            if (isOk){
                return true;
            }
        }
        return false;
    }
    //贪心
    public static boolean canJump2(int[] nums){
        if (nums==null){
            return false;
        }
        if (nums.length==0||nums.length==1){
            return true;
        }
        int last=0;
        for (int i = 0; i <nums.length-1 ; i++) {
            int sum=i+nums[i];
            if (i+nums[i]>=nums.length-1&&last>=i){
                return true;
            }
            if (last>=i){
                last=Math.max(last,sum);
            }
        }
        return false;
    }

    //45
//    public static int jump(int[] nums) {
//
//    }
    //70
    public static int climbStairs(int n) {
        if (n==0){
            return 0;
        }
        if (n==1){
            return 1;
        }
        return process(n,0);
    }
    public static int process(int n,int sum){
        if (sum==n){
            return 1;
        }
        if (sum>n){
            return 0;
        }
        int one=process(n,sum+1);
        int two=process(n,sum+2);
        return one+two;
    }

    public static int climbStairs2(int n) {
        if(n == 0 || n == 1) {
            return 1;
        }
        int prev = 1;
        int prev1 = 1;
        for(int i =2 ;i<=n;i++){
            int curr = prev + prev1;
            prev1  = prev;
            prev = curr;
        }
        return prev;
    }


    public static int [] quiet(int [] nums){
        if (nums==null||nums.length==0){
            return null;
        }
        int len=nums.length;
        int [] res=new int[len];
        for (int i = 0; i <len ; i++) {
            int sum=0;
            for (int j = 0; j <len ; j++) {
                if (j==i){
                    continue;
                }
                int distance=Math.abs(i-j);
                int weight=len-distance;
                int val=nums[j]>weight?nums[j]-weight:0;
                sum+=val;
            }
            res[i]=sum;
        }
        return res;
    }
    //优化

    public static void main(String[] args) {
        int []nums={10,1,10,10,10};
      //  List list=Arrays.asList(quiet(nums));
        Arrays.stream(quiet(nums)).forEach(System.out::print);
    }
}