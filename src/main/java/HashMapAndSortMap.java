import java.util.BitSet;
import java.util.HashMap;

/**
 * @author zhang
 * @ClassName: HashMapAndSortMap
 * @Package PACKAGE_NAME
 * @Description: 哈希表
 * @date 2021/12/216:46
 */
public class HashMapAndSortMap {
//哈希表增删改查为复杂度为o（1）
//treeMap 有序表复杂度为o（logN）

//把40亿数只给1g内存 统计其重复数最大的
//哈希表  扩容代价为o(N*logN))
// java离线扩容技术  jvm离线申请扩容  不占用在线内存

    //设置RandomPool结构
    //设计RandomPool结构
    //【题目】
    //设计一种结构，在该结构中有如下三个功能：
    //insert (key)：将某个key加入到该结构，做到不重复加入
    //delete (key)：将原本在结构中的某个key移除
    //getRandom （）：等概率随机返回结构中的任何一个key。
    //【要求】
    //Insert、delete和getRandom方法的时间复杂度都是0(1)
    public static class RandomPool<K>{
        //删除时维护
        private HashMap<K,Integer> map1;
        //getRandom时维护
        private HashMap<Integer, K> map2;
        private int size;

        public RandomPool() {
            this.map1 = new HashMap<>();
            this.map2 = new HashMap<>();
            this.size = 0;
        }

        public void insert(K k){
            if (!map1.containsKey(k)){
                map1.put(k,size);
                map2.put(size++,k);
            }
        }
        public void delete(K k){
            if (map1.containsKey(k)){
                int k1=map1.get(k);
                int last=--size;
                K lastK=map2.get(last);
                map1.put(lastK,k1);
                map2.put(k1,lastK);
                map1.remove(k);
                map2.remove(last);
            }

        }
        public K getRandom(){
            if (size!=0){
                int rand=(int)Math.random()*size;
                return map2.get(rand);
            }else {
                return null;
            }

        }



    }


    //布隆过滤（没有删除行为  黑名单  有失误率）与单样本大小无关
    //会出现白的变黑（失误） 不会出现黑变白
    //p失误率
    //n样本量
    //k个hash函数
    //m bit长度
    //m的长度越长  失误率越低
    //k个hash函数 存在一个合理的数量

    //设计布隆过滤
    //m=-（nlnp）/（ln2）^2
    //k=m/n*ln2
    //p真实失误率=（1-e^kn/m）^k
    public static void fiter(){
        //4*8*10=320bit
        int[] arr=new int[10];
        //arr[0] int 0-31

        //找178位状态
        int i=178;

        int numIndex=178/32;
        int bitIndex=178%32;

        //拿到178位状态
        int s=((arr[numIndex]>>(bitIndex))&1);

        //把178状态改1  把1左移到bit位置  或运算：有1则1
        arr[numIndex]=arr[numIndex] | (1<<(bitIndex));

        //把178状态改0  把1左移到bit位置取反该位置变0  与运算：都1则1
        arr[numIndex]=arr[numIndex]&(~(1<<(bitIndex)));

    }


    //hash一致性
    //寻找服务器
    //问题1 如何做到每台机器负载均分
    //问题2 删加服务器后 如何做到负载均分
    //虚拟节点技术



    //有序表




}
