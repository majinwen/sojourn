package com.ziroom.minsu.services.basedata.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.file.FileCityEntity;
import com.ziroom.minsu.entity.file.FileRegionEntity;
import com.ziroom.minsu.entity.file.FileRegionItemsEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityArchiveService;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import com.ziroom.minsu.services.basedata.entity.CityArchivesVo;
import com.ziroom.minsu.services.basedata.service.CityArchiveServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/7
 */
@Component("basedata.cityArchiveServiceProxy")
public class CityArchiveServiceProxy implements CityArchiveService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CityArchiveServiceProxy.class);

    @Resource(name = "basedata.cityArchiveServiceImpl")
    private CityArchiveServiceImpl cityArchiveServiceImpl;

    @Resource(name = "basedata.messageSource")
    private MessageSource messageSource;

    /**
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/8 14:11
     */
    @Override
    public String getCityArchiveList(String paramJson) {
        LogUtil.debug(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try{
            CityArchivesRequest cityArchivesRequest = JsonEntityTransform.json2Object(paramJson, CityArchivesRequest.class);
            PagingResult<CityArchivesVo> pageResult = cityArchiveServiceImpl.searchArchivesList(cityArchivesRequest);
            dto.putValue("total", pageResult.getTotal());
            dto.putValue("list", pageResult.getRows());
        }catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 查询城市景点商圈
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 10:16
     */
    @Override
    public String getRegionList(String paramJson) {
        LogUtil.debug(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try{
            CityArchivesRequest cityArchivesRequest = JsonEntityTransform.json2Object(paramJson, CityArchivesRequest.class);
            List<CityArchivesVo> regionList = cityArchiveServiceImpl.getRegionList(cityArchivesRequest);
            dto.putValue("regionList",regionList);
        }catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 查询景点商圈下辖推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/11 11:41
     */
    @Override
    public String getRegionItems(String paramJson) {
        LogUtil.debug(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try{
            CityArchivesRequest cityArchivesRequest = JsonEntityTransform.json2Object(paramJson, CityArchivesRequest.class);
            List<CityArchivesVo> regionItems = cityArchiveServiceImpl.getRegionItems(cityArchivesRequest);
            dto.putValue("regionItems",regionItems);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 根据景点商圈fid查询其存在的推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/14 9:42
     */
    @Override
    public String getItemsByHotRegionFid(String paramJson) {
        LogUtil.debug(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try{
            CityArchivesRequest cityArchivesRequest = JsonEntityTransform.json2Object(paramJson, CityArchivesRequest.class);
            if(Check.NuNObj(cityArchivesRequest.getHotRegionFid())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("未传入景点商圈fid");
                return dto.toJsonString();
            }
            List<FileRegionItemsEntity> items = cityArchiveServiceImpl.getItemsByHotRegionFid(cityArchivesRequest);
            dto.putValue("items",items);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 保存或修改景点商圈
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 14:06
     */
    @Override
    public String saveOrUpRegion(String paramJson) {
        LogUtil.debug(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try{
            CityArchivesRequest cityArchivesRequest = JsonEntityTransform.json2Object(paramJson, CityArchivesRequest.class);
            //通过HotRegionFid查询景点商圈是否有内容
            if(Check.NuNObj(cityArchivesRequest.getHotRegionFid())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("未传入景点商圈fid");
                return dto.toJsonString();
            }
            //城市第一次建立城市档案
            FileRegionEntity fileRegionEntity = new FileRegionEntity();
            fileRegionEntity.setHotRegionFid(cityArchivesRequest.getHotRegionFid());
            //如果以后需要一个商圈多条内容，商圈内容fid用于以后扩展
            if(!Check.NuNObj(cityArchivesRequest.getRegionContentFid())){
                fileRegionEntity.setFid(cityArchivesRequest.getRegionContentFid());
            }
            FileRegionEntity region = cityArchiveServiceImpl.getRegion(fileRegionEntity);
            if(Check.NuNObj(region)){//如果没有查到记录，则插入一条新的记录
                //设置插入记录的属性
                fileRegionEntity.setFid(UUIDGenerator.hexUUID());
                fileRegionEntity.setCreateFid(cityArchivesRequest.getCreateFid());
                fileRegionEntity.setHotRegionBrief(cityArchivesRequest.getHotRegionBrief());
                fileRegionEntity.setHotRegionContent(cityArchivesRequest.getHotRegionContent());
                //插入记录，并且更新关联表
                cityArchiveServiceImpl.saveRegion(fileRegionEntity,cityArchivesRequest.getCityCode());
            }else{
                //更新记录,描述和内容
                if(!Check.NuNObj(cityArchivesRequest.getHotRegionBrief())){
                    region.setHotRegionBrief(cityArchivesRequest.getHotRegionBrief());
                }
                if(!Check.NuNObj(cityArchivesRequest.getHotRegionContent())){
                    region.setHotRegionContent(cityArchivesRequest.getHotRegionContent());
                }
                if(Check.NuNObj(cityArchivesRequest.getHotRegionBrief()) && Check.NuNObj(cityArchivesRequest.getHotRegionContent())){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("未做修改，确认放弃");
                    return dto.toJsonString();
                }
                region.setLastModifyDate(null);
                cityArchiveServiceImpl.updateRegion(region);
            }

        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }

    /**
     * 查询景点商圈内容
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 19:25
     */
    @Override
    public String getRegion(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        try{
            FileRegionEntity fileRegionEntity = JsonEntityTransform.json2Object(paramJson, FileRegionEntity.class);
            FileRegionEntity region = cityArchiveServiceImpl.getRegion(fileRegionEntity);
            if(Check.NuNObj(region)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
                return dto.toJsonString();
            }
            dto.putValue("region",region);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();

    }

    /**
     * 保存或者修改景点商圈推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 19:22
     */
    @Override
    public String saveOrUpItem(String paramJson) {
        LogUtil.debug(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try{
            CityArchivesRequest cityArchivesRequest = JsonEntityTransform.json2Object(paramJson, CityArchivesRequest.class);
            if(Check.NuNObj(cityArchivesRequest.getHotRegionFid())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("未传入景点商圈fid");
                return dto.toJsonString();
            }
            //判断插入还是更新操作 依据，是否传入itemFid
            if(Check.NuNObj(cityArchivesRequest.getItemFid())){
                //插入一条新的推荐项
                FileRegionItemsEntity fileRegionItemsEntity = new FileRegionItemsEntity();
                fileRegionItemsEntity.setFid(UUIDGenerator.hexUUID());
                dto.putValue("itemFid",fileRegionItemsEntity.getFid());
                fileRegionItemsEntity.setHotRegionFid(cityArchivesRequest.getHotRegionFid());
                fileRegionItemsEntity.setCreateFid(cityArchivesRequest.getCreateFid());
                fileRegionItemsEntity.setItemName(cityArchivesRequest.getItemName());
                fileRegionItemsEntity.setItemBrief(cityArchivesRequest.getItemBrief());
                fileRegionItemsEntity.setItemAbstract(cityArchivesRequest.getItemAbstract());
                fileRegionItemsEntity.setItemContent(cityArchivesRequest.getItemContent());
                cityArchiveServiceImpl.saveRegionItem(fileRegionItemsEntity);
            }else{
                //根据itemFid查询出推荐项内容
                FileRegionItemsEntity item = cityArchiveServiceImpl.getRegionItem(cityArchivesRequest.getItemFid());
                if(!Check.NuNObj(cityArchivesRequest.getItemName())){
                    item.setItemName(cityArchivesRequest.getItemName());
                }
                if(!Check.NuNObj(cityArchivesRequest.getItemBrief())){
                    item.setItemBrief(cityArchivesRequest.getItemBrief());
                }
                if(!Check.NuNObj(cityArchivesRequest.getItemAbstract())){
                    item.setItemAbstract(cityArchivesRequest.getItemAbstract());
                }
                if(!Check.NuNObj(cityArchivesRequest.getItemContent())){
                    item.setItemContent(cityArchivesRequest.getItemContent());
                }
                if(Check.NuNObj(cityArchivesRequest.getItemName()) && Check.NuNObj(cityArchivesRequest.getItemBrief()) && Check.NuNObj(cityArchivesRequest.getItemAbstract()) && Check.NuNObj(cityArchivesRequest.getItemContent())){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("未做修改，确认放弃");
                    return dto.toJsonString();
                }
                item.setLastModifyDate(null);
                cityArchiveServiceImpl.updateRegionItem(item);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }

    /**
     * 保存活修改城市档案
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 18:33
     */
    @Override
    public String saveOrUpArchive(String paramJson) {
        LogUtil.debug(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try{
            CityArchivesRequest cityArchivesRequest = JsonEntityTransform.json2Object(paramJson, CityArchivesRequest.class);
            if(Check.NuNObj(cityArchivesRequest.getCityCode())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("未传城市无法建立档案");
                return dto.toJsonString();
            }
            //判断插入还是更新操作 fid存在则修改，否则插入新的城市档案内容
            if(Check.NuNObj(cityArchivesRequest.getFid())){
                FileCityEntity fileCityEntity = new FileCityEntity();
                fileCityEntity.setFid(UUIDGenerator.hexUUID());
                fileCityEntity.setCreateFid(cityArchivesRequest.getCreateFid());
                fileCityEntity.setCityCode(cityArchivesRequest.getCityCode());
                fileCityEntity.setCityFileContent(cityArchivesRequest.getCityFileContent());
                cityArchiveServiceImpl.saveArchive(fileCityEntity,cityArchivesRequest.getCityCode());
            }else{
                //更新记录,根据fid查出对应的城市档案内容
                FileCityEntity file = cityArchiveServiceImpl.getArchive(cityArchivesRequest.getFid());
                if(!Check.NuNObj(cityArchivesRequest.getCityFileContent())){
                    file.setCityFileContent(cityArchivesRequest.getCityFileContent());
                    file.setLastModifyDate(null);
                    cityArchiveServiceImpl.updateArchive(file);
                }else{
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("未做修改，确认放弃");
                    return dto.toJsonString();
                }

            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }

    /**
     * 查询一条推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 21:11
     */
    @Override
    public String getRegionItem(String itemFid) {
        FileRegionItemsEntity item = cityArchiveServiceImpl.getRegionItem(itemFid);
        String resultJson = JsonEntityTransform.Object2Json(item);
        return resultJson;
    }

    /**
     * 查询一条城市档案
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/11 10:49
     */
    @Override
    public String getArchive(String fid) {
        FileCityEntity archive = cityArchiveServiceImpl.getArchive(fid);
        return JsonEntityTransform.Object2Json(archive);
    }
}
