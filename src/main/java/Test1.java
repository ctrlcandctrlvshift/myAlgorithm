import org.omg.CORBA.MARSHAL;

import javax.sound.sampled.DataLine.Info;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;

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
    public static int cordL(int[] arr, int L) {
        int right = 0;
        int max = 1;
        int cur = 1;
        for (int left = 0; left < arr.length; ) {
            while (right + 1 < arr.length) {
                if (arr[++right] - arr[left] <= L) {
                    cur++;
                    max = Math.max(max, cur);
                } else {
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
    public static int sack(int n) {
        if ((n & 1) != 0) {
            return -1;
        }
        int bag;
        if (n % 8 == 0) {
            return n / 8;
        } else {
            bag = n / 8;
            bag = process(n - (8 * bag), bag, n);
        }
        return bag;
    }

    public static int process(int remain, int bag, int n) {
        if (remain > n) {
            bag = -1;
            return bag;
        }
        if (remain % 6 == 0) {
            bag += remain / 6;
            return bag;
        } else {
            bag = process(remain + 8, bag - 1, n);
            return bag;
        }
    }

    //预处理（缓存）
//牛牛有一些排成一行的正方形。每个正方形已经被染成红色或者绿色。牛牛现在可
//以选择任意一个正方形然后用这两种颜色的任意一种进行染色，这个正方形的颜色将
//会被覆盖。牛牛的目标是在完成染色之后，每个红色R都比每个绿色G距离最左侧近。
//牛牛想知道他最少需要涂染几个正方形。
//如样例所示：
//= RGRGR
//我们涂染之后变成RRRGG满足要求了，涂染的个数为2，没有比这个更好的涂染方案
    //想法1：设左边是R 右边是G(暴力递归)
    //想法2：预处理结果方法 用数组记录左右两边R G出现的次数
    public static int scrawl(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        return process(s, -1);
    }

    public static int process(String s, int left) {
        if (left > s.length()) {
            return Integer.MAX_VALUE;
        }
        int cur = 0;
        char[] arr = s.toCharArray();
        if (left != -1) {
            for (int i = 0; i < arr.length; i++) {
                if (i <= left) {
                    if (arr[i] == 'G') {
                        cur++;
                    }
                } else {
                    if (arr[i] == 'R') {
                        cur++;
                    }
                }
            }
        } else {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 'R') {
                    cur++;
                }
            }
        }
        int minPre = process(s, left + 1);
        return Math.min(cur, minPre);
    }


//给定一个N*N的矩阵matrix，只有0和1两种值，返回边框全是1的最大正方形的边
//长长度
//例如：
//01111
//01001
//01001
//01111
//01011
//其中边框全是1的最大正方形的大小为4*4，所以返回4。
    //想法1：固定左上角点  再遍历边长 画出唯一正方形
    // 验证：1依次遍历4条边（o（n））
    // 2（预处理）使用两个矩阵 一个从右边自己开始看右边（包括自己）有多少个连续的1  一个从下看（包括自己）连续有多少个1
    //验证的时候直接拿这两个矩阵去对 左上 左下 右上的角


    //给定一个西数f，可以1~5的数字等概率返回一个。请加工出1~7的数字等概率
    //返回一个的西数g。
    //给定一个西数f，可以a~b的数字等概率返回一个。请加工出o~d的数字等概率
    //返回一个的西数g。
    //给定一个函数f，以p概率返回0，以1-p概率返回1。请加工出等概率返回0和1的：两个f函数 得到01 10返回 即等概率
    //函数g
    //思路：先加工成01 发生器
    //通过0到X +某个数  达到想要的范围

    public static int f() {
        return (int) (Math.random() * 5) + 1;
    }


    //等概率返回0和1的函数
    //12 3 45 其中中3重做 等概率返回
    public static int r01() {
        int res = 0;
        do {
            res = f();
        } while (res == 3);
        return res > 3 ? 0 : 1;
    }

    //1-7 即时0-6+1
    //3个二进制位即可
    public static int g() {
        int res = 0;
        do {
            res = (r01() << 2) + (r01() << 2) + r01();
        } while (res == 7);
        return res + 1;
    }

    //给定一个非负整数n，代表二叉树节点个数 返回能形成多少种不同的二叉树结构
    public static int process(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int res = 0;
        for (int i = 0; i <= n - 1; i++) {
            int left = process(i);
            int right = process(n - i - 1);
            res += left * right;
        }
        return res;
    }

    public static int dpBinaryTree(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int dp[] = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i < n + 1; i++) {
            //左边j 右边i-j-1
            for (int j = 0; j <= i - 1; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }


        return dp[n];

    }

    //一个完整的括号字符串定义规则如下
    //①空字符串是完整的
    //②如果s是完整的字符串，那么（s)也是完整的。
    //@如果s和七是完整的宇符串，
    //，将它们连接起来形成的st也是完整的。
    //例如.
    //"(00)"
    //""和"（0）（“是完整的括号字符串，
    //"）（"
    //"0（"和""
    //是不完整的括号字符串，
    //牛牛有一个括号字符串s，现在需要在其中任意位置尽量少地添加括号，将其转化
    //为一个完整的括号字符串。请问牛牛至少需要添加多少个括号
    //1 判断是否完整  使用 int count  左括号+1 右括号-1 1. count 不能减到0以下  2.count为0
    //
    public static int needParentheses(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int count = 0;
        //处理count=0左边的右框
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                count++;
            } else {
                if (count == 0) {
                    //有多少右 加多少左
                    ans++;
                } else {
                    count--;
                }
            }

        }
        return ans + count;


    }


    //给定一个数组arr，求差值为k的去重数字对
    public static List<List<Integer>> differentialNum(int arr[], int k) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }

        Iterator it = set.iterator();
        while (it.hasNext()) {
            int cur = (int) it.next();
            int next = cur + k;
            if (set.contains(next)) {
                List<Integer> list = new ArrayList<>();
                list.add(cur);
                list.add(next);
                result.add(list);
            }
        }
        return result;
    }


    //给一个包含n个整数元素的集合a，
    //一个包含n个整数元素的集合b。
    //定义magic操作为，从一个集合中取出一个元素，放到免一个集合里，且操作过
    //后每个集合的平均值都大大手于操作前。
    //注意以下两点：
    //1）不可以把一个集合的元素取空，这样就没有平均值了
    //2）值为x的元素从集合b取出放入集合a，但集合a中己经有值为x的元素，则a的
    //平均值不变（因为集合元素不会重复），b的平均值可能会改变 （因为x被取出
    //了）
    //问最多可以进行多少次magic操作？
    public static int maxOps(int[] arr1, int[] arr2) {
        double sum1 = 0;
        for (int i = 0; i < arr1.length; i++) {
            sum1 += (double) arr1[i];
        }
        double sum2 = 0;
        for (int i = 0; i < arr2.length; i++) {
            sum2 += (double) arr2[i];
        }
        if (avg(sum1, arr1.length) == avg(sum2, arr2.length)) {
            return 0;
        }
        int[] arrMore;
        int[] arrLess;
        double sumMore;
        double sumLess;

        if (avg(sum1, arr1.length) > avg(sum2, arr2.length)) {
            arrMore = arr1;
            arrLess = arr2;
            sumMore = sum1;
            sumLess = sum2;
        } else {
            arrMore = arr2;
            arrLess = arr1;
            sumMore = sum2;
            sumLess = sum1;
        }
        Arrays.sort(arrMore);
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < arrLess.length; i++) {
            set.add(arrLess[i]);
        }

        int res = 0;
        int moreSize = arrMore.length;
        int lessSize = arrLess.length;
        for (int i = 0; i < arrMore.length; i++) {
            if (arrMore[i] < avg(sumMore, moreSize) && arrMore[i] > avg(sumLess, lessSize) && !set.contains(arrMore[i])) {
                sumMore -= arrMore[i];
                sumLess += arrMore[i];
                set.add(arrMore[i]);
                moreSize--;
                lessSize++;
                res++;
            }
        }
        return res;
    }

    public static double avg(double sum, int length) {
        return sum / length;
    }


    //一个合法的括号匹配序列有以下定义：
//①空串""是一个合法的括号匹配序列
//②如果"x"和”Y"都是合法的括号匹配序列，"xY"也是一个合法的括号匹配序列
//③如果"x"是
//一个合法的括号匹配序列，那么"(X)“也是一个合法的括号匹配序列
//④每个合法的括号序列都可以由以上规则生成。
//例如：
//111
//"（）”
//"（）"
//"（（0））"都是合法的括号序列
//对于
//一个合法的括号序列我们又有以下定义它的深度：
//①空串"“的深度是0
//②如果字符串"X"的深度是x，字符串"Y "的深度是y，那么字符串"XY"的深度为
//max (x,y）3、如果"X“的深度是x,那么字符串"(X)"的深度是x+1
//例如：
//"（）（）（）”的深度是1，
//"((（）））“的深度是3。牛牛现在给你一个合法的括号
//序列.需要你计算出其深度
    //假设s没问题
    public static int parenthesesDeep(String s) {
        char[] arr = s.toCharArray();
        int count = 0;
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (count < 0) {
                return -1;
            }
            if (arr[i] == '(') {
                count++;
                max = Math.max(count, max);
            } else {
                count--;
            }
        }
        if (count != 0) {
            return -1;
        }
        return max;
    }


    //找括号最长有效子串 求dp[]
    public static int parenthesesMAX(String s) {
        if (s == null || s.length() == 0 || s.length() == 1) {
            return 0;
        }
        char[] arr = s.toCharArray();
        int dp[] = new int[arr.length];
        int res = 0;
        int pre = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == ')') {
                pre = i - dp[i - 1] - 1;
                if (pre >= 0 && arr[pre] == '(') {
                    dp[i] = pre - 1 >= 0 ? dp[i - 1] + 2 + dp[pre - 1] : dp[i - 1] + 2;
                }
                res = Math.max(dp[i], res);
            }
        }
        return res;
    }


    //编写一个程序，对一个栈里的整型数据，按升序进行排序 （即排序前，栈里
    //的数据是无序的，排序后最大元素位于栈顶）
    //，要求最多只能使用一个额外的
    //栈存放临时数据
    //但不得将元素复制到别的数据结构中。

    public static Stack<Integer> myStack(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return null;
        }
        Stack<Integer> newStack = new Stack<>();
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            while (!newStack.isEmpty() && cur > newStack.peek()) {
                stack.push(newStack.pop());
            }
            newStack.add(cur);
        }
        while (!newStack.isEmpty()) {
            stack.push(newStack.pop());
        }
        return stack;
    }

    //将给定的数转换为字符串，原则如下：1对应 a，2对应b，26对应z
