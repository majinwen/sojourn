package com.ziroom.minsu.report.common.util;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ziroom.minsu.report.order.entity.OrderEntity;


/**
 * Java反射工具类
 * @author <a href="xdemo.org">xdemo.org</a>
 *
 */
public class ReflectUtils {

    /**
     * 获取成员变量的修饰符
     * @param clazz
     * @param field
     * @return
     * @throws Exception
     */
    public static <T> int getFieldModifier(Class<T> clazz, String field) throws Exception {
        //getDeclaredFields可以获取所有修饰符的成员变量，包括private,protected等getFields则不可以
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(field)) {
                return fields[i].getModifiers();
            }
        }
        throw new Exception(clazz+" has no field \"" + field + "\"");
    }

    /**
     * 获取成员方法的修饰符
     * @param clazz
     * @param method
     * @return
     * @throws Exception
     */
    public static <T> int getMethodModifier(Class<T> clazz, String method) throws Exception {

        //getDeclaredMethods可以获取所有修饰符的成员方法，包括private,protected等getMethods则不可以
        Method[] m = clazz.getDeclaredMethods();

        for (int i = 0; i < m.length; i++) {
            if (m[i].getName().equals(m)) {
                return m[i].getModifiers();
            }
        }
        throw new Exception(clazz+" has no method \"" + m + "\"");
    }

    /**
     *  [对象]根据成员变量名称获取其值
     * @param clazzInstance
     * @param field
     * @return
     * @throws Exception 
     */
    public static <T> Object getFieldValue(Object clazzInstance, Object field) throws Exception {
    	Field[] fields = clazzInstance.getClass().getDeclaredFields();
        
        if(clazzInstance.getClass().getSuperclass()==null){
            throw new Exception(clazzInstance.getClass().getName()+"没有父类");
        }
        
        Field[] superFields = clazzInstance.getClass().getSuperclass().getDeclaredFields();
        Field[] allFields=new Field[fields.length+superFields.length];
        for(int i=0;i<superFields.length;i++){
            allFields[fields.length+i]=superFields[i];
        }
        for(int i=0;i<fields.length;i++){
            allFields[i]=fields[i];
        }
        for (int i = 0; i < allFields.length; i++) {
            if (allFields[i].getName().equals(field)) {
                //对于私有变量的访问权限，在这里设置，这样即可访问Private修饰的变量
            	allFields[i].setAccessible(true);
                return allFields[i].get(clazzInstance);
            }
        }

        return null;
    }

    /**
     *[类]根据成员变量名称获取其值（默认值）
     * @param clazz
     * @param field
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> Object getFieldValue(Class<T> clazz, String field) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {

        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(field)) {
                //对于私有变量的访问权限，在这里设置，这样即可访问Private修饰的变量
                fields[i].setAccessible(true);
                return fields[i].get(clazz.newInstance());
            }
        }

        return null;
    }

    /**
     * 获取所有的成员变量
     * @param clazz
     * @return
     */
    public static <T> String[] getFields(Class<T> clazz) {

        Field[] fields = clazz.getDeclaredFields();

        String[] fieldsArray = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            fieldsArray[i] = fields[i].getName();
        }

        return fieldsArray;
    }

    /**
     * 获取所有的成员变量,包括父类
     * @param clazz
     * @return
     * @throws Exception 
     */
    public static <T> Field[] getClassFieldsAndSuperClassFields(Class<T> clazz) throws Exception {

        Field[] fields = clazz.getDeclaredFields();

        if(clazz.getSuperclass()==null){
            throw new Exception(clazz.getName()+"没有父类");
        }

        Field[] superFields = clazz.getSuperclass().getDeclaredFields();
        Field[] allFields=new Field[fields.length+superFields.length];
        for(int i=0;i<superFields.length;i++){
            allFields[fields.length+i]=superFields[i];
        }
        for(int i=0;i<fields.length;i++){
            allFields[i]=fields[i];
        }
        return allFields;
    }

    /**
     * 指定类，调用指定的无参方法
     * @param clazz
     * @param method
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> Object invoke(Class<T> clazz, String method) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Object instance = clazz.newInstance();
        Method m = clazz.getMethod(method, new Class[] {});
        return m.invoke(instance, new Object[] {});
    }

    /**
     * 通过对象，访问其方法
     * 
     * @param clazzInstance
     * @param method
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> Object invoke(Object clazzInstance, String method) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Method m = clazzInstance.getClass().getMethod(method, new Class[] {});
        return m.invoke(clazzInstance, new Object[] {});
    }

    /**
     * 指定类，调用指定的方法
     * 
     * @param clazz
     * @param method
     * @param paramClasses
     * @param params
     * @return Object
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static <T> Object invoke(Class<T> clazz, String method, Class<T>[] paramClasses, Object[] params) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Object instance = clazz.newInstance();
        Method _m = clazz.getMethod(method, paramClasses);
        return _m.invoke(instance, params);
    }

    /**
     * 通过类的实例，调用指定的方法
     * 
     * @param clazzInstance
     * @param method
     * @param paramClasses
     * @param params
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static <T> Object invoke(Object clazzInstance, String method, Class<T>[] paramClasses, Object[] params) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method _m = clazzInstance.getClass().getMethod(method, paramClasses);
        
        return _m.invoke(clazzInstance, params);
    }

    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
    	
    	OrderEntity orderEntity = new  OrderEntity();
    	orderEntity.setCityCode("北京123");
    	orderEntity.setOrderSn("对象");
    	/*Field[] fieldsTitle = getClassFieldsAndSuperClassFields(OrderEntity.class);
         FieldMeta excel = null;
         for (Field field : fieldsTitle){
	            field.setAccessible(true);
	            excel = field.getAnnotation(FieldMeta.class);
	            if (excel == null) {
	                continue;
	            }
	           String fieldName = field.getName();
	           System.out.println("获取对象的value值:"+getFieldValue(OrderEntity.class,fieldName));
	            // 写入标题
	          System.out.println(excel.name());
	         }*/
        //User u = new User();
        //invoke(u, "setName", new Class[] { String.class }, new Object[] { "xx发大水法大水法x" });
        //System.out.println(getFieldValue(u, "name"));
    	
    	/*Method m2 = orderEntity.getClass().getMethod("setOrderSn", String.class);
    	m2.invoke(orderEntity, "welcome");
    	Method m3 = orderEntity.getClass().getMethod("getOrderSn");
    	System.out.println(m3.invoke(orderEntity));*/
    	Object invoke = invoke(orderEntity, "setOrderSn",new Class[]{String.class},new Object [] {"1234"});
    	System.out.println(invoke(orderEntity, "getOrderSn"));
    	System.out.println(orderEntity.getOrderSn());
    }

}
