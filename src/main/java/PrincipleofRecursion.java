import java.util.List;

/**
 * @author zhang
 * @ClassName: PrincipleofRecursion
 * @Package PACKAGE_NAME
 * @Description: 简单递归
 * @date 2022/1/811:56
 */
public class PrincipleofRecursion {
    //相反顺序打印字符串
    public static char [] reverseString(char[] s) {
        if (s.length==0){
            return null;
        }
        process(s,0,s.length-1);
        return s;
    }
    public static void process(char []s,int start,int end){
        if (start>end){
            return;
        }
        swap(s,start,end);
        process(s,start+1,end-1);

    }
    public static void swap(char[]s,int p1,int p2){
        char tmp=s[p1];
        s[p1]=s[p2];
        s[p2]=tmp;
    }


    //成对交换节点
    //Given a linked list,
    // swap every two adjacent nodes and return its head.
    // You must solve the problem without modifying the values
    // in the list's nodes (i.e., only nodes themselves may be changed.)

    public static class ListNode {
      int val;
      ListNode next;
      ListNode(int val) { this.val = val; }
  }
    public static ListNode swapPairs(ListNode head) {
        if (head==null||head.next==null){
            return head;
        }
        head=swap(head);
        ListNode next=head.next;
        process(next);
        return head;
    }
    public static void process(ListNode next){
        if (next==null||next.next==null||next.next.next==null){
            return;
        }
        ListNode cur=next;
        next=cur.next;
        next=swap(next);
        cur.next=next;
        process(next.next);
    }

    public static ListNode swap(ListNode node){
        ListNode p1=node.next;
        node.next=p1.next;
        p1.next=node;
        return p1;
    }

    public static void main(String[] args) {
//        char[] str1={'k','2','H'};
//        System.out.println(reverseString(str1));

        ListNode node1=new ListNode(1);
        ListNode node2=new ListNode(2);
        ListNode node3=new ListNode(3);
        ListNode node4=new ListNode(4);
        ListNode node5=new ListNode(5);
        node1.next=node2;
        node2.next=node3;
        node3.next=node4;
        node4.next=node5;

       node1=swapPairs(node1);
        while (node1!=null){
            System.out.println(node1.val);
            node1=node1.next;
        }
    }



}
