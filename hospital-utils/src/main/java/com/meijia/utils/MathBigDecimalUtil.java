package com.meijia.utils;

import java.math.BigDecimal;

public class MathBigDecimalUtil {
	//默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;
    //这个类不能实例化
    private MathBigDecimalUtil(){
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal b1,BigDecimal b2){
        return b1.add(b2);
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal b1,BigDecimal b2){
        return b1.subtract(b2);
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal b1,BigDecimal b2){
        return b1.multiply(b2);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal b1,BigDecimal b2){
        return div(b1,b2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal b1,BigDecimal b2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，直接取整
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal divForRoundIng(BigDecimal b1,BigDecimal b2){
        return b1.divide(b2,0,BigDecimal.ROUND_DOWN);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param b 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal b,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 使用BigDecimal，保留小数点后两位
     */
    public static String round2(BigDecimal bd) {

      bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
      return bd.toString();
    }

	public static void main(String[] args) {
		BigDecimal pay = new BigDecimal(0.01);
		System.out.println("pay = " + pay.toString());
		BigDecimal pay_round_2 = MathBigDecimalUtil.round(pay, 2);
		System.out.println("pay保留两位小数 = " + pay_round_2.toString());
		BigDecimal p1 = new BigDecimal(100);
		BigDecimal p2 = MathBigDecimalUtil.mul(pay, p1);
		BigDecimal p3 = MathBigDecimalUtil.round(p2, 0);
		System.out.println("pay乘以100 = " + p3.toString());
	}
}