//例如12258可以转换为”abbeh"
//"aveh"
//"abyh"
//"lbeh"
//和 "lyh"
//个数为5，编写一个函数，给出可以转换的不同字符串的个数
    public static int numToChar(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] arr = s.toCharArray();
        return process1(arr, 0);
    }

    public static int process1(char[] arr, int index) {
        if (index >= arr.length) {
            return 1;
        }
        if (arr[index] == '0') {
            return 0;
        }
        int p2 = 0;
        int p1 = process1(arr, index + 1);

        if (arr[index] == '1' && index + 1 < arr.length) {
            p2 = process1(arr, index + 2);
        }
        if (arr[index] == '2' && index + 1 < arr.length && arr[index + 1] <= '6') {
            p2 = process1(arr, index + 2);
        }
        return p1 + p2;


    }


    //二叉树每个结点都有一个int型权值，给定一棵二叉树，要求计算出从根结点到
    //叶结点的所有路径中，权值和最大的值为多少。

    public static class Node {
        public Node left;
        public Node right;
        public int value;

        public Node(Node left, Node right, int value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public Node(int value) {
            this.left = null;
            this.right = null;
            this.value = value;
        }
    }

    public static class Info {
        public int preMax;

        public Info(int preMax) {
            this.preMax = preMax;
        }
    }

    public static int maxNode(Node head) {
        if (head == null) {
            return 0;
        }
        Info res = process(head);
        return res.preMax;
    }

    public static Info process(Node node) {
        if (node == null) {
            return new Info(Integer.MIN_VALUE);
        }
        if (node.left == null && node.right == null) {
            return new Info(node.value);
        }
        Info left = process(node.left);
        Info right = process(node.right);
        return new Info(Math.max(left.preMax, right.preMax) + node.value);
    }


    //给定一个元素为非负整数的二维数组matrix，每行和每列都是从小到大有序的。
//再给定一个非负整数aim，请判断aim是否在matrix中。
    public static boolean matrixIn(int[][] matrix, int num) {
        //行
        int rows = matrix.length;
        //列
        int cols = matrix[0].length;
        return find(matrix,num,0,matrix[0].length-1,rows,cols);
    }

    public static boolean find(int[][] matrix, int num, int r, int c,int rows,int cols) {
        if (c<0||r >= rows) {
            return false;
        }
        if (num == matrix[r][c]) {
            return true;
        }
        else if (num > matrix[r][c]) {
            return find(matrix, num, r+1, c,rows,cols) ? true : false;
        }else {
            return find(matrix, num, r, c-1,rows,cols) ? true : false;
        }

    }


    //有n个打包机器从左到右一字排开，
    //上方有一个自动装置会抓取一批放物品到每个打
    //包机上，放到每个机器上的这些物品数量有多有少，
    //由于物品数量不相同，需要工人
    //将每个机器上的物品进行移动从而到达物品数量相等才能打包。每个物品重量太大
    //每次只能搬一个物品进行移动，为了省力，只在相邻的机器上移动。请计算在搬动最
    //小轮数的前提下，使每个机器上的物品数量相等。如果不能使每个机器上的物品相同
    //返回-1。
    //例如[1，0,5]表示有3个机器，每个机器上分别有1、0、5个物品，经过这些轮后：
    //第一轮：1 1 4
    // 第二轮:2 1 3
    // 第三轮：2 2 2
    //(同时)
    //移动了3轮，每个机器上的物品相等，所以返回3
    //例如[2,2,3]表示有3个机器，每个机器上分别有2、2、3个物品，
    //这些物品不管怎么移动，都不能使三个机器上物品数量相等，返回-1

//一条线上有n台超级洗衣机。 最初，每台洗衣机都有一些衣服或者是空的。
//对于每一步，你可以选择任意m (1 <= m <= n)台洗衣机，并将每台洗衣机中的一件衣服  “同时”  传递给相邻的一台洗衣机。
//给定一个整数数组，表示每台洗衣机从左到右的衣服数量，返回使所有洗衣机拥有相同数量衣服的最小移动次数。 如果不可能，则返回-1。
// 因为是同时的 所以从左到右最高代价的那个可以完成任务
    public static int minOps(int [] machines){
        if (machines==null||machines.length==0){
            return -1;
        }
        int sum=0;
        int []dp=new int[machines.length];
        for (int i = 0; i <machines.length ; i++) {
            sum+=machines[i];
            dp[i]=sum;
        }
        if (sum%machines.length!=0){
            return -1;
        }
        int max=Integer.MIN_VALUE;
        int avg=sum/machines.length;
        for (int i = 0; i <machines.length ; i++) {
            int leftSize=i;
            int leftDiff=i>0?dp[i-1]-avg*leftSize:0;

            int rightSize=machines.length-1-i;
            int rightDiff=i<machines.length-1?(sum-dp[i])-avg*rightSize:0;

            if (leftDiff<0&&rightDiff<0){
                max=Math.max(max,Math.abs(leftDiff)+Math.abs(rightDiff));
            }else {
                max=Math.max(max,Math.max(Math.abs(leftDiff),Math.abs(rightDiff)));
            }
        }
        return max;
    }


    //假设s和m初始化，s ="a";m=s；
    //再定义两种操作，第一种操作：
    //m=s:
    //s=s + s:
    //第二种操作：
    //s=s+m
    //求最小的操作步骤数，可以将s拼接到长度等于n
    //1.如果是质数  那么只能使用第二种操作
    //2.如果不是质数  那么分解为质数相乘  重复质数操作

    public static boolean isPrim(int n){
        boolean flag=true;
        for (int i = 2; i < n; i++) {
            if (n%i==0){
                flag=false;
                break;
            }
        }
        return flag;
    }

    public static int stepSize(int n){
        if (n<2){
            return 0;
        }
        if (isPrim(n)){
            return n-1;
        }
        int [] res=divPrimAndSum(n);
        return res[0]-res[1];
    }
    public static int [] divPrimAndSum(int n){
        int sum=0;
        int count=0;
        for (int i = 2; i <=n ; i++) {
            while (n%i==0){
                sum+=i;
                count++;
                n/=i;
            }
        }
        return new int[]{sum, count};
    }

    //arr数组返回词频最高的前k个
    //小根堆方法
    public static class PileNode{
        public String s;
        public int count;

    public PileNode(String s, int count) {
        this.s = s;
        this.count = count;
    }
}
    public static class PileComparater implements Comparator<PileNode>{
            @Override
            public int compare(PileNode o1, PileNode o2) {
                return o1.count-o2.count;
            }
        }
    public static String [] topString(String []arr,int k){
        if (arr==null||k<0){
            return null;
        }
        if (arr.length<k){
            return arr;
        }
        HashMap<String, Integer> map=new HashMap<>();

        for (int i = 0; i <arr.length ; i++) {
            if (!map.containsKey(arr[i])){
                map.put(arr[i],1);
            }else {
                int count=map.get(arr[i]);
                map.put(arr[i],++count);
            }
        }
        PriorityQueue <PileNode>pile =new PriorityQueue<>(new PileComparater());
        int size=0;
        Set set=map.keySet();
        Iterator it=set.iterator();
        while (it.hasNext()){
            String cur=(String) it.next();
            if (size<k){
                pile.add(new PileNode(cur,map.get(cur)));
                size++;
            }else {
                if (pile.peek().count<map.get(cur)){
                    pile.poll();
                    pile.add(new PileNode(cur,map.get(cur)));
                }
            }
        }
        String [] result=new String[k];
        for (int i = 0; i <k ; i++) {
            result[i]=pile.poll().s;
        }
        return result;
    }

    //手撸一个可以更新的小堆
    public static class MyPile<T>{
        public int heapSize=0;
        public HashMap <T,Integer> indexMap=new HashMap<>();
        public ArrayList<T> arr=new ArrayList<>();
        public Comparator<T> comparator;

        public MyPile(Comparator<T> comparator) {
            this.comparator = comparator;
        }
        public boolean isContain(T t){
            return indexMap.containsKey(t);
        }

        public int getSize(){
            return heapSize;
        }
        //从零开始的
        //head:index
        //left:2*index+1  right:2*index+2
        //小堆
        public void headify(int index){
            while ((index<<1)+1<heapSize){
                int left=(index<<1)+1;
                int right=left+1;
                int min=right<heapSize&&comparator.compare(arr.get(right),arr.get(left))<0?right:left;
                min=comparator.compare(arr.get(index),arr.get(min))<0?index:min;

                if (index==min){
                    break;
                }
                swap(index,min);
                index=min;
            }

        }

        public void headInsert(int index){
            while (index>0){
                int head=(index-1)>>1;
                int min=comparator.compare(arr.get(index),arr.get(head))>=0?index:head;
                if (min==index){
                    break;
                }
                swap(index,min);
                index=min;
            }
        }

        public void swap(int p1,int p2){
            T o1=arr.get(p1);
            T o2=arr.get(p2);
            indexMap.put(o1,p2);
            indexMap.put(o2,p1);
            arr.set(p1,o2);
            arr.set(p2,o1);

        }
        public void add(T t){
            if (t==null){
                return;
            }
            indexMap.put(t,heapSize);
            arr.add(heapSize,t);
            headInsert(heapSize++);
        }

        public T pop(){
            T res=arr.get(0);
            swap(0,--heapSize);
            indexMap.remove(arr.get(heapSize));
            arr.remove(heapSize);
            headify(0);
            return res;
        }

        public T peek(){
            return arr.get(0);
        }

        public boolean regin(T t){
            if (indexMap.containsKey(t)){
                headify(indexMap.get(t));
                headInsert(indexMap.get(t));
                return true;
            }
            return false;
        }

    }
    //随时更新top
    public static class TopSumSort{
        //词频统计
        HashMap<String, PileNode> collectMap=new HashMap<>();
        //可更新的最小堆
        MyPile<PileNode> piles=new MyPile<>(new PileComparater());
        int top;

        public TopSumSort(int top) {
            this.top = top;
        }

        //维护top个数
        public void add(String s){
            if (top>piles.getSize()){
                if (collectMap.containsKey(s)){
                    //更新
                    collectMap.get(s).count++;
                    piles.regin(collectMap.get(s));
                }else {
                    //添加新的
                    PileNode node=new PileNode(s,1);
                    collectMap.put(s,node);
                    piles.add(node);
                }
            }else {
                if (collectMap.containsKey(s)){
                    PileNode cur=collectMap.get(s);
                    cur.count++;
                    if (piles.isContain(cur)){
                        piles.regin(cur);
                    }else {
                     if (cur.count>piles.peek().count){
                         piles.pop();
                         piles.add(cur);
                     }
                    }
                }else {
                    PileNode node=new PileNode(s,1);
                    collectMap.put(s,node);
                }

            }
        }

        public String [] topPrint(){
            String[] res=new String[top];
            for (int i = 0; i <top ; i++) {
                res[i]=piles.arr.get(i).s;
                System.out.print(res[i]);
            }
            System.out.println();
            return res;
        }


    }

//动态规划的空间压缩技巧
//给你一个二维数组matrix，其中每个数都是正数，要水从左上角走到右下角。每
//一步只能向右或者向下，沿途经过的数宇要累加起来。最后请返口最小的路径和。
    public static int sumMatrix(int [][] matrix){
        if (matrix==null||matrix.length==0){
            return 0;
        }
        return process(matrix,0,0);
    }

    //row 行
    //col 列
    public static int process(int [][] matrix,int row,int col){
        if (row>=matrix.length||col>=matrix[0].length){
            return Integer.MAX_VALUE;
        }
        if (row==matrix.length-1&&col==matrix[0].length-1){
            return matrix[row][col];
        }

            int right=process(matrix,row,col+1);
            int down=process(matrix,row+1,col);
            return matrix[row][col]+Math.min(right,down);
    }

    public static int dpSumMatrix(int [][]matrix){
        if (matrix==null||matrix.length==0){
            return 0;
        }
        int rows=matrix.length;
        int cols=matrix.length;
        int [][]dp=new int[rows+1][cols+1];
        for (int i = 0; i <cols+1 ; i++) {
            dp[rows][i]=Integer.MAX_VALUE;
        }
        for (int i = 0; i <rows+1 ; i++) {
            dp[i][cols]=Integer.MAX_VALUE;
        }
        for (int row = rows-1; row >=0; row--) {
            for (int col = cols-1; col >=0 ; col--) {
                if (row==matrix.length-1&&col==matrix[0].length-1){
                    dp[row][col]=matrix[row][col];
                    continue;
                }
                int right=dp[row][col+1];
                int dowm=dp[row+1][col];
                dp[row][col]= matrix[row][col]+Math.min(right,dowm);
            }
        }
return dp[0][0];

    }


    //压缩dp  分析dp 得出解压形式
    public static int compressDpSum(int [][]matrix){
        if (matrix==null||matrix.length==0){
            return 0;
        }
        int rows=matrix.length;
        int cols=matrix.length;
        int []dp=new int[cols];
        //压缩好了
        dp[cols-1]=matrix[rows-1][cols-1];
        for (int i = cols-2; i>=0 ; i--) {
            dp[i]=matrix[rows-1][i]+dp[i+1];
        }

        //解压
        for (int i = rows-2; i >=0 ; i--) {
            for (int j = cols-1; j >=0 ; j--) {
                if (j==cols-1){
                    dp[j]=dp[j]+matrix[i][j];
                    continue;
                }
                int res=matrix[i][j]+Math.min(dp[j+1],dp[j]);
                dp[j]=res;
            }

        }

        return dp[0];
    }

    //给定一个数组arr，己知其中所有的值都是非负的，将这个数组看作一个容器，
    //请返回容器能装多少水
    //比如，arr = {3,1，2，5，2，4}，根据值画出的直方图就是容器形状，该容
    //器可以装下5格水
    //再比如，arr = {4,5，1，3，2}，该容器可以装下2格水
    //有点像洗衣机那道题  中间分析左右  可装水值=Max（Min(左Max，右Max)-arr[i]，0）

    public static int waterBucket(int [] arr){
        if (arr==null||arr.length==0){
            return 0;
        }
        int leftMax=arr[0];
        int rightMax=arr[arr.length-1];
        int left=1;
        int right=arr.length-2;
        int res=0;
        //哪边max小结算哪边

        while (left<=right){
            //结算左边
            if (leftMax<=rightMax){
                res+=Math.max(leftMax-arr[left],0);
                leftMax=Math.max(arr[left],leftMax);
                left++;
            }else
                //结算右边
            {
                res+=Math.max(rightMax-arr[right],0);
                rightMax=Math.max(arr[right],rightMax);
                right--;
            }
        }
        return res;
    }
    //给定一个数组arr长度为N，你可以把任意长度大于0且小于N的前缀作为左部分，剩下的
    //作为右部分。但是每种划分下都有左部分的最大值和右部分的最大值，请返回最大的，
    //左部分最大值减去右部分最大值的绝对值。

    //取数组最大值先
    //0 和 N-1 必是左右边的一个数
    //所以谁小选谁
    public static int maxLeftMaxRight(int [] arr){
        int max=Integer.MIN_VALUE;
        for (int i = 0; i <arr.length ; i++) {
            max=Math.max(max,arr[i]);
        }
        return arr[0]>arr[arr.length-1]?max-arr[arr.length-1]:max-arr[0];
    }

    //如果一个字符串为str，把字符串str前面任意的部分挪到后面形成的字符串叫
    //作str的旋转词。比如str="12345”
    //str的旋转词有"12345"、
    //1"23451"
    //"34512"
    //〝45123”和"51234”。给定两个字符串a和b，请判断a和b是否互为旋转
    //词。
    //比如：
    //a="cdab"
    //b="abcd”，返回true。
    //a="1ab2"
    //b="ab12
    //返回false。
    //a="2ab1
    //b="ab12"
    //返回true。
    //解法  用两倍的a 如果b字符串存在两倍的a中 那么返回ture （主要是kmp这类算法）


//有咖啡机煮咖啡的时间  arr[]={1,2,3} 串行煮咖啡
    //有N个人想喝咖啡
    //有一台洗咖啡机
    //洗的时间为a
    //挥发时间为b
    //怎么搞时间最短 喝完和洗干净

    //咖啡机可用时间

    //问题1： 10个人最短拿到咖啡的时间arr也是什么时间开始洗的时间 （秒喝）（小根堆）
    //暴力递归里的题（后面完成）


    //给定一个数组arr，如果通过调整可以做到arr中任意两个相邻的数字相乘是4的倍数 返回true  不能false
    //找出 奇数a  只有一个2因子b  包含4因子的数c
    //1.b==0   a==1,c>=1  a>1 c>=a-1
    //2.b!=0   a==0 c>=0  a>0  c>=a

    public static boolean _4Num(int [] arr){
        if (arr==null||arr.length==0){
            return false;
        }
        int a=0;
        int b=0;
        int c=0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i]&1)!=0){
                a++;
                continue;
            }
            if (arr[i]%2==0){
                int p1=arr[i]%2;
                if (arr[i]==2){
                    b++;
                    continue;
                }
                if (p1%2!=0){
                    b++;
                    continue;
                }
            }
            if (arr[i]%4==0){
                c++;
                continue;
            }
        }
        if (b==0){
            if (a==1&&c>=1){
                return true;
            }
            if (a>1&&c>=a-1){
                return true;
            }
        }else {
            if (a==0&&c>=0){
                return true;
            }

            if (a>0 &&c>=a){
                return true;
            }
        }
        return false;

    }


    //斐波那契数列式递归套路  改logN  F(n)=F(n-1)+F(n-2)
    //公式：|F(n),F(n-1)|=|F(2),F(1)|*{{1,1}{1,0}}^n-2
    //
    public static int fi(int n){
        if (n<1){
            return 0;
        }
        if (n==1){
            return 1;
        }
        if (n==2){
            return 2;
        }
        int [][]m={{1,1},{1,0}};
        int[][]res=matrixPower(m,n-2);
        return 2*res[0][0]+res[1][0];
    }


    //求{1,1}{1,0}^n-2
    public static int [][] matrixPower(int [][]m,int p){
        int[][] res=new int[m.length][m[0].length];

        for (int i = 0; i <res.length ; i++) {
            res[i][i]=1;
        }
        int [][]t={{1,1},{1,0}};
// p>>=1 是 p=p>>1
        for (; p !=0 ; p>>=1) {
            if ((p&1)==1){
                res=muliMatrix(res,t);
            }
            t=muliMatrix(t,t);
        }
return res;
    }

    //矩阵相乘
    //k是指针 一个行动 一个列动
    public static int [][] muliMatrix(int [][]p1,int [][]p2){
        int [][]res=new int[p1.length][p1[0].length];
        for (int i = 0; i <p1.length ; i++) {
            for (int j = 0; j <p2[0].length ; j++) {
                for (int k = 0; k <p2.length ; k++) {
                    res[i][j]+=p1[i][k]*p2[k][j];
                }
            }
        }
        return res;
    }

    //套路
    //牛生牛问题  3年可以生牛
    //问几年后有多少牛
    //去年的牛 加上3年前的牛的数量
    //F（N）=F（N-1）+F（N-3）
    //最大3阶

    //递归版本 o（n）
    public static int processCow(int n){
        if (n<1){
            return 0;
        }
        if (n==1){
            return 1;
        }
        if (n==2){
            return 2;
        }
        if (n==3){
            return 3;
        }
        int p1=processCow(n-1);
        int p2=processCow(n-3);
        return p1+p2;
    }

    //斐波那契数列套路版本
    //log（N）
    // 3X3 m={}
    //公式 ：|F（N）F（N-1）F（N-2）|=|F（3）F（2）F（1）|*m{}^n-2
    //和上面一样
    //未完成  m太难求了
    public static int cows(int n){
        if (n<1){
            return 0;
        }
        if (n==1){
            return 1;
        }
        if (n==2){
            return 2;
        }
        if (n==3){
            return 3;
        }

        return 0;


    }



    //宇符串只由‘0’和'1°两种宇符构成，
    //当字符串长度为1时，所有可能的字符串为"0"
    //"1":
    //当字符串长度为2时，所有可能的字符串为"00”
    //"01"
    //"10",
    //"11";
    //当字符串长度为3时，所有可能的宇符串为"000"，
    //001" "010", "011" "100"
    //"101”
    //’110"
    //"111"
    //如果某一个字符串中，只要是出现’0的位置，左边就靠着1°，这样的字符串叫作达
    //标字符串
    //给定一个正数N，返回所有长度为N的字符串中，达标字符串的数量。
    //比如，N=3，返回3，因为只有101”、
    //"110"
    //"111"达标。

    //暴力递归（不建议使用）打表用的
    public static int _01arr(int n){
        if (n<1){
            return 0;
        }
        char []arr=new char[n];
        return process01(n,arr,0);

    }
    public static int process01(int n,char [] arr,int index){
        if (index>n){
            return 0;
        }
        if (index==n){
            if (isOk(arr)){
                return 1;
            }
            return 0;
        }
        arr[index]='1';
        int p1=process01(n,arr,index+1);
        arr[index]='0';
        int p0=process01(n,arr,index+1);
        return p1+p0;
    }
    public static boolean isOk(char [] arr){
        int index=0;
        while (index<arr.length){
            if(arr[index]=='0'&&(index-1<0||arr[index-1]!='1')){
                 return false;
            }
            index++;
        }
        return true;
    }
    //打表可知  上面是斐波那契数列
    //F（N）=F（N-1）+F（N-2）


//在迷迷糊糊的大草原上，小红捡到了n根木棍，第;根木棍的长度为i，
//小红现在很开心。想选出其中的三根木棍组成美丽的三角形。
//但是小明想捉弄小红，想去掉一些木棍，使得小红任意选三根木棍都不能组成
//三角形.
//请问小明最少去掉多少根木棍呢？
//给定N，返回至少去掉多少根?
    //斐波那契数列  剩余斐波那契数列根  即时去掉最少根
    //当[i]==[i-1]+[i-2]  永远不可能成三角形
    public static int clubNum(int n) {
        if (n < 3) {
            return 0;
        }
        ArrayList <Integer> arr=new ArrayList<>();
        int res=processClub(n,1,arr);
       return res;

    }
    //index
    public static int processClub(int n,int index,ArrayList<Integer> arr){
        if (index>n){
            //判断是否合法
            int a=1;
            int left=a+1;
            int leftleft=left+1;
            while (a<=arr.size()-2){
                while (leftleft<=arr.size()){
                    if (isTriangle(arr.get(a-1),arr.get(left-1),arr.get(leftleft-1))){
                        return Integer.MAX_VALUE;
                    }
                    left++;
                    leftleft++;
                }
                a++;
                left=a+1;
                leftleft=left+1;

            }
            for (int i = 0; i <arr.size() ; i++) {
                System.out.print(arr.get(i));
            }
            System.out.println();

            return n-arr.size();
        }


        int no=processClub(n,index+1,arr);

        arr.add(index);
        int yes=processClub(n,index+1,arr);
        arr.remove((Integer) index);

        int res=Math.min(no,yes);
        return res;
    }
    public static boolean isTriangle(int a,int b,int c){
        if (a+b>c&&Math.abs(a-b)<c){
            return true;
        }
        return false;
    }




