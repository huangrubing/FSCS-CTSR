// dpt_sncndn.h: interface for the dpt_sncndn class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DPT_SNCNDN_H__E7DD6C62_7571_11D5_948C_00A0C9DAC62C__INCLUDED_)
#define AFX_DPT_SNCNDN_H__E7DD6C62_7571_11D5_948C_00A0C9DAC62C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "stdarg.h"
#include "dpt_TestProgram_base.h"

class dpt_sncndn : public dpt_TestProgram_base
{
public:
	dpt_sncndn();
	virtual ~dpt_sncndn();

	virtual bool Produces_Error(double x, double y);

public:
	void sncndn(float uu, float emmc, float * sn, float * cn, float * dn);
	void sncndnm(float uu, float emmc, float * sn, float * cn, float * dn);

};

#endif // !defined(AFX_DPT_SNCNDN_H__E7DD6C62_7571_11D5_948C_00A0C9DAC62C__INCLUDED_)
