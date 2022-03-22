import sun.misc.Queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author zhang
 * @ClassName: LRU
 * @Package PACKAGE_NAME
 * @Description: LRU
 * @date 2022/3/1614:54
 */
public class LRUandLFU {

//    设计一个遵循最近最少使用(LRU)缓存约束的数据结构。
//    实现LRUCache类:
//    LRUCache(int capacity)初始化大小为正的LRU缓存。
//    如果键存在，返回键的值，否则返回-1。
//    如果键存在，则更新键的值。 否则，请将该键值对添加到缓存中。 如果键的数量超过该操作的容量，则退出最近最少使用的键。
//    函数get和put必须以O(1)的平均时间复杂度运行。int get(int key)如果键存在，返回键值，否则返回-1。
//    put 如果键存在，则更新键的值。 否则，请将该键值对添加到缓存中。 如果键的数量超过该操作的容量，则退出最近最少使用的键。
//    get和put函数每次运行的平均时间复杂度必须为O(1)。
    public static class MyNode{
        int val;
        int key;
        MyNode next;
        MyNode pre;

        public MyNode(int val, int key, MyNode next, MyNode pre) {
            this.val = val;
            this.key = key;
            this.next = next;
            this.pre = pre;
        }
    }
    public static class MyLink{
        MyNode head;
        MyNode last;
        public MyLink(MyNode head) {
            this.head = head;
            this.last = head;
        }

        public void remove(MyNode node){
            MyNode pre=node.pre;
            MyNode next=node.next;
            if (next!=null){
                pre.next=next;
                next.pre=pre;
            }else {
                pre.next=null;
                last=pre;
            }

            node.next=null;
            node.pre=null;
        }

        public MyNode  removeHead(){
            MyNode next=head.next;
            if (next==null){
                return null;
            }
            MyNode nextnext=head.next.next;
            next.next=null;
            next.pre=null;
            if (nextnext!=null){
                nextnext.pre=head;
                head.next=nextnext;
            }else {
                last=head;
            }

            return next;
        }

        public MyNode addLast(MyNode node){
            last.next=node;
            node.pre=last;
            last=node;
            return node;
        }
    }
    public static class LRUCache {
        MyLink nums;
        HashMap<Integer,MyNode>values;
        int capacity;
        int cur;
        public LRUCache(int capacity) {
            this.capacity=capacity;
            this.cur=0;
            this.nums=new MyLink(new MyNode(0,0,null,null));
            this.values=new HashMap();
        }

        public int get(int key) {
            if (values.containsKey(key)){
                MyNode getNode=values.get(key);
                nums.remove(getNode);
                nums.addLast(getNode);
                return getNode.val;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (capacity==0){
                return;
            }
            if (values.containsKey(key)){
                MyNode updateNode=values.get(key);
                updateNode.val=value;
                values.put(key,updateNode);
                nums.remove(updateNode);
                nums.addLast(updateNode);
            }else {
                if (cur==capacity){
                    MyNode removeNode=nums.removeHead();
                    values.remove(removeNode.key);
                }else {
                    cur++;
                }
                MyNode insertNode=new MyNode(value,key,null,null);
                insertNode=nums.addLast(insertNode);
                values.put(key,insertNode);
            }
        }

    }
    public static class LRUCacheTest {

        ArrayList <Integer>nums;
        HashMap<Integer, Integer>values;
        int capacity;
        int cur;
        public LRUCacheTest(int capacity) {
            this.capacity=capacity;
            this.cur=0;
            this.nums=new ArrayList<>();
            this.values=new HashMap();
        }

        public int get(int key) {
            if (values.containsKey(key)){
                nums.remove((Integer)key);
                nums.add(key);
                return values.get(key);
            }
            return -1;
        }

