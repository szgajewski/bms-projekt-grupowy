#include<stdio.h>
#include<math.h>

#define N 10
#define PI 3.14
struct Regula{
	int F,co; //0-gaussian  //0-temp0
	float b; //wartosc srednia
	float czas;
	Regula(int x, int y,float f ,float c){
		F=x;
		co=y;
		b=f;
		czas=c;
	}
	Regula(){
      F=co=0;
      b=czas=0; 
    }
} reg(0,0,0,0);

float gaussian( double b, double c, double x){
      double a;
      float value;
      a= 1/(c*sqrt(2* PI));
		//  ,b=mean value ,c=standard deviation
      value = (float) (a * exp(-1 * ((x - b) * (x - b)) / (2 * c * c)));
      return value;
}
float fuzzy1(float wartosc, float p1, float p2) { //S
        float wynik = 0;
        if (wartosc <= p1) {
            wynik = 0;
        } else if (wartosc >= p2) {
            wynik = 1;
        } else {
            wynik = 0+(wartosc- p1)/ (p2 - p1);
        }
        return wynik;
}

float fuzzy2(float wartosc, float p1, float p2) { //Z
        float wynik = 0;
        if (wartosc <= p1) {
            wynik = 1;
        } else if (wartosc >= p2) {
            wynik = 0;
        } else {
            wynik = 1-(wartosc-p1)/(p2-p1);
        }
        return wynik;
}

float fuzzy3(float wartosc, float p1, float p2, float p3) { //trojkatna funkcja
        float wynik = 0;
        if (wartosc <= p1 || wartosc>=p3) {
            wynik = 0;
        } else if (wartosc == p2) {
            wynik = 1;
        } else if (wartosc > p1 && wartosc < p2) {
            wynik = 0+(wartosc- p1)/ (p2 - p1);
        } else {
            wynik = 1-(wartosc-p2)/(p3-p2);
        }
        return wynik;
}

float fuzzy4(float wartosc, float p1, float p2, float p3, float p4) { //trapezowa funkcja
        float wynik = 0;
        if (wartosc <= p1 || wartosc >=p4) {
            wynik = 0;
        } else if (wartosc >= p2 && wartosc <= p3) {
            wynik = 1;
        } else if (wartosc > p1 && wartosc < p2) {
            wynik = 0+(wartosc- p1)/ (p2 - p1);
        } else {
            wynik = 1-(wartosc-p3)/(p4-p3);
        }
        return wynik;
}
float fuzzyAND(float f1,float f2){
	if (f1>f2)
		return f2;
	return f1;
}
float fuzzyOR(float f1,float f2){
	if (f1>f2)
		return f1;
	return f2;
}
float fuzzyNOT(float f1){
	return 1-f1;
}
Regula* odczyt(int czujnik){
	Regula* reguly=new Regula[N];;
	//odczyt_regul_z_bazy(reguly,czujnik);
	reguly[0]=Regula(0,0,19,0);//
	reguly[1]=Regula(0,0,20,0);//
	reguly[2]=Regula(0,0,21,0);//
	//reguly[3]=Regula(0,0,21,0);//
    return reguly;
}

float oblicz(Regula* r,float f,int n){
	//system rozmyty
	float zz,z,i,c,zc,*val;
	float value;
	int j=0;
	val=new float [2];//0-grzej, 1-wietrz
	for(j=0;j<n;++j){
       switch(r[j].F){
	               case 0:
                     zz=25*gaussian(0,10,f);
	                 z=12.5*gaussian(r[j].b-5,5,f);
	                 i=gaussian(r[j].b,1,f)/0.4;
	                 c=12.5*gaussian(r[j].b+5,5,f);
	                 zc=25*gaussian(40,10,f);
	                 val[0]=fuzzyOR(zz,z);//za zimno OR zimno-> grzej
	                 val[1]=fuzzyOR(c,zc);//cieplo OR za cieplo-> wietrz
                     //printf("G:\n\tTemp: %2f \nzz: %f z: %f i: %f \nc: %f zc: %f\ngrzej: %f wietrz: %f \nroznica: %f\n",f,zz,z,i,c,zc,val[0],val[1],val[0]-val[1]);
	                 value+=val[0]-val[1];
                     break;
                  case 1:
                     zz=fuzzy2(f,10,15);
	                 z=fuzzy4(f,10,15,r[j].b-5,r[j].b+3);
	                 i=fuzzy3(f,r[j].b-5,r[j].b,r[j].b+5);
	                 c=fuzzy4(f,r[j].b-3,r[j].b+5,25,30);
	                 zc=fuzzy1(f,25,30);
	                 val[0]=fuzzyOR(zz,z);
	                 val[1]=fuzzyOR(c,zc);
	                 value+=val[0]-val[1];
                     //printf("T:\n\tTemp: %2f \nzz: %f z: %f i: %f \nc: %f zc: %f\ngrzej: %f wietrz: %f \nroznica: %f\n",f,zz,z,i,c,zc,val[0],val[1],val[0]-val[1]);
	                 break;
                  }
    }
    value/=n;
	return value;
}
void set(int i, float *f){
	//zapis_ustawien_do_bazy(i,f);
}

int main(){
	int i;
	float tmp_f;
	float *wart=new float[N];
	Regula *tmp;
	wart[0]=15;
	for(i=0;i<21;++i){ //glowna petla
		tmp=odczyt(i);
		tmp_f=oblicz(tmp, i*0.5+15,  4);
		printf("%1.1f:%f\t", i*0.5+15,   tmp_f);
		//printf("%1.1f:%f\t",i*0.5+15,tmp_f[0]-tmp_f[1]);
		//set(i,tmp_f);
	}
	getchar();
	return 0;
}

