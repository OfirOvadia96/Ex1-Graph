package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;


class WGraph_Algotest {
	   private static Random _rnd = null;

	@Test
	void isConnectedtest() {
		WGraph_DS g1 = (WGraph_DS) createGraph(0,0,1);
		WGraph_Algo ag1 = new WGraph_Algo();
		ag1.init(g1);
		assertTrue(ag1.isConnected());
		
		  g1 = (WGraph_DS) createGraph(1,0,1);
	        ag1 = new WGraph_Algo();
	        ag1.init(g1);
	        assertTrue(ag1.isConnected());

	         g1 = (WGraph_DS) createGraph(2,0,1);
	        ag1 = new WGraph_Algo();
	        ag1.init(g1);
	        assertFalse(ag1.isConnected());
	        
	         g1 = (WGraph_DS) createGraph(2,1,1);
	        ag1 = new WGraph_Algo();
	        ag1.init(g1);
	        assertTrue(ag1.isConnected());
	        
	        g1 = (WGraph_DS) createGraph(100 , 300 ,1);
	        ag1.init(g1);
	        assertTrue(ag1.isConnected());
	}
	
	@Test
	void shortestPathDist() {
		  weighted_graph g1 = createGraph(11 ,0 ,1);
		  g1.connect(1, 2, 5);
		  g1.connect(2, 3, 5);
		  g1.connect(3, 4, 5);
		  g1.connect(4, 5, 5);
		  g1.connect(5, 6 ,5);
		  g1.connect(6, 7, 5);
		  g1.connect(7, 8, 5);
		  g1.connect(8, 9, 5);
		  g1.connect(9, 10, 5);
		  g1.connect(2, 10, 5);
	        WGraph_Algo ag1 = new WGraph_Algo();
	        ag1.init(g1);
	       assertFalse(ag1.isConnected());
	        double d = ag1.shortestPathDist(1,10);
	        assertEquals(d, 10);
	}
	
	@Test
	void shortestPath() {
		  WGraph_DS g1 = (WGraph_DS) createGraph(12 , 0 , 1);
		  g1.connect(0, 1, 5);
		  g1.connect(1, 2, 5);
		  g1.connect(2, 3, 5);
		  g1.connect(3, 4, 5);
		  g1.connect(4, 5, 5);
		  g1.connect(5, 6, 5);
		  g1.connect(6, 7, 5);
		  g1.connect(7, 8, 5);
		  g1.connect(8 ,9 , 5);
		  g1.connect(9, 10, 5);
		  g1.connect(0, 11 , 30);
		  g1.connect(11, 10, 5);
	       WGraph_Algo ag1 = new WGraph_Algo();
	        ag1.init(g1);
	        List<node_info> s = ag1.shortestPath(0,10);
	        int[] checkKey = {0, 11, 10};
	        int i = 0;
	        for(node_info n: s) {
	        	assertEquals(n.getKey(), checkKey[i]);
	        	i++;
	        }
	}
	
	@Test
	void save_and_load() {
		  WGraph_DS g1 = (WGraph_DS) createGraph(10,35,1);
	        weighted_graph_algorithms ag1 = new WGraph_Algo();
	        ag1.init(g1);
	        String str = "g1.object";
	        ag1.save(str);
	        WGraph_DS g2 = (WGraph_DS) createGraph(10,35,1);
	        ag1.load(str);
	        assertEquals(g1,g2);
	        g1.removeNode(0);
	        assertNotEquals(g1,g2);
	}
	
	
	 public static weighted_graph createGraph(int v_size, int e_size, int seed) {
	        weighted_graph g = new WGraph_DS();
	        _rnd = new Random(seed);
	        for(int i=0;i<v_size;i++) {
	            g.addNode(i);
	        }
	        
	        int[] nodes = nodes(g);
	        while(g.edgeSize() < e_size) {
	            int a = nextRnd(0,v_size);
	            int b = nextRnd(0,v_size);
	            int i = nodes[a];
	            int j = nodes[b];
	            double w = _rnd.nextDouble();
	            g.connect(i,j, w);
	        }
	        return g;
	    }
	 
	  private static int nextRnd(int min, int max) {
	        double v = nextRnd(0.0+min, (double)max);
	        int ans = (int)v;
	        return ans;
	    }
	  private static double nextRnd(double min, double max) {
	        double d = _rnd.nextDouble();
	        double dx = max-min;
	        double ans = d*dx+min;
	        return ans;
	    }
	  private static int[] nodes(weighted_graph g) {
	        int size = g.nodeSize();
	        Collection<node_info> V = g.getV();
	        node_info[] nodes = new node_info[size];
	        V.toArray(nodes); // O(n) operation
	        int[] ans = new int[size];
	        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
	        Arrays.sort(ans);
	        return ans;
	    }
}
