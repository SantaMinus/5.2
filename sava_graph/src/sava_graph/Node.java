package sava_graph;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import java.util.ArrayList;

//node that represents a vertex in paralel algortihm
import javax.swing.*;
@SuppressWarnings("serial")
public class Node extends JPanel implements MouseMotionListener,MouseListener{
	
	int radius=50, id, weight, adjacency=0, critTimePath=0;
	ArrayList<Node> prev = new ArrayList<Node>();
	ArrayList<Node> next = new ArrayList<Node>();
	
	Node(int id, int w){
		this.setOpaque(false);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.id=id;
		this.weight=w;
	}
	
	public void paint(Graphics g){
		g.drawOval(0, 0, radius, radius);			//label drawing should be added here
		//g.drawString(Integer.toString(id), 0, 0);
	}


	//mouse dragging
	public void mouseDragged(MouseEvent arg0) {
		// sets new position of panel in parent view
		//since arg0's will return coordinates regarding its own panel we take these coordinates and add to current position in main panel (this.getX+arg0.getX)
		//since i messed up with naming radius is really a diameter so to center coordinates we do minus diameter/2 (radius/2)
		// i think you should rename it ;)
		this.setBounds(this.getX()+arg0.getX()-radius/2, this.getY()+arg0.getY()-radius/2, radius+1, radius+1);
		//next to line forward mouse event to parent panel. it is done to detect mouse clicks and make possible to connect to nodes together
		//since on this level node knows only itself and parent, so only parent can connect two nodes
		Component source = (Component)arg0.getSource();
		source.getParent().dispatchEvent(arg0);
	}


	public void mouseMoved(MouseEvent arg0) {
		//next to line forward mouse event to parent panel
		Component source=(Component)arg0.getSource();
		source.getParent().dispatchEvent(arg0);
	}

	public void mouseClicked(MouseEvent arg0) {
		//next to line forward mouse event to parent panel
		Component source=(Component)arg0.getSource();
		source.getParent().dispatchEvent(arg0);
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {
		//next to line forward mouse event to parent panel
		Component source=(Component)arg0.getSource();
		source.getParent().dispatchEvent(arg0);
	}

	public void mouseReleased(MouseEvent arg0) {
		//next to line forward mouse event to parent panel
		Component source=(Component)arg0.getSource();
		source.getParent().dispatchEvent(arg0);
	}
	
	public void countAdj(){
		for(int j=0; j<Maths.matrix.length; j++){
			this.adjacency+=Maths.matrix[this.id-1][j];
			this.adjacency+=Maths.matrix[j][this.id-1];
		}
	}
	
	public void setPrev(Node node){
		this.prev.add(node);
	}
	
	public void setNext(Node node){
		this.next.add(node);
	}
	
	public void setCritPath(int x){
		this.critTimePath+=weight+x;
	}
}
