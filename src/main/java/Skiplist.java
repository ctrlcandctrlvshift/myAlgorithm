import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author zhang
 * @ClassName: Skiplist
 * @Package PACKAGE_NAME
 * @Description: tiaobiao
 * @date 2022/4/3019:20
 */
public class Skiplist {
    //leecodr1206
    //设计一个不使用任何内置库的Skiplist。
    //skiplist是一种需要O(log(n))时间来添加、删除和搜索的数据结构。
    // 与功能和性能相同的treap和红黑树相比，Skiplist的代码长度相对较短，其思想只是简单的链表。


    //你可以看到Skiplist中有许多层。每一层都是一个排序的链表。在顶层的帮助下，添加、删除和搜索可以比O(n)更快。可以证明，每个操作的平均时间复杂度为O(log(n))，空间复杂度为O(n)。
    //了解更多关于Skiplist的信息:https://en.wikipedia.org/wiki/Skip_list
    //实现Skiplist类:
    //Skiplist()初始化Skiplist对象。
    //bool search(int target)如果Skiplist中存在整数目标，则返回true，否则返回false。
    //void add(int num)向SkipList中插入值num。
    //bool erase(int num)从Skiplist中移除num值并返回true。如果num在Skiplist中不存在，则不执行任何操作并返回false。如果存在多个num值，删除其中任何一个都可以。
    //注意，Skiplist中可能存在副本，您的代码需要处理这种情况。


  public static  class  Skiplist2{
      private static  final int maxlevel=32;
      private static final  double  probability=0.5;
      private HashSet set;
      private int curLevel;
      class Node{
          int value;
          List<Node> forward;


          public Node(int value) {
              this.value = value;
              this.forward= new ArrayList<>();

              //设置层数 ???
              for (int i = 0; i <maxlevel ; i++) {
                  forward.add(null);
              }
          }

          public Node nextNode(int level){
              return this.forward.get(level);
          }
      }

      public Node head;

      public Skiplist2() {
          this.head=new Node(-1);
          this.set=new HashSet();
          this.curLevel=0;
      }

      public boolean search(int target) {
          if (!contains(target)){
              return false;
          }
          Node node=head;
          int level=curLevel;
          while (level>=0){
              Node pre=node;
              Node next=node.nextNode(level);
              while (next.value<target){
                  pre=next;
                  next=next.nextNode(level);
              }
              if (next.value==target){
                  return true;
              }
              if (next.value>target){
                  node=pre;
              }
              level--;
          }

          return false;
      }
      public void add(int num) {
          if (!contains(num)){
              int level=randomLevel();
              set.add(num);
              if (level>curLevel){
                  curLevel=level;
              }

              Node newN=new Node(num);
              Node node=head;
              //一层层添加
              while (level>=0){
                  Node pre=node;
                  Node next=node.nextNode(level);
                  if (next==null){
                      pre.forward.set(level,newN);
                  }
                  while (next!=null){
                          if (next.value<num){
                              pre=next;
                              next=next.nextNode(level);
                          }else {
                              Node tmp=pre.forward.get(level);
                              pre.forward.set(level,newN);
                              newN.forward.set(level,tmp);
                              break;
                          }
                      }
                  level--;
              }
          }
      }

      //随机层数
      public int randomLevel(){
          int level=0;
          while (Math.random() <= probability && level<maxlevel-1){
              level++;
          }
          return level;
      }

      public boolean erase(int num) {
          if (!contains(num)){
              return false;
          }
          int level=curLevel;
          Node node=head;
          while (level>=0){
              Node pre=node;
              Node next=node.nextNode(level);
              while (next!=null){
               if (next.value==num){
                   Node tmp=next.nextNode(level);
                   pre.forward.set(level,tmp);
                   break;
               }
               pre=next;
               next=next.nextNode(level);
              }
              level--;
          }
          return true;
      }

      public boolean contains(int num){
          return set.contains(num);
      }

  }


}
