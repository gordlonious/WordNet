package wordnet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
//import java.util.stream.Collectors;
/**
 *
 * @author gordl
 */
public class WordNet {
       private Digraph G;
       private final SAP sap;
       private final HashMap<Integer, Collection<String>> Synsets;
       private final HashMap<String, Integer> Nouns;
       public WordNet(String synsets, String hypernyms) {
           if(synsets == null || hypernyms == null) throw new NullPointerException();
           Synsets = new HashMap<>();
           Nouns = new HashMap<>();
           G = new Digraph(82192);  // do we need to support various numbers of vertices?
           In in = new In(synsets);
           while(in.hasNextLine()) {
               String synline = in.readLine();
               String[] synitems = synline.split(",");
               int id = Integer.parseInt(synitems[0]);
               String[] synset = synitems[1].split(" ");
               Synsets.put(id, Arrays.asList(synset)); // make ST with synset id as key and collection of nouns as value
               for(String syn : synset) {
                   Nouns.put(syn, id); // make ST with noun as key and synset id as value
               }
           }
           in = new In(hypernyms);
           while(in.hasNextLine()) {
               String hypline = in.readLine();
               List<String> hyplist = Arrays.asList(hypline.split(","));
               int v = Integer.parseInt(hyplist.get(0));
               hyplist.stream().skip(1).forEach(s -> G.addEdge(v, Integer.parseInt(s)));
           }
           Collection<String> syntest = Synsets.get(38003);
           sap = new SAP(G);
           if(!sap.isRootedDAG()) throw new IllegalArgumentException("WordNet was unable to create a Rooted Directed Acyclic Graph from argument");
           System.out.println("Done reading synsets and hypernyms");
       }
       
       public Iterable<String> nouns() {
           // this works by using the Synsets symbol table
           //return Synsets.values().stream().flatMap(syn -> syn.stream()).collect(Collectors.toList());
           
           return Nouns.keySet();
       }
       
       public boolean isNoun(String n)
       {
           // this works by using the Synsets symbol table
           //return Synsets.values().stream().anyMatch(synset -> synset.stream().anyMatch(s -> s.equals(n)));
           
           if(n == null) throw new NullPointerException("calls isNoun with null argument");
           return Nouns.get(n) != null;
       }
       
       public int distance(String nounA, String nounB) {
           if(nounA == null || nounB == null) throw new NullPointerException("calls distance with null argument");
           if(Nouns.get(nounA) == null || Nouns.get(nounB) == null) throw new IllegalArgumentException("calls distance with non-WordNet noun(s) as argument");
           return sap.length(Nouns.get(nounA), Nouns.get(nounB));
       }
       
       public String sap(String nounA, String nounB) {
           if(nounA == null || nounB == null) throw new NullPointerException("calls sap with null argument");
           if(Nouns.get(nounA) == null || Nouns.get(nounB) == null) throw new IllegalArgumentException("calls distance with non-WordNet noun(s) as argument");
           int ancestor = sap.ancestor(Nouns.get(nounA), Nouns.get(nounB));
           StringJoiner sj = new StringJoiner("', ", "{'", "'}");
           for(String s : Synsets.get(ancestor)) {
               sj.add(s);
           }
           return sj.toString();
       }

           
    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        //164,21012,56099 
        // // means that the the synset 164 ("Actifed") has two hypernyms: 21012 ("antihistamine") and 56099 ("nasal_decongestant")
        StringJoiner hyps = new StringJoiner(", ");
        Iterable<Integer> hyps1 = wn.G.adj(164);
        for(Integer i : hyps1) { 
            for (String s : wn.Synsets.get(i)) {
               hyps.add(s);
            }
      }
        System.out.printf("The hypernyms of the word 'Actifed' are: %s%n", hyps);
        
        // test isNoun with 'Actifed' example
        System.out.printf("Actifed isNoun should be true, acutal is %b%n", wn.isNoun("Actifed"));
        // test that an obviously wrong noun is !isNoun
        System.out.printf("xxybviaZ123 isNoun should be false, actual is %b%n", wn.isNoun("xxybviaZ123"));
        
        // test nouns() by printing the first ten in the collection
        Iterable<String> nouns1 = wn.nouns();
        int n1Count = 0;
        for(String s : nouns1) {
            if(n1Count <= 9) { 
                System.out.printf("%s, ", s);
                n1Count++;
            }
            else  {
                System.out.println();
                break;
            }
        }
        System.out.printf("The sap distance between 'Actifed' and 'antihistamine' is %d%n", wn.distance("Actifed", "antihistamine"));
        System.out.printf("The sap distance between 'kick' and 'action' is %d%n", wn.distance("kick", "action"));
        System.out.printf("This shortest common ancestor of 'happiness' and 'stoicism' is %s%n", wn.sap("disease", "sickness"));
        System.out.printf("This shortest common ancestor of 'disease' and 'sickness' is %s%n", wn.sap("disease", "sickness"));
    }
    
}
