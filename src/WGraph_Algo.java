package ex1.src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import ex1.src.WGraph_DS.Node;

public class WGraph_Algo implements weighted_graph_algorithms , Serializable {

	private WGraph_DS wgraph_ds;

	public WGraph_Algo() {
		this.wgraph_ds = new WGraph_DS();
	}
	
	/*
	 * The auxiliary function for all other functions that checks the shortest distance between vertices
	 * and checks if the graph is connected
	 * do this with the help of the priority queue data structure
	 * Summarizes the distances from the initial vertex that the function receives
	 * Thus updating to each vertex the shortest distance from the initial vertex
	 * In addition, she marks in gray / black each vertex she visited and all its neighbors
	 *  and thus knows at the end of the process whether the graph is connected
	 */

	public void DijkstraAlgorithm(WGraph_DS.Node node) {
		PriorityQueue<WGraph_DS.Node> pQueue = new PriorityQueue<WGraph_DS.Node>();
		node.setTag(0);
		pQueue.add(node);
		while(!pQueue.isEmpty()) {
			WGraph_DS.Node parent = pQueue.poll();
			for(node_info next : this.wgraph_ds.getV(parent.getKey())) {
				Node temp = (Node) next;
				double dis = this.wgraph_ds.getEdge(parent.getKey(), temp.getKey());
				if(temp.getTag() > parent.getTag() + dis) {
					temp.setTag(parent.getTag() + dis);
					temp.setParent(parent);
				}
				if(temp.getInfo().equals("white")) {
					pQueue.add(temp);
				}
			}
			parent.setInfo("black");	
		}
	}

	@Override
	public void init(weighted_graph g) {
		this.wgraph_ds = (WGraph_DS) g;

	}

	@Override
	public weighted_graph getGraph() {
		return wgraph_ds;
	}

	@Override
	public weighted_graph copy() {
		WGraph_DS Wgraph = new WGraph_DS(this.wgraph_ds); 
		return (weighted_graph) Wgraph;
	}
	
	/*
	 * A function that checks whether the graph is connected
	 * The function is aided by DijkstraAlgorithm which checks if all distances of all vertices are initialized 
	 * if not initialized (and left with max value integer) then the graph is not connected
	 * If the graph is connected, return true
	 * if he doesn't return false
	 * 
	 */
	@Override
	public boolean isConnected() {
		boolean ans = true;
		if(this.wgraph_ds.nodeSize() <= 1) {
			return ans;
		}
		if(this.wgraph_ds.edgeSize() == 0 && this.wgraph_ds.nodeSize() > 1) {
			ans = false;
		}
		WGraph_DS copyWDs = (WGraph_DS) this.copy();
		Iterator<node_info> it = copyWDs.getV().iterator();
		WGraph_DS.Node n = (Node)it.next();
		DijkstraAlgorithm(n);
		while(it.hasNext()) {
			if(n.getTag() == Integer.MAX_VALUE) {
				ans = false;
			}
			n = (Node) it.next();
		}
		return ans;
	}
	/*
	 * if one of the certices we got doesn't exist in fact its value is null we will return -1
	 * in addition even if the values we recevied are equal in fact it is the same vertex and in this case we also return -1
	 * with the help of the auxiliary function (DijkstraAlgorithm) we will create connections to the shortest path,
	 * then we return the shortest path between the vertices
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		WGraph_DS copyWDs = new WGraph_DS(this.wgraph_ds);
		Node n = (Node)copyWDs.getNode(src);
		Node s = (Node) copyWDs.getNode(dest);
		if((n==null && s==null) || (src ==dest)) {return -1;}//If the vertices not contained in the graph
		else {
			DijkstraAlgorithm(n); 	//Running on the graph according to an auxiliary function
			double shortestPathDist =  copyWDs.getNode(dest).getTag();
			return shortestPathDist;
		}
	}
	/*we will return a collection (list) of the shortest path from the vertex with source value we received,
	 * With the help of the auxiliary function (DijkstraAlgorithm) we will create connections to the shortest path,
	 * We will go back from the destination vertex with the help of parent and so we will add all the vertices one by one to the list
	 * and then we will return the list with the shortest path
	 */
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		WGraph_DS g=new WGraph_DS(this.wgraph_ds);
		LinkedList<node_info> list=new LinkedList<node_info>();
		Node n=(Node)g.getNode(src);
		Node d=(Node)g.getNode(dest);
		if(n != null && d != null) {
			DijkstraAlgorithm(n);
			if(d.getTag() == Integer.MAX_VALUE) {return null;}
			while(d.getParent()!=null) {
				list.addFirst(d);
				d=d.getParent();
			}
			list.addFirst(n);
			return list;
		}
		return null;
	}
	//
	/*
	 * Saves this weighted (undirected) graph to the given file
     *  file - the file name (may include a relative path).
     * return true - iff the file was successfully saved
     * (Realized with Serializable)
	 */
	
	@Override
	public boolean save(String file) {
		boolean ans = false;
		try {
			FileOutputStream FileName = new FileOutputStream(file);
			ObjectOutputStream Object = new ObjectOutputStream(FileName);
			Object.writeObject(this.wgraph_ds);
			Object.close();
			FileName.close();
			ans = true;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(" Get Exception!!! ");
			ans = false;
		}
		return ans;
	}

	/*
	 *  * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed to the loaded one,
     *  in case the graph was not loaded the original graph should remain "as is".
     * @return true - if the graph was successfully loaded.
     * return false if he doesn't
     * (Realized with Serializable)
	 */
	@Override
	public boolean load (String file) {
		boolean ans = false;
		try {
			FileInputStream FileName = new FileInputStream(file);
			ObjectInputStream Object = new ObjectInputStream(FileName);
			Object.close();
			FileName.close();
			ans = true;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(" Get Exception!!! ");
			ans = false;
		}
		return ans;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WGraph_Algo other = (WGraph_Algo) obj;
		if (wgraph_ds == null) {
			if (other.wgraph_ds != null)
				return false;
		} else if (!wgraph_ds.equals(other.wgraph_ds))
			return false;
		return true;
	}
	
}
