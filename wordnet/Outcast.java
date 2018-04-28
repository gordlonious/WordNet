package wordnet;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                distToNoun.put(dist, nouns[c] + " " + nouns[to] + " ancestor: " + ancestor);
            }
        }
        return distToNoun.get(Collections.max(distances));
    }
    
//    public int length(int v, int w) {
//        if(v < 0 || v > (G.V() - 1) || w < 0 || w > (G.V() - 1)) throw new IndexOutOfBoundsException("Invalid vertices passed to length");
//        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G, v);
//        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G, w);
//        Collection<Integer> vc = new ArrayList<>();
//        Collection<Integer> wc = new ArrayList<>();
//        IntStream.range(0, G.V()).filter(i -> vbfs.hasPathTo(i)).forEach(i -> vc.add(i));
//        IntStream.range(0, G.V()).filter(i -> wbfs.hasPathTo(i)).forEach(i -> wc.add(i));
//        Collection<Integer> candidateVertices = vc.stream().filter(vrt -> wc.contains(vrt)).collect(Collectors.toList());
//        int shortestV;
//        if(candidateVertices.size() > 1) {
//            shortestV = candidateVertices.iterator().next();
//            for(int i : candidateVertices) {
//                if(vbfs.distTo(i) + wbfs.distTo(i) < vbfs.distTo(shortestV) + wbfs.distTo(shortestV)) {
//                    shortestV = i;
//                }
//            }
//            return vbfs.distTo(shortestV) + wbfs.distTo(shortestV);
//        } else if(candidateVertices.size() == 1) {
//            shortestV = candidateVertices.iterator().next();
//            return vbfs.distTo(shortestV) + wbfs.distTo(shortestV);
//        } else {
//            return -1;
//        }
//    }
    // run this: java wordnet/Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
    public static void main(String[] args) {
        String[] args2 = { "synsets.txt", "hypernyms.txt", "outcast5.txt", "outcast8.txt", "outcast11.txt" };
        WordNet wn = new WordNet(args2[0], args2[1]);
        Outcast oc = new Outcast(wn);
        for(int t = 2; t < args2.length; t++) {
            In in = new In(args2[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args2[t] + ": " + oc.outcast(nouns));
        }
    }
}
