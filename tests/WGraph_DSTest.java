package ex1.tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.WGraph_DS.Node;

class WGraph_DSTest {

	@Test
	void hasEdge() {
		WGraph_DS g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(1, 2, 3);
		boolean ansT1 = false;
		boolean ansF1 = true;
		boolean ansT2 = false;
		ansT1 = g.hasEdge(1, 2);
		ansT2 = g.hasEdge(2, 1);
		ansF1 = g.hasEdge(1, 3);
		assertTrue(ansT1);
		assertFalse(ansF1); 
		assertTrue(ansT2);
	}

	@Test
	void getEdge() {
		WGraph_DS g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(1, 2, 3);
		double EdgeBeforeR = g.getEdge(1 , 2);
		double noEdge = g.getEdge(1, 3);
		assertEquals( 3 , EdgeBeforeR);
		assertEquals(-1 , noEdge);
		g.removeEdge(1, 2);
		double noEdgeAfterR = g.getEdge(1, 3);
		assertEquals(-1 , noEdgeAfterR);
	}

	@Test
	void addNode_getNode() {
		WGraph_DS g = new WGraph_DS();
		g.addNode(2);
		g.getNode(2);
		assertEquals(2 , g.getNode(2).getKey());
		assertEquals(null ,g.getNode(30));
		assertEquals(1 ,g.nodeSize());
		assertEquals(0 ,g.edgeSize());
		assertEquals(1 ,g.getMC());
	}

	@Test	
	void connect() {
		WGraph_DS g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(5);
		g.connect(1, 2, 2);
		g.connect(1, 3, 3);
		g.connect(1, 4, 4);
		assertEquals(3 ,g.edgeSize());
		assertEquals(5 ,g.nodeSize());
		assertEquals(8 ,g.getMC());
		assertEquals(-1 ,g.getEdge(2, 3));
		g.removeEdge(1, 4);
		assertEquals(2 ,g.edgeSize());
		assertEquals(-1 ,g.getEdge(1 , 4));
		g.connect(1, 2, 1);
		assertEquals(2 ,g.edgeSize());
		assertEquals(10 ,g.getMC());
		assertEquals(1 ,g.getEdge(1, 2));
		g.connect(1, 1, 100);
		assertEquals(2 ,g.edgeSize());
	}

	@Test
	void removeNode() {
		WGraph_DS g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		assertEquals(4 ,g.nodeSize());
		g.removeNode(4);
		g.removeNode(3);
		g.removeNode(2);
		assertEquals(1 ,g.nodeSize());
		g.removeNode(100);
		assertEquals(1 ,g.nodeSize());
		assertEquals(7 ,g.getMC());
		assertEquals(null ,g.removeNode(300));
	}

	@Test
	void removeEdge() {
		WGraph_DS g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(100);
		g.connect(1, 2, 2);
		g.connect(1, 3, 3);
		g.connect(1, 4, 4);
		g.connect(1, 100, 99);
		g.removeEdge(1, 2);
		g.removeEdge(1, 3);
		g.removeEdge(1, 4);
		assertEquals(-1 ,g.getEdge(1, 2));
		g.removeEdge(1, 2);
		assertEquals(-1 ,g.getEdge(1, 3));
		assertEquals(-1 ,g.getEdge(1, 4));
		assertFalse(g.hasEdge(1, 3));
		assertTrue(g.hasEdge(1, 100));
	}

	@Test
	void nodesize_edgesize_mc() {
		WGraph_DS g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(5);
		g.addNode(6);
		g.addNode(7);
		g.addNode(8);
		g.removeNode(8);
		g.removeNode(7);
		g.removeNode(6);
		g.removeNode(5);
		g.removeNode(4);
		g.connect(1, 2, 400);
		g.connect(1, 3, 5);
		g.connect(1, 2, 3);
		assertEquals(16 , g.getMC());
		assertEquals(16 , g.getMC());
		assertEquals(3 , g.nodeSize());
		assertEquals(2 , g.edgeSize());	
	}

	@Test
	void getV() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(0,1,1);
		g.connect(0,2,2);
		Collection<node_info> v = g.getV();
		Iterator<node_info> iter = v.iterator();
		while (iter.hasNext()) {
			node_info n = iter.next();
			assertNotNull(n);
		}
	}
}
