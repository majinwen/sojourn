package com.zra.business.logic;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.zra.business.service.BusinessService;

/**
 * 业务层抽象测试类.
 * 目前仅供小范围使用学习
 * @author cuiyh
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractLogicMockTest {

    @Mock
    BusinessService businessService;  
    
    @BeforeClass
    public static void initMocks() {
    }
}
