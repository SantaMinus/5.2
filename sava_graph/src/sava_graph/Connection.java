package sava_graph;
import java.awt.Graphics;

import javax.swing.*;

@SuppressWarnings("serial")
public class Connection extends JPanel {
	Node from;
	Node to;
	int weight = 0;
	
	public Connection() {
		this.setOpaque(false);
	}
	
	public void paint(Graphics g) {
		int x = (from.getX() < to.getX()? from.getX() : to.getX()) +
				to.radius/2 + ((from.getX() > to.getX()? from.getX() : to.getX()) -
				(from.getX() < to.getX()? from.getX() : to.getX())) / 2;
		int y = (from.getY() < to.getY()? from.getY() : to.getY()) + to.radius/2 + ((from.getY() > to.getY()? from.getY() : to.getY()) - (from.getY() < to.getY()? from.getY() : to.getY())) / 2;
		g.drawString("" + weight, x, y);
		g.drawLine(from.getX() + from.radius/2, from.getY() + from.radius/2, to.getX() + to.radius/2, to.getY() + to.radius/2);
		g.drawOval(to.getX() + to.radius/2 - 3, to.getY() + to.radius/2 - 3, 5, 5);
	}

	public void repaint(Graphics g){
		g.drawLine(0, 0, from.getX() - to.getX(), from.getY() - to.getY());
	}
}
