// dpt_bessj.h: interface for the dpt_bessj class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_BESSJ_H__51519E23_75ED_11D5_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_BESSJ_H__51519E23_75ED_11D5_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_bessj : public dpt_TestProgram_base
{
public:
	dpt_bessj();
	virtual ~dpt_bessj();


public:
	double bessj(int n, double x);
	double bessjm(int n, double x);
	double bessj0(double x);
	double bessj0m(double x);
	double bessj1(double x);

	virtual double original_fn(double x, double y);
	virtual double modified_fn(double x, double y);
};

#endif // !defined(AFX_DPT_BESSJ_H__51519E23_75ED_11D5_948C_00A0C9DAC62C__INCLUDED_)
