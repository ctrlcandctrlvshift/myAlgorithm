package tools;

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

//
//    Example 1:
//    Input: s = "aab"
//    Output: [["a","a","b"],["aa","b"]]
    public static List<List<String>> partition(String s) {
        if (s==null||s.length()==0){
            return null;
        }
        return null;
    }
    public static void precess(){





    }

    public static boolean isPalindrome(String s,int low,int high){
        while (low<high){
            if (s.charAt(low++)!=s.charAt(high--)) {
                return false;
            }
        }
        return true;

    }











}
