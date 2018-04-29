/******************************************************************************
 *  Name:  GORDON PORTZLINE
 *  NetID:    
 *  Precept:  
 *
 *  Partner Name:    SHAWN NEVELLE 
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
	Reading in info from hypernyms.txt = ~5E  // we noticed that in the file, no line is has more than 6 values (this was just some manual checking, we didn't write an automated test to be 100% positive)
	Creating reverse Digraph = ~V  // this is part of the isRootedDAG check
	Adding appropriate edges to reverse Digraph = ~5E // this is part of the isRootedDAG check
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

Description:  We basically broke it down into 4 steps, 1: creating 2 BFS objects, 2: narrowing our results to 
				vertices that have a path to them from a given source, 3: calulating possible ancestor/length
				computation candidates by removing vertices that are not contained in BOTH BFS object paths
				4: calculating distance (length) between our inputs or returning the ancestor vertex (synset id).

                                              running time
method                               best case            worst case     // We actually took the running time analysis given in the algs4 book
------------------------------------------------------------------------ // at face value and then added our additional overhead on-top
length(int v, int w)													 // (sorry, we took a bit of a shortcut in the analysis, but we did
	WORST CASE:															 //  still spend significant effort on our analysis AND	
		~2E + 2V (creating two BFS objects)								 //  found a place to improve our worst-case performance!)
		~2V (for adding common reachable vertices between BFS objects)
		~V (calculating candidate vertices (reachable vertices that are in both BFS objects))
		~V (Identyifying shortest common path vertices among candidate vertices)
	BEST CASE:
		~2E + 2V (creating two BFS objects)
		~2V (for adding common reachable vertices between BFS objects)
		~1 (calculating candidate vertices (reachable vertices that are in both BFS objects)) // could be constant because common reachable vertex collection could be size 0
		~1 (Identyifying shortest common path vertices among candidate vertices)
	
	

ancestor(int v, int w) // Exact same running time as length method ( we just return a vertex value instead of a distance value )
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

// BELOW METHODS IS WHERE WE HANDLED THE WORST CASE OF OUR CLIENT INPUT BEING LONGER THAN THE NUMBER OF VERTICES 
// (thus potentially removing longer inputs with what has to be duplicate entries since we maintain our index bound checking)
length(Iterable<Integer> v,
       Iterable<Integer> w)
	WORST CASE:
		~2E + 3V (creating two BFS objects with many source vertices)  // additional ~V for checking length AND boundaries of parameters
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
		~2E + 3V (creating two BFS objects with many source vertices) // additional ~V for checking length AND boundaries of parameters
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
  There is a possible bug in the Outcast class where the output is not what the instructions say should be the right answer.
	outcast8.txt: coffee --> should be bed according to instructions
	outcast11.txt: peach --> should be potato according to instructions
  However, after investigating this bug and enlisting some additional peer reviews of my code, the conclusions were either 
  not helpful in finding the cause of the bug or the code is actually correct and the data files provided are different
  than perhaps they were before.
  
  Please see the last few lines of the Wordnet class for a sample of some of the manual testing done to troubleshoot the issue.

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
 
 I enlisted the help of a Java tutor in the business building (I can't remember his name!),
 when I couldn't seem to workout the problem with my Outcast class. He helped me mostly
 by being cheerful and encouraging me to 'play' with my code instead of continuing to bang 
 my head against the wall.

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/


/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/

 I partnered with Shawn Neville and we definitely followed protocol. I actually ended up writing most of the code,
 but Shawn was helpful in prividing insights and ideas while reviewing my code. We both contributed to the run
 time analysis provided above.

/**********************************************************************
 *  Have you completed the mid-semester survey? If you haven't yet,
 *  please complete the brief mid-course survey at https://goo.gl/gB3Gzw
 * 
 ***********************************************************************/

 Does this apply to the CS 2420 class at SLCC I wonder?

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
 
 Awesome assignment. I'd like to continue working on similar projects and learn more about natural language processing.