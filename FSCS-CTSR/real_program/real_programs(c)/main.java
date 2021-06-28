package real_programs;

import java.util.Random;

public class main {
	static{
		System.loadLibrary("Win32Project2");
	}
	public native boolean getResult(String name,double a);
	public native boolean getResult(String name,double a,double b);
	public native boolean getResult(String name,double a,double b,double c);
	public native boolean getResult(String name,double a,double b,double c,double d);

}
