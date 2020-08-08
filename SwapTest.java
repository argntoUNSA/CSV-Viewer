import java.util.*;
public class SwapTest {
    
    static void swap(List<Integer> l1,List<Integer> l2,List<String> tmp)
    {
        for(int i=0;i<l2.size();i++)
        {
            int ind = l1.indexOf(l2.get(i));
            Collections.swap(tmp, i, ind);
        }
    }
    public static void main(String[] args) {
        
        List<Integer> l1 = Arrays.asList(0,1,2,3);
        List<Integer> l2 = Arrays.asList(3,1,2);
        
        List<List<String>> demo = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("Hello1","H2","H3","h4")),new ArrayList<>(Arrays.asList("Hello1","H2","H3","h4")),new ArrayList<>(Arrays.asList("Hello1","H2","H3","h4")),new ArrayList<>(Arrays.asList("Hello1","H2","H3","h4")),new ArrayList<>(Arrays.asList("Hello1","H2","H3","h4"))));

        System.out.println(l1);
        System.out.println(l2);
        
        // List<String> tmp = demo.get(0);
        // List<String> res = new ArrayList<>();
        // for (int i = 0; i < tmp.size(); i++) {
        //     res.add(tmp.get(l2.get(i)));
        // }

        List<List<String>> tmp = new ArrayList<>();
        System.out.println(demo);
        
        for(int i=0;i<demo.size();i++)
        {
            tmp.add(new ArrayList<>());
            // tmp.get(i).
            for(int j=0;j<l2.size();j++)
            {
                String ele = demo.get(i).get(l2.get(j));
                tmp.get(i).add(ele);
            }
        }
        
        // for(List<String> t:tmp)
        // {
        //     for (List<Integer> l : l2) {
                
        //     }
        // } 

        // for(int j =0,i=0; j<demo.size();j++,i++)
        // {
        //     // Collections.swap(li,0,1);
        //     // for (int i = 0; i < res.size(); i++) 
        //     {
        //         int ind = tmp.indexOf(res.get(i));
        //         Collections.swap(demo.get(j), i, ind);
        //     }
            
        // }
        // for(int i=0;i<demo.size();i++)
        // {
            // demo.get(i).remove(0);
            // swap(l1, l2,demo.get(1));
        // }
        
        // System.out.println(demo);
        System.out.println(tmp);
        // System.out.println();
        // swap(l1, l2);
        // System.out.println(l1);
        // System.out.println(l2);
    }
}