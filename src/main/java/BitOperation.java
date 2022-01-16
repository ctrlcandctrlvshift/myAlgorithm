/**
 * @author zhang
 * @ClassName: BitOperation
 * @Package PACKAGE_NAME
 * @Description: 位运算
 * @date 2022/1/1217:09
 */
public class BitOperation {

    //给定两个有符号的32位整数 a和b  返回较大那个、

    //当n是1或者0时
    //1变0
    //0变1
    public static int flip(int n){
        return n^1;
    }



    //n是非负数返回1
    //n是负数返回0
    public static int sign(int n){
        //符号位左移动  与1
        return flip((n>>31)&1);
    }
//可能溢出
    public static int getMax1(int a,int b){
        int c=a-b;
        //看看c是正数还是负数
        int scA=sign(c);
        //反过来
        int scB=flip(scA);
        //谁大谁是1
        return a*scA+b*scB;


    }

    //只有一种可能是溢出的 就是a是正数 b是负数 正数减负数 为正数
    public static int getMax2(int a,int b){
        int c=a-b;
        //找出符号
        int sa=sign(a);
        int sb=sign(b);
        int sc=sign(c);

        //一样的是0 不一样是1
        int difSab=sa^sb;
        int sameSab=flip(difSab);

        int returnA=difSab*sa+sameSab*sc;
        int returnB=flip(returnA);

        return returnA*a+b*returnB;

    }

    //判断是否是2的幂次方  1.n&（n-1）==0 即是 否不是 2.提取int 值 最后一位 int n=n与（n取反加1） 再与本身 是0即是
    //判断是否是4的幂次方 1判断是不是2的幂次方  n 与 01010101..01 是否为0
    public static boolean is4Power(int n){
        return (n &(n-1))==0&& (n&0x55555555)!=0;
    }

    //32位整数  a和b  不使用算术运算符  实现a和b的加减乘除运算
    //保证计算过程中不出现溢出
    //如果用户输入的参数就是溢出的 那用户活该
    public static int add(int a,int b){
        int sum=a;
        while (b!=0){
            //无进位相加结果
            sum=a^b;
            //进位信息
            b=(a&b)<<1;
            a=sum;
        }
        return sum;
    }
    //减法
    //取相反数
    public static int negNum(int n){
        return add(~n,1);
    }

    public static int minus(int a,int b){
        return add(a,negNum(b));
    }

    //乘法 乘法不需要特意去考虑符号位的原因是 如果想将符号位变化那么容易越界
    //按照人的二进制乘法
    public static int multi(int a,int b){
        int res=0;
        while (b!=0){
            if ((b&1)!=0){
                res=add(res,a);
            }
            a=a<<1;
            b=b>>>1;
        }
       return res;
    }



    //除法 需要考虑符号问题  因为除法越出越小
    //最简单的就是不断地用被除数减去除数，直到被除数小于除数，此时减的次数就是商；
    public static boolean isNeg(int n){
        return n<0;
    }
    public static int div(int a,int b){
        //把所有数变成正数
        int x=isNeg(a)?negNum(a):a;
        int y=isNeg(b)?negNum(b):b;
        int res=0;

        for (int i = 31; i >-1 ; i=minus(i,1)) {
            //右移动小于等于y  实际上的操作是y向左移动之所以要x向右移动 y向右移动可能会刚好移动到符号位 导致y突然变小
            if ((x>>i)>=y){
                res|=(1<<i);
                x=minus(x,y<<i);
            }

        }
        //如果ab 有一个为负 res为负 不用逆转
        return isNeg(a)^isNeg(b)?negNum(res):res;
    }









    public static void main(String[] args) {
////        int a=Integer.MAX_VALUE;
////        int b=Integer.MIN_VALUE;
////        System.out.println(getMax2(a, b));
////        int a=40;
////        int b=2;
////
////        System.out.println(negNum(b));
////        System.out.println(Integer.toBinaryString(b));
////        System.out.println(add(a, b));
//
//        int c=~(Integer.MIN_VALUE>>>2);
//        int d=~(Integer.MIN_VALUE>>>1);
//        int e=~(Integer.MIN_VALUE>>>3);
//        int a=((Integer.MIN_VALUE>>5)&c&d&e);
// //       System.out.println(a * 2);
//
//
//        //System.out.println(Integer.toBinaryString(a));
//        int b=2;
//
//        multi(a,b);
//
//        System.out.println(multi(a, b));
//        System.out.println(a);
//        System.out.println(Integer.MIN_VALUE);
        int a=-10;
        int b=-1;
        System.out.println(div(a, b));
    }




}
