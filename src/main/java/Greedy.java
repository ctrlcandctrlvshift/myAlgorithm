import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * @author zhang
 * @ClassName: Greedy
 * @Package PACKAGE_NAME
 * @Description: 贪心算法
 * @date 2021/12/219:51
 */
public class Greedy {


    public static class Program{
        int start;
        int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    //暴力递归方法
    //还剩什么会议都放在programs里
    //done 之前安排过的会议的数量
    //timeline目前来到时间点是什么

    //目前来到timeLine的时间点，已经安排了done多的会议，剩下的会议progarms可以自由安排
    //返回能安排的最多会议数量
    public static int process1(Program[] programs,int done,int timeLine){
        if (programs.length==0){
            return done;
        }
        //安排会议最多
        int max=done;
        //当前安排的会议是什么  枚举
        for (int i = 0; i < timeLine; i++) {
            //时间在之后
            if (programs[i].start>=timeLine){
                //删掉会议
                Program[] next=copyButExcept(programs,i);
                max=Math.max(max,process1(next,done+1,programs[i].end));
            }
        }
        return max;
    }
    public static Program[] copyButExcept(Program[] programs,int i){
        Program[]ans =new Program[programs.length-1];
        int index=0;
        for (int j = 0; j < programs.length; j++) {
            if (i!=j){
                ans[index++]=programs[j];
            }
        }
return ans;
    }
    public static int bestArrange1(Program[] programs){
        if (programs.length==0||programs==null){
            return 0;
        }
       return process1(programs,0,0);
    }
    //贪心
    public static int bestArrange2(Program[] programs){
        Arrays.sort(programs,new ProgramComparator());
        int timeLine=0;
        int result=0;
        for (int i = 0; i <programs.length ; i++) {
            if (programs[i].start>=timeLine){
                result++;
                timeLine=programs[i].end;
            }
        }
return result;
    }
    public static class ProgramComparator implements Comparator<Program>{
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end-o2.end;
        }
    }


    //给定一个字符串str,
    //只由X 和：两种宇符构成。
    //X表示墙，不能放灯，也不需要点亮
    //.表示居民点，可以放灯，需要点亮
    //如果灯放在i位置，可以让i-1，和i+1三个位置被点亮
    //返回如果点亮str中所有需要点亮的位置，至少需要几盏灯

    //暴力解
    public static void putLight(String str){
       if (str==null){
           return;
       }
       process2(str.toCharArray(),0,new HashSet<Integer>());
    }
    //index 当前值
    //lighs 存储该位置是否点灯
    //返回的最小放灯数
    public static int process2(char[] str , int index, HashSet<Integer> lights){
        //当所有位置都被挑完了
        if (index==str.length){
            //判断你选择的方案是否所有位置都被点亮
            for (int i = 0; i <str.length ; i++) {
                //是点位置
                if (str[i]!='X'){
                    //判断需要被照亮的是否有灯
                    if (!lights.contains(i-1)&&!lights.contains(i)&&!lights.contains(i+1)){
                        //方案失败
                        return Integer.MAX_VALUE;
                    }
                }
            }
            //返回放了多少灯
            return lights.size();
        }//方案还没结束
        //递归index是否放灯
        else {
            //不放灯
            int no=process2(str,index+1,lights);
            //放灯
            int yes=Integer.MAX_VALUE;
            if (str[index]=='.'){
                lights.add(index);
                //加灯看后面什么情况
                yes=process2(str,index+1,lights);
                //还原现场
                lights.remove(index);
            }
            //判断是放灯小还是不放灯小

            return Math.min(no,yes);
        }
    }

    //贪心
    public static int putLight2(String str){
        char[]chars = str.toCharArray();
        int index=0;
        int light=0;
        while (index<str.length()){
            if (chars[index]=='X'){
                index++;
            }else {
                //遇到点无论如何必加灯
                light++;
                //你是最后一个了加灯走人
                if (index+1==chars.length){
                    break;
                }else {
                    //下一个是叉 可以去看下下个了
                 if (chars[index+1]=='X'){
                     index=index+2;
                     //下一个是点 可以跳到第四个
                 } else {
                  index= index+3;
                 }
                }
            }
        }
        return light;
    }



    //分金条
    //小根堆 大根堆
//    一块金条切成两半，是需要花费和长度数值一样的铜板的。
//    比如长度为20的金条，不管怎么切，都要花费20个铜板。
//    一群人想整分整块金条，怎么分最省铜板？
//    例如,给定数组（10,20,30)，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
//    如果先把长度60的金条分成10和50，花费60;再把长度50的金条分成20和30，花费50:一共花费110铜板。
//    但如果先把长度60的金条分成30和30，花费60;再把长度30金条分成10和20，
//    花费30;一共花费90铜板
//    输入一个数组，返回分割的最小代价
    //贪心
    //哈夫曼树
    public static int lessMoney2(int [] arr){

        PriorityQueue<Integer> pq=new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pq.add(arr[i]);
        }
        int sum=0;
        int cur=0;
        while (pq.size()>1){
            cur = pq.poll()+pq.poll();
            sum+=cur;
            pq.add(cur);
        }
        return sum;
    }


//    输入：正数数组costs、正数数组profits正数K、正数M
////            costs[i]表示1号项目的花费
////    profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润）
////            。K表示你只能串行的最多做k个项目
////            M表示你初始的资金
////            说明：每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
////            输出：你最后获得的最大钱数。

    public static class Project{
        public int cost;
        public int profit;

        public Project(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }
    public static class MaxProfitComparator implements Comparator<Project>{
        @Override
        public int compare(Project o1, Project o2) {
            return o2.profit-o1.profit;
        }
    }
    public static class MinCostsComparator implements Comparator<Project>{
        @Override
        public int compare(Project o1, Project o2) {
            return o1.cost-o2.cost;
        }
    }
    public static int findMaximizedCapital(int K,int W,int [] costs,int [] profits ){
        PriorityQueue<Project> minCosts=new PriorityQueue<>(new MinCostsComparator());
        PriorityQueue<Project> maxProfit=new PriorityQueue<>(new MaxProfitComparator());

        for (int i = 0; i <costs.length ; i++) {
            minCosts.add(new Project(costs[i],profits[i]));
        }

        for (int i = 0; i <K ; i++) {
            while (!minCosts.isEmpty()&&minCosts.peek().cost<=W){
                maxProfit.add(minCosts.poll());
            }
            if (maxProfit.isEmpty()){
                return W;
            }

            W+=maxProfit.poll().profit;

        }

return W;

    }


















}
