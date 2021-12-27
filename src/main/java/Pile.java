import java.beans.beancontext.BeanContext;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhang
 * @ClassName: Pile
 * @Package PACKAGE_NAME
 * @Description: 堆
 * @date 2021/12/522:02
 */
public class Pile {
    /*
    完全二叉树数组下标i 当i下表可以为零时
    * i 左2*i+1
    *   右2*i+2
    *   父i-1/2
    *
    *当i下表1开始时  位运算速度快
    i   左2*i  i<<1
    *   右2*i+1 i<<1|1
    *   父i/2   i>>1

    大根堆
    任何根节点都是最大的数
    小根堆
    任何根节点都是最小的数
    * */

    //大根堆
    public static class BigPile{
        private int heapSize=0;
        private int limit;
        private int [] arr;

        public BigPile(int limit) {
            this.arr=new int[limit-1];
        }
        public void push(int value){
            if (heapSize==limit){
                System.out.println("数组已满");
                return;
            }
            //暂时放到数组中去
            arr[heapSize]=value;
            heapInsert(arr,heapSize++);
        }

        private static void heapInsert(int arr[],int index){
            //比父节点大 交换位置
            //比父节点小 返回
            //index=0 返回
            while (arr[index]>arr[(index-1)>>1]){
                swap(arr,index,(index-1)>>1);
                index=(index-1)>>1;
            }
        }
        private static void swap(int arr[],int p1,int p2){
            int temp=arr[p1];
            arr[p1]=arr[p2];
            arr[p2]=temp;
        }

        //用户要你返回最大值，并且删掉最大值
        //剩下的数保持大根堆组织
        public int pop(){
            int ans=arr[0];
            //与最后一个数交换
            swap(arr,0,--heapSize);
            heapify(arr,0,heapSize);
            return ans;

        }

        //从index位置，往下看，不断下层
        //停：我的孩子不再比我大或者没有孩子了
        private void heapify(int[]arr,int index,int heapSize){
            //找到左节点
            int left=index*2+1;
            //有孩子
            while (left<heapSize){
                //左右两孩子谁大把下标给largest
                int largest=left+1<heapSize&&arr[left+1]>arr[left]?left+1:left;
                //最大孩子和父节点比较大小
                largest=arr[largest]>arr[index]?largest:index;
                //父节点大于孩子节点
                if (largest == index){
                    break;
                }
                //交换数据
                swap(arr,largest,index);
                index=largest;
                //下层到下一个子节点
                left=index*2+1;
            }
        }


        //额外空间复杂度（o(1)）
        public  void heapSort(int arr[]){
            if (arr==null&&arr.length<2){
                return;
            }
            //建最大堆
            //o(n*logn)
            /*for (int i = 0; i <arr.length ; i++) {
                //o(logn)
                heapInsert(arr,i);
            }
             */
            //优化 成o(n)前提要一起给一堆数组
            for (int i = arr.length-1; i <=0; i--) {
                heapify(arr,i,arr.length);
            }

            int heapSize2=arr.length;
            //o(n*logn)
            while (heapSize2>0){
                //构建大根堆
                heapify(arr,0,heapSize2);
                swap(arr,0,--heapSize2);
            }

//            另外的写法
//            for (int i = 0; i <arr.length ; i++) {
//                this.push(arr[i]);
//            }
//
//            for (int i = arr.length-1; i < 0; i--) {
//                arr[i]=this.pop();
//            }


            

        }

    }


    public static void main(String[] args) {
        //小根堆
        PriorityQueue<Integer> heap=new PriorityQueue<>();
        //大根堆
        PriorityQueue<Integer> bigHeap=new PriorityQueue<>(new myCom());
    }

    //负数，o1放在上面的情况
    public static class myCom implements Comparator<Integer>{

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2-o1;
        }
    }


    //已知一个几乎有序的数组，几乎是指，如果把数组排好顺序
    //元素移动的位置不超过k，并且k相对于数组的长度来说是较小的
    //选择合适的排序策略，对这个数组进行排序
    //o(n*logK)
    public void sortedArrDistanceLessK(int arr[],int k){
        //默认小根堆
        PriorityQueue<Integer>heap =new PriorityQueue<>();
        int index=0;
        //把0到k的数放到小根堆中
        for (; index <Math.min(arr.length-1,k); index++) {
            heap.add(arr[index]);
        }
        int i=0;
        //（index==k）index后面的数加入 弹出前面的最小值 完成排序
        for (; index<arr.length; i++,index++) {
            heap.add(arr[index]);
            arr[i]=heap.poll();
        }
        //当index后面没值了  小根堆里面还有值 把剩下的值放到数组中 完成最后的
        while (!heap.isEmpty()){
            arr[i++]=heap.poll();
        }

    }


    public static class MyHead<T>{
        private ArrayList<T> heap;
        // T 任何一个样本，记录该样本堆的位置
        private HashMap<T,Integer> indexMap;
        private int heapSize;
        private Comparator<? super T> comparator;

        public MyHead(Comparator<? super T> comparator) {
            this.comparator = comparator;
            this.indexMap=new HashMap<>();
            this.heap=new ArrayList<>();
            heapSize=0;
        }
        public boolean isEmpty(){
            return heapSize==0;
        }

        public int size(){
            return heapSize;
        }
        private void heapInsert(int index){
            //小根堆
            while (comparator.compare(heap.get(index),
                    heap.get((index-1)/2))<0){
                swap(index,(index-1)/2);
                index=(index-1)/2;
            }

        }
        private void swap(int p1, int p2){
            T o1=heap.get(p1);
            T o2=heap.get(p2);
            heap.set(p1,o2);
            heap.set(p2,o1);

            indexMap.put(o1,p2);
            indexMap.put(o2,p1);

        }
        public void add(T value){
            heap.add(value);
            //记录位置
            indexMap.put(value,heapSize);
            heapInsert(heapSize++);
        }
        public T poll(){
            T ans=heap.get(0);
            int end=heap.size()-1;
            swap(0,end);
            heap.remove(end);
            indexMap.remove(ans);
            heapify(0,--heapSize);
            return ans;
        }
        private void heapify(int index,int heapSize){
            int left=index*2+1;
            while (left<heapSize){
                int less=left+1<heapSize&&comparator.compare(heap.get(left),heap.get(left+1))<0?left:left+1;
                less=comparator.compare(heap.get(index),heap.get(less))<0?index:less;
                if (index==less){
                    break;
                }
                swap(index,less);
                index=less;
                left=less*2+1;
            }

        }
        public void resign(T value){
            int valueIndex=indexMap.get(value);
            //修改值 需要重构堆
            //必定只会触发一个函数  一个触发另一个失效
            heapInsert(valueIndex);
            heapify(valueIndex,heapSize);
        }











    }










}
