package ex1.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph , Serializable{

	private int mc; //number of changes in the graph
	private int NodeSize; //number of Nodes
	private int EdgeSize; //number of Edges
	private HashMap<Integer,node_info> HashWGraph; ////contains all the vertices of the graph

	
	public class Node implements node_info , Serializable , Comparable<Node> { //Node class*******
		private int key; // value node
		private String info; //if visited
		private double tag; //distance from the apex in a DijkstraAlgorithm
		private Node parent; // Previous vertex
		private HashMap<Integer,node_info> hashNeighbors; //contains all the neighbors
		private HashMap<node_info , Double> hashEdge;

		public Node() {// constructor
			this.info = "white";
			this.tag = Integer.MAX_VALUE;
			this.key = Integer.MIN_VALUE;
			this.parent = null;
			this.hashNeighbors = new HashMap<Integer,node_info>();
			this.hashEdge = new HashMap<node_info , Double> ();
		}

		public Node (Node vertex) { // deep copy constructor
			this.info = vertex.info;
			this.tag = vertex.tag;
			this.hashNeighbors = new HashMap<Integer,node_info>();
			this.parent = vertex.parent;
			this.key = vertex.key;
			this.hashEdge = new HashMap <node_info , Double>();
		}

		public HashMap<node_info , Double>  getHashEdge() {
			return hashEdge;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public HashMap<Integer, node_info> getHashNighbors() {
			return hashNeighbors;
		}

		public void setKey(int key) {
			this.key = key;
		}

		/*
		 * Auxiliary function that checks whether the vertex has a neighbor with the key we received
		 */
		public boolean hasNi(int key) {
			return hashNeighbors.containsKey(key);
		}

		@Override
		public int getKey() {
			return this.key;
		}

		@Override
		public String getInfo() {
			return this.info;
		}

		@Override
		public void setInfo(String s) {
			this.info = s;
		}

		@Override
		public double getTag() {
			return this.tag;
		}

		@Override
		public void setTag(double t) {
			this.tag = t;
		}


		@Override
		public int compareTo(Node o) { //for the PriorityQueue
			if(this.getTag() > o.getTag()) {
				return 1;
			}
			if(this.getTag() < o.getTag()) {
				return -1;
			}
				return 0;
		}
		 public boolean equals(Object o){ //equals between two vertices
				if(o==null ){
					return false;
				}
				if(o instanceof Node) {
					return key==((Node) o).getKey();
				}
				return false;
			 }

		
	}
	//////////////////////////end Node///////////////////////////

	public WGraph_DS() { //constructor
		this.HashWGraph = new HashMap<Integer,node_info>();
		this.NodeSize = 0;
		this.EdgeSize = 0;
		this.mc = 0;
	}

	public WGraph_DS(WGraph_DS graph) { //deep copy constructor
		this.mc = graph.mc;
		this.NodeSize = graph.NodeSize;
		this.EdgeSize = graph.EdgeSize;
		this.HashWGraph = new HashMap<Integer,node_info>();
		for(node_info n_i :	graph.getV()) {
			this.HashWGraph.put(n_i.getKey(), n_i);
		}
	}



	public HashMap<Integer, node_info> getHashWGraph() {
		return HashWGraph;
	}

	@Override
	public node_info getNode(int key) {
		if(!this.getHashWGraph().isEmpty()) {
			return this.getHashWGraph().get(key); //if the graph contains the vertex we return the value (key) of the Node
		}
		return null; //if the graph doesn't contain the vertex with the value obtained
	}

	/*
	 * if hasEdge -  true
	 * else - return false
	 */
	@Override
	public boolean hasEdge(int node1, int node2) {
		boolean ans = false;
		if(node1 ==node2) {return ans;}
		Node n1 = (Node) this.getNode(node1);
		ans = n1.hasNi(node2);
		return ans;
	}

	/*
	 * if hasEdge - return the edge Weight
	 * else - return -1
	 */
	@Override
	public double getEdge(int node1, int node2) {
		if(hasEdge(node1,node2)) {
			Node n1 = (Node) this.getNode(node1);
			Node n2 = (Node) this.getNode(node2);
			return n1.getHashEdge().get(n2);
		}
		return -1;
	}
	 /*
	  *Add a vertex provided there is no vertex with this value already in the graph
	  *Raising the tracking of the number of vertices in the graph
	  */
	@Override
	public void addNode(int key) {
		if(!this.getHashWGraph().containsKey(key)) {
			Node n = new Node();
			n.setKey(key);
			this.getHashWGraph().put(n.getKey(), n);
			this.mc++; //Because the composition of the graph has changed
			this.NodeSize++;
		}
	}
	/*
	 * if the values we got are equal it is the same vertex and nothing is done
	 * Connect two vertices in the graph using an edge
	 * If the edge exists then we will update the size of the edge
	 * 
	 */
	@Override
	public void connect(int node1, int node2, double w) {
		if(node1 ==node2) {return;}
		Node n1 = (Node) this.getNode(node1);
		Node n2 = (Node) this.getNode(node2);
		if(hasEdge(node1,node2)) {
			n1.getHashEdge().replace(n2, n1.getHashEdge().get(n2), w);
			n2.getHashEdge().replace(n1, n2.getHashEdge().get(n1), w);
		}
		else {
			n1.getHashEdge().put(n2, w);
			n2.getHashEdge().put(n1, w);
			n1.getHashNighbors().put(node2, n2);
			n2.getHashNighbors().put(node1, n1);
			this.EdgeSize++;
		}
		this.mc++;
	}

	@Override
	public Collection<node_info> getV() {
		return this.HashWGraph.values();
	}

	@Override
	public Collection<node_info> getV(int node_id) {
		Node n = (Node) this.getNode(node_id);
		return n.getHashNighbors().values();
	}
	
	/*if we find a vertex with the key we got we will remove it from the graph by removing it from all the neighbor lists
	 * of its current neighbors
	 * We will then update the number of vertices in the graph and the changes made in the graph
	 */
	@Override
	public node_info removeNode(int key) {
		if(this.HashWGraph.containsKey(key)) {
			Node n = (Node) this.getHashWGraph().get(key);
			for(node_info node : n.getHashNighbors().values()) {
				((Node) node).getHashEdge().remove(n);
				((Node) node).getHashNighbors().remove(key);
				this.EdgeSize--;
			}
			this.mc++;
			this.NodeSize--;
			return this.HashWGraph.remove(key);
		}
		return null;
	}
	/*
	 * only if there is such an edge will we remove it:
	 * deletes from each vertex its neighbor from the list of neighbors and of course from its list of edges
	 * In addition, we will update the number of edges in the graph and the number of changes made
	 */
	@Override
	public void removeEdge(int node1, int node2) {
		if(hasEdge(node1 , node2)) {
			Node n1 = (Node) this.getNode(node1);
			Node n2 = (Node) this.getNode(node2);
			n1.getHashNighbors().remove(node2);
			n2.getHashNighbors().remove(node1);
			n1.getHashEdge().remove(n2);
			n2.getHashEdge().remove(n1);
			this.EdgeSize--;
			this.mc++;
		}

	}

	@Override
	public int nodeSize() {
		return this.NodeSize;
	}

	@Override
	public int edgeSize() {
		return this.EdgeSize;
	}

	@Override
	public int getMC() {
		return this.mc;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WGraph_DS other = (WGraph_DS) obj;
		if (EdgeSize != other.EdgeSize)
			return false;
		if (HashWGraph == null) {
			if (other.HashWGraph != null)
				return false;
		} else if (!HashWGraph.equals(other.HashWGraph))
			return false;
		if (NodeSize != other.NodeSize)
			return false;
		if (mc != other.mc)
			return false;
		return true;
	}
}
