package com.ziroom.zrp.service.houses.api;

/**
 * <p>物品相关接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月30日 14:07
 * @since 1.0
 */
public interface ItemsService {
    /**
     * 物品清单
     * @author jixd
     * @created 2017年10月30日 14:10:48
     * @param
     * @return
     */
    String listItems(String itemType,String itemName);
    /**
     * 列出 房间和户型下面的 物品列表  如果房间不存在取户型的
     * @author jixd
     * @created 2017年10月30日 14:12:08
     * @param
     * @return
     */
    String listItemsByRoomIdAndHouseType(String roomId);

    /**
     * 保存房间物品配置
     * @author jixd
     * @created 2017年11月02日 18:16:37
     * @param
     * @return
     */
    String saveRoomItemConfig(String paramJson);
    /**
	 * 根据roomId区分是房间还是床位，然后根据规则（如果是房间，获取房间物品配置，如果没有则获取该户型下的物品配置；
	 * 如果是床位，则获取床位物品配置，如果没有则获取对应户型下的物品配置，再与其父房间的物品取并集）获取对应的物品配置
	 *
	 * @Author: xiangbin
	 * @CreateDate: 2017年11月18日
	 */
    public String getConfigsByRoomId(String roomId);
    /**
	 * <p>查询物品项</p>
	 * @author xiangb
	 * @created 2017年11月20日
	 * @param
	 * @return
	 */
	public String getItemListByFid(String itemId);
	/**
	 * <p>保存或更新物品项</p>
	 * @author xiangb
	 * @created 2017年11月20日
	 * @param
	 * @return
	 */
	public String saveOrUPdateItemList(String param);
	/**
     * 多条件查询房间物品信息
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
	public String selectRoomItemsConfigByParams(String param);
}
