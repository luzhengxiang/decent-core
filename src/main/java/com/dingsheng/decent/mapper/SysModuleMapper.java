package com.dingsheng.decent.mapper;

import com.dingsheng.decent.entity.SysModule;
import com.dingsheng.decent.util.db.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysModuleMapper extends MyMapper<SysModule> {

    List<SysModule> getModuleByParentId(@Param("parentId")int parentId);

    List<SysModule> getCurrentUserPermissionByParentId(@Param("parentId")int parentId,@Param("permission")String permission);
}