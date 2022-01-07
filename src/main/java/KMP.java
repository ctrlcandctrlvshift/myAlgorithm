/**
 * @author zhang
 * @ClassName: KMp
 * @Package PACKAGE_NAME
 * @Description: kmp
 * @date 2022/1/617:30
 */
public class KMP {

    //某一string1 包含某一string2 返回string2在string1的开始位置

    //k  string2取前后缀最大相等长度
    //next arr



    public static int kmp(String s1,String s2){
        if (s1==null||s2==null||s1.isEmpty()||s2.isEmpty()){
            return -1;
        }
        char []str1=s1.toCharArray();
        char []str2=s2.toCharArray();
        int i1=0;
        int i2=0;
        int []arr=getArry(str2);

        while (i1<str1.length&&i2<str2.length){
            if (str1[i1]==str2[i2]){
                i1++;
                i2++;
                //当无法往前跳
            }else if (arr[i2]==-1){
                i1++;
            }else {
                i2=arr[i2];
            }
        }
        return i2==str2.length?i1-i2:-1;

    }
    public static int[] getArry(char[] str){
        if (str.length==1){
            return new int[]{-1};
        }
        int next[]=new int[str.length];
        next[0]=-1;
        next[1]=0;
        int i=2;
        //i-1的数前后缀最大值（也是下标）
        int cn=0;
        while (i<str.length){
            if (str[i-1]==str[cn]){
                next[i++]=++cn;
            }else if (cn>0){
                //往前跳
                cn=next[cn];
            }else {
                //没办法往前跳了
                next[i++]=0;
            }
        }

        return next;

    }


    public static void main(String[] args) {
        String a="abc";
        String b="anc";
        System.out.println(kmp(a,b));
    }


}
