package wordnet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author gordl
 */
public class WordNet {
       private Digraph G;
       private HashMap<Integer, String> Synsets;
       public WordNet(String synsets, String hypernyms) {
           Synsets = new HashMap<>();
           G = new Digraph(82192);
           In in = new In(synsets);
           while(in.hasNextLine()) {
               String synline = in.readLine();
               String[] synitems = synline.split(",");
               int id = Integer.parseInt(synitems[0]);
               String synset = synitems[1];
               Synsets.put(id, synset);
           }
           in = new In(hypernyms);
           while(in.hasNextLine()) {
               String hypline = in.readLine();
               List<String> hyplist = Arrays.asList(hypline.split(","));
               int v = Integer.parseInt(hyplist.get(0));
               hyplist.stream().skip(1).forEach(s -> G.addEdge(v, Integer.parseInt(s)));
           }
           System.out.println("Done reading synsets and hypernyms");
       }
       public static void main(String[] args) {
        // TODO code application logic here
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
    }
    
}
