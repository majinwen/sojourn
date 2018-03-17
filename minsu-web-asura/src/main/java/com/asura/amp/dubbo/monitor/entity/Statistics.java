package com.asura.amp.dubbo.monitor.entity;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.asura.framework.base.entity.BaseEntity;

public class Statistics extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer keyid;
	
	private String checkcode;
	
	private String application;
	
	private String service;
	
	private String method;
	
	private String version;
	
	private String provider;
	
	private String consumer;
	
	private String type;
	
	private String statisticstype;
	
	private Integer success;
	
	private Integer failure;
	
	private Integer elapsed;
	
	private Integer concurrent;
	
	private Integer max_elapsed;
	
	private Integer max_concurrent;
	
	private Date dtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getKeyid() {
		return keyid;
	}

	public void setKeyid(Integer keyid) {
		this.keyid = keyid;
	}
	
	public String getCheckcode() {
		if (checkcode != null) {
			return checkcode;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(application).append("\t");
			sb.append(service).append("\t");
			sb.append(method).append("\t");
			sb.append(version).append("\t");
			sb.append(provider).append("\t");
			sb.append(consumer);

			setCheckcode(DigestUtils.md5Hex(sb.toString().getBytes()));

			return checkcode;
		}
	}
	
	private void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatisticstype() {
		return statisticstype;
	}

	public void setStatisticstype(String statisticstype) {
		this.statisticstype = statisticstype;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getFailure() {
		return failure;
	}

	public void setFailure(Integer failure) {
		this.failure = failure;
	}

	public Integer getElapsed() {
		return elapsed;
	}

	public void setElapsed(Integer elapsed) {
		this.elapsed = elapsed;
	}

	public Integer getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(Integer concurrent) {
		this.concurrent = concurrent;
	}

	public Integer getMax_elapsed() {
		return max_elapsed;
	}

	public void setMax_elapsed(Integer max_elapsed) {
		this.max_elapsed = max_elapsed;
	}

	public Integer getMax_concurrent() {
		return max_concurrent;
	}

	public void setMax_concurrent(Integer max_concurrent) {
		this.max_concurrent = max_concurrent;
	}

	public Date getDtime() {
		return dtime;
	}

	public void setDtime(Date dtime) {
		this.dtime = dtime;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("id:").append(id).append("\n");
		sb.append("keyid:").append(keyid).append("\n");
		sb.append("checkcode:").append(getCheckcode()).append("\n");
		sb.append("application:").append(application).append("\n");
		sb.append("service:").append(service).append("\n");
		sb.append("method:").append(method).append("\n");
		sb.append("version:").append(version).append("\n");
		sb.append("provider:").append(provider).append("\n");
		sb.append("consumer:").append(consumer).append("\n");
		sb.append("type:").append(type).append("\n");
		sb.append("statisticstype:").append(statisticstype).append("\n");
		sb.append("success:").append(success).append("\n");
		sb.append("failure:").append(failure).append("\n");
		sb.append("elapsed:").append(elapsed).append("\n");
		sb.append("concurrent:").append(concurrent).append("\n");
		sb.append("max_elapsed:").append(max_elapsed).append("\n");
		sb.append("max_concurrent:").append(max_concurrent).append("\n");
		sb.append("dtime:").append(dtime);
		
		sb.insert(0, "{").append("}");
		
		return sb.toString();
	}
}
