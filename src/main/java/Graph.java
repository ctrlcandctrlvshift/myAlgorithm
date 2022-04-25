import org.w3c.dom.Node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author zhang
 * @ClassName: graph
 * @Package PACKAGE_NAME
 * @Description: 图
 * @date 2022/4/149:00
 */
public class Graph {

    //给定一个变量对方程数组和一个实数数组，其中equations[i] = [Ai, Bi]和values[i]表示方程Ai / Bi =values[i]。
    // 每个Ai或Bi都是表示单个变量的字符串。
    //还会给您一些queries，其中queries[j] = [Cj, Dj]表示第j个查询，您必须在其中找到Cj / Dj = ?的答案。
    //返回所有查询的答案。 如果无法确定单个答案，则返回-1.0。
    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int n=equations.size();
        GraphT1 g=new GraphT1();
        for (int i = 0; i <n ; i++) {
            if (g.nodes.containsKey(equations.get(i).get(0))){
                Node from=g.nodes.get(equations.get(i).get(0));
                if (g.nodes.containsKey(equations.get(i).get(1))){

                    Node next=g.nodes.get(equations.get(i).get(1));
                    Edge edge1=new Edge(values[i],from,next);
                    Edge edge2=new Edge(1/values[i],next,from);

                    from.nexts.add(next);
                    from.map.put(next, edge1);

                    next.nexts.add(from);
                    next.map.put(from, edge2);

                    g.edges.add(edge1);
                    g.edges.add(edge2);

                }else {
                    Node next=new Node(equations.get(i).get(1));
                    Edge edge1=new Edge(values[i],from,next);
                    Edge edge2=new Edge(1/values[i],next,from);
                    g.nodes.put(equations.get(i).get(1),next);

                    from.map.put(next, edge1);
                    from.nexts.add(next);

                    next.map.put(from, edge2);
                    next.nexts.add(from);

                    g.edges.add(edge1);
                    g.edges.add(edge2);
                }
            }else {
                Node from=new Node(equations.get(i).get(0));
                if (g.nodes.containsKey(equations.get(i).get(1))){
                    Node next=g.nodes.get(equations.get(i).get(1));

                    Edge edge1=new Edge(values[i],from,next);
                    Edge edge2=new Edge(1/values[i],next,from);
                    from.nexts.add(next);
                    from.map.put(next, edge1);
                    next.map.put(from, edge2);
                    next.nexts.add(from);

                    g.edges.add(edge1);
                    g.edges.add(edge2);
                }else {
                    Node next=new Node(equations.get(i).get(1));
                    Edge edge1=new Edge(values[i],from,next);
                    Edge edge2=new Edge(1/values[i],from,next);
                    g.nodes.put(equations.get(i).get(1),next);
                    from.map.put(next, edge1);
                    from.nexts.add(next);
                    next.map.put(from, edge2);
                    next.nexts.add(from);

                    g.edges.add(edge1);
                    g.edges.add(edge2);
                }
                g.nodes.put(equations.get(i).get(0),from);
            }
        }
        double []res=new double[queries.size()];

        for (int i = 0; i <queries.size() ; i++) {
            if (g.nodes.containsKey(queries.get(i).get(0))){
                if (queries.get(i).get(0).equals(queries.get(i).get(1))){
                    res[i]=1;
                    continue;
                }
                Node from=g.nodes.get(queries.get(i).get(0));
                double ans=equation(from,queries.get(i).get(1),new HashSet<String>(),1);
                res[i]=ans;
                continue;
            }
            res[i]=-1;
        }

