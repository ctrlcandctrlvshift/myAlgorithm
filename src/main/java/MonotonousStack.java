import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author zhang
 * @ClassName: MonotonousStack
 * @Package PACKAGE_NAME
 * @Description: 单调栈
 * @date 2022/1/1010:00
 */
public class MonotonousStack {
    //在数组中想找到一个数，左边和右边比这个数小、且离这个数最近的位置。
    //如果对每一个数都想求这样的信息，能不能整体代价达到o(N？需要使用到单调栈结构
    //单调栈结构的原理和实现

    public static class Node{
        public int left;
        public int right;

        public Node(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static class Ms{

        private Stack<LinkedList<Integer>> stack;
        private HashMap<Integer, Node> nodes;
        private int [] arr;

        public Ms(int[] arr) {
            this.stack = new Stack<>();
            this.nodes = new HashMap<>();
            this.arr = arr;

        }
         public HashMap<Integer, Node> getMinNode(){
            if (arr==null||arr.length==0){
                return null;
            }
             for (int i = 0; i <arr.length ; i++) {
                 while (!stack.isEmpty()){
                     if (arr[i]>arr[stack.peek().getFirst()]){
                         LinkedList<Integer> p2=new LinkedList<>();
                         p2.add(i);
                         stack.push(p2);
                         break;
                     }else if (arr[i]==arr[stack.peek().getFirst()]){
                         stack.peek().addLast(i);
                         break;
                     }else {
                         LinkedList<Integer> p3=stack.pop();
                         while (!p3.isEmpty()){
                             if (stack.isEmpty()){
                                 Node node=new Node(-1,i);
                                 nodes.put(p3.getFirst(),node);
                                 p3.removeFirst();
                             }else {
                                 Node node=new Node(stack.peek().getLast(),i);
                                 nodes.put(p3.getFirst(),node);
                                 p3.removeFirst();
                             }
                         }
                     }

                 }
                 if (stack.isEmpty()){
                     LinkedList<Integer> p1=new LinkedList<>();
                     p1.add(i);
                     stack.push(p1);
                     continue;
                 }
             }
             while (!stack.isEmpty()){
                 LinkedList<Integer> p4=stack.pop();
                 while (!p4.isEmpty()){
                     if (stack.isEmpty()){
                         Node node=new Node(-1,-1);
                         nodes.put(p4.getFirst(),node);
                         p4.removeFirst();
                     }else {
                         Node node=new Node(stack.peek().getLast(),-1);
                         nodes.put(p4.getFirst(),node);
                         p4.removeFirst();
                     }
                 }
             }

             return nodes;

        }

    }

    //定义：数组中累积和与最小值的乘积，假设叫做指标A。(正数)
    //给定一个数组，请返回子数组中，指标A最大的值。

    public static int targetA(int arr[]){
        if (arr==null||arr.length==0){
            return 0;
        }
        Ms ms=new Ms(arr);
        HashMap<Integer, Node> nodes=ms.getMinNode();
        int max=Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            Node node=nodes.get(i);
            int left=node.left+1;
            int right=node.right;
            if (right==-1){
                right=arr.length-1;
            }else {
                right--;
            }
            int cur=0;
            for (int j = left; j <= right; j++) {
                cur+=arr[j];
            }
            cur=cur*arr[i];
            max=Math.max(max,cur);
        }
        return max;
    }





    public static void main(String[] args) {
//        int [] arr={5,4,3,4,5,3,5,6};
//        Ms ms=new Ms(arr);
//        HashMap<Integer, Node> nodes=ms.getMinNode();
//        for (int i = 0; i <arr.length ; i++) {
//            System.out.println(i+": 左"+nodes.get(i).left+" 右"+nodes.get(i).right);
//        }
        int []arr={1,2,3};
        System.out.println(targetA(arr));

    }




}