//牛牛准备参加学校组织的春游，出发前牛牛准备往背包里装入一些零食，牛牛的背包容
//量为w
//牛牛家里一共有n袋零食，第i袋零食体积为v[i]。
//牛牛想知道在总体积不超过背包容量的情况下，他一共有多少种零食放法(总体积为o也
//算一种放法）
    //背包问题



//为了找到自己满意的工作，牛牛收集了每种工作的难度和报酬。牛牛选工作的标准是在难度不超过自身能力
//牛牛的小伙伴们来找牛牛帮忙选工作，
//class Job
//pub lic int money ://该工作的报酬
//public int hard;//该工作的难度
//public Job (int money,
//int hard)
//this. money = monev:
//this. hard = hard:
//给定-
//一个Job类型的数组 jobarr， 表示所有的工作。给定一个int类型的数组arr，表示所有小伙伴的能力。
//返回int类型的数组，表示每一个小伙伴按照牛牛的标准选工作后所能获得的报酬
    //使用有序表 从小到大 排序好jobs  使用有序表保证工作难度 和金钱是成正比的

    public static class Job{
        public int hard;
        public int money;

        public Job(int hard, int money) {
            this.hard = hard;
            this.money = money;
        }
    }
    public static class JobComaptor implements Comparator<Job>{
        @Override
        public int compare(Job o1, Job o2) {
            return o1.hard!=o2.hard?(o1.hard-o2.hard):(o2.money-o1.money);
        }
    }
    public static int[] getMoneys(Job[] jobs,int [] ability){
        Arrays.sort(jobs,new JobComaptor());

        TreeMap<Integer, Integer> map=new TreeMap<>();
        map.put(jobs[0].hard,jobs[0].money);
        Job pre=jobs[0];
        for (int i = 1; i <jobs.length ; i++) {
            if (pre.hard!=jobs[i].hard&&pre.money<jobs[i].money){
                pre=jobs[i];
                map.put(jobs[i].hard,jobs[i].money);
            }
        }

        int [] newArray=new int[ability.length];
        for (int i = 0; i <ability.length ; i++) {
            Integer key=map.floorKey(ability[i]);
            newArray[i]=key!=null?map.get(key):0;
        }
        return newArray;
    }




    //str 变 int
    //str 肯定是合法的
    public static int strToInt(String str){
         char[] arr=str.toCharArray();
        boolean neg=arr[0]=='-'?true:false;
        int minq=Integer.MIN_VALUE/10;
        int minr=Integer.MIN_VALUE%10;
        int res=0;
        int cur=0;
        for (int i = neg?1:0; i <arr.length ; i++) {
            //改成负数 因为负数比正数多1
            cur='0'-arr[i];
            //防止溢出
            //举个例子就好懂了
            if ((res<minq)||(res==minq&&cur<minr)){
                throw new RuntimeException("bu neng zhuang");
            }
            res=res*10+cur;

        }
        //正数  且到系统负数最小值  那么正数必越界
        if (!neg&&res==Integer.MIN_VALUE){
            throw new RuntimeException("bu neng zhuang");
        }
        return neg?-res:res;
    }



    //前缀树
    //给你一个字符串类型的数组arr，譬如：
    //String D arr = 1
    //"b\\cst"
    //"d\\”
    //"a\\d\\e"
    //"a\\b\\e"
    //你把这些路径中蕴含的目录结构给画出来，子目录直接列在父目录下面，并比父目录
    //向右进两格，就像这样：
    //a
    //   b
    //     C
    //   d
    //     e
    //b
    //   cst
    //d
    //同一级的需要按字母顺序排列，不能乱。

    public static class TrieNode{
        String name;
        TreeMap<String,TrieNode> map;

        public TrieNode(String name) {
            this.name = name;
            this.map = new TreeMap<>();
        }
    }

    public static TrieNode generateFolderTree(String []folderPaths){
        TrieNode head=new TrieNode("");
        for (String folderPath:folderPaths){
            String []path=folderPath.split("\\\\");
            TrieNode cur=head;
            for (int i = 0; i <path.length ; i++) {
                if (!cur.map.containsKey(path[i])){
                    cur.map.put(path[i],new TrieNode(path[i]));
                }
                cur=cur.map.get(path[i]);
            }
        }
        return head;
    }

    //深度优先遍历
    public static void processPrint(TrieNode node,int level){
        if (node==null){
            return;
        }
        System.out.println(getSpace(level) + node.name);
        for (TrieNode next:node.map.values()){
            processPrint(next,level+1);
        }

    }

    public static String getSpace(int level){
        String s="";
        for (int i = 1; i <level ; i++) {
            s+=" ";
        }
        return s;
    }

    public static void start(String []folderPaths){
        if (folderPaths==null||folderPaths.length==0){
            return;
        }
        TrieNode head=generateFolderTree(folderPaths);
        processPrint(head,1);
    }

    //搜索二叉树转有序双向链表
    //二叉树套路
    public static class NodeD{
        NodeD left;
        NodeD right;
        int value;

        public NodeD(int value) {
            this.value = value;
            left=null;
            right=null;
        }
    }
    public static class Info1{
        NodeD start;
        NodeD end;

        public Info1(NodeD start, NodeD end) {
            this.start = start;
            this.end = end;
        }
    }
    public static NodeD treeToLink(NodeD head){
        if (head==null){
            return null;
        }
        Info1 info1=process(head);
        return info1.start;
    }
    public static Info1 process(NodeD node){
        if (node==null){
            return null;
        }
        Info1 left=process(node.left);
        Info1 right=process(node.right);

        if(left!=null){
            left.end.right=node;
            node.left=left.end;
        }
        if (right!=null){
            right.start.left=node;
            node.right=right.start;
        }
        return new Info1(left==null?node:left.start,right==null?node:right.end);
    }


    //二叉树中 最大搜索二叉树节点个数
    public static class Info2{
        boolean isBST;
        int ans;
        int max;
        int min;
        public Info2(boolean isBST,int ans, int max, int min) {
            this.isBST = isBST;
            this.ans = ans;
            this.max = max;
            this.min = min;
        }
    }
    public static int numMaxBSTNode(NodeD head){
        if (head==null){
            return 0;
        }
        Info2 res=proceessBST(head);
        return res.ans;
    }
    public static Info2 proceessBST(NodeD node){
        if (node==null){
            return new Info2(true,0,Integer.MIN_VALUE,Integer.MAX_VALUE);
        }
        Info2 left=proceessBST(node.left);
        Info2 right=proceessBST(node.right);

        if (left.isBST&&right.isBST&&(left.max<node.value&&right.min>node.value)){
            return new Info2(true,left.ans+right.ans+1,
                    Math.max(node.value,Math.max(left.max,right.max)),
                    Math.min(node.value,Math.min(left.min,right.min)));
        }else {
            return new Info2(false,Math.max(left.ans,right.ans),Integer.MIN_VALUE,Integer.MIN_VALUE);
        }
    }


    //为了保证招聘信息的质量问题，公司为每个职位设计了打分系统，打分可以为正数，也
    //可以为负数，正数表示用户认可帖子质量，负数表示用户不认可帖子质量．打分的分数
    //根据评价用户的等级大小不定，比如可以为 ：1分，10分，
    //30分，
    //-10分等。假设数组A
    //记录了一条站子所有打分记录，现在需要找出帖子曾经得到过最高的分数是多少，
    //后续根据最高分数来确认需要对发帖用户做相应的惩罚或奖勋．其中，
    //最高分的定义为：
    //用户所有打分记录中，
    //连续打分数据之和的最大值即认为是贴子曾经获得的最高分。例
    //如：站子10001010近期的打
    //分记录为[1,1,-1,-10,11,4,-6,9,20.-10,-2]那么该条帖子曾经到达过的最高分数为
    //11+4+（-6)+9+20=38。请实现一段代码，输入为帖子近期的打分记录，输出为当前帖子
    //得到的最高分数。

    //先假设答案再分析
    //累加和数组最长的
    //max 有大变大  cur 为负数变0
    public static int sumMax(int [] arr){
        if (arr==null||arr.length==0){
            return 0;
        }
        int cur=0;
        int max=Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            cur+=arr[i];
            max=Math.max(max,cur);
            if (cur<0){
                cur=0;
            }
        }
        
        return max;
    }

    //给定一个整型矩阵  返回子矩阵的最大累加和
    public static int sumMirtixMax(int [][]mix){
        if (mix==null||mix.length==0){
            return 0;
        }
        int max=Integer.MAX_VALUE;
        int cur=0;
        int []s=null;
        for (int i = 0; i <mix.length ; i++) {
            s=new int[mix[i].length];
            for (int j = i; j <mix[i].length ; j++) {
                cur=0;
                for (int k = 0; k <s.length ; k++) {
                    s[k]+=mix[j][k];
                    cur+=s[k];
                    max=Math.max(max,cur);
                    cur=cur<0?0:cur;
                }
            }

        }
        return max;

    }

    //s中只有'.'或者‘X’两种符号
    //路灯可以点亮左中右3个位置
    //至少需要多少灯，把‘.’点亮
    //贪心：
    //从左到右 位置i
    //1."X" 不点 跳到i+1
    //2."." 看i+1位置
    //如果i+1位置是“.” 点i+1位置  跳到i+3位置
    //如果i+1位置是“X” 点i位置 跳到i+2位置


//己知一棵二叉树中没有重复节点，并且给定了这棵树的中序遍历数组和先序遍历
//数组，返回后序遍历数组
//比如给定：
//intl pre
//=1.2.4 5. 3.6.7};
//intl in =(4 2 5 1 6. 3.7):
//返回：
//(4, 5, 2, 6, 7, 3, 1}

    //先序中左右
    //中序是左中右
    //后序是左右中

    //通过先序找到中 在递归分界中序  左 中 右
    // 放中  再将左放左  再放右
    public static int [] preAndInToPos(int []pre ,int []in){
        if (pre==null||in==null||pre.length==0||in.length==0){
            return null;
        }
        int pos[]=new int[pre.length];
        process(pre,0,pre.length-1,in,0,in.length-1,pos,0,pos.length-1);
        return pos;
    }


    public static void  process(int []pre,int preS,int preE,int []in,int inS,int inE,int[]pos,int posS,int posE ){

        if(preS>preE){
            return ;
        }
        if (preS==preE){
            pos[posS]=pre[preS];
            return;
        }
        //放中
        pos[posE]=pre[preS];
            int preMid=pre[preS];
            int inMidPlace=0;
        for (int i = inS; i <=inE ; i++) {
            if (in[i]==preMid){
                inMidPlace=i;
                break;
            }
        }
        //放左
        process(pre,preS+1,preS+(inMidPlace-inS),in,inS,inMidPlace-1,pos,posS,posS+(inMidPlace-inS)-1);
       //放右
        process(pre,preS+(inMidPlace-inS)+1,preE,in,inMidPlace+1,inE,pos,posS+(inMidPlace-inS),posE-1);

    }



    //把一个数字用中文表示出来。数字范围为 [0, 99999]。
    //为了方便输出，使用字母替换相应的中文，万W 千。 百8十S 零。使用数字取代中
    //文数字注：对于 11应该表示为
    //-十一（1$1），而不是十-
    //- ($1)
    //输入描述：
    //数字。（包含）到 99999（包含）
    //输出描述：
    //用字母替换相应的中文，万W干。百B十S零
    //示例1：
    //输入
    //12001
    //1W2QL1

    //从小的开始算  如 1到10  1到100  1到1000
    public static void oneToNine(int num){

    }
    //完全二叉树个数
    //非遍历方法
    //复杂度是o(logN^2)
    public static int treeCompete(NodeD head){
        if (head==null){
            return 0;
        }
        int res=process(treeCompHight(head),head);
        return res;
    }
    public static int process(int h,NodeD node){
        if (node==null){
            return 0;
        }
        int rightH=treeCompHight(node.right);
        int res=0;
        if (rightH+1==h){
            res=process(rightH,node.right);
            int n=h-1;
            return (1<<n)+res;
        }else {
            res=process(h-1,node.left);
            return (1<<rightH)+res;
        }
    }
    public static int treeCompHight(NodeD node){
        int height=0;
        NodeD next=node;
        while (next!=null){
            height++;
            next=next.left;
        }
        return height;
    }

    //判断完全二叉树递归
    public static boolean isCompleteTree(NodeD head){
        if (head==null){
            return false;
        }
        int h=treeHigh(head);
        InfoComplete res=process(h,1,head);
        return res.isOk;

    }
    public static class InfoComplete{
        boolean isOk;
        boolean haveLeft;
        int hight;

        public InfoComplete(boolean isOk, boolean haveLeft, int hight) {
            this.isOk = isOk;
            this.haveLeft = haveLeft;
            this.hight = hight;
        }
    }
    public static InfoComplete process(int h,int level,NodeD node){
        if ((node.left==null&&node.right!=null)||(level!=h-1&&node.left!=null&&node.right==null)
                ||(level!=h-1&&node.left==null&&node.right==null)){
            return new InfoComplete(false,false,0);
        }

        if (level==h-1){
            if (node.left!=null&&node.right==null){
                return new InfoComplete(true,true,h);
            }
            if (node.left!=null&&node.right!=null){
                return  new InfoComplete(true,false,h);
            }
            if (node.left==null&&node.right==null){
                return new InfoComplete(true,false,h-1);
            }
        }
       InfoComplete left=process(h,level+1,node.left);
        InfoComplete right=process(h,level+1,node.right);

        if (left.isOk&&right.isOk) {
            if (!left.haveLeft && !right.haveLeft && left.hight > right.hight) {
                return new InfoComplete(true, false, left.hight);
            }
        }
        return new InfoComplete(false,false,0);
    }
    public static int treeHigh(NodeD head){
        if (head==null){
            return 0;
        }
        int left=treeHigh(head.left);
        int right=treeHigh(head.right);

        return Math.max(left,right)+1;
    }


    //最长递增子序列数n^2

    static int maxSum=0;
    public static int lengthOfLIS(int[] nums){
        for (int i = 0; i <nums.length ; i++) {
            processLengthLIS(nums,i);
        }
        return maxSum;

    }
    public static int processLengthLIS(int []nums,int index){
        if (index==0){
            maxSum=Math.max(maxSum,1);
            return 1;
        }
        int res=1;
        for (int i = 0; i <=index ; i++) {
            if (nums[index]>nums[i]){
                res=Math.max(processLengthLIS(nums,i)+1,res);
            }
        }
        maxSum=Math.max(maxSum,res);
        return res;
    }

    public static int lengthOfLISDp(int[] nums){
        int []dp=new int[nums.length];
        dp[0]=1;
        int max=1;
        for (int i = 1; i <nums.length ; i++) {
            int res=1;
            for (int j = 0; j <=i-1 ; j++) {
                if (nums[i]>nums[j]){
                    res=Math.max(dp[j]+1,res);
                }
            }
            dp[i]=res;
            max=Math.max(dp[i],max);
        }
return max;
    }

    //最长递增子序列数（logn *n）
    public static int increaseNumEasy(int []nums){
        if (nums==null||nums.length==0){
            return 0;
        }
        ArrayList<Integer> end=new ArrayList<>();
        int max=0;
        end.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i]>end.get(end.size()-1)){
                end.add(nums[i]);
            }else {
                //找到代替这个位置的最小数
                int index=binarySearchAndChange(end,nums[i]);
                end.set(index,nums[i]);
            }
        }
        
        return end.size();
    }
    //大于等于n的最左位置
    public static int binarySearchAndChange(ArrayList<Integer> arr,int n){
        int left=0;
        int right=arr.size()-1;
       int mid=0;
       int index=-1;
        if (arr==null|| arr.size()==0){
            return index;
        }
        while (left<=right){
            mid=(left+right)/2;
            if (arr.get(mid)>=n){
                right=mid-1;
                index=mid;
            }else {
                left=mid+1;
            }
        }
        return index;
    }


    //给定一个整数数组A，长度为n，有1<=A[i]<=n，且对于[1,n]的整数，其
    //中部分整数会重复出现而部分不会出现。
    //实现算法找到[1,n]中所有未出现在A中的整数。
    //提示：尝试实现0(n) 的时间复杂度和0(1)的空间复杂度（返回值不计入空间复
    //杂度）
    //输入描述：
    //一行数宇，全部为整数，空格分隔
    //A0 A1 A2 A3.
    //输出描述：
    //一行数宇，全部为整数，空格分隔RO R1 R2 R3.
    //示例1：
    //输入
    //1343
    //输出
    //2

    public static void lostNum(int [] arr){
        if (arr==null||arr.length==0){
            return ;
        }
        for (int i = 0; i <arr.length ; i++) {
            int n=arr[i];
                while (arr[n-1]!=n){
                    int t=arr[n-1];
                    arr[n-1]=n;
                    n=t;
                }
        }
        for (int i = 0; i <arr.length ; i++) {
            if (arr[i]!=i+1){
                System.out.println(i+1);
            }
        }
        for (int i = 0; i <arr.length ; i++) {
            System.out.print(arr[i]);
        }

    }

    //CC里面有一个土豪很喜欢一位女直播Kiki唱歌，平时就经常给她点赞、送礼、私聊。最近GC直播平台在举行
    //中秋之星主播唱歌比赛，假设一开始该女主播的初始人气值为start， 能够晋升下一轮人气需要刚好达到end,
    //士豪给主播增加人气的可以采取的方法有：
    //点赞 花费x币，人气+2
    //b
    //送礼 花费y C币，
    //人气*2
    //私聊 花费zC币，人气-2
    //其中 end 远大于start且end为偶数
    //请写一个程序帮士豪计算一下，最少花费多少C币就能帮助该主播
    //xiki将人气刚好达到end， 从而能够晋级下一轮？
    //输入描述：
    //第一行输入5个数据，分别为：x y Z start end，每项数据以空格分开。
    //其中：0<x.y.z<=10000，
    //O<start, end<=1000000
    //输出描述：
    //需要花费的最少C币。
    //示例1：
    //输入
    //3 100 1 2 6
    //输出 6
    public static int coinNum(int x,int y,int z,int start ,int end){
        return process(x,y,z,start,end);

    }

    public static int process(int x,int y,int z,int cur,int end){
        if (cur==end){
            return 0;
        }
        if (cur>end){
            int t=cur-end;
            if ((t&1)==0){
                return (t/2)*z;
            }else {
                return Integer.MAX_VALUE-2000;
            }
        }
        int p1=process(x,y,z,cur+2,end)+x;
        int p2=process(x,y,z,cur*2,end)+y;
        return Math.min(p1,p2);
    }

    public static int coinNumDp(int x,int y,int z,int start ,int end){

        int []dp=new int[2*end];
        for (int cur = 2*(end-1); cur >=start; cur--) {
            if (cur==end){
                dp[cur]=0;
                continue;
            }
            if (cur>end){
                int t=cur-end;
                if ((t&1)==0){
                   dp[cur]=(t/2)*z;
                   continue;
                }else {
                    dp[cur]=Integer.MAX_VALUE-2000;
                    continue;
                }
            }
            int p1=dp[cur+2]+x;
            int p2=dp[cur*2]+y;
            dp[cur]=Math.min(p1,p2);
        }
return dp[start];

    }

    //CC直播的题


    //给定一个只由 0（假)、1（真)、＆(逻辑与）|(逻辑或)和^(异或）五种字符组成
    //的字符串express，再给定一个布尔值 desired。 返回express能有多少种组合
    //方式，
    //可以达到desired的结果。
    //【举例】
    //express="1^0|0|1". desired=false
    //只有 1^((0|0)|1)和 1^(0|(0|1)）的组合可以得到 false， 返回2。
    //express="1", desired=false
    //无组合则可以得到false，返回0


    //默认合法

    public static int desired(String str,boolean des){
        return process(str.toCharArray(),0,str.length()-1,des);
    }
    public static int process(char[] arr,int l,int r,boolean des){
        if (l==r){
            if (arr[l]=='1'){
                return des?1:0;
            }else {
                return des?0:1;
            }
        }

        int res=0;
        if (des){
            for (int i = l+1; i <=r-1 ; i=i+2) {
                if (arr[i]=='&'){
                    res+=process(arr,l,i-1,true)*process(arr,i+1,r,true);
                }
                if (arr[i]=='|'){
                    res+=process(arr,l,i-1,true)*process(arr,i+1,r,true);
                    res+=process(arr,l,i-1,true)*process(arr,i+1,r,false);
                    res+=process(arr,l,i-1,false)*process(arr,i+1,r,true);
                }
                if(arr[i]=='^'){
                    res+=process(arr,l,i-1,true)*process(arr,i+1,r,false);
                    res+=process(arr,l,i-1,false)*process(arr,i+1,r,true);
                }
            }
        }else {
            for (int i = l+1; i <=r-1 ; i=i+2) {
                if (arr[i]=='&'){
                    res+=process(arr,l,i-1,true)*process(arr,i+1,r,false);
                    res+=process(arr,l,i-1,false)*process(arr,i+1,r,false);
                    res+=process(arr,l,i-1,false)*process(arr,i+1,r,true);

                }
                if (arr[i]=='|'){
                    res+=process(arr,l,i-1,false)*process(arr,i+1,r,false);
                }
                if(arr[i]=='^'){
                    res+=process(arr,l,i-1,false)*process(arr,i+1,r,false);
                    res+=process(arr,l,i-1,true)*process(arr,i+1,r,true);
                }
            }

        }
        return res;
    }

    public static int desiredDp(String str,boolean des){
        int n=str.length();
        int [][]tmap=new int[n][n];
        int [][]fmap=new int[n][n];
        char []arr=str.toCharArray();
        for (int i = 0; i < n; i=i+2) {
            if (arr[i]=='1'){
                tmap[i][i]=1;
                fmap[i][i]=0;
            }else {
                tmap[i][i]=0;
                fmap[i][i]=1;
            }
        }

        for (int l = arr.length-3; l >=0 ; l=l-2) {
            for (int r = l + 2; r < arr.length; r = r + 2) {
                for (int i = l + 1; i <= r - 1; i = i + 2) {
                    if (arr[i] == '&') {
                        tmap[l][r] += tmap[l][i - 1] * tmap[i + 1][r];

                        fmap[l][r] += tmap[l][i - 1] * fmap[i + 1][r];
                        fmap[l][r] += fmap[l][i - 1] * fmap[i + 1][r];
                        fmap[l][r] += fmap[l][i - 1] * tmap[i + 1][r];

                    }
                    if (arr[i] == '|') {
                        tmap[l][r] += tmap[l][i - 1] * tmap[i + 1][r];
                        tmap[l][r] += tmap[l][i - 1] * fmap[i + 1][r];
                        tmap[l][r] += fmap[l][i - 1] * tmap[i + 1][r];

                        fmap[l][r] += fmap[l][i - 1] * fmap[i + 1][r];

                    }
                    if (arr[i] == '^') {
                        tmap[l][r] += tmap[l][i - 1] * fmap[i + 1][r];
                        tmap[l][r] += fmap[l][i - 1] * tmap[i + 1][r];

                        fmap[l][r] += fmap[l][i - 1] * fmap[i + 1][r];
                        fmap[l][r] += tmap[l][i - 1] * tmap[i + 1][r];
                    }
                }
            }
        }
        return des?tmap[0][n-1]:fmap[0][n-1];

    }

    //在一个字符串中找到没有重复字符子串中最长的长度。
    //例奶：
    //abcabcbb没有重复宇符的最长子串是abc，长度为3
    //bbbbb，答案是b，长度为1
    //pwwkeW,
    //答案是wke，长度是3
    //要求：答案必须是子串，
    //"pwke〞是一个子字符序列但不是一个子字符串
    public static int maxUnique(String str){
        if (str==null||str.length()==0){
            return 0;
        }
        char[] arr=str.toCharArray();
        HashMap<Character, Integer> map=new HashMap<>();
        int cur=0;
        int max=0;
        for (int i = 0; i <arr.length ; i++) {
            cur=map.containsKey(arr[i])?i-map.get(arr[i]): cur+1;
            max=Math.max(max,cur);
            map.put(arr[i],i);
        }
        return max;
    }