        return res;
    }
    public static double equation(Node node,String target,HashSet <String>set,double res){
        if (node.name.equals(target)){
            return res;
        }
        int n=node.nexts.size();
        if (n==1&&set.contains(node.nexts.get(0).name)){
            return -1;
        }
        double ans=-1;
        set.add(node.name);

        for (int i = 0; i <n ; i++) {
            if (ans!=-1){
                break;
            }
            if (!set.contains(node.nexts.get(i).name)){
               ans= equation(node.nexts.get(i),target,set,res*node.map.get(node.nexts.get(i)).value);
            }
        }

        return ans;
    }
    public static class Node{
        String name;
        //出度的点
        public ArrayList<Node> nexts;
        public HashMap<Node,Edge> map;



        public Node(String name) {
            this.name = name;
            this.nexts = new ArrayList<>();
            this.map=new HashMap<>();
        }
    }
    public static class Edge{
        double value;
        Node from;
        Node to;

        public Edge(double value, Node from, Node to) {
            this.value = value;
            this.from = from;
            this.to = to;
        }
    }
    public static class GraphT1{
        HashMap <String ,Node>nodes;
        HashSet <Edge>edges;

        public GraphT1() {
            this.nodes = new HashMap<>();
            this.edges = new HashSet<>();
        }
    }

    //给定一个nxn的二进制矩阵网格。 您最多可以更改一个0为1。
    //应用此操作后，返回网格中最大岛屿的大小。
    //岛是由4个方向相连的1组成的群。

    public static int largestIsland(int[][] grid) {
        if (grid==null){
            return 0;
        }
        int max=Integer.MIN_VALUE;
        boolean target=true;
        HashMap<Integer, Integer> map=getIsland(grid);
        for (int i = 0; i <grid.length ; i++) {
            for (int j = 0; j <grid[0].length ; j++) {
                int ans=0;
                if (grid[i][j]==0){
                    target=false;
                   HashSet <Integer>color=new HashSet();
                    int colorUp=i==0?0:grid[i-1][j];
                    int colorDown=i==grid.length-1?0:grid[i+1][j];
                    int colorLeft=j==0?0:grid[i][j-1];
                    int colorRight=j==grid.length-1?0:grid[i][j+1];
                    if (colorUp!=0){
                        color.add(colorUp);
                        ans+=map.get(colorUp);
                    }
                    if (colorDown!=0){
                        if (!color.contains(colorDown)){
                            ans+=map.get(colorDown);
                            color.add(colorDown);
                        }
                    }
                    if (colorLeft!=0){
                        if (!color.contains(colorLeft)){
                            ans+=map.get(colorLeft);
                            color.add(colorLeft);
                        }
                    }
                    if (colorRight!=0){
                        if (!color.contains(colorRight)){
                            ans+=map.get(colorRight);
                            color.add(colorRight);
                        }
                    }

                    ans++;
                    max=Math.max(max,ans);
                }
            }
        }

     return target?grid.length*grid[0].length:max;
    }
    public static HashMap<Integer, Integer> getIsland(int [][]grid){
        Integer [][]dp=new Integer[grid.length+1][grid[0].length+1];
        int color=2;
        HashMap<Integer, Integer> map=new HashMap<>();
        for (int i = 0; i <grid.length ; i++) {
            for (int j = 0; j <grid[0].length ; j++) {
                if (grid[i][j]==1){
                 int num=process2(grid,i,j,dp,color);
                 map.put(color,num);
                 color++;
                }
            }
        }
        return map;
    }
    //找岛有多大
    public static int process2(int [][]grid,int r,int c,Integer [][]dp,int color){
        if (r<0||c<0||r>=grid.length||c>=grid[0].length||grid[r][c]!=1){
            return 0;
        }
        if (dp[r][c]!=null){
            return dp[r][c];
        }
        grid[r][c]=color;
        int p1=process2(grid,r+1,c,dp,color);
        int p2=process2(grid,r-1,c,dp,color);
        int p3=process2(grid,r,c+1,dp,color);
        int p4=process2(grid,r,c-1,dp,color);
        dp[r][c]=p1+p2+p3+p4+1;
        return dp[r][c];
    }

//你有一个由n个节点标记为0到n - 1的无向连通图。
// 给定一个数组图，graph[i]是一个由节点i通过一条边连接的所有节点组成的列表。
//返回访问每个节点的最短路径的长度。 您可以在任何节点开始和停止，可以多次访问节点，也可以重用边。
    //dfs
