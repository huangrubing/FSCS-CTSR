// dpt_el2.h: interface for the dpt_el2 class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_EL2_H__AE427265_1414_11D6_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_EL2_H__AE427265_1414_11D6_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_el2 : public dpt_TestProgram_base  
{
public:
	dpt_el2();
	virtual ~dpt_el2();

public:
	double el2(double x, double qqc, double aa, double bb);
	double el2m(double x, double qqc, double aa, double bb);

	virtual double original_fn(double x, double y, double z, double w);
	virtual double modified_fn(double x, double y, double z, double w);

	// Overridden version to get around some problem in the modified version
	virtual bool Produces_Error(double x, double y, double z, double w);

};

#endif // !defined(AFX_DPT_EL2_H__AE427265_1414_11D6_948C_00A0C9DAC62C__INCLUDED_)