//给定两个字符串str1和str2，再给定三个整数ic、dc和rc，分别代表插入、删
//除和替换一个字符的代价，返回将str1编辑成str2的最小代价。
//【举例】
//str1="'abc"
//str2="ada", ic=5, dc=3， rc=2
//从”abc"编辑成"adc"
//把'b’替换成'd“是代价最小的，所以返回2
//str1="abc"
//str2="adc'
//ic=5, dc=3,
//rc=100
//从"abc"编辑成"adc"，先删除’b’，然后插入’d’是代价最小的，所以返回8
//str1="abc"
//str2="abc"
//ic=5.
//dc=3,
//rc=2
//不用编辑了，本来就是一样的字符串，所以返回o
    //默认合法
   public static int payForStr(String str1,String str2,int ic,int dc,int rc){
        if (str1==null||str2==null||str1.length()==0||str2.length()==0){
            return 0;
        }
        char[] arr1=str1.toCharArray();
        char[] arr2=str2.toCharArray();
        return processPay(arr1,arr2,str1.length(),str2.length(),ic,dc,rc);

}

//arr1 改arr2
    public static int processPay(char [] arr1,char [] arr2,int p1,int p2,int ic,int dc,int rc){
        if (p1==0&&p2==0){
            return 0;
        }
        if (p1==0){
            return ic;
        }
        if (p2==0){
            return dc;
        }

        int res=0;
        for (int i = 1; i <=p1 ; i++) {
            if (arr1[p2-1]==arr2[i-1]){
                res=processPay(arr1,arr2,i-1,p2-1,ic,dc,rc);
            }else {
                res=Math.min(Math.min(processPay(arr1,arr2,i-1,p2-1,ic,dc,rc)+rc,processPay(arr1,arr2,i,p2-1,ic,dc,rc)+ic),
                        processPay(arr1,arr2,i-1,p2,ic,dc,rc)+dc);
            }
        }

        return res;
    }

    public static int payForStrDp(String str1,String str2,int ic,int dc,int rc){
        if (str1==null||str2==null||str1.length()==0||str2.length()==0){
            return 0;
        }
        char[] arr1=str1.toCharArray();
        char[] arr2=str2.toCharArray();
        int n=str1.length();
        int dp[][]=new int[n+1][n+1];
        for (int i = 1; i <=n ; i++) {
            dp[i][0]=ic;
            dp[0][i]=dc;
        }
        for (int i = 1; i <=n ; i++) {
            for (int j = 1; j <=n ; j++) {
                if (arr1[i-1]==arr2[j-1]){
                    dp[i][j]=dp[i-1][j-1];
                }else {
                    dp[i][j]=Math.min(Math.min(dp[i-1][j-1]+rc,dp[i-1][j]+ic),dp[i][j-1]+dc);
                }
            }
        }
        return dp[n][n];
    }
    





    



//str1 str2  求最长公共子序列
    public static int subsequenNum(String str1,String str2){
        if (str1==null||str2==null||str1.length()==0||str2.length()==0){
            return 0;
        }
        char[] arr1=str1.toCharArray();
        char []arr2=str2.toCharArray();
        return process(arr1,arr2,str1.length(),str2.length());
    }
    //path指的是多长
    public static int process(char []arr1,char[] arr2,int path1,int path2){
        if (path1==0||path2==0){
            return 0;
        }
        int res=0;
        for (int i = 1; i <=path2; i++) {
            if (arr1[path1-1]==arr2[i-1]){
                res=process(arr1,arr2,path1-1,i-1)+1;
            }else {
                res=Math.max(process(arr1,arr2,path1-1,i),process(arr1,arr2,path1,i-1));
            }
        }
        return res;
    }
    //未完成
    public static int LCSdp(String str1,String str2){
        if (str1==null||str2==null||str1.length()==0||str2.length()==0){
            return 0;
        }
        char[] arr1=str1.toCharArray();
        char []arr2=str2.toCharArray();
        int n1=str1.length();
        int n2=str2.length();
        return 0;
    }


