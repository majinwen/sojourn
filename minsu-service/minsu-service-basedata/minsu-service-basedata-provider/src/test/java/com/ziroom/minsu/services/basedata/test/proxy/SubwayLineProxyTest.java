package com.ziroom.minsu.services.basedata.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.google.gson.JsonObject;
import com.ziroom.minsu.services.basedata.dto.SubwayLineRequest;
import com.ziroom.minsu.services.basedata.proxy.SubwayLineProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class SubwayLineProxyTest extends BaseTest {

	@Resource(name = "basedata.subwayLineProxy")
	private SubwayLineProxy subwayLineProxy;

	@Test
	public void findSubwayLinePageTest() {

		SubwayLineRequest subwayLineRequest = new SubwayLineRequest();
		subwayLineRequest.setPage(1);
		subwayLineRequest.setLimit(5);
		subwayLineRequest.setLineName("1号线");

		String result = subwayLineProxy.findSubwayLinePage(JsonEntityTransform.Object2Json(subwayLineRequest));
		System.out.println("result:" + result);
	}

	@Test
	public void findSubwayLineTest() {

		JsonObject json = new JsonObject();
		json.addProperty("lineFid", "8a9e9ab9540fa26e01540fa26ed30000");
		json.addProperty("stationId", "8a9e9ab9540faf8201540faf82500000");

		String result = subwayLineProxy.findSubwayInfo(json.toString());
		System.out.println("result:" + result);
	}

	@Test
	public void saveSubwayLineTest() {
		String json = "{\"nationCode\":\"CN\",\"provinceCode\":\"BJ\",\"cityCode\":\"BJSD\",\"lineName\":\"1号线\",\"stationName\":\"这一站1111\",\"stationLongitude\":116.45911799999999,\"stationLatitude\":39.8658,\"outList\":[{\"outName\":\"11111\",\"outLongitude\":116.46009720000006,\"outLatitude\":39.8648765}]}";
		subwayLineProxy.saveSubwayLine(json);
	}

	@Test
	public void updateSubwayLineTest() {
		String json = "{\"lineFid\":\"8a9e9ab9540fa26e01540fa26ed30000\",\"stationFid\":\"8a9e9ab9540faf8201540faf82500000\",\"nationCode\":\"11\",\"provinceCode\":\"22\",\"cityCode\":\"33\",\"lineName\":\"1号线\",\"stationName\":\"将台站\",\"stationLongitude\":116.46009720000006,\"stationLatitude\":39.8648765,\"outList\":[{\"outFid\":\"8a9e9ab9540fb06101540fb061dc0000\",\"outName\":\"A口1\",\"outLongitude\":2121.11,\"outLatitude\":12313.11}]}";
		subwayLineProxy.updateSubwayLine(json);
	}
}
