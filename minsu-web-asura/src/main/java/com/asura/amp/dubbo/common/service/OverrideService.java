/*
 * Copyright 1999-2101 Alibaba Group.
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
package com.asura.amp.dubbo.common.service;

import java.util.List;

import com.asura.amp.dubbo.common.entity.DynamicConfig;

/**
 * @author tony.chenl
 */
public interface OverrideService {
    
    void saveOverride(DynamicConfig override);
    
    void updateOverride(DynamicConfig override);

    void deleteOverride(Long id);
    
    void enableOverride(Long id);
    
    void disableOverride(Long id);

    List<DynamicConfig> findByService(String service);
    
    List<DynamicConfig> findByAddress(String address);
    
    List<DynamicConfig> findByServiceAndAddress(String service, String address);
    
    List<DynamicConfig> findByApplication(String application);

    List<DynamicConfig> findByServiceAndApplication(String service, String application);
    
    List<DynamicConfig> findAll();

    DynamicConfig findById(Long id);
    
}
