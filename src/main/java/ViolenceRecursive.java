import java.util.*;

/**
 * @author zhang
 * @ClassName: ViolenceRecursive
 * @Package PACKAGE_NAME
 * @Description: 暴力递归
 * @date 2021/12/2517:04
 */
public class ViolenceRecursive {
    //汉诺塔问题
    //小压大
    //左中右
    //3步骤：
    //1.1-N-1的圆盘移动到中柱
    //2.N圆盘移动到右柱
    //3.1-N-1的圆盘移动到右柱
    //ps：不要被左中右柱迷惑了    左中右柱随时可以切换  步骤仍然是这几个
    public static void hanoi(int n){
        if (n>0){
            func_(n,"左","右","中");
        }
    }
    public static void func_(int n,String from,String to,String other){
        if (n==1){
            System.out.println("1从" + from + "到" + to);
        }else {
            func_(n-1,from,other,to);
            System.out.println(n+"从" + from + "到" + to);
            func_(n-1,other,to,from);
        }
    }

    //使用递归把栈逆序
    //步骤：1把栈最后一数拿出来 再将之前拿出来的数压回去
    public static int f(Stack<Integer> stack){
        int result=stack.pop();
        if (stack.isEmpty()){
            return result;
        }
        //继续递归直到最后一个数
        int last=f(stack);
        //拿到last数之后  把我自己之前弹出的result 压回栈
        stack.push(result);
        //返回已经拿到的最后值
        return last;
}
    //2.将拿出来的最后值 一步步压入栈
    public static void reverse(Stack<Integer> stack){
        if (stack.isEmpty()){
            return;
        }
        int i=f(stack);
        //依次拿出last值
    reverse(stack);
    //再从最后一个数压入栈
    stack.push(i);
}


//打印一个字符串全部子序列
    //index 当前位置
    //ans 所有子序列
    //path 某一子序列
    //步骤：index上面的值 拿还是不拿
    public static void process1(char[] str, int index, List<String> ans,String path){
        if (index==str.length){
            ans.add(path);
            return;
        }
        String no=path;
        process1(str,index+1,ans,no);
        String yes=path+ str[index];
        process1(str,index+1,ans,yes);
    }

//打印一个字符串全部子序列 不要出现重复的字面值子序列
public static void process1(char[] str, int index, HashSet<String> ans, String path){
    if (index==str.length){
        ans.add(path);
        return;
    }
    String no=path;
    process1(str,index+1,ans,no);
    String yes=path+ str[index];
    process1(str,index+1,ans,yes);
}

//全排列
    //i是当前位置
    //res是答案
public static void process2(char [] str, int i, ArrayList<String> res){
        if (i==str.length){
            res.add(String.valueOf(str));
        }
        //i后面的所有字符都可以交换
    for (int j = i; j <str.length; j++) {
        swap(str,i,j);
        process2(str,i+1,res);
        //还原现场
        swap(str,i,j);
    }
}
public static void swap(char []str,int p1,int p2){
        char tmp=str[p1];
        str[p1]=str[p2];
        str[p2]=tmp;
}
    //全排列  不出现重复排列
    //分支限界（可能出现的不再出现）
public static void process3(char [] str, int i, ArrayList<String> res){
    if (i==str.length){
        res.add(String.valueOf(str));
    }
    HashSet<String> set=new HashSet<>();
    for (int j = i; j <str.length ; j++) {
        if (!set.contains(String.valueOf(str[j]))){
            set.add(String.valueOf(str[j]));
            swap(str,i,j);
            process3(str,i+1,res);
            swap(str,i,j);
        }
    }
}


//从左往右尝试模型1
//规定1和A对应、2和B对应、3和C对应 26字母依次对应
//那么一个数字字符串比如“111”就可以转化为：
//"AAA”
//“KA"和“AK"
//给定一个只有数字字符组成的字符串str，返回有多少种转化结果
    //i是当前位置
    //步骤：1.index的值单个拿出来
    //2.index与index+1的值一起拿出来
    public static int ways(String s){
        if (s==null||s.length()==0){
            return 0;
        }
        char[] str=s.toCharArray();
        return process4(str,0);
    }

