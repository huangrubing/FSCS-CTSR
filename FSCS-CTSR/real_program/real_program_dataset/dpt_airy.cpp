// dpt_airy.cpp: implementation of the dpt_airy class.
//
//////////////////////////////////////////////////////////////////////

#include "dpt_airy.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

dpt_airy::dpt_airy()
{

}

dpt_airy::~dpt_airy()
{

}

void dpt_airy::airy(double *z, int *nfunc, int *iscal, double *ai, double *bi, 
					double *aid, double *bid)
{
	if ((*z)<0.0) goto lab10;
	else if ((*z)==0.0) goto lab10;
	else goto lab40;

	lab10:
	if (((*z)+7.0)<0.0) goto lab20;
	else if (((*z)+7.0)==0.0) goto lab20;
	else goto lab30;

	lab20:
	coef1(z, nfunc, ai, bi, aid, bid);
	return;

	lab30:
	coef2(z, nfunc, ai, bi, aid, bid);
	return;

	lab40:
	if (((*z)-7.0)<0.0) goto lab50;
	else if (((*z)-7.0)==0.0) goto lab60;
	else goto lab60;

	lab50:
	coef3(z, nfunc, ai, bi, aid, bid);
	return;

	lab60:
	coef4(z, nfunc, iscal, ai, bi, aid, bid);
}

void dpt_airy::airym(double *z, int *nfunc, int *iscal, double *ai, double *bi, 
					 double *aid, double *bid)
{
	if ((*z)<0.0) goto lab10;
	else if ((*z)==0.0) goto lab10;
	else goto lab40;

	lab10:
	if (((*z)+7.0)<0.0) goto lab20;
	else if (((*z)+7.0)==0.0) goto lab20;
	else goto lab30;

	lab20:
	coef1(z, nfunc, ai, bi, aid, bid);
	return;

	lab30:
	coef2(z, nfunc, ai, bi, aid, bid);
	return;

	lab40:
	//if (((*z)-7.0)<0.0) goto lab50;
	//else if (((*z)-7.0)==0.0) goto lab60;
	//else goto lab60;

	goto lab60;
	lab50:
	coef3(z, nfunc, ai, bi, aid, bid);
	return;

	lab60:
	coef4(z, nfunc, iscal, ai, bi, aid, bid);
}

void dpt_airy::cheb(double *x, int n, double *a, double *f)
{
	//double a[25];
	double b,c, d, u, y;
	int i,j;

	b=0.0;
	d=a[n-1];
	u=(*x)+(*x)-1.0;
	y=u+u;
	j=n-1;
	for (i=3; i<=n; i++) {
		c=b;
		b=d;
		d=y*b-c+a[j-1];
		j=j-1;
		}
	*f=u*d - b + 0.5*a[0];
	return;
}

void dpt_airy::coef1(double *z, int *nfunc, double *ai, double *bi, double *aid, 
					 double *bid)
{
	double a[5] = {1.1282427601, -0.6803534E-04, 0.16687E-06, -0.128E-08, 0.2E-10};
	double b[5] = {0.7822108673E-01, -0.6895649E-04, 0.32857E-06, -0.370E-08, 0.7E-10};
	double c[5] = {1.1285404716, 0.8046925E-04,-0.18161E-06,0.135E-08,-0.2E-10};
	double d[5] = {-0.10954855184, 0.7713350E-04, -0.35168E-06, 0.388E-08, -0.7E-10};
	double pi4=0.78539816340;
	double anglm=250.0, angup=1.0e10;
	int nout=22;
	double x, sx, zeta, y, z4, ang, zetai, z4i;
	double sn, cn, fa, fb, fc, fd;

	x=-*z;
	sx=sqrt(x);
	y=x*sx;
	zeta=0.66666666667*y;
	z4=sqrt(sx);
	ang=zeta+pi4;
	if ((anglm-ang)<0.0) goto coef1_10;
	else if ((anglm-ang)==0.0) goto coef1_40;
	else goto coef1_40;

	coef1_10:
	if ((angup-ang) <0.0) goto coef1_20;
	else if ((angup-ang) ==0.0) goto coef1_20;
	else goto coef1_30;

	coef1_20:;
	*ai=0.0;
	*bi=0.0;
	*aid=0.0;
	*bid=0.0;
	return;

	coef1_30:;

	coef1_40:
	sn=sin(ang);
	cn=cos(ang);
	zetai=1.0/zeta;
	x=7.0/x;
	x=x*x*x;

	if (*nfunc < 0) goto coef1_50;
	cheb(&x, 5, a, &fa);
	cheb(&x, 5, b, &fb);
	z4i=1.0/z4;

	fb=fb*zetai;
	*ai=z4i*(sn*fa-cn*fb);
	*bi=z4i*(cn*fa+sn*fb);
	if (*nfunc > 0) return;

	coef1_50:
	cheb(&x, 5, c, &fc);
	cheb(&x, 5, d, &fd);
	fd=fd*zetai;
	*aid=-z4*(cn*fc+sn*fd);
	*bid=z4*(sn*fc-cn*fd);

	return;
}

