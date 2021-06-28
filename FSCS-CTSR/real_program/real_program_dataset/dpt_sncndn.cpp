// dpt_sncndn.cpp: implementation of the dpt_sncndn class.
//
//////////////////////////////////////////////////////////////////////

#include "dpt_sncndn.h"

#define CA 0.0003

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

dpt_sncndn::dpt_sncndn()
{

}

dpt_sncndn::~dpt_sncndn()
{

}

void dpt_sncndn::sncndn(float uu, float emmc, float *sn, float *cn, float *dn)
{
	float a,b,c,d,emc,u;
	float em[14],en[14];
	int i,ii,l,bo;

	emc=emmc;
	u=uu;
	if (emc) {
		bo=(emc < 0.0);
		if (bo) {
			d=1.0-emc;
			emc /= -1.0/d;
			u *= (d=sqrt(d));
		}
		a=1.0;
		*dn=1.0;
		for (i=1;i<=13;i++) {
			l=i;
			em[i]=a;
			en[i]=(emc=sqrt(emc));
			c=0.5*(a+emc);
			if (fabs(a-emc) <= CA*a) break;
			emc *= a;
			a=c;
		}
		u *= c;
		*sn=sin(u);
		*cn=cos(u);
		if (*sn) {
			a=(*cn)/(*sn);
			c *= a;
			for (ii=l;ii>=1;ii--) {
				b=em[ii];
				a *= c;
				c *= (*dn);
				*dn=(en[ii]+a)/(b+a);
				a=c/b;
			}
			a=1.0/sqrt(c*c+1.0);
			*sn=(*sn >= 0.0 ? a : -a);
			*cn=c*(*sn);
		}
		if (bo) {
			a=(*dn);
			*dn=(*cn);
			*cn=a;
			*sn /= d;
		}
	} else {
		*cn=1.0/cosh(u);
		*dn=(*cn);
		*sn=tanh(u);
	}
}

void dpt_sncndn::sncndnm(float uu, float emmc, float *sn, float *cn, float *dn)
{
	float a,b,c,d,emc,u;
	float em[14],en[14];
	int i,ii,l,bo;

	emc=emmc;
	u=uu;
	if (emc) {
		bo=(emc < 0.0);
		if (bo) {
			d=1.0-emc;
			emc /= -1.0/d;
			u *= (d=sqrt(d));
		}
		a=1.0;
		*dn=1.0;
		for (i=1;i<=13;i++) {
			l=i;
			em[i]=a;
			en[i]=(emc=sqrt(emc));
			c=0.5*(a+emc);
			if (fabs(a-emc) <= CA*a) break;
			emc *= a;
			a=c;
		}
		u *= c;
		*sn=sin(u);
		*cn=cos(u);
		/* ERROR */
		/* if (*sn) { */
		if (*dn) {
			a=(*cn)/(*sn);
			c *= a;
			for (ii=l;ii>=1;ii--) {
				b=em[ii];
				a *= c;
				c *= (*dn);
				*dn=(en[ii]+a)/(b+a);
				a=c/b;
			}
			/* ERROR */
			/* a=1.0/sqrt(c*c+1.0); */
			a=1.0/sqrt(a*a+1.0);
			/* ERROR */
			/* *sn=(*sn >= 0.0 ? a : -a); */
			*sn=(*sn >= 0.005 ? a : -a);
			*cn=c*(*sn);
		}
		if (bo) {
			a=(*dn);
			*dn=(*cn);
			*cn=a;
			*sn /= d;
		}
	} else {
		*cn=1.0/cosh(u);
		/* ERROR */
		/* *dn=(*cn); */
		*dn=(*sn);
		*sn=tanh(u);
	}
}

bool dpt_sncndn::Produces_Error(double x, double y)
{
	float xf = (float) x; float yf = (float) y;

	// other parameters also needed to test sncndn
	float sn, cn, dn, snm, cnm, dnm;

	sncndn(xf, yf, &sn, &cn, &dn);
	sncndnm(xf, yf, &snm, &cnm, &dnm);

	return (sn != snm || cn != cnm || dn != dnm);
}



#undef CA
