package com.dingsheng.decent.mapper;

import com.dingsheng.decent.entity.Member;
import com.dingsheng.decent.util.db.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper extends MyMapper<Member> {

    List<Member> selectByReferralCode(@Param("referralCode") String referralCode);

    List<Member> MySecondLowerByReferralCode(@Param("referralCode") String referralCode);


    int modifyPwd(@Param("telphone") String telphone,@Param("password") String password);

}