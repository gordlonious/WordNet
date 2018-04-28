/******************************************************************************
 *  Name:     
 *  NetID:    
 *  Precept:  
 *
 *  Partner Name:     
 *  Partner NetID:    
 *  Partner Precept:  
 *
 *  Hours to complete assignment (optional):
 *
 ******************************************************************************/

Programming Assignment 6: WordNet


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 *****************************************************************************/
 We used a Java Hashmap to make a Symbol Table with the synset id as the key and a collection of associated nouns as the value.
 I opted for a Java Hasmap over an algs4 lib class in the hopes it would provide extra utility in the event of needing to solve
 an unforeseen problem. In the end, I think it would have been just as simple to use an algs4 Symbol Table (ST).



/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 *****************************************************************************/
 Once read in, we immediately stored the information from hypernyms.txt as edges in our Digraph explicitly using
 addEdge(). This avoided using additional memory by first storing the values in a collection or ST.



/******************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithms as a function of the number of vertices V and the
 *  number of edges E in the digraph?
 *****************************************************************************/

Description: First we create a Digraph with 82192 vertices, then loop through each line in synsets.txt and store the info in an ST,
	then we loop through each line in hypernyms.txt and add an edge to our digraph for each hypernym relation, 
	and finally we check if the digraph we created is a rooted acyclic digraph (if not, we throw an exception).



Order of growth of running time:
	Creating a Diagraph to store vertices/edge info in an friendly api = ~V
	Reading in info from sysnsets.txt = ~V
	Reading in info from hypernyms.txt = ~5E
	Creating reverse Digraph = ~V
	Adding appropriate edges to reverse Digraph = ~5E
	Overall approximation: 3V + 10E
	
	Overall Big O Running Time: V + E
	


/******************************************************************************
 *  Describe concisely your algorithm to compute the shortest common
 *  ancestor in ShortestCommonAncestor. For each method, what is the order of
 *  growth of the worst-case running time as a function of the number of
 *  vertices V and the number of edges E in the digraph? For each method,
 *  what is the order of growth of the best-case running time?
 *
 *  If you use hashing, you should assume the uniform hashing assumption
 *  so that put() and get() take constant time.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't
 *  forget to count the time needed to initialize the marked[],
 *  edgeTo[], and distTo[] arrays.
 *****************************************************************************/

Description:

                                              running time
method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)
	WORST CASE:
		~2E + 2V (creating two BFS objects)
		~2V (for adding common reachable vertices between BFS objects)
		~V (calculating candidate vertices (reachable vertices that are in both BFS objects))
		~V (Identyifying shortest common path vertices among candidate vertices)
	BEST CASE:
		~2E + 2V (creating two BFS objects)
		~2V (for adding common reachable vertices between BFS objects)
		~1 (calculating candidate vertices (reachable vertices that are in both BFS objects)) // could be constant because common reachable vertice collection could be size 0
		~1 (Identyifying shortest common path vertices among candidate vertices)
	
	

ancestor(int v, int w) // Exact same running time as length method ( we just return vertice value instead of distance value )
	WORST CASE:
		~2E + 2V (creating two BFS objects)
		~2V (for adding common reachable vertices between BFS objects)
		~V (calculating candidate vertices (reachable vertices that are in both BFS objects))
		~V (Identyifying shortest common path vertices among candidate vertices)
	BEST CASE:
		~2E + 2V (creating two BFS objects)
		~2V (for adding common reachable vertices between BFS objects)
		~1 (calculating candidate vertices (reachable vertices that are in both BFS objects)) // could be constant because common reachable vertice collection could be size 0
		~1 (Identyifying shortest common path vertices among candidate vertices)

length(Iterable<Integer> v,
       Iterable<Integer> w)
	WORST CASE:
		~2E + 3V (creating two BFS objects with many source vertices)
		~2V (for adding common reachable vertices between BFS objects)
		~V (calculating candidate vertices (reachable vertices that are in both BFS objects))
		~V (Identyifying shortest common path vertices among candidate vertices)
	BEST CASE:
		~2E + 2V (creating two BFS objects with many source vertices)  // best case is 'many sources' is actually just an iterable with one element.
		~2V (for adding common reachable vertices between BFS objects)
		~1 (calculating candidate vertices (reachable vertices that are in both BFS objects)) // could be constant because common reachable vertice collection could be size 0
		~1 (Identyifying shortest common path vertices among candidate vertices)

ancestor(Iterable<Integer> v,
         Iterable<Integer> w)
	WORST CASE:
		~2E + 3V (creating two BFS objects with many source vertices)
		~2V (for adding common reachable vertices between BFS objects)
		~V (calculating candidate vertices (reachable vertices that are in both BFS objects))
		~V (Identyifying shortest common path vertices among candidate vertices)
	BEST CASE:
		~2E + 2V (creating two BFS objects with many source vertices)  // best case is 'many sources' is actually just an iterable with one element.
		~2V (for adding common reachable vertices between BFS objects)
		~1 (calculating candidate vertices (reachable vertices that are in both BFS objects)) // could be constant because common reachable vertice collection could be size 0
		~1 (Identyifying shortest common path vertices among candidate vertices)




/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/


/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/


/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/




/**********************************************************************
 *  Have you completed the mid-semester survey? If you haven't yet,
 *  please complete the brief mid-course survey at https://goo.gl/gB3Gzw
 * 
 ***********************************************************************/


/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/