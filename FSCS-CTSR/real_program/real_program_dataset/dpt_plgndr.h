// dpt_plgndr.h: interface for the dpt_plgndr class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_PLGNDR_H__51519E25_75ED_11D5_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_PLGNDR_H__51519E25_75ED_11D5_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_plgndr : public dpt_TestProgram_base 
{
public:
	dpt_plgndr();
	virtual ~dpt_plgndr();


public:
	double plgndr(int l, int m, double x);
	double plgndrm(int l, int m, double x);

	virtual double original_fn(double x, double y, double z);
	virtual double modified_fn(double x, double y, double z);

	// Override this to get around the nerror() problems
	virtual bool Produces_Error(double x, double y, double z);
};

#endif // !defined(AFX_DPT_PLGNDR_H__51519E25_75ED_11D5_948C_00A0C9DAC62C__INCLUDED_)
