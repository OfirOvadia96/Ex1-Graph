<h1>Ex1-Graph:</h1>


This task deals with building a data structure of a weighted and undirectional graph

**These are three interfaces:**
* node_info
* weighted_graph 
* weighted_graph_algorithms

<h2>Node:</h2>

**The Node class is an internal class of (WGraph_DS) and implements the node_info interface the class contains:**

* **Int key** - the value of the vertex
* **HashMap data structures** - that hold all its neighbors in the graph
* **Another HashMap data structure** -  that holds the list of edges of the same vertex
* **tag** - distance from the initial vertex When going through the graph in the Dijkstra Algorithm
* **parent** - which is the previous vertex to the current vertex from which we reached in DijkstraAlgorithm in order to obtain the shortest path
* **info** -  which indicates if we visited the same vertex in the graph in DijkstraAlgorithm, and so we will know if the graph is linked

<h3>Department capabilities:</h3>

* tell if there is a particular neighbor to the same vertex,
* Can compare vertices,
* gives access to its variables using get functions.

<h2>WGraph_DS:</h2>

**The WGraph_DS class implements the weighted_graph interface, the class contains:**

* **HashMap data** structures that hold all the vertices of the graph
* **NodeSize** - Counts the number of vertices in the graph
* **EdgeSize** - Counts the number of sides in a graph
* **MC** - A variable that documents the number of changes made to the graph

<h3>Department capabilities:</h3>

* Adding and removing vertices from the graph
* Adding and removing edges from the graph
* Check if two vertices contained in the graph are neighbors directly
* Get a value of a certain vertex or a value of a certain edge
* Get the list of neighbors of a particular vertex
* Get the vertex values in the graph

<h2>WGraph_Algo:</h2>

**The WGraph_Algo class that implements the weighted_graph_algorithms interface The class contains:**
**WGraph_DS type when this class is built on it ("upgrades" it and adds additional functions)**

<h3>Department capabilities:</h3>

* Check if the graph is connected
* find the shortest path between two vertices in the graph (the fastest way)
* Write the graphic object in the file and read the graph back from it

* These functions use the **DijkstraAlgorithm** function, - a graph scanning algorithm that analyzes the graph and updates all the information in it ,
helps solve the problem of the shortest path between two vertices contained in the graph and also checks if the graph is connected.