public static int shortestPathLength(int[][] graph) {
        if (graph==null){
            return 0;
        }
        int N=graph.length;
        int endTarget=(1<<N)-1;
        int min=Integer.MAX_VALUE;
    for (int i = 0; i <N ; i++) {
        int []pathMark=new int[N];
        min=Math.min(min,processPath(graph,i,pathMark,(1<<i),endTarget,0));
    }
        return min;
}
public static int processPath(int [][]graph,int node,int []pathMark,int endMark,int endTarget,int pathRes){
        int n=graph[node].length;
        int pathEndMark=(1<<n)-1;
        int mark=pathMark[node];
        if (endMark==endTarget){
            return pathRes;
        }
        if (mark==pathEndMark){
            return Integer.MAX_VALUE;
        }
        int ans=Integer.MAX_VALUE;
        for (int i = 0; i <n ; i++) {
            if ((mark&(1<<i))==0){
                pathMark[node]=(mark^(1<<i));
                if ((endMark&(1<<graph[node][i]))==0){
                    int p1=processPath(graph,graph[node][i],pathMark,endMark^(1<<graph[node][i]),endTarget,pathRes+1);
                    ans=Math.min(ans,p1);
                }else {
                    int p2=processPath(graph,graph[node][i],pathMark,endMark,endTarget,pathRes+1);
                    ans=Math.min(ans,p2);
                }
                pathMark[node]=mark;
            }
        }
        return ans;
}

//bfs
public static int shortestPathLength2(int[][] graph) {
        if (graph==null||graph[0]==null){
            return 0;
        }
        int n=graph.length;
        ArrayList<int[]> q=new ArrayList<>();
        int endMark=(1<<n)-1;
        boolean [][]seen=new boolean[n][endMark+1];
        for (int i = 0; i <n ; i++) {
            q.add(new int[]{i,(1<<i)});
            seen[i][1<<i]= true;
        }
        int step=0;
        while (!q.isEmpty()){
            ArrayList <int[]>newq=new ArrayList<>();
            for (int k = 0; k <q.size() ; k++) {
                int []cur=q.get(k);
                int node=cur[0];
                int mark=cur[1];
                for (int i = 0; i <graph[node].length; i++) {
                    int nextMark=mark|(1<<graph[node][i]);
                    int next=graph[node][i];

                    if (nextMark==endMark){
                        return step+1;
                    }
                    if (!seen[next][nextMark]){
                        newq.add(new int[]{next,nextMark});
                        seen[next][nextMark]=true;
                    }
                }
            }
            step++;
            q=newq;
        }
        return 0;
}

//有一个总数numCourses课程你必须采取，标记从0到numCourses - 1。您将得到一个数组先决条件，其中先决条件[i] = [ai, bi]表示如果您想学习课程ai，就必须首先学习课程bi。
//例如，配对[0,1]表示要选择课程0，必须先选择课程1。
//如果你能完成所有课程返回true。否则,返回false。

    static HashSet <Integer> t=new HashSet<>();
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites==null||prerequisites.length==0||prerequisites[0].length==0){
            return true;
        }
         HashMap<Integer,List<Integer>>graph=new HashMap<>();
        int []dp=new int[numCourses];

        for (int i = 0; i <prerequisites.length ; i++) {
            int key=prerequisites[i][0];
            int value=prerequisites[i][1];
            if (graph.get(key)!=null){
                List tmp=graph.get(key);
                tmp.add(value);
            }else {
                List tmp=new ArrayList();
                tmp.add(value);
                graph.put(key,tmp);
            }
        }
        for (int key:graph.keySet()){
            t=new HashSet<>();
            if (!isOk(graph,key,dp)){
                return false;
            }
            dp[key]=1;
        }
        return true;
}
    public  static boolean isOk(HashMap<Integer, List<Integer>>graph,int pre,int [] dp){
        List <Integer>list=graph.get(pre);
        if (list==null){
           return true;
        }
        if (dp[pre]!=0) {
            return true;
        }
        t.add(pre);
        for (int i = 0; i <list.size() ; i++) {
            int next=list.get(i);
            if (t.contains(next)){
                return false;
            }
            boolean ans=isOk(graph,next,dp);
            t.remove(next);
            if (!ans){
                return false;
            }
        }
        return true;
    }

