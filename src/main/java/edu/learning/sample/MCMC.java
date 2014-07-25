package edu.learning.sample;

/**
 * Date 2014-07-21
 *
 * @author SunLian
 * @version 0.1
 */
public class MCMC {

    /**
     * 判断x与y的平方和是否小于等于1
     *
     * @param x
     * @param y
     * @return true or false
     */
    public  boolean isTrue(double x, double y){
        if(x*x+y*y<=1) return true;
        else{
            return false;
        }
    }

    /**
     *求圆周PI的值
     *
     * @return PI
     */
    public double calculator(){
        double count=5000;
        double PI;
        int num=0;

        for(int i=0;i<count;i++){
           double x=Math.random()*1.00;
           double y=Math.random()*1.00;
            if(isTrue(x,y)) {
                num++;
            }
        }
        PI=(num*4.00)/count;
        return PI;
    }
}
