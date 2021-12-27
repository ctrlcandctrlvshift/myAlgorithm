/**
 * @author zhang
 * @ClassName: BucketSort
 * @Package PACKAGE_NAME
 * @Description: 桶排序
 * @date 2021/12/1016:51
 */
public class BucketSort {
    //计数排序
    //当需要排序的数在一定的范围内有用
    //该题是0到200的数
    public static void countSort(int arr[]){
        if (arr==null||arr.length<2){
            return;
        }
        int bucket[]=new int [201];
        for (int i = 0; i <arr.length ; i++) {
            bucket[arr[i]]++;
        }
        int var=0;
        for (int i = 0; i <bucket.length ; i++) {
            while (bucket[i]-->0){
                arr[var++]=i;
            }
        }
    }

    //基数排序（非负）
    public  void radixSort(int arr[]){
        if (arr==null||arr.length<2){
            return;
        }
        radixSort(arr,0,arr.length-1,maxbits(arr));
    }
    //找出最大值有多少位
    public static int maxbits(int arrp[]){
        int max=Integer.MIN_VALUE;
        for (int i = 0; i < arrp.length ; i++) {
            max=Math.max(max,arrp[i]);
        }
        int res=0;
        while (max!=0){
            max/=10;
            res++;
        }
        return res;
    }
    public  void radixSort(int []arr,int l,int r,int digit){
        //十进制的数 以10为基底
        final int radix=10;
        int i=0,j=0;
        int [] help=new int[r-l+1];
        //有多少位 循环多少次
        for (int k = 1; k <=digit ; k++) {
            //10个空间
            //count[0]当前位k位是0数字有多少个
            //count[1]当前位k位是1数字有多少个
            //等等到10个  因为是十进制
            int count[]=new int[radix];
            for (i = l; i <=r ; i++) {
                //拿出k位的数
                j=getDigit(arr[i],k);
                count[j]++;
            }
            for (i = 1; i < radix; i++) {
                //每一个累计等于本个加上上一个
                //变成累加的位数
                count[i]=count[i]+count[i-1];
            }
            //从数组尾部开始算，尾部的数放在help下标为累计的数-1
            //完成后count下标为j的数减1
            for (i = r; i <=l ; i--) {
                j=getDigit(arr[i],k);
                help[count[j]-1]=arr[i];
                count[j]--;
            }
            //help数组排序完成放回arr 完成第一轮的排序
            for (i = l,j=0; i <=r ; i++,j++) {
                arr[i]=help[j];
            }

        }

    }
    //拿出d位置的数如个位 十位...
    public int getDigit(int arr,int digit){
        return (arr/((int)Math.pow(10,--digit)))%10;
    }



    //排序稳定性：冒泡 插入 归并 计数 基数
    //非稳定性：选择 快排 堆排

    //不基于比较的排序 对样本数据有严格要求 不易改写
    //基于比较的排序  只要规定好样本怎么比大小就可以直接复用
    //基于比较的排序 时间复杂度极限是o（logn*n）
    //时间复杂度o（logn*n） 额外空间复杂度低于o（n）且稳定的比较排序是不存在的
    //为了绝对速度选择快排  为了省空间堆排 为了稳定选归并

    //用快排：左边一类数  右边一类数 甚至可以中间一类数






}
