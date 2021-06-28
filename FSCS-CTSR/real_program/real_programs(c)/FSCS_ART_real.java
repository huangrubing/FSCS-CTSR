package real_programs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.management.timer.TimerMBean;

import Common.Dimension;
import Common.Testcase;
import real_programs.main;



public class FSCS_ART_real {
	ArrayList<Dimension> dim_list;// 负责的是维度的范围值
	ArrayList<Double> mut_area_r; // 用于存储的是多个面积不同时的每个失效的面积的r
	ArrayList<Double> points = new ArrayList<>(); // 针对的是生成的失效区域的点坐标
	ArrayList<ArrayList<Double>> mutiple; // 存放的是 多个失效域的坐标点
	ArrayList<Testcase> candidate=new ArrayList<>();
	ArrayList<Testcase> Selected;;
	double totalsize; // 整个输入域的面积
	double fail_rate; // 失效率
	double fail_length; // 失效域的边长
	int fail_number;// 失效域的个数
	boolean prodomain;// 多个失效区域是否有主区域
	double fail_totalsize;
	String name;
	ArrayList<Double> random_number = new ArrayList<>();// 存放随机生成数；
	Double sum = 0.0; // 用于多个面积不同的
	Double r = 0.0;// 用于多个面积不同的面积的R参数
	main m=new main();
	public FSCS_ART_real(ArrayList<Dimension> dim_list,String name) {
		/*
		 * 求输入域面积，这是共存在步骤
		 */
	
		
		this.dim_list = dim_list;
		this.name=name;
		/*
		 * 针对的单个的正方形失效区域的面积的边长
		*/
	}

	public int run() {

		int count = 1;
		Testcase testcase = new Testcase();
		for (int i = 0; i < dim_list.size(); i++) {
			testcase.list.add(dim_list.get(i).getMin() + ((dim_list.get(i).getRange()) * new Random().nextDouble()));
		}
		// System.out.println("生成的测试用例为" + testcase.list);
		// isCorrect(testcase);
		Selected=new ArrayList<>();
		
		while (!isCorrect(testcase)) {
//			System.out.println(testcase.list);
			Selected.add(testcase);
			count++;
			candidate=new ArrayList<>();
		
			for(int i=0;i<10;i++){
				Testcase temp=new Testcase();
				for (int j = 0; j < dim_list.size(); j++) {
					temp.list
							.add(dim_list.get(j).getMin() + ((dim_list.get(j).getRange()) * new Random().nextDouble()));
				}
				candidate.add(temp);
			}
			testcase= Best_candidate(Selected, candidate);

		}
		
		
		return count;
	}

	private double Euclidean_Distance(ArrayList<Double> arrayList, ArrayList<Double> templist) {
		// TODO 自动生成的方法存根
		double sum=0.0;
		for(int i=0;i<arrayList.size();i++){
			sum=sum+(Math.pow(arrayList.get(i)-templist.get(i),2));
		}
		return Math.sqrt(sum);
	}

	private double Euclidean_Distance(Testcase testcase, Testcase testcase2) {
		// TODO 自动生成的方法存根
		double sum=0.0;
//		System.out.println(testcase.list);
//		System.out.println("2"+testcase2.list);
		for(int i=0;i<testcase.list.size();i++){
			sum=sum+(Math.pow(testcase.list.get(i)-testcase2.list.get(i),2));
		}
		return Math.sqrt(sum);
	}

	private boolean isCorrect(Testcase testcase) {
		// TODO 自动生成的方法存根

		// 测试用例落在了失效域，则返回false
//		 return m.getResult(name, testcase.list.get(0),testcase.list.get(1),testcase.list.get(2),testcase.list.get(3));
		 return m.getResult(name, testcase.list.get(0), testcase.list.get(1));
//		 return m.getResult(name, testcase.list.get(0));
//		 return m.getResult(name, testcase.list.get(0),testcase.list.get(1),testcase.list.get(2));
		
//		return cal.producesError(
//				testcase.list.get(0).intValue(), testcase.list.get(1).intValue(), 
//				testcase.list.get(2).intValue(), testcase.list.get(3).intValue(), 
//				testcase.list.get(4).intValue(), testcase.list.get(5).intValue(),
//				testcase.list.get(6).intValue(), testcase.list.get(7).intValue());
//		return false;
		
	}

