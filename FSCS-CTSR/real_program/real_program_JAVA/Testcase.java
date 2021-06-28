package complex;

import java.util.ArrayList;


public class Testcase {
//	public ArrayList<Integer> list=new ArrayList<>();
	public ArrayList<Double> list=new ArrayList<>();
	public double minDistance;
	public ArrayList<Double> meanAndVar=new ArrayList<>();
	public void preprocess(Testcase this){
		double average;
		double variance=0;
		double sum=0;
		for (int i = 0; i < this.list.size(); i++) {
			sum = sum + this.list.get(i);
		}
		average=sum/this.list.size();
		for (int i = 0; i < this.list.size(); i++) {
			variance =variance + Math.pow(this.list.get(i) - average, 2);
		}
		variance=Math.sqrt(variance/this.list.size());
		this.meanAndVar.add(average);
		this.meanAndVar.add(variance);
	}
	
}
  