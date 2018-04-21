package wordnet;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.DirectedDFS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author gordl
 */
public class SAP {
    private final Digraph G;
    public SAP(Digraph G) {
        this.G = G;
    }
    
    public boolean isDAG() {
        DirectedCycle dc = new DirectedCycle(G);
        return !dc.hasCycle();
    }
    public boolean isRootedDAG() {
        boolean rootReachesAllOtherNodesWithReverseEdges = true;
        DirectedDFS dfs = new DirectedDFS(this.G.reverse(), 0/* is 0 really the root */);
        for(int v = 0; v < this.G.V(); v++) {
            if(!dfs.marked(v)) {
                rootReachesAllOtherNodesWithReverseEdges = false;
                break;
            }
        }
        return isDAG() && rootReachesAllOtherNodesWithReverseEdges;
    }
    public int length(int v, int w) {
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G, w);
        Collection<Integer> vc = new ArrayList<>();
        Collection<Integer> wc = new ArrayList<>();
        IntStream.range(0, G.V()).filter(i -> vbfs.hasPathTo(i)).forEach(i -> vc.add(i));
        IntStream.range(0, G.V()).filter(i -> wbfs.hasPathTo(i)).forEach(i -> wc.add(i));
        Collection<Integer> candidateVertices = vc.stream().filter(vrt -> wc.contains(vrt)).collect(Collectors.toList());
        int shortestV = candidateVertices.iterator().next();
        for(int i : candidateVertices) {
            if(vbfs.distTo(i) + wbfs.distTo(i) < vbfs.distTo(shortestV) + wbfs.distTo(shortestV)) {
                shortestV = i;
            }
        }
        return vbfs.distTo(shortestV) + wbfs.distTo(shortestV);
    }
}
