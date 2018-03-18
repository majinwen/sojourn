package com.zra.common.vo.delivery;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>物品目录 明细</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月21日 17:38
 * @since 1.0
 */
public class CatalogItemVo {

    private String name;

    private List<ItemInfo> items = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemInfo> getItems() {
        return items;
    }

    public void setItems(List<ItemInfo> items) {
        this.items = items;
    }

    public class ItemInfo{
        /**
         * 物品名称
         */
        private String name;
        /**
         * 物品价格
         */
        private String price;
        /**
         * 物品梳理
         */
        private Integer num;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

}
