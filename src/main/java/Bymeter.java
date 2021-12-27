import java.util.ArrayList;

/**
 * @author zhang
 * @ClassName: Bymeter
 * @Package PACKAGE_NAME
 * @Description: 打表
 * @date 2021/12/1915:30
 */
public class Bymeter {
    //打表找规律
    // 1）某个面试题，输入参数类型简单，并且只有一个实际参数
    // 2） 要求的返回值类型也简单，并且只有一个
    // 3） 用暴力方法，把输入参数对应的返回值，打印出来看看，进而优化code

    //小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。（未完成）
    //1） 能装下6个苹果的袋子
    //2）能装下8个苹果的袋子
    //小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用
    //的袋子数量必须最少，
    //且使用的每个袋子必须装满.
    //给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必
    //须装满，返回-1

    public static int minBagAwesome(int apple){
        //如果是奇数 返回-1
        if ((apple&1)!=0){
            return -1;
        }
        if (apple<18){
            //一系列规律
        }
        return (apple-18)/8+3;
    }
//    给定一个正整数N，表示有N份青草统一堆放在仓库里
//    有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
//    不管是生还是羊，每一轮能吃的草量必须是：
//     1, 4， 16，64•(4的某次方）
//    谁最先把草吃完，谁获胜
//    假设牛和羊都绝顶聪明，都想赢，
//    都会做出理性的決定
//    根据唯一的参数N，返回谁会赢

    public static String winner1(int n){
        //01234
        //后先后先先
        if (n<5){
            return (n==0||n==2)?"先手":"后手";
        }
        //我是先手
        int base=1;
        while (base<=n){
            //递归很多步  有一步是后手赢的 那么如果有下一步 那么必是先手赢
            if (winner1(n-base).equals("后手")){
                return "先手";
            }
            //防止base越界
            if (base>n/4){
                break;
            }
            base*=4;
        }
        return "后手";
    }

    public static String winner2(int n){
        if (n%5==0||n%5==2){
            return "后手";
        }else {
            return "先手";
        }
    }

//定义一种数：可以表示成若干 （数量＞1）连续正数和的数
//比如：
//5=2+3，5就是这样的数
//12=3+4+5，12就是这样的数
//1不是这样的数，因为要求数量大于1个、连续正数和
//2=1+1，2也不是，因为等号右边不是连续正数
//给定一个参数N，返回是不是可以表示成若干连续正数和的
    public static boolean continuousPositive(int n){
        for (int i = 1; i <=n ; i++) {
            int sum=i;
            for (int j = i+1; j <=n ; j++) {
                if (sum+j>n){
                    break;
                }
                if (sum+j==n){
                    return true;
                }
                sum+=j;
            }
        }
return false;
    }
    public static boolean continuousPositive2(int n){
        if (n<3){
            return false;
        }
        //是不是2的某次幂 (n&(n-1))==0 是2的某次方
        return (n&(n-1))!=0;
    }



    //矩阵
    //打印矩阵蛇形zigzag

    public static void printMatrixZigZag(int [][] matrix){
        //行
        int Ar=0;
        //列
        int Ac=0;
        int Br=0;
        int Bc=0;
        int Endr=matrix.length-1;
        int Endc=matrix[0].length-1;
        //是不是从右上往左下打印
        boolean fromUp=false;
        while (Ar!=Endr+1){
            printlevel(matrix,Ar,Ac,Br,Bc,fromUp);
            //行
            Ar=Ac==Endc?Ar+1:Ar;
            //列
            Ac=Ac==Endc?Ac:Ac+1;
            //B刚好反过来 行动先再列  先动的写在最后
            Bc=Br==Endr?Bc+1:Bc;
            Br=Br==Endr?Br:Br+1;
            fromUp=!fromUp;
        }
    }
    public static void printlevel(int [] [] matrix,int Ar,int Ac,int Br,int Bc,boolean fromUp){
        //从上往上使用 B指针  从下往上使用A指针
        if (fromUp){
            while (Ar!=Br+1){
                //从下往上打印  列-- 往回走  行++ 往下走
                System.out.println(matrix[Ar++][Ac--]+" ");
            }
        }else {
            while (Br!=Ar-1){
                //行--往上走 列++往前走
                System.out.println(matrix[Br--][Bc++]+" ");
            }
        }


    }

    //矩阵转圈打印
    public static void printEdge(int [][] m,int a,int b,int c,int d){
        //成横线
        if (a==c){
            for (int i = b; i <=d; i++) {
                System.out.println(m[a][i]);
            }
        }else if (b==d){
            for (int i = a; i <=c ; i++) {
                System.out.println(m[i][d]);
            }
        }else {
            int curC=b;
            int curR=a;
            while (curC!=d){
                System.out.println(m[a][curC++]);
            }
            while (curR!=c){
                System.out.println(m[curR++][d]);
            }
            while (curC!=b){
                System.out.println(m[c][curC--]);
            }
            while (curR!=a){
                System.out.println(m[curR--][b]);
            }

        }
    }
    public static void spiralOrderPrint(int [][]m){
        //左上的点
        int a=0;
        int b=0;
        //右上的点
        int c=m.length-1;
        int d=m[0].length-1;
        while (a<=c&&b<=d){
            printEdge(m,a++,b++,c--,d--);
        }
    }

    //正方矩阵旋转
    public static void rotate(int [][] m){
        int a=0;
        int b=0;
        int c=m.length-1;
        int d=m[0].length-1;
        while (a<c){
            rotateEdge(m,a++,b++,c--,d--);
        }

    }
    public static void rotateEdge(int [][]m,int a,int b,int c,int d){
        int tmp=0;
        //以组计算 i为小组号
        for (int i = 0; i < a-c; i++) {
            //组内1号m[a][b+i]
            //2.m[a+i][d]
            //3.m[c][d-i]
            //4.m[c-i][b]
            //1去2 2去3 3去4 4去1
            tmp=m[a][b+i];
            m[a][b+i]=m[c-i][b];
            m[c-i][b]=m[c][d-i];
            m[c][d-i]=m[a+i][d];
            m[a+i][d]=tmp;
        }




    }

























    public static void main(String[] args) {
       int m[][]={{1,2,3},{3,4,5}};
       printMatrixZigZag(m);
    }












}
