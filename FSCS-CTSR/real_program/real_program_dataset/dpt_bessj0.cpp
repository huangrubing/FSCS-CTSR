// dpt_bessj0.cpp: implementation of the dpt_bessj0 class.
//
//////////////////////////////////////////////////////////////////////

#include "dpt_bessj0.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

dpt_bessj0::dpt_bessj0()
{

}

dpt_bessj0::~dpt_bessj0()
{

}

double dpt_bessj0::bessj0(double x)
{
	float ax,z;
	double xx,y,ans,ans1,ans2;

	if ((ax=fabs(x)) < 8.0) {
		y=x*x;
		ans1=57568490574.0+y*(-13362590354.0+y*(651619640.7
			+y*(-11214424.18+y*(77392.33017+y*(-184.9052456)))));
		ans2=57568490411.0+y*(1029532985.0+y*(9494680.718
			+y*(59272.64853+y*(267.8532712+y*1.0))));
		ans=ans1/ans2;
	} else {
		z=8.0/ax;
		y=z*z;
		xx=ax-0.785398164;
		ans1=1.0+y*(-0.1098628627e-2+y*(0.2734510407e-4
			+y*(-0.2073370639e-5+y*0.2093887211e-6)));
		ans2 = -0.1562499995e-1+y*(0.1430488765e-3
			+y*(-0.6911147651e-5+y*(0.7621095161e-6
			-y*0.934935152e-7)));
		ans=sqrt(0.636619772/ax)*(cos(xx)*ans1-z*sin(xx)*ans2);
	}
	return ans;
}

double dpt_bessj0::bessj0m(double x)
{
	float ax,z;
	double xx,y,ans,ans1,ans2;

	/* ERROR */
	/* if ((ax=fabs(x)) < 8.0) { */
	if ((ax=fabs(x)) <= 10.0) {
		y=x*x;
		ans1=57568490574.0+y*(-13362590354.0+y*(651619640.7
			+y*(-11214424.18+y*(77392.33017+y*(-184.9052456)))));
		ans2=57568490411.0+y*(1029532985.0+y*(9494680.718
			/* ERROR */
			/* +y*(59272.64853+y*(267.8532712+y*1.0)))); */
			+y*(59272.64853+y*(267.8532712+y+1.0))));
		ans=ans1/ans2;
	} else {
		z=8.0/ax;
		y=z*z;
		xx=ax-0.785398164;
		ans1=1.0+y*(-0.1098628627e-2+y*(0.2734510407e-4
			+y*(-0.2073370639e-5+y*0.2093887211e-6)));
		ans2 = -0.1562499995e-1+y*(0.1430488765e-3
			/* ERROR */
			/* +y*(-0.6911147651e-5+y*(0.7621095161e-6 */
			+y*(-0.6911147651e-5+z*(0.7621095161e-6
			/* ERROR */
			/* -y*0.934935152e-7))); */
			+y*0.934935152e-7)));
		ans=sqrt(0.636619772/ax)*(cos(xx)*ans1-z*sin(xx)*ans2);
	}
	return ans;
}

double dpt_bessj0::original_fn(double x)
{
	return bessj0(x);
}

double dpt_bessj0::modified_fn(double x)
{
	return bessj0m(x);
}
