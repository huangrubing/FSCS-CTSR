// dpt_probks.h: interface for the dpt_probks class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_PROBKS_H__AE427263_1414_11D6_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_PROBKS_H__AE427263_1414_11D6_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_probks : public dpt_TestProgram_base  
{
public:
	dpt_probks();
	virtual ~dpt_probks();

public:
	double probks(double alam);
	double probksm(double alam);

	virtual double original_fn(double x);
	virtual double modified_fn(double x);

};

#endif // !defined(AFX_DPT_PROBKS_H__AE427263_1414_11D6_948C_00A0C9DAC62C__INCLUDED_)
