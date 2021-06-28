// dpt_plgndr.cpp: implementation of the dpt_plgndr class.
//
//////////////////////////////////////////////////////////////////////

#include "dpt_plgndr.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

dpt_plgndr::dpt_plgndr()
{

}

dpt_plgndr::~dpt_plgndr()
{

}

double dpt_plgndr::plgndr(int l, int m, double x)
{
	double fact,pll=0.0,pmm,pmmp1,somx2;
	int i,ll;

	if (m < 0 || m > l || fabs(x) > 1.0)
		nrerror("Bad arguments in routine PLGNDR");
	pmm=1.0;
	if (m > 0) {
		somx2=sqrt((1.0-x)*(1.0+x));
		fact=1.0;
		for (i=1;i<=m;i++) {
			pmm *= -fact*somx2;
			fact += 2.0;
		}
	}
	if (l == m)
		return pmm;
	else {
		pmmp1=x*(2*m+1)*pmm;
		if (l == (m+1))
			return pmmp1;
		else {
			for (ll=(m+2);ll<=l;ll++) {
				pll=(x*(2*ll-1)*pmmp1-(ll+m-1)*pmm)/(ll-m);
				pmm=pmmp1;
				pmmp1=pll;
			}
			return pll;
		}
	}
}
	
double dpt_plgndr::plgndrm(int l, int m, double x)
{
	double fact,pll=0.0,pmm,pmmp1,somx2;
	int i,ll;

	if (m < 0 || m > l || fabs(x) > 1.0)
		nrerror("Bad arguments in routine PLGNDR");
	pmm=1.0;
	/* ERROR */
	/* if (m > 0) { */
	if (m >= 1) {
		somx2=sqrt((1.0-x)*(1.0+x));
		fact=1.0;
		for (i=1;i<=m;i++) {
			pmm *= -fact*somx2;
			fact += 2.0;
		}
	}
	if (l == m)
		return pmm;
	else {
		pmmp1=x*(2*m+1)*pmm;
		/* ERROR */
		/* if (l == (m+1)) */
		if (l <= (m*x))
			return pmmp1;
		else {
			for (ll=(m+2);ll<=l;ll++) {
				pll=(x*(2*ll-1)*pmmp1-(ll+m-1)*pmm)/(ll-m);
				pmm=pmmp1;
				pmmp1=pll;
			}
			return pll;
		}
	}
}

double dpt_plgndr::original_fn(double x, double y, double z)
{
	return plgndr((int) x, (int) y, z);
}

double dpt_plgndr::modified_fn(double x, double y, double z)
{
	return plgndrm((int) x, (int) y, z);
}

// Need to override this here to get around the nerror problem
bool dpt_plgndr::Produces_Error(double x, double y, double z)
{
	bool error_exists = false;

	if ((int) y > (int) x) // nerror problem in modified version
		error_exists = false; // just an nerror problem!!
	else // shouldn't be any nerror problems
		error_exists = (original_fn(x, y, z) != modified_fn(x, y, z));

	return error_exists;
}
