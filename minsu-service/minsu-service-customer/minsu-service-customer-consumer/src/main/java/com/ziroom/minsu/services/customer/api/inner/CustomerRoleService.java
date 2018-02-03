package com.ziroom.minsu.services.customer.api.inner;

/**
 * <p>用户角色service</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/23.
 * @version 1.0
 * @since 1.0
 */
public interface CustomerRoleService {



    /**
     * 获取当前的所有的角色信
     * 分页查询
     * @author afi
     * @return
     */
    String getBaseRolesByPage(String paramJson);

    /**
     * 获取当前的所有的角色信息
     * @author afi
     * @return
     */
    String getBaseRoles();


    /**
     * 获取当前的所有的角色信息
     * @author afi
     * @return
     */
    String getBaseRolesMap();

    /**
     * 解冻当前用户
     * @author afi
     * @param uid
     * @return
     */
    String unfrozenCustomerRoleByType(String uid,String roleType);


    /**
     * 冻结当前用户
     * @author afi
     * @param uid
     * @return
     */
    String frozenCustomerRoleByType(String uid,String roleType);


     /**
     * 获取当前用户的角色信息
     * @author afi
     * @param uid
     * @return
     */
     String getCustomerRoles(String uid);


    /**
     * 获取当前用户的角色信息
     * @author afi
     * @param uid
     * @return
     */
    String getCustomerRolesMap(String uid);


    /**
     * 保存用户角色信息
     * #author afi
     * @param uid
     * @param roleCode
     * @return
     */
     String saveCustomerRole(String uid,String roleCode);

    /**
     * 校验用户角色信息
     * #author afi
     * @param uid
     * @param roleCode
     * @return
     */
    String checkCustomerRole(String uid,String roleCode);

    /**
     * 
     * 取消客户uid的角色roleCode
     *
     * @author loushuai
     * @created 2017年5月11日 下午2:28:11
     *
     * @param uid
     * @param roleCode
     * @return
     */
    String cancelAngelLandlord(String uid, String roleCode);

    /**
      * 校验是否有申请天使房东的资格
      * @author wangwentao
      * @created 2017/5/19 17:15
      *
      * @param
      * @return
      */
    String checkIsBan(String uid, String roleCode);

    /**
      * 查询用户角色，不包含是否冻结
      * @author wangwentao
      * @created 2017/5/25 18:28
      *
      * @param
      * @return
      */
    String getCustomerRolesMapWithoutFrozen(String uid);
}