//给定一个全是小写字母的字符串str，删除多余字符，使得每种字符只保留一个，并让
//最终结果字符串的字典序最小
//【举例】
//str
//"acbc"
//，删掉第一个，
//得到"abc"
//，是所有结果字符串中字典序最小的。
//str
//=
//"'dbcacbca"
//，删掉第一个b
//、笑迎
//c
//、第二个'c’、第二个'a'，得到"dabc"
//是所有结 果字符串中字典序最小的。

    public static String deleteStr(String str){
        if (str==null||str.length()<1){
            return "";
        }

        int []map=new int[256];
        for (int i = 0; i <str.length() ; i++) {
            map[str.charAt(i)]++;
        }
        int minIndex=0;
        for (int i = 0; i <str.length() ; i++) {
            if (--map[str.charAt(i)]==0){
                break;
            }else {
                minIndex=str.charAt(minIndex)>str.charAt(i)?i:minIndex;
            }
        }

        return String.valueOf(str.charAt(minIndex))+ deleteStr(
                str.substring(minIndex+1).replaceAll(String.valueOf(str.charAt(minIndex)),"")
        );
    }


    //最小字典序
    public static String samllStr(String s){
        if (s.length()<2){
            return s;
        }
        int left=0;
        int right=s.length()-1;
        boolean leftFlag=false;

        while (left<=right){
          if (s.charAt(left)==s.charAt(right)){
              left++;
              right--;
          }
          if (s.charAt(left)<s.charAt(right)){
              leftFlag=true;
              break;
          }else {
              break;
          }
        }
        if (leftFlag){
            return String.valueOf(s.charAt(0))+samllStr(s.substring(1));
        }else {
            return String.valueOf(s.charAt(s.length()-1))+samllStr(s.substring(0,s.length()-1));
        }

    }

    //在数据加密和数据压缩中常需要对特殊的字符串进行编码。给定的字母表A由26个小写英文字母组成，即
    //A=[a，b.z]。该字母表产生的长序宇符串是指定宇符串中字母从左到右出现的次序与字母在字母表中出现
    //的次序相同，
    //且每个字符最多出现1次。例如，
    //a，b，ab，bc，xyz等字符串是升序字符串。对字母表A产生
    //的所有长度不超过6的升序字符串按照字典排列编码如下： a(1)，b (2)，
    //。(3•
    //ac(28)•…对于任意长度不超过16的升序字符串，迅速计算出它在上述字典中的编码。
    //2(26), ab (27)，
    //输入描述：
    //第1行是一个正整数N，表示接下来共有N行，在接下来的N行中，每行给出一个字符串。输出描述：
    //输出N行，每行对应于一个字符串编码。
    //示例1：
    //输入
    //3
    //b
    //ab
    //输出
    //1
    //2
    //27

    //第i字符开头  长度为len的子序列多少个
    //z开头 长度为6 返回0
    public static int g(int i,int len){
        int sum=0;
        if (len==1){
            return 1;
        }
        for (int j = i+1; j <=26 ; j++) {
            sum+=g(j,len-1);
        }
        return sum;
    }

    //长度为len的字符串多少
    public static int f(int len){
        int sum=0;
        for (int i = 1; i <=26 ; i++) {
            sum+=g(i,len);
        }
        return sum;
    }

    //s默认合法
    public static int kth(String s){
        char []arr=s.toCharArray();
        int n=arr.length;
        int sum=0;
        for (int i = 1; i <n ; i++) {
            sum+=f(i);
        }
        int first=arr[0]-'a'+1;
        //解决当前行n的数
        for (int i = 1; i <first ; i++) {
            sum+=g(i,n);
        }
        int pre=first;
        for (int i =1; i <n ; i++) {
            int cur=arr[i]-'a'+1;
            for (int j = pre+1; j <cur ; j++) {
                sum+=g(j,n-i);
            }
            pre=cur;

        }

        return sum+1;


    }


    //吃血条问题 士兵初始血条小 至少为1血
    //求[i][j]到最后一格初始血多少
    public static int booled(int [][] matrix){
        if (matrix==null||matrix.length==0){
            return -1;
        }
        int res=processBooled(matrix,0,0);
        return res;
    }

    public static int processBooled(int [][]m,int right,int down){
        if (down==m.length-1&&right==m[0].length-1){
            return m[down][right]<0?-m[down][right]+1:1;
        }
        if (down==m.length||right==m[0].length){
            return Integer.MAX_VALUE;
        }
        int p1=processBooled(m,right+1,down);
        int p2=processBooled(m,right,down+1);
        int pay=Math.min(p1,p2);
        return m[down][right]<0?(-m[down][right]+1)+(pay-1):pay>m[down][right]?pay-m[down][right]:1;
    }

    //上面改dp很简单不做了

    //区间合并
    public static class Interval{
        public int start;
        public int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    //默认合法
    public static List<Interval>insert(List<Interval> items,Interval newItem){
        if (items==null||newItem==null){
            return null;
        }
        List<Interval> res=new ArrayList<>();
        int i=0;
        while (i<items.size()&&items.get(i).end<newItem.start){
            res.add(items.get(i++));
        }
        while (i<items.size()&&items.get(i).start<=newItem.end){
            newItem.start=Math.min(items.get(i).start,newItem.start);
            newItem.end=Math.max(items.get(i).end,newItem.end);
            i++;
        }
        res.add(newItem);
        while (i<items.size()){
            res.add(items.get(i++));
        }
        return res;
    }


    //数组[1,2,4,5]  右上左下 循环  会不会撞上


    //二叉树递归最大值
    //任意点
    public static class InfoMax{
        int max;
        int headmax;

        public InfoMax(int max, int headmax) {
            this.max = max;
            this.headmax = headmax;
        }
    }
    public static int maxNum(NodeD head){
        if(head==null){
            return Integer.MIN_VALUE;
        }
        return processMaxNum(head).max;
    }

    public static InfoMax processMaxNum(NodeD node){
        if(node==null){
            return new InfoMax(Integer.MIN_VALUE,Integer.MIN_VALUE);
        }
        InfoMax p1=processMaxNum(node.left);
        InfoMax p2=processMaxNum(node.right);
        int max= Math.max(p1.max,p2.max);

        int leftheadMax=p1.headmax==Integer.MIN_VALUE?node.value:p1.headmax+node.value;
        int rightheadMax=p2.headmax==Integer.MIN_VALUE?node.value:p2.headmax+node.value;

        int headmax=Math.max(leftheadMax,rightheadMax);
        max=Math.max(headmax,max);
        return new InfoMax(max,headmax);
    }

    //某一字符串 str
    //arr是去重单词表  不是空串 且任意使用次数
    //拼接成str 返回方法数

    public static int getStr(String []ss,String s){
        if (s==null||s.length()==0){
            return 0;
        }
        return process(ss,s,new ArrayList<String>());
    }

    public static int process(String []ss, String s, ArrayList<String> arr){
        String path="";
        for (int i = 0; i <arr.size() ; i++) {
            path+=arr.get(i);
        }

        if (path.equals(s)){
//            for (int i = 0; i <arr.size() ; i++) {
//                System.out.print(" "+arr.get(i));
//            }
//            System.out.println();
            return 1;
        }
        if (path.length()>=s.length()&&!path.equals(s)){
            return 0;
        }
        int res=0;
        for (int i = 0; i <ss.length ; i++) {
            arr.add(ss[i]);
            res+=process(ss,s,arr);
            arr.remove(arr.size()-1);
        }
        return res;
    }


    public static int getStr2(String [] ss,String s){
        HashSet<String> set=new HashSet<>();
        for (int i = 0; i <ss.length ; i++) {
            set.add(ss[i]);
        }
        return f(s,set,0);
    }


    public static int f(String s,HashSet<String> set,int index){
        if (index==s.length()){
            return 1;
        }
        int res=0;
        for (int end = index; end <s.length() ; end++) {
            if (set.contains(s.substring(index,end+1))){
                res+=f(s,set,end+1);
            }
        }
        return res;
    }
    public static int getStr2Dp(String [] ss,String s){
        HashSet<String> set=new HashSet<>();
        for (int i = 0; i <ss.length ; i++) {
            set.add(ss[i]);
        }
        int []dp=new int[s.length()+1];
        //o(n^3)
        for (int i = s.length(); i >=0 ; i--) {
            if (i==s.length()){
                 dp[i]=1;
            }
            for (int end = i; end <s.length() ; end++) {
                //继续优化  单词表变前缀树（没完成）
                if (set.contains(s.substring(i,end+1))){
                    dp[i]+=dp[end+1];
                }
            }
        }
        return dp[0];
    }

//继续优化  单词表变前缀树




    //给定一个数组，求如果排序之后，相邻两数的最大差值。要求时间复杂度0(N)，且要
    //求不能用基于比较的排序（桶排）




    //给出一个数组 划分区间 最多使得每个区间的数字异或和为0

    public static int xorMin(int []arr){
        if (arr==null||arr.length==0){
            return 0;
        }
        return processXor(arr,0,0);
    }
    public static int processXor(int []arr,int start,int end){
        if (end==arr.length-1){
            if (arr[end]==0){
                return 1;
            }else {
                int endRes=0;
                for (int i = start; i <=end ; i++) {
                    endRes^=arr[start];
                }
                return endRes==0?1:0;
            }
        }
        int res=0;
        if (arr[end]==0){
            res+=processXor(arr,end+1,end+1)+1;
        }else {
            int endRes=0;
            for (int i = start; i <=end ; i++) {
                endRes^=arr[i];
            }
            res+=endRes==0?processXor(arr,end+1,end+1)+1:processXor(arr,start,end+1);
        }
        return res;
    }
    public static int xorMinDp(int []arr){
        if (arr==null||arr.length==0){
            return 0;
        }
        int N=arr.length;
        int [][]dp=new int[N][N];
        for (int start = arr.length-1; start >=0 ; start--) {
            for (int end = arr.length-1; end >=0; end--) {
                if (start>end){
                    continue;
                }

                if (end==arr.length-1){
                    if (arr[end]==0){
                        dp[start][end]=1;
                        continue;
                    }else {
                        int endRes=0;
                        for (int i = start; i <=end ; i++) {
                            endRes^=arr[start];
                        }
                        dp[start][end]=endRes==0?1:0;
                        continue;
                    }
                }
                if (arr[end]==0){
                    dp[start][end]+=dp[end+1][end+1]+1;
                }else {
                    int endRes=0;
                    for (int i = start; i <=end ; i++) {
                        endRes^=arr[i];
                    }
                    dp[start][end]+=endRes==0? dp[end+1][end+1]+1:dp[start][end+1];
                }

            }
        }
        return dp[0][0];

    }

    //优化： start改成一个前面异或的变量存储异或结果tmp
    public static int xorMinDpEasy(int []arr){
        if (arr==null||arr.length==0){
            return 0;
        }
        int N=arr.length;
        int []dp=new int[N];
        int tmp=0;
        dp[N-1]=arr[N-1]==0?1:0;
            for (int end = arr.length-2; end >=0; end--) {
                if (arr[end]==0){
                    dp[end]+=dp[end+1]+1;
                    tmp=0;
                }else {
                    int endRes=tmp^arr[end];
                    dp[end]+=endRes==0? dp[end+1]+1:dp[end+1];
                    tmp=endRes;
                }
            }
        return dp[0];

    }




    //给定一个路径数组 paths，表示一张图。paths [i]一j代表城市 ；连向城市j，如果
    //paths 【i]==i，则表示 ；城市是首都，
    //一张图里只会有一个首都且图中除首都指向自己之
    //外不会有环。
    //例如， paths=[9, 1,4,9,0,4,8,9,0,1】,
    //由数组表示的图可以知道，城市 1 是首都，所以距离为 0，离首都距离为 1 的城市只有城
    //市 9，离首都距离为 2的城市有城市 0、3和7.
    //离首都距离为3的城市有城市 4和8.
    //离首都 距离为 4 的城市有城市 2、5和6
    //所以距离为 0的城市有 1座，距离为 1的
    //城市有 1座，
    //距离 为 ？的城市有 3座，距离为 3的城市有2座
    //距离为
    //4 的城市有
    //3 座。那么统计数组为nums= [1.1,3.2.3.0.0.0.0.0]
    //nums [i]==j 代表距离为 ；的城市有
    //座。要求实现一个 void 类型的西 数，输入一个路径数组 paths
    //直接在原数组上调整，
    //使之变为nums 数组，即paths=[9.1.4,9.0.4.8.9.0. 11经过这个西效处理后变成
    //[1,1,3,2,3.0,0,0,0.0]。
    //【要求】
    //如果 paths 长度为 N，请达到时间复杂度为 。(N，额外空间复杂度为 0(1)


    //删除重复区间
    public static int removeCoveredIntervals(int [][]intervals){
        if (intervals==null||intervals.length==0){
            return 0;
        }

        Arrays.sort(intervals,(a,b)->(a[0]==b[0]?b[1]-a[1]:a[0]-b[0]));
        return intervals.length-processR(intervals,1,intervals[0][1]);

    }
    public static int processR(int [][]intervals,int index,int max){
        if (index==intervals.length){
            return 0;
        }
        int res=0;
        int t=max>=intervals[index][1]?1:0;
        max=Math.max(intervals[index][1],max);
        res=processR(intervals,index+1,max)+t;
        return res;
    }
    //改dp很简单








    //现有nl+n2种面值的硬币，其中前n1种为普通币，可以取任意枚．后n2种为纪念市
    //每种最多只能取一枚，每种硬币有一个面值，问能用多少种方法拼出m的面值？
    //0--n1-1
    public static int moneySum(int []arr,int n1,int n2,int m){
        if (arr==null||arr.length==0){
            return 0;
        }
        HashSet set=new HashSet();
        return processMoneySum(arr,n1,n2,m,0,set);
    }
    public static int processMoneySum(int []arr,int n1,int n2,int m,int res,HashSet<Integer> set){
        if (res>m){
            return 0;
        }
        if (res==m){
            return 1;
        }
        int ans=0;
        for (int i = 0; i <arr.length ; i++) {
            if (i>=n1){
                if (!set.contains(arr[i])){
                    set.add(arr[i]);
                    ans+=processMoneySum(arr,n1,n2,m,res+arr[i],set);
                    set.remove(arr[i]);
                }
                continue;
            }
            ans+=processMoneySum(arr,n1,n2,m,res+arr[i],set);

        }
        return ans;
    }


//给定两个一维int数组A和B.
//其中：A是长度为m、元素从小到大排好序的有序数组。B是长度为n、元素从小
//到大排好序的有序数组。希望从A和B数组中，找出最大的k个数宇，要求：使用
//尽量少的比较次数。

    //1.二分法
    public static int dichotomy(int []arr,int n){
        int left=0;
        int right=arr.length-1;
        int index=-1;
        while (left<=right){
            int mid=(right+left)>>1;
            if (arr[mid]>=n){
                right=mid-1;
                index=mid;
            }else {
                left=mid+1;
            }
        }
        return index;

    }
    public static int findNum(int []arr1,int []arr2,int k){
        if (arr1.length+arr2.length<k){
            return -1;
        }
        int f=1;
        int cur=-1;
        int i=arr1.length>>1;
        boolean small=true;
        while (cur != k) {
                if (f==1){
                    int other=dichotomy(arr2,arr1[i]);
                    if (other==-1){
                        cur=arr2.length+i+1;
                    }else {
                        cur=other+i+1;
                    }
                    if (cur>k){
                            if (i-1>=0){
                                i--;
                                f=1;
                                continue;
                            }
                        if (other==-1){
                            i=arr2.length-1;
                            f=2;
                            continue;
                        }
                            i=other;
                            f=2;
                            continue;

                    }else if (cur==k){
                        break;
                    }else {
                        if (i+1<arr1.length){
                            i++;
                            f=1;
                            int ex=dichotomy(arr2,arr1[i])+i+1;
                            if (ex>k){
                                i=other;
                                f=2;
                            }
                            continue;
                        }
                        i=other;
                        f=2;
                        continue;
                    }
                }
                if (f==2){
                    int other=dichotomy(arr1,arr2[i]);
                    if (other==-1){
                        cur=arr1.length+i+1;
                    }else {
                        cur=other+i+1;
                    }

                    if (cur>k){
                        if (i-1>=0){
                            i--;
                            f=2;
                            continue;
                        }
                        if (other==-1){
                            i=arr2.length-1;
                            f=1;
                            continue;
                        }
                            i=other;
                            f=1;
                    }else if (cur==k){
                        break;
                    }else {
                        if (i+1<arr2.length){
                            i++;
                            f=2;
                            int ex=dichotomy(arr1,arr2[i])+i+1;
                            if (ex>k){
                                i=other;
                                f=1;
                            }
                            continue;
                        }
                        i=other;
                        f=1;
                    }

                }
        }
        return f==1?arr1[i]:arr2[i];
    }

    //优化
    public static int findNumE(int []arr1,int []arr2,int k){
        if (arr1==null||arr2==null||k>arr1.length+arr2.length){
            return -1;
        }
        int n1=arr1.length;
        int n2=arr2.length;
        int []samller=n1>n2?arr2:arr1;
        int []bigger=n1>n2?arr1:arr2;
        int s=samller.length;
        int b=bigger.length;
        if (k<=s){
            int []newN1=new int[k];
            int []newN2=new int[k];
            for (int i = 0; i <k ; i++) {
                newN1[i]=arr1[i];
                newN2[i]=arr2[i];
            }
            return findNumMidUp(newN1,newN2);
        }
        if (s<k&&k<=b){
            int t=k-s-1;
            if (bigger[t]>samller[samller.length-1]){
                return bigger[t];
            }else {
                t++;
            }
            int []newS=new int[s];
            int []newB=new int[s];
            for (int i = 0; i <s ; i++) {
                newS[i]=samller[i];
            }
            ArrayList<Integer> tmp=new ArrayList<>();
            for (int i = t; i <t+s ; i++) {
                tmp.add(bigger[i]);
            }
            for (int i = 0; i <newB.length ; i++) {
                newB[i]=tmp.get(i);
            }
            return findNumMidUp(newS,newB);
        }
        if (k>b&&k<(s+b)){
            int t1=k-b-1;
            int t2=k-s-1;
            if (samller[t1]>bigger[b-1]){
                return samller[t1];
            }else {
                t1++;
            }
            if (bigger[t2]>samller[s-1]){
                return bigger[t2];
            }else {
                t2++;
            }
            int l=s-t1;
            int []newS=new int[l];
            int []newB=new int[l];
            ArrayList<Integer> tmp1=new ArrayList<>();
            for (int i = t1; i <s; i++) {
                tmp1.add(samller[i]);
            }
            for (int i = 0; i <l ; i++) {
                newS[i]=tmp1.get(i);
            }
            ArrayList<Integer>tmp2 =new ArrayList<>();
            for (int i = t2; i <t2+l ; i++) {
                tmp2.add(bigger[i]);
            }
            for (int i = 0; i <l ; i++) {
                newB[i]=tmp2.get(i);
            }
            return findNumMidUp(newS,newB);
        }
        return -1;
    }
    public static int findNumMidUp(int []arr1,int []arr2){
        if (arr1.length==1){
            return arr1[0]>arr2[0]?arr1[0]:arr2[0];
        }
        return processFind(arr1,arr2,0,arr1.length-1,0,arr2.length-1);
    }
    public static int processFind(int []arr1,int []arr2,int s1,int e1,int s2,int e2){
        if (s1==e1||s2==e2){
            return arr1[s1]<arr2[s2]?arr1[s1]:arr2[s2];
        }
        int m1=(e1-s1)/2+s1;
        int m2=(e2-s2)/2+s2;
        int l1=(e1-s1)+1;
        int res=0;
        if ((l1&1)==0){
            if (arr1[m1]==arr2[m2]){
                return arr1[m1];
            }else {
                if (arr1[m1]>arr2[m2]){
                    res=processFind(arr1,arr2,s1,m1,m2+1,e2);
                }else {
                    res=processFind(arr1,arr2,m1+1,e1,s2,m2);
                }
            }
        }else {
            if (arr1[m1]==arr2[m2]){
                return arr1[m1];
            }
            if (arr1[m1]>arr2[m2]){
                if (arr2[m2]<arr1[m1-1]){
                    res=processFind(arr1,arr2,s1,m1-1,m2+1,s2);
                }else {
                 return arr2[m2];
                }
            }else {
                if (arr1[m1]<arr2[m2-1]){
                    res=processFind(arr1,arr2,m1+1,e1,s2,m2-1);
                }else {
                    return arr2[m2];
                }
            }
        }
        return res;
    }



    //n 皇后问题
    public static int nQueen(int n){
        if (n<=3){
            return 0;
        }
        int [] m= new int[n];
        return processQueen(n,0,m);

    }
    public static int processQueen(int n,int index,int []m){
        if (index==n){
            return 1;
        }
        int res=0;
        for (int j = 0; j <n ; j++) {
            if (isOk(m,index,j)){
                m[index]=j;
                res+=processQueen(n,index+1,m);
            }
        }
        return res;
    }
    public static boolean isOk(int []m,int i,int j){
        boolean flag=true;
        for (int k = 0; k <i ; k++) {
            if (m[k]==j||Math.abs(i-k)==Math.abs(j-m[k])){
                return false;
            }
        }
        return true;
    }


//给出一个长度为n的整数数组numSlots和一个长度为2 * numSlots >= n的整数数组numSlots。numSlots的编号从1到numSlots。
//
//你必须把所有n个整数放入槽中，使每个槽最多包含两个数字。 给定位置的与和是每个数字与其各自槽号的位与和的和。
//
//例如，将数字[1,3]放入槽位1和[4,6]放入槽位2的AND和等于(1 AND 1) + (3 AND 1) + (4 AND 2) + (6 AND 2) = 1 + 1 + 0 + 2 = 4。
//返回numSlots的nums的最大值和总数。
    public static int maximumANDSum(int[] nums, int numSlots) {
        if (nums==null||nums.length==0||numSlots==0){
            return 0;
        }
        int []slots=new int [numSlots+1];
        return processMaxAND(nums,slots,0,numSlots);
    }
    public static int processMaxAND(int [] nums,int [] slots,int index,int slotNum){
        if (index==nums.length){
            return 0;
        }
        int max=0;
        for (int i = 1; i <=slotNum ; i++) {
            if (slots[i]<2){
                slots[i]++;
               max=Math.max(max,(nums[index]&i)+processMaxAND(nums,slots,index+1,slotNum));
               slots[i]--;
            }
        }
        return max;
    }

    //最慢的
    public static int maximumANDSumUpdate(int []nums ,int ns){
        if (nums==null||nums.length==0||ns==0){
            return 0;
        }
        int n=2*ns;
        return processMaximumANDSumUpdate(nums,n,0,0);
    }
    public static int processMaximumANDSumUpdate(int []nums,int ns,int mask,int index){
        if (index==nums.length){
            return 0;
        }
        int max=0;
        for (int i = 0; i <ns ; i++) {
            int slot=(i>>1)+1;
            if ((mask&(1<<i))==0){
                max=Math.max(max,(slot&nums[index])+processMaximumANDSumUpdate(nums,ns,mask^(1<<i),index+1));
            }
        }
        return max;
    }

    public static int maximumANDSumUpdateDp(int []nums ,int ns){
        if (nums==null||nums.length==0||ns==0){
            return 0;
        }
        Integer[] dp=new Integer[1<<(2*ns)];
        return processMaximumANDSumUpdateDp(nums,ns*2,0,0,dp);
    }

    //半dp
    public static int processMaximumANDSumUpdateDp(int []nums,int ns,int mask,int index,Integer []dp){
        if (index==nums.length){
            return 0;
        }
        if (dp[mask]!=null){
            return dp[mask];
        }
        int max=0;
        for (int i = 0; i <ns ; i++) {
            int slot=(i>>1)+1;
            if ((mask&(1<<i))==0){
                max=Math.max(max,(slot&nums[index])+processMaximumANDSumUpdateDp(nums,ns,mask^(1<<i),index+1,dp));
            }
        }
        return dp[mask]=max;
    }

    public static int maximumANDSumUpdateDp2(int []nums ,int ns) {
        if (nums == null || nums.length == 0 || ns == 0) {
            return 0;
        }
        int m=nums.length;
        int n=2*ns;
        int seleceted=1<<n;
       int [][]dp=new int[nums.length+1][seleceted];
        for (int index = m-1; index>=0; index--) {
            for (int mask = seleceted-1; mask >=0 ; mask--) {
                for (int i = 0; i <n ; i++) {
                    int slot=(i>>1)+1;
                    if ((mask&(1<<i))==0){
                        dp[index][mask]=Math.max(dp[index][mask],(slot&nums[index])+dp[index+1][mask^(1<<i)]);
                    }
                }
            }
        }
        return dp[0][0];

    }



//给定两个长度为n的整数数组nums1和nums2。
//
//两个整数数组的异或和为(nums1[0]异或nums2[0]) + (nums1[1]异或nums2[1]) +… + (nums1[n - 1] XOR nums2[n - 1])(0索引)。
//
//例如，[1,2,3]和[3,2,1]的XOR和等于(1 XOR 3) + (2 XOR 2) + (3 XOR 1) = 2 + 0 + 2 = 4。
//重新排列nums2中的元素，使结果的异或和最小。
//
//在重新排列后返回异或和。

    public static int minimumXORSum(int[] nums1, int[] nums2) {
        if (nums1==null||nums2==null||nums1.length==0){
            return 0;
        }
        int n1=nums1.length;
        int selected=1<<n1;
        Integer []dp=new Integer[selected];
        return processMinimumXORSum(nums1,nums2,0,0,dp);
    }

    public static int processMinimumXORSum(int []nums1,int []nums2,int mask,int index,Integer [] dp){
        if(index==nums1.length){
            return 0;
        }
        if (dp[mask]!=null){
            return dp[mask];
        }
        int min=Integer.MAX_VALUE;
        for (int i = 0; i <nums2.length ; i++) {
            if ((mask&(1<<i))==0){
                min=Math.min(min,(nums1[index]^nums2[i])+processMinimumXORSum(nums1,nums2,mask^(1<<i),index+1,dp));
            }
        }
        return dp[mask]=min;

    }
    //给定一个0索引的二进制字符串s，它表示一个火车车厢序列。 S [i] = '0'表示第i辆车不含非法货物，S [i] = '1'表示第i辆车不含非法货物。
    //
    //作为列车长，你希望清除所有载有非法货物的车厢。 你可以任意次数地执行以下三种操作:
    //
    //从左端移除一节火车车厢(即移除s[0])，这需要1个单位的时间。
    //从右端卸下火车车厢。 长度- 1])，需要1个单位的时间。
    //从序列中的任何地方移除一节火车车厢，这需要2个单位的时间。
    //退回所有载有非法货物的车辆的最短时间。
    //
    //请注意，一个空的汽车序列被认为没有载有非法货物的汽车。

    public static int minimumTime(String s) {
        if(s==null){
            return 0;
        }
        char[] path=s.toCharArray();
        return processMinimumTime(0,path,0);
    }


    public static int processMinimumTime(int index,char[] path,int size){
        if (index==path.length){
            return 0;
        }
        char cur=path[index];
        int min=0;
        if (cur=='0'){
            min=processMinimumTime(index+1,path,size+1);
        }else {
            int p1=processMinimumTime(index+1,path,0)+size+1;
            int p2=processMinimumTime(index+1,path,size)+2;
            int p3=path.length-index;
            min=Math.min(Math.min(p1,p2),p3);
        }
        return min;
    }

    public static int minimumTimeDp(String s){
        if(s==null){
            return 0;
        }
        char[] path=s.toCharArray();
        int [][]dp=new int[path.length+1][path.length+1];
        for (int index = path.length-1; index >=0 ; index--) {
            for (int size=path.length-1; size>=0;size--){
                char cur=path[index];
                int min=0;
                if (cur=='0'){
                    min=dp[index+1][size+1];
                }else {
                    int p1=dp[index+1][0]+size+1;
                    int p2=dp[index+1][size]+2;
                    int p3=path.length-index;
                    min=Math.min(Math.min(p1,p2),p3);
                }
             dp[index][size]=min;
            }
            
        }
        return dp[0][0];
    }
    public static int  minimumTimeGreedy(String s){
        if(s==null){
            return 0;
        }
        int n=s.length();
        int res=n;
        int left=0;
        int size=0;
        for (int i = 0; i <n ; i++) {
            if (s.charAt(i)=='1'){
                left=Math.min(size+1,left+2);
            }
            size++;
            res=Math.min(res,left+n-i-1);
        }
        return res;
    }

    //给出一个二进制数组nums和一个整数k。
    //k位翻转就是从nums中选择一个长度为k的子数组，同时把子数组中的每一个0都改成1，把子数组中的每一个1都改成0。
    //返回数组中不存在0所需的最小k位翻转次数。 如果不可能，则返回-1。
    //子数组是数组的连续部分。

    public static int minKBitFlips(int[] nums, int k){
        if (nums==null||nums.length<k){
            return -1;
        }
        int res=0;
        int n=nums.length;

        for (int i = 0; i <n;i++) {
            if (nums[i]==0) {
                if (i + k > n) {
                    return -1;
                }
                for (int j = i; j < i + k; j++) {
                    nums[j] = nums[j] == 1 ? 0 : 1;
                }
                res++;
            }
        }

        return res;
    }

    public static int minKBitFlips2(int[] nums, int k){
        if (nums==null||nums.length<k){
            return -1;
        }
        int res=0;
        int n=nums.length;
        int []f=new int[n+1];
        if (nums[0]==0){
            f[0]++;
            f[k]--;
            res++;
        }
        for (int i = 1; i <n;i++) {
            f[i]+=f[i-1];
            if (((f[i]&1)==0&&nums[i]==0)||((f[i]&1)!=0&&nums[i]==1)){
                res++;
                f[i]++;
                if (i + k > n) {
                    return -1;
                }
                f[i+k]--;
            }
        }

        return res;
    }

    //你有一个由n个节点标记为0到n - 1的无向连通图。 给定一个数组图，图[i]是一个由节点i通过一条边连接的所有节点组成的列表。
    //返回访问每个节点的最短路径的长度。 您可以在任何节点开始和停止，可以多次访问节点，也可以重用边。\
    public static int shortestPathLength(int[][] graph) {
        if (graph==null||graph.length==0||graph[0].length==0){
            return 0;
        }
        int n=graph.length;
        int res=Integer.MAX_VALUE;
        for (int i = 0; i <graph.length ; i++) {
            res=Math.min(res,processPathLength(graph,i,new int[n],0,new int[n]));
        }
        return res-1;
    }

    public static int processPathLength(int [][]graph,int index,int []m,int count,int []map){
        int n=graph[index].length;
        int t=(1<<n)-1;
        if (count==graph.length){
            return 0;
        }
        if ((m[index]^t)==0){
            return Integer.MAX_VALUE-2000;
        }

        int res=Integer.MAX_VALUE;
        for (int i = 0; i <graph[index].length ; i++) {
            if ((m[index]&(1<<i))==0){
                m[index]=m[index]^(1<<i);
                if (map[index]==0){
                    count++;
                }
                map[index]++;
                res=Math.min(res,processPathLength(graph,graph[index][i],m,count,map)+1);
                if (--map[index]==0){
                    count--;
                }
                m[index]=m[index]^(1<<i);
            }
        }

        return res;

    }






    public static int shortestPathLengthDp(int[][] graph) {
        if (graph==null||graph.length==0||graph[0].length==0){
            return 0;
        }
        int n=graph.length;
        int res=Integer.MAX_VALUE;
        for (int i = 0; i <graph.length ; i++) {
            res=Math.min(res,processPathLengthDp(graph,i,new int [n],0,new int[n],new Integer[n+1][1<<n],new ArrayList()));
        }
        return res-1;
    }

    public static int processPathLengthDp(int [][]graph,int index,int []m,int count,int []map,Integer [][]dp,ArrayList list){
        int n=graph[index].length;
        int t=(1<<n)-1;
        if (count==graph.length){
            return 0;
        }
        if ((m[index]^t)==0){
            return Integer.MAX_VALUE-2000;
        }
        if (dp[index][m[index]]!=null){
            return dp[index][m[index]];
        }

        int res=Integer.MAX_VALUE;
        for (int i = 0; i <graph[index].length ; i++) {
            if ((m[index]&(1<<i))==0){
                m[index]=m[index]^(1<<i);
                if (map[index]==0){
                    count++;
                }
                map[index]++;
                list.add(index);
                res=Math.min(res,processPathLengthDp(graph,graph[index][i],m,count,map,dp,list)+1);
                dp[index][m[index]]=res;
                m[index]=m[index]^(1<<i);
                if (--map[index]==0){
                    count--;
                }
                list.remove(list.size()-1);
            }
        }
        return res;

    }

    //你是个职业抢劫犯，打算抢劫沿街的房屋。 每个房子都有一定数量的钱藏起来，阻止你抢劫的唯一限制就是相邻
    // 的房子都有安全系统连接，如果相邻的两间房子在同一晚被闯入，它会自动联系警察。
    //给定一个代表每家的钱的整数数组，在不通知警察的情况下，退还你今晚可以抢劫的最大金额的钱。
    public static int rob(int[] nums) {
        if (nums==null||nums.length==0){
            return 0;
        }
        return processRob(nums,0);
    }
    public static int processRob(int []nums,int index){
        if(index>=nums.length){
            return 0;
        }
        int p1=processRob(nums,index+1);
        int p2=processRob(nums,index+2)+nums[index];
        return Math.max(p1,p2);
    }
    public static int robDp(int[] nums) {
        if (nums==null||nums.length==0){
            return 0;
        }
        int N=nums.length;
        int []dp=new int[N+2];
        for (int index = N-1; index >=0; index--) {
            int p1=dp[index+1];
            int p2=dp[index+2]+nums[index];
            dp[index]=Math.max(p1,p2);
        }
        return dp[0];

    }
    //你是个职业抢劫犯，打算抢劫沿街的房屋。 每户人家都藏了一定数量的钱。 这里所有的房子都围成一圈。
    // 这意味着第一个房子是最后一个房子的邻居。 与此同时，相邻的住宅都安装了安保系统，
    // 如果在同一晚，相邻的两间住宅被闯入，安保系统就会自动报警。
    //给定一个代表每家的钱的整数数组，在不通知警察的情况下，退还你今晚可以抢劫的最大金额的钱。
    //给定一个代表每家的钱的整数数组，在不通知警察的情况下，退还你今晚可以抢劫的最大金额的钱。
    public static int rob2(int[] nums) {
        if (nums==null||nums.length==0){
            return 0;
        }
        return processRob2(nums,0,false);
    }
    public static int processRob2(int []nums,int index,boolean isOne){
        if (index>=nums.length){
            return 0;
        }
        if (isOne&&index==nums.length-1){
           return 0;
        }
        int p1=processRob2(nums,index+1,isOne?true:false);
        int p2=processRob2(nums,index+2,index==0||isOne?true:false)+nums[index];
        return Math.max(p1,p2);
    }

    public static int rob2Dp(int[] nums) {
        if (nums==null||nums.length==0){
            return 0;
        }
        int N=nums.length;
        int []f=new int[N+2];
        int []t=new int[N+2];
        for (int index = N-1; index >=0 ; index--) {
            int f1,f2,t1,t2;
            if (index==0){
                f[index]=f[index+1];
                t[index]=t[index+2]+nums[index];
                continue;
            }
            if (index==N-1){
                t[index]=t[index+1];
                f[index]=f[index+2]+nums[index];
                continue;
            }

            f1=f[index+1];
            f2=f[index+2]+nums[index];
            f[index]=Math.max(f1,f2);

            t1=t[index+1];
            t2=t[index+2]+nums[index];
            t[index]=Math.max(t1,t2);

        }
        
        return Math.max(t[0],f[0]);
    }















    //你得到一个价格数组，其中价格[i]是给定股票在第i天的价格。
    //你想让你的利润最大化找1天去买股票 找不同的1天去卖股票
    //退还您能从这笔交易中获得的最大利润。 如果你不能获得任何利润，返回0。
    public static int maxProfit(int[] prices) {
        if (prices==null||prices.length==0){
            return 0;
        }
        int max=0;
        int cur=prices[0];
        for (int i = 0; i <prices.length ; i++) {
            if (cur>=prices[i]){
               cur= prices[i];
            }else {
                max=Math.max(max,prices[i]-cur);
            }
        }
        return max;
    }

    //你得到一个整数数组价格，其中价格[i]是给定股票第i天的价格。
    //在每一天，你可以决定购买和/或出售股票。 任何时候你最多只能持有该股票的一股。 不过，你也可以在买入当日立即卖出。
    //找到并回报你能达到的最大利润。
    public static int maxProfit2(int[] prices) {
        if (prices==null||prices.length==0){
            return 0;
        }
        int max=0;
        int cur=prices[0];
        for (int i = 0; i <prices.length ; i++) {
            if(cur>=prices[i]){
                cur=prices[i];
            }else {
                max+=prices[i]-cur;
                cur=prices[i];
            }
        }
        return max;
    }
    //你得到一个价格数组，其中价格[i]是给定股票在第i天的价格。
    //找到你能获得的最大利润。 您最多可完成两笔交易。
    //注意:你不能同时进行多笔交易(即，你必须在再次买入之前卖出股票)。

    public static int maxProfits3vr(int []prices){
        if (prices==null||prices.length==0){
            return 0;
        }

        int max=Integer.MIN_VALUE;
        for (int i = 0; i <prices.length ; i++) {
            int p1=Integer.MIN_VALUE;
            for (int j = 0; j <=i ; j++) {
                p1=Math.max(p1,prices[i]-prices[j]);
            }
            max=Math.max(max,p1+processMaxProfits1(prices,i,i+1));
        }
        return max;
    }
    public static int processMaxProfits1(int []prices,int pay1,int pay2){
        if (pay2>=prices.length){
            return 0;
        }
            int p2=Integer.MIN_VALUE;
            for (int i = pay1+1; i <=pay2; i++) {
                 p2=Math.max(p2,prices[pay2]-prices[i]);
            }
            return Math.max(p2,processMaxProfits1(prices,pay1,pay2+1));
    }

    public static int maxProfits3Dp(int []prices){
        if (prices==null||prices.length==0){
            return 0;
        }
        int max=Integer.MIN_VALUE;
        int N=prices.length;
        int [][]dp=new int[N+1][N+1];
        for (int i = 0; i <prices.length ; i++) {
            int p1=Integer.MIN_VALUE;
            for (int j = 0; j <=i ; j++) {
                p1=Math.max(p1,prices[i]-prices[j]);
            }

            for (int j = N-1; j >=i+1 ; j--) {

                int p2=Integer.MIN_VALUE;
                for (int index = i+1; index <=j; index++) {
                    p2=Math.max(p2,prices[j]-prices[index]);
                }
                dp[i][j]=Math.max(p2,dp[i][j+1]);

            }
            max=Math.max(max,p1+dp[i][i+1]);
        }
        return max;
    }

    public static int maxProfits3vr2(int []prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
       return processMaxProfits2(prices,2,prices.length-1);
    }
    //第i笔交易截止第day天的最大值
    public static int processMaxProfits2(int []prices,int index,int day){
        if (index<=0){
            return 0;
        }
        if (day<0){
            return 0;
        }
        int max=Integer.MIN_VALUE;
        for (int i = 0; i <day ; i++) {
            max=Math.max(max,prices[day]-prices[i]+processMaxProfits2(prices,index-1,i));
        }
        max=Math.max(max,processMaxProfits2(prices,index,day-1));
        return max;
    }

    public static int maxProfits3Dp2(int []prices){
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int [][]dp=new int[3][prices.length+1];

        for (int i = 1; i <3 ; i++) {
            for (int j = 1; j <prices.length ; j++) {
                int max=Integer.MIN_VALUE;

                for (int k = 0; k <=j ; k++) {
                    max=Math.max(max,prices[j]-prices[k]+dp[i-1][k]);
                }
                dp[i][j]=Math.max(max,dp[i][j-1]);
            }
        }

        return dp[2][prices.length-1];
    }

    public static int maxProfits3Dp3(int []prices){
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int [][]dp=new int[3][prices.length+1];

        for (int i = 1; i <3 ; i++) {
            int maxDiff=dp[i-1][0]-prices[0];
            for (int j = 1; j <prices.length ; j++) {
                int max=Integer.MIN_VALUE;

                //max=Math.max(max,prices[j]-prices[k]+dp[i-1][k]);
                //-prices[k]+dp[i-1][k]+prices[j]
                //经典 可以常看
                max=Math.max(max,prices[j]+maxDiff);
                dp[i][j]=Math.max(max,dp[i][j-1]);
                maxDiff=Math.max(maxDiff,dp[i-1][j]-prices[j]);

            }
        }

        return dp[2][prices.length-1];
    }
    //给定一个整数数组的价格，其中价格[i]是给定股票第i天的价格，还有一个整数k。
    //找到你能获得的最大利润。 你最多可以完成k个交易。
    //注意:你不能同时进行多笔交易(即，你必须在再次买入之前卖出股票)。
    public static int maxProfits4Dp(int []prices,int k){
        if (prices == null || prices.length == 0||k==0) {
            return 0;
        }

        int [][]dp=new int[k+1][prices.length+1];

        for (int i = 1; i <k+1 ; i++) {
            int maxDiff=dp[i-1][0]-prices[0];
            for (int j = 1; j <prices.length ; j++) {
                int max=Integer.MIN_VALUE;

                //max=Math.max(max,prices[j]-prices[k]+dp[i-1][k]);
                //-prices[k]+dp[i-1][k]+prices[j]
                //经典 可以常看
                max=Math.max(max,prices[j]+maxDiff);
                dp[i][j]=Math.max(max,dp[i][j-1]);
                maxDiff=Math.max(maxDiff,dp[i-1][j]-prices[j]);

            }
        }

        return dp[k][prices.length-1];
    }
    //你有n个气球，索引从0到n - 1。 每个气球上都画有一个数字，用数组nums表示。 你被要求炸掉所有的气球。
    //如果你爆破第i个气球，你将得到nums[i - 1] * nums[i] * nums[i + 1]硬币。 如果i - 1或i + 1超出了数组的范围，
    // 则将其视为一个画有1的气球。
    //退还你可以收集的最大的气球爆破硬币。
    public static int maxCoins(int[] nums) {
        if (nums==null||nums.length==0){
            return 0;
        }
        LinkedList list=new LinkedList();
        for (int i = 0; i <nums.length; i++) {
            list.add(nums[i]);
        }
        return processCoins(nums,list);
    }
    public static int processCoins(int []nums,LinkedList<Integer> list){
        if (list.size()==0){
            return 0;
        }
        int max=Integer.MIN_VALUE;
     //   LinkedList tmp=new LinkedList(list);
        for (int i = 0; i <list.size() ; i++) {
            LinkedList tmp=new LinkedList(list);
            int left=i-1<0?1:list.get(i-1);
            int right=i+1>=list.size()?1:list.get(i+1);
            int ans=left*list.get(i)*right;
            list.remove(i);
            max=Math.max(max,processCoins(nums,list)+ans);
            list=tmp;
        }

        return max;
    }
    public static int maxCoins2(int[] nums) {
        if (nums==null||nums.length==0){
            return 0;
        }
        return processConins2(nums,0,nums.length-1);

    }
    public static int processConins2(int []nums,int left,int right){
        if (right<0||left>=nums.length){
            return 0;
        }
        int max=0;
        for (int i = left; i <=right ; i++) {
            int l=processConins2(nums,left,i-1);
            int r=processConins2(nums,i+1,right);
            int ans=(left==0?1:nums[left-1])*nums[i]*(right==nums.length-1?1:nums[right+1]);
            max=Math.max(max,l+r+ans);
        }
        return max;
    }
    public static int maxCoinsDp(int [] nums){
        if (nums==null||nums.length==0){
            return 0;
        }
        int N=nums.length;
        int [][]dp=new int[N][N];
        for (int left =N-1; left >=0 ; left--) {
            for (int right = 0; right <N ; right++) {
                if (right<0||left>=nums.length){
                    continue;
                }
                for (int i = left; i <=right ; i++) {
                    int l=i-1<0?0:dp[left][i-1];
                    int r=i+1>=N?0:dp[i+1][right];
                    int ans=(left==0?1:nums[left-1])*nums[i]*(right==nums.length-1?1:nums[right+1]);
                   dp[left][right]=Math.max(dp[left][right],l+r+ans);
                }
            }
        }
        return dp[0][N-1];
    }


    //给定一个输入字符串s和一个模式p，实现支持'的正则表达式匹配'.'和'*'，其中:
//“。” 匹配任意单个字符。
//"*"匹配前面的0个或多个元素。
//匹配应该覆盖整个输入字符串(而不是部分)
    public static  boolean isMatch(String s, String p) {
        if (s==null||s.length()==0||p==null||p.length()==0||p.charAt(0)=='*'){
            return false;
        }
        return processIsMatch(s,p,0,0);
    }

    //有* 出现两种分支 一种是不拿 因为*可以为0 另一种是拿
    public static boolean processIsMatch (String s,String p,int s1,int p1){
         if (p1>=p.length()){
             return s1==s.length();
         }
         boolean ans;
         boolean cur=s1<s.length()&&(s.charAt(s1)==p.charAt(p1)||p.charAt(p1)=='.');
         if (p1+1<p.length()&&p.charAt(p1+1)=='*'){
             ans= processIsMatch(s,p,s1,p1+2)||(cur&&processIsMatch(s,p,s1+1,p1));
         }else {
             ans= cur&&processIsMatch(s,p,s1+1,p1+1);
         }
         return ans;

    }
    //改dp非常简单暂不做


    ///给定一个正整数数组bean，其中每个整数表示在特定魔术包中找到的魔术豆的数量。
    //从每个袋子中删除任意数量的豆子(可能没有)，这样每个剩下的非空袋子(仍然包含
    // 至少一个豆子)中的豆子数量就相等。 一旦豆子从袋子里取出，你就不能把它放回任何袋子里。
    //prefix
    //返回您必须移除的魔豆的最小数量。
    public static long minimumRemoval(int[] beans) {
        if (beans==null||beans.length==0){
            return 0;
        }
        return processMinmunR(beans,0);
    }
    public static long processMinmunR(int [] beans,int index){
        if (index==beans.length){
            return Integer.MAX_VALUE;
        }

        long cur=beans[index];
        long min=0;
        for (int i = 0; i <beans.length ; i++) {
            if (i!=index){
                min+=beans[i]<cur?beans[i]:beans[i]-cur;
            }
        }
        return Math.min(min,processMinmunR(beans,index+1));
    }

    public static long minimumRemovalDp(int[] beans) {
        if (beans==null||beans.length==0){
            return 0;
        }
        int N=beans.length;
        long []dp=new long[N+1];
        dp[N]=Integer.MAX_VALUE;
        for (int index = N-1; index >=0 ; index--) {
            long cur=beans[index];
            for (int i = 0; i <beans.length ; i++) {
                if (i!=index){
                    dp[index]+=beans[i]<cur?beans[i]:beans[i]-cur;
                }
            }
             dp[index]=Math.min(dp[index],dp[index+1]);
        }
        return dp[0];
    }
    public static long minimumRemovalG(int[] beans) {
        if (beans==null||beans.length==0){
            return 0;
        }
        Arrays.sort(beans);
        long sum=0;
        long prefix=0;
        long suffix=0;
        long min=Long.MAX_VALUE;
        int N=beans.length;
        long ans=0;

        for (int i = 0; i <beans.length ; i++) {
            sum+=beans[i];
        }
        for (int i = 0; i <beans.length ; i++) {
            prefix+=beans[i];
            suffix=sum-prefix;
            ans=(prefix-beans[i])+(suffix-beans[i]*(N-i-1L));
            min=Math.min(min,ans);
        }

        return min;

    }

    //编写一个程序来解决数独谜题，填满空的单元格。
    //一个数独解决方案必须满足以下所有规则:
    //每个数字1-9必须在每行中恰好出现一次。
    //每个数字1-9必须在每一列中恰好出现一次。
    //每个数字1-9必须在网格的9个3x3子框中恰好出现一次。
    //”。字符表示空单元格。
    public static void solveSudoku(char[][] board) {
        char[][]ans=new char[board.length][board[0].length];
       ans= processSudoku(board,0,0);
        for (int i = 0; i <board.length ; i++) {
            for (int j = 0; j <board[0].length ; j++) {
                System.out.print(ans[i][j]);
            }
            System.out.println();
        }
        System.out.println(isValidSudoku(ans));

    }



    public static char[][] processSudoku(char[][] board,int r,int c){
        if (r==board.length){
            return board;
        }
        int newr=0;
        int newc=0;
        if (c==board[0].length-1){
            newc=0;
            newr=r+1;
        }else {
            newr=r;
            newc=c+1;
        }
        char[][]ans=board;
        if (board[r][c]!='.'){
            ans=processSudoku(board,newr,newc);
            if (isFinsh(ans)){
                return ans;
            }
        }else {
            for (int i = 1; i <=9 ; i++) {
                char cur=(char)(i+'0');
                if (isValid(board,r,c,cur)){
                    board[r][c]=cur;
                    ans=processSudoku(board,newr,newc);
                    if (isFinsh(ans)){
                        return ans;
                    }
                    board[r][c]='.';
                }

            }

        }
        return ans;
    }
    public static boolean isValid(char [][] board,int r,int c,char cur){
        boolean p1=isRows(board,r,0,cur,c);
        boolean p2=isCols(board,0,c,cur,r);
        int limitr,limitc,r1,c1;
        if (c>=0&&c<=2){
            limitc=2;
            c1=0;
        }else if (c>=3&&c<=5){
            limitc=5;
            c1=3;
        }else {
            limitc=8;
            c1=6;
        }
        if (r>=0&&r<=2){
            limitr=2;
            r1=0;
        }else if (r>=3&&r<=5){
            limitr=5;
            r1=3;
        }else {
            limitr=8;
            r1=6;
        }
        boolean p3=isBox(board,r1,c1,r,c,cur,limitr,limitc);
        if (!p1||!p2||!p3){
            return false;
        }
        return true;

    }
    public static boolean isFinsh(char[][]board){
        for (int i = 0; i <board.length ; i++) {
            for (int j = 0; j <board[0].length ; j++) {
                if (board[i][j]=='.'){
                    return false;
                }
            }
        }
        return true;
    }

    //确定9 × 9数独板是否有效。只对已填满的单元格进行验证，验证规则如下:
    //每行必须包含数字1-9，不能重复。
    //每一列必须包含数字1-9，不能重复。
    //网格的9个3 × 3子盒中的每一个必须包含数字1-9，不能重复。
    public static boolean isValidSudoku(char[][] board) {
        for (int r = 0; r <board.length ; r++) {
            for (int c = 0; c <board[0].length ; c++) {
                char cur=board[r][c];
                if (cur!='.'){
                    boolean p1=isRows(board,r,c,cur,c);
                    boolean p2=isCols(board,r,c,cur,r);
                    int limitr,limitc,r1,c1;
                    if (c>=0&&c<=2){
                        limitc=2;
                        c1=0;
                    }else if (c>=3&&c<=5){
                        limitc=5;
                        c1=3;
                    }else {
                        limitc=8;
                        c1=6;
                    }
                    if (r>=0&&r<=2){
                        limitr=2;
                        r1=0;
                    }else if (r>=3&&r<=5){
                        limitr=5;
                        r1=3;
                    }else {
                        limitr=8;
                        r1=6;
                    }
                    boolean p3=isBox(board,r1,c1,r,c,cur,limitr,limitc);
                    if (!p1||!p2||!p3){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static boolean isRows(char[][]board,int r,int c,char q,int index){
        if (c==board[0].length){
            return true;
        }
        char cur=board[r][c];
        boolean p1=true;
        if (cur!='.'){
            if (index!=c&&cur==q){
                return false;
            }else {
             p1=isRows(board,r,c+1,q,index);
            }
        }else {
            p1=isRows(board,r,c+1,q,index);
        }
        return p1;
    }
    public static boolean isCols(char[][]board,int r,int c,char q,int index){
        if (r==board.length){
            return true;
        }
        char cur=board[r][c];
        boolean p1=true;
        if (cur!='.'){
            if (index!=r&&cur==q){
                return false;
            }else {
             p1=isCols(board,r+1,c,q,index);
            }
        }else {
            p1=isCols(board,r+1,c,q,index);
        }
        return p1;
    }
    public static boolean isBox(char[][]board,int r,int c,int rc,int cc,char q,int limitRe,int limitCe){
        if (r>limitRe){
            return true;
        }
        if (c>limitCe){
            return true;
        }
        boolean p1=true,p2=true;
        char cur=board[r][c];
        if (cur!='.'){
            if (cur==q&&r!=rc&&c!=cc){
                return false;
            }else{
                p1=isBox(board,r+1,c,rc,cc,q,limitRe,limitCe);
                p2=isBox(board,r,c+1,rc,cc,q,limitRe,limitCe);
            }
        }else {
            p1=isBox(board,r+1,c,rc,cc,q,limitRe,limitCe);
            p2=isBox(board,r,c+1,rc,cc,q,limitRe,limitCe);
        }
        return p1&&p2;
    }




//Given a sorted array of distinct integers and a target value,
// return the index if the target is found. If not, return the index where it would be if it were inserted in order.
//You must write an algorithm with O(log n) runtime complexity.
    public int searchInsert(int[] nums, int target) {
        int pre=-1;
        for (int i = 0; i <nums.length ; i++) {
            if (nums[i]<target){
                pre=i;
            }else if (nums[i]==target){
                return i;
            }else {
                return pre==0?0:pre+1;
            }
        }
        return nums.length;
    }

    //给定一个m x n的整数数组网格，其中网格[i][j]可能是:1表示起始方块。只有一个起点。2代表结束方块。
    // 只有一个方尾。0表示空的方块，我们可以走过去。
    // -1代表我们无法跨越的障碍。返回从开始方块到结束方块的4个方向行走的次数，即通过每个非障碍方块的次数。
    public static int uniquePathsIII(int[][] grid) {

return 0;
    }
    //count-and-say序列是由递归公式定义的数字字符串序列:countAndSay(1) = "1" countAndSay(n)是你“说”来自countAndSay(n-1)的数字字符串的方式，然后转换成一个不同的数字字符串。要确定如何“说”一个数字字符串，请将其分割为最小数量的组，以便每个组都是包含相同字符的连续部分。
    // 然后对每一组，说出字符的数量，然后说出字符。要将语句转换为数字字符串，请将计数替换为数字并连接每个语句。
    public static String countAndSay(int n) {
        return processCAS(n,1,"1");
    }
    public static String processCAS(int n,int index,String s){
        if (index==n){
            return s;
        }
        String newS=s;
        int cur=0;
        int count=0;
        boolean more=false;
        for (int i = 0; i <s.length() ; i++) {
            count++;
            if (i+1==s.length()){
                if (more){
                    newS+=String.valueOf(count)+s.charAt(cur);
                }else {
                    newS=String.valueOf(count)+s.charAt(cur);
                }
                continue;
            }
            if (i+1<s.length()&&s.charAt(cur)==s.charAt(i+1)){
                 if (i==s.length()-1){
                     newS=String.valueOf(count)+s.charAt(cur);
                 }
            }else {
                if (more){
                    newS+=String.valueOf(count)+s.charAt(cur);
                    cur=i+1;
                    count=0;
                }else {
                    newS=String.valueOf(count)+s.charAt(cur);
                    cur=i+1;
                    count=0;
                    more=true;
                }


            }
        }
        return processCAS(n,index+1,newS);
    }

    //给定一个由不同的候选整数和一个目标整数组成的数组，返回所有候选整数的唯一组合的列表，其中所选的数字和到目标。
    // 您可以以任何顺序返回组合。
    //同样的数字可以从候选人中选择无限次。如果所选数字中至少有一个的频率不同，那么这两个组合就是唯一的。
    //对于给定的输入，保证目标的唯一组合的总数小于150个组合。

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        return processComb(candidates,0,target,0,new ArrayList<Integer>(),new ArrayList<List<Integer>>());
    }
    public static List<List<Integer>> processComb(int [] candidates,int index,int target,int sum,ArrayList<Integer> list,List<List<Integer>> lists ){
        if (index>=candidates.length){
            if (sum==target){
                lists.add(new ArrayList<>(list));
                return lists;
            }else {
                return lists;
            }

        }
        if (sum==target){
            lists.add(new ArrayList<>(list));
            return lists;
        }
        if (sum>target){
            return lists;
        }

        if (sum<target){
            list.add(candidates[index]);
            lists=processComb(candidates,index,target,sum+candidates[index],list,lists);
            list.remove(list.size()-1);
        }

        lists=processComb(candidates,index+1,target,sum,list,lists);
        return lists;
    }


    public static List<List<Integer>> combinationSumDfs(int[] candidates, int target) {
        return processCombDfs(candidates,target,0,new ArrayList<Integer>(),new ArrayList<List<Integer>>(),0);
    }

    public static List<List<Integer>> processCombDfs(int [] candidates,int target,
                                                     int sum,ArrayList<Integer> list,List<List<Integer>> lists,int index ){
        if (sum==target){
                lists.add(new ArrayList<>(list));
            return lists;
        }
        if (sum>target){
            return lists;
        }

        for (int i =index ; i <candidates.length ; i++) {
            list.add(candidates[i]);
            lists=processCombDfs(candidates,target,sum+candidates[i],list,lists,i);
            list.remove(list.size()-1);
        }
        return lists;
    }

//给定一个候选数(candidate)和一个目标数(target)的集合，找出所有候选数和为目标数的唯一组合。
//候选人中的每个数字只能在组合中使用一次。
//注意:解集不能包含重复的组合。
    public static List<List<Integer>> combinationSumDfs2(int[] candidates, int target) {
        Arrays.sort(candidates);
        return processCombDfs2(candidates,target,0,new ArrayList<Integer>(),new ArrayList<List<Integer>>(),0);
    }
    public static List<List<Integer>> processCombDfs2(int [] candidates,int target,
                                                     int sum,ArrayList<Integer> list,List<List<Integer>> lists,int index ){
        if (sum==target){
            lists.add(new ArrayList<>(list));
            return lists;
        }
        if (sum>target){
            return lists;
        }

        for (int i =index ; i <candidates.length ; i++) {

            if (i>index&&candidates[i]==candidates[i-1]){
                continue;
            }

            list.add(candidates[i]);
            lists=processCombDfs2(candidates,target,sum+candidates[i],list,lists,i+1);
            list.remove(list.size()-1);
        }
        return lists;
    }

//    给定一个未排序的整数数组nums，返回缺失的最小正整数。
//    你必须实现一个在O(n)时间内运行并且使用常量的额外空间的算法。
public static int firstMissingPositive(int[] nums) {
    for (int i = 0; i <nums.length;) {
        if (nums[i]>0&&nums[i]<nums.length&&i+1!=nums[i]&&nums[nums[i]-1]!=nums[i]){
            int temp=nums[i];
            nums[i]=nums[temp-1];
            nums[temp-1]=temp;
        }else {
         i++;
        }
    }
    for (int i = 0; i <nums.length ; i++) {
        if (nums[i]!=i+1){
            return i+1;
        }
    }

return nums.length+1;
}
//给定两个0索引的整数数组服务器和任务，长度分别为n和m。 服务器[i]是第i个服务器的权重，任务[j]是处理第j个任务所需的时间，单位是秒。
//任务通过任务队列分配给服务器。 最初，所有服务器都是空闲的，队列是空的。
//在第二个j, j任务是插入到队列(从第0个任务被插入到第二0)。只要有免费的服务器和队列非空,队列的前面的任务将被分配给
// 一个空闲的服务器与最小的权重,和一条领带,是分配给一个免费的服务器与最小的指数。
//如果没有空闲的服务器，队列也不是空的，我们会等待服务器空闲，然后立即分配下一个任务。 如果多个服务器同时空闲，那
// 么队列中的多个任务将按照上面的权重和索引优先级的插入顺序分配。
//在第t秒被分配任务j的服务器将在第t +任务秒再次空闲[j]。
//构建一个长度为m的数组ans，其中ans[j]是第j个任务要分配给的服务器的索引。
//返回数组ans。
    public static class server{
        int index;
        int val;

    public server(int index, int val) {
        this.index = index;
        this.val = val;
    }
}

public static class assign{
        server server;
        int freetime;

    public assign(Test1.server server, int freetime) {
        this.server = server;
        this.freetime = freetime;
    }
}
    public static int[] assignTasks(int[] servers, int[] tasks) {
        PriorityQueue <server> freeServer=new PriorityQueue<server>((a,b)->a.val!=b.val?a.val-b.val:a.index-b.index);
        PriorityQueue<assign> runServer=new PriorityQueue<assign>((Comparator.comparingInt(a -> a.freetime)));
        for (int i = 0; i <servers.length ; i++) {
            freeServer.add(new server(i,servers[i]));
        }
        int time=0;
        int k=0;
        int [] ans=new int[tasks.length];
        while (k<tasks.length){
            while (!runServer.isEmpty()&&runServer.peek().freetime<=time){
                freeServer.add(runServer.poll().server);
            }
            while (!freeServer.isEmpty()&&k<=time&&k<tasks.length){
                int taskT=tasks[k];
                int freeTime=time+taskT;
                server s=freeServer.poll();
                ans[k]=s.index;
                runServer.add(new assign(s,freeTime));
                ++k;
            }
            if (!freeServer.isEmpty()){
                time++;
            }else {
                time=runServer.peek().freetime;
            }

        }
        return ans;
}

//给定一个由k个链表组成的数组，每个链表按升序排序
//将所有的链表合并成一个排序的链表并返回它。
public static class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
//heap
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists==null||lists.length==0){
            return null;
        }
      PriorityQueue <ListNode> heap=new PriorityQueue<>((a,b)->a.val-b.val);
        for (int i = 0; i <lists.length ; i++) {
            if (lists[i]==null){
                continue;
            }
            heap.add(lists[i]);
        }
        if (heap.isEmpty()){
            return null;
        }
        ListNode node=heap.poll();
        if (node.next!=null){
            heap.add(node.next);
        }

        ListNode head=new ListNode(node.val);
        ListNode cur=head;
        while (!heap.isEmpty()){
            ListNode node1=heap.poll();
            if (node1.next!=null){
                heap.add(node1.next);
            }
            ListNode node2=new ListNode(node1.val);
            cur.next=node2;
            cur=cur.next;

        }
        return head;
    }
//    给定两个大小分别为m和n的有序数组nums1和nums2，返回这两个有序数组的中值。
//    总体运行时复杂度应为O(log (m+n))
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return 0;
    }

//给定一个输入字符串(s)和一个模式(p)，实现通配符模式匹配，支持'? '和'*'，其中:
//“?” 匹配任意单个字符。
//*匹配任何字符序列(包括空序列)。
//匹配应该覆盖整个输入字符串(而不是部分)
public static boolean isMatch2(String s, String p) {
        if (s==null||p==null){
            return false;
        }
        return processIsMatch2(s,p,0,0);
}

public static boolean processIsMatch2(String s,String p,int s1,int p1){
        if (p1==p.length()){
            return s1==s.length();
        }
        char cur=p.charAt(p1);
        boolean ans=false;

        if (s1<s.length()&&(cur==s.charAt(s1)||cur=='?')){
            ans=processIsMatch2(s,p,s1+1,p1+1);
        }
        if (s1<s.length()&&cur!=s.charAt(s1)&&cur!='?'&&cur!='*'){
            ans=false;
        }
        if (cur=='*'){
            boolean t1=processIsMatch2(s,p,s1,p1+1);
            boolean t2=s1<s.length()?processIsMatch2(s,p,s1+1,p1):false;
            ans=t1||t2;
        }
        return ans;
}
    public static boolean isMatch2Dp(String s, String p) {
        if (s==null||p==null){
            return false;
        }
        int S=s.length();
        int P=p.length();
        boolean [][]dp=new boolean[P+1][S+1];
        for (int p1 = P; p1 >=0 ; p1--) {
            for (int s1 = S; s1 >=0 ; s1--) {
                if (p1==p.length()){
                     dp[p1][s1]=s1==s.length();
                     continue;
                }
                char cur=p.charAt(p1);
                if (s1<s.length()&&(cur==s.charAt(s1)||cur=='?')){
                    dp[p1][s1]=dp[p1+1][s1+1];
                }
                if (s1<s.length()&&cur!=s.charAt(s1)&&cur!='?'&&cur!='*'){
                    dp[p1][s1]=false;
                }
                if (cur=='*'){
                    boolean t1=dp[p1+1][s1];
                    boolean t2=s1<s.length()? dp[p1][s1+1]:false;
                    dp[p1][s1]=t1||t2;
                }
            }
        }
        return dp[0][0];
    }




//twoSum
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map=new HashMap<>();
        for (int i = 0; i <nums.length ; i++) {
            if (map.containsKey(target-nums[i])){
                return new int[]{map.get(target-nums[i]),i};
            }
            map.put(nums[i],i);
        }

        return null;
}


//给定两个非空链表，表示两个非负整数。数字按倒序存储，每个节点包含一个数字。将两个数字相加
// ，并以链表的形式返回总和。你可以假设这两个数字不包含任何前导零，除了数字0本身。

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return null;
    }
