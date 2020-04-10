package com.dingsheng.decent.mapper;

import com.dingsheng.decent.entity.SysAdmin;
import com.dingsheng.decent.util.db.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysAdminMapper extends MyMapper<SysAdmin> {

    SysAdmin selectByUserName(@Param("userName")String userName);

    int updatePwd(@Param("pwd")String pwd,@Param("id")Integer id);
}