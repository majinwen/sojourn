/**
 * @FileName: HouseServiceFacEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author yd
 * @created 2016年12月5日 下午5:48:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.valeenum;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.house.entity.EnumVo;
import com.ziroom.minsu.services.house.entity.HouseBedNumVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;

import java.util.LinkedList;
import java.util.List;


/**
 * <p>房源 服务 设施 在app端展示个数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum HouseServiceFacEnum {

	House_Service(1,2){
		@Override
		public void setHouseServiceFacNum(TenantHouseDetailVo tenantHouseDetailVo) {
			if(!Check.NuNObj(tenantHouseDetailVo)){
			}
		}
	},
	House_Fac(2,1){
		@Override
		public void setHouseServiceFacNum(TenantHouseDetailVo tenantHouseDetailVo) {
			if(!Check.NuNObj(tenantHouseDetailVo)){
			}
		}
	},
	House_Electric(3,1){
		@Override
		public void setHouseServiceFacNum(TenantHouseDetailVo tenantHouseDetailVo) {
			if(!Check.NuNObj(tenantHouseDetailVo)){
			}
		}
	},
	House_Bathroom(4,1){
		@Override
		public void setHouseServiceFacNum(TenantHouseDetailVo tenantHouseDetailVo) {
			if(!Check.NuNObj(tenantHouseDetailVo)){
			}
		}
	};

	HouseServiceFacEnum(int code,int facNum){

		this.code = code;
		this.facNum = facNum;
	}

	/**
	 * 最大展示数
	 */
	protected static final int max = 5;

	protected static final String staticUrl= "http://minsustatic.ziroom.com/api";

	protected static final String picTwoName= "2x.png";

	protected static final String picThrName= "3x.png";
	/**
	 * 枚举code
	 */
	private int code;

	/**
	 * 相关设施个数
	 */
	private int facNum;

	public void setHouseServiceFacNum(TenantHouseDetailVo tenantHouseDetailVo){

	}

	/**
	 * 
	 * 给当前 服务   设施 电器 卫浴  排序 以及 给床 归为到 设施中
	 * 
	 * 说明： 
	 * 房源设施 展示规则：
	 *  先服务 取两次  ，其他各取一次，满足则返回，不满足，在如此剩下的设施中 在取一轮（每一次 取需要的个数），如果不能满足则返回
	 *
	 * @author yd
	 * @created 2016年12月5日 下午6:16:06
	 *
	 * @param tenantHouseDetailVo
	 */
	public void sortHouseServiceFac(TenantHouseDetailVo tenantHouseDetailVo,String staticUrl){

		if(!Check.NuNObj(tenantHouseDetailVo)){
			if(Check.NuNStrStrict(staticUrl)) staticUrl = this.staticUrl;

			staticUrl = staticUrl+"/imgs/";

			//房源——部分设施展示
			LinkedList<EnumVo> listHouseFacilities = new LinkedList<EnumVo>();

			//部分服务
			LinkedList<EnumVo> fuwuList = new LinkedList<EnumVo>();

			//部分设施
			LinkedList<EnumVo> sheshiList = new LinkedList<EnumVo>();

			//部分电器
			LinkedList<EnumVo> dianqiList = new LinkedList<EnumVo>();

			//部分卫浴
			LinkedList<EnumVo> weiyuList = new LinkedList<EnumVo>();

			//床位和智能锁
			LinkedList<EnumVo> chuangList = new LinkedList<EnumVo>(); 
			//服务列表
			List<HouseConfVo> serveList = tenantHouseDetailVo.getServeList();

			//房源配套设施列表
			List<HouseConfVo> facilityList= tenantHouseDetailVo.getFacilityList();


			LinkedList<EnumVo> listHouseFacServ = new LinkedList<EnumVo>();
			int num1 = House_Service.getFacNum();
			//添加服务
			if(!Check.NuNCollection(serveList)){


				EnumVo enumVo = new EnumVo();
				enumVo.setKey(ProductRulesEnum.ProductRulesEnum0015.getValue());
				enumVo.setText(ProductRulesEnum.ProductRulesEnum0015.getName());

				List<EnumVo> subListEnum = new LinkedList<EnumVo>();
				for (HouseConfVo houseConfVo : serveList) {
					EnumVo enumV = new EnumVo();
					enumV.setKey(houseConfVo.getDicValue());
					enumV.setText(houseConfVo.getDicName());
					enumV.setIconTwoUrl(staticUrl+ProductRulesEnum.ProductRulesEnum0015.getValue()+"_"+enumV.getKey()+"_"+this.picTwoName);
					enumV.setIconThrUrl(staticUrl+ProductRulesEnum.ProductRulesEnum0015.getValue()+"_"+enumV.getKey()+"_"+this.picThrName);
					subListEnum.add(enumV);
					if(num1>0){
						num1--;
						fuwuList.add(enumV);
					}
				}
				enumVo.setSubEnumVals(subListEnum);
				listHouseFacServ.add(enumVo);
			}
			int num2 = House_Fac.getFacNum();
			int num3 = House_Electric.getFacNum();
			int num4 = House_Bathroom.getFacNum();

			LinkedList<EnumVo> subSheshiList = new LinkedList<EnumVo>(); 
			LinkedList<EnumVo> subDianqiList = new LinkedList<EnumVo>(); 
			LinkedList<EnumVo> subWeiyuList = new LinkedList<EnumVo>(); 


			//床位信息  需要封装到设施中
			List<HouseBedNumVo> bedList= tenantHouseDetailVo.getBedList();

			//是否有智能锁
			int isLock = tenantHouseDetailVo.getIsLock();
			//区分设施  电器 卫浴
			if(!Check.NuNCollection(facilityList)){

				for(HouseConfVo vo:facilityList){

					//设施  添加床位信息
					if(vo.getDicCode().equals(ProductRulesEnum.ProductRulesEnum002003.getValue())){
						EnumVo enumV = new EnumVo();
						enumV.setKey(vo.getDicValue());
						enumV.setText(vo.getDicName());
						enumV.setIconTwoUrl(staticUrl+ProductRulesEnum.ProductRulesEnum002003.getValue()+"_"+enumV.getKey()+"_"+this.picTwoName);
						enumV.setIconThrUrl(staticUrl+ProductRulesEnum.ProductRulesEnum002003.getValue()+"_"+enumV.getKey()+"_"+this.picThrName);
						subSheshiList.add(enumV);

						if(num2>0){
							num2--;
							sheshiList.add(enumV);
						}

					}
					//电器
					if(vo.getDicCode().equals(ProductRulesEnum.ProductRulesEnum002001.getValue())){
						EnumVo enumV = new EnumVo();
						enumV.setKey(vo.getDicValue());
						enumV.setText(vo.getDicName());
						enumV.setIconTwoUrl(staticUrl+ProductRulesEnum.ProductRulesEnum002001.getValue()+"_"+enumV.getKey()+"_"+this.picTwoName);
						enumV.setIconThrUrl(staticUrl+ProductRulesEnum.ProductRulesEnum002001.getValue()+"_"+enumV.getKey()+"_"+this.picThrName);
						subDianqiList.add(enumV);
						if(num3>0){
							num3--;
							dianqiList.add(enumV);
						}
					}
					//卫浴
					if(vo.getDicCode().equals(ProductRulesEnum.ProductRulesEnum002002.getValue())){
						EnumVo enumV = new EnumVo();
						enumV.setKey(vo.getDicValue());
						enumV.setText(vo.getDicName());
						enumV.setIconTwoUrl(staticUrl+ProductRulesEnum.ProductRulesEnum002002.getValue()+"_"+enumV.getKey()+"_"+this.picTwoName);
						enumV.setIconThrUrl(staticUrl+ProductRulesEnum.ProductRulesEnum002002.getValue()+"_"+enumV.getKey()+"_"+this.picThrName);
						subWeiyuList.add(enumV);
						if(num4>0){
							num4--;
							weiyuList.add(enumV);
						}
					}


				}

				if(!Check.NuNCollection(bedList)){
					for (HouseBedNumVo houseBedNumVo : bedList) {
						EnumVo enumV = new EnumVo();
						enumV.setKey(houseBedNumVo.getBedType()+"_"+houseBedNumVo.getBedNum());
						enumV.setText(houseBedNumVo.getBedNum()+houseBedNumVo.getBedTypeName());
						//床的icon统一改为双人床, @Author:lusp @Date:2017/9/5
//						enumV.setIconTwoUrl(staticUrl+ProductRulesEnum.ProductRulesEnum005.getValue()+"_"+houseBedNumVo.getBedType()+"_"+this.picTwoName);
//						enumV.setIconThrUrl(staticUrl+ProductRulesEnum.ProductRulesEnum005.getValue()+"_"+houseBedNumVo.getBedType()+"_"+this.picThrName);
						enumV.setIconTwoUrl(staticUrl+ProductRulesEnum.ProductRulesEnum005.getValue()+"_2_"+this.picTwoName);
						enumV.setIconThrUrl(staticUrl+ProductRulesEnum.ProductRulesEnum005.getValue()+"_2_"+this.picThrName);
						chuangList.add(enumV);
					}
				}


				if(isLock == 1){
					EnumVo enumV = new EnumVo();
					enumV.setKey("house_lock");
					enumV.setText("密码锁");
					enumV.setIconTwoUrl(staticUrl+enumV.getKey()+"_"+this.picTwoName);
					enumV.setIconThrUrl(staticUrl+enumV.getKey()+"_"+this.picThrName);
					chuangList.add(enumV);
				}

				if(subSheshiList.size()>0||chuangList.size()>0){
					EnumVo enumVo = new EnumVo();
					enumVo.setKey(ProductRulesEnum.ProductRulesEnum002003.getValue());
					enumVo.setText(ProductRulesEnum.ProductRulesEnum002003.getName());
					enumVo.setSubEnumVals(subSheshiList);
					if(chuangList.size()>0){
						subSheshiList.addAll(chuangList);
					}
					listHouseFacServ.add(enumVo);
				}
				if(subDianqiList.size()>0){
					EnumVo enumVo = new EnumVo();
					enumVo.setKey(ProductRulesEnum.ProductRulesEnum002001.getValue());
					enumVo.setText(ProductRulesEnum.ProductRulesEnum002001.getName());
					enumVo.setSubEnumVals(subDianqiList);
					
					listHouseFacServ.add(enumVo);
				}
				if(subWeiyuList.size()>0){
					EnumVo enumVo = new EnumVo();
					enumVo.setKey(ProductRulesEnum.ProductRulesEnum002002.getValue());
					enumVo.setText(ProductRulesEnum.ProductRulesEnum002002.getName());
					enumVo.setSubEnumVals(subWeiyuList);
					listHouseFacServ.add(enumVo);
				}
			}

			//总大小
			int fuwuSize = fuwuList.size();
			int sheshiSize = sheshiList.size();
			int dianqiSize = dianqiList.size();
			int weiyuSize = weiyuList.size();

			int max = fuwuSize+sheshiSize+dianqiSize+weiyuSize;
			if(max<this.max){
				int need = this.max-max;
				//服务处理
				if(num1==0&&need>0&&serveList.size()>House_Service.getFacNum()){
					for (HouseConfVo houseConfVo : serveList) {
						num1++;
						if(num1>House_Service.getFacNum()){
							EnumVo enumV = new EnumVo();
							enumV.setKey(houseConfVo.getDicValue());
							enumV.setText(houseConfVo.getDicName());
							enumV.setIconTwoUrl(staticUrl+ProductRulesEnum.ProductRulesEnum0015.getValue()+"_"+enumV.getKey()+"_"+this.picTwoName);
							enumV.setIconThrUrl(staticUrl+ProductRulesEnum.ProductRulesEnum0015.getValue()+"_"+enumV.getKey()+"_"+this.picThrName);
							fuwuList.add(enumV);
							need--;
							if(need == 0){
								break ;
							}
						}
					}
				}

				//设施处理 由于设施有添加  
				if(need>0&&num2==0&&subSheshiList.size()>House_Fac.getFacNum()){

					for (EnumVo enumVo : subWeiyuList) {
						num2++;
						if(num2>House_Fac.getFacNum()){
							sheshiList.add(enumVo);
							need--;
							if(need == 0){
								break ;
							}
						}
					}
				}
				//电器
				if(need>0&&num3==0&&subDianqiList.size()>House_Electric.getFacNum()){

					for (EnumVo enumVo : subDianqiList) {
						num3++;
						if(num3>House_Electric.getFacNum()){
							dianqiList.add(enumVo);
							need--;
							if(need == 0){
								break ;
							}
						}
					}

				}
				//卫浴
				if(need>0&&num4==0&&subWeiyuList.size()>House_Bathroom.getFacNum()){

					for (EnumVo enumVo : subWeiyuList) {
						num4++;
						if(num4>House_Bathroom.getFacNum()){
							weiyuList.add(enumVo);
							need--;
							if(need == 0){
								break ;
							}
						}
					}

				}
				//取床位
				if(need>0&&!Check.NuNCollection(chuangList)){
					for (EnumVo enumVo : chuangList) {
						sheshiList.add(enumVo);
						need--;
						if(need == 0){
							break ;
						}
					}
				}
			}

			if(fuwuList.size()>0)
				listHouseFacilities.addAll(fuwuList);
			if(sheshiList.size()>0)
				listHouseFacilities.addAll(sheshiList);
			if(dianqiList.size()>0)
				listHouseFacilities.addAll(dianqiList);
			if(weiyuList.size()>0)
				listHouseFacilities.addAll(weiyuList);
			tenantHouseDetailVo.setListHouseFacilities(listHouseFacilities);
			tenantHouseDetailVo.setListHouseFacServ(listHouseFacServ);
		}
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the facNum
	 */
	public int getFacNum() {
		return facNum;
	}

	/**
	 * @param facNum the facNum to set
	 */
	public void setFacNum(int facNum) {
		this.facNum = facNum;
	} 


}
