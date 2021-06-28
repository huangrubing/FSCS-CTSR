package Fault_8d;

/**
 * 判断点与三角形的位置
 */
import java.awt.geom.Line2D;

public class PntTrianglePos extends PUT_8D {

	// MyPoint is defined in Exercise09_04
	public MyPoint p1, p2, p3;

	public PntTrianglePos() {

	}

	public PntTrianglePos(double x1, double y1, double x2, double y2, double x3, double y3) {
		this.p1 = new MyPoint(x1, y1);
		this.p2 = new MyPoint(x2, y2);
		this.p3 = new MyPoint(x3, y3);
	}

	public boolean contains(double x, double y) {
		double maxX = Math.max(p1.getX(), Math.max(p2.getX(), p3.getX()));
		double minX = Math.min(p1.getX(), Math.min(p2.getX(), p3.getX()));
		double maxY = Math.max(p1.getY(), Math.max(p2.getY(), p3.getY()));
		double minY = Math.min(p1.getY(), Math.min(p2.getY(), p3.getY()));

		if (x < minX || x > maxX || y < minY || y > maxY) {
			return false; // Outside the bounding rectangle of the triangle
		}

		Line2D side1 = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		Line2D side2 = new Line2D.Double(p1.getX(), p1.getY(), p3.getX(), p3.getY());
		Line2D side3 = new Line2D.Double(p2.getX(), p2.getY(), p3.getX(), p3.getY());

		if (side1.contains(x, y) || side2.contains(x, y) || side3.contains(x, y)) {
			return true; // On the border of the triangle
		}

		double topY = -10;
		if (x == p1.getX()) {
			topY = p1.getY();
		} else if (x == p2.getX()) {
			topY = p2.getY();
		} else if (x == p3.getX()) {
			topY = p3.getY();
		}

		Line2D line;
		if (y < topY)
			line = new Line2D.Double(x, y, x, minY);
		else
			line = new Line2D.Double(x, y, x, maxY);

		int hits = 0;
		if (line.intersectsLine(side1))
			hits++;

		if (line.intersectsLine(side2))
			hits++;

		if (line.intersectsLine(side3))
			hits++;

		if (hits % 2 == 0)
			return false;
		else
			return true;
	}

	public boolean containsErr(double x, double y) {
		double maxX = Math.max(p1.getX(), Math.max(p2.getX(), p3.getX()));
		double minX = Math.min(p1.getX(), Math.min(p2.getX(), p3.getX()));
		double maxY = Math.max(p1.getY(), Math.max(p2.getY(), p3.getY()));
		double minY = Math.min(p1.getY(), Math.min(p2.getY(), p3.getY()));

		if (x < minX + 1 || x > maxX || y < minY || y > maxY) { // ERROR if (x < minX || x > maxX || y < minY || y > maxY) {
			return false; // Outside the bounding rectangle of the triangle
		}

		Line2D side1 = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		Line2D side2 = new Line2D.Double(p1.getX(), p1.getY(), p3.getX(), p3.getY());
		Line2D side3 = new Line2D.Double(p2.getX(), p2.getY(), p3.getX(), p3.getY());

		if (side1.contains(x, y) || side2.contains(x, y) || side3.contains(x, y)) {
			return true; // On the border of the triangle
		}

		double topY = -10;
		if (x == p1.getX()) {
			topY = p1.getY();
		} else if (x == p2.getX()) {
			topY = p2.getY();
		} else if (x == p3.getX()) {
			topY = p3.getY();
		}

		Line2D line;
		if (y < topY)
			line = new Line2D.Double(x, y, x, minY);
		else
			line = new Line2D.Double(x, y, x, maxY);

		int hits = 0;
		if (line.intersectsLine(side1))
			hits++;

		if (line.intersectsLine(side2))
			hits++;

		if (line.intersectsLine(side3))
			hits++;

		if (hits % 2 == 0)
			return false;
		else
			return true;
	}

	@Override
	public boolean producesError(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		// TODO Auto-generated method stub
		this.p1 = new MyPoint(x1, y1);
		this.p2 = new MyPoint(x2, y2);
		this.p3 = new MyPoint(x3, y3);
		if (contains(x4, y4) != containsErr(x4, y4)) {
			return true;
		}
		return false;
	}

	@Override
	public double modified_fn(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		// TODO Auto-generated method stub
		this.p1 = new MyPoint(x1, y1);
		this.p2 = new MyPoint(x2, y2);
		this.p3 = new MyPoint(x3, y3);
		containsErr(x4, y4);
		return 0;
	}

}

class MyPoint {
	private double x;
	private double y;

	public MyPoint() {
	}

	public MyPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double distance(MyPoint secondPoint) {
		return distance(this, secondPoint);
	}

	public static double distance(MyPoint p1, MyPoint p2) {
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
