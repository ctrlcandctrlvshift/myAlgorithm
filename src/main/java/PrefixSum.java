import java.util.HashMap;

/**
 * @author zhang
 * @ClassName: PrefixSum
 * @Package PACKAGE_NAME
 * @Description: 前缀和
 * @date 2022/3/1213:11
 */
public class PrefixSum {
    //给定一个由整数nums和整数k组成的数组，返回其总和等于k的子数组的总数。
    public int subarraySum(int[] nums, int k) {
        int N = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0;
        int res = 0;
        for (int i = 0; i < N; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                res += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return res;
    }

    //给定一个整数数组nums和一个整数数组k，返回具有可被k整除的和的非空子数组的数量。
//子数组是数组的连续部分。
    public static int subarraysDivByK(int[] nums, int k) {
        int N = nums.length;
        int[] prefix = new int[N];
        prefix[0] = nums[0];
        for (int i = 1; i < N; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }
        int res = 0;
        for (int start = 0; start < N; start++) {
            for (int end = start; end < N; end++) {
                int cur = prefix[end] - (start == 0 ? 0 : prefix[start - 1]);
                if (cur % k == 0) {
                    res += 1;
                }

            }
        }
        return res;
    }
    public static int subarraysDivByK2(int[] nums, int k) {
        int sum=0;
        int res=0;

        HashMap<Integer, Integer> map=new HashMap<>();
        map.put(0,1);
        for (int i = 0; i <nums.length ; i++) {
            sum+=nums[i];
            int rem=sum%k;
            if (rem<0){
                rem+=k;
            }
            if (map.containsKey(rem)){
                res+=map.get(rem);
            }
            map.put(rem,map.getOrDefault(rem,0)+1);

        }
return res;
    }

//给定一个整数数组nums和一个整数数组k，如果nums的连续子数组中至少有两个元素的总和是k的倍
// 数则返回true，否则返回false。如果存在整数n使x = n * k，则x是k的倍数。0总是k的倍数。

    public static boolean checkSubarraySum(int[] nums, int k) {
        HashMap <Integer, Integer> map=new HashMap<>();
        int sum=0;
        map.put(0,-1);
        for (int i = 0; i <nums.length ; i++) {
            sum+=nums[i];
            int rem=sum%k;
            if (rem<0){
                rem+=k;
            }
            if (map.containsKey(rem)){
                if (i-map.get(rem)>1){
                    return true;
                }
            }else{
                map.put(rem,i);
            }
        }

return false;
    }
//给定一个二进制数组nums，返回0和1个数相等的相邻子数组的最大长度。相当于k==0
public static int findMaxLength(int[] nums) {
        HashMap<Integer, Integer> map=new HashMap<>();
        int res=Integer.MIN_VALUE;
        int sum=0;
        map.put(0,-1);
    for (int i = 0; i <nums.length ; i++) {
        nums[i]=nums[i]==0?-1:1;
    }
    for (int i = 0; i <nums.length ; i++) {
        sum+=nums[i];
        int ans=0;
        if (map.containsKey(sum-0)){
            ans=i-map.get(sum);
            res=Math.max(res,ans);
        }else {
            map.put(sum,i);
        }

    }
    return res==Integer.MIN_VALUE?0:res;
}
    public static void main(String[] args) {
        int []nums={0,0};
        int k=2;
        System.out.println(findMaxLength(nums));
    }


    //给定一个二维矩阵矩阵，处理以下类型的多个查询:
    //计算矩形内由其左上角(row1, col1)和右下角(row2, col2)定义的矩阵元素的和。
    //实现NumMatrix类:
    //NumMatrix(int[][] matrix)用整数矩阵矩阵初始化对象。
    //返回矩形内由其左上角(row1, col1)和右下角(row2, col2)定义的矩阵元素的和。
    static class NumMatrix {

        public NumMatrix(int[][] matrix) {

        }

        public int sumRegion(int row1, int col1, int row2, int col2) {

            return 0;
        }
    }
}