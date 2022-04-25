import sun.misc.Unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhang
 * @ClassName: UnionFind
 * @Package PACKAGE_NAME
 * @Description: 并查集   图
 * @date 2021/12/2215:16
 */
public class UnionFind {

    //并查集
    public static class Node<V>{
        V value;

        public Node(V value) {
            this.value = value;
        }
    }
    public static class UnionSet<V>{
        //v 节点
        public HashMap<V,Node<V>> nodes;
        public HashMap<Node<V>,Node<V>> parents;
        //代表节点下的节点数
        public HashMap<Node<V>,Integer> sizeMap;

        public UnionSet(List<V> values){
            for (V value: values) {
                Node<V>node =new Node<>(value);
                nodes.put(value,node);
                parents.put(node, node);
                sizeMap.put(node,1);
            }
        }
        public UnionSet(Collection<V> values){
            for (V value: values) {
                Node<V>node =new Node<>(value);
                nodes.put(value,node);
                parents.put(node, node);
                sizeMap.put(node,1);
            }
        }
        public UnionSet(V [] values){
            for (V value: values) {
                Node<V>node =new Node<>(value);
                nodes.put(value,node);
                parents.put(node, node);
                sizeMap.put(node,1);
            }
        }

        public boolean isSameSet(V a,V b){
            if (!nodes.containsKey(a)||!nodes.containsKey(b)){
                return false;
            }
            return findFather(nodes.get(a))==findFather(nodes.get(b));
        }

        //找到不能找到的代表点
        public Node<V> findFather(Node<V> cur){
            Stack<Node<V>> path=new Stack<>();
            //所有进栈
            while (cur!=parents.get(cur)){
                path.push(cur);
                cur=parents.get(cur);
            }
            //经历的所有节点全挂在代表节点上
            while (!path.isEmpty()){
                parents.put(path.pop(),cur);
            }
            return cur;
        }

        public void union(V a,V b){
            if (!nodes.containsKey(a)||!nodes.containsKey(b)){
                return;
            }
            Node<V>aHead=findFather(nodes.get(a));
            Node<V>bHead=findFather(nodes.get(b));
            if (aHead!=bHead){
                int aSize=sizeMap.get(aHead);
                int bSize=sizeMap.get(bHead);
                if (aSize>=bSize){
                    parents.put(bHead, aHead);
                    sizeMap.put(aHead,aSize+bSize);
                    sizeMap.remove(bHead);
                }else {
                    parents.put(aHead, bHead);
                    sizeMap.put(bHead,aSize+bSize);
                    sizeMap.remove(aHead);
                }
            }
        }

        public int getSize(){
            return sizeMap.size();
        }
    }

    //给定一个m x n个二维二进制网格，它表示“1”(土地)和“0”(水)的地图，返回岛屿的数量。
    //岛屿被水包围，通过水平或垂直连接相邻的陆地而形成。 你可以假设网格的四边都被水包围着。
    public static int numIslands(char[][] grid) {
        if (grid==null||grid.length==0){
            return 0;
        }
        int count=0;
        for (int i = 0; i <grid.length ; i++) {
            for (int j = 0; j <grid[0].length ; j++) {
                if (grid[i][j]=='1'){
                    count++;
                    infectIslands(grid,i,j,grid.length-1,grid[0].length-1);
                }
            }
        }

        return count;
    }
    public static void infectIslands(char [][]m,int right,int down,int row,int col){
        if (right>row||down>col||down<0||right<0||m[right][down]=='0'||m[right][down]=='2'){
            return;
        }
        m[right][down]='2';
        infectIslands(m,right+1,down,row,col);
        infectIslands(m,right,down+1,row,col);
        infectIslands(m,right-1,down,row,col);
        infectIslands(m,right,down-1,row,col);
    }



    //在这个问题中，树是一个连通且无环的无向图。你得到一个图，它开始是一个树，有n个节点标记为1到n，
    // 并添加了一条额外的边。添加的边从1到n有两个不同的顶点，并且不是已经存在的边。
    // 图表示为一个长度为n的数组边，边[i] = [ai, bi]表示图中节点ai和bi之间存在一条边。返回一条可以删除的边，
    // 这样得到的图形就是一个有n个节点的树。如果有多个答案，则返回输入中最后出现的答案。
    public static int[] findRedundantConnection(int[][] edges) {
        if (edges==null){
            return null;
        }
        int n=edges.length;
        int []p=new int[n+1];
        int []s=new int[n+1];
        for (int i = 1; i <=n ; i++) {
            p[i]=i;
            s[i]=1;
        }
        int []ans=new int[2];

        for (int i = 0; i <n ; i++) {
            if (isUnion(s,p,edges[i][0],edges[i][1])){
                ans=edges[i];
            }
        }

        return ans;
    }

