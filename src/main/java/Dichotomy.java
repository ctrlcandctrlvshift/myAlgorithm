/**
 * @author zhang
 * @ClassName: Dichotomy
 * @Package PACKAGE_NAME
 * @Description: 二分法
 * @date 2021/11/2716:08
 */
public class Dichotomy {

    public static void main(String[] args) {
        int [] test=new int[5];

        for (int i = 0; i <test.length ; i++) {
            test[i]=i;
        }

        exist(test,3);
        nearestIndex(test,3);




    }


    //有序数组找到某个数是否存在 log2N复杂度
    public static int exist(int [] sortedArr,int num) {
        int mid=0;
        int L=0;
        int R=sortedArr.length-1;
        if(sortedArr.length == 0 && sortedArr ==null){
            return -1;
        }
            while (L <= R) {
                //mid=(L+R)/2
                //L 10亿 R 18亿 容易溢出
                //mid=L+(R-L)/2  N>>1 == N/2
                mid= L+((R-L)/2);
                if(sortedArr[mid]==num){
                    System.out.println("the number is" + num);
                    return mid;
                }else if(sortedArr[mid]>num){
                    R=mid-1;
                }else if(sortedArr[mid]<num){
                    L=mid+1;
                }
            }
            return -1;
    }

    //有序数组找到满足》=value的最左位置
    public static int nearestIndex(int []arr,int value){
        int L=0;
        int R=arr.length-1;
        int mid=0;
        int index=-1;
        if(arr.length==0 && arr==null)
        {
            return index;
        }
            while (L>=R) {
             mid=L+((R-L)>>1);
             if (arr[mid]>=value){
                 R=mid-1;
                 index=mid;
             }else {
                 L=mid+1;
             }
            }
            return index;

    }

    // 无序二分 找到局部最小
    public static int getLessIndex(int [] arr){
        if (arr.length==0&&arr==null){
            return -1;
        }
        if(arr.length==1&&arr[0]<arr[1]){
            return 0;
        }
        if(arr[arr.length-1]<arr[arr.length-2]){
            return arr.length-1;
        }
        int left=1;
        int right=arr.length-2;
        int mid=0;

        // [.....mid-1  mid   mid+1 ......]
        while (left>right){
            mid=left+((right-left)>>1);
            if (arr[mid]>arr[mid-1]){
                right=mid-1;
            }else if (arr[mid]>arr[mid+1]){
                left=mid+1;
            }else {
                return mid;
            }
        }
        //最后左边的数永远是最小的
        return left;
    }

    //异或无进位相加
    /*
    a和b 要在不同的内存中
    * int a int b
    * 交换数据
    * a=a异或b
    * b=a异或b
    * a=a异或b
    *
    *
    * */

    /*
    *一个数组中有一种数出现奇数次其他数都出现偶数次怎么打印这个数
    * （一堆数组进行异或 无论顺序如何 最后都是相同的）
    * int eor=0；
    * 对数组进行异或  最后出现的数就是这个数
    *
    *
    *
    * */

    public static void printOddTimesNum1(int []arr1){
        int eor=0;
        for (int i = 0; i <arr1.length ; i++) {
            eor = eor ^ arr1[i];
        }
        System.out.println(eor);
    }


    /*
    *
    * @提取int 值 最后一位1
    *
    * int n=n与（n取反加1）
    *
    *异或可以消除一个1
    * 一个数组中有两种数组出现了奇数次，其他数都出现偶数次 打印这两个数：
    *
    *
    * */

    public static void printOddTimesNum2(int []arr1){
        int eor=0;
        for (int i = 0; i <arr1.length ; i++) {
            eor=eor^arr1[i];
        }
        //eor =a^b
        //eor!=0
        //eor必然有一个位置上是1

        //01100100
        //00000100
        int rightOne=eor & (~eor +1);
        int eor1=0;
        for (int i = 0; i <arr1.length ; i++) {

            //假设arr[1]= 1111101100
            //rightOne=000000100
            //在最后一个1的位置上有1
            if((arr1[i]& rightOne )!= 0){
                eor1=eor1^arr1[i];
            }

        }
        System.out.println(eor1+" "+(eor^eor1));
    }


}
