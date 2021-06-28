package FSCS_ART;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;



import Common.Dimension;
import Common.Testcase;

public class fscs_ctsr {
	ArrayList<Dimension> dim_list;// �������ά�ȵķ�Χֵ
	ArrayList<Double> mut_area_r; // ���ڴ洢���Ƕ�������ͬʱ��ÿ��ʧЧ�������r
	ArrayList<Double> points = new ArrayList<>(); // ��Ե������ɵ�ʧЧ����ĵ�����
	ArrayList<ArrayList<Double>> mutiple; // ��ŵ��� ���ʧЧ��������
	ArrayList<Testcase> candidate=new ArrayList<>();
	ArrayList<Testcase> Selected;
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

	public fscs_ctsr(ArrayList<Dimension> dim_list, double failrate, int fail_number, boolean prodomain,
			double r,int f_k) {
		/*                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		 * ����������������ǹ����ڲ���
		 */
		totalsize = dim_list.get(0).getRange();
		for (int i = 1; i < dim_list.size(); i++) {
			totalsize = totalsize * dim_list.get(i).getRange();
		}
		this.dim_list = dim_list;
		this.fail_rate = failrate;
		this.f_k=f_k;
		this.fail_totalsize = failrate * totalsize;
		this.fail_number = fail_number;
		this.prodomain = prodomain;
		this.r = r;
		/*
		 * ��Եĵ�����������ʧЧ���������ı߳�
		 */
		if (fail_number == 1) {
			fail_length = sqrt(fail_totalsize, dim_list.size());}
		 else if (prodomain) { // ��������ͬ��������
			mut_area_r = new ArrayList<>();
			for (int i = 0; i < fail_number - 1; i++) {
				random_number.add(Math.random());
				sum = sum + random_number.get(i);
			}
		} else { // ��������ͬ��������
			fail_length = sqrt(fail_totalsize / fail_number, dim_list.size());
		}
//		System.out.println("�����Ϊ" + totalsize + " fail_length:" + fail_length);
	}

	public int run() {

		if (fail_number == 1) {
			// �������ʧЧ���򣬿��ǵ��߽���������ֵֻ����1-fail_rate
			for (int i = 0; i < dim_list.size(); i++) {
				double temp = dim_list.get(i).getMin() + ((dim_list.get(i).getRange()-fail_length) * new Random().nextDouble());
				points.add(temp);
			}

//			System.out.println("ʧЧ�������" + points);

		
		} 
		else if (prodomain) {
			mutiple = new ArrayList<>(); // ��Ŷ�������
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
				// System.out.println("��ʱ�����Ϊ��"+area+"��ʱ�İ뾶"+r);
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
			// System.out.println("ǰ��������:"+mutiple);
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
			// System.out.println("�����"+mutiple);
			// System.out.println("�뾶"+mut_area_r);
		} 
		
		
		
		else {
			// System.out.println("��ʱ��Ϊ��������ͬ��");
			mutiple = new ArrayList<>(); // ��Ŷ�������
			ArrayList<Double> templist = new ArrayList<>();
			for (int j = 0; j < dim_list.size(); j++) {
				double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-fail_length) * new Random().nextDouble());		
				templist.add(temp);
			}
			mutiple.add(templist);
	//		System.out.println("��һ����"+mutiple);
			for (int i = 0; i < fail_number-1; i++) {
				templist.clear();
				for (int j = 0; j < dim_list.size(); j++) {
					double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-fail_length) * new Random().nextDouble());	
					templist.add(temp);
				}
	//			System.out.println("forѭ����"+templist);
				while (fail1(templist)) {
					templist=new ArrayList<>();
					for (int j = 0; j < dim_list.size(); j++) {
						double temp = dim_list.get(j).getMin() + ((dim_list.get(j).getRange()-fail_length) * new Random().nextDouble());	
						templist.add(temp);
					}
				}
				mutiple.add(templist);
			}
			// System.out.println("ʧЧ�������" + mutiple);
		}
		
		
		

		int count = 1;
		Testcase testcase = new Testcase();
		for (int i = 0; i < dim_list.size(); i++) {
			testcase.list.add(dim_list.get(i).getMin() + ((dim_list.get(i).getRange()) * new Random().nextDouble()));
		}
		// System.out.println("���ɵĲ�������Ϊ" + testcase.list);
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
		while (isCorrect(testcase)) {
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

	 boolean fail1(ArrayList<Double> templist) {
			// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������
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

	private boolean isCorrect(Testcase testcase) {
		// TODO �Զ����ɵķ������

		// ��������������ʧЧ���򷵻�false
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
					ArrayList<Double> temp = mutiple.get(i); // �����
					// System.out.println("��ʱ��ʧЧ��������Ϊ:"+area);
					for (int j = 0; j < temp.size(); j++) {
						if ((testcase.list.get(j) < temp.get(j) || testcase.list.get(j) > temp.get(j) + rr)) {
							// ��������������ʧЧ���򷵻�false
							// System.out.println("����"+temp.getX()+","+temp.getY()+"��ʱ�����ɵĲ��Ե�"+x+","+y+"
							// �뾶"+rr);
							flag = true;
						}
					}

				} else {
					double rr = mut_area_r.get(i);
					ArrayList<Double> temp = mutiple.get(i); // �����
					// System.out.println("��ʱ��ʧЧ��������Ϊ:"+area);
					for (int j = 0; j < temp.size(); j++) {
						if ((testcase.list.get(j) < temp.get(j) || testcase.list.get(j) > temp.get(j) + rr)) {
							// ��������������ʧЧ���򷵻�false
							// System.out.println("����"+temp.getX()+","+temp.getY()+"��ʱ�����ɵĲ��Ե�"+x+","+y+"
							// �뾶"+rr);
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
						// ��������������ʧЧ���򷵻�false
						// System.out.println("����"+temp.getX()+","+temp.getY()+"
						// ��ʱ�����ɵĲ��Ե�"+x+","+y+" �뾶"+rr);
						flag = true;
					}
				}
				if (!flag) {
					jutice = false;
					break;
				}

			}
		}
	//	System.out.println("�Ƿ���ʧЧ" + !jutice);
		return jutice;
	}

	static double sqrt(double d, double i) {
		i = 1 / i;

		return Math.pow(d, i);
	}

	public static void main(String args[]) {
		int times = 3000;
		long sums = 0;// ��ʼ��ʹ�õĲ���������
		int temp = 0;
		double failrate=0.002;
		int f_k=5;
		ArrayList<Dimension> list = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Dimension x = new Dimension(0, 1);
			list.add(x);
		}
		// public FSCS_ART_Single_Sequare(ArrayList<Dimension> dim_list,
		// double failrate, int fail_number, boolean prodomain,double r)
		ArrayList<Integer> data =new ArrayList<>();
		ArrayList<Long> time =new ArrayList<>();
		long startTime = System.currentTimeMillis();
		for (int i = 1; i <= times; i++) {
			long startTime1 = System.currentTimeMillis();
			temp=new fscs_ctsr(list, failrate, 1, false, 0.3,f_k).run();// 1Ϊblockģ�ͣ�25Ϊpointģ��
			long endTime1 = System.currentTimeMillis();
			System.out.println("��" + i + "������F-measure:" + temp);
			time.add(endTime1-startTime1);
			data.add(temp);
			sums += temp;
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Fm: " + sums / (double) times+"  ������Fart/Frt: "+sums / (double) times*failrate);// ƽ��ÿ��ʹ�õĲ���������
		System.out.println("Time: " + (endTime - startTime) / (double) times);// ������ʹ�õ�ʱ��
		ArrayList<Double> result =new ArrayList<>();
		result.add(sums / (double) times*failrate);
		result.add((endTime - startTime) / (double) times);
		java.io.File file = new java.io.File("D:/f_k_data/"+"pointv4"+list.size()+"D"+failrate+"fm.txt");
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
		
		
		
		java.io.File file2 = new java.io.File("D:/f_k_data/"+"pointkv4"+list.size()+"D"+failrate+".txt");
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
//		java.io.File file3 = new java.io.File("D:/f_k_data/"+"stripv2"+list.size()+"D"+failrate+"time.txt");
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