void dpt_airy::coef2(double *z, int *nfunc, double *ai, double *bi, double *aid, 
					 double *bid)
{
	double a[17]={0.11535880704, 0.6542816649E-01, 0.26091774326, 0.21959346500,
	0.12476382168, -0.43476292594, 0.28357718605, -0.9751797082E-01,
	0.2182551823E-01, -0.350454097E-02, 0.42778312E-03,
	-0.4127564E-04, 0.323880E-05, -0.21123E-06, 0.1165E-07,
	-0.55E-09, 0.2E-10};

	double b[16]={0.10888288487, -0.17511655051, 0.13887871101, -0.11469998998,
	0.22377807641, -0.18546243714, 0.8063565186E-01,
	-0.2208647864E-01, 0.422444527E-02, -0.60131028E-03,
	0.6653890E-04, -0.590842E-05, 0.43131E-06, -0.2638E-07,
	0.137E-08, -0.6E-10};

	double c[16]={0.7571648463E-01,-0.10150232871,0.7800551669E-01,
	-0.8324569361E-01,0.10105322731,-0.6578603344E-01,
	0.2500140353E-01,-0.625962704E-02,0.111945149E-02,
	-0.15102718E-03,0.1598086E-04,-0.136545E-05,0.9636E-07,
	-0.572E-08,0.29E-09,-0.1E-10};

	double d[17]={0.61603048107,0.85738069722,0.86345334421,0.80890228699,
	-0.50565665369,-0.81829752697,0.77829538563,-0.31201242692,
	0.7677186198E-01,-0.1320520264E-01,0.170185698E-02,
	-0.17177956E-03,0.1401068E-04,-0.94532E-06,0.5374E-07,
	-0.261E-08,0.11E-09};

	double e1=0.35502805389, e2=0.25881940379, root3=1.7320508076;
	double x, fa, fb, fc, fd;

	x=-0.29154518950E-02*(*z)*(*z)*(*z);

	if (*nfunc <0) goto coef2_10;
	cheb(&x, 17, a, &fa);
	cheb(&x, 16, b, &fb);
	fa=e1*fa;
	fb=e2*(*z)*fb;
	*ai=fa-fb;
	*bi=root3*(fa+fb);
	if (nfunc>0) return;

	coef2_10:
	cheb(&x, 16, c, &fc);
	cheb(&x, 17, d, &fd);
	fc=e1*(*z)*(*z)*fc;
	fd=e2*fd;
	*aid=fc-fd;
	*bid=root3*(fc+fd);
	return;
}

void dpt_airy::coef3(double *z, int *nfunc, double *ai, double *bi, double *aid, 
					 double *bid)
{
	double a[20]={1.2974695794,-0.20230907821,
	-0.45786169516,0.12953331987,0.6983827954E-01,
	-0.3005148746E-01,-0.493036334E-02,0.390425474E-02,
	-0.1546539E-04,-0.32193999E-03,0.3812734E-04,0.1714935E-04,
	-0.416096E-05,-0.50623E-06, 0.26346E-06,-0.281E-08,
	-0.1122E-07,0.120E-08,0.31E-09,-0.7E-10};

	double b[25]={0.47839902387,-0.6881732880E-01,0.20938146768,
	 -0.3988095886E-01,0.4758441683E-01,-0.812296149E-02,
	  0.462845913E-02,0.70010098E-03,-0.75611274E-03,
	   0.68958657E-03,-0.33621865E-03,0.14501668E-03,-0.4766359E-04,
	    0.1251965E-04,-0.193012E-05,-0.19032E-06,0.29390E-06,
	     -0.13436E-06,0.4231E-07 ,-0.967E-08,0.135E-08, 0.7E-10,
	      -0.12E-09,0.4E-10 ,-0.1E-10};

	double c[22]={-2.2359158747,
	 -0.2638272392E-01,0.95151904332,-0.8383641182E-01,
	  -0.19401303219,0.3580664778E-01,0.2269348562E-01,
	   -0.671179820E-02,-0.152460473E-02,0.75474150E-03,
	    0.3729934E-04,-0.5653536E-04,0.350796E-05,0.289418E-05,
	     -0.47423E-06,-0.9449E-07,0.3054E-07,0.109E-08,-0.130E-08,
	      0.8E-10,0.4E-10,-0.1E-10};

        double d[24]={0.71364662990,0.23777925892,0.28219009446,0.4912480040E-01,
         0.6741261353E-01,-0.406308553E-02,0.1544814895E-01,
          -0.449172894E-02,0.322474426E-02,-0.105361380E-02,
           0.41311371E-03,-0.8536169E-04,0.655166E-05,0.960458E-05,
            -0.641792E-05,0.280308E-05 ,-0.89454E-06,0.21392E-06,
             -0.2958E-07,-0.309E-08,0.376E-08,-0.148E-08,0.39E-09,
              -0.7E-10};

      	double x,ex, ey;

	x=0.14285714286*(*z);
	ex=exp(1.75*(*z));
	ey=1.0/ex;

	if (*nfunc<0) goto coef3_10;
	cheb(&x, 20, a, ai);
	cheb(&x, 25, b, bi);

	*ai=*ai * ey;
	*bi=*bi * ex;
	if (*nfunc > 0) return;

	coef3_10:
	cheb(&x, 22, c, aid);
	cheb(&x, 24, d, bid);

	*aid=*aid * ey;
	*bid=*bid * ex;
	return;
}