//给定一个字符串s，找出不重复字符的最长子字符串的长度。
    public static int lengthOfLongestSubstring(String s) {
        if (s==null||s.length()==0){
            return 0;
        }
        HashMap <Character,Integer> map=new HashMap<>();
        int max=1;
        int res=0;
        int near=-1;
        for (int i = 0; i <s.length() ; i++) {
            char cur=s.charAt(i);
            if (map.containsKey(cur)){
                int index=map.get(cur);
                int back=0;
                if (near==-1){
                    back=index;
                }else {
                    back=index>near?index:near;
                }
                res=i-back;
                near=index>near?index:near;
                max=Math.max(res,max);
                map.put(cur,i);
            }else {
                map.put(cur,i);
                res++;
                max=Math.max(res,max);
            }
        }
        return max;
    }
    //给定一个长度为n的整数数组。有n条垂线，第i条线的两个端点是(i, 0)和(i，高度[i])。
    //找出两条与x轴一起构成一个容器的直线，使容器包含最多的水。
    //返回一个容器可以储存的最大水量。
    //注意，你不能倾斜容器。
    public static int maxArea(int[] height) {
        if (height==null||height.length==0){
            return 0;
        }
        int res=0;
        for (int i = 0; i <height.length ; i++) {
            int left=-1;
            int right=-1;
            for (int j = 0; j <height.length ; j++) {
                if (i==j){
                    continue;
                }
                if (height[j]>=height[i]){
                    if (j<i){
                        left=left==-1?j:left;
                    }else {
                        right=j;
                    }
                }
            }
            int ansl=left==-1?0:i-left;
            int ansr=right==-1?0:right-i;
            int ans=(ansl+ansr)*height[i];
            res=Math.max(ans,res);
        }
        return res;
    }
    public static int maxArea2(int[] height) {
        int max=0;
        int left=0;
        int right=height.length-1;
        while (left<=right){
            max=Math.max(Math.min(height[left],height[right])*(right-left),max);
            if (height[left]<height[right]){
                left++;
            }else {
                right--;
            }
        }
return max;
    }
    //转化罗马数字
    public static int romanToInt(String s) {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        map.put('A',0);
        int res=0;
        char prev='A';
        for (char c:s.toCharArray()){
            if (map.get(prev)<map.get(c)){
                res+=map.get(c)-2*map.get(prev);
            }else {
                res+=map.get(c);
            }
            prev=c;
        }
        return res;
    }

