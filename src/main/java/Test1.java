import java.lang.reflect.Array;
import java.util.*;

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


    //斐波拉列式递归套路  改logN
    //公式：|F(n),F(n-1)|=|F(2),F(1)|*{{1,1}{1,0}}^n-2
    //
    public static int fi(int n){
        if (n<=0){
            return 0;
        }
        if (n==1||n==2){
            return 1;
        }
        int [][]m={{1,1},{1,0}};
        int[][]res=matrixPower(m,n-2);
        return res[0][0]+res[1][0];
    }


    //求{1,1}{1,0}^n-2
    public static int [][] matrixPower(int [][]m,int p){
        int[][] res=new int[m.length][m[0].length];

        for (int i = 0; i <res.length ; i++) {
            res[i][i]=1;
        }
        int [][]t={{1,1},{1,0}};

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
























    public static void main(String[] args) {
        int [] arr={2,2,1,4};
        System.out.println(_4Num(arr));

    }
}