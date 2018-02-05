/**
 * @FileName: GetReturnValueFromCallable.java
 * @Package test
 * 
 * @author yd
 * @created 2017年5月25日 上午9:51:44
 * 
 * Copyright 2011-2015 asura
 */
package test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>线程返回测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class GetReturnValueFromCallable {


	private static final int SLEEP_MILLS = 3000;  


	private static final int SECOND_MILLS = 1000;  


	private static int sleepSeconds = SLEEP_MILLS / SECOND_MILLS;  


	ExecutorService executorService = Executors.newCachedThreadPool();  


	/**
	 * 
	 * 在创建多线程程序的时候，我们常实现Runnable接口，Runnable没有返回值，要想获得返回值，Java5提供了一个新的接口Callable 
	 *
	 * @author yd
	 * @created 2017年5月25日 上午9:52:36
	 *
	 * @param args
	 */
	public static void main(String[] args) {  


		new GetReturnValueFromCallable().testCallable();  
	}  


	private void testCallable() {  


		/** 
		 * Callable需要实现的是call()方法，而不是run()方法，返回值的类型有Callable的类型参数指定， 
		 * Callable只能由ExecutorService.submit() 执行，正常结束后将返回一个future对象 
		 */  
		Future<String> future = executorService.submit(new Callable<String>() {  


			public String call() throws Exception {  


				Thread.sleep(SLEEP_MILLS);  
				return "I from callable";  
			}  
		});  


		while (true) {  
			/** 
			 * 获得future对象之前可以使用isDone()方法检测future是否完成，完成后可以调用get()方法获得future的值， 
			 * 如果直接调用get()方法，get()方法将阻塞值线程结束 
			 */  
			if (future.isDone()) {  
				try {  
					System.out.println(future.get());  
					break;  
				} catch (InterruptedException e) {  
					// ignored  
				} catch (ExecutionException e) {  
					// ignored  
				}  
			}else {  
				try {  
					System.out.println("after " + sleepSeconds-- + " seconds, we will get future");  
					Thread.sleep(SECOND_MILLS);  
				} catch (InterruptedException e) {  
					// ignored  
				}  
			}  
		}  
	}  
}