//给定一棵二叉树，找出树中两个给定节点的最低共同祖先(LCA)。
//根据维基百科对LCA的定义:“在两个节点p和q之间，最低共同祖先被定义为T中同时具有p和q
//后代的最低节点(在这里，我们允许一个节点是它自己的后代)。”
    static TreeNode res=new TreeNode(0);
public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
          processLCA(root,p,q);
        return res;
}
public static boolean processLCA(TreeNode cur,TreeNode p,TreeNode q){
        if (cur==null){
            return false;
        }

        boolean ans=cur==p||cur==q?true:false;
        boolean left=processLCA(cur.left,p,q);
        boolean right=processLCA(cur.right,p,q);
        if ((left&&right)||(ans&&left)||(ans&&right)){
            res=cur;
        }
        return left||right||ans;
}



    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }


    public static boolean isPalindrome(int x) {
    if (x<0){
        return false;
    }
    ArrayList list =new ArrayList();
    while (x/10!=0){
        list.add(x%10);
        x=x/10;
    }
    list.add(x);
    int left=0;
    int right=list.size()-1;
    while (left<=right){
        if (list.get(left)!=list.get(right)){
            return false;
        }
        left++;
        right--;
    }
    return true;
    }

    //一个有效的IP地址由四个由单点分隔的整数组成。每个整数的取值范围为0 ~ 255，且不能有前导零。
