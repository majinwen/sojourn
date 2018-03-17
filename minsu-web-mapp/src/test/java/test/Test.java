package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		Date start= new Date(1466835826);
		Date end = new Date(2097987826);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(format.format(start));
		System.out.println(format.format(end));
		
	}
}
