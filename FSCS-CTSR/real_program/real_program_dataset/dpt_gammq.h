// dpt_gammq.h: interface for the dpt_gammq class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_GAMMQ_H__47DE5CA3_7531_11D5_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_GAMMQ_H__47DE5CA3_7531_11D5_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_gammq : public dpt_TestProgram_base 
{
public:
	dpt_gammq();
	virtual ~dpt_gammq();

public:
	void gcf(float * gammcf, float a, float x, float* gln);
	float gammln(float xx);
	void gser(float * gamser, float a, float x, float * gln);
	void gserm(float * gamser, float a, float x, float * gln);
	float gammq(float a, float x);
	float gammqm(float a, float x);

	virtual double original_fn(double x, double y);
	virtual double modified_fn(double x, double y);

	// Override this to get around the nerror() problems
	virtual bool Produces_Error(double x, double y);
};

#endif // !defined(AFX_DPT_GAMMQ_H__47DE5CA3_7531_11D5_948C_00A0C9DAC62C__INCLUDED_)
