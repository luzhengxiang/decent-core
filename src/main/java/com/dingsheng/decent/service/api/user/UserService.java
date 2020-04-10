package com.dingsheng.decent.service.api.user;

import com.dingsheng.decent.dto.api.user.RegisterRequest;
import com.dingsheng.decent.entity.Account;
import com.dingsheng.decent.entity.AssetsIoRecords;
import com.dingsheng.decent.entity.Member;
import com.dingsheng.decent.mapper.AccountMapper;
import com.dingsheng.decent.mapper.AssetsIoRecordsMapper;
import com.dingsheng.decent.mapper.MemberMapper;
import com.dingsheng.decent.util.db.DatabaseUtil;
import com.dingsheng.decent.util.encrypt.MD5;
import com.dingsheng.decent.util.old.EncryptUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @luzhengxiang
 * @create 2020-04-07 15:59
 **/
@Service
public class UserService {


    @Autowired
    AccountMapper accountMapper;
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    AssetsIoRecordsMapper assetsIoRecordsMapper;

    /**
     *
     * @param id
     * @return
     */
    public Account getAccount(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @param id
     * @return
     */
    public Member getMember(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取资金流水
     * @return
     */
    public PageInfo<AssetsIoRecords> ioList(long userId,int page,int pageSize){
        PageHelper.startPage(page,pageSize);
        PageInfo<AssetsIoRecords> pageInfo = new PageInfo<>(assetsIoRecordsMapper.ioList(userId));
        return pageInfo;
    }

    /**
     * 添加用户信息
     * @param request
     */
    @Transactional
    public void register(RegisterRequest request){

        //推荐人账号被锁定，暂不可注册
        //推荐人账号不存在，不可以注册
        String a = request.getReferralCode();

        // 获取唯一邀请码
        String referralCode = EncryptUtil.getInstance().GetRandNum(6, 1);

        //生成用户id
        long id = DatabaseUtil.getDatabasePriykey();
        Member member = new Member();
        member.setId(id);

        member.setPayPwd(MD5.encode(request.getPayPwd()));
        member.setTelephone(request.getPhone());
        member.setProvince(MD5.encode(request.getLoginPwd()));
        member.setTrackid(referralCode);
        member.setRegdate(new Date());
        member.setReferralCode(request.getReferralCode());
        memberMapper.insertSelective(member);

        Account account = new Account();
        account.setId(id);
        account.setBalance(BigDecimal.ZERO);
        account.setDjAmount(BigDecimal.ZERO);
        account.setTotalAssets(BigDecimal.ZERO);
        accountMapper.insertSelective(account);
    }

    /**
     * 修改登录密码
     * @param telphone
     * @param password
     */
    public void modifyPwd(String telphone,String password){
        memberMapper.modifyPwd(telphone,password);
    }

    /**
     * 获取我的下级用户
     * @param userId
     */
    public List<Member> getMyLower(Long userId){
        Member member = memberMapper.selectByPrimaryKey(userId);
        return memberMapper.selectByReferralCode(member.getTrackid());
    }

    /**
     * 获取我的下级用户
     * @param userId
     */
    public List<Member> getMySecondLower(Long userId){
        Member member = memberMapper.selectByPrimaryKey(userId);
        return memberMapper.selectByReferralCode(member.getTrackid());
    }


}
