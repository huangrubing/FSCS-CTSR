//#pragma once
#if !defined(AFX_DPT_TESTPROGRAM_BASE_H__6A70D8C2_792C_11D5_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_TESTPROGRAM_BASE_H__6A70D8C2_792C_11D5_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <stdlib.h>
#include <iostream>
#include <iomanip>
#include <stdio.h>
#include <math.h>
using namespace std;

class dpt_TestProgram_base
{
//public:
	//dpt_TestProgram_base(void);
public:
	dpt_TestProgram_base();
	virtual ~dpt_TestProgram_base();

	// to be overloaded in each derived class
	virtual double original_fn(double x);
	virtual double original_fn(double x, double y);
	virtual double original_fn(double x, double y, double z);
	virtual double original_fn(double x, double y, double z, double w);

	virtual double modified_fn(double x);
	virtual double modified_fn(double x, double y);
	virtual double modified_fn(double x, double y, double z);
	virtual double modified_fn(double x, double y, double z, double w);

	virtual bool Produces_Error(double x);
	virtual bool Produces_Error(double x, double y);
	virtual bool Produces_Error(double x, double y, double z);
	virtual bool Produces_Error(double x, double y, double z, double w);

public:
	static void nrerror(char * error_text);
};
#endif // !defined(AFX_DPT_TESTPROGRAM_BASE_H__6A70D8C2_792C_11D5_948C_00A0C9DAC62C__INCLUDED_)
