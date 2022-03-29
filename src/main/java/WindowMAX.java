import com.sun.org.apache.regexp.internal.RE;
import javafx.stage.Window;

import java.util.*;
import java.util.function.IntFunction;

/**
 * @author zhang
 * @ClassName: WindowMAX
 * @Package PACKAGE_NAME
 * @Description: 滑动窗口
 * @date 2022/1/918:22
 */
public class WindowMAX {
     public static class WindowS{
         private int [] arr;
         private LinkedList<Integer> qmax;
         private int R;
         private int L;

         public WindowS(int[] arr) {
             this.arr = arr;
             this.qmax=new LinkedList<>();
             this.L=-1;

             //下一个
             this.R=0;
         }

         public void toR(){
             if (R==arr.length){
                 return;
             }
            while (!qmax.isEmpty()&&arr[qmax.peekLast()]<=arr[R]){
                qmax.pollLast();
            }
             qmax.addLast(R++);
         }

         public void toL(){
             if (++L>R){
                 return;
             }
             if (qmax.peekFirst()==L){
                 qmax.pollFirst();
             }
         }
         public int getMAX(){
             return arr[qmax.peekFirst()];
         }

     }
     //有一个整型数组arr和一个大小为w的窗口从数组的最左边滑到最右边，窗口每次 向右边滑
    //-个位置，
    //例如，数组为[4,3,5,4,3,3.6,7〕，窗口大小为3时：
    //[43 5]4 3 3 6 7
    //4[3 5 4]3 3 6 7
    //4 3[5 4 3]3 6 7
    //4 3 5 4 3 3 6 7
    //4 3 5 4 3 3 67
    //43543[3671
    //窗口中最大值为5 窗口中最大值为5 窗口中最大值为5 窗口中最大值为4 窗口中最大值为6
    //窗口中最大值为7
    //如果数组长度为n，窗口大小为w，则一共产生n-W+1个窗口的最大值。
    //请实现一个西数。输入：整型数组arr，窗口大小为"
    //输出：
    //一个长度为n-wt1的数组res, resLil 表示每一种窗口状态下的 以本题为例，结果应该
    //返回{5.5.5.4.6.7)。

    public static int[] getMaxWindow(int arr[],int w){
         if (arr==null||arr.length==0||w<1||arr.length<w){
             return null;
         }
         int [] maxWindow=new int[arr.length-w+1];
         WindowS windowS=new WindowS(arr);
        for (int i = 0; i <w ; i++) {
            windowS.toR();
        }
        int index=0;
        int R=2;
        maxWindow[index++]=windowS.getMAX();

        while (R<=arr.length-1){
            windowS.toR();
            R++;
            windowS.toL();
            if (index!=maxWindow.length){
                maxWindow[index++]=windowS.getMAX();
            }

        }
        return maxWindow;
    }

    public static int[] getMaxWindow2(int arr[],int w){
        if (arr==null||arr.length==0||w<1||arr.length<w){
            return null;
        }
        int [] maxWindow=new int[arr.length-w+1];
        LinkedList <Integer>qmax=new LinkedList<>();
        int index=0;

        for (int i = 0; i < arr.length; i++) {
            while (!qmax.isEmpty()&&arr[qmax.peekLast()]<=arr[i]){
                qmax.pollLast();
            }
            qmax.addLast(i);
            if (qmax.peekFirst()==i-w){
                qmax.pollFirst();
            }
            if (i>=w-1){
                maxWindow[index++]=arr[qmax.peekFirst()];
            }

        }
return maxWindow;
    }




    //DNA序列由一系列核苷酸组成，缩写为“A”、“C”、“G”和“T”。
    //例如，“ACGAATTCCG”是一个DNA序列。
    //在研究DNA时，识别DNA中的重复序列是很有用的。
    //给定一个表示DNA序列的字符串s，返回所有在DNA分子中出现不止一次的10个字母长的序列(子字符串)。 你可以按任意顺序返回答案。
    public static List<String> findRepeatedDnaSequences(String s) {
         List<String> lists=new ArrayList<>();
         HashMap<String,Integer> map=new HashMap<>();
         int left=0;
         int right=9;
         while (right<s.length()){
             String cur=s.substring(left,right+1);
             map.put(cur,map.getOrDefault(cur,0)+1);
             if (map.get(cur)==2){
                 lists.add(cur);
             }
             left++;
             right++;
         }
         return lists;
    }

