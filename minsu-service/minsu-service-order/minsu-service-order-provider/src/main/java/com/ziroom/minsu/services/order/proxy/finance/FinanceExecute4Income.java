package com.ziroom.minsu.services.order.proxy.finance;

import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import org.springframework.stereotype.Component;

@Component("order.financeExecute4Income")
public class FinanceExecute4Income extends FinanceExecute {

	public void run(FinanceIncomeEntity financeIncomeEntity) throws Exception {
		// 调账户系统消费冻结
		this.callFrozenAccountIncomeService(financeIncomeEntity);
	}
	
}
