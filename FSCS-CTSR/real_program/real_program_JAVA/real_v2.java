package complex;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.management.timer.TimerMBean;







public class real_v2 {
	ArrayList<Dimension> dim_list;// �������ά�ȵķ�Χֵ
	ArrayList<Double> mut_area_r; // ���ڴ洢���Ƕ�������ͬʱ��ÿ��ʧЧ�������r
	ArrayList<Double> points = new ArrayList<>(); // ��Ե������ɵ�ʧЧ����ĵ�����
	ArrayList<ArrayList<Double>> mutiple; // ��ŵ��� ���ʧЧ��������
	ArrayList<Testcase> candidate=new ArrayList<>();
	ArrayList<Testcase> Selected;
	String name;
	double totalsize; // ��������������
	double fail_rate; // ʧЧ��
	double fail_length; // ʧЧ��ı߳�
	int fail_number;// ʧЧ��ĸ���
	boolean prodomain;// ���ʧЧ�����Ƿ���������
	double fail_totalsize;
	int f_k;
	ArrayList<Double> random_number = new ArrayList<>();// ��������������
	Double sum = 0.0; // ���ڶ�������ͬ��
	Double r = 0.0;// ���ڶ�������ͬ�������R����

	public real_v2(ArrayList<Dimension> dim_list,int f_k,String name) {
		/*
		 * ����������������ǹ����ڲ���
		 */
	
		
		this.dim_list = dim_list;
		this.f_k=f_k;
		this.name=name;
		/*
		 * ��Եĵ�����������ʧЧ���������ı߳�
		*/
	}

	public int run() {

		int count = 1;
		Testcase testcase = new Testcase();
		for (int i = 0; i < dim_list.size(); i++) {
			testcase.list.add(dim_list.get(i).getMin() + ((dim_list.get(i).getRange()) * new Random().nextDouble()));
//			testcase.list.add(0.5);
		}
		Selected=new ArrayList<>();
		candidate=new ArrayList<>();
		Complex cal =new Complex();
		while (!isCorrect(testcase,cal)) {
//			System.out.println(testcase.list);
			Selected.add(testcase);

			count++;
			for(int i=f_k;i<10;i++){
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
			for (int i = 8; i >= f_k; i--) {
				candidate.remove(i);		
			}		
		}
		
		return count;
	}

	private double Euclidean_Distance(ArrayList<Double> arrayList, ArrayList<Double> templist) {
		// TODO �Զ����ɵķ������
		double sum=0.0;
		for(int i=0;i<arrayList.size();i++){
			sum=sum+(Math.pow(arrayList.get(i)-templist.get(i),2));
		}
		return Math.sqrt(sum);
	}

	private double Euclidean_Distance(Testcase testcase, Testcase testcase2) {
		// TODO �Զ����ɵķ������
		double sum=0.0;
//		System.out.println(testcase.list);
//		System.out.println("2"+testcase2.list);
		for(int i=0;i<testcase.list.size();i++){
			sum=sum+(Math.pow(testcase.list.get(i)-testcase2.list.get(i),2));
		}
		return Math.sqrt(sum);
	}

	private boolean isCorrect(Testcase testcase,Complex a) {
		// TODO �Զ����ɵķ������
		return	a.producesError(testcase.list.get(0).intValue(), testcase.list.get(1).intValue(),
				testcase.list.get(2).intValue(), testcase.list.get(3).intValue(), testcase.list.get(4).intValue(),
				testcase.list.get(5).intValue());
		}

	static double sqrt(double d, double i) {
		i = 1 / i;

		return Math.pow(d, i);
	}

	public static void main(String args[]) {
		int times = 3000;
		long sums = 0;// ��ʼ��ʹ�õĲ���������
		int temp = 0;// 
		int f_k;
		int k=5;//k��2��10
		f_k=10-k;
		double failrate=0.000901;
		String name="complex";
		
		
		Dimension x = new Dimension(-20, 20);
		Dimension y = new Dimension(-20, 20);
		Dimension z = new Dimension(-20, 20);
		Dimension u = new Dimension(-20, 20);
		Dimension v = new Dimension(-20, 20);
        Dimension w = new Dimension(-20, 20);
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
		list.add(z);
		list.add(u);
		list.add(v);
		list.add(w);
//		list.add(a);
//		list.add(b);
//		list.add(c);
//		list.add(d);
		
		
		// public FSCS_ART_Single_Sequare(ArrayList<Dimension> dim_list,
		// double failrate, int fail_number, boolean prodomain,double r)
		long  total=0;
		for (int i = 1; i <= times; i++) {
			long startTime = System.currentTimeMillis();
			temp=new real_v2(list,f_k,name).run();
			long endTime = System.currentTimeMillis();
			System.out.println("��" + i + "������F-measure:" + temp);
			F_meaure.add(temp);
			Time.add(endTime-startTime);
			sums += temp;
			total+=endTime-startTime;
		}
		
		result.add(sums / (double) times*failrate);
		result.add((total) / (double) times);
		System.out.println("Fm: " + sums / (double) times+"  ������Fart/Frt: "+sums / (double) times*failrate);// ƽ��ÿ��ʹ�õĲ���������
		System.out.println("Time: " + (total) / (double) times);// ������ʹ�õ�ʱ��
		
		java.io.File file2 = new java.io.File("D:/data/"+name+"v2.txt");
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
		
		
		file2 = new java.io.File("D:/data/"+name+"v2_F_measure.txt");
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
		
		file2 = new java.io.File("D:/data/"+name+"v2_Time.txt");
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
	
	
	public void Best_candidate(ArrayList<Testcase> e, ArrayList<Testcase> c) {
		Testcase p = null;
        double mindist, maxmin = 0;
        int cixu = -1;
        for (int i = f_k; i < 10; i++) {
            mindist = 12000;
            for (int j = 0; j < e.size(); j++) {
                //������룬Math.sqrt()��ƽ����Math.pow(a,b)��a��b�η�
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
	public void Best_candidate1(ArrayList<Testcase> e, ArrayList<Testcase> c) {
		Testcase p = null;
        double mindist, maxmin = 0;
        int cixu = -1;
        for (int i = 0; i < c.size(); i++) {
            mindist = 12000;
            for (int j = 0; j < e.size(); j++) {
                //������룬Math.sqrt()��ƽ����Math.pow(a,b)��a��b�η�
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
