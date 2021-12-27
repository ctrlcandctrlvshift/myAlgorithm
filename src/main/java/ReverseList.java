import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 * @author zhang
 * @ClassName: ReverseList
 * @Package PACKAGE_NAME
 * @Description: 链表
 * @date 2021/11/3016:27
 */
public class ReverseList {

    //双向链表
    public static class Node{
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }
    }
    public static class DoubleNode{
        String data;
        DoubleNode pre;
        DoubleNode next;

        public DoubleNode(String data) {
            this.data = data;
        }
    }
    //反转链表
    public static Node reverseLinkedList(Node head){
        Node pre=null;
        Node next=null;

        while (head!=null){
            //next跳到下一个位置
            next=head.next;
            //指向上一个数
            head.next=pre;
            //pre跳到当前位置
            pre =head;
            //head跳到下一个位置
            head=next;
        }
        return pre;
    }
    public static DoubleNode reverseDoubleLinkedList(DoubleNode head){
        DoubleNode pre=null;
        DoubleNode next=null;


        while(head!=null){
            next=head.next;
            head.next=pre;
            head.pre=next;
            pre=head;
            head=next;
        }
return pre;
    }
    //删除特定的值
    public static  Node removeLinkedValue(Node head,int num){
        while (head !=null){
            //找到一个不等于num的点做头部
            if(head.data != num){
                break;
            }
            head=head.next;
        }
        //head 来到一个不需要删除的位置 pre和cur相同位置
        Node pre=head;
        //代替head操作
        Node cur=head;

        while (cur!=null){

            //删除一个节点 需要两个指针 一个在删除的点的前面a 一个在删除的点b  删除该点做法：a.next=b.next(重置前一个点的位置 指向后一个点)
            if(cur.data == num){
                pre.next=cur.next;
            }
            //pre往前跳
            else {
                pre=cur;
            }
            //跳到下一个 此时cur在pre的后面
            cur=cur.next;
        }
        return head;
    }

    //使用容器（哈希表，数组）
    //快慢指针
    //快指针走两步
    //慢指针走一步
    //输入链表的头节点，奇数长度返回中点，偶数长度返回上 中点
    public Node midOrUpMideNode(Node head){
        if (head==null||head.next==null||head.next.next==null){
            return head;
        }
        //链表有3个以上的点
        //快指针走两步
        //慢指针走一步
        Node slow =head.next;
        Node fast=head.next.next;
       while (fast.next.next!=null&&fast.next!=null){
           slow=slow.next;
           fast=fast.next.next;
       }
       return slow;
    }
    //输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    public Node midOrDownMidNode(Node head){
        if (head==null||head.next==null){
            return head;
        }
        //不同点  快慢指针的起始位置不一样
        Node slow=head.next;
        Node fast=head.next;
        while (fast.next!=null&&fast.next.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }
        return fast;


    }
    //。。。，奇数长度返回中点前一个，偶数长度返回上中点前一个
    //fast=head.next.next  slow=head
    //其余代码基本一致
    //。。。。，奇数长度返回中点前一个，偶数长度返回下中点前一个
    //fast=head.next slow=head
    //其余代码基本一致
    //总：以上的代码可以用（arr.size-n）/2直接拿出来



    //回文链表
    //使用栈  先把链表里面的数进栈  再出栈与链表一一对应 只要有不对的就返回false
    public static boolean isPalindrome1(Node head){
        if (head ==null||head.next==null){
            return false;
        }
        Stack<Node>stack=new Stack<Node>();
        Node cur=head;
        while (cur!=null){
            stack.push(cur);
            cur=cur.next;
        }
        while (head!=null){
            if (stack.pop().data!=head.data){
                return false;
            }
            head=head.next;
        }
        return true;
    }
    //使用中间node mid 栈
    public boolean isPalindrome2(Node head){
        if (head ==null||head.next==null){
            return false;
        }
        Stack <Node> stack=new Stack<>();
        Node mid=midOrUpMideNode(head);
        Node cur =mid.next;

        while (cur!=null){
            stack.push(cur);
        }
        while (!stack.isEmpty()){
            if (head.data!=stack.pop().data){
                return false;
            }
            head=head.next;
        }
        return true;


    }
    //改链表 使用快慢指针
    public boolean isPalindrome3(Node head){
        if (head ==null||head.next==null){
            return false;
        }
        //n1是中点
        Node n1 =midOrUpMideNode(head);
        Node cur =n1.next;
        n1.next=null;
        Node n2=null;
        //反转
        while (cur!=null){
            n2=cur.next;
            cur.next=n1;
            n1=cur;
            cur=n2;
        }
        //一个从头部开始
        //一个从尾部开始

        //需要逆序回来 保存n1 位置
        n2=n1;
        cur=head;
        //开始对比
        while (n1 !=null&& cur !=null){
            if (n1.data!=cur.data){
                return false;
            }
            n1=n1.next;
            cur=cur.next;
        }
        //复原链表
        cur=n2.next;
        n2.next=null;
        while (cur!=null){
            n1=cur.next;
            cur.next=n2;
            n2=cur;
            cur=n1;
        }
        return true;

    }


    //将单向链表按某值划分为左边小 中间相等 右边大
    //链表放入数组  数组做partition
    public Node listPartition1(Node head,int pivot){
        if (head==null){
            return null;
        }
        int i=0;
        Node cur=head;
        while (cur!=null){
            i++;
            cur=cur.next;
        }
        Node[] nodeArray=new Node[i];
        i=0;
        cur=head;
        for (i = 0; i <nodeArray.length ; i++) {
            nodeArray[i]=cur;
            cur=cur.next;
        }
        arrPartition(nodeArray,pivot);
        for (i = 1; i <nodeArray.length ;i++) {
            nodeArray[i-1].next=nodeArray[i];
        }
        nodeArray[i-1].next=null;
        return nodeArray[0];
    }
    public void arrPartition(Node arr[],int pivot){
        int less=-1;
        int r=arr.length;
        int p1=0;
        while (p1<r){
            if (arr[p1].data<pivot){
                swap(arr,p1++,++less);
            }else if (arr[p1].data==pivot){
                p1++;
            }else{
                swap(arr,p1,--r);
            }
        }
    }
    public void swap(Node arr[],int p1,int p2){
        Node tem=arr[p1];
        arr[p1]=arr[p2];
        arr[p2]=tem;
    }
    //改链表结构
    public Node listPartition2(Node head,int pivot) {
        //小于区等于区大于区的
        Node sh = null, st = null, eh = null, et = null, mh = null, mt = null;
        //设置下一个节点指针
        Node next = null;
        while (head != null) {
            //记住下一个节点
            next = head.next;
            //断掉下一个点
            head.next = null;
            if (head.data < pivot) {
                if (sh == null) {
                    sh = head;
                    st = head;
                } else {
                    st.next=head;
                    st=head;
                }
            } else if (head.data == pivot) {
                if (eh == null) {
                    eh = head;
                    et = head;
                } else {
                    et.next = head;
                    et = head;
                }
            } else {
                if (mh == null) {
                    mh = head;
                    mt = head;
                } else {
                    mt.next = head;
                    mt = head;
                }
                //走到下一个节点
                head = next;
            }


        }


        if (st!=null){
            st.next=eh;
            et=et!=null?et:st;
        }
        //上面if 不管有没有运行，et
        //使用et连下一步
        if (et!=null){
            et.next=mh;
        }
        return sh!=null?sh:(eh!=null?eh:mh);
    }


    //rand指针是单链表结构新增的指针，rand可能指向链表中的任意一个节点，也可能指向null
    //给定向一个由Node1节点类型组成的无环单链表的头节点head 请实现一个函数完成这个链表的复制 并返回复制的
    //新链表的头节点 时间复制度0（n）额外空间复杂度o（1）
    public static class Node1{
        Node1 rand;
        Node1 next;
        int value;

        public Node1(int value) {
            this.value = value;
        }
    }
    //使用hashmap<node,node>旧节点key 新节点value
    public static Node1 copyListWithRand1(Node1 head){
        if (head ==null){
            return null;
        }
        HashMap<Node1,Node1> nodeMap = new HashMap();
        Node1 cur=head;
        while (cur!=null){
            nodeMap.put(cur, new Node1(cur.value));
            cur=cur.next;
        }
        cur=head;
        //内部串起来
        while (cur!=null){
            //老节点cur
            //新节点nodeMap.get(cur)
            //新节点连接新节点
            nodeMap.get(cur).next=nodeMap.get(cur.next);
            nodeMap.get(cur).rand=nodeMap.get(cur.rand);
            cur=cur.next;
        }
        return nodeMap.get(head);

    }
    //把链表结构修改为1->1`->2->2`形式 便于复制
    public static Node1 copyListWithRand2(Node1 head){
        if (head==null){
            return null;
        }
        Node1 cur=head;
        Node1 next=null;
        //1->2
        //1->1`->2->2`
        //next复制
        while (cur!=null){
            next=cur.next;
            //插入新节点
            Node1 node=new Node1(cur.value);
            cur.next= node;
            node.next=next;
            cur=cur.next;
        }
        cur=head;
        Node1 curCopy=null;
        while (cur!=null){
            //新节点之后的节点
            //cur是旧节点
            //cur.next是新节点
            next=cur.next.next;
            //新节点
            curCopy=cur.next;
            curCopy.rand=cur.rand!=null?cur.rand.next:null;
            //cur.next.rand=cur.rand!=null?cur.rand.next:null;应该是一样的
            cur=next;
        }
        //分离
        cur=head;
        //新节点的头部
        Node1 newhead=head.next;
        while (cur!=null){
            //记住下一个节点
            next=cur.next.next;
            curCopy=cur.next;
            //旧节点指向下一个旧节点
            cur.next=next;
            //新节点指向下一个新节点
            curCopy.next=next!=null?next.next:null;
            //跳到下一个旧节点
            cur=cur.next;
        }
        return newhead;
    }



    //给定两个可能有环也可能无环的单链表，头节点head1和head2.请实现一个函数
    //如果两个链表相交，请返回相交的第一个节点 如果不相交返回null
    //时间复制度o（N）额外空间复杂度o（1）

    //找到链表环相交的节点
    //1.使用hashset记录链表  重复那个节点即环相交节点
    //2.使用快慢指针  快指针起点在第三个节点  慢指针起始在第二个节点
    //当快慢指针重合时  快指针回到头节点 慢指针不动；快指针和慢指针以相同的一步速度往下走

    //当快慢指针再次重合时，重合的节点即相交节点
    public static Node getLoopNode(Node head){
        if (head==null||head.next==null||head.next.next==null){
            return null;
        }
        Node slow,fast;
        slow=head.next;
        fast=head.next.next;
        while (slow!=fast){
            //不是环链
            if (fast.next==null||fast.next.next==null){
                return null;
            }
            slow=slow.next;
            fast=fast.next.next;
        }
        fast=head;
        while (fast!=slow){
            fast=fast.next;
            slow=slow.next;
        }
        return fast;
    }
    //两条无环链的相交 肯定会形成Y形状
    public  static Node noLoop(Node head1,Node head2){
        if (head1==null||head2==null){
            return null;
        }
        Node cur1=head1;
        Node cur2=head2;
        //差值
        int n=0;
        //计算长度 到最后一个非空节点
        while (cur1.next!=null){
            n++;
            cur1=cur1.next;
        }
        while (cur2.next!=null){
            n--;
            cur2=cur2.next;
        }
        //两条不相交的链 返回null
        if (cur1!=cur2){
            return null;
        }
        //谁长谁的头变成cur1
        cur1=n>0?head1:head2;
        //谁短谁变成cur2
        cur2=cur1==head1?head2:head1;
        n=Math.abs(n);
        //长链头走到与短头链相似位置
        while (n!=0){
            n--;
            cur1=cur1.next;
        }
        //当还没相等时不断往前行直到相等 返回相等的那个数
        while (cur1!=cur2){
            cur1=cur1.next;
            cur2=cur2.next;
        }
        return cur1;

    }
    //两条有环链相交有三种情况
    //1.有环不相交
    //2.相交环点
    //3.不相交环点
    public static Node bothLoop(Node head1,Node head2,Node loop1,Node loop2){
        Node cur1=null;
        Node cur2=null;
        //和两条直线链处理是一样的
        if (loop1==loop2){
            cur1=head1;
            cur2=head2;
            int n=0;
            while (cur1!=loop1){
                n++;
                cur1=cur1.next;
            }
            while (cur2!=loop2){
                n--;
                cur2=cur2.next;
            }
            cur1=n>0?head1:head2;
            cur2=cur1==head1?head2:head1;

            while (n!=0){
                n--;
                cur1=cur1.next;
            }
            while (cur1!=cur2){
                cur1=cur1.next;
                cur2=cur2.next;
            }
            return cur1;
        }
        else {
            cur1=loop1.next;
            while (cur1!=loop1){
             if (cur1==loop2){
                 return loop1;
             }
             cur1=cur1.next;
            }
            return null;
        }







    }
    //主方法
    public static Node getIntersectNode(Node head1,Node head2){
        if (head1==null||head2==null){
            return null;
        }
        Node loop1=getLoopNode(head1);
        Node loop2=getLoopNode(head2);
        if (loop1==null&&loop2==null){
            noLoop(head1,head2);
        }
        if (loop1!=null&&loop2!=null){
            bothLoop(head1,head2,loop1,loop2);
        }
        return null;
    }

    //不给头节点 只给删的节点 如何删掉节点
    //抖机灵做方法 1->2->3  想删掉2节点：把2 value值变3 的value值  去掉尾部3节点
    //问题：1.内存不对 2.没办法删除最后节点





























    public static void main(String[] args) {
        Node1 head=new Node1(1);
        Node1 node1=new Node1(2);
        Node1 node2=new Node1(3);
        head.next=node1;
        node1.next=node2;
        head.rand=node2;
        node1.rand=null;
        node2.rand=node1;
        copyListWithRand1(head);
        HashSet set=new HashSet<String>();
        set.add(head);
        set.add(head);
        set.add(head);
    }









    }


