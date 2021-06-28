// dpt_el2.cpp: implementation of the dpt_el2 class.
//
//////////////////////////////////////////////////////////////////////

#include "dpt_el2.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

dpt_el2::dpt_el2()
{

}

dpt_el2::~dpt_el2()
{

}

double dpt_el2::el2(double x, double qqc, double aa, double bb)
{
	const double PI = 3.14159265;
	const double CA = 0.0003;
	const double CB = 1.0e-9;

	double a, b, c, d, e, f, g, em, eye, p, qc, y, z;
	int l;

	if (x == 0.0)
		return 0.0;
	else if (qqc != 0.0) 
	{
		qc = qqc;
		a = aa;
		b = bb;
		// Original Pascal uses Pascal routine sqr(x) instead of x*x
		c = (x*x);
		d = 1.0+c;
		// Original Pascal uses Pascal routine sqr(qc) instead of qc*qc
		p = sqrt( (1.0+c*(qc*qc))/d);
		d = x/d;
		c = d/(2.0*p);
		z = a-b;
		eye = a;
		a = 0.5*(b+a);
		y = fabs(1.0/x);
		f = 0.0;
		l = 0;
		em = 1.0;
		qc = fabs(qc);

one:	b = eye*qc+b;
		e = em*qc;
		g = e/p;
		d = f*g+d;
		f = c;
		eye = a;
		p = g+p;
		c = 0.5*(d/p+c);
		g = em;
		em = qc+em;
		a = 0.5*(b/em+a);
		y = -e/y+y;

		if (y == 0.0) 
			y = sqrt(e)*CB;

		if (fabs(g-qc) > CA*g) 
		{
			qc = sqrt(e)*2.0;
			l = l+l;
			if (y < 0.0)
				l = l+1;
			goto one;
		}
		if (y < 0.0) 
			l = l+1;

		e = (atan(em/y)+PI*l)*a/em;

		if (x < 0.0)
			e = -e;

		return (e+c*z);
	} else { // (x != 0.0) and (qqc == 0.0)
		// overwrote the ::Produces_Error() method
		// writeln('pause in routine EL2'); readln;
		cout << "pause in routine el2" << endl;
		char dpt;
		cin >> dpt;		
	}
}

double dpt_el2::el2m(double x, double qqc, double aa, double bb)
{
	const double PI = 3.14159265;
	const double CA = 0.0003;
	const double CB = 1.0e-9;

	double a, b, c, d, e, f, g, em, eye, p, qc, y, z;
	int l;

	if (x == 0.0)
		return 0.0;
	else if (qqc != 0.0) 
	{
		qc = qqc;
		a = aa;
		b = bb;
		// Original Pascal uses Pascal routine sqr(x) instead of x*x
		c = (x*x);
		d = 1.0+c;
		// Original Pascal uses Pascal routine sqr(qc) instead of qc*qc
		/* ERROR */
		/* p = sqrt( (1.0+c*(qc*qc))/d); */
		p = sqrt( (1.0+c*(qqc*qqc))/d);
		d = x/d;
		c = d/(2.0*p);
		z = a-b;
		eye = a;
		a = 0.5*(b+a);
		y = fabs(1.0/x);
		f = 0.0;
		l = 0;
		em = 1.0;
		qc = fabs(qc);

one:	b = eye*qc+b;
		e = em*qc;
		g = e/p;
		d = f*g+d;
		f = c;
		eye = a;
		p = g+p;
		c = 0.5*(d/p+c);
		g = em;
		em = qc+em;
		a = 0.5*(b/em+a);
		y = -e/y+y;

		if (y == 0.0) 
			/* ERROR */
			/* y = sqrt(e)*CB; */
			y = sqrt(em)+CB;

		/* ERROR */
		/* if (fabs(g-qc) > CA*g) */
		if (fabs(g-qc) >= CA*g) 
		{
			qc = sqrt(e)*2.0;
			l = l+l;
			/* ERROR */
			/* if (y < 0.0) */
			if (y <= 0.1)
				l = l+1;
			goto one;
		}
		/* ERROR */
		/* if (y < 0.0) */
		if (y < 4.0) 
			l = l+1;

		e = (atan(em/y)+PI*l)*a/em;

		/* ERROR */
		/* if (x < 0.0) */
		if (x <= 0.1)
			e = -e;

		return (e+c*z);
	} else { // (x != 0.0) and (qqc == 0.0)
		// overwrote the ::Produces_Error() method
		// writeln('pause in routine EL2'); readln;
		cout << "pause in routine el2" << endl;
		char dpt;
		cin >> dpt;		
	}
}


double dpt_el2::original_fn(double x, double y, double z, double w)
{
	return el2(x, y, z, w);
}

double dpt_el2::modified_fn(double x, double y, double z, double w)
{
	return el2m(x, y, z, w);
}

// Need to override this here to get around the pause problem
bool dpt_el2::Produces_Error(double x, double y, double z, double w)
{
	bool error_exists = false;

	 // (x != 0.0) and (qqc == 0.0)
	if ((y == 0.0) && (x != 0.0)) // pause problem in modified version
		error_exists = false; // just a pause problem!!
	else // shouldn't be any nerror problems
	{
		double orig = original_fn(x, y, z, w);
		double modi = modified_fn(x, y, z, w);

		error_exists = (orig != modi);

		// error_exists = (original_fn(x, y, z, w) != modified_fn(x, y, z, w));
	}

	return error_exists;
}
