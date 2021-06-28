#ifndef _RANDOMFUNCTION_H
#define _RANDOMFUNCTION_H


#include <iostream>
#include <fstream>
#include <strstream>
#include <string>
#include <time.h>
#include <Windows.h>
#include <stdlib.h>
#include <vector>
#include <set>
#include <list>
#include <math.h>
#include <sstream>
#include <bitset>
#include <utility>
#include <iomanip>

#include <algorithm>

using namespace std;


/* Period parameters */  
#define N 624
#define M 397
#define MATRIX_A 0x9908b0df   /* constant vector a */
#define UPPER_MASK 0x80000000 /* most significant w-r bits */
#define LOWER_MASK 0x7fffffff /* least significant r bits */
/* Tempering parameters */   
#define TEMPERING_MASK_B 0x9d2c5680
#define TEMPERING_MASK_C 0xefc60000
#define TEMPERING_SHIFT_U(y)  (y >> 11)
#define TEMPERING_SHIFT_S(y)  (y << 7)
#define TEMPERING_SHIFT_T(y)  (y << 15)
#define TEMPERING_SHIFT_L(y)  (y >> 18)


static unsigned long mt[N]; /* the array for the state vector  */
static int mti=N+1; /* mti==N+1 means mt[N] is not initialized */

/* Initializing the array with a seed */
static void sgenrand(unsigned long seed)
{
	int i;

	for (i=0;i<N;i++) {
		mt[i] = seed & 0xffff0000;
		seed = 69069 * seed + 1;
		mt[i] |= (seed & 0xffff0000) >> 16;
		seed = 69069 * seed + 1;
	}
	mti = N;
}

static unsigned long genrand()
{
	unsigned long y;
	static unsigned long mag01[2]={0x0, MATRIX_A};
	/* mag01[x] = x * MATRIX_A  for x=0,1 */

	if (mti >= N) { /* generate N words at one time */
		int kk;

		if (mti == N+1) 
		{/* if sgenrand() has not been called, */
			srand(time(0)+mti*rand());
			//rand();
			sgenrand(rand());
			//sgenrand(4357); /* a default initial seed is used   */

		}

		for (kk=0;kk<N-M;kk++) {
			y = (mt[kk]&UPPER_MASK)|(mt[kk+1]&LOWER_MASK);
			mt[kk] = mt[kk+M] ^ (y >> 1) ^ mag01[y & 0x1];
		}
		for (;kk<N-1;kk++) {
			y = (mt[kk]&UPPER_MASK)|(mt[kk+1]&LOWER_MASK);
			mt[kk] = mt[kk+(M-N)] ^ (y >> 1) ^ mag01[y & 0x1];
		}
		y = (mt[N-1]&UPPER_MASK)|(mt[0]&LOWER_MASK);
		mt[N-1] = mt[M-1] ^ (y >> 1) ^ mag01[y & 0x1];

		mti = 0;
	}

	y = mt[mti++];
	y ^= TEMPERING_SHIFT_U(y);
	y ^= TEMPERING_SHIFT_S(y) & TEMPERING_MASK_B;
	y ^= TEMPERING_SHIFT_T(y) & TEMPERING_MASK_C;
	y ^= TEMPERING_SHIFT_L(y);

	return y; /* for integer generation */
}

static double i0i1genrand()
{
	unsigned long y = genrand();
	return ( (double)y * 2.3283064370807974e-10 ); /* reals :[0,1]*/
	/* return y; */ /* for integer generation */
}

static double i0e1genrand()
{
	unsigned long y = genrand();
	return ( (double)y * 2.3283064365386963e-10 ); /* reals: [0,1)-interval */
	/* return y; */ /* for integer generation */
}

static double e0e1genrand()
{
	unsigned long y = genrand();
	return ( ((double)y + 1.0) * 2.3283064359965952e-10 ); /* reals: (0,1)-interval */
	/* return y; */ /* for integer generation */    
}

/*[a , b )*/
static long 
rand_test_iaeb(long a , long b)
{
	return a + (b-a) * i0i1genrand();
}

/*(a, b)*/
static long 
rand_test_eaeb(long a , long b)
{
	long ret = a + (b-a) * i0i1genrand();
	while (ret == a)
		ret = a + (b-a) * i0i1genrand();
	return ret;
}

/*(a, b]*/
static long 
rand_test_eaib(long a , long b)
{
	long ret = a + (b+1-a) * i0i1genrand();
	while (ret == a)
		ret = a + (b+1-a) * i0i1genrand();
	return ret;
}

/* [a, b] */
static long 
rand_test_iaib(long a , long b)
{
	return a + (b+1-a) * e0e1genrand();
}

static double i0i1randomDouble(double a, double b) /*real: [a, b]-double*/
{
	double c = 0;

	c = a + i0i1genrand() * (b - a);

	return c;
}

static double i0e1randomDouble(double a, double b) /*real: [a, b)-double*/
{
	double c = 0;

	c = a + i0e1genrand() * (b - a);

	return c;
}
//Random function end://


#endif