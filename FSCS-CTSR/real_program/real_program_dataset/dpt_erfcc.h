// dpt_erfcc.h: interface for the dpt_erfcc class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_ERFCC_H__E7DD6C63_7571_11D5_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_ERFCC_H__E7DD6C63_7571_11D5_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_erfcc : public dpt_TestProgram_base
{
public:
	dpt_erfcc();
	virtual ~dpt_erfcc();


public:
	double erfcc(double x);
	double erfccm(double x);

	virtual double original_fn(double x);
	virtual double modified_fn(double x);
};

#endif // !defined(AFX_DPT_ERFCC_H__E7DD6C63_7571_11D5_948C_00A0C9DAC62C__INCLUDED_)
