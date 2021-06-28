//#include  "StdAfx.h"

#include  "dpt_TestProgram_base.h"

//dpt_TestProgram_base::dpt_TestProgram_base(void)
//{
//}
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

dpt_TestProgram_base::dpt_TestProgram_base()
{

}

dpt_TestProgram_base::~dpt_TestProgram_base()
{

}

void dpt_TestProgram_base::nrerror(char *error_text)
{
	/*fprintf(stderr,"Numerical Recipes run-time error...\n");
	fprintf(stderr,"%s\n",error_text);
	fprintf(stderr,"...now exiting to system...\n");*/
	//exit(1);
}

double dpt_TestProgram_base::original_fn(double x)
{
	//fprintf(stderr,"execute the base function\n");
	return 0.0;
}

double dpt_TestProgram_base::original_fn(double x, double y)
{
	return 0.0;
}

double dpt_TestProgram_base::original_fn(double x, double y, double z)
{
	return 0.0;
}

double dpt_TestProgram_base::original_fn(double x, double y, double z, double w)
{
	return 0.0;
}

double dpt_TestProgram_base::modified_fn(double x)
{
	return 0.0;
}

double dpt_TestProgram_base::modified_fn(double x, double y)
{
	return 0.0;
}

double dpt_TestProgram_base::modified_fn(double x, double y, double z)
{
	return 0.0;
}

double dpt_TestProgram_base::modified_fn(double x, double y, double z, double w)
{
	return 0.0;
}

// 1D
bool dpt_TestProgram_base::Produces_Error(double x)
{
	//fprintf(stderr,"execute the base function\n");
	return (original_fn(x) != modified_fn(x));
}

// 2D
bool dpt_TestProgram_base::Produces_Error(double x, double y)
{
	return (original_fn(x, y) != modified_fn(x, y));
}

// 3D
bool dpt_TestProgram_base::Produces_Error(double x, double y, double z)
{
	return (original_fn(x, y, z) != modified_fn(x, y, z));
}

// 4D
bool dpt_TestProgram_base::Produces_Error(double x, double y, double z, double w)
{
	double orig = original_fn(x, y, z, w);
	double modi = modified_fn(x, y, z, w);

	return (orig != modi);
	// for some unknown reason, the following line doesn't work on unix intel!
//	return (original_fn(x, y, z, w) != modified_fn(x, y, z, w));
}
