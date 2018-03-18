package com.zra.push.dao;

import org.springframework.stereotype.Repository;

import com.zra.push.entity.PushLog;

@Repository
public interface PushLogMapper {
    
    int insert(PushLog record);
}