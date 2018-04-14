package wordnet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
/**
 *
 * @author gordl
 */
public class WordNet {
       private Digraph G;
       private HashMap<Integer, Iterable<String>> Synsets;
       public WordNet(String synsets, String hypernyms) {
           Synsets = new HashMap<>();
           G = new Digraph(82192);
           In in = new In(synsets);
           while(in.hasNextLine()) {
               String synline = in.readLine();
               String[] synitems = synline.split(",");
               int id = Integer.parseInt(synitems[0]);
               String[] synset = synitems[1].split(" ");
               Synsets.put(id, Arrays.asList(synset));
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
       
       public Iterable<String> nouns() {
           List<String> l = new ArrayList<>();
           Collection<Iterable<String>> vals = Synsets.values();
           for(Iterable<String> syn : vals) {
               for(String s : syn) {
                   l.add(s);
               }
           }
           // return Synsets.values().stream().flatMap(syn -> syn.stream()).collect(Collectors.toList());
           return l;
       }
       public static void main(String[] args) {
        // TODO code application logic here
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        //164,21012,56099 
        // // means that the the synset 164 ("Actifed") has two hypernyms: 21012 ("antihistamine") and 56099 ("nasal_decongestant")
        // The hypernyms of Actifed are '', ''
        StringJoiner hyps = new StringJoiner(", ");
        Iterable<Integer> hyps1 = wn.G.adj(164);
        for(Integer i : hyps1) { 
            for (String s : wn.Synsets.get(i)) {
               hyps.add(s);
            }
      }
        System.out.printf("The hypernyms of the word 'Actifed' are: %s%n", hyps);
    }
    
}
