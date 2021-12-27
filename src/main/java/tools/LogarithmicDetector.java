package tools;

import java.util.Arrays;

/**
 * @author zhang
 * @ClassName: LogarithmicDetector
 * @Package PACKAGE_NAME
 * @Description:
 * @date 2021/11/2715:46
 */
public class LogarithmicDetector {

    //随机长度
    //使用Math.random

    public static int[] generateRandomArray(int maxSize,int maxValue){
        int[] arr= new int[(int)((maxSize+1)*Math.random())];
        for (int i = 0; i < arr.length; i++) {
            //[-,+]
            arr[i]= (int) ((maxValue + 1) * Math.random())-(int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    public static int[] copyArray(int [] arr){
        if(arr == null)
        {
            return null;
        }
        int [] temp=new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            temp[i]=arr[i];
        }
        return temp;

    }

    public static void comparator(int [] arr){
        Arrays.sort(arr);
    }
}
