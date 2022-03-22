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

    //给定一个字符串s，返回s中最长的回文子字符串。
    public static String longestPalindromeDp(String s) {
        if (s==null||s.length()==0){
            return null;
        }
        boolean dp[][]=new boolean[s.length()][s.length()];
        int max=1;
        int left=0;
        int right=0;
        for (int end = 0; end <s.length() ; end++) {
            for (int start = end; start >=0 ; start--) {
                boolean isOk=s.charAt(start)==s.charAt(end);
                if (start==end||(end-start==1&&isOk)||(dp[start+1][end-1]&&isOk)){
                    dp[start][end]=true;
                    if(end-start+1>max){
                        left=start;
                        right=end;
                        max=end-start+1;
                    }
                }
            }
        }
        return s.substring(left,right+1);
    }

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
        String s="cbbd";
        System.out.println(longestPalindromeDp(s));
    }






}
