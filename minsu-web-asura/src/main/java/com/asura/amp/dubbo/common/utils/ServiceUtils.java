package com.asura.amp.dubbo.common.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asura.amp.dubbo.common.entity.Consumer;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.Provider;

@Component
public class ServiceUtils {

	private static PullTool tool;

	@Autowired
	private ServiceUtils(PullTool tool) {
		ServiceUtils.tool = tool;
	}

	public static List<Provider> providerProcesser(List<Provider> providers) {
		for (Provider provider : providers) {
			providerProcesser(provider);
		}

		return providers;
	}

	public static Provider providerProcesser(Provider provider) {
		provider.setWeight(tool.getProviderWeight(provider));
		provider.setEnabled(tool.isProviderEnabled(provider));

		//		if(StringUtil.isNotEmpty(provider.getAddress())) {
		//			//provider.setAddress(PullTool.getSimpleName(provider.getService()));
		//		}

		return provider;
	}

	public static List<Consumer> consumerProcesser(List<Consumer> consumers) {
		for (Consumer consumer : consumers) {
			consumerProcesser(consumer);
		}

		return consumers;
	}

	public static Consumer consumerProcesser(Consumer consumer) {
		consumer.setAllowed(!tool.isInBlackList(consumer));

		String mock = tool.getConsumerMock(consumer);
		if ("force%3Areturn+null".equals(mock)) {
			consumer.setMockType("已屏蔽");
		} else if ("fail%3Areturn+null".equals(mock)) {
			consumer.setMockType("已容错");
		} else {
			consumer.setMockType("未降级");
		}

		//		if (StringUtil.isNotEmpty(consumer.getAddress())) {
		//			//provider.setAddress(PullTool.getSimpleName(provider.getService()));
		//		}

		return consumer;
	}

	public static String applicationMock(List<DynamicConfig> overrides) {
		String mockType = "";
		String mock = tool.getOverridesMock(overrides);
		if ("force%3Areturn+null".equals(mock)) {
			mockType = "已屏蔽";
		} else if ("fail%3Areturn+null".equals(mock)) {
			mockType = "已容错";
		} else {
			mockType = "未降级";
		}

		return mockType;
	}
}
