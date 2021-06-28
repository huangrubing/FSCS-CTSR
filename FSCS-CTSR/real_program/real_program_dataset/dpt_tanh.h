// dpt_tanh.h: interface for the dpt_tanh class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_TANH_H__AE427261_1414_11D6_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_TANH_H__AE427261_1414_11D6_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include <iostream>

#include "dpt_TestProgram_base.h"

class dpt_tanh : public dpt_TestProgram_base  
{
public:
	dpt_tanh();
	virtual ~dpt_tanh();

public:
	double tanh(double u);
	double tanhm(double u);

	virtual double original_fn(double x);
	virtual double modified_fn(double x);
};

#endif // !defined(AFX_DPT_TANH_H__AE427261_1414_11D6_948C_00A0C9DAC62C__INCLUDED_)
