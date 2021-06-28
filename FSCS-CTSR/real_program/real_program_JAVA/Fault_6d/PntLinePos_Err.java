package Fault_6d;

import java.util.Scanner;


public class PntLinePos_Err {
	
	/**
	 * Return true if point (x2, y2) is on the left side of the directed line from
	 * (x0, y0) to (x1, y1)
	 */
	public static boolean leftOfTheLine(double x0, double y0, double x1, double y1, double x2, double y2) {
		return (x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0) > 0;
	}

	/**
	 * Return true if point (x2, y2) is on the same line from (x0, y0) to (x1, y1)
	 */
	public static boolean onTheSameLine(double x0, double y0, double x1, double y1, double x2, double y2) {
		return Math.abs((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) < 2;     //ERROR < 0.000000001;
	}

	/**
	 * Return true if point (x2, y2) is on the line segment from (x0, y0) to (x1,
	 * y1)
	 */
	public static boolean onTheLineSegment(double x0, double y0, double x1, double y1, double x2, double y2) {
		return onTheSameLine(x0, y0, x1, y1, x2, y2) && ((x0 <= x2 && x2 <= x1) || (x0 >= x2 && x2 >= x1));
	}

	public static int getTheLocation(double x0, double y0, double x1, double y1, double x2, double y2) {
		if (leftOfTheLine(x0, y0, x1, y1, x2, y2))
			return 0; // 左边
		else if (onTheLineSegment(x0, y0, x1, y1, x2, y2))
			return 1; // 在线段上
		else if (onTheSameLine(x0, y0, x1, y1, x2, y2))
			return 2; // 在直线上
		else
			return 3; // 右边
	}

}
