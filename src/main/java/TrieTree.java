import org.w3c.dom.Node;

import java.util.HashMap;

/**
 * @author zhang
 * @ClassName: TrieTree
 * @Package PACKAGE_NAME
 * @Description: 前缀树
 * @date 2021/12/916:25
 */
public class TrieTree {

    public static class Node1{
        public int pass;
        public int end;
        public Node1[] nexts;
        public Node1() {
            pass=0;
            end=0;
            //对应26个字母
            //nexts[0]->a
            //nexts[1]->b
            //.....
            //nexts[i]==null 没有路
            //nexts[i]!=null 有路
            nexts=new Node1[26];
        }
    }
    public static class Trie1{
        private Node1 root;
        public Trie1() {
            root=new Node1();
        }
        public void insert(String word){
            if (word==null){
                return;
            }
            char []str =word.toCharArray();
            //创建指针node 指向root
            Node1 node=root;
            node.pass++;
            int path=0;
            for (int i = 0; i <str.length ; i++) {
                //减a，可得出该字母的路径
                path=str[i]-'a';
                //当指向路径中的节点为空
                if (node.nexts[path]==null){
                    //创建指向路径的节点
                    node.nexts[path]=new Node1();
                }
                //指针指向该路径的节点
                node=node.nexts[path];
                node.pass++;
            }
            //输入字符串结束，node指针指向最后的字符
            node.end++;
        }
        //word这个单词之前出现过多少次
        public int search(String word){
            if (word==null){
                return -1;
            }
            char []str=word.toCharArray();
            Node1 node=root;
            int path=0;
            for (int i = 0; i <str.length ; i++) {
                path=str[i]-'a';
                if (node.nexts[path]==null){
                    return 0;
                }
                node=node.nexts[path];
            }
            return node.end;
        }
        //所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre){
            if (pre==null){
                return -1;
            }
            Node1 node=root;
            int path=0;
            char[] str=pre.toCharArray();
            for (int i = 0; i <str.length ; i++) {
                path=str[i]-'a';
                if (node.nexts[path]==null){
                    return 0;
                }
                node=node.nexts[path];
            }
            return node.pass;
        }
        public void delete(String word){
            //确定word在前缀树中
            if (search(word)!=0){
                int path=0;
                Node1 node=root;
                node.pass--;
                char [] str= word.toCharArray();
                for (int i = 0; i <str.length ; i++) {
                    path=str[i]-'a';
                    //当一个node的pass减为0时，该node下所有的node全部删除
                    if (--node.nexts[path].pass==0){
                        node.nexts[path]=null;
                        return;
                    }
                    node=node.nexts[path];
                }
                node.end--;

            }


        }
    }


    public static class Node2{
        public int pass;
        public int end;
        public HashMap<Integer, Node2> nexts;

        public Node2() {
            pass=0;
            end=0;
            nexts=new HashMap<>();
        }
    }
    public  static class Trie2{
        private Node2 root;
        public Trie2() {
            this.root = new Node2();
        }
        public void insert(String word){
            if (word ==null){
                return;
            }
            char[] str=word.toCharArray();
            int path=0;
            Node2 node=root;
            node.pass++;
            for (int i = 0; i <str.length ; i++) {
                //把字符强转int
                path=(int)str[i];
                if (!node.nexts.containsKey(path)){
                    node.nexts.put(path,new Node2());
                }
                node=node.nexts.get(path);
                node.pass++;
            }
            node.end++;
        }
        public int prefixNumber(String pre){
            if (pre == null){
                return 0;
            }
            int path;
            char []str =pre.toCharArray();
            Node2 node=root;
            for (int i = 0; i <str.length ; i++) {
                path=(int)str[i];
                if (!node.nexts.containsKey(path)){
                    return 0;
                }
                node=node.nexts.get(path);
            }
            return node.pass;
        }
        public int search(String word){
            if (word==null){
                return 0;
            }
            char []str =word.toCharArray();
            int path;
            Node2 node = root;
            for (int i = 0; i <str.length ; i++) {
                path=(int)str[i];
                if (!node.nexts.containsKey(path)){
                    return 0;
                }
                node=node.nexts.get(path);
            }
            return node.end;
        }
        public void delete(String word){
            if (search(word)!=0){
                char [] str=word.toCharArray();
                Node2 node=root;
                int path;
                node.pass--;
                for (int i = 0; i <str.length ; i++) {
                    path=(int)str[i];
                    if (--node.nexts.get(path).pass==0){
                        node.nexts.remove(path);
                    }
                    node=node.nexts.get(path);
                }
                node.end--;

            }
        }
    }
    //可以使用前缀树解决有关前缀的问题












}