        public void put(int key, int value) {
            if (values.containsKey(key)){
                values.put(key,value);
                nums.remove((Integer)key);
                nums.add(key);
            }else {
                if (cur==capacity){
                    values.remove(nums.get(0));
                    nums.remove(0);
                    values.put(key,value);
                    nums.add(key);
                }else {
                    values.put(key,value);
                    nums.add(key);
                    cur++;
                }
            }
        }

    }



//设计并实现一个最不常用(LFU)缓存的数据结构。
//实现LFUCache类:
//LFUCache(int capacity)用该数据结构的容量初始化该对象。
//int get(int key)如果该键存在于缓存中，则获取该键的值。否则,返回1。
//put 如果键存在，则更新键的值;如果键不存在，则插入键。当缓存达到其容量时，它应该在插入
// 并删除该键。
// 对于这个问题，当有一个平局(即，两个或更多的键具有相同的频率)，最近最少使用的键将失效。
//为了确定最不常用的键，为缓存中的每个键维护一个使用计数器。使用计数器最小的键就是使用频率最低的键。
//当一个键第一次插入到缓存中时，它的使用计数器被设置为1(由于put操作)。缓存中键的use计数器会递增，或者对其调用get或put操作。
//get和put函数每次运行的平均时间复杂度必须为O(1)。

    public static class MyNode2{
        int val;
        int counter;
        int key;
        MyNode2 pre;
        MyNode2 next;

        public MyNode2(int val, int counter, int key, MyNode2 pre, MyNode2 next) {
            this.val = val;
            this.counter = counter;
            this.key = key;
            this.pre = pre;
            this.next = next;
        }
    }
    public static class MyLink2{
        MyNode2 head;
        MyNode2 tail;

    public MyLink2() {
        this.head = new MyNode2(0,0,0,null,null);
        this.tail = head;
    }
    public void remove(MyNode2 node){
        MyNode2 prev=node.pre;
        MyNode2 next=node.next;
        prev.next=next;
        if (next==null){
            tail=prev;
            return;
        }
        next.pre=prev;
    }

    public void insert(MyNode2 node){
        MyNode2 temp=head.next;
        head.next=node;
        node.pre=head;
        node.next=temp;
        if (temp==null){
            tail=node;
        }else {
            temp.pre=node;
        }

    }
    public boolean isEmpty(){
        return head.next==null?true:false;
    }

    public MyNode2 erase(){
        if (isEmpty()){
            return null;
        }
        MyNode2 rem=tail;
        remove(rem);
        return rem;
    }
}
    static class LFUCache {
        HashMap <Integer, MyNode2> val;
        HashMap<Integer, MyLink2> fq;
        int capacity;
        int cur;
        int minf;
        public LFUCache(int capacity) {
            this.capacity=capacity;
            this.cur=0;
            this.val=new HashMap<>();
            this.fq=new HashMap<>();
            this.minf=1;
        }

        //没加 不考虑容量问题
        public void update(MyNode2 node){
            int counter=node.counter;
            MyLink2 curfqL=fq.get(counter);
            curfqL.remove(node);
            //更新minf
            if (minf==counter){
                if(curfqL.isEmpty()){
                    minf++;
                }
            }
            MyLink2 nextfqL;
            if (fq.containsKey(counter+1)){
                nextfqL=fq.get(counter+1);
            }else {
                nextfqL=new MyLink2();
                fq.put(counter+1,nextfqL);
            }
            node.counter++;
            nextfqL.insert(node);
            fq.put(counter+1,nextfqL);
        }
        public int get(int key) {
            if (val.containsKey(key)){
                MyNode2 cur=val.get(key);
                update(cur);
                return cur.val;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (val.containsKey(key)){
                MyNode2 cur=val.get(key);
                cur.val=value;
                update(cur);
                val.put(key,cur);
            }else {
                if (capacity==0){
                    return;
                }
                if (cur==capacity){
                    //删没怎么用的
                 MyLink2 lestfqL=fq.get(minf);
                 MyNode2 rem=lestfqL.erase();
                 val.remove(rem.key);
                }else {
                    cur++;
                }
                //加入新数据
                MyNode2 addNode=new MyNode2(value,1,key,null,null);
                MyLink2 firstLink;
                if(fq.containsKey(1)){
                    firstLink=fq.get(1);
                }else {
                    firstLink=new MyLink2();
                    fq.put(1,firstLink);
                }
                firstLink.insert(addNode);
                minf=1;
                val.put(key,addNode);
            }
        }
    }






    public static void main(String[] args) {
        LFUCache cache=new LFUCache(2);
        cache.put(1,1);
        cache.put(2,2);
        System.out.println(cache.get(1));
        cache.put(3,3);
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        cache.put(4,4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }


}
