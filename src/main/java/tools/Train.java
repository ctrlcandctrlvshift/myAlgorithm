package tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author zhang
 * @ClassName: Train
 * @Package tools
 * @Description: 练习
 * @date 2021/12/2612:21
 */
public class Train {
    public static class Node{
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }
    //先序序列化
    public static void pre(Node head,Queue<String> queue){
        if (head==null){
            queue.add(null);
            return;
        }
        queue.add(String.valueOf(head.value));
        pre(head.left,queue);
        pre(head.right,queue);
    }
   public static Queue<String> preSerial(Node head){
        if (head==null){
            return null;
        }
        Queue<String> queue=new LinkedList<>();
        pre(head,queue);
        return queue;
   }

   //反序列化
    public static Node preb(Queue<String> queue){
        String value= queue.poll();
        if (value==null){
            return null;
        }
        Node node=new Node(Integer.parseInt(value));
        node.left=preb(queue);
        node.right=preb(queue);
        return node;
    }
    public static Node bulidByPreQueue(Queue<String> queue){
        if (queue==null||queue.isEmpty()){
            return null;
        }
        return  preb(queue);
    }




    //Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.
    //A palindrome string is a string that reads the same backward as forward.

    ////给定一个字符串s，分区s，分区的每个子串都是回文。返回s的所有可能回文分区。
    ////回文字符串是向后读取和向前读取相同的字符串。
//
//    Example 1:
//    Input: s = "aab"
//    Output: [["a","a","b"],["aa","b"]]
    public static List<List<String>> partition(String s) {
        if (s==null||s.length()==0){
            return null;
        }
        List<List<String>> result=new ArrayList<>();
        process(s,0,result,new ArrayList<String>());
        return result;
    }
    public static void process(String s,int index,List<List<String>>result,List<String>cur){
        if (index>=s.length()){
            result.add(new ArrayList<>(cur));
            return;
        }
        for (int end = index; end <=s.length()-1 ; end++) {
            if (isPalindrome(s,index,end)) {
                String curString=s.substring(index, end+1);
                cur.add(curString);
                process(s, end+1, result, cur);
                cur.remove(cur.size()-1);
            }
        }

    }

    public static List<List<String>> partitionDp(String s){
        if (s==null||s.length()==0){
            return null;
        }
        List<List<String>> result=new ArrayList<>();
        int n=s.length();
        boolean dp[][]=new boolean[n][n];
        dfs(s,0,result,new ArrayList<>(),dp);
        return result;
    }
    public static void dfs(String s,int index,List<List<String>>result,List<String>cur,boolean dp[][]){
        if (index>=s.length()){
            result.add(new ArrayList<>(cur));
        }
        for (int end = index; end <=s.length()-1 ; end++) {


            if (s.charAt(index)==s.charAt(end)&&(end-index<=2||dp[index][end-1])) {
                String curString=s.substring(index, end+1);
                cur.add(curString);
                dp[index][end]=true;
                dfs(s, end+1, result, cur,dp);
                cur.remove(curString);
            }
        }



    }







    public static boolean isPalindrome(String s,int low,int high){
        while (low<high){
            if (s.charAt(low++)!=s.charAt(high--)) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        String s="cbbbcc";
        List<List<String>> result=partitionDp(s);
        for (List<String> list:result){
            for (String str: list){
                System.out.print(str+" ");
            }
            System.out.println();
        }

       // System.out.println(s.charAt(3));
    }









}
