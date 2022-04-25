import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author zhang
 * @ClassName: temp
 * @Package PACKAGE_NAME
 * @Description:
 * @date 2022/4/1523:54
 */
public class temp {
    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int n=equations.size();
        GraphT1 g=new GraphT1();
        for (int i = 0; i <n ; i++) {
            if (g.nodes.containsKey(equations.get(i).get(0))){
                Node1 from=g.nodes.get(equations.get(i).get(0));
                if (g.nodes.containsKey(equations.get(i).get(1))){

                    Node1 next=g.nodes.get(equations.get(i).get(1));
                    Edge edge1=new Edge(values[i],from,next);
                    Edge edge2=new Edge(1/values[i],next,from);
                    from.nexts.add(next);
                    from.map.put(next, edge1);
                    next.nexts.add(from);
                    from.map.put(next, edge1);

                    g.edges.add(edge1);
                    g.edges.add(edge2);

                }else {
                    Node1 next=new Node1(equations.get(i).get(1));
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
                Node1 from=new Node1(equations.get(i).get(0));
                if (g.nodes.containsKey(equations.get(i).get(1))){
                    Node1 next=g.nodes.get(equations.get(i).get(1));

                    Edge edge1=new Edge(values[i],from,next);
                    Edge edge2=new Edge(1/values[i],next,from);
                    from.nexts.add(next);
                    from.map.put(next, edge1);
                    next.map.put(from, edge2);
                    next.nexts.add(from);

                    g.edges.add(edge1);
                    g.edges.add(edge2);
                }else {
                    Node1 next=new Node1(equations.get(i).get(1));
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
                Node1 from=g.nodes.get(queries.get(i).get(0));
                double ans=equation(from,queries.get(i).get(1),new HashSet<String>(),1);
                res[i]=ans;
                continue;
            }
            res[i]=-1;
        }

        return res;
    }
    public static double equation(Node1 node,String target,HashSet <String>set,double res){
        if (node.name==target){
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
    static class Node1{
        public String name;
        public ArrayList<Node1> nexts;
        public HashMap<Node1,Edge> map;



        public Node1(String name) {
            this.name = name;
            this.nexts = new ArrayList<>();
            this.map=new HashMap<>();
        }
    }
    static class Edge{
        public double value;
        public Node1 from;
        public Node1 to;

        public Edge(double value, Node1 from, Node1 to) {
            this.value = value;
            this.from = from;
            this.to = to;
        }
    }
    static class GraphT1{
        public HashMap <String ,Node1>nodes;
        public HashSet <Edge>edges;

        public GraphT1() {
            this.nodes = new HashMap<>();
            this.edges = new HashSet<>();
        }
    }
    public static void main(String[] args) {
        List<List<String>> equations=new ArrayList<>();
        List<String> list1=new ArrayList<>();
        list1.add("a");
        list1.add("b");

        List<String> list2=new ArrayList<>();
        list2.add("e");
        list2.add("f");

        List<String> list4=new ArrayList<>();
        list4.add("b");
        list4.add("e");
        equations.add(list1);
        equations.add(list2);
        equations.add(list4);
        double[]values={3.4,1.4,2.3};
        List<List<String>> queries=new ArrayList<>();

        List<String> list3=new ArrayList<>();
        list3.add("a");
        list3.add("f");


        queries.add(list3);
        double[] res=calcEquation(equations,values,queries);
        for(double d:res){
            System.out.print(d+" ");

        }
    }



}
