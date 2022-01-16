import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhang
 * @ClassName: Morris
 * @Package PACKAGE_NAME
 * @Description: Morris遍历
 * 一种遍历二叉树的方式，并且时间复杂度O(N)，额外空间复杂度0（1)
 * 通过利用原树中大量空闲指针的方式，达到节省空间的目的
 * @date 2022/1/110:14
 */
public class Morris {
    //Morris遍历细节
    //假设来到当前节点cUr，开始时cur来到头节点位置
    //1)如果cur没有左孩子，cur向右移动(Cur = cur.right)
    //2）如果cur有左孩子，找到左子树上最右的节点mostRight:
    //a.如果mostRight的右指针指向空，让其指向cur,
    //然后cur向左移动(cur =cur.left)
    //b.如果mostRight的右指针指向cur，让其指向null,
    //然后cur向右移动(cur =cur.right)
    //3） cur为空时遍历停止

    public static class Node{
        public int val;
        public Node left;
        public Node right;

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
        public Node(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
    public static void morris_(Node head){
        if (head==null){
            return;
        }
        Node cur=head;
        System.out.println(cur.val);
        Node mostRight=null;

        while (cur!=null){
            //左树为空
            if (cur.left==null){
                cur=cur.right;
                if (cur!=null) {
                    System.out.println(cur.val);
                }
            }else{
                //左树不为空
                mostRight=cur.left;
                while (mostRight.right!=null&&mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if (mostRight.right==null){
                    mostRight.right=cur;
                    cur=cur.left;
                    System.out.println(cur.val);
                }
                if (mostRight.right==cur){
                    mostRight.right=null;
                    cur=cur.right;
                    System.out.println(cur.val);
                }
            }


        }

    }
    //先序 出现与一次直接打印 出现第二次的出现第一次打印 第二次不管
    public static void morrisPre_(Node head){
    if (head==null){
        return;
    }
    Node cur=head;
    System.out.println(cur.val);
    Node mostRight=null;

    while (cur!=null){
        if (cur.left==null){
            cur=cur.right;
        }else{
            mostRight=cur.left;
            while (mostRight.right!=null&&mostRight.right!=cur){
                mostRight=mostRight.right;
            }
            if (mostRight.right==null){
                mostRight.right=cur;
                cur=cur.left;
                System.out.println(cur.val);
            }
            if (mostRight.right==cur){
                mostRight.right=null;
                cur=cur.right;
                System.out.println(cur.val);
            }
        }


    }


}
    //中序 出现一次打印 出现两个的第一次不打印 第二次打印
    public static void morrisIn_(Node head){
    if (head==null){
        return;
    }
    Node cur=head;
    Node mostRight=null;

    while (cur!=null){
        if (cur.left==null){
            System.out.println(cur.val);
            cur=cur.right;
            if (cur!=null) {
                System.out.println(cur.val);
            }
        }else{
            mostRight=cur.left;
            while (mostRight.right!=null&&mostRight.right!=cur){
                mostRight=mostRight.right;
            }
            if (mostRight.right==null){
                mostRight.right=cur;
                cur=cur.left;
            }
            if (mostRight.right==cur){
                mostRight.right=null;
                cur=cur.right;
            }
        }


    }

}

//逆序打印左树右边界
//再打印整棵树的右边界
    public static void morrisPos_(Node head){
        if (head==null){
            return;
        }
        Node cur=head;
        Node mostRight=null;

        while (cur!=null){
            if (cur.left==null){
                cur=cur.right;
            }else{
                mostRight=cur.left;
                while (mostRight.right!=null&&mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if (mostRight.right==null){
                    mostRight.right=cur;
                    cur=cur.left;
                }
                if (mostRight.right==cur){
                    mostRight.right=null;
                    //第二次出现打印逆序左树右边界
                    printRightEdge(cur.left);
                    cur=cur.right;

                }
            }


        }
        //打印整棵树的右边界
        printRightEdge(head);

    }

    //逆序打印该树的右边界
    public static void printRightEdge(Node n){
        Node tail=reverseNode(n);
        Node cur=tail;
        while (cur!=null){
            System.out.println(cur.val);
            cur=cur.right;
        }
        reverseNode(tail);

    }
    public static Node reverseNode(Node n){
        Node pre=null;
        Node cur=n;
        Node next=null;
        while (cur!=null){
            next=cur.right;
            cur.right=pre;
            pre=cur;
            cur=next;
        }
        return pre;
    }

    //isBTS使用中序遍历  因为如果是BST 那么树是按顺序打印出来的
    public static boolean morrisBTS_(Node head){
        if (head==null){
            return true;
        }
        Node cur=head;
        Node mostRight=null;
        int valuePre=Integer.MIN_VALUE;

        while (cur!=null){
            if (cur.left==null){
                if (cur.val<=valuePre){
                    return false;
                }
                valuePre=cur.val;
                cur=cur.right;
                if (cur!=null) {
                    if (cur.val<=valuePre){
                        return false;
                    }
                    valuePre=cur.val;
                }
            }else{
                mostRight=cur.left;
                while (mostRight.right!=null&&mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if (mostRight.right==null){
                    mostRight.right=cur;
                    cur=cur.left;
                }
                if (mostRight.right==cur){
                    mostRight.right=null;
                    cur=cur.right;
                }
            }


        }
return true;
    }


    //二叉树递归判断BST
    public static class InfoBST{
        boolean isBST;
        int max;
        int min;

        public InfoBST(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }
    public static boolean isBST(Node head){
        if (head==null){
            return true;
        }
        InfoBST bst=processBST(head);
        return bst.isBST;
    }
    public static InfoBST processBST(Node head){
        if (head==null){
            return null;
        }

        InfoBST left=processBST(head.left);
        InfoBST right=processBST(head.right);
        if (left==null||right==null){
            return new InfoBST(true,head.val,head.val);
        }


        if (left.isBST&&right.isBST){
            int max=left.max;
            int min=right.min;
            if (max>head.val||min<head.val){
                return new InfoBST(false,0,0);
            }else {
                max=Math.max(Math.max(max,min),head.val);
                min=Math.min(Math.min(max,min),head.val);
                return new InfoBST(true,max,min);
            }
        }else {
            return new InfoBST(false,0,0);
        }

    }


    //宽度建立
    public static Node bulid(Queue<Integer> list){
        if (list==null||list.isEmpty()){
            return null;
        }
        Node head=generateNode(list.poll());
        Queue<Node> queue=new LinkedList<>();
        queue.add(head);

        Node node=null;
        while(!queue.isEmpty()){
            node =queue.poll();
            node.left=generateNode(list.poll());
            node.right=generateNode(list.poll());
            if (node.left!=null){
                queue.add(node.left);
            }
            if (node.right!=null){
                queue.add(node.right);
            }

        }
        return head;

    }
    public static Node generateNode(Integer i){
        if (i==null){
            return null;
        }
        return new Node(i);
    }




    public static void main(String[] args) {
//        Queue<Integer> queue=new LinkedList<>();
//        for (int i = 1; i <=7 ; i++) {
//            queue.add(i);
//        }
//        Node head=bulid(queue);
//        //morrisPos_(head);
        Node node1=new Node(2);
        Node node2=new Node(1);
        Node node3=new Node(3);
        node1.left=node2;
        node1.right=node3;
        morrisIn_(node1);


    }








}
