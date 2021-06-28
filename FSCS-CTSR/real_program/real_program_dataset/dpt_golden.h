// dpt_golden.h: interface for the dpt_golden class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_GOLDEN_H__AE427264_1414_11D6_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_GOLDEN_H__AE427264_1414_11D6_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_golden : public dpt_TestProgram_base  
{
public:
	dpt_golden();
	virtual ~dpt_golden();

public:
	double bessj0(double x);
	double func(double x);
	double golden(double ax, double bx, double cx, double tol, double& xmin);
	double goldenm(double ax, double bx, double cx, double tol, double& xmin);
	virtual bool Produces_Error(double x, double y, double z);

};

#endif // !defined(AFX_DPT_GOLDEN_H__AE427264_1414_11D6_948C_00A0C9DAC62C__INCLUDED_)
