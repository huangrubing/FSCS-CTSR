// dpt_cel.h: interface for the dpt_cel class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_CEL_H__47DE5CA1_7531_11D5_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_CEL_H__47DE5CA1_7531_11D5_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_cel : public dpt_TestProgram_base 
{
public:
	dpt_cel();
	virtual ~dpt_cel();


public:
	double cel(double qqc, double pp, double aa, double bb);
	double celm(double qqc, double pp, double aa, double bb);

	virtual double original_fn(double x, double y, double z, double w);
	virtual double modified_fn(double x, double y, double z, double w);

};

#endif // !defined(AFX_DPT_CEL_H__47DE5CA1_7531_11D5_948C_00A0C9DAC62C__INCLUDED_)
