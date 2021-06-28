package Strip;
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

import javax.management.timer.TimerMBean;


public class V2_FSCS_ART_Strip {
	ArrayList<Dimension> dim_list = new ArrayList<>();// 负责的是维度的范围值
	ArrayList<Double> mut_area_r; // 用于存储的是多个面积不同时的每个失效的面积的r
	ArrayList<Double> points = new ArrayList<>(); // 针对的是生成的失效区域的点坐标
	ArrayList<ArrayList<Double>> mutiple; // 存放的是 多个失效域的坐标点
	ArrayList<Testcase> candidate = new ArrayList<>();
	ArrayList<Testcase> Selected;;
	double totalsize; // 整个输入域的面积
	double fail_rate; // 失效率
	double fail_length; // 失效域的边长
	int fail_number;// 失效域的个数
	boolean prodomain;// 多个失效区域是否有主区域
	double fail_totalsize;
	ArrayList<Double> random_number = new ArrayList<>();// 存放随机生成数；
	Double sum = 0.0; // 用于多个面积不同的
	Double r = 0.0;// 用于多个面积不同的面积的R参数
	// TwoLinesPos cal=new TwoLinesPos();
	FaultZone_Strip fzb;
	double p;

	public V2_FSCS_ART_Strip(int[][] bd2, float failerate, double p) {
		/*
		 * 求输入域面积，这是共存在步骤
		 */

		for (int i = 0; i < bd2.length; i++) {
			dim_list.add(new Dimension(bd2[i][0], bd2[i][1]));
		}
		this.fail_rate = failerate;

		/*
		 * 针对的单个的正方形失效区域的面积的边长
		 */
		fzb = new FaultZone_Strip(bd2, failerate, 0.9);

		this.p = p;
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
		candidate=new ArrayList<Testcase>();
		for(int i=0;i<5;i++){
			Testcase temp=new Testcase();
			for (int j = 0; j < dim_list.size(); j++) {
				temp.list.add(dim_list.get(j).getMin() + ((dim_list.get(j).getRange()) * new Random().nextDouble()));
			}
			candidate.add(temp);
		}
		for(int i=0;i<5;i++){
			candidate.get(i).minDistance=Euclidean_Distance(candidate.get(i), testcase);
		}
		while (!isCorrect(testcase)) {
//			System.out.println(testcase.list);
			Selected.add(testcase);

			count++;
			for(int i=5;i<10;i++){
				Testcase temp=new Testcase();
				for (int j = 0; j < dim_list.size(); j++) {
					temp.list.add(dim_list.get(j).getMin() + ((dim_list.get(j).getRange()) * new Random().nextDouble()));
				}
				candidate.add(temp);
			}
			Best_candidate(Selected, candidate);		
			sort(candidate);	
			testcase=candidate.get(0);
			candidate.remove(0);	
			update(candidate, testcase);		
			sort(candidate);	
			for (int i = 8; i >= 5; i--) {
				candidate.remove(i);		
			}		
		}
		
		return count;
	}
	private double Euclidean_Distance(Testcase testcase, Testcase testcase2) {
		// TODO 自动生成的方法存根
		double sum = 0.0;

		for (int i = 0; i < testcase.list.size(); i++) {
			sum = sum + (Math.pow(Math.abs(testcase.list.get(i) - testcase2.list.get(i)), p));
		}
		return Math.pow(sum, 1.0 / p);
	}

	private boolean isCorrect(Testcase testcase) {
		// TODO 自动生成的方法存根
		Point mapp = new Point(dim_list.size());
		float[] a = new float[dim_list.size()];
		for (int i = 0; i < testcase.list.size(); i++) {
			a[i] = testcase.list.get(i).floatValue();
		}
		mapp.coordPoint = a;

		return fzb.findTarget(mapp);
	}

	static double sqrt(double d, double i) {
		i = 1 / i;
		return Math.pow(d, i);
	}

