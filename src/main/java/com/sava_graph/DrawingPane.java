package com.sava_graph;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class DrawingPane extends JPanel implements MouseListener, MouseMotionListener{
	
	boolean connectionMode = false;
	Connection temp=new Connection();
	int id = 1;
	
	DrawingPane(){
		this.addMouseListener(this);			
		this.addMouseMotionListener(this);			
	}
		
	public void mouseClicked(MouseEvent arg0) {
		Component c = arg0.getComponent();				// gets component that send event
		if(c.getClass().getSimpleName().equals("DrawingPane")) {				// if click was made in drawing panel then create new node
			String w = (String)JOptionPane.showInputDialog(
                    UI.frame,
                    "Set node weight",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "1");
			Node n = new Node(id, Integer.parseInt(w));
			id++;
			Maths.nodes.add(n);
			this.add(n);		
			n.setBounds(arg0.getX() - n.radius/2, arg0.getY() - n.radius/2, n.radius+1, n.radius+1);			//look mouseDragged method in Node
		} else if(arg0.getSource().getClass().getSimpleName().equals("Node")) {			// if you clicked on the node then go to node connection mode
			Node d = (Node)arg0.getSource();			//get node that was clicked
			if(!connectionMode) {			// if it was not in node connection mode 
				temp = new Connection();			//then create new connection
				temp.from = d;			//set starting node for connection. clicked node in this case
				this.add(temp);			//add connection to drawing panel
			} else {			//if it WAS in connection mode then it is time to finish it
				String w = (String)JOptionPane.showInputDialog(
	                    UI.frame,
	                    "Set link weight",
	                    "Customized Dialog",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "1");
				temp.weight = Integer.parseInt(w);
				temp.to = d;			// set end node of connection to node clicked
				Maths.nodes.get(temp.to.id - 1).setPrev(temp.from);
				Maths.nodes.get(temp.from.id - 1).setNext(temp.to);
				temp.setBounds(0, 0, 1000, 1000);
				Maths.advLinks.add(temp);
			}
			connectionMode=!connectionMode;// switch modes 
		}
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

	public void mouseDragged(MouseEvent arg0) {
		this.repaint();					
	}

	//here i tried to make one end of line follow the mouse in connection mode but i failed.
	public void mouseMoved(MouseEvent arg0) {
		//System.out.println(arg0.getSource());
		if(connectionMode){
			//temp.to=new Point(arg0.getX(),arg0.getY());
			//temp.setBounds(temp.from.x>temp.to.x?temp.to.x:temp.from.x, temp.from.y>temp.to.y?temp.to.y:temp.from.y, Math.abs(temp.to.x-temp.from.x),Math.abs(temp.to.y-temp.from.y));
		}
	}
}
