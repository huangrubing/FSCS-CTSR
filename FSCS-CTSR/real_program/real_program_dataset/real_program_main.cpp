#include "stdarg.h"
#include "real_program_main.h"
#include "dllApi.h"
JNIEXPORT jboolean JNICALL Java_real_1program_main_getResult__Ljava_lang_String_2D
(JNIEnv * env, jobject, jstring name, jdouble a){
	const char *c_str = NULL;
	c_str = env->GetStringUTFChars(name, NULL);

	
//	cout << c_str << endl;
	return getResult((char*)c_str, a);
	env->ReleaseStringUTFChars(name, c_str);
}

/*
* Class:     real_program_main
* Method:    getResult
* Signature: (Ljava/lang/String;DD)Z
*/
JNIEXPORT jboolean JNICALL Java_real_1program_main_getResult__Ljava_lang_String_2DD
(JNIEnv * env, jobject, jstring name, jdouble a, jdouble b){
	const char *c_str = NULL; 
	c_str =env->GetStringUTFChars(name, NULL);
  
	//cout << c_str << endl;

	
	

	return getResult((char*)c_str, a,b);

	env->ReleaseStringUTFChars(name, c_str);
}

/*
* Class:     real_program_main
* Method:    getResult
* Signature: (Ljava/lang/String;DDD)Z
*/
JNIEXPORT jboolean JNICALL Java_real_1program_main_getResult__Ljava_lang_String_2DDD
(JNIEnv * env, jobject, jstring name, jdouble a, jdouble b, jdouble c){
	const char *c_str = NULL;
	c_str = env->GetStringUTFChars(name, NULL);

	
//	cout << c_str << endl;
	return getResult((char*)c_str, a,b,c);
	env->ReleaseStringUTFChars(name, c_str);
}

/*
* Class:     real_program_main
* Method:    getResult
* Signature: (Ljava/lang/String;DDDD)Z
*/
JNIEXPORT jboolean JNICALL Java_real_1program_main_getResult__Ljava_lang_String_2DDDD
(JNIEnv * env, jobject, jstring name, jdouble a, jdouble b, jdouble c, jdouble d){
	const char *c_str = NULL;
	c_str = env->GetStringUTFChars(name, NULL);

	
//	cout << c_str << endl;
	return getResult((char*)c_str, a,b,c,d);

	env->ReleaseStringUTFChars(name, c_str);
}