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
    //3步：
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

    public static void main(String[] args) {
        String i="我是谁";
        char[] j=i.toCharArray();
        for (int k = 0; k <j.length ; k++) {
            System.out.println(j[k]);
        }
    }

    //使用递归把栈逆序
    //把栈最后一数拿出来
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
    //将拿出来的最后值 一步步压入栈
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
    public static void process1(char[] str, int index, List<String> ans,String path){
        if (index==str.length){
            ans.add(path);
            return;
        }
        String no=path;
        process1(str,index+1,ans,no);
        String yes=path+String.valueOf(str[index]);
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
    String yes=path+String.valueOf(str[index]);
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
    public static int process4(char [] str,int i){
        if (i==str.length){
            return 1;
        }
        if (str[i]==0){
            return 0;
        }
        if (str[i]==1){
            int res=process4(str,i+1);
            if (i+1<str.length){
                res+=process4(str,i+2);
            }
            return res;
        }
        if (str[i]==2){
            int res=process4(str,i+1);
            if (i+1<str.length&&(str[i+1]>'0'&&str[i+1]<='6')){
                res=process4(str,i+2);
            }
            return res;
        }
        return process4(str,i+1);

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


    //范围上尝试模型
    //给定一个整数数组arr，代表数值不同的纸牌排成一条线
    //玩家A和玩家B一次拿走每张纸牌
    //规定玩家A先拿，玩家B后拿
    //但是每个玩家每次只能拿走最左最右的纸牌
    //玩家A和玩家B绝顶聪明，请返回最后获胜者分数
    //我是先手
    public static int f(int arr[],int l,int r){
        if (l==r){
            return arr[l];
        }
        //先手选了之后  后手选了之后先手再选得出的分数
        //两个动作：一步是我自己选 另外一步是大家一起选
        //ps:因为是两人游戏 先手不选的  必然是后手 所以总能返回最大值
//      arr[l]+s(arr,l+1,r)如果是先手  那么arr[r]+s(arr,l,r-1)必然是后手 反之也一样
        return Math.max(arr[l]+s(arr,l+1,r),arr[r]+s(arr,l,r-1));
    }
    //先手之后的步骤 先手是不知道后手拿哪个数，但是知道必然是拿大数，所以后续先手只能返回最小的数
    public static int s(int arr[],int l,int r){
        if (l==r){
            return arr[l];
        }
        //不知道后手拿的那一步  可以得知 后手必然拿最大那个数
        return Math.min(f(arr,l+1,r),f(arr, l, r-1));
    }


    //n皇后问题
    //i行
    //m指的是位置m[0]=7 0行7列
    //总n行
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











}
