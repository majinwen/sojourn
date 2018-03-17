function alertMsgByJson(json) {
	if(json.statusCode == DWZ.statusCode.ok) {
		alertMsg.correct(json.message);
	} else if(json.statusCode == DWZ.statusCode.error) {
		alertMsg.error(json.message);
	} else if(json.statusCode == DWZ.statusCode.timeout) {
		alertMsg.error(json.message);
	} else {
		alertMsg.info(json.message);
	}
}