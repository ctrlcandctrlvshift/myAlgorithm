package tools;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collector;

/**
 * @author zhang
 * @ClassName: Comparatpr
 * @Package tools
 * @Description: 比较器
 * @date 2021/12/815:22
 */
public class Comparatpr {

    private static class Student{
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
    public static class IdComparator implements Comparator<Student>{

        @Override
        public int compare(Student o1, Student o2) {
//            if (o1.getId()>o2.getId()){
//                return 1;
//            }else if (o1.getId()<o2.getId()){
//                return -1;
//            }else {
//                return 0;
//            }
            return o1.getId()-o2.getId();

        }
    }

    public static void main(String[] args) {
        Student[] student={};

        Arrays.sort(student,new IdComparator());
    }


}
