package com.dingsheng.decent.mapper;

import com.dingsheng.decent.entity.Account;
import com.dingsheng.decent.util.db.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends MyMapper<Account> {
}