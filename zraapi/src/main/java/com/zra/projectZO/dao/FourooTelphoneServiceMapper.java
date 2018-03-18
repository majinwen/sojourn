package com.zra.projectZO.dao;



import com.zra.common.dto.zotel.FourooTelDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 400分机号
 */
@Repository
public interface FourooTelphoneServiceMapper {


    /**
     * 绑定分机号
     */
    int bindPhone(FourooTelDto zo);

    /**
     * 查询分机号
     */
    List<FourooTelDto> queryPhone(String id);

    /**
     * 查询最大分机号
     */
    String queryMaxExtCode();

    /**
     * 解绑分机号
     */
    int unBindPhone(String keeperId);
}