    //给定一个正整数数组nums和一个正整数目标，返回连续子数组的最小长度[numsl, numsl+1，…， numsr-1, numsr]，
    // 其和大于或等于target。 如果没有这样的子数组，则返回0。
    public static int minSubArrayLen(int target, int[] nums) {
         int left=0;
         int min=Integer.MAX_VALUE;
         int sum=0;
        for (int i = 0; i <nums.length; i++) {
            sum+=nums[i];
            if (sum>=target){
                while (sum>=target&&left<=i){
                    int len=i-left+1;
                    if (len<min){
                        min=len;
                    }
                    sum-=nums[left++];
                }
            }
        }
        return min==Integer.MAX_VALUE?0:min;
    }

    //给定一个整数数组nums，有一个大小为k的滑动窗口从数组的最左边移动到最右边。你只能看到窗口中的k个数字。每次滑动窗口向右移动一个位置。
    //返回最大滑动窗口。
    public static int[] maxSlidingWindow(int[] nums, int k) {
         if (nums==null||nums.length<k){
             return null;
         }
         LinkedList<Integer> maxLink=new LinkedList<>();
         int [] res=new int[nums.length-k+1];
         for (int i = 0; i <k ; i++) {
             while (!maxLink.isEmpty()&&nums[maxLink.getLast()]<=nums[i]){
                 maxLink.pollLast();
             }
             maxLink.add(i);
         }
         int target=0;
         res[target++]=nums[maxLink.getFirst()];

        for (int j=0,i = k; i < nums.length;i++,j++) {
            if (j==maxLink.getFirst()){
                maxLink.pollFirst();
            }
            while (!maxLink.isEmpty()&&nums[maxLink.getLast()]<=nums[i]){
                maxLink.pollLast();
            }
            maxLink.add(i);
            res[target++]=nums[maxLink.getFirst()];
        }

        return res;
    }

    // 滑动窗口中值

    static PriorityQueue <Integer> min=new PriorityQueue<>();
    static PriorityQueue<Integer> max=new PriorityQueue<>(Comparator.reverseOrder());
    public static double[] medianSlidingWindow(int[] nums, int k) {
         if (nums==null||nums.length<k){
             return null;
         }
         double[] res=new double[nums.length-k+1];
         int target=0;
        for (int i = 0; i <k ; i++) {
            add(nums[i]);
        }
        res[target++]=getMax();
        remove(nums[0]);
        for (int start = 1,end=k; end <nums.length;) {
            add(nums[end++]);
            res[target++]=getMax();
            remove(nums[start++]);
        }
        return res;
    }
    public static void add(int num){
        if (max.size()==0){
            max.add(num);
            return;
        }
        if (num<=max.peek()){
            max.add(num);
        }else {
            min.add(num);
        }
        balance();
    }


    public static void balance(){
        if (min.size()-max.size()>=2){
            max.add(min.poll());
        }
        if (max.size()-min.size()>=2){
            min.add(max.poll());
        }
    }

    public static void  remove(int num){
        if (max.remove(num)){
            balance();
            return;
        }else{
            min.remove(num);
            balance();
        }
    }
    public static double getMax(){
        if (max.size()==min.size()){
            return(min.peek()+ 0.0 + max.peek())/2.0;
        }
        else if (max.size()>min.size()){
            return (double)max.peek();
        }
        else{
         return (double)min.peek();
        }
    }


    public static void main(String[] args) {
       int []nums={-2147483648,-2147483648,2147483647,-2147483648,-2147483648,
               -2147483648,2147483647,2147483647,2147483647,2147483647,-2147483648,2147483647,-2147483648};
       int k=2;

       double [] res=medianSlidingWindow(nums,k);
        for (int i = 0; i <res.length ; i++) {
            System.out.print(res[i]+" ");
        }
    }



}
