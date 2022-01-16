/**
 * @author zhang
 * @ClassName: Test1
 * @Package PACKAGE_NAME
 * @Description: 练习
 * @date 2022/1/1517:05
 */
public class Test1 {

//滑动窗口问题
//给定一个有序数组arr，代表数轴上从左到右有n个点arr[0],arr[1],arr[2],arr[3]
//给定一个正数L，代表一根长度为L的绳子，求绳子最多能覆盖其中的几个点
    //想法1： 某一个点刚好被右侧（在该点上）覆盖 推算左边点的位置
    //想法2： 以L R线去判断  以L 盖到绳子左侧R去延申到绳子的右侧
    public static int cordL(int []arr, int L){
        int right=0;
        int max=1;
        int cur=1;
        for (int left = 0; left <arr.length ;) {
            while (right+1<arr.length){
                if (arr[++right]-arr[left]<=L){
                    cur++;
                    max=Math.max(max,cur);
                }else {
                    right--;
                    break;
                }
            }
            left++;
            cur--;
        }
        return max;
    }




    //贪心 打表题
    //小虎去附近的商店买苹果，奸诈的商贩使用了捆鄉交易，只提供6个每袋和8个
    //每袋的包装包装不可拆分。可是小虎现在只想购买恰好n个苹果，小虎想购买尽
    //量少的袋数方便携带。如果不能购买恰好n个苹果，
    //小虎将不会购买。输入一个
    //整数n,
    //表示小虎想购买的个苹果，返回最小使用多少袋子。如果无论如何都不
    //能正好装下，返回-1。
    //想法1：尽量使用8号袋(暴力递归)
    //想法2: 贪心
    public static int sack(int n){
        if ((n&1)!=0){
            return -1;
        }
        int bag;
        if (n%8==0){
            return n/8;
        }else {
            bag=n/8;
            bag=process(n-(8*bag),bag,n);
        }
        return bag;
    }
    public static int process(int remain,int bag,int n){
        if (remain>n){
            bag=-1;
            return bag;
        }
        if (remain%6==0){
            bag+=remain/6;
            return bag;
        }else{
             bag=process(remain+8,bag-1,n);
            return bag;
        }
    }


//牛牛有一些排成一行的正方形。每个正方形已经被染成红色或者绿色。牛牛现在可
//以选择任意一个正方形然后用这两种颜色的任意一种进行染色，这个正方形的颜色将
//会被覆盖。牛牛的目标是在完成染色之后，每个红色R都比每个绿色G距离最左侧近。
//牛牛想知道他最少需要涂染几个正方形。
//如样例所示：
//= RGRGR
//我们涂染之后变成RRRGG满足要求了，涂染的个数为2，没有比这个更好的涂染方案









    public static void main(String[] args) {
//        int []arr={2,5,8,9,10};
//        int L=5;
//        System.out.println(cordL(arr, L));
        System.out.println(sack(115));


    }
}
