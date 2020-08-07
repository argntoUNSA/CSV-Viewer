import java.util.*;
public class SwapTest {
    
    static void swap(List<Integer> l1,List<Integer> l2)
    {
        for(int i=0;i<l2.size();i++)
        {
            int ind = l1.indexOf(l2.get(i));
            Collections.swap(l1, i, ind);
        }
    }
    public static void main(String[] args) {
        
        List<Integer> l1 = Arrays.asList(0,1,2,3);
        List<Integer> l2 = Arrays.asList(3,2,1,0);
        

        System.out.println(l1);
        System.out.println(l2);

        System.out.println();
        swap(l1, l2);
        System.out.println(l1);
        System.out.println(l2);
    }
}