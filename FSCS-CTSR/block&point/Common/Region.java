package Common;

import java.util.ArrayList;

public class Region {
	public point getPoint() {
		return point;
	}

	public void setPoint(point point) {
		this.point = point;
	}

	public ArrayList<Double> getLength() {
		return length;
	}

	public void setLength(ArrayList<Double> length) {
		this.length = length;
	}

	public point point = new point(); // ��ŵľ�����С��
	public ArrayList<Double> length = new ArrayList<>(); // ��ŵ��Ǹ�����ĳ���
	public point testcase;

	// ArrayList<Double> zhong=new ArrayList<>();
	public Region(point point, ArrayList<Double> length) {
		this.point = point;
		this.length = length;
		// for(int i=0;i<length.size();i++){
		// zhong.add(point.point.get(i)+length.get(i)/2);
		// }
	}

	public Region() {
		// TODO �Զ����ɵĹ��캯�����
	}

	public boolean isIn_area(point temp) {
		boolean jutice = false; // �������ڣ�
		if (temp == null) {
			return true;
		} else {

			for (int i = 0; i < point.point.size(); i++) {
				if (temp.point.get(i) < this.point.point.get(i)
						|| temp.point.get(i) > this.point.point.get(i) + length.get(i)) {
					jutice = true; // ���������ڣ�
					break;
				}
			}
			return jutice;
		}
	}

	@Override
	public String toString() {
		// TODO �Զ����ɵķ������
		String a = "�����" + point.point + " " + testcase;
		return a;
	}

	public boolean equals(Object obj) {
		// TODO �Զ����ɵķ������
		if (obj instanceof Region) {
			for (int i = 0; i < point.point.size(); i++) {
				if (!point.point.get(i).equals(((Region) obj).point.point.get(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
