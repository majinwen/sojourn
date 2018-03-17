package test;

import java.util.Random;

public class Test {
	public static void main(String[] args) {
		/*Random rand = new Random();
		for(int i=1;i<=100;i++){
			int num = rand.nextInt(100)+1;
			System.out.println(num);
		}*/
		for(int i=1;i<=100;i++){
			int[] money = {500,300,100};
			int num = doRamdon();
			System.out.println(money[num]);
		}
	}
	private static int doRamdon() {  
		//直接调用Math.random()是产生一个[0，1）之间的随机数
        double[] ds = new double[] {5.0 , 15.0 , 80.0 };  
        double sum = getSum(ds);  
        double last = 0;  
        for (int i = 0; i < ds.length; i++) {  
            sum = sum- last;  
            double random = Math.random();  
            if (random <= ds[i] / sum) {  
                return i;  
            }  
            last = ds[i];  
        }  
        return 3;  
    }  
  
    private static double getSum(double[] weight) {  
        double sum = 0;  
        for (double d : weight) {  
            sum += d;  
        }  
        return sum;  
    }
    @org.junit.Test
    public void testCoupon(){
//    	System.out.println("dslkjf");
    	String[] actLimit= {"500","700","1000"};
        String[] actCut = {"100","300","500"};
        String[] ratio = {"80","15","5"};
        StringBuilder sb = new StringBuilder();
//        System.out.println(actLimit.length);
        for(int i=0;i < actLimit.length;i++){
        	sb.append(actLimit[i]).append("_").append(actCut[i]).append("_").append(ratio[i]).append("|");
        }
        System.out.println(sb.toString());
    }
}
