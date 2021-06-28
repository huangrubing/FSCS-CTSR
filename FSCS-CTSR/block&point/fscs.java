package FSCS_ART;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import Common.Dimension;
import Common.Testcase;


public class fscs {
	ArrayList<Dimension> dim_list;// 负责的是维度的范围值
	ArrayList<Double> mut_area_r; // 用于存储的是多个面积不同时的每个失效的面积的r
	ArrayList<Double> points = new ArrayList<>(); // 针对的是生成的失效区域的点坐标
	ArrayList<ArrayList<Double>> mutiple; // 存放的是 多个失效域的坐标点
	ArrayList<Testcase> candidate=new ArrayList<>();
	ArrayList<Testcase> Selected;
	double totalsize; // 整个输入域的面积
	double fail_rate; // 失效率
	double fail_length; // 失效域的边长
	int fail_number;// 失效域的个数
	boolean prodomain;// 多个失效区域是否有主区域
	double fail_totalsize;
	int k;//已执行测试用例集合大小
	ArrayList<Double> random_number = new ArrayList<>();// 存放随机生成数；
	Double sum = 0.0; // 用于多个面积不同的
	Double r = 0.0;// 用于多个面积不同的面积的R参数

	public fscs(ArrayList<Dimension> dim_list, double failrate, int fail_number, boolean prodomain,
			double r) {
		/*                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		 * 求输入域面积，这是共存在步骤
		 */
		totalsize = dim_list.get(0).getRange();
		for (int i = 1; i < dim_list.size(); i++) {
			totalsize = totalsize * dim_list.get(i).getRange();
		}
		this.dim_list = dim_list;
		this.fail_rate = failrate;
		this.fail_totalsize = failrate * totalsize;
		this.fail_number = fail_number;
		this.prodomain = prodomain;
		this.r = r;
		/*
		 * 针对的单个的正方形失效区域的面积的边长
		 */
		if (fail_number == 1) {
			fail_length = sqrt(fail_totalsize, dim_list.size());}
		 else if (prodomain) { // 多个面积不同的正方形
			mut_area_r = new ArrayList<>();
			for (int i = 0; i < fail_number - 1; i++) {
				random_number.add(Math.random());
				sum = sum + random_number.get(i);
			}
		} else { // 多个面积相同的正方形
			fail_length = sqrt(fail_totalsize / fail_number, dim_list.size());
		}
//		System.out.println("总面积为" + totalsize + " fail_length:" + fail_length);
	}

	public int run() {

		if (fail_number == 1) {
			// 随机生成失效区域，考虑到边界情况，最大值只能是1-fail_rate
			for (int i = 0; i < dim_list.size(); i++) {
				double temp = dim_list.get(i).getMin() + ((dim_list.get(i).getRange()-fail_length) * new Random().nextDouble());
				points.add(temp);
			}

//			System.out.println("失效的坐标点" + points);

		
		} 
		else if (prodomain) {
			mutiple = new ArrayList<>(); // 存放多个坐标点
			ArrayList<Double> templist = new ArrayList<>();
			double first_r=sqrt( random_number.get(0) / sum * (1 - this.r) * fail_totalsize, dim_list.size());
			for (int j = 0; j < dim_list.size(); j++) {
				double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-first_r) * new Random().nextDouble());
				templist.add(temp);
			}
			mutiple.add(templist);
			mut_area_r.add(first_r);
			for (int i = 1; i < fail_number - 1; i++) {
				templist = new ArrayList<>();
				Double area = random_number.get(i) / sum * (1 - this.r) * fail_totalsize;
				double r = sqrt(area, dim_list.size());
				mut_area_r.add(r);
				// System.out.println("此时的面积为："+area+"此时的半径"+r);
				for (int j = 0; j < dim_list.size(); j++) {
					double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-r) * new Random().nextDouble());
					templist.add(temp);
				}
				while(fail2(templist)){
					templist=new ArrayList<>();
					for (int j = 0; j < dim_list.size(); j++) {
						double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-r) * new Random().nextDouble());
						templist.add(temp);
					}
				}
				mutiple.add(templist);
			}
			// System.out.println("前面的坐标点:"+mutiple);
			templist = new ArrayList<>();
			for (int j = 0; j < dim_list.size(); j++) {
				double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-sqrt(r * fail_totalsize, dim_list.size())) * new Random().nextDouble());
				templist.add(temp);
			}while(fail2(templist)){
				templist=new ArrayList<>();
				for (int j = 0; j < dim_list.size(); j++) {
					double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-sqrt(r * fail_totalsize, dim_list.size())) * new Random().nextDouble());
					templist.add(temp);
				}
			}
			mutiple.add(templist);
			mut_area_r.add(sqrt(r * fail_totalsize, dim_list.size()));
			// System.out.println("坐标点"+mutiple);
			// System.out.println("半径"+mut_area_r);
		} 
		
		
		
		else {
			// System.out.println("此时的为多个面积相同的");
			mutiple = new ArrayList<>(); // 存放多个坐标点
			ArrayList<Double> templist = new ArrayList<>();
			for (int j = 0; j < dim_list.size(); j++) {
				double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-fail_length) * new Random().nextDouble());		
				templist.add(temp);
			}
			mutiple.add(templist);
	//		System.out.println("第一个点"+mutiple);
			for (int i = 0; i < fail_number-1; i++) {
				templist.clear();
				for (int j = 0; j < dim_list.size(); j++) {
					double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-fail_length) * new Random().nextDouble());	
					templist.add(temp);
				}
	//			System.out.println("for循环中"+templist);
				while (fail1(templist)) {
					templist=new ArrayList<>();
					for (int j = 0; j < dim_list.size(); j++) {
						double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-fail_length) * new Random().nextDouble());	
						templist.add(temp);
					}
				}
				mutiple.add(templist);
			}
			// System.out.println("失效的坐标点" + mutiple);
		}
		
		
		
		

		int count = 1;
		Testcase testcase = new Testcase();
