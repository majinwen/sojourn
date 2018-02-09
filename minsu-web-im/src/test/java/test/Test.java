package test;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.valenum.customer.CustomerEduEnum;

public class Test {
	public static void main(String[] args) {
		CustomerBaseMsgEntity baseEntity = new CustomerBaseMsgEntity();
		baseEntity.setCustomerEdu(1);
		String customerEduName = baseEntity.getCustomerEdu() == null ? "" :(CustomerEduEnum.getCustomerEduByCode(baseEntity.getCustomerEdu()).getName());
		System.out.println(customerEduName);
	}
}