    public static int process4(char [] str,int i){
        if (i==str.length){
            return 1;
        }
        if (str[i]=='0'){
            return 0;
        }
        if (str[i]=='1'){
            int res=process4(str,i+1);
            if (i+1<str.length){
                res+=process4(str,i+2);
            }
            return res;
        }
        if (str[i]=='2'){
            int res=process4(str,i+1);
            if (i+1<str.length&&(str[i+1]>'0'&&str[i+1]<='6')){
                res=process4(str,i+2);
            }
            return res;
        }
        return process4(str,i+1);

    }
    //动态规划

    public static int ways2(String i){
        if (i==null||i.length()==0){
            return 0;
        }
        char[] s=i.toCharArray();
        int N=s.length;
        int[] dp = new int[N + 1];
        dp[N]=1;
        //dp生成
        for (int j = N-1; j >=0 ; j--) {
            if (s[j]=='0'){
                dp[j]=0;
            }
            else if (s[j]=='1'){
                dp[j]=dp[j+1];
                if (j+1<s.length){
                    dp[j]=dp[j]+dp[j+2];
                }
            }
            else if (s[j]=='2'){
                dp[j]=dp[j+1];
                if (j+1<s.length&&(s[j+1]>'0'&&s[j+1]<='6')){
                    dp[j]+=dp[j+2];
                }
            }else {
                dp[j]=s[j+1];
            }

//            if (i==str.length){
//                return 1;
//            }
//            if (str[i]==0){
//                return 0;
//            }
//            if (str[i]==1){
//                int res=process4(str,i+1);
//                if (i+1<str.length){
//                    res+=process4(str,i+2);
//                }
//                return res;
//            }
//            if (str[i]==2){
//                int res=process4(str,i+1);
//                if (i+1<str.length&&(str[i+1]>'0'&&str[i+1]<='6')){
//                    res=process4(str,i+2);
//                }
//                return res;
//            }
//            return process4(str,i+1);
        }
        return dp[0];

    }






//从左往右的尝试模型2
//给定两个长度都为N的数组weights和values,
//weights[i]和values[i]可分别代表i号物品的重量和价值。
//给定一个正数bag，表示一个载重bag的袋子，
//你装的物品不能超过这个重量
//返回你能装下最多的价值是多少？
    //i装了多少
    //index 当前的包
    public static int process5(int [] weight,int [] values,int bag,int i,int index){
        if (i>bag){
            return -1;
        }
        //index 到最后一个了 没东西选了
        if (index== weight.length){
            return 0;
        }
        //没装
        int p1=process5(weight,values,bag,i,index+1);
        //装 但是怕超标了
        int p2Next=process5(weight,values,bag,i+weight[index],index+1);
        int p2=-1;
        if (p2Next!=-1){
            p2=p2Next+values[index];
        }
        return Math.max(p1,p2);
    }
    //bag还剩多少
    public static int process6(int [] weight,int [] values,int index,int bag){
        if (bag<=0){
            return 0;
        }
        if (index==weight.length){
            return 0;
        }
        int p1=process6(weight,values,index+1,bag);

        int p2=-1;
        //背包能装下再装
        if (bag>=weight[index]){
            p2=values[index]+process6(weight,values,index+1,bag-weight[index]);
        }
        return Math.max(p1,p2);
    }

    //动态规划
    public static int dpWay(int []w, int[] v, int bag){
        int n=w.length;
        int [][]dp=new int[n+1][bag+1];
        for (int index = n-1; index >=0; index--) {
            for (int rest = 0; rest <=bag ; rest++) {
                //和注释那段一一对应
                int p1=dp[index+1][rest];
                int p2=-1;
                if (rest>w[index]){
                    p2=v[index]+dp[index+1][rest-w[index]];
                }
                dp[index][rest]=Math.max(p1,p2);

//                int p1=process6(w,v,index+1,bag);
//                int p2=-1;
//                if (bag>=w[index]){
//                    p2=v[index]+process6(w,v,index+1,bag-w[index]);
//                }
//                return Math.max(p1,p2);
            }
        }
        return dp[0][bag];
    }




    //范围上尝试模型
    //给定一个整数数组arr，代表数值不同的纸牌排成一条线
    //玩家A和玩家B一次拿走每张纸牌
    //规定玩家A先拿，玩家B后拿
    //但是每个玩家每次只能拿走最左最右的纸牌
    //玩家A和玩家B绝顶聪明，请返回最后获胜者分数
    //我是先手拿牌人
    //步骤：拿牌+等拿牌+拿牌。。。。
    public static int f(int[] arr, int l, int r){
        if (l==r){
            return arr[l];
        }
        //先手选了,下一步我就是后手没牌拿的人所以 是牌+我的后手
        return Math.max(arr[l]+s(arr,l+1,r),arr[r]+s(arr,l,r-1));
    }
    //后手等拿牌人
    public static int s(int[] arr, int l, int r){
        //只剩一张牌 你是没有牌可以拿的
        if (l==r){
            return 0;
        }
        //我在等拿牌
        return Math.min(f(arr,l+1,r),f(arr, l, r-1));
    }