//有一个总数numCourses课程你必须采取，标记从0到numCourses - 1。
// 您将得到一个数组先决条件，其中先决条件[i] = [ai, bi]表示如果您想学习课程ai，就必须首先学习课程bi。
//例如，配对[0,1]表示要选择课程0，必须先选择课程1。
//交回你应修课程的顺序，以完成所有课程。 如果有许多有效的答案，返回其中任何一个。 如果不可能完成所有课程，则返回一个空数组。
    static HashMap<Integer, List<Integer>> dp=new HashMap<>();
    static HashSet <Integer> loop;
    static List<Integer> keyPath;
    static HashSet<Integer> rep;
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses==0){
            return new int[0];
        }
    if (prerequisites==null||prerequisites.length==0||prerequisites[0].length==0){
        int []res=new int[numCourses];
        for (int i = 0; i <numCourses ; i++) {
            res[i]=i;
        }
        return res;
    }

   ArrayList <Integer> res=new ArrayList<>();

    HashMap<Integer,List<Integer>>graph=new HashMap<>();
  //  int []dp=new int[numCourses];

    //生成图
    for (int i = 0; i <prerequisites.length ; i++) {
        int key=prerequisites[i][0];
        int value=prerequisites[i][1];
        if (graph.get(key)!=null){
            List tmp=graph.get(key);
            tmp.add(value);
        }else {
            List tmp=new ArrayList();
            tmp.add(value);
            graph.put(key,tmp);
        }
    }



    for (int key:graph.keySet()){
        loop=new HashSet<>();
        keyPath=new ArrayList<>();
        rep=new HashSet<>();
        if (!isDone(graph,key)){
            return new int[0];
        }
        keyPath.add(key);
        dp.put(key,keyPath);
    }
    ArrayList <Integer>heads=new ArrayList();
    for (int i = 0; i <numCourses ; i++) {
        heads.add(i);
        res.add(i);
    }
    for (int i = 0; i <prerequisites.length ; i++) {
        int value=prerequisites[i][1];
        int key=prerequisites[i][0];
        heads.remove((Integer)value);
        res.remove((Integer) value);
        res.remove( (Integer) key);
    }
    for (int head:heads){
        if (graph.containsKey(head)){
            List<Integer> path=dp.get(head);
            for (Integer t:path){
                if (!res.contains((Integer)t)){
                    res.add(t);
                }
            }
        }
    }
    int [] ans= new int[numCourses];
    Iterator it=res.iterator();
    int i=0;
    while (it.hasNext()){
        ans[i++]=(int)it.next();
    }
    return ans;
}
    public  static boolean isDone(HashMap<Integer, List<Integer>>graph,int pre){
        List <Integer>list=graph.get(pre);
        if (list==null){
            return true;
        }
        loop.add(pre);
        for (int i = 0; i <list.size() ; i++) {
            int next=list.get(i);
            if (loop.contains(next)){
                return false;
            }

            if (!isDone(graph,next)){
                return false;
            }
            if (!rep.contains(next)){
                keyPath.add(next);
            }

            rep.add(next);
            loop.remove(next);
        }
        return true;
    }



    public static void main(String[] args){
  //  int [][]prerequisites={{1,0},{2,6},{1,7},{6,4},{7,0},{0,5}};
    //  int [][]prerequisites={{1,4},{2,4},{3,1},{3,2}};
      //  int [][]prerequisites={{2,0},{1,0},{3,1},{3,2},{1,3}};
       int [][]prerequisites={{1,0},{2,0}};
    int numCourses=3;
      int [] res=findOrder(numCourses, prerequisites);
      for (int r:res){
          System.out.print(r);
      }
    }

}
