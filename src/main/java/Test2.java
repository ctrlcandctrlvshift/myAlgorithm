import org.w3c.dom.Node;

import java.util.HashMap;
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
    public static void main(String[] args) {
        ReentrantLock reentrantLock=new ReentrantLock();
        Thread thread=Thread.currentThread();
        System.out.println(thread.isInterrupted());
        ThreadLocal threadLocal=new ThreadLocal();



    }


}
