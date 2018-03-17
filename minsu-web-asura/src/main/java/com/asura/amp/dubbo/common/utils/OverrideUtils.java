/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.asura.amp.dubbo.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.dubbo.common.entity.Consumer;
import com.asura.amp.dubbo.common.entity.DynamicConfig;
import com.asura.amp.dubbo.common.entity.LoadBalance;
import com.asura.amp.dubbo.common.entity.Provider;
import com.asura.amp.dubbo.common.entity.Weight;

public class OverrideUtils {
	public static LoadBalance overrideToLoadBalance(DynamicConfig override) {
		return OverrideUtils.overridesToLoadBalances(Arrays.asList(override)).get(0);
	}

	public static DynamicConfig loadBalanceToOverride(LoadBalance loadBalance) {
		DynamicConfig override = new DynamicConfig();
		override.setId(loadBalance.getId());
		override.setService(loadBalance.getService());
		override.setEnabled(true);
		String method = loadBalance.getMethod();
		String strategy = loadBalance.getStrategy();
		if (StringUtils.isEmpty(method) || method.equals("*")) {
			override.setParams("loadbalance=" + strategy);
		} else {
			override.setParams(method + ".loadbalance=" + strategy);
		}

		return override;
	}

	public static List<LoadBalance> overridesToLoadBalances(List<DynamicConfig> overrides) {
		List<LoadBalance> loadBalances = new ArrayList<LoadBalance>();
		if (overrides == null) {
			return loadBalances;
		}
		for (DynamicConfig o : overrides) {
			if (StringUtils.isEmpty(o.getParams())) {
				continue;
			} else {
				Map<String, String> params = StringUtils.parseQueryString(o.getParams());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (entry.getKey().endsWith("loadbalance")) {
						LoadBalance loadBalance = new LoadBalance();
						String method = null;
						if (entry.getKey().endsWith(".loadbalance")) {
							method = entry.getKey().split(".loadbalance")[0];
						} else {
							method = "*";
						}

						loadBalance.setMethod(method);
						loadBalance.setId(o.getId());
						loadBalance.setService(o.getService());
						loadBalance.setStrategy(entry.getValue());
						loadBalances.add(loadBalance);

					}
				}
			}
		}
		return loadBalances;
	}

	public static Weight overrideToWeight(DynamicConfig override) {
		return overridesToWeights(Arrays.asList(override)).get(0);
	}

	public static DynamicConfig weightToOverride(Weight weight) {
		DynamicConfig override = new DynamicConfig();
		override.setId(weight.getId());
		override.setAddress(weight.getAddress());
		override.setEnabled(true);
		override.setParams("weight=" + weight.getWeight());
		override.setService(weight.getService());
		return override;
	}

	public static List<Weight> overridesToWeights(List<DynamicConfig> overrides) {
		List<Weight> weights = new ArrayList<Weight>();
		if (overrides == null) {
			return weights;
		}
		for (DynamicConfig o : overrides) {
			if (StringUtils.isEmpty(o.getParams())) {
				continue;
			} else {
				Map<String, String> params = StringUtils.parseQueryString(o.getParams());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (entry.getKey().equals("weight")) {
						Weight weight = new Weight();
						weight.setAddress(o.getAddress());
						weight.setId(o.getId());
						weight.setService(o.getService());
						weight.setWeight(Integer.valueOf(entry.getValue()));
						weights.add(weight);
					}
				}
			}
		}
		return weights;
	}

	public static final Comparator<DynamicConfig> OVERRIDE_COMPARATOR = new Comparator<DynamicConfig>() {
		public int compare(DynamicConfig o1, DynamicConfig o2) {
			if (o1 == null && o2 == null) {
				return 0;
			}
			if (o1 == null) {
				return -1;
			}
			if (o2 == null) {
				return 1;
			}
			int cmp = cmp(o1.getAddress(), o2.getAddress());
			if (cmp != 0) {
				return cmp;
			}
			cmp = cmp(o1.getApplication(), o2.getApplication());
			if (cmp != 0) {
				return cmp;
			}
			return cmp(o1.getService(), o2.getService());
		}

		private int cmp(String s1, String s2) {
			if (s1 == null && s2 == null) {
				return 0;
			}
			if (s1 == null) {
				return -1;
			}
			if (s2 == null) {
				return 1;
			}
			if (s1.equals(s2)) {
				return 0;
			}
			if (isAny(s1)) {
				return 1;
			}
			if (isAny(s2)) {
				return -1;
			}
			return s1.compareTo(s2);
		}

		private boolean isAny(String s) {
			return s == null || s.length() == 0 || Constants.ANY_VALUE.equals(s) || Constants.ANYHOST_VALUE.equals(s);
		}
	};

	public static void setConsumerOverrides(Consumer consumer, List<DynamicConfig> overrides) {
		if (consumer == null || overrides == null) {
			return;
		}
		List<DynamicConfig> result = new ArrayList<DynamicConfig>(overrides.size());
		for (DynamicConfig override : overrides) {
			if (!override.isEnabled()) {
				continue;
			}
			if (override.isMatch(consumer)) {
				result.add(override);
			}
			if (override.isUniqueMatch(consumer)) {
				consumer.setOverride(override);
			}
		}
		Collections.sort(result, OverrideUtils.OVERRIDE_COMPARATOR);
		consumer.setOverrides(result);
	}

	public static void setProviderOverrides(Provider provider, List<DynamicConfig> overrides) {
		if (provider == null || overrides == null) {
			return;
		}
		List<DynamicConfig> result = new ArrayList<DynamicConfig>(overrides.size());
		for (DynamicConfig override : overrides) {
			if (!override.isEnabled()) {
				continue;
			}
			if (override.isMatch(provider)) {
				result.add(override);
			}
			if (override.isUniqueMatch(provider)) {
				provider.setOverride(override);
			}
		}
		provider.setOverrides(overrides);
	}

}
