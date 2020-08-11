import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import static com.github.chrisgleissner.jutil.table.TablePrinter.DefaultTablePrinter;
import java.util.regex.*;

public class CSVViewer {

    static void print(List<String> header, List<List<String>> data) {
        System.out.println(DefaultTablePrinter.print(header, data));
    }

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Usage of csv-viewer :");
            System.out.println("\t-f value");
            System.out.println("\t\tfilter");
            System.out.println("\t-l int");
            System.out.println("\t\tset max display rows num");
            System.out.println("\t-p string");
            System.out.println("\t\tset csv file path");
            System.out.println("\t-o \"col1,col2,..\"");
            System.out.println("\t\tset csv file path");
            System.out.println("\t-s string");
            System.out.println("\t\tsort by set value");
            System.out.println("\t\teg. col [ASC/DESC]?");

        } else {

            // System.out.println(Arrays.asList(args));
            Map<String, String> options = new HashMap<>();

            for (int i = 0; i + 1 < args.length; i++) {
                if (args[i].charAt(0) == '-') {
                    options.put(args[i].charAt(1) + "", args[i + 1]);
                }
            }

            // System.out.println(options);
            List<String> file = Files.readAllLines(Paths.get(options.get("p")));

            List<String> header = Arrays.asList(file.get(0).split(","));

            List<List<String>> data = file.stream().map(e -> Arrays.asList(e.split(","))).collect(Collectors.toList());

            // remove header from data
            data.remove(0);

            //to json
            if(options.containsKey("j"))
            {
                System.out.println("[");
                for(int i=0;i<data.size();i++)
                {
                    System.out.println("\t{");
                    for (int j = 0; j < data.get(i).size(); j++) {
                        System.out.print("\t\t");
                        System.out.print("\""+header.get(j)+"\":\""+data.get(i).get(j)+"\"");
                        System.out.println(j == data.get(i).size()-1?"":",");
                    }

                    System.out.println(i == data.size() -1 ?"\t}":"\t},");
                }
                System.out.println("]");
                System.exit(0);
            }
            if (options.containsKey("s")) {

                String sort_str = options.get("s");

                String[] split = sort_str.split(" ");
                boolean order = true;
                if (split.length == 2) {
                    if (split[1].equals("DESC"))
                        order = false;
                }
                int col = header.indexOf(split[0]);
                Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                if (pattern.matcher(data.get(0).get(col)).matches()) {
                    // numerical sorting
                    try {
                        // Comparator cmp = Comparator.comparing(e->e.get(col));
                        if (order)
                            data.sort(Comparator.comparing((List<String> e) -> Double.parseDouble(e.get(col))));
                        else
                            // data.sort(Comparator.comparing((List<String> e)-> e.get(col)).reversed());
                            data.sort(Comparator.comparing((List<String> e) -> Double.parseDouble(e.get(col)))
                                    .reversed());

                    } catch (Exception e) {
                        System.out.println("INVALID COLUMN NUMBER : Data cannot be sorted .");
                    }
                } else {
                    try {
                        // Comparator cmp = Comparator.comparing(e->e.get(col));
                        if (order)
                            data.sort(Comparator.comparing((List<String> e) -> e.get(col)));
                        else
                            data.sort(Comparator.comparing((List<String> e) -> e.get(col)).reversed());

                    } catch (Exception e) {
                        System.out.println("INVALID COLUMN NUMBER : Data cannot be sorted .");
                    }

                }
            }

            if (options.containsKey("l")) {
                int opt = Integer.parseInt(options.get("l"));
                data = data.subList(0, opt);
            }

            if (options.containsKey("f")) {
                String condition = options.get("f");
                String[] split = condition.split(" ");

                int index = header.indexOf(split[0]);

                if (split[1].equals("="))
                    data = data.stream().filter(e -> e.get(index) == split[2]).collect(Collectors.toList());
                else if (split[1].equals("<"))
                    data = data.stream().filter(e -> Double.parseDouble(e.get(index)) < Double.parseDouble(split[2]))
                            .collect(Collectors.toList());
                else if (split[1].equals(">"))
                    data = data.stream().filter(e -> Double.parseDouble(e.get(index)) > Double.parseDouble(split[2]))
                            .collect(Collectors.toList());

            }

            // only columns

            if (options.containsKey("o")) {
                String[] opt = options.get("o").split(",");

                List<Integer> ind = new ArrayList<>();
                for (String o : opt) {
                    ind.add(header.indexOf(o));
                }
                List<List<String>> tmp = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    tmp.add(new ArrayList<>());
                    // tmp.get(i).
                    for (int j = 0; j < ind.size(); j++) {
                        String ele = data.get(i).get(ind.get(j));
                        tmp.get(i).add(ele);
                    }
                }
                List<String> header2 = new ArrayList<>();
                for (int s : ind) {
                    header2.add(  header.get(s) );
                
                }

                header = header2;
                data = tmp;
            }

            // 0 1 2 3
            // 1 2 3 0

            // data.forEach(e-> System.out.println(Arrays.toString(e)));

            // data.removeIf(e->e.length == 0);
            // data.remove(data.size() -1); // remove last empty lines
            // System.out.println(data);

            print(header, data);
            // System.out.println();

            // sort according first col
            // data.sort(Comparator.comparing(e->e[0]));
            // debug(data);
            // System.out.println();

            // sort first column according to number
            // data.sort(Comparator.comparing(e->Float.parseFloat(e[1])));
            // debug(data);

            // System.out.println();
            // data.sort(Comparator.comparing(e->e[2]));
            // debug(data);

            // System.out.println();
            // data.sort(Comparator.comparing(e->e[3]));
            // debug(data);

        }

    }
}