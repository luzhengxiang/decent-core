package com.dingsheng.decent.mapper;

import com.dingsheng.decent.entity.AssetsIoRecords;
import com.dingsheng.decent.util.db.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AssetsIoRecordsMapper extends MyMapper<AssetsIoRecords> {

    List<AssetsIoRecords> ioList(@Param("userId")Long userId);
}