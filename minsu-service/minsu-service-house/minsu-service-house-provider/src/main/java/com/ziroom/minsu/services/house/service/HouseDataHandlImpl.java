package com.ziroom.minsu.services.house.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.services.house.dao.*;
import com.ziroom.minsu.services.house.dto.HouseBaseExtRequest;
import com.ziroom.minsu.services.house.dto.HouseConfParamsDto;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseRoomListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年06月29日 17:24
 * @since 1.0
 */
@Service("house.houseDataHandlImpl")
public class HouseDataHandlImpl {


    @Resource(name="house.houseBaseExtDao")
    private HouseBaseExtDao houseBaseExtDao;

    @Resource(name="house.houseRoomMsgDao")
    private HouseRoomMsgDao houseRoomMsgDao;

    @Resource(name = "house.houseConfMsgDao")
    private HouseConfMsgDao houseConfMsgDao;

    @Resource(name = "house.houseDescDao")
    private HouseDescDao houseDescDao;


    public void handHouseData() throws IOException {
        HouseBaseExtRequest houseBaseExtRequest = new HouseBaseExtRequest();
        houseBaseExtRequest.setPage(1);
        houseBaseExtRequest.setLimit(100);

        String insertExt = "insert ignore into `t_house_room_ext` (`fid`, `room_fid`, `order_type`, `check_out_rules_code`, `deposit_rules_code`, `room_rules`, `min_day`, `check_in_time`, `check_out_time`) values('%s','%s','%d','%s','%s','%s','%s','%s','%s');";
        String insertConf = "insert into `t_house_conf_msg` (`fid`, `house_base_fid`, `room_fid`, `bed_fid`, `dic_code`, `dic_val`) values('%s','%s','%s',NULL,'%s','%s');";
        String updateExt = "update t_house_base_ext set rent_room_num = %d where house_base_fid = '%s';";
        File file = new File("D:\\ext.sql");
        if (file.exists()){
            file.delete();
        }

        File file1 = new File("D:\\conf.sql");
        if (file1.exists()){
            file1.delete();
        }
        File file2 = new File("D:\\update.sql");
        if (file2.exists()){
            file2.delete();
        }
        FileWriter fileWriter = new FileWriter(file,true);
        FileWriter fileWriter1 = new FileWriter(file1,true);
        FileWriter fileWriter2 = new FileWriter(file2,true);
        int page = 1;
        while (true){

            houseBaseExtRequest.setPage(page);

            PagingResult<HouseBaseExtEntity> pagingResult = houseBaseExtDao.findHouseBaseExtByPageF(houseBaseExtRequest);
            List<HouseBaseExtEntity> list = pagingResult.getRows();
            if (Check.NuNCollection(list)){
                break;
            }
            page ++;

            for (HouseBaseExtEntity houseBaseExtEntity : list){
                String houseBaseFid = houseBaseExtEntity.getHouseBaseFid();

                //houseBaseFid = "8a9099775d5fc93f015d5fdf6df50051";
                //押金 处理
                HouseConfVo houseDepositConf = houseConfMsgDao.findHouseDepositConfByHouseFid(houseBaseFid, null, 0);

                System.err.println(JsonEntityTransform.Object2Json(houseDepositConf));
                HouseDescEntity houseDescEntity = houseDescDao.findHouseDescByHouseBaseFid(houseBaseFid);
                if (Check.NuNObj(houseDescEntity)){
                    continue;
                }

                Map<String,Object> map = new HashMap<>();
                map.put("houseBaseFid",houseBaseFid);
                map.put("dicCode", "ProductRulesEnum0024");
                List<HouseConfVo> houseConfList = houseConfMsgDao.findHouseConfVoList(map);

                //查询房间列表
                List<HouseRoomMsgEntity> roomList = houseRoomMsgDao.findRoomListByHouseBaseFid(houseBaseFid);

                //处理描述信息
                String updateSql = String.format(updateExt,roomList.size(),houseBaseFid);
                fileWriter2.write(updateSql +"\r\n");
                fileWriter2.flush();

                for (HouseRoomMsgEntity roomMsgEntity : roomList){
                    String roomFid = roomMsgEntity.getFid();
                    String ext = String.format(insertExt, UUIDGenerator.hexUUID(), roomMsgEntity.getFid(), houseBaseExtEntity.getOrderType(),
                            houseBaseExtEntity.getCheckOutRulesCode(), houseBaseExtEntity.getDepositRulesCode(), houseDescEntity.getHouseRules()
                            , houseBaseExtEntity.getMinDay(), houseBaseExtEntity.getCheckInTime(), houseBaseExtEntity.getCheckOutTime());


                    fileWriter.write(ext+"\r\n");
                    fileWriter.flush();
                    //导入押金
                    if (!Check.NuNObj(houseDepositConf)){
                        //查看房间是否有押金
                        HouseConfVo roomDeposit = houseConfMsgDao.findHouseDepositConfByHouseFid(houseBaseFid, roomFid, 1);
                        if (Check.NuNObj(roomDeposit)){
                            String conf = String.format(insertConf,UUIDGenerator.hexUUID(),houseBaseFid,roomFid,houseDepositConf.getDicCode(),houseDepositConf.getDicValue());
                            fileWriter1.write(conf+"\r\n");
                            fileWriter1.flush();
                        }
                    }

                    if (!Check.NuNCollection(houseConfList)){
                        for (HouseConfVo houseConfVo : houseConfList){
                            //查询相同的code房间是否存在，如果不存在则与房源相同
                            String dicCode = houseConfVo.getDicCode();
                            HouseConfParamsDto houseConfParamsDto = new HouseConfParamsDto();
                            houseConfParamsDto.setHouseBaseFid(houseBaseFid);
                            houseConfParamsDto.setRoomFid(roomFid);
                            houseConfParamsDto.setRentWay(1);
                            houseConfParamsDto.setDicCode(dicCode);
                            List<HouseConfMsgEntity> singleCode = houseConfMsgDao.findHouseConfValidList(houseConfParamsDto);
                            if (Check.NuNCollection(singleCode)){
                                String conf = String.format(insertConf,UUIDGenerator.hexUUID(),houseBaseFid,roomFid,houseConfVo.getDicCode(),houseConfVo.getDicValue());
                                fileWriter1.write(conf+"\r\n");
                                fileWriter1.flush();
                            }

                        }
                    }

                }

            }

        }

    }

}