void dpt_airy::coef4(double *z, int *nfunc, int *iscal, double *ai, double *bi, 
					 double *aid, double *bid)
{
	double a[7]={0.56265126169,-0.76136219E-03,0.765252E-05,-0.14228E-06,
	0.380E-08,-0.13E-09,0.1E-10};

	double b[7]={1.1316635302,0.166141673E-02,0.1968882E-04,0.47047E-06,
	0.1769E-07,0.94E-09,0.6E-10};

	double c[7]={0.56635357764,0.107273242E-02,-0.910034E-05,0.15998E-06,
	-0.415E-08,0.14E-09 ,-0.1E-10};

	double d[7]={1.1238058432,-0.230925296E-02,-0.2309457E-04,-0.52171E-06,
	-0.1907E-07,-0.100E-08,-0.7E-10};

	double zlim=175.0, zup=1.0e50;
	int nout=6;
	double x,y,zeta, ex, ey, sz, z4,z4i;

	if ((zup-(*z))<0.0) goto coef4_10;
	else if ((zup-(*z))==0.0) goto coef4_20;
	else goto coef4_20;

	coef4_10:;
	*ai=0.0;
	*bi=0.0;
	*aid=0.0;
	*bid=0.0;
	return;

	coef4_20:
	sz=sqrt(*z);
	y=(*z)*sz;
	z4=sqrt(sz);
	if ((*iscal)<0) goto coef4_30;
	else if ((*iscal)==0) goto coef4_40;
	else goto coef4_30;

	coef4_30:
	ex=1.0;
	ey=1.0;
	goto coef4_70;

	coef4_40:
	zeta=0.66666666667*y;
	if ((zlim-zeta)<0.0) goto coef4_50;
	else if ((zlim-zeta)==0.0) goto coef4_60;
	else goto coef4_60;

	coef4_50:;
	*ai=0.0;
	*aid=0.0;

	*bi=exp(zlim);
	*bid=*bi;
	return;

	coef4_60:
	ex=exp(zeta);
	ey=1.0/ex;

	coef4_70:
	x=18.520259177/y;

	if((*nfunc)<0) goto coef4_80;
	cheb(&x, 7, a, ai);
	cheb(&x, 7, b, bi);
	z4i=1.0/z4;
	*ai=z4i*(*ai)*ey;
	*bi=z4i*(*bi)*ex;
	if ((*nfunc) > 0) return;

	coef4_80:
	cheb(&x, 7, c, aid);
	cheb(&x, 7, d, bid);
	*aid=-z4*(*aid)*ey;
	*bid=z4*(*bid)*ex;
	return;
}


bool dpt_airy::Produces_Error(double x)
{
	bool error_exists = false;

	int nfunc = 0;
	int iscal = 1;
	double ai, aim, bi, bim, aid, aidm, bid, bidm;

	// Call the functions
	airy(&x, &nfunc, &iscal, &ai, &bi, &aid, &bid);
	airym(&x, &nfunc, &iscal, &aim, &bim, &aidm, &bidm);

	if (ai != aim || bi != bim)
		error_exists = true;
	
	return error_exists;
}