	public static void main(String args[]) {
		int times = StripParameters.run_times;
		long sums = 0;// 初始化使用的测试用例数
		int temp = 0;// 初始化测试用例落在失效域的使用的测试用例的个数
		ArrayList<Integer> data =new ArrayList<>();
		ArrayList<Long> time =new ArrayList<>();
		float failrate = StripParameters.failure_rate;
		double p = StripParameters.lp;
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(StripParameters.thread_pool_num);
		CompletionService<String> cService = new ExecutorCompletionService<String>(fixedThreadPool);
		for (int i = 1; i <= times; i++) {
			cService.submit(new ThreadWithCallback2(StripParameters.bd, failrate, p));
		}
		long startTime = System.currentTimeMillis();
		try {
			for (int i = 1; i <= times; i++) {
				long startTime1 = System.currentTimeMillis();
				Future future = cService.take();
				temp = (int) future.get();
				long endTime1 = System.currentTimeMillis();
				time.add(endTime1-startTime1);
				data.add(temp);
				System.out.println("第" + i + "次试验F_Measure：" + temp);
				sums += temp;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();

//		System.out.println("当前参数：dimension = " + StripParameters.dimension +  "   failure-rate = " + failrate); // 输出当前参数信息
//		System.out.println("Fm: " + sums / (double) times + "  且最后的Fart/Frt: " + sums / (double) times * failrate);// 平均每次使用的测试用例数
	//	System.out.println("Time: " + (endTime - startTime) / (double) times);// 测试所使用的时间
		System.out.println("Fm: " + sums / (double) times+"  且最后的Fart/Frt: "+sums / (double) times*failrate);// 平均每次使用的测试用例数
		System.out.println("Time: " + (endTime - startTime) / (double) times);// 测试所使用的时间
		
		
		ArrayList<Double> result =new ArrayList<>();
		result.add(sums / (double) times*failrate);
		result.add((endTime - startTime) / (double) times);
		java.io.File file = new java.io.File("D:/f_k_data/"+"V2sfscs"+StripParameters.dimension+"D"+failrate+"fm.txt");
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
		
		
		
		java.io.File file2 = new java.io.File("D:/f_k_data/"+"V2sfscs"+StripParameters.dimension+"D"+failrate+".txt");
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
		java.io.File file3 = new java.io.File("D:/f_k_data/"+"V2sfscs"+StripParameters.dimension+"D"+failrate+"time.txt");
		try {
			if (!file3.exists()) {
				file3.createNewFile();
			}
			FileWriter stream = new FileWriter(file3);
			// stream.write("sdfdsfdsfds");
			for (int i=0;i<time.size();i++){
				stream.write(time.get(i) + "\r\n");
			}
			stream.close();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void Best_candidate(ArrayList<Testcase> e, ArrayList<Testcase> c) {
		Testcase p = null;
        double mindist, maxmin = 0;
        int cixu = -1;
        for (int i = 5; i < 10; i++) {
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
            }
            c.get(i).minDistance=mindist;
        }
       
    }
	
	public void sort(ArrayList<Testcase> c){
		Testcase temp=new Testcase();
		for (int i=0;i<c.size()-1;i++){
			for(int j=0;j<c.size()-i-1;j++){
				if (c.get(j).minDistance<c.get(j+1).minDistance) {
					temp=c.get(j);
					c.set(j, c.get(j+1));
					c.set(j+1, temp);
				}
			}
		}
	}
	public void update(ArrayList<Testcase> final_c,Testcase t) {
		double dist;
		for(int i=0;i<final_c.size();i++){
			dist=Euclidean_Distance(final_c.get(i), t);
			if (dist<final_c.get(i).minDistance)
				final_c.get(i).minDistance=dist;
		}
	}
}

class ThreadWithCallback2 implements Callable {
	private int[][] list;
	private float fail_rate;
	double p;

	ThreadWithCallback2(int[][] list, float fail_rate, double p) {
		this.list = list;
		this.fail_rate = fail_rate;
		this.p = p;
	}

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		int temp = new V2_FSCS_ART_Strip(list, fail_rate, p).run();
		return temp;
	}
}