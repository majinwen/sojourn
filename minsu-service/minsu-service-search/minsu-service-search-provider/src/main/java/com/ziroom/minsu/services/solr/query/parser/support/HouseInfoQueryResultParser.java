package com.ziroom.minsu.services.solr.query.parser.support;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.search.LabelTipsEntity;
import com.ziroom.minsu.services.common.constant.BaseConstant;
import com.ziroom.minsu.services.common.constant.SearchConstant;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.search.entity.LabelEntity;
import com.ziroom.minsu.services.search.vo.TonightDiscountInfoVo;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.services.solr.query.parser.AbstractQueryResultParser;
import com.ziroom.minsu.valenum.common.WeekEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.OrderTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.search.IconPicTypeEnum;
import com.ziroom.minsu.valenum.search.LabelTipsEnum;
import com.ziroom.minsu.valenum.search.LabelTipsStyleEnum;
import com.ziroom.minsu.valenum.search.LabelTypeEnum;
import com.ziroom.minsu.valenum.search.SearchSourceTypeEnum;

/**
 * <p>
 * 房源的搜索
 * </p>
 * <p/>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public class HouseInfoQueryResultParser extends AbstractQueryResultParser<HouseInfoEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseInfoQueryResultParser.class);

    private static final String datePatten = "yyMMdd";

    @Override
    protected HouseInfoEntity doParser(Map<String, Object> par, SolrDocument doc) {
        if (Check.NuNObj(doc)) {
            return null;
        }

        Integer searchSourceType = getSearchSourceType(par);

        HouseInfoEntity house = new HouseInfoEntity();
        // 设置当前的城市code
        house.setCityCode(ValueUtil.getStrValue(doc.getFieldValue("cityCode")));
        house.setCityName(ValueUtil.getStrValue(doc.getFieldValue("cityName")));

        int rentWay = ValueUtil.getintValue(doc.getFieldValue("rentWay"));
        if (rentWay == RentWayEnum.HOUSE.getCode()) {
            house.setFid(ValueUtil.getStrValue(doc.getFieldValue("houseId")));
        } else if (rentWay == RentWayEnum.ROOM.getCode()) {
            house.setFid(ValueUtil.getStrValue(doc.getFieldValue("roomId")));
        } else if (rentWay == RentWayEnum.BED.getCode()) {
            house.setFid(ValueUtil.getStrValue(doc.getFieldValue("bedId")));
        } else if (rentWay == RentWayEnum.VIRTUAL.getCode()) {
            house.setFid(ValueUtil.getStrValue(doc.getFieldValue("houseId")));
        }
        house.setHouseFid(ValueUtil.getStrValue(doc.getFieldValue("houseId")));

        // 出租方式
        house.setRentWay(rentWay);
        house.setRentWayName(ValueUtil.getStrValue(doc.getFieldValue("rentWayName")));

        house.setPicUrl(getRealPic(par, doc.getFieldValue("picUrl")));

        // 价格
        setRealPrice(par, doc, house);
        //今日特惠
        dealTonightInfo(par, doc, house);

        // 设置当前的房租是否可租
        house.setIsAvailable(getIsAvaliable(par, doc));
        house.setOccupyDays(getOccupyDays(doc));

        house.setHotRegin(getHotRegin(doc));

        house.setPersonCount(getPersonCount(ValueUtil.getintValue(doc.getFieldValue("personCount"))));
        house.setRoomCount(ValueUtil.getintValue(doc.getFieldValue("roomCount")));
        house.setOrderType(ValueUtil.getintValue(doc.getFieldValue("orderType")));
        house.setOrderTypeName(ValueUtil.getStrValue(doc.getFieldValue("orderTypeName")));
        house.setHouseQualityGrade(ValueUtil.getStrValue(doc.getFieldValue("houseQualityGrade")));
        house.setHouseName(ValueUtil.getStrValue(doc.getFieldValue("houseName")));
        house.setNickName(ValueUtil.getStrValue(doc.getFieldValue("nickName")));
        house.setIsLock(ValueUtil.getintValue(doc.getFieldValue("isLock")));
        List<Map> beds = new ArrayList<>();
        Collection bedList = doc.getFieldValues("bedList");
        if (!Check.NuNCollection(bedList)) {
            for (Iterator<String> iterator = bedList.iterator(); iterator.hasNext(); ) {
                String element = iterator.next();
                int spLength = element.indexOf(BaseConstant.split, 1);
                int nuLength = element.indexOf(BaseConstant.split, 2);
                if (spLength > 0) {
                    Map<String, String> ele = new HashMap<>();
                    String type = element.substring(0, spLength);
                    String num = element.substring(spLength + 1, nuLength);
                    String name = element.substring(nuLength + 1);
                    ele.put("type", type);
                    ele.put("name", name);
                    ele.put("num", num);
                    beds.add(ele);
                }
            }
        }

        house.setIsTop50Online(ValueUtil.getintValue(doc.getFieldValue("isTop50Online")));
        house.setTop50Title(ValueUtil.getStrValue(doc.getFieldValue("top50Title")));

        house.setIndivLabelTipsList(getIndivLabelTipsList(doc));

        house.setBedList(beds);
        house.setToiletCount(ValueUtil.getintValue(doc.getFieldValue("toiletCount")));
        house.setIsToilet(ValueUtil.getintValue(doc.getFieldValue("isToilet")));
        house.setBalconyCount(ValueUtil.getintValue(doc.getFieldValue("balconyCount")));
        house.setIsBalcony(ValueUtil.getintValue(doc.getFieldValue("isBalcony")));

        house.setUpdateTime(ValueUtil.getlongValue(doc.getFieldValue("refreshDate")));
        house.setLandlordUrl(ValueUtil.getStrValue(doc.getFieldValue("landlordUrl")));
        house.setLandlordUid(ValueUtil.getStrValue(doc.getFieldValue("landlordUid")));
        house.setWeights(ValueUtil.getlongValue(doc.getFieldValue("weights")));
        house.setDist(ValueUtil.getStrValue(doc.getFieldValue("dist")));
        house.setEvaluateCount(ValueUtil.getintValue(doc.getFieldValue("evaluateCount")));
        house.setEvaluateScore(
                ValueUtil.getEvaluteSoreDefault(ValueUtil.getStrValue(doc.getFieldValue("evaluateScore"))));
        // 真实评分
        house.setRealEvaluateScore(
                ValueUtil.getRealEvaluteSore(ValueUtil.getStrValue(doc.getFieldValue("realEvaluateScore"))));
        house.setLoc(ValueUtil.getStrValue(doc.getFieldValue("loc")));
        // 设置当前的房源是否被收藏
        house.setIsCollect(getIsCollect(par, doc));
        house.setIsNew(ValueUtil.getintValue(doc.getFieldValue("isNew")));
        house.setHouseType(ValueUtil.getintValue(doc.getFieldValue("houseType")));
        house.setHouseTypeName(ValueUtil.getStrValue(doc.getFieldValue("houseTypeName")));
        if (house.getHouseType() == 3) {// 公寓不显示
            house.setHouseTypeName("");
        }
        house.setHouseAddr("");
        if (!Check.NuNObj(searchSourceType) && (searchSourceType == SearchSourceTypeEnum.troy_search_one.getCode() || searchSourceType == SearchSourceTypeEnum.troy_search_list.getCode())) {
            house.setHouseAddr(ValueUtil.getStrValue(doc.getFieldValue("houseAddr")));
            house.setAcceptOrder60DaysCount(ValueUtil.getlongValue(doc.getFieldValue("acceptOrder60DaysCount")));
            house.setOrder60DaysCount(ValueUtil.getlongValue(doc.getFieldValue("order60DaysCount")));
            house.setWeightsComposition(ValueUtil.getStrValue(doc.getFieldValue("weightsComposition")));
            house.setScore(ValueUtil.getdoubleValue(doc.getFieldValue("score")));
        }

        // 获取当前的标签信息
        house.setLabelInfo(getLable(par, doc));
        //以下两个列表顺序不能变，后一个列表是否添加取决于前一个
        house.setLabelTipsList(getLabelTipsEntityList(par, doc));
        house.setLabelInfoList(getLableList(par, doc));
        dealLableAndTips(par, house, doc);

        return house;
    }


    /**
     * 房源个性化标签
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年3月21日 下午5:20:29
     */
    private List<String> getIndivLabelTipsList(SolrDocument solrDocument) {

        List<String> list = new ArrayList<>();

        Collection<Object> tags = solrDocument.getFieldValues("indivLabelTipsList");
        // 是否特殊价格
        if (!Check.NuNCollection(tags)) {
            for (Object ele : tags) {
                String eleStr = ValueUtil.getStrValue(ele);
                list.add(eleStr);
            }
        }

        return list;
    }

    /**
     * 处理最终的标签显示规则
     *
     * @param house
     * @author zl
     * @created 2017年3月14日 上午10:33:15
     */
    private void dealLableAndTips(Map<String, Object> par, HouseInfoEntity house, SolrDocument solrDocument) {
        if (Check.NuNObj(house)) {
            return;
        }

        List<LabelEntity> lableList = house.getLabelInfoList();

        if (!Check.NuNCollection(lableList)) {
            LabelEntity newlable = null;
            for (LabelEntity lable : lableList) {
                if (lable.getCode().trim().equals(LabelTypeEnum.IS_NEW_V2.getCode())) {
                    newlable = lable;
                    break;
                }
            }

            if (!Check.NuNObj(newlable)) {
                LabelTipsEntity labelEntity = new LabelTipsEntity();
                labelEntity.setIndex(house.getLabelTipsList().size() + 1);
                labelEntity.setName(LabelTipsEnum.IS_NEW.getName());
                labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());

                List<LabelTipsEntity> tipsList = house.getLabelTipsList();
                if (tipsList == null) {
                    tipsList = new ArrayList<>();
                }
                tipsList.add(labelEntity);

                house.setLabelTipsList(tipsList);
            }
        }

        Integer versionCode = getVersionCode(par);

        if (!Check.NuNCollection(house.getLabelTipsList())) {
            Iterator<LabelTipsEntity> iterator = house.getLabelTipsList().iterator();

            while (iterator.hasNext()) {
                LabelTipsEntity labelTipsEntity = iterator.next();
                if (labelTipsEntity.getName().equals(LabelTipsEnum.IS_TODAY_DISCOUNT.getName())
                        && (!isTonightDisActive(solrDocument) || (!Check.NuNObj(versionCode) && versionCode >= 100015))) {
                    iterator.remove();
                }

            }

        }


        if (Check.NuNCollection(house.getLabelTipsList())) {
            hiddenOriginalPrice(house, solrDocument);
            return;
        }

        List<LabelTipsEntity> appButtonLabelTipsList = new ArrayList<>();
        List<LabelTipsEntity> otherLabelTipsList = new ArrayList<>();
        for (LabelTipsEntity labelTipsEntity : house.getLabelTipsList()) {
            if (labelTipsEntity.getTipsType().equals(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode())) {
                appButtonLabelTipsList.add(labelTipsEntity);
            } else {
                otherLabelTipsList.add(labelTipsEntity);
            }
        }

        int limit = 2;
        if (appButtonLabelTipsList.size() > limit) {
            appButtonLabelTipsList = appButtonLabelTipsList.subList(0, limit);
        }
        appButtonLabelTipsList.addAll(otherLabelTipsList);
        house.setLabelTipsList(appButtonLabelTipsList);
        hiddenOriginalPrice(house, solrDocument);
    }

    /**
     * 隐藏原价
     *
     * @author zl
     * @created 2017年3月14日 下午3:43:34
     */
    private void hiddenOriginalPrice(HouseInfoEntity house, SolrDocument solrDocument) {

        Integer originalPrice = house.getOriginalPrice();
        house.setOriginalPrice(null);

        //是否显示原价
        boolean showOriginalPrice = false;

        for (LabelTipsEntity labelTipsEntity : house.getLabelTipsList()) {
            if (labelTipsEntity.getName().equals(LabelTipsEnum.IS_TODAY_DISCOUNT.getName())
                    || labelTipsEntity.getName().equals(LabelTipsEnum.IS_JIAXIN_DISCOUNT1.getName())
                    || labelTipsEntity.getName().equals(LabelTipsEnum.IS_JIAXIN_DISCOUNT2.getName())) {
                showOriginalPrice = true;
            }
        }
        if (!Check.NuNObj(originalPrice) && (showOriginalPrice ||
                (!Check.NuNObj(house.getIsToNightDiscount()) && house.getIsToNightDiscount() == YesOrNoEnum.YES.getCode()
                        && isTonightDisActive(solrDocument))
        )) {
            house.setOriginalPrice(originalPrice);
        }

    }


    /**
     * 获取当前的人数限制
     *
     * @param personCount
     * @return
     * @author afi
     */
    private int getPersonCount(int personCount) {
        if (personCount == SolrConstant.default_person_count) {
            return SolrConstant.default_person_count_show;
        } else {
            return personCount;
        }
    }

    /**
     * 获取当前的房源的标签信息（老版本）
     *
     * @param par
     * @param doc
     * @return
     * @author afi
     */
    private static LabelEntity getLable(Map<String, Object> par, SolrDocument doc) {
        int isNew = ValueUtil.getintValue(doc.getFieldValue("isNew"));
        int orderType = ValueUtil.getintValue(doc.getFieldValue("orderType"));
        if (orderType == OrderTypeEnum.CURRENT.getCode()) {
            LabelEntity labelEntity = new LabelEntity();
            labelEntity.setCode(LabelTypeEnum.IS_SPEED.getCode());
            labelEntity.setName(LabelTypeEnum.IS_SPEED.getName());
            labelEntity.setIconUrl(getRealIcon(par, LabelTypeEnum.IS_SPEED));
            return labelEntity;
        } else if (isNew == YesOrNoEnum.YES.getCode()) {
            LabelEntity labelEntity = new LabelEntity();
            labelEntity.setCode(LabelTypeEnum.IS_NEW.getCode());
            labelEntity.setName(LabelTypeEnum.IS_NEW.getName());
            labelEntity.setIconUrl(getRealIcon(par, LabelTypeEnum.IS_NEW));
            return labelEntity;
        }
        return null;
    }

    /**
     * 获取当前的房源的标签信息列表
     *
     * @param par
     * @param doc
     * @return
     * @author zl
     */
    private static List<LabelEntity> getLableList(Map<String, Object> par, SolrDocument doc) {

        List<LabelEntity> list = new ArrayList<>();

        int isNew = ValueUtil.getintValue(doc.getFieldValue("isNew"));
        int orderType = ValueUtil.getintValue(doc.getFieldValue("orderType"));
        int index = 1;

        if (orderType == OrderTypeEnum.CURRENT.getCode()) {
            LabelEntity labelEntity = new LabelEntity();
            labelEntity.setCode(LabelTypeEnum.IS_SPEED_V3.getCode());
            labelEntity.setName(LabelTypeEnum.IS_SPEED_V3.getName());
            labelEntity.setIconUrl(getRealIcon(par, LabelTypeEnum.IS_SPEED_V3));

            labelEntity.setIndex(index);
            list.add(labelEntity);
            index++;
        }
        if (isNew == YesOrNoEnum.YES.getCode()) {
            LabelEntity labelEntity = new LabelEntity();
            labelEntity.setCode(LabelTypeEnum.IS_NEW_V2.getCode());
            labelEntity.setName(LabelTypeEnum.IS_NEW_V2.getName());
            labelEntity.setIconUrl(getRealIcon(par, LabelTypeEnum.IS_NEW_V2));
            labelEntity.setIndex(index);
            list.add(labelEntity);
            index++;
        }

        return list;
    }

    /**
     * 获取提示标签
     *
     * @param par
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年3月7日 下午2:53:58
     */
    private static List<LabelTipsEntity> getLabelTipsEntityList(Map<String, Object> par, SolrDocument solrDocument) {

        List<LabelTipsEntity> list = new ArrayList<>();

        int index = 1;

        //是否top50已经上线的房源
        int isTop50Online = ValueUtil.getintValue(solrDocument.getFieldValue("isTop50Online"));
        if (isTop50Online == YesOrNoEnum.YES.getCode()) {
            LabelTipsEntity labelEntity = new LabelTipsEntity();
            labelEntity.setIndex(index);
            labelEntity.setName(LabelTipsEnum.IS_TOP50.getName());
            labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
            list.add(labelEntity);
            index++;
        }

        int isLandTogether = ValueUtil.getintValue(solrDocument.getFieldValue("isLandTogether"));
        if (isLandTogether == YesOrNoEnum.YES.getCode()) {
            LabelTipsEntity labelEntity = new LabelTipsEntity();
            labelEntity.setIndex(index);
            labelEntity.setName(LabelTipsEnum.IS_LANDTOGETHER.getName());
            labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
            list.add(labelEntity);
            index++;
        }

        // 长租标签
        Map<String, Double> longTermLeaseDiscountMap = getLongTermLeaseDiscountMap(solrDocument);
        if (!Check.NuNMap(longTermLeaseDiscountMap)) {

            if (!Check.NuNObj(longTermLeaseDiscountMap.get(LabelTipsEnum.IS_WEEK_DISCOUNT.getCode()))) {
                LabelTipsEntity labelEntity = new LabelTipsEntity();
                labelEntity.setIndex(index);
                Double discount = longTermLeaseDiscountMap.get(LabelTipsEnum.IS_WEEK_DISCOUNT.getCode());
                if (!Check.NuNObj(discount)) {
                    discount = BigDecimalUtil.div(discount, 10, 1);
                }
                String discountStr = String.valueOf(discount);
                if (discountStr.length() > 3) {
                    discountStr = discountStr.substring(0, 3);
                }

                labelEntity.setName(String.format(LabelTipsEnum.IS_WEEK_DISCOUNT.getName(), discountStr));
                labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
                list.add(labelEntity);
                index++;
            }
            if (!Check.NuNObj(longTermLeaseDiscountMap.get(LabelTipsEnum.IS_MONTH_DISCOUNT.getCode()))) {
                LabelTipsEntity labelEntity = new LabelTipsEntity();
                labelEntity.setIndex(index);
                Double discount = longTermLeaseDiscountMap.get(LabelTipsEnum.IS_MONTH_DISCOUNT.getCode());
                if (!Check.NuNObj(discount)) {
                    discount = BigDecimalUtil.div(discount, 10, 1);
                }
                String discountStr = String.valueOf(discount);
                if (discountStr.length() > 3) {
                    discountStr = discountStr.substring(0, 3);
                }
                labelEntity.setName(String.format(LabelTipsEnum.IS_MONTH_DISCOUNT.getName(), discountStr));
                labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
                list.add(labelEntity);
                index++;
            }
        }

        Map<String, Double> flexiblePriceMap = flexiblePriceMap(solrDocument);
        if (isTonightDisActive(solrDocument)) {// 当天
            Double tdaydiscount = flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020001.getValue());
            if (!Check.NuNObj(tdaydiscount)) {
                LabelTipsEntity labelEntity = new LabelTipsEntity();
                labelEntity.setIndex(index);
                labelEntity.setName(LabelTipsEnum.IS_TODAY_DISCOUNT.getName());
                labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
                list.add(labelEntity);
                index++;
            }
        }

        return list;
    }

    /**
     * 校验当前的房源是否被收藏
     *
     * @param par
     * @param solrDocument
     * @return
     * @author afi
     */
    private int getIsCollect(Map<String, Object> par, SolrDocument solrDocument) {
        if (Check.NuNMap(par)) {
            return YesOrNoEnum.NO.getCode();
        }
        if (!par.containsKey("collectMap")) {
            return YesOrNoEnum.NO.getCode();
        }
        Map<String, String> collectMap = (Map<String, String>) par.get("collectMap");
        String id = ValueUtil.getStrValue(solrDocument.getFieldValue("id"));
        // 如果收藏列表为空，直接返回未收藏
        if (Check.NuNMap(collectMap)) {
            return YesOrNoEnum.NO.getCode();
        }

        if (collectMap.containsKey(id)) {
            return YesOrNoEnum.YES.getCode();
        } else {
            return YesOrNoEnum.NO.getCode();
        }
    }

    /**
     * 校验当前房源是否在当前时间段内可租
     *
     * @param par
     * @param solrDocument
     * @return
     * @author afi
     */
    private static int getIsAvaliable(Map<String, Object> par, SolrDocument solrDocument) {
        Set<String> daySet = (Set<String>) par.get("daySet");
        if (Check.NuNCollection(daySet)) {
            return YesOrNoEnum.YES.getCode();
        }

        Collection<Object> days = solrDocument.getFieldValues("occupyDays");
        Map<String, String> dayMap = new HashMap<>(days.size());
        if (!Check.NuNCollection(days)) {
            for (Object ele : days) {
                String eleStr = ele.toString();
                dayMap.put(eleStr, eleStr);
            }
        }
        for (String day : daySet) {
            if (!dayMap.containsKey(day)) {
                return YesOrNoEnum.YES.getCode();
            }
        }
        return YesOrNoEnum.NO.getCode();
    }

    /**
     * 获取开始时间
     *
     * @param par
     * @return
     * @author zl
     * @created 2017年3月16日 下午6:42:09
     */
    private static String getStartTime(Map<String, Object> par) {
        String startTime = ValueUtil.getStrValue(par.get("startTime"));
        if (Check.NuNStr(startTime)) {
            startTime = DateUtil.dateFormat(new Date());
        }
        return startTime;
    }

    /**
     * 获取搜索起始时间
     *
     * @param par
     * @return
     * @author zl
     * @created 2017年3月7日 下午4:37:05
     */
    private static Date getStartDate(Map<String, Object> par) {
        String startTime = getStartTime(par);
        Date startDate = null;
        if (!Check.NuNStr(startTime)) {
            try {
                startDate = DateUtil.parseDate(startTime, "yyyy-MM-dd");
            } catch (Exception e) {
                LogUtil.info(LOGGER, "startTime={}, parse error:{}", startTime, e);
            }
        }
        return startDate;
    }

    /**
     * 获取搜索的结束时间
     *
     * @param par
     * @return
     * @author zl
     * @created 2017年3月7日 下午4:44:33
     */
    private static Date getEndTime(Map<String, Object> par) {
        String endTime = ValueUtil.getStrValue(par.get("endTime"));
        Date endDate = null;
        if (!Check.NuNStr(endTime)) {
            try {
                endDate = DateUtil.parseDate(endTime, "yyyy-MM-dd");
            } catch (Exception e) {
                LogUtil.info(LOGGER, "endTime={},parse error:{}", endTime, e);
            }
        }
        return endDate;
    }

    /**
     * 获取灵活定价配置
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年3月7日 下午4:54:55
     */
    private static Map<String, Double> flexiblePriceMap(SolrDocument solrDocument) {
        Collection<Object> flexiblePrice = solrDocument.getFieldValues("flexiblePrice");
        Map<String, Double> flexiblePriceMap = new HashMap<>();
        if (!Check.NuNCollection(flexiblePrice)) {
            for (Object object : flexiblePrice) {
                if (Check.NuNStr(ValueUtil.getStrValue(object))) {
                    continue;
                }
                String[] tem = ValueUtil.getStrValue(object).split(",");
                if (!Check.NuNObj(tem) && tem.length == 2) {
                    String key = tem[0];
                    Double val = ValueUtil.getdoubleValue(tem[1]);
                    if (val > 0 && !Check.NuNStr(key)) {
                        flexiblePriceMap.put(key, val);
                    }

                }
            }
        }

        return flexiblePriceMap;

    }

    /**
     * 获取长租折扣配置
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年3月7日 下午5:20:53
     */
    private static Map<String, Double> getLongTermLeaseDiscountMap(SolrDocument solrDocument) {
        Collection<Object> longTermLeaseDiscount = solrDocument.getFieldValues("longTermLeaseDiscount");
        Map<String, Double> longTermLeaseDiscountMap = new HashMap<>();
        if (!Check.NuNCollection(longTermLeaseDiscount)) {
            for (Object object : longTermLeaseDiscount) {
                if (Check.NuNStrStrict(ValueUtil.getStrValue(object))) {
                    continue;
                }
                String[] tem = ValueUtil.getStrValue(object).split(",");
                if (!Check.NuNObj(tem) && tem.length == 2) {
                    String key = tem[0];
                    Double val = ValueUtil.getdoubleValue(tem[1]);
                    if (val > 0 && !Check.NuNStr(key)) {
                        longTermLeaseDiscountMap.put(key, val);
                    }

                }
            }
        }

        return longTermLeaseDiscountMap;
    }

    /**
     * 获取夹心日期
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年3月7日 下午4:59:26
     */
    private static List<String> getPriorityDays(SolrDocument solrDocument) {
        Collection<Object> priorityDate = solrDocument.getFieldValues("priorityDate");
        List<String> priorityDays = new ArrayList<>();
        if (!Check.NuNCollection(priorityDate)) {
            for (Object object : priorityDate) {
                if (!Check.NuNObj(object)) {
                    String day = String.valueOf(object);
                    if (!Check.NuNStr(day)) {
                        priorityDays.add(day);
                    }
                }
            }
        }

        if (!Check.NuNCollection(priorityDays)) {

            Collections.sort(priorityDays, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (Check.NuNStr(o1) || Check.NuNStr(o2)) {
                        return 0;
                    }
                    try {
                        Date day1 = DateUtil.parseDate(o1, datePatten);
                        Date day2 = DateUtil.parseDate(o2, datePatten);
                        return day1.compareTo(day2);
                    } catch (ParseException e) {
                        LogUtil.error(LOGGER, "夹心日期转化错误，e={}", e);
                    }
                    return 0;
                }
            });

        }

        return priorityDays;
    }

    /**
     * 获取夹心日期map
     *
     * @param priorityDays
     * @return
     * @author zl
     * @created 2017年3月7日 下午5:04:39
     */
    private static Map<String, List<String>> priorityDaysMap(List<String> priorityDays) {
        Map<String, List<String>> priorityDaysMap = new HashMap<>();

        if (!Check.NuNCollection(priorityDays)) {

            List<List<String>> jaxinListList = new ArrayList<>();
            List<String> jaxinList = new ArrayList<>();
            for (int i = 0; i < priorityDays.size(); i++) {
                String day = priorityDays.get(i);
                if (Check.NuNStr(day)) {
                    continue;
                }
                try {
                    Date date = DateUtil.parseDate(day, datePatten);
                    if (i > 0) {
                        String yesterDayStr = priorityDays.get(i - 1);
                        if (Check.NuNStr(yesterDayStr)) {
                            continue;
                        }
                        Date yesterday = DateUtil.parseDate(yesterDayStr, datePatten);
                        if (date.before(yesterday)) {
                            continue;
                        }
                        if (DateSplitUtil.countDateSplit(yesterday, date) > 1) {
                            jaxinListList.add(jaxinList);
                            jaxinList = new ArrayList<>();
                        }
                    }

                    jaxinList.add(day);

                    if (i == priorityDays.size() - 1) {
                        jaxinListList.add(jaxinList);
                    }

                } catch (Exception e) {
                    LogUtil.error(LOGGER, "夹心日期转化错误，day={}，e={}", day, e);
                }
            }

            for (int i = 0; i < jaxinListList.size(); i++) {
                jaxinList = jaxinListList.get(i);
                if (!Check.NuNCollection(jaxinList)) {
                    for (String day : jaxinList) {
                        priorityDaysMap.put(day, jaxinList);
                    }
                }
            }

        }

        return priorityDaysMap;
    }

    /**
     * 热门区域
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年6月1日 上午10:10:24
     */
    private static List<String> getHotRegin(SolrDocument solrDocument) {
        Collection<Object> hotRegins = solrDocument.getFieldValues("hotRegin");
        List<String> results = new ArrayList<>();
        if (!Check.NuNCollection(hotRegins)) {
            for (Object object : hotRegins) {
                if (Check.NuNStrStrict(ValueUtil.getStrValue(object))) {
                    continue;
                }
                results.add(ValueUtil.getStrValue(object));
            }
        }

        return results;
    }


    /**
     * 获取锁定日期
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年3月7日 下午5:07:27
     */
    private static List<String> getOccupyDays(SolrDocument solrDocument) {
        Collection<Object> occupyDate = solrDocument.getFieldValues("occupyDays");
        List<String> occupyDays = new ArrayList<>();
        if (!Check.NuNCollection(occupyDate)) {
            for (Object object : occupyDate) {
                if (Check.NuNStrStrict(ValueUtil.getStrValue(object))) {
                    continue;
                }
                occupyDays.add(ValueUtil.getStrValue(object));
            }
        }

        return occupyDays;
    }

    /**
     * 获取夹心日期最大天数
     *
     * @param par
     * @return
     * @author zl
     * @created 2017年3月7日 下午5:43:50
     */
    private static Integer getJianxiDaysLimit(Map<String, Object> par) {
        return ValueUtil.getintValue(par.get(ProductRulesEnum0019.ProductRulesEnum0019003.getValue()));
    }

    /**
     * 房源基础价格
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年5月11日 上午11:46:24
     */
    private static Integer getBasePrice(SolrDocument solrDocument) {
        return ValueUtil.getintValue(solrDocument.getFieldValue("price"));
    }

    /**
     * 房源特殊价格
     *
     * @param par
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年5月11日 上午11:48:10
     */
    private static Integer getSpecialPrice(Map<String, Object> par, SolrDocument solrDocument) {
        String startTime = getStartTime(par);
        Date startDate = getStartDate(par);

        int price = getBasePrice(solrDocument);
        Collection<Object> prices = solrDocument.getFieldValues("prices");
        // 是否特殊价格
        boolean spPrice = false;
        if (!Check.NuNCollection(prices)) {
            for (Object ele : prices) {
                String eleStr = ele.toString();
                String[] priceInf = eleStr.split(",");
                if (priceInf.length == 2) {
                    String start = priceInf[0];
                    String money = priceInf[1];
                    if (startTime.equals(start)) {
                        price = ValueUtil.getintValue(money);
                        spPrice = true;
                        break;
                    }
                }
            }
        }
        // 获取周末价格
        Collection<Object> weekPrices = solrDocument.getFieldValues("weekPrices");
        if (!Check.NuNCollection(weekPrices) && !spPrice) {
            // week price
            try {
                WeekEnum weekEnum = WeekEnum.getWeek(startDate);
                for (Object ele : weekPrices) {
                    String eleStr = ele.toString();
                    String[] priceInf = eleStr.split(",");
                    if (priceInf.length == 2) {
                        String start = priceInf[0];
                        String money = priceInf[1];
                        if (ValueUtil.getStrValue(weekEnum.getNumber()).equals(start)) {
                            price = ValueUtil.getintValue(money);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.info(LOGGER, "start time is error:{}", startTime);
            }
        }

        price = price / 100 * 100;

        return price;
    }

    /**
     * 处理今日特惠
     *
     * @param solrDocument
     * @param house
     * @author zl
     * @created 2017年5月11日 上午11:35:38
     */
    private static void dealTonightInfo(Map<String, Object> par, SolrDocument solrDocument, HouseInfoEntity house) {

        // 房源配置的灵活定价
        Map<String, Double> flexiblePriceMap = flexiblePriceMap(solrDocument);

        Double tdaydiscount = flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020001.getValue());

        // 锁定日期
        List<String> occupyDays = getOccupyDays(solrDocument);

        if (!Check.NuNObj(tdaydiscount) && (Check.NuNCollection(occupyDays)
                || !occupyDays.contains(DateUtil.dateFormat(new Date(), datePatten)))) {

            TonightDiscountInfoVo tonightDiscountInfoVo = new TonightDiscountInfoVo();
            tonightDiscountInfoVo.setTipsNname(LabelTipsEnum.IS_TODAY_DISCOUNT.getName());

            Date activeTime = getTonightDisOpenDate(solrDocument);

            String symbol = "-";
            String blank = " ";

            if (!Check.NuNObj(activeTime)) {
                long remain = 0;
                if (!Check.NuNObj(activeTime)) {
                    remain = activeTime.getTime() - System.currentTimeMillis();
                }

                if (remain < 0) {
                    remain = 0;
                }
                tonightDiscountInfoVo.setRemainTime(remain);

                String openTimeStr = DateUtil.dateFormat(activeTime, "HH:mm:ss").substring(0, 5);
                tonightDiscountInfoVo.setOpenTime(openTimeStr);

                String openTimeTips = ValueUtil
                        .getStrValue(par.get(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_STARTTIME_TIPS));
                tonightDiscountInfoVo.setOpenTimeTips(openTimeTips);

                String openTimeListTips = ValueUtil
                        .getStrValue(par.get(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_STARTTIME_LIST_TIPS));
                tonightDiscountInfoVo.setOpenTimeListTips(symbol + blank + openTimeStr + openTimeListTips + blank + symbol);
            }

            Date tonightDisDeadlineDate = getTonightDisDeadlineDate(solrDocument);
            if (!Check.NuNObj(tonightDisDeadlineDate)) {

                long remain = 0;
                if (!Check.NuNObj(tonightDisDeadlineDate)) {
                    remain = tonightDisDeadlineDate.getTime() - System.currentTimeMillis();
                }

                if (remain < 0) {
                    remain = 0;
                }

                tonightDiscountInfoVo.setDeadlineRemainTime(remain);

                tonightDiscountInfoVo.setDeadlineTime(DateUtil.dateFormat(tonightDisDeadlineDate, "HH:mm:ss").substring(0, 5));

                String endTimeTips = ValueUtil
                        .getStrValue(par.get(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ENDTIME_TIPS));
                tonightDiscountInfoVo.setDeadlineTimeTips(endTimeTips);

                String endTimeListTips = ValueUtil
                        .getStrValue(par.get(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ENDTIME_LIST_TIPS));
                tonightDiscountInfoVo.setDeadlineTimeListTips(symbol + blank + endTimeListTips + blank + symbol);
            }

            int price = getSpecialPrice(par, solrDocument);
            price = Double.valueOf(price * tdaydiscount).intValue();
            price = price / 100 * 100;

            tonightDiscountInfoVo.setTonightPrice(price);

            // 折扣值 20170906
            tonightDiscountInfoVo.setTonightDiscount(tdaydiscount);


            house.setTonightDiscountInfoVo(tonightDiscountInfoVo);

            house.setIsToNightDiscount(YesOrNoEnum.YES.getCode());
            if (!Check.NuNObj(tonightDisDeadlineDate) && new Date().after(tonightDisDeadlineDate)) {
                house.setIsToNightDiscount(YesOrNoEnum.NO.getCode());
            }

        }

    }

    /**
     * 今日特惠是否已经生效
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年5月17日 上午11:16:22
     */
    private static boolean isTonightDisActive(SolrDocument solrDocument) {
        boolean active = false;
        Date activeTime = getTonightDisOpenDate(solrDocument);
        Date tonightDisDeadlineDate = getTonightDisDeadlineDate(solrDocument);
        Date now = new Date();

        if (!Check.NuNObj(activeTime) && !Check.NuNObj(tonightDisDeadlineDate) && now.after(activeTime) && now.before(tonightDisDeadlineDate)) {
            active = true;
        }

        return active;
    }

    /**
     * 今夜特价开始时间
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年5月15日 下午5:01:07
     */
    private static Date getTonightDisOpenDate(SolrDocument solrDocument) {

        if (!Check.NuNObj(flexiblePriceMap(solrDocument).get(ProductRulesEnum020.ProductRulesEnum020001.getValue()))
                && !Check.NuNObj(solrDocument.getFieldValue("tonightDisOpenDate"))) {
            long value = ValueUtil.getlongValue(solrDocument.getFieldValue("tonightDisOpenDate"));
            if (value != 0) {
                return new Date(value);
            }
        }

        return null;
    }

    /**
     * 今夜特价截止时间
     *
     * @param solrDocument
     * @return
     * @author zl
     * @created 2017年5月15日 下午5:01:43
     */
    private static Date getTonightDisDeadlineDate(SolrDocument solrDocument) {

        if (!Check.NuNObj(flexiblePriceMap(solrDocument).get(ProductRulesEnum020.ProductRulesEnum020001.getValue()))
                && !Check.NuNObj(solrDocument.getFieldValue("tonightDisDeadlineDate"))) {
            long value = ValueUtil.getlongValue(solrDocument.getFieldValue("tonightDisDeadlineDate"));
            if (value != 0) {
                return new Date(value);
            }
        }

        return null;
    }


    /**
     * 版本号
     *
     * @param par
     * @return
     * @author zl
     * @created 2017年5月11日 下午3:43:30
     */
    private static Integer getVersionCode(Map<String, Object> par) {
        Integer versionCode = null;
        if (!Check.NuNObj(par.get("versionCode"))) {
            versionCode = ValueUtil.getintValue(par.get("versionCode"));
        }
        return versionCode;

    }


    /**
     * 搜索入口
     *
     * @param par
     * @return
     * @author zl
     * @created 2017年5月11日 下午3:40:57
     */
    private static Integer getSearchSourceType(Map<String, Object> par) {
        Integer searchSourceType = null;
        if (!Check.NuNObj(par.get("searchSourceType"))) {
            searchSourceType = ValueUtil.getintValue(par.get("searchSourceType"));
        }
        return searchSourceType;
    }


    /**
     * 获取房源的价格 如果有开始时间返回开始时间的价格
     *
     * @param par
     * @param solrDocument
     * @return
     * @author afi
     */
    private static void setRealPrice(Map<String, Object> par, SolrDocument solrDocument, HouseInfoEntity house) {


        int price = getSpecialPrice(par, solrDocument);
        int originalPrice = price;

        Date startDate = getStartDate(par);
        Date endDate = getEndTime(par);

        Integer longTerm7DaysLimit = ValueUtil
                .getintValue(par.get(ProductRulesEnum0019.ProductRulesEnum0019001.getValue()));
        Integer longTerm30DaysLimit = ValueUtil
                .getintValue(par.get(ProductRulesEnum0019.ProductRulesEnum0019002.getValue()));
        Integer jianxiDaysLimit = getJianxiDaysLimit(par);

        // 房源配置的灵活定价
        Map<String, Double> flexiblePriceMap = flexiblePriceMap(solrDocument);
        // 夹心日期
        List<String> priorityDays = getPriorityDays(solrDocument);
        // 夹心日期map
        Map<String, List<String>> priorityDaysMap = priorityDaysMap(priorityDays);
        // 锁定日期
        List<String> occupyDays = getOccupyDays(solrDocument);

        Double discount = 1.0;

        if (!Check.NuNObj(startDate) && !Check.NuNObj(endDate)) {

            List<Date> days = DateSplitUtil.dateSplit(startDate, endDate);
            if (Check.NuNCollection(days)) {
                days = new ArrayList<>();
            }

            // 房源配置的长租折扣
            Map<String, Double> longTermLeaseDiscountMap = getLongTermLeaseDiscountMap(solrDocument);

            // 处理长租30天折扣价格
            if (!Check.NuNObj(longTerm30DaysLimit) && longTerm30DaysLimit > 0 && days.size() >= longTerm30DaysLimit
                    && !Check.NuNMap(longTermLeaseDiscountMap) && !Check.NuNObj(
                    longTermLeaseDiscountMap.get(ProductRulesEnum0019.ProductRulesEnum0019002.getValue()))) {
                // Double longTermDiscount =
                // longTermLeaseDiscountMap.get(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
                //
                // if (longTermDiscount>1) {//长租折扣是百分比，要转化为小数
                // longTermDiscount = longTermDiscount/100;
                // }
                // if(discount==1 && longTermDiscount>0){
                // discount = longTermDiscount;
                // }

            } else if (!Check.NuNObj(longTerm7DaysLimit) && longTerm7DaysLimit > 0 && days.size() >= longTerm7DaysLimit
                    && !Check.NuNMap(longTermLeaseDiscountMap) && !Check.NuNObj(
                    longTermLeaseDiscountMap.get(ProductRulesEnum0019.ProductRulesEnum0019001.getValue()))) {// 处理长租7天折扣价格
                // Double longTermDiscount =
                // longTermLeaseDiscountMap.get(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
                // if (longTermDiscount>1) {//长租折扣是百分比，要转化为小数
                // longTermDiscount = longTermDiscount/100;
                // }
                // if(discount==1 && longTermDiscount>0){
                // discount = longTermDiscount;
                // }
            } else if (!Check.NuNObj(jianxiDaysLimit) && jianxiDaysLimit >= days.size()
                    && !Check.NuNMap(flexiblePriceMap) && !Check.NuNCollection(priorityDays)) {// 处理间隙折扣价格

                boolean flag = true;
                for (Date date : days) {
                    if (!priorityDays.contains(DateUtil.dateFormat(date, datePatten))) {
                        flag = false;
                    }
                }

                if (flag) {

                    List<String> jaxinList = priorityDaysMap.get(DateUtil.dateFormat(startDate, datePatten));
                    if (jaxinList.size() == ProductRulesEnum020.ProductRulesEnum020003.getDayNum()
                            && !Check.NuNObj(flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020003.getValue()))) {
                        discount = flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020003.getValue());
                    } else if (jaxinList.size() == ProductRulesEnum020.ProductRulesEnum020002.getDayNum() && !isTonightDisActive(solrDocument)
                            && !Check.NuNObj(flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020002.getValue()))) {
                        discount = flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020002.getValue());
                    }

                }
            }

        } else if (Check.NuNCollection(occupyDays) || !occupyDays.contains(DateUtil.dateFormat(new Date(), datePatten))) {// 当天没有出租

            List<String> jaxinList = priorityDaysMap.get(DateUtil.dateFormat(new Date(), datePatten));
            if (!Check.NuNCollection(jaxinList)) {
                if (jaxinList.size() == ProductRulesEnum020.ProductRulesEnum020003.getDayNum() &&
                        !Check.NuNObj(flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020003.getValue()))) {
                    discount = flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020003.getValue());
                } else if (jaxinList.size() == ProductRulesEnum020.ProductRulesEnum020002.getDayNum() &&
                        !Check.NuNObj(flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020002.getValue())) && !isTonightDisActive(solrDocument)) {
                    discount = flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020002.getValue());
                }
            }

        }

        // 今夜特价
        Double tdaydiscount = flexiblePriceMap.get(ProductRulesEnum020.ProductRulesEnum020001.getValue());
        if (!Check.NuNObj(tdaydiscount) && isTonightDisActive(solrDocument)) {
            discount = tdaydiscount;
        }

        discount = Check.NuNObj(discount) ? 1.0 : discount;
        price = Double.valueOf(price * discount).intValue();

        price = price / 100 * 100;// 元取整数
        house.setPrice(price);

        if (price < originalPrice) {
            house.setOriginalPrice(originalPrice);
        }

    }

    /**
     * 获取房源的真实的图片信息
     *
     * @param par
     * @param picUrl
     * @return
     * @author afi
     */
    private static String getRealPic(Map<String, Object> par, Object picUrl) {
        String picSize = ValueUtil.getStrValue(par.get("picSize"));
        return ValueUtil.getStrValue(picUrl).replace(SolrConstant.picSize, picSize);
    }

    /**
     * 获取真实的图标信息
     *
     * @param par
     * @param labelTypeEnum
     * @return
     * @author afi
     */
    private static String getRealIcon(Map<String, Object> par, LabelTypeEnum labelTypeEnum) {
        String iconBaseUrl = ValueUtil.getStrValue(par.get("iconBaseUrl"));
        return iconBaseUrl + labelTypeEnum.getPath().replace(SolrConstant.iconPath,
                IconPicTypeEnum.getByCode(ValueUtil.getintValue(par.get("iconType"))).getPath());
    }

}
