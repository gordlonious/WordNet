package wordnet;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author gordl
 */
public class Outcast {
    WordNet wn;
    public Outcast(WordNet wn) {
        this.wn = wn;
    }
    public String outcast(String[] nouns) {
        // could call isNoun on each noun, but instructions say to assume nouns are WordNet nouns
        Collection<Integer> distances = new ArrayList<>();
        ST<Integer, String> distToNoun = new ST<>();
        for(int c = 0; c < nouns.length; c++) {
            for(int to = c + 1; to < nouns.length; to++) {
                int dist = wn.distance(nouns[c], nouns[to]);
                String ancestor = wn.sap(nouns[c], nouns[to]);
                distances.add(dist);
                distToNoun.put(dist, nouns[to]);
            }
        }
        return distToNoun.get(Collections.max(distances));
    }

    public static void main(String[] args) {
        String[] args2 = { "synsets.txt", "hypernyms.txt", "outcast5.txt", "outcast8.txt", "outcast11.txt" };  // easier to hard-code than pass in everytime
        WordNet wn = new WordNet(args2[0], args2[1]);
        Outcast oc = new Outcast(wn);
        for(int t = 2; t < args2.length; t++) {
            In in = new In(args2[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args2[t] + ": " + oc.outcast(nouns));
        }
        System.out.println("PLEASE SEE OUTPUT OF WORDNET CLASS TO SEE ADDITIONAL INFO ABOUT POSSIBLE OUTCAST BUG! COULD BE THAT OUTCAST IS ACTUALLY CORRECT");
    }
}
