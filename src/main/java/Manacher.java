/**
 * @author zhang
 * @ClassName: Manacher
 * @Package PACKAGE_NAME
 * @Description: Manacher最长回文子串
 * @date 2022/1/810:02
 */
public class Manacher {
    //经典做法：
    //以一个数为中心 两边扩
    // 前提要在char的两端加#  o（n^2）
    //返回最大回文串数值

    public static int manachar(String s){
        if (s==null||s.length()==0){
            return 0;
        }
        char[] str=manacharString(s);
        //半径 包括中心点
        int [] arrp=new int[str.length];
        //在有效范围内下一个
        int R=-1;
        int C=-1;
        int max=Integer.MIN_VALUE;
        for (int i = 0; i <str.length ; i++) {
         // 解决原本的arr  包括扩 与 不扩的数
            //2*C-1 是i'
            arrp[i]=R>i?Math.min(arrp[2*C-i],R-i):1;

            //统一扩
            while ((i+arrp[i])<str.length&&(i-arrp[i])>-1){
                if (str[i+arrp[i]]==str[i-arrp[i]]){
                    arrp[i]++;
                }else {
                    break;
                }

            }
            if (arrp[i]+i>R){
                R=arrp[i]+i;
                C=i;
            }
            max=Math.max(max,arrp[i]);
        }
        return max-1;
    }
    public static char [] manacharString(String s){
        char[] str=s.toCharArray();
        int n=(str.length*2)+1;
        char[] newStr=new char[n];
        newStr[0]='#';
        int down=1;
        for (int i = 1; i <n;) {
         newStr[i]=str[i-down];
         newStr[i+1]='#';
         down++;
         i=i+2;
        }
        return newStr;
    }






    public static void main(String[] args) {
        String s="112";
//        String b=String.valueOf(manacharString(a));
//        System.out.println(b);
        System.out.println(manachar(s));

    }






}
