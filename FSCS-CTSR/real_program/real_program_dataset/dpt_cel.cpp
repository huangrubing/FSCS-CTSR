// dpt_cel.cpp: implementation of the dpt_cel class.
//
//////////////////////////////////////////////////////////////////////

#include "dpt_cel.h"

#define CA 0.0003
#define PIO2 1.57079632679490

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

dpt_cel::dpt_cel()
{

}

dpt_cel::~dpt_cel()
{

}

double dpt_cel::cel(double qqc, double pp, double aa, double bb)
{
	double a,b,e,f,g,em,p,q,qc;
//	void nrerror();

	if (qqc == 0.0) nrerror("Bad qqc in routine CEL");
	qc=fabs(qqc);
	a=aa;
	b=bb;
	p=pp;
	e=qc;
	em=1.0;
	if (p > 0.0) {
		p=sqrt(p);
		b /= p;
	} else {
		f=qc*qc;
		q=1.0-f;
		g=1.0-p;
		f -= p;
		q *= (b-a*p);
		p=sqrt(f/g);
		a=(a-b)/g;
		b = -q/(g*g*p)+a*p;
	}
	for (;;) {
		f=a;
		a += (b/p);
		g=e/p;
		b += (f*g);
		b += b;
		p=g+p;
		g=em;
		em += qc;
		if (fabs(g-qc) <= g*CA) break;
		qc=sqrt(e);
		qc += qc;
		e=qc*em;
	}
	return PIO2*(b+a*em)/(em*(em+p));
}

double dpt_cel::celm(double qqc, double pp, double aa, double bb)
{
	double a,b,e,f,g,em,p,q,qc;
//	void nrerror();

	if (qqc == 0.0) nrerror("Bad qqc in routine CEL");
	qc=fabs(qqc);
	a=aa;
	b=bb;
	p=pp;
	e=qc;
	em=1.0;
	/* ERROR */
	/* if (p > 0.0) { */
	if (p >= 0.1) {
		p=sqrt(p);
		b /= p;
	} else {
		f=qc*qc;
		q=1.0-f;
		g=1.0-p;
		/* ERROR */
		/* f -= p; */
		f = p;
		q *= (b-a*p);
		p=sqrt(f/g);
		a=(a-b)/g;
		b = -q/(g*g*p)+a*p;
	}
	for (;;) {
		f=a;
		a += (b/p);
		g=e/p;
		b += (f*g);
		b += b;
		p=g+p;
		g=em;
		em += qc;
		if (fabs(g-qc) <= g*CA) break;
		qc=sqrt(e);
		qc += qc;
		e=qc*em;
	}
	return PIO2*(b+a*em)/(em*(em+p));
}

double dpt_cel::original_fn(double x, double y, double z, double w)
{
	return cel(x, y, z, w);
}

double dpt_cel::modified_fn(double x, double y, double z, double w)
{
	return celm(x, y, z, w);
}

#undef CA
#undef PIO2