    public static int win(int[] arr){
        if (arr==null||arr.length==0){
            return 0;
        }
        //拿牌人 就是先手 等拿牌人就是后手
        return Math.max(f(arr,0,arr.length-1),s(arr,0,arr.length-1));

    }

    //动态规划
    public static int dPwin(int []arr){
        if (arr==null||arr.length==0){
            return 0;
        }
        int N=arr.length;
        int [][]f=new int[N][N];
        int [][]s=new int[N][N];
        for (int i = 0; i <N; i++) {
            f[i][i]=arr[i];
            //可省
            s[i][i]=0;
        }

        for (int i = 1; i <N ; i++) {
            int l=0;
            int r=i;
            while (r<N&&l<N){
                f[l][r]=Math.max(arr[l]+s[l+1][r],arr[r]+s(arr,l,r-1));
                s[l][r]=Math.min(f[l+1][r],f[l][r-1]);

                r++;
                l++;
            }

        }
return Math.max(f[0][arr.length-1],s[0][arr.length-1]);
    }



    //n皇后问题
    //i行
    //m指的是位置m[0]=7 0行7列
    //总n行
    public static int num(int n){
        if (n<=1){
            return 0;
        }
        int [] m=new int[n];
        return process7(m,0,n);
    }

    public static int process7(int [] m,int i,int n){
        if (i==n){
            return 1;
        }
        int res=0;
        for (int j = 0; j <n ; j++){
            if (isOk(m,i,j)){
                m[i]=j;
                res+=process7(m,i+1,n);
            }
        }
return res;
    }

    //j为列
    //i为行
    //a b c d
    //斜边
    //|a-c|==|b-d|
    public static boolean isOk(int [] m,int i,int j){
        for (int k = 0; k <i ; k++) {
            if (j==m[k]||Math.abs(i-k)==Math.abs(j-m[k])){
                return false;
            }
        }
return true;
    }

    //位运算优化 n皇后
    //不要超过32
public static int num2(int n){
        if (n<1||n>32){
            return 0;
        }

        //如果你是8皇后问题 最右8个1 其他是0
    //如果是32皇后问题  那么就是-1  因为 -1 全是1
    //不是32皇后问题 把1左移动n位  再减1 把高位变低位
        int limit= n==32 ?-1:(1<<n)-1;
        return process8(limit,0,0,0);
}

    //limit 划定规模
    //colLimit 列的限制 1不能放皇后 0可以
    //left 左限制
    //right 右限制
public static int process8(int limit, int colLimit,int leftDiam,
                           int rightDiam){
        //所有为1
        //完成组合
        if (colLimit==limit){
            return 1;
        }
        //可选位置pos
    //colLimit|leftDiam|rightDiam 总限制 1是限制数
    //~(colLimit|leftDiam|rightDiam) 把没有限制的位置变成1
    //limit&(~(colLimit|leftDiam|rightDiam)) 把前面一群1 变成0
        int pos= limit&(~(colLimit|leftDiam|rightDiam));

        int mostRightOne=0;
        int res=0;

        while (pos!=0){

            //取出pos最右的1 剩下0  即皇后放置的位置
            mostRightOne=pos&(~pos+1);

            //皇后放置结束 该位置变限制 减或者异或
            pos=pos-mostRightOne;

            res+=process8(limit,
                    colLimit|mostRightOne,
                    (leftDiam|mostRightOne)<<1,
                    //>>>逻辑右移运算符，也称无符号右移 只对位进行操作，用0填充左侧的空位。
                    // >>算术右移运算符，也称带符号右移。用最高位填充移位后左侧的空位。
                    (rightDiam|mostRightOne)>>>1
                    );
        }
        return res;
}

//假设有排成一行的N个位置，记为1~N， N一定大于或等于 2
//开始时机器人在其中的M位置上（M 一定是 1~N 中的一个
//如果机器人来到1位置，那么下一步只能往右来到2位置;
//如果机器人来到N位置，那么下一步只能往左来到 N-1 位置
//如果机器人来到中间位置，那么下一步可以往左走或者往右走
//规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
//给定四个参数 N、M、K、P，返回方法数。