//		for (int i = 0; i < dim_list.size(); i++) {
//			testcase.list.add(dim_list.get(i).getMin() + ((dim_list.get(i).getRange()) * new Random().nextDouble()));
//		}
		for (int i = 0; i < dim_list.size(); i++) {
			testcase.list.add(dim_list.get(i).getMin() + ((dim_list.get(i).getRange()) * new Random().nextDouble()));
//			testcase.list.add(0.5);
		}
		// System.out.println("生成的测试用例为" + testcase.list);
		// isCorrect(testcase);
		Selected=new ArrayList<>();
		
		while (isCorrect(testcase)) {
//			System.out.println(testcase.list);
			Selected.add(testcase);

			count++;
			candidate=new ArrayList<Testcase>();
//			Double best_distance = -1.0;
//			Testcase bestdata=null;
//			for (int i = 0; i < 10; i++) {
//				testcase.list.clear();;
//				for (int j = 0; j < dim_list.size(); j++) {
//					testcase.list
//							.add(dim_list.get(j).getMin() + ((dim_list.get(j).getRange()) * new Random().nextDouble()));
//				}
//				candidate.add(testcase);
//				Double min_candidate_distance=12000.0;
//				for(int j=0;j<Selected.size();j++){
//					min_candidate_distance=Math.min(min_candidate_distance, Euclidean_Distance(Selected.get(j),testcase));
//				}
//				if(best_distance<min_candidate_distance){
//					bestdata=testcase;
//					best_distance=min_candidate_distance;
//				}
//			}
		
			for(int i=0;i<10;i++){
				Testcase temp=new Testcase();
				for (int j = 0; j < dim_list.size(); j++) {
					temp.list.add(dim_list.get(j).getMin() + ((dim_list.get(j).getRange()) * new Random().nextDouble()));
				}
				candidate.add(temp);
			}
			testcase= Best_candidate(Selected, candidate);
			
		}
		
		return count;
	}

	 boolean fail1(ArrayList<Double> templist) {
			// TODO 自动生成的方法存根
			  for(int i=0;i<mutiple.size();i++){
		//		  System.out.println(mutiple.get(i));
		//		  System.out.println(ex(mutiple.get(i), templist,fail_length));
		//		  System.out.println(1);
				   if(ex(mutiple.get(i), templist,fail_length)){
				//	   ex(mutiple.get(i), templist,fail_length)
				//	   Euclidean_Distance(mutiple.get(i), templist)<fail_length
					   return true;
				   }
					  
		        }
		        return false;
		}
		 
		public boolean ex(ArrayList<Double> list1,ArrayList<Double> list2,Double r){
	//		boolean flag=false;
			for(int i=0;i<list1.size();i++){
				if(Math.abs(list1.get(i)-list2.get(i))>r){
					return false;
				}
			}
			return true;
		}
	 
	 
	 boolean fail2(ArrayList<Double> templist) {
		// TODO 自动生成的方法存根
		  for(int i=0;i<mutiple.size();i++){
	//		  System.out.println(mutiple.get(i));
	//		  System.out.println(templist);
			   if(ex(mutiple.get(i), templist,mut_area_r.get(i))){
				   return true;
			   }
				  
	        }
	        return false;
	}


	private double Euclidean_Distance(ArrayList<Double> arrayList, ArrayList<Double> templist) {
		// TODO 自动生成的方法存根
		double sum=0.0;
		for(int i=0;i<arrayList.size();i++){
			sum=sum+(Math.pow(arrayList.get(i)-templist.get(i),2));
		}
		return sum;
	}

	private double Euclidean_Distance(Testcase testcase, Testcase testcase2) {
		// TODO 自动生成的方法存根
		double sum=0.0;
//		System.out.println(testcase.list);
//		System.out.println("2"+testcase2.list);
		for(int i=0;i<testcase.list.size();i++){
			sum=sum+(Math.pow(testcase.list.get(i)-testcase2.list.get(i),2));
		}
		return sum;
	}

	private boolean isCorrect(Testcase testcase) {
		// TODO 自动生成的方法存根

		// 测试用例落在了失效域，则返回false
		boolean jutice = false;
		if (fail_number == 1) {
			for (int i = 0; i < points.size(); i++) {
				if (testcase.list.get(i) < points.get(i) || testcase.list.get(i) > points.get(i) + fail_length) {
					jutice = true;
				}
			}
		} 
		else if (prodomain) {
			jutice = true;
			for (int i = 0; i < mutiple.size(); i++) {
				boolean flag = false;
				if (i != mutiple.size() - 1) {
					double rr = mut_area_r.get(i);
					ArrayList<Double> temp = mutiple.get(i); // 坐标点
					// System.out.println("此时的失效区域的面积为:"+area);
					for (int j = 0; j < temp.size(); j++) {
						if ((testcase.list.get(j) < temp.get(j) || testcase.list.get(j) > temp.get(j) + rr)) {
							// 测试用例落在了失效域，则返回false
							// System.out.println("命中"+temp.getX()+","+temp.getY()+"此时的生成的测试点"+x+","+y+"
							// 半径"+rr);
							flag = true;
						}
					}

				} else {
					double rr = mut_area_r.get(i);
					ArrayList<Double> temp = mutiple.get(i); // 坐标点
					// System.out.println("此时的失效区域的面积为:"+area);
					for (int j = 0; j < temp.size(); j++) {
						if ((testcase.list.get(j) < temp.get(j) || testcase.list.get(j) > temp.get(j) + rr)) {
							// 测试用例落在了失效域，则返回false
							// System.out.println("命中"+temp.getX()+","+temp.getY()+"此时的生成的测试点"+x+","+y+"
							// 半径"+rr);
							flag = true;
						}
					}
				}
				if (!flag) {
					jutice = false;
					break;
				}
			}
		} else {
			jutice = true;
			for (int i = 0; i < mutiple.size(); i++) {
				ArrayList<Double> temp = mutiple.get(i);
				// System.out.println("(x,y) "+temp.x+","+temp.y);
				boolean flag = false;
				for (int j = 0; j < temp.size(); j++) {
					if ((testcase.list.get(j) < temp.get(j) || testcase.list.get(j) > temp.get(j) + fail_length)) {
						// 测试用例落在了失效域，则返回false
						// System.out.println("命中"+temp.getX()+","+temp.getY()+"
						// 此时的生成的测试点"+x+","+y+" 半径"+rr);
						flag = true;
					}
				}
				if (!flag) {
					jutice = false;
					break;
				}

			}
		}
	//	System.out.println("是否在失效" + !jutice);
		return jutice;
	}

	static double sqrt(double d, double i) {
		i = 1 / i;

		return Math.pow(d, i);
	}

	public static void main(String args[]) {
		int times = 3000;
		long sums = 0;// 初始化使用的测试用例数
		int temp = 0;
		double failrate=0.002;
		ArrayList<Dimension> list = new ArrayList<>();
//		System.out.println(Math.pow(failrate,1.0/5));
		for (int i = 0; i <2; i++) {
			
			list.add(new Dimension(0, 1));
		}


		// public FSCS_ART_Single_Sequare(ArrayList<Dimension> dim_list,
		// double failrate, int fail_number, boolean prodomain,double r)
		ArrayList<Integer> data =new ArrayList<>();
		ArrayList<Long> time =new ArrayList<>();
		long startTime = System.currentTimeMillis();
//		for (int i = 1; i <= times; i++) {
//			long startTime1 = System.currentTimeMillis();
//			temp=new fscs(list, failrate, 25, false, 0.3).run();
//			long endTime1 = System.currentTimeMillis();
//			System.out.println("第" + i + "次试验F-measure:" + temp);
//			time.add(endTime1-startTime1);
//			data.add(temp);
//			sums += temp;
//		}
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
		CompletionService<String> cService = new ExecutorCompletionService<String>(fixedThreadPool);
		for (int i = 1; i <= times; i++) {
			cService.submit(new ThreadWithCallback2(list, failrate, 1,false, 0.3));
		}
//		long startTime = System.currentTimeMillis();
		try {
			for (int i = 1; i <= times; i++) {
				long startTime1 = System.currentTimeMillis();
				Future future = cService.take();
				temp = (int) future.get();
//				long endTime1 = System.currentTimeMillis();
			
				data.add(temp);
				System.out.println("第" + i + "次试验F_Measure：" + temp);
				sums += temp;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Fm: " + sums / (double) times+"  且最后的Fart/Frt: "+sums / (double) times*failrate);// 平均每次使用的测试用例数
		System.out.println("Time: " + (endTime - startTime) / (double) times);// 测试所使用的时间
		ArrayList<Double> result =new ArrayList<>();
		result.add(sums / (double) times*failrate);
		result.add((endTime - startTime) / (double) times);
		java.io.File file = new java.io.File("D:/f_k_data/"+"v3point"+list.size()+"D"+failrate+"fm.txt");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter stream = new FileWriter(file);
			// stream.write("sdfdsfdsfds");
			for (int i=0;i<data.size();i++){
				stream.write(data.get(i) + "\r\n");
			}
			stream.close();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		java.io.File file2 = new java.io.File("D:/f_k_data/"+"v3point"+list.size()+"D"+failrate+".txt");
		try {
			if (!file2.exists()) {
				file2.createNewFile();
			}
			FileWriter stream = new FileWriter(file2);
			// stream.write("sdfdsfdsfds");
			for (int i=0;i<result.size();i++){
				stream.write(result.get(i) + "\r\n");
			}
			stream.close();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		java.io.File file3 = new java.io.File("D:/f_k_data/"+"stripfscs"+list.size()+"D"+failrate+"time.txt");
//		try {
//			if (!file3.exists()) {
//				file3.createNewFile();
//			}
//			FileWriter stream = new FileWriter(file3);
//			// stream.write("sdfdsfdsfds");
//			for (int i=0;i<time.size();i++){
//				stream.write(time.get(i) + "\r\n");
//			}
//			stream.close();
//
//		} catch (IOException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}
	
	
	public Testcase Best_candidate(ArrayList<Testcase> e, ArrayList<Testcase> c) {
        double mindist, maxmin = 0;
        int cixu = -1;
        for (int i = 0; i < c.size(); i++) {
            mindist = 12000;
            for (int j = 0; j < e.size(); j++) {
                //计算距离，Math.sqrt()开平方，Math.pow(a,b)求a的b次方
//                double dist = Math.sqrt((Math.pow(c.get(i).getX()
//                        - e.get(j).getX(), 2))
//                        + Math.pow((c.get(i).getY() - e.get(j).getY()), 2));
            	double dist=Euclidean_Distance(c.get(i), e.get(j));
   //         	System.out.println(dist);
                if (dist < mindist) {
                    mindist = dist;
                }
            }
            if (maxmin < mindist) {
                maxmin = mindist;
                cixu = i;
            }
        }
        return c.get(cixu);
    }
}

class ThreadWithCallback2 implements Callable {
	private ArrayList<Dimension> list;
	private double fail_rate;
	int failure_number;
	boolean pro;
	double r;

	ThreadWithCallback2(ArrayList<Dimension> list, double fail_rate, int failure_number,boolean pro,double r) {
		this.list = list;
		this.fail_rate = fail_rate;
		this.failure_number=failure_number;
		this.pro=pro;
		this.r=r;
	}

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		int temp = new fscs(list, fail_rate, 1,false,0.3).run();
		return temp;
	}
}