	static double sqrt(double d, double i) {
		i = 1 / i;

		return Math.pow(d, i);
	}

	public static void main(String args[]) {
		int times = 3000;
		long sums = 0;// 初始化使用的测试用例数
		int temp = 0;// 初始化测试用例落在失效域的使用的测试用例的个数
		double failrate=0.001298;
		String name="bessj";
		
		
		Dimension x = new Dimension(2, 300);
		Dimension y = new Dimension(-1000, 15000);
//		Dimension z = new Dimension(-100, 60);
//		Dimension u = new Dimension(0.001, 1000);
//		Dimension v = new Dimension(-15, 15);
//        Dimension w = new Dimension(-15, 15);
//        Dimension a = new Dimension(-15, 15);
//        Dimension b = new Dimension(-15, 15);
//        Dimension c = new Dimension(1, 1000);
//        Dimension d = new Dimension(1, 1000);

		
		ArrayList<Integer> F_meaure=new ArrayList<>();
		ArrayList<Double> result=new ArrayList<>();
		ArrayList<Long> Time=new ArrayList<>();
		ArrayList<Dimension> list = new ArrayList<>();
		list.add(x);
		list.add(y);
//		list.add(z);
//		list.add(u);
//		list.add(v);
//		list.add(w);
//		list.add(a);
//		list.add(b);
//		list.add(c);
//		list.add(d);
		
		
		// public FSCS_ART_Single_Sequare(ArrayList<Dimension> dim_list,
		// double failrate, int fail_number, boolean prodomain,double r)
		long  total=0;
		for (int i = 1; i <= times; i++) {
			long startTime = System.currentTimeMillis();
			temp=new FSCS_ART_real(list,name).run();
			long endTime = System.currentTimeMillis();
			System.out.println("第" + i + "次试验F-measure:" + temp);
			F_meaure.add(temp);
			Time.add(endTime-startTime);
			sums += temp;
			total+=endTime-startTime;
		}
		result.add(sums / (double) times*failrate);
		result.add((total) / (double) times);
		System.out.println("Fm: " + sums / (double) times+"  且最后的Fart/Frt: "+sums / (double) times*failrate);// 平均每次使用的测试用例数
		System.out.println("Time: " + (total) / (double) times);// 测试所使用的时间
		
		java.io.File file2 = new java.io.File("D:/data/"+name+"FSCS.txt");
		try {
			if (!file2.exists()) {
				file2.createNewFile();
			}
			FileWriter stream = new FileWriter(file2);
			// stream.write("sdfdsfdsfds");
			for (int j = 0; j < result.size(); j++) {
				stream.write(result.get(j) + "\r\n");
			}
			stream.close();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		file2 = new java.io.File("D:/data/"+name+"FSCS_F_measure.txt");
		try {
			if (!file2.exists()) {
				file2.createNewFile();
			}
			FileWriter stream = new FileWriter(file2);
			// stream.write("sdfdsfdsfds");
			for (int j = 0; j < F_meaure.size(); j++) {
				stream.write(F_meaure.get(j) + "\r\n");
			}
			stream.close();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		file2 = new java.io.File("D:/data/"+name+"FSCS_Time.txt");
		try {
			if (!file2.exists()) {
				file2.createNewFile();
			}
			FileWriter stream = new FileWriter(file2);
			// stream.write("sdfdsfdsfds");
			for (int j = 0; j < Time.size(); j++) {
				stream.write(Time.get(j) + "\r\n");
			}
			stream.close();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public Testcase Best_candidate(ArrayList<Testcase> e, ArrayList<Testcase> c) {
		Testcase p = null;
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