    public static int rebortMove(int n,int m,int k,int p){
        return walk(n,m,k,p);
    }
    public static int walk(int n,int cur,int rest,int p){
        if (rest==0){
            return cur==p?1:0;
        }
        if (cur==1){
            return walk(n,cur+1,rest-1,p);
        }
        if(cur==n){
            return walk(n,cur-1,rest-1,p);
        }

        return walk(n,cur+1,rest-1,p)+
        walk(n,cur-1,rest-1,p);
    }

    //优化
    //加缓存
    //记忆化搜索
    public static int rebortMove2(int n,int m,int k,int p){
        //当前位置  以及多少步
        int [][]dp=new int[n+1][k+1];
        for (int i = 0; i <=n ; i++) {
            for (int j = 0; j <=k ; j++) {
                dp[i][j]=-1;
            }
        }
        return walk(n,m,k,p);
    }
    public static int walk2(int n, int cur, int rest, int p, int[][] dp){
        if ( dp[cur][rest]!=-1){
            return  dp[cur][rest];
        }
        if (rest==0){
            dp[cur][rest]=cur==p?1:0;
            return dp[cur][rest];
        }
        if (cur==1){
            dp[cur][rest]=walk(n,cur+1,rest-1,p);
            return  dp[cur][rest];
        }
        if(cur==n){
            dp[cur][rest]=walk(n,cur-1,rest-1,p);
            return dp[cur][rest];
        }
        dp[cur][rest]=walk(n,cur+1,rest-1,p)+
                walk(n,cur-1,rest-1,p);
        return dp[cur][rest];
    }

    //有一组钱币面值arr  可以任意使用数量 如：两张7 三张3
    //求有多少种方式对特定数进行组合   如：[7,3,100,50]  组合成1000
    public static int moneyWay2(int []arr,int aim){
        if (arr==null||arr.length==0||aim<0){
            return 0;
        }
        return processMoney2(arr,aim,0);
    }
    public static int processMoney2(int []arr,int rest,int index){
        if (index==arr.length){
            return rest==0?1:0;
        }
        int ways=0;
        for (int i = 0; i*arr[index]<=rest ; i++) {
            ways+=processMoney2(arr,rest-(i*arr[index]),index+1);
        }
return ways;

    }
//记忆搜索
    public static int moneyWay1(int[] arr, int aim){
        if (arr==null||arr.length==0||aim<0){
            return 0;
        }
        HashMap<String,Integer> map=new HashMap<>();
        return processMoney1(arr,aim,0, map);
    }
    public static int processMoney1(int []arr,int rest,int index,HashMap<String,Integer> map){
        if (map.containsKey(index +"_"+ rest)){
            return map.get(index +"_"+ rest);
        }
        if (rest<0){
            map.put(index +"_"+ rest,0);
            return 0;
        }
        if (index==arr.length){
            if (rest==0){
                map.put(index +"_"+ rest,1);
                return 1;
            }else {
                map.put(index +"_"+ rest,0);
                return 0;
            }
        }
        int ways=0;
        for (int i = 0; i*arr[index]<=rest ; i++) {
            ways+=processMoney1(arr,rest-(i*arr[index]),index+1,map);
        }
        map.put(index +"_"+ rest,ways);
        return ways;

    }

    //经典动态规划
    public static int moneyDp(int[] arr, int aim){
        if (arr==null||arr.length==0||aim<0){
            return 0;
        }
        int N=arr.length;
        int [][]dp=new int[N+1][aim+1];
        dp[N][0]=1;
        for (int index = N-1; index >=0 ; index--) {
            for (int rest = 0; rest <=aim ; rest++) {
                int ways=0;
                for (int i = 0; i*arr[index]<=rest ; i++) {
                    ways+=dp[index+1][rest-(i*arr[index])];
                }
                dp[index][rest]=ways;
            }

        }
return dp[0][aim];
    }

    public static void main(String[] args) {
        int arr[]={10,20};
        int aim=100;
        System.out.println(moneyWay2(arr, aim));
        System.out.println(moneyWay1(arr, aim));
        System.out.println(moneyDp(arr, aim));
    }








}
