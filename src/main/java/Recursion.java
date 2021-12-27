/**
 * @author zhang
 * @ClassName: Recursion
 * @Package PACKAGE_NAME
 * @Description: 递归
 * @date 2021/12/213:25
 */
public class Recursion {

    //T(N)=a*T(N/b)+O(N^d)
    //log b a> d    复杂度为 o(N^ log b a);
    //log b a< d    复杂度为 o(N^ d);
    //log b a== d   复杂度为 o(N^ d * log N);
    //b指分了多少等分去找
    //a指运行了多少次
    //d指除去递归的复杂度

    //这个函数为复杂度为
    //T(N) =2T(N/2)+O(N^0)
    public static void main(String[] args) {
        int [] arr={3,4,1,7};


    }
    public static int processGetMax(int arr[],int L,int R){
        if (L==R){
            //L到R范围只有一个数时候，直接返回该数
            return arr[L];
        }
        int mid=L+((R-L)>>1);
        int leftMax=processGetMax(arr, L, mid);
        int rightMax=processGetMax(arr,mid+1,R);
        return Math.max(leftMax,rightMax);
    }



    //合并排序让局部有序
    //递归合并排序
    //左边一部分排序 右边一部分排序 最后合并排序
    public static void mergeSort1(int arr[]){
        if (arr == null || arr.length < 2 ){
            System.out.println("数组为空或为1 无需排序");
        }else {
            processMerge1(arr,0,arr.length-1);
        }
    }
    private static void processMerge1(int arr[], int L, int R){
        if(L==R){
            return;
        }
        int mid=L+((R-L)>>1);
        processMerge1(arr,L,mid);
        processMerge1(arr,mid+1,R);
        merge(arr,L,mid,R);

    }
    private static void merge(int arr[],int L,int mid, int R){
        int [] help=new int[R-L+1];
        //help数组指针
        int i=0;
        //指向左边数组的最左的数
        int p1=L;
        int p2=mid+1;
        while (p1<=mid && p2<=R){
            help[i++]=arr[p1]<=arr[p2]?arr[p1++]:arr[p2++];
        }
        //要么p1越界要么p2越界 谁没越界谁把剩下的数拷贝到help中
        while (p1<=mid){
            help[i++]=arr[p1++];
        }
        while (p2<=R){
            help[i++]=arr[p2++];
        }
        //
        for (int j = 0; j <help.length ; j++) {
            arr[L+j]=help[i];
        }

    }


    //非递归合并排序
    public static void merageSort2(int arr[]){
        if (arr==null||arr.length<2){
            System.out.println("无需排序");
            return;
        }
        int N=arr.length;
        //当前有序的小组长度
        int mergeSize= 1;
        while (mergeSize<N){
            //标记小组的左边位置
            int L=0;

            while (L < N){
                //L...M
                //L本身需要算进去的 所以要减一
                int M=L+mergeSize-1;
                if (M>=N){break;}

                //L..M   M+1..R
                //M不是 不需要加1
                int R=Math.min(M+mergeSize,N-1);
                //合并
                merge(arr,L,M,R);
                //L的位置在R的右边
                //M1+1....R1L2
                L=R+1;

            }
            //防止mergeSize越界
            if (mergeSize>(N/2)){
                break;
            }
            mergeSize <<=1;

        }


    }

    //在一个数组中，一个数左边比它小的数的总和，叫数的小和，所有数
    //的小和累加起来加数组小和(即找出右边有多少个比你大)
    public int sumSumList(int arr[]){
        if (arr == null || arr.length < 2 ){
            System.out.println("数组为空或为1 无需排序");
            return 0;
        }else {
            return processMerge2(arr,0,arr.length-1);
        }
    }
    public static int processMerge2(int arr[],int l,int r ){
        if (l==r){
            return 0;
        }
        int mid=l+((r-l)>>1);
        return processMerge2(arr,l,mid)+
                processMerge2(arr,mid+1,r)+
                merge2(arr,l,mid,r);
    }
    private static int merge2(int arr[],int l,int mid,int r){
        int help[]=new int[r-l+1];
        int p1=l;
        int p2=mid+1;
        int i=0;
        int res=0;
        while (p1<mid&&p2<r){
            //相等时不加
            res+=arr[p1]<arr[p2]?(r-p2+1)*arr[p1]:0;
            //相等的时候让右边先进 下一步才能知道准确有多少个数比你大
            help[i++]=arr[p1]<arr[p2]?arr[p1++]:arr[p2++];
        }
        while (p1<mid){
            help[i++]=arr[p1++];
        }
        while (p2<r){
            help[i++]=arr[p2++];
        }

        for (int j = 0; j <help.length ; j++) {
            arr[l+j]=help[j];
        }
        return res;
    }
    //使用合并排序的场景  求左边或者右边哪些数比你大或者小



    //练习非递归排序
    public static void  sort2(int arr[]){
        if(arr==null&&arr.length>2){
            return;
        }
        int l=0;
        int mergeSize=1;
        int m;
        int r;
        int n=arr.length;
        while (l<n){
            if (mergeSize>=n){
                break;
            }
            m=l+mergeSize-1;
            if (m>=n){
                break;
            }
            r=Math.min(m+mergeSize,n-1);
            merge(arr,l,m,r);
            if (mergeSize>(n/2)){
                break;
            }
            l=r+1;
            mergeSize<<=1;

        }


}

    //快速排序

    //arr[l....r]荷兰国旗问题的划分 以arr[r]划分
    //<arr[R] ==arr[R]  >arr[R]
    public static int[] netherlandsFlag(int [] arr,int l,int r ){
        if (l>r){
            return new int[]{-1,-1};
        }
        if (l==r){
            return new int[]{l,r};
        }
        // < 区的边界
        int less=l-1;
        // > 区的边界 从r开始是因为最后好找r相等的数最后的下标
        int more=r;
        int index=l;
        while (index < more){
            //等于时index直接+1
            if(arr[index]==arr[r]){
                index++;
                //小于时index与小于下标+1的位置交换数据，index加1
            }else if(arr[index]<arr[r]){
                swap(arr,index++,++less);
                //大于时index与大于表-1的位置交换数据，index不动
            }else if(arr[index]>arr[r]){
                swap(arr,index,--more);
            }
        }
        //完成上述操作后，大于下标与r交换数据，即把相同的数并在一次
        swap(arr,more,r);
        //返回与r相等的下标串
        return new int[]{less+1,more};

    }
    //交换数据
    private static void swap(int arr[],int p1,int p2){
        int temp;
        temp=arr[p1];
        arr[p1]=arr[p2];
        arr[p2]=temp;
    }
    //快排2.0 以arr[r]划分 时间复杂度o(n2)
    public static void quickSort2(int arr[]){
        if (arr==null||arr.length<2){
            return;
        }
        processQuick2(arr,0,arr.length);
    }
    private static void processQuick2(int arr[],int l,int r){
        if (l>=r){
            return;
        }
        int []equals=netherlandsFlag(arr,l,r);
        processQuick2(arr,l,equals[0]-1);
        processQuick2(arr,equals[1]+1,r);
    }


    //快排3.0 以随机数划分 时间复杂度o(n*logn)
    private static void processQuick3(int arr[],int l,int r){
        if (l>=r){
            return;
        }
        //随机选择l.....r的位置上任意一个数
        swap(arr,l+(int)(Math.random()*(r-l+1)),r);
        int []equals=netherlandsFlag(arr,l,r);
        processQuick3(arr,l,equals[0]-1);
        processQuick2(arr,equals[1]+1,r);
    }
















}
