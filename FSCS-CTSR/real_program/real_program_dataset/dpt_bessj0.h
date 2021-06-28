// dpt_bessj0.h: interface for the dpt_bessj0 class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_BESSJ0_H__51519E24_75ED_11D5_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_BESSJ0_H__51519E24_75ED_11D5_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_bessj0 : public dpt_TestProgram_base
{
public:
	dpt_bessj0();
	virtual ~dpt_bessj0();


public:
	double bessj0(double x);
	double bessj0m(double x);

	virtual double original_fn(double x);
	virtual double modified_fn(double x);
};

#endif // !defined(AFX_DPT_BESSJ0_H__51519E24_75ED_11D5_948C_00A0C9DAC62C__INCLUDED_)