    public static int findp(int []p,int c1){
        if (c1==p[c1]){
            return c1;
        }
        c1=findp(p,p[c1]);
        return c1;
    }

    public static boolean isUnion(int []s,int []p,int c1,int c2){
        int p1=findp(p,c1);
        int p2=findp(p,c2);
        if (p1==p2){
            return true;
        }else {
            if (s[p1]>s[p2]){
                p[p2]=p1;
                s[p1]+=s[p2];
            }else {
                p[p1]=p2;
                s[p2]+=s[p1];
            }
        }
        return false;
    }
//有n个城市。其中一些是连接的，而另一些则不是。如果a市与b市直接相连，b市与c市直接相连，则a市与c市间接相连。
//一个省是由直接或间接相连的城市组成的一组，除省外没有其他城市。
//如果第i个城市和第j个城市是直接相连的，则isConnected[i][j] = 1，否则isConnected[i][j] = 0。
//返回省的总数。
    public static int findCircleNum(int[][] isConnected) {
        if (isConnected==null){
            return 0;
        }
        int n=isConnected[0].length;
        int []p=new int[n+1];
        int []s=new int[n+1];
        int []index=new int[n+1];
        for (int i = 1; i <=n ; i++) {
            p[i]=i;
            s[i]=1;
            index[i]=1;
        }
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <n ; j++) {
                if (isConnected[i][j]==1){
                 uoin(p,s,i+1,j+1,index);
                }
            }
        }
        int count=0;
        for (int i = 1; i <n+1 ; i++) {
            if (index[i]==1){
                count++;
            }
        }
        return count;
    }
    public static int findFa(int []p,int c1){
        if (p[c1]==c1){
            return p[c1];
        }
        return findFa(p,p[c1]);
    }
    public static void uoin(int []p,int []s,int c1,int c2,int []index){
        int p1=findFa(p,c1);
        int p2=findFa(p,c2);

        if (p1!=p2){
            if (s[p1]>s[p2]){
                p[p2]=p1;
                s[p1]+=s[p2];
                index[p1]=1;
                index[p2]=0;
            }else {
                p[p1]=p2;
                s[p2]+=s[p1];
                index[p2]=1;
                index[p1]=0;
            }
        }
    }

    //在这个问题中，有根树是这样一种有向图，其中只有一个节点(根)，其他所有节点都是这个节点的后代，
    // 加上每个节点都只有一个父节点，除了根节点没有父节点。
    //给定的输入是一个有向图，它开始是一个有根的树，有n个节点(从1到n的不同值)，并添加了一个额外的有向边。 添加的边从1到n有两个不同的顶点，并且不是已经存在的边。
    //得到的图是一个二维的边数组。 每个边的元素是一对[ui, vi]，表示连接节点ui和vi的有向边，其中ui是子vi的父节点。
    //返回一条可以删除的边，这样得到的图就是一个有n个节点的有根树。 如果有多个答案，则返回在给定2d数组中最后出现的答案。
    public static int[] findRedundantDirectedConnection(int[][] edges) {
        if (edges==null){
            return null;
        }
        int n=edges.length;
        int []p =new int[n+1];
        int []index=new int[n+1];
        for (int i = 1; i <=n ; i++) {
            p[i]=i;
        }
        int first=-1;
        int second=-1;
        Arrays.fill(index,-2);
        for (int i = 0; i <n ; i++) {
            if (index[edges[i][1]]!=-2){
                first=index[edges[i][1]];
                second=i;
                break;
            }
            index[edges[i][1]]=i;
        }
        for (int i = 0; i <n ; i++) {
            if (i==second){
                continue;
            }
            if (unionAndCheck(p,edges[i][0],edges[i][1])) {
                if (first==-1){
                    return edges[i];
                }else {
                    return edges[first];
                }
            }
        }
        return edges[second];
    }

    public static boolean  unionAndCheck(int []p,int c1,int c2){
        int p1=findFather(p,c1);
        int p2=findFather(p,c2);

        if (p1!=p2){
            p[p2]=p1;
        }else {
             return true;
        }
        return false;
    }

    public static int findFather(int []p,int c){
        if (p[c]==c){
            return c;
        }
        return findFather(p,p[c]);
    }


    public static void main(String[] args) {
        int [][]edges={{1,2},{2,3},{3,1},{4,1}};
        int []edge=findRedundantDirectedConnection(edges);
        for (int i:edge){
            System.out.print(i);
        }
    }



    //如果两个user a字段一样 或者b 或者c一样 就认为是一个人
    //请返回合并后的用户数
    public static class User{
        public String a;
        public String b;
        public String c;

        public User(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
    public static int mergeUsers(User [] users){
        List <User>list=Arrays.asList(users);
        HashMap<String, User> mapa = new HashMap<>();
        HashMap<String, User> mapb = new HashMap<>();
        HashMap<String, User> mapc = new HashMap<>();
        UnionSet<User> userUnionSet=new UnionSet<User>(list);
        //内存地址并的 不会存在重复
        for (User user: list) {
            if (mapa.containsKey(user.a)){
                userUnionSet.union(user,mapa.get(user.a));
            }else {
                mapa.put(user.a,user);
            }
            if (mapb.containsKey(user.b)){
                userUnionSet.union(user,mapb.get(user.b));
            }else {
                mapb.put(user.b,user);
            }
            if (mapc.containsKey(user.c)){
                userUnionSet.union(user,mapc.get(user.c));
            }else {
                mapc.put(user.c,user);
            }
        }
        return userUnionSet.getSize();


    }


    //岛问题
    //【题目
    //一个矩阵中只有0和1两种值，每个位置都可以和自己的上、下、左、右 四个位置相连，如
    //果有一片1连在一起，这个部分叫做一个岛，求一个矩阵中有多少个岛？
    //【举例】
    //001010
    //111010
    //100100
    //000000
    //这个矩阵中有三个岛
    //【进阶】
    //如何设计一个并行算法解决这个问题
    //使用并查集

    public static int countIslands(int [][] m){
        if (m==null || m[0]==null){
            return 0;
        }
        int N=m.length;
        int M=m[0].length;
        int res=0;
        for (int i = 0; i <N ; i++) {
            for (int j = 0; j <M ; j++) {
                if (m[i][j]==1){
                    res++;
                    infect(m,i,j,N,M);
                }
            }
        }
        return res;
    }

    public static void infect(int [][]m,int i,int j,int N,int M){
        if (i>=N||j>=M||i<0||j<0||m[i][j]!=1){
            return;
        }
        m[i][j]=2;
        infect(m,i+1,j,N,M);
        infect(m,i-1,j,N,M);
        infect(m,i,j+1,N,M);
        infect(m,i,j-1,N,M);
    }


    //图
    public static class Node1{
        //编号
        public int value;
        //入度
        public int in;
        //出度
        public int out;
        //出度的点
        public ArrayList<Node1>nexts;
        //出度的边集合
        public ArrayList<Edge> edges;

        public Node1(int value) {
            this.value = value;
        }
    }
    public static class Edge<T>{
        public T weight;
        public Node1 from;
        public Node1 to;

        public Edge(T weight, Node1 from, Node1 to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }
    public static class Graph{
        //点集
        public HashMap<Integer, Node1> nodes;
        //边集
        public HashSet<Edge> edges;
        public Graph() {
            this.nodes = new HashMap<>();
            this.edges = new HashSet<>();
        }
    }

    //[weight,from,to]
    public static Graph createGraph(int [][]matrix){
        Graph graph=new Graph();

        for (int i = 0; i <matrix.length ; i++) {
            Integer weight= matrix[i][0];
            Integer fromNode=matrix[i][1];
            Integer toNode=matrix[i][2];
            if (!graph.nodes.containsKey(fromNode)){
                graph.nodes.put(fromNode,new Node1(fromNode));
            }
            if (!graph.nodes.containsKey(toNode)){
                graph.nodes.put(toNode,new Node1(toNode));
            }
            Node1 from=graph.nodes.get(fromNode);
            Node1 to=graph.nodes.get(toNode);

            Edge<Integer> edge=new Edge<>(weight,from,to);
            from.nexts.add(to);
            from.out++;
            from.edges.add(edge);
            to.in++;
            graph.edges.add(edge);
        }

return graph;

    }

    //宽度遍历
    public static void bfs(Node1 node){
        if (node==null){
            return;
        }
        Queue<Node1> queue=new LinkedList<>();
        //防止图有环  无穷无尽
        HashSet <Node1>set=new HashSet();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()){
            Node1 node1=queue.poll();
            System.out.println(node1.value);
            for (Node1 v:node1.nexts) {
                if (!set.contains(v)){
                    set.add(node1);
                    queue.add(node1);
                }
            }

        }
    }

    //深度遍历
    public static void dfs(Node1 node){
        if (node == null){
            return;
        }
        Stack<Node1> stack=new Stack<>();
        HashSet <Node1>set=new HashSet();
        stack.push(node);
        set.add(node);
        System.out.println(node.value);
        while (!stack.isEmpty()){
            Node1 cur=stack.pop();
            for (Node1 next:cur.nexts) {
                if (!set.contains(next)){
                    set.add(next);
                    stack.push(cur);
                    stack.push(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }





    }

    //拓扑排序
    public static List<Node1> sortedTopology(Graph graph){
        //入度表：Ineger ：剩余入度
        HashMap<Node1,Integer> inMap =new HashMap<>();
        //剩余入度为0的点进入队列
        Queue<Node1> zeroInQueue=new LinkedList<>();

        for (Node1 node:graph.nodes.values()) {
            //入度表记录节点入度数
            inMap.put(node,node.in);
            if (node.in==0) {
                zeroInQueue.add(node);
            }
        }
        List<Node1>result=new ArrayList<>();

        while (!zeroInQueue.isEmpty()){
            Node1 cur=zeroInQueue.poll();
            result.add(cur);
            //删除next点的入度
            //消除对下面节点的影响
            for (Node1 next:cur.nexts) {
                inMap.put(next,inMap.get(next)-1);
                //如果入度减为0 则进入zeroInQueue
                if (inMap.get(next)==0){
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }


    //最小生成树 Kruskal
    public static class EdgeComparator implements Comparator<Edge<Integer>>{
        @Override
        public int compare(Edge<Integer> o1, Edge<Integer> o2) {
            return o1.weight-o2.weight;
        }
    }
    public static Set<Edge> kruskalMST(Graph graph){
        //边进并查集
        UnionSet<Node1> unionSet=new UnionSet<Node1>(graph.nodes.values());
        PriorityQueue<Edge<Integer>> priorityQueue=new PriorityQueue<>(new EdgeComparator());
        //小根堆排序
        for (Edge edge:graph.edges){
            priorityQueue.add(edge);
        }
        Set<Edge> result=new HashSet<>();
        while (!priorityQueue.isEmpty()){
            //从最小那条边开始
            Edge edge=priorityQueue.poll();
            //是否是没有连通 没有 即连通
            if (!unionSet.isSameSet(edge.from,edge.to)){
                result.add(edge);
                unionSet.union(edge.from,edge.to);
            }
        }
        return result;
    }
    //Prim算法
    public static Set<Edge> primMST(Graph graph){
        //小根堆
        PriorityQueue<Edge<Integer>> priorityQueue=new PriorityQueue<>(new EdgeComparator());
        //记录点是否被解锁
        HashSet<Node1> set=new HashSet();
        Set<Edge> result=new HashSet<>();
        //随意挑选点 防森林
        for (Node1 node:graph.nodes.values()) {
            if (!set.contains(node)){
                set.add(node);
                for (Edge edge:node.edges) {
                    priorityQueue.add(edge);
                }
                //当该循环结束时  算法已经结束了
                while (!priorityQueue.isEmpty()){
                    Edge curEdge=priorityQueue.poll();
                    Node1 toNode=curEdge.to;
                    //但该点时新点时
                    if (!set.contains(toNode)){
                        //拿该边
                        result.add(curEdge);
                        //放入点集中
                        set.add(curEdge.to);
                        //下一个节点的边全部进小根堆
                        for (Edge next:toNode.edges){
                            priorityQueue.add(next);
                        }
                    }
                }
            }
            break;

        }

return result;


    }

    //迪杰拉斯算法
    public static HashMap<Node1,Integer> dijksta(Node1 from){
        //value从from节点出发到key节点最小值
        HashMap<Node1,Integer> distanceMap = new HashMap<>();
        distanceMap.put(from,0);
        //已经选择过的节点
        HashSet<Node1> selectedNodes=new HashSet<>();
        //from 0
        Node1 minNode=getMinDistanceAndUnselectedNode(distanceMap,selectedNodes);
        while(minNode!=null){
            //得到该点的最小值
            int distance=distanceMap.get(minNode);
            for (Edge<Integer> edge:minNode.edges) {
                //获取该边的下一个节点
                Node1 nextNode=edge.to;
                //判断该点是否存在map中 不存在的话就put上 上一个点的最小值加这个点的权值就是next节点的最小值
                //如果存在 就判断是之前的值小 还是 上一个点的最小值加这个点的权值小
                if (!distanceMap.containsKey(nextNode)){
                    distanceMap.put(nextNode,distance+edge.weight);
                }else {
                    distanceMap.put(edge.to,Math.min(distanceMap.get(nextNode),distance+edge.weight));
                }
            }
            //放入点集中锁起来
            selectedNodes.add(minNode);
            //下一个map中没有锁起来的最小距离的节点
            minNode=getMinDistanceAndUnselectedNode(distanceMap,selectedNodes);

        }
return distanceMap;
    }
   // map中没有锁起来的最小距离的节点
    public static Node1 getMinDistanceAndUnselectedNode(
            HashMap<Node1,Integer> distanceMap,
            HashSet<Node1> selectedNodes){
        Node1 minNode=null;
        int minDistance=Integer.MAX_VALUE;
        for (Map.Entry<Node1,Integer> entry:distanceMap.entrySet()) {
            Node1 cur=entry.getKey();
            int distance=entry.getValue();
            //没有进点集且在map中的最小值
            if (!selectedNodes.contains(cur)&&distance<minDistance){
                minNode=cur;
                minDistance=distance;
            }
        }
return minNode;
    }


    public static class NodeRecord{
        int distance;
        Node1 node1;

        public NodeRecord(int distance, Node1 node1) {
            this.distance = distance;
            this.node1 = node1;
        }
    }
    //特制dijksta小根堆
    public static class NodeHead{
        List <Node1> nodeList=new ArrayList<>();
        HashMap<Node1,Integer> map=new HashMap<>();
        //最小距离
        HashMap<Node1,Integer> distanceMap=new HashMap<>();
        int heapSize=0;

        public void headify(int index){
            int left=index*2+1;
            while (left<heapSize){
                int less=left+1<heapSize&&distanceMap.get(nodeList.get(left))-
                        distanceMap.get(nodeList.get(left+1))<0?left:left+1;
                less=distanceMap.get(nodeList.get(index))-distanceMap.get(nodeList.get(less))<0?index:less;

                if (less==index){
                    break;
                }
                swap(index,less);
                index=less;
                left=less*2+1;
            }
        }

        public void swap(int p1,int p2){
            Node1 o1=nodeList.get(p1);
            Node1 o2=nodeList.get(p2);

            map.put(o1,p2);
            map.put(o2,p1);

            nodeList.set(p1,o2);
            nodeList.set(p2,o1);
        }

        public void headInsert(int index){
            while (distanceMap.get(nodeList.get(index))-distanceMap.get(nodeList.get((index-1)>>1))<0){
                swap(index,(index-1)/2);
                index=(index-1)/2;
            }
        }

        public void regin(Node1 node){
            int valueIndex=map.get(node);
            headify(valueIndex);
            headInsert(valueIndex);
        }

        public void addUpdateIgnore(Node1 node,int distance){
            if (inHead(node)){
                distanceMap.put(node,Math.min(distance,distanceMap.get(node)));
                regin(node);
            }
            if (!map.containsKey(node)){
                nodeList.add(node);
                map.put(node,heapSize);
                distanceMap.put(node,distance);
                heapSize++;
                regin(node);
            }
        }

        public NodeRecord pop(){
            NodeRecord nodeRecord=new NodeRecord(distanceMap.get(nodeList.get(0)),nodeList.get(0));
            swap(0,heapSize-1);
            map.put(nodeList.get(heapSize-1),-1);
            distanceMap.remove(nodeList.get(heapSize-1));
            nodeList.remove(heapSize-1);
            heapSize--;
            headify(0);
            return nodeRecord;
        }

        public boolean inHead(Node1 node){
            return map.containsKey(node)&&map.get(node)!=-1;
        }

        public boolean isEmpty(){
            return heapSize==0?true:false;
        }
    }

    //使用自制小根堆
    public static HashMap<Node1,Integer> dijkstra2(Node1 head){
        NodeHead nodeHead=new NodeHead();
        nodeHead.addUpdateIgnore(head,0);
        HashMap<Node1,Integer> result=new HashMap<>();

        while (!nodeHead.isEmpty()){
            NodeRecord nodeRecord=nodeHead.pop();
            Node1 cur =nodeRecord.node1;
            int distance=nodeRecord.distance;

            for (Edge <Integer>edge:cur.edges){
                nodeHead.addUpdateIgnore(edge.to,edge.weight+distance);
            }
            result.put(cur,distance);
        }
        return  result;
    }


































}