//例如:“0.1.2.201”、“192.168.1.1”为合法IP地址，“0.011.255.245”、“192.168.1.312”、“192.168@1.1”为非法IP地址。
//给定一个只包含数字的字符串s，返回所有可能的有效IP地址，这些IP地址可以通过在s中插入点来构成。你不允许重新排序或删除s中的任何数字。
// 你可以以任何顺序返回有效的IP地址。
    public static List<String> restoreIpAddresses(String s) {
        if (s==null){
            return null;
        }
        StringBuilder str=new StringBuilder(s);
        List<String> lists=new ArrayList<>();
        processAddresses(str,1,3,lists);
        return lists;
    }

    public static void processAddresses(StringBuilder str,int index,int rest,List<String> lists){
        if (rest==0){
            if (isAddresses(str)){
                lists.add(new String(str));
            }
            return;
        }
        if (index>str.length()&&rest>0){
            return;
        }
        for (int i = index; i <str.length() ;i++) {
            str.insert(i,'.');
            processAddresses(str,i+2,rest-1,lists);
            str=str.deleteCharAt(i);
        }
    }
    public static boolean isAddresses(StringBuilder str){
        ArrayList <Integer> list=new ArrayList<>();
        for (int i = 0; i <str.length() ; i++) {
            if (str.charAt(i)=='.'){
               list.add(i);
            }
        }

        String s1= str.substring(0,list.get(0));
        String s2=str.substring(list.get(0)+1,list.get(1));
        String s3=str.substring(list.get(1)+1,list.get(2));
        String s4=str.substring(list.get(2)+1,str.length());
        ArrayList <String> strings=new ArrayList<>();
        strings.add(s1);
        strings.add(s2);
        strings.add(s3);
        strings.add(s4);

        for (int i = 0; i <strings.size() ; i++) {
            String s=strings.get(i);
            if (s.length()>3||s.length()==0){
                return false;
            }
            if (s.charAt(0)-'0'>=3){
                if (s.length()==3){
                    return false;
                }
            }else {
                if (s.charAt(0)-'0'==0){
                    if (s.length()==2||s.length()==3){
                        return false;
                    }
                }
                if (s.charAt(0)-'0'==2){
                    if (s.length()==3&&s.charAt(1)-'0'>5){
                        return false;
                    }

                    if (s.length()==3&&s.charAt(1)-'0'==5&&s.charAt(2)-'0'>5){
                        return false;
                    }

                }

            }
        }
        return true;
    }

    //给定n个非负整数表示一个高程图，其中每个条的宽度是1，计算出下雨后它能捕获多少水。
    public static int trap(int[] height) {
    if (height==null||height.length<3){
        return 0;
    }
    int [] leftmax=new int[height.length];
    int [] rightmax=new int[height.length];
    int lmax=Integer.MIN_VALUE;
    int rmax=Integer.MIN_VALUE;
    int res=0;
        for (int i = 0; i <height.length ; i++) {
            lmax=Math.max(lmax,height[i]);
            leftmax[i]=lmax;
        }
        for (int i = height.length-1; i >=0 ; i--) {
            rmax=Math.max(rmax,height[i]);
            rightmax[i]=rmax;
        }

        for (int i = 0; i <height.length ; i++) {
            if (height[i]<leftmax[i]&&height[i]<rightmax[i]){
                if (leftmax[i]<rightmax[i]){
                    res+=leftmax[i]-height[i];
                }else {
                    res+=rightmax[i]-height[i];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
    int []height={4,2,0,3,2,5};
        System.out.println(trap(height));


    }

}