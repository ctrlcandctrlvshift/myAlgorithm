import java.util.*;
import java.util.concurrent.ExecutorService;

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
