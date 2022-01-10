import com.sun.org.apache.regexp.internal.RE;
import javafx.stage.Window;

import java.util.LinkedList;

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


    public static void main(String[] args) {
         int [] i={4,3,5,4,3,3,6,7};
         int w=3;
         int []res=getMaxWindow(i,w);
        int []res2=getMaxWindow2(i,w);
        for (int j = 0; j <res.length ; j++) {
            System.out.print(res[j]);
            System.out.println(res2[j]);
        }
    }



}
