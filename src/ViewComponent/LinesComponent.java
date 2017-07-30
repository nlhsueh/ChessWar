package ViewComponent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LinesComponent extends JComponent{
	
	private final LinkedList<Line> lines = new LinkedList<Line>();
	
	public void addLine(int x1, int x2, int x3, int x4) {
	    addLine(x1, x2, x3, x4, Color.black);
	}
	
	public void addLine(int x1, int x2, int x3, int x4, Color color) {
	    lines.add(new Line(x1,x2,x3,x4, color));        
	    repaint();
	}
	
	public void clearLines() {
	    lines.clear();
	    repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    for (Line line : lines) {
	    	/* color */
	        g.setColor(line.color);
	        /* width */
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setStroke(new BasicStroke(3));
	        g.drawLine(line.x1, line.y1, line.x2, line.y2);
	    }
	}

}