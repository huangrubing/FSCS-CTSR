// dpt_airy.h: interface for the dpt_airy class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_AIRY_H__A4FD27E1_158B_11D6_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_AIRY_H__A4FD27E1_158B_11D6_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_airy : public dpt_TestProgram_base  
{
public:
	dpt_airy();
	virtual ~dpt_airy();

public:
	void cheb(double *x, int n, double *a, double *f);
	void coef1(double *z, int *nfunc, double *ai, double *bi, double *aid, double *bid);
	void coef2(double *z, int *nfunc, double *ai, double *bi, double *aid, double *bid);
	void coef3(double *z, int *nfunc, double *ai, double *bi, double *aid, double *bid);
	void coef4(double *z, int *nfunc, int *iscal, double *ai, double *bi, 
			double *aid, double *bid);

	void airy(double *z, int *nfunc, int *iscal, double *ai, double *bi, 
			double *aid, double *bid);
	void airym(double *z, int *nfunc, int *iscal, double *ai, double *bi, 
			double *aid, double *bid);

	virtual bool Produces_Error(double x);
};

#endif // !defined(AFX_DPT_AIRY_H__A4FD27E1_158B_11D6_948C_00A0C9DAC62C__INCLUDED_)
