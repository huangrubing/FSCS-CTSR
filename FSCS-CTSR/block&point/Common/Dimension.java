package Common;

public class Dimension {
	public double min;   //存放的是该维度的最小值
	double max;   //存放该维度的最大值
	double range; //存放该维度的取值的范围
	public Dimension(double min,double max) {
		// TODO 自动生成的构造函数存根
		this.min=min;
		this.max=max;
		range=max-min;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getRange() {
		return range;
	}
	

	
}
