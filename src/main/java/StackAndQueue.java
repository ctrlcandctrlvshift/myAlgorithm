import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author zhang
 * @ClassName: Stack
 * @Package PACKAGE_NAME
 * @Description: 栈与队列
 * @date 2021/11/3023:24
 */
public class StackAndQueue {
    //使用双向链表形式
    public static class Node<T>{
        public T value;
        public Node pre;
        public Node next;
        public Node(T value) {
            this.value = value;
        }
    }
    //双端链表
    public static class DoubleEndsQueue<T>{
        public Node<T> head;
        public Node<T> tail;

        public void addFormHead(T value){
            Node<T> node=new Node<>(value);
            if (head == null){
                head=node;
                tail=node;

            }//不需要动尾部
            else {
                node.next=head;
                head.pre=node;
                head=node;
            }
        }

        public void addFormTail(T value){
            Node<T> node=new Node<>(value);
            if (tail ==null){
                tail =node;
                head=node;
            }//不需要动头部
            else {
                node.pre=tail;
                tail.next=node;
                tail=node;

            }

        }



        public T popFromHead() {
            if(head==null){
                return null;
            }
            Node<T>cur=head;
            if(head==tail){
                head=null;
                tail=null;
                return cur.value;
            }else {
                head=head.next;
                cur.next=null;
                return cur.value;
            }

        }


        public T popFromTail(){
            Node<T>cur=tail;
            if (tail==null){
                tail=null;
                head=null;
                return cur.value;
            }else {
                tail=tail.pre;
                cur.pre=null;
                return cur.value;

            }
        }

        public boolean isEmpty(){
            if(head==null){
                return true;
            }else {
                return false;
            }


        }

    }

    public static class MyStack<T>{
        private DoubleEndsQueue<T> queue;

        public MyStack() {
            this.queue = new DoubleEndsQueue<T>();
        }
        public T pop(){
            return queue.popFromHead();
        }
        public void push(T value){
            queue.addFormHead(value);
        }
        public boolean isEmpty(){
            return queue.isEmpty();
        }
    }

    public static class MyQueue<T>{
        private DoubleEndsQueue<T> queue;

        public MyQueue() {
            this.queue = new DoubleEndsQueue<T>();
        }
        public void push(T value){
            queue.addFormHead(value);
        }
        public T pop(){
            return queue.popFromTail();
        }

        public boolean isEmpty(){
            return queue.isEmpty();
        }

    }


    public static class MyStack2<T>{

        private LinkedList <T>arr=new LinkedList<>();
        public void push(T value)
        {
        }

    }


//数组实现 固定大小的7
    public static class Mystack2{
        private int arr [];
        private int minarr[];
        private int index =0;
        public Mystack2() {
            this.arr = new int [6];
            this.minarr=new int[6];
        }
        public int pop(){
            if (index == 0){
                return -1;
            }else {
                int temp=index;
                index=index-1;
             return arr[temp-1];
            }
        }
        public void push(int value){
            if(index==7){
                System.out.println("数组满了");
            }else {
                arr[index]=value;
                if(index==0){
                    minarr[index]=value;
                }else {
                    if (value > arr[index - 1]) {minarr[index] = minarr[index-1];}
                    else {minarr[index] = value;}
                }
                index++;
            }
        }
        public boolean isEmpty(){
            return index==0?false:true;
        }

        //获取栈最小值
        public int getMin(){
            return minarr[index-1];
        }
    }
    public static class Mystack3{
        //最小值的另外方法
        /*
        * push时：
        * 当用户压入的数据时判断数据是否比最小栈栈顶的数小
        * 小的话压入最小栈 大的话不压入
        * pop时：
        * 弹出数据时判断弹出的数据是否与最小栈栈顶的数相同
        * 是的话同时弹出
        * 不是的话最小栈不动
        *
        * */
    }
    public  static class Myqueue{
        private int arr[];
        private int size=0;
        private int popi=0;
        private int pushi=0;
        private final int limit=7;

        public void push(int value){
            if (size ==limit){
                System.out.println("队列满了");
            }else {
                size++;
                //放进数
                arr[pushi]=value;
                pushi=nextInt(pushi);
            }
        }
        public int pop(){
            if (size ==0){
                return -1;
            }else {
                size--;
                int tempi=arr[popi];
                popi=nextInt(popi);
                return tempi;
            }
        }

        public int nextInt(int i){
            return i+1>=limit?0:i+1;
        }
        public boolean isEmpty(){
            return size==0?true:false;
        }
    }


    //两队列实现栈
    public static class QueueByStack{
        /*
        * 实现两个队列
        *
        * push时：
        * 把数压入队列a中
        *
        * pop时：
        * 把a队列里面数除了最新压入那个数全部压入队列b
        * 弹出a队列最新压入那个数
        *
        * 再push时：
        * 把数压入队列b
        *
        * 再pop时：
        * 把b队列里面数除了最新压入那个数全部压入队列a
        * 弹出b队列最新压入那个数
        *
        * 如此循环往复
        * 。。。。。。。。
        *
        *
        *
        *
        * */
    }
    //两栈实现队列2重点：要pop为空才能放数据进去
    public static class StackByQueu2<T>{
        private Stack<T> pushS;
        private Stack<T> popS;

        public StackByQueu2() {
            this.pushS = new Stack<>();
            this.popS = new Stack<>();
        }

        //要pop为空才能放数据进去
        private void pushTopop(){
            if (popS.isEmpty()){
             while (!pushS.isEmpty()) {
                 popS.push(pushS.pop());
             }
            }
        }

        public void add(T value){
            pushS.push(value);
            pushTopop();
        }

        public T poll(){
            if (popS.isEmpty()&&pushS.isEmpty()){
                return null;
            }else {
                pushTopop();
                return popS.pop();
            }
        }
        public T peek(){
            if (popS.isEmpty()&&pushS.isEmpty()){
                return null;
            }else {
                pushTopop();
                return popS.peek();
            }
        }

    }







}
