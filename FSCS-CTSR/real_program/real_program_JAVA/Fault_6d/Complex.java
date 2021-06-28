package Fault_6d;

public class Complex extends PUT_6D {
	
	public static int[] complex(int a, int b, int c, int d, int e, int f) {

		int result;
		char z;
		int j1 = c + b;
		int j2 = e + c;
		int j3 = a + b + d;

		if (a < e || e + a != j1 || j2 < j3) {
			a++;
			int w = a + b;

			if (w == 0) {
				result = 2;
			} else if (w == 10) {
				a = b - e;
			} else if (w == 23) {
				e = a + b + d;
			} else {
				c = a;
				a = b;
			}
			c--;

		} else {
			b++;
			if (c < b || (a > b && e < d) || a == e) {
				c++;
			}
		}
		a = b + c;
		z = 0;
		while (f < d && z < 100) {
			z++;
			e = c + e + b;
			f -= 2 * a + e + 1;
			if (e < f) {
				break;
			}
			a = d + a;
			if (f == a) {
				break;
			}
		}
		if ((a < b || b < c) && (a < c || e < d)) {
			result = 1;
			int[] res = { a, b, c, d, e, f };
			return res;
		}

		a = b + c;
		z = 0;
		while (f + e < a + b && z < 100) {
			z++;
			e = c + e + b;
			f -= 2 * a + e;
			if (e < f) {
				break;
			}
			a = d + a;
			if (f == a) {
				break;
			}
		}
		result = 0;
		int[] res = { a, b, c, d, e, f };
		return res;
	}

	public static int[] complexErr(int a, int b, int c, int d, int e, int f) {

		int result;
		char z;
		int j1 = c + b;
		int j2 = e + c;
		int j3 = a + b + d;

		if (a < e || e + a != j1 || j2 < j3) {
			a++;
			int w = a + b;

			if (w == 0) {
				result = 2;
			} else if (w == 10) {
				a = b - e;
			} else if (w == 23) {
				e = a + b + d;
			} else {
				c = a;
				a = b;
			}
			c--;

		} else {
			b++;
			if (c < b || (a > b && e < f) || a == e) { // ERROR if (c < b || (a > b && e < d) || a == e) {
				c++;
			}
		}
		a = b + c;
		z = 0;
		while (f < d && z < 100) {
			z++;
			e = c + e + b;
			f -= 2 * a + e + 1;
			if (e < f) {
				break;
			}
			a = d + a;
			if (f == a) {
				break;
			}
		}
		if ((a < b || b < c) && (a < c || e < d)) {
			result = 1;
			int[] res = { a, b, c, d, e, f };
			return res;
		}

		a = b + c;
		z = 0;
		while (f + e < a + b && z < 100) {
			z++;
			e = c + e + b;
			f -= 2 * a + e;
			if (e < f) {
				break;
			}
			a = d + a;
			if (f == a) {
				break;
			}
		}
		result = 0;
		int[] res = { a, b, c, d, e, f };
		return res;
	}

	@Override
	public boolean producesError(int x0, int y0, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		int[] origResult = complex(x0, y0, x1, y1, x2, y2);
		int[] errResult = complexErr(x0, y0, x1, y1, x2, y2);
		for (int i = 0; i < origResult.length; i++) { // 将两者结果返回的数组进行对比 有一处不相等 则发现失效
			if (origResult[i] != errResult[i]) {
				return true; // 发现程序失效
			}
		}
		return false;
	}

	@Override
	public double modified_fn(int x0, int y0, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		int[] errResult = complexErr(x0, y0, x1, y1, x2, y2);
		return 0;
	}

}
