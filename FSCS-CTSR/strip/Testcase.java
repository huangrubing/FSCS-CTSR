package Strip;

import java.util.ArrayList;

public class Testcase {
	public ArrayList<Double> list=new ArrayList<>();
	public double minDistance;
	public double average;
	public double variance=0;
	public void preprocess(Testcase this){
		double sum=0;
		for (int i = 0; i < this.list.size(); i++) {
			sum = sum + this.list.get(i);
		}
		this.average=sum/this.list.size();
		for (int i = 0; i < this.list.size(); i++) {
			this.variance = variance + Math.pow(this.list.get(i) - average, 2);
		}
		this.variance=Math.sqrt(this.variance/this.list.size());
	}
		
	
}
  