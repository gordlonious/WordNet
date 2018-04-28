package wordnet;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
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
        DirectedDFS dfs = new DirectedDFS(this.G.reverse(), 38003);
        for(int v = 0; v < this.G.V(); v++) {
            if(!dfs.marked(v)) {
                rootReachesAllOtherNodesWithReverseEdges = false;
                break;
            }
        }
        return isDAG() && rootReachesAllOtherNodesWithReverseEdges;
    }
    public int length(int v, int w) {
        if(v < 0 || v > (G.V() - 1) || w < 0 || w > (G.V() - 1)) throw new IndexOutOfBoundsException("Invalid vertices passed to length");
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G, w);
        Collection<Integer> vc = new ArrayList<>();
        Collection<Integer> wc = new ArrayList<>();
        IntStream.range(0, G.V()).filter(i -> vbfs.hasPathTo(i)).forEach(i -> vc.add(i));
        IntStream.range(0, G.V()).filter(i -> wbfs.hasPathTo(i)).forEach(i -> wc.add(i));
        Collection<Integer> candidateVertices = vc.stream().filter(vrt -> wc.contains(vrt)).collect(Collectors.toList());
        int shortestV;
        if(candidateVertices.size() > 1) {
            shortestV = candidateVertices.iterator().next();
            for(int i : candidateVertices) {
                if(vbfs.distTo(i) + wbfs.distTo(i) < vbfs.distTo(shortestV) + wbfs.distTo(shortestV)) {
                    shortestV = i;
                }
            }
            return vbfs.distTo(shortestV) + wbfs.distTo(shortestV);
        } else if(candidateVertices.size() == 1) {
            shortestV = candidateVertices.iterator().next();
            return vbfs.distTo(shortestV) + wbfs.distTo(shortestV);
        } else {
            return -1;
        }
    }
    
     public int length(Iterable<Integer> vs, Iterable<Integer> ws) {
        for(int v : vs) if(v < 0 || v > (G.V() - 1)) throw new IndexOutOfBoundsException("Invalid vertices passed to length");
        for(int w : ws) if(w < 0 || w > (G.V() - 1)) throw new IndexOutOfBoundsException("Invalid vertices passed to length");
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G, vs);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G, ws);
        Collection<Integer> vc = new ArrayList<>();
        Collection<Integer> wc = new ArrayList<>();
        IntStream.range(0, G.V()).filter(i -> vbfs.hasPathTo(i)).forEach(i -> vc.add(i));
        IntStream.range(0, G.V()).filter(i -> wbfs.hasPathTo(i)).forEach(i -> wc.add(i));
        Collection<Integer> candidateVertices = vc.stream().filter(vrt -> wc.contains(vrt)).collect(Collectors.toList());
        int shortestV;
        if(candidateVertices.size() > 1) {
            shortestV = candidateVertices.iterator().next();
            for(int i : candidateVertices) {
                if(vbfs.distTo(i) + wbfs.distTo(i) < vbfs.distTo(shortestV) + wbfs.distTo(shortestV)) {
                    shortestV = i;
                }
            }
            return vbfs.distTo(shortestV) + wbfs.distTo(shortestV);
        } else if(candidateVertices.size() == 1) {
            shortestV = candidateVertices.iterator().next();
            return vbfs.distTo(shortestV) + wbfs.distTo(shortestV);
        } else {
            return -1;
        }
    }
     
    public int ancestor(int v, int w) {
        if(v < 0 || v > (G.V() - 1) || w < 0 || w > (G.V() - 1)) throw new IndexOutOfBoundsException("Invalid vertices passed to ancestor");
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G, w);
        Collection<Integer> vc = new ArrayList<>();
        Collection<Integer> wc = new ArrayList<>();
        IntStream.range(0, G.V()).filter(i -> vbfs.hasPathTo(i)).forEach(i -> vc.add(i));
        IntStream.range(0, G.V()).filter(i -> wbfs.hasPathTo(i)).forEach(i -> wc.add(i));
        Collection<Integer> candidateVertices = vc.stream().filter(vrt -> wc.contains(vrt)).collect(Collectors.toList());
        int shortestV;
        if(candidateVertices.size() > 1) {
        shortestV = candidateVertices.iterator().next();
            for(int i : candidateVertices) {
                if(vbfs.distTo(i) + wbfs.distTo(i) < vbfs.distTo(shortestV) + wbfs.distTo(shortestV)) {
                    shortestV = i;
                }
            }
            return shortestV;
        } else if(candidateVertices.size() == 1) {
            return candidateVertices.iterator().next();
        } else {
            return -1;
        }
    }
    
     public int ancestor(Iterable<Integer> vs, Iterable<Integer> ws) {
        for(int v : vs) if(v < 0 || v > (G.V() - 1)) throw new IndexOutOfBoundsException("Invalid vertices passed to ancestor");
        for(int w : ws) if(w < 0 || w > (G.V() - 1)) throw new IndexOutOfBoundsException("Invalid vertices passed to ancestor");
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G, vs);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G, ws);
        Collection<Integer> vc = new ArrayList<>();
        Collection<Integer> wc = new ArrayList<>();
        IntStream.range(0, G.V()).filter(i -> vbfs.hasPathTo(i)).forEach(i -> vc.add(i));
        IntStream.range(0, G.V()).filter(i -> wbfs.hasPathTo(i)).forEach(i -> wc.add(i));
        Collection<Integer> candidateVertices = vc.stream().filter(vrt -> wc.contains(vrt)).collect(Collectors.toList());
        int shortestV;
        if(candidateVertices.size() > 1) {
        shortestV = candidateVertices.iterator().next();
            for(int i : candidateVertices) {
                if(vbfs.distTo(i) + wbfs.distTo(i) < vbfs.distTo(shortestV) + wbfs.distTo(shortestV)) {
                    shortestV = i;
                }
            }
            return shortestV;
        } else if(candidateVertices.size() == 1) {
            return candidateVertices.iterator().next();
        } else {
            return -1;
        }
    }
     
    public static void main(String[] args) {
        if(args.length > 0) { 
            In gin = new In(args[0]);
            Digraph G = new Digraph(gin);
            SAP sap = new SAP(G);
            int v = Integer.parseInt(args[1]);
            int w = Integer.parseInt(args[2]);
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        } else {
            In gin = new In("digraph1ForTesting.txt");
            boolean streamExists = gin.exists();
            Digraph G = new Digraph(gin);
            SAP sap = new SAP(G);
            In in = new In("digraph1ForTesting.txt");
            while(in.hasNextLine()) {
                String line = in.readLine();
                String[] a = line.split(" ");
                if (a.length == 1) {
                    System.out.println("ignoring line");
                } else {
                    int v = Integer.parseInt(a[0]);
                    int w = Integer.parseInt(a[1]);
                    int length = sap.length(v, w);
                    int ancestor = sap.ancestor(v, w);
                    StdOut.printf("for veritces %d and %d: length = %d, ancestor = %d\n", v, w, length, ancestor);
                }  
            }
        }   
    }
}
