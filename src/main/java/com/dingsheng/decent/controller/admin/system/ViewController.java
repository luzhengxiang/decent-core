package com.dingsheng.decent.controller.admin.system;

import com.dingsheng.decent.entity.SysAdmin;
import com.dingsheng.decent.entity.SysModule;
import com.dingsheng.decent.service.admin.system.SysAdminService;
import com.dingsheng.decent.service.admin.system.SysModuleService;
import com.dingsheng.decent.util.old.AdminInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 视图控制器
 */
@Controller
@RequestMapping(value = "admin")
public class ViewController {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SysAdminService sysAdminService;
    @Autowired
    SysModuleService sysModuleService;

    @RequestMapping("")
    public String index() {
        return "Login";
    }

    //<editor-fold desc="母版页">
    @RequestMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) return "Login";
        Object isLogin = session.getAttribute("isLogin");
        if (isLogin == null) return "Login";
        if (!(Boolean) isLogin) return "Login";
        if (AdminInfo.getPAdmin() == null) return "Login";
        model.addAttribute("AdminName", AdminInfo.getPAdmin().getNickName());
        return "home";
    }
    //</editor-fold>

    //<editor-fold desc="首页">

    @RequestMapping(value = "/homeData")
    public String homeData(Model model) {
        //0普通会员 1高级会员 2钻石会员 3皇冠会员 4金牌会员
        long userLevelCount0 = 0;
        long userLevelCount1 = 0;
        long userLevelCount2 = 0;
        long userLevelCount3 = 0;
        long userLevelCount4 = 0;

        //宠物预约数
        long productBuyCount1 = 0;
        long productBuyCount2 = 0;
        long productBuyCount3 = 0;
        long productBuyCount4 = 0;
        long productBuyCount5 = 0;
        long productBuyCount6 = 0;
        long productBuyCount7 = 0;
        long productBuyCount8 = 0;
        long productBuyCount9 = 0;

        //宠物转让数
        long productSellCount1 = 0;
        long productSellCount2 = 0;
        long productSellCount3 = 0;
        long productSellCount4 = 0;
        long productSellCount5 = 0;
        long productSellCount6 = 0;
        long productSellCount7 = 0;
        long productSellCount8 = 0;
        long productSellCount9 = 0;

        try {
//            userLevelCount0 = queryFactory.select(qUser.id.count()).from(qUser).where(qUser.userLevel.eq(EnumUtil.EUserLevel.普通会员.getValue())).fetchCount();
//            userLevelCount1 = queryFactory.select(qUser.id.count()).from(qUser).where(qUser.userLevel.eq(EnumUtil.EUserLevel.高级会员.getValue())).fetchCount();
//            userLevelCount2 = queryFactory.select(qUser.id.count()).from(qUser).where(qUser.userLevel.eq(EnumUtil.EUserLevel.钻石会员.getValue())).fetchCount();
//            userLevelCount3 = queryFactory.select(qUser.id.count()).from(qUser).where(qUser.userLevel.eq(EnumUtil.EUserLevel.皇冠会员.getValue())).fetchCount();
//            userLevelCount4 = queryFactory.select(qUser.id.count()).from(qUser).where(qUser.userLevel.eq(EnumUtil.EUserLevel.金牌会员.getValue())).fetchCount();

//            productBuyCount1 = queryFactory.select(qUserHangBuy.id.count()).from(qUserHangBuy).where(qUserHangBuy.productId.eq(1)
//                    .and(qUserHangBuy.state.eq(EnumUtil.EUserHangBuy.预约中.getValue()))).fetchCount();
//            productBuyCount2 = queryFactory.select(qUserHangBuy.id.count()).from(qUserHangBuy).where(qUserHangBuy.productId.eq(2)
//                    .and(qUserHangBuy.state.eq(EnumUtil.EUserHangBuy.预约中.getValue()))).fetchCount();
//            productBuyCount3 = queryFactory.select(qUserHangBuy.id.count()).from(qUserHangBuy).where(qUserHangBuy.productId.eq(3)
//                    .and(qUserHangBuy.state.eq(EnumUtil.EUserHangBuy.预约中.getValue()))).fetchCount();
//            productBuyCount4 = queryFactory.select(qUserHangBuy.id.count()).from(qUserHangBuy).where(qUserHangBuy.productId.eq(4)
//                    .and(qUserHangBuy.state.eq(EnumUtil.EUserHangBuy.预约中.getValue()))).fetchCount();
//            productBuyCount5 = queryFactory.select(qUserHangBuy.id.count()).from(qUserHangBuy).where(qUserHangBuy.productId.eq(5)
//                    .and(qUserHangBuy.state.eq(EnumUtil.EUserHangBuy.预约中.getValue()))).fetchCount();
//            productBuyCount6 = queryFactory.select(qUserHangBuy.id.count()).from(qUserHangBuy).where(qUserHangBuy.productId.eq(6)
//                    .and(qUserHangBuy.state.eq(EnumUtil.EUserHangBuy.预约中.getValue()))).fetchCount();
//            productBuyCount7 = queryFactory.select(qUserHangBuy.id.count()).from(qUserHangBuy).where(qUserHangBuy.productId.eq(7)
//                    .and(qUserHangBuy.state.eq(EnumUtil.EUserHangBuy.预约中.getValue()))).fetchCount();
//            productBuyCount8 = queryFactory.select(qUserHangBuy.id.count()).from(qUserHangBuy).where(qUserHangBuy.productId.eq(8)
//                    .and(qUserHangBuy.state.eq(EnumUtil.EUserHangBuy.预约中.getValue()))).fetchCount();
//            productBuyCount9 = queryFactory.select(qUserHangBuy.id.count()).from(qUserHangBuy).where(qUserHangBuy.productId.eq(9)
//                    .and(qUserHangBuy.state.eq(EnumUtil.EUserHangBuy.预约中.getValue()))).fetchCount();
//
//            productSellCount1 = queryFactory.select(qUserHangSell.id.count()).from(qUserHangSell).where(qUserHangSell.productId.eq(1)
//                    .and(qUserHangSell.state.eq(EnumUtil.EUserHangSell.待转让.getValue()))).fetchCount();
//            productSellCount2 = queryFactory.select(qUserHangSell.id.count()).from(qUserHangSell).where(qUserHangSell.productId.eq(2)
//                    .and(qUserHangSell.state.eq(EnumUtil.EUserHangSell.待转让.getValue()))).fetchCount();
//            productSellCount3 = queryFactory.select(qUserHangSell.id.count()).from(qUserHangSell).where(qUserHangSell.productId.eq(3)
//                    .and(qUserHangSell.state.eq(EnumUtil.EUserHangSell.待转让.getValue()))).fetchCount();
//            productSellCount4 = queryFactory.select(qUserHangSell.id.count()).from(qUserHangSell).where(qUserHangSell.productId.eq(4)
//                    .and(qUserHangSell.state.eq(EnumUtil.EUserHangSell.待转让.getValue()))).fetchCount();
//            productSellCount5 = queryFactory.select(qUserHangSell.id.count()).from(qUserHangSell).where(qUserHangSell.productId.eq(5)
//                    .and(qUserHangSell.state.eq(EnumUtil.EUserHangSell.待转让.getValue()))).fetchCount();
//            productSellCount6 = queryFactory.select(qUserHangSell.id.count()).from(qUserHangSell).where(qUserHangSell.productId.eq(6)
//                    .and(qUserHangSell.state.eq(EnumUtil.EUserHangSell.待转让.getValue()))).fetchCount();
//            productSellCount7 = queryFactory.select(qUserHangSell.id.count()).from(qUserHangSell).where(qUserHangSell.productId.eq(7)
//                    .and(qUserHangSell.state.eq(EnumUtil.EUserHangSell.待转让.getValue()))).fetchCount();
//            productSellCount8 = queryFactory.select(qUserHangSell.id.count()).from(qUserHangSell).where(qUserHangSell.productId.eq(8)
//                    .and(qUserHangSell.state.eq(EnumUtil.EUserHangSell.待转让.getValue()))).fetchCount();
//            productSellCount9 = queryFactory.select(qUserHangSell.id.count()).from(qUserHangSell).where(qUserHangSell.productId.eq(9)
//                    .and(qUserHangSell.state.eq(EnumUtil.EUserHangSell.待转让.getValue()))).fetchCount();

        } catch (Exception ex) {
            ex.getMessage();
        }

        model.addAttribute("productBuyCount1", productBuyCount1);
        model.addAttribute("productBuyCount2", productBuyCount2);
        model.addAttribute("productBuyCount3", productBuyCount3);
        model.addAttribute("productBuyCount4", productBuyCount4);
        model.addAttribute("productBuyCount5", productBuyCount5);
        model.addAttribute("productBuyCount6", productBuyCount6);
        model.addAttribute("productBuyCount7", productBuyCount7);
        model.addAttribute("productBuyCount8", productBuyCount8);
        model.addAttribute("productBuyCount9", productBuyCount9);

        model.addAttribute("productSellCount1", productSellCount1);
        model.addAttribute("productSellCount2", productSellCount2);
        model.addAttribute("productSellCount3", productSellCount3);
        model.addAttribute("productSellCount4", productSellCount4);
        model.addAttribute("productSellCount5", productSellCount5);
        model.addAttribute("productSellCount6", productSellCount6);
        model.addAttribute("productSellCount7", productSellCount7);
        model.addAttribute("productSellCount8", productSellCount8);
        model.addAttribute("productSellCount9", productSellCount9);

        model.addAttribute("userLevelCount0", userLevelCount0);
        model.addAttribute("userLevelCount1", userLevelCount1);
        model.addAttribute("userLevelCount2", userLevelCount2);
        model.addAttribute("userLevelCount3", userLevelCount3);
        model.addAttribute("userLevelCount4", userLevelCount4);
        return "homeData";
    }
    //</editor-fold>

    //<editor-fold desc="系统配置">

    @RequestMapping("/WebConfig")
    public String WebConfig(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (IsSession(session)) return "Login";

//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//        WebConfig webConfig = queryFactory.selectFrom(qWebConfig).fetchFirst();

//        model.addAttribute("drawRules", webConfig.drawRules);
//        model.addAttribute("customerServiceImg", webConfig.customerServiceImg);
//        model.addAttribute("isOpenExchange", webConfig.isOpenExchange);
//        model.addAttribute("luckDrawConsume", webConfig.luckDrawConsume);
//        model.addAttribute("authenticationGive", webConfig.authenticationGive);
//        model.addAttribute("profitRate1", webConfig.profitRate1);
//        model.addAttribute("profitRate2", webConfig.profitRate2);
//        model.addAttribute("profitRate3", webConfig.profitRate3);
//        model.addAttribute("profitRate4", webConfig.profitRate4);
//        model.addAttribute("profitRate5", webConfig.profitRate5);
//        model.addAttribute("profitRate6", webConfig.profitRate6);
//        model.addAttribute("upgrade0", webConfig.upgrade0);
//        model.addAttribute("upgrade1", webConfig.upgrade1);
//        model.addAttribute("upgrade2", webConfig.upgrade2);
//        model.addAttribute("upgrade3", webConfig.upgrade3);
//        model.addAttribute("recommCount1", webConfig.recommCount1);
//        model.addAttribute("recommCount2", webConfig.recommCount2);
//        model.addAttribute("recommCount3", webConfig.recommCount3);
//        model.addAttribute("teamCount1", webConfig.teamCount1);
//        model.addAttribute("teamCount2", webConfig.teamCount2);
//        model.addAttribute("tradeTime", webConfig.tradeTime);
//        model.addAttribute("orient_fee", webConfig.orient_fee);
//        model.addAttribute("orientMin", webConfig.orientMin);
//        model.addAttribute("unlockAmount", webConfig.unlockAmount);
//        model.addAttribute("notSellSwitch", webConfig.notSellSwitch);

        return "WebConfig";
    }

    //</editor-fold>

    //<editor-fold desc="管理员">

    //<editor-fold desc="列表">
    @RequestMapping("/AdminIndex")
    public String AdminIndex(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (IsSession(session)) return "Login";
        return "AdminIndex";
    }
    //</editor-fold>

    //<editor-fold desc="编辑页">

    @RequestMapping("/AdminEdit")
    public String AdminEdit(Integer id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (IsSession(session)) return "login";

        if (id > 0) {
//            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//            Admin info = queryFactory.selectFrom(qAdmin).where(qAdmin.id.eq(id)).fetchFirst();
            SysAdmin info = sysAdminService.selectById(id);
            model.addAttribute("id", info.getId());
            model.addAttribute("nickName", info.getNickName());
            model.addAttribute("userName", info.getUserName());
            model.addAttribute("locking", info.getLocking());
            model.addAttribute("permission", info.getPermission());
            model.addAttribute("URL", "url:'/admin/Admin/GetModule?id=" + info.getId() + "',method:'get',animate:true,checkbox:true");
        } else {
            model.addAttribute("id", 0);
            model.addAttribute("nickName", "");
            model.addAttribute("userName", "");
            model.addAttribute("locking", "");
            model.addAttribute("permission", "");
            model.addAttribute("URL", "url:'/admin/Admin/GetModule?id=0',method:'get',animate:true,checkbox:true");
        }
        return "AdminEdit";
    }
    //</editor-fold>

    //<editor-fold desc="修改密码">
    @RequestMapping("/UpdatePwd")
    public String UpdatePwd(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (IsSession(session)) return "Login";
        return "UpdatePwd";
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="功能导航栏">

    //<editor-fold desc="列表">
    @RequestMapping("/ModuleIndex")
    public String ModuleIndex(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (IsSession(session)) return "Login";
        return "ModuleIndex";
    }
    //</editor-fold>

    //<editor-fold desc="编辑页">

    @RequestMapping("/ModuleEdit")
    public String ModuleEdit(Integer ID, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (IsSession(session)) return "login";

        if (ID > 0) {
//            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//            Module info = queryFactory.selectFrom(qModule).where(qModule.ID.eq(ID)).fetchFirst();
            SysModule info = sysModuleService.getById(ID);
            model.addAttribute("ID", info.getId());
            model.addAttribute("Name", info.getName());
            model.addAttribute("ParentID", info.getParentID());
            model.addAttribute("Icon", info.getIcon());
            model.addAttribute("LinkUrl", info.getLinkUrl());
            model.addAttribute("Sort", info.getSort());
        } else {
            model.addAttribute("ID", 0);
            model.addAttribute("Name", "");
            model.addAttribute("ParentID", 0);
            model.addAttribute("Icon", "");
            model.addAttribute("LinkUrl", "");
            model.addAttribute("Sort", 0);
        }
        return "ModuleEdit";
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="用户">

    //<editor-fold desc="列表">
    @RequestMapping("/UserIndex")
    public String UserIndex(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (IsSession(session)) return "Login";

        return "UserIndex";
    }
    //</editor-fold>

    //<editor-fold desc="充值">

//    @RequestMapping("/rechargeView")
//    public String rechargeView(Integer id, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        if (id > 0) {
//            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//            User userInfo = queryFactory.selectFrom(qUser).where(qUser.id.eq(id)).fetchFirst();
//
//            model.addAttribute("id", userInfo.id.toString());
//            model.addAttribute("userName", userInfo.userName);
//            model.addAttribute("amount", userInfo.amount);
//            model.addAttribute("integral", userInfo.integral);
//            model.addAttribute("creditScore", userInfo.creditScore);
//        }
//        return "UserRechargeView";
//    }

    //</editor-fold>

    //<editor-fold desc="收款码信息">
//    @RequestMapping("/UserReceiptInfo")
//    public String UserReceiptInfo(Integer id, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//        User info = queryFactory.selectFrom(qUser).where(qUser.id.eq(id)).fetchFirst();
//        if (info != null) {
//            String undefinedImg = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=26528730,97133990&fm=26&gp=0.jpg";
//            String undefinedText = "未绑定";
//            model.addAttribute("userNo", info.userNo);
//            model.addAttribute("userName", info.userName);
//            model.addAttribute("wecharQRCode", StringUtils.isBlank(info.wecharQRCode) ? undefinedImg : info.wecharQRCode);
//            model.addAttribute("alipayQRCode", StringUtils.isBlank(info.alipayQRCode) ? undefinedImg : info.alipayQRCode);
//            model.addAttribute("bankNo", StringUtils.isBlank(info.bankNo) ? undefinedText : info.bankNo);
//            model.addAttribute("openingBank", StringUtils.isBlank(info.openingBank) ? undefinedText : info.openingBank);
//            model.addAttribute("realName", StringUtils.isBlank(info.realName) ? undefinedText : info.realName);
//            model.addAttribute("idCard", StringUtils.isBlank(info.idCard) ? undefinedText : info.idCard);
//        }
//
//        return "UserReceiptInfo";
//    }
    //</editor-fold>

    //<editor-fold desc="提现列表">
//    @RequestMapping("/UserWithdrawIndex")
//    public String UserWithdrawIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//
//        return "UserWithdrawIndex";
//    }
    //</editor-fold>

    //<editor-fold desc="解锁申请列表">
//    @RequestMapping("/UserUnlockApplyIndex")
//    public String UserUnlockApplyIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//
//        return "UserUnlockApplyIndex";
//    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="留言列表">
//    @RequestMapping("/UserMessageIndex")
//    public String UserMessageIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//
//        return "UserMessageIndex";
//    }
    //</editor-fold>

    //<editor-fold desc="留言列表">
//    @RequestMapping("/UserMsgReply")
//    public String UserMsgReply(Integer ID, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        model.addAttribute("ID", ID);
//        return "UserMsgReply";
//    }
    //</editor-fold>

    //<editor-fold desc="账户流水">

    //<editor-fold desc="列表">
//    @RequestMapping("/UserRecordIndex")
//    public String UserRecordIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//
//        return "UserRecordIndex";
//    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="退出登录">
//    @RequestMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//            return "Login";
//        } else {
//            return "Login";
//        }
//    }
    //</editor-fold>

    //<editor-fold desc="文件上传方法">

    /**
     * 文件上传
     *
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/UploadImg2", method = {RequestMethod.GET, RequestMethod.POST})
//    public void UploadImg2(HttpServletRequest request, HttpServletResponse response) {
//        response.setContentType("application/json");
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;//上传第一张图
//        List<MultipartFile> files1 = multipartRequest.getFiles("upfile");
//        String res = new UploadingUnity().DitorFileUpload(files1.get(0), AdminInfo.getPAdmin().nickName, EnumUtil.UploadVerifyType.图片.getValue());
//        ResponseUtils.renderJson(response, res);
//    }
    //</editor-fold>

    //<editor-fold desc="宠物列表">

    //<editor-fold desc="列表">
//    @RequestMapping("/ProductIndex")
//    public String RobotIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        return "ProductIndex";
//    }
    //</editor-fold>

    //<editor-fold desc="编辑页">

//    @RequestMapping("/ProductEdit")
//    public String RobotEdit(Integer id, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//
//        if (id > 0) {
//            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//            Product robot = queryFactory.selectFrom(qProduct).where(qProduct.id.eq(id)).fetchFirst();
//
//            if (robot.matchTime != null) {
//                List<String> result = Arrays.asList(robot.matchTime.split(","));
//                model.addAttribute("mStart", result.get(0));
//                model.addAttribute("mEnd", result.get(1));
//            }
//            model.addAttribute("id", robot.id);
//            model.addAttribute("name", robot.name);
//            model.addAttribute("image", robot.image);
//            model.addAttribute("bookingConsume", robot.bookingConsume);
//            model.addAttribute("exchangeAmount", robot.exchangeAmount);
//            model.addAttribute("greaterAmount", robot.greaterAmount);
//            model.addAttribute("amount", robot.amount);
//            model.addAttribute("maxAmount", robot.maxAmount);
//            model.addAttribute("durationCount", robot.durationCount);
//            model.addAttribute("profit", robot.profit);
//            model.addAttribute("upgradeProductId", robot.upgradeProductId);
//            model.addAttribute("isOpen", robot.isOpen);
//            model.addAttribute("matchTime", robot.matchTime);
//            model.addAttribute("matchSuccessRate", robot.matchSuccessRate);
//        } else {
//            model.addAttribute("id", 0);
//            model.addAttribute("name", "");
//            model.addAttribute("image", "");
//            model.addAttribute("bookingConsume", "");
//            model.addAttribute("exchangeAmount", 0);
//            model.addAttribute("amount", 0);
//            model.addAttribute("maxAmount", 0);
//            model.addAttribute("durationCount", 0);
//            model.addAttribute("profit", 0);
//            model.addAttribute("upgradeProductId", 0);
//            model.addAttribute("isOpen", 0);
//            model.addAttribute("matchTime", "");
//        }
//        return "ProductEdit";
//    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="用户土地">

    //<editor-fold desc="列表">
//    @RequestMapping("/UserProductIndex")
//    public String UserGardenLandIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        return "UserProductIndex";
//    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="宠物交易">

    //<editor-fold desc="预约列表">
//    @RequestMapping("/UserHangBuyIndex")
//    public String UserHangBuyIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        return "UserHangBuyIndex";
//    }
    //</editor-fold>

    //<editor-fold desc="转让列表">
//    @RequestMapping("/UserHangSellIndex")
//    public String UserHangSellIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        return "UserHangSellIndex";
//    }
    //</editor-fold>

    //<editor-fold desc="匹配列表">

//    @RequestMapping("/TradeMatchIndex")
//    public String TradeMatchIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        return "TradeMatchIndex";
//    }

    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="预约手动匹配">

//    @RequestMapping("/ManualBuyMatch")
//    public String ManualBuyMatch(Integer id, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//
//        if (id > 0) {
//            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//            UserHangBuy buy = queryFactory.selectFrom(qUserHangBuy).where(qUserHangBuy.id.eq(id)).fetchFirst();
//            User user = queryFactory.selectFrom(qUser).where(qUser.id.eq(buy.userId)).fetchFirst();
//            if (user != null) {
//                model.addAttribute("userName", user.userName);
//            }
//            Product product = queryFactory.selectFrom(qProduct).where(qProduct.id.eq(buy.productId)).fetchFirst();
//            if (product != null) {
//                model.addAttribute("productName", product.name);
//            }
//            model.addAttribute("id", id);
//        } else {
//            model.addAttribute("id", 0);
//            model.addAttribute("userName", "");
//            model.addAttribute("productName", "");
//        }
//        return "ManualBuyMatch";
//    }
    //</editor-fold>

    //<editor-fold desc="转让手动匹配">

//    @RequestMapping("/ManualSellMatch")
//    public String ManualSellMatch(Integer id, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//
//        if (id > 0) {
//            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//            UserHangSell sell = queryFactory.selectFrom(qUserHangSell).where(qUserHangSell.id.eq(id)).fetchFirst();
//            User user = queryFactory.selectFrom(qUser).where(qUser.id.eq(sell.userId)).fetchFirst();
//            if (user != null) {
//                model.addAttribute("userName", user.userName);
//            }
//            Product product = queryFactory.selectFrom(qProduct).where(qProduct.id.eq(sell.productId)).fetchFirst();
//            if (product != null) {
//                model.addAttribute("productName", product.name);
//            }
//            model.addAttribute("id", id);
//        } else {
//            model.addAttribute("id", 0);
//            model.addAttribute("userName", "");
//            model.addAttribute("productName", "");
//        }
//        return "ManualSellMatch";
//    }
    //</editor-fold>

    //<editor-fold desc="奖品列表">

    //<editor-fold desc="列表">
//    @RequestMapping("/PrizeIndex")
//    public String PrizeIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        return "PrizeIndex";
//    }
    //</editor-fold>

    //<editor-fold desc="编辑页">

//    @RequestMapping("/PrizeEdit")
//    public String PrizeEdit(Integer id, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//
//        if (id > 0) {
//            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//            Prize robot = queryFactory.selectFrom(qPrize).where(qPrize.id.eq(id)).fetchFirst();
//
//            model.addAttribute("id", robot.id);
//            model.addAttribute("name", robot.name);
//            model.addAttribute("image", robot.image);
//            model.addAttribute("type", robot.type);
//            model.addAttribute("rewardIntegral", robot.rewardIntegral);
//            model.addAttribute("probability", robot.probability);
//            model.addAttribute("position", robot.position);
//        }
//        return "PrizeEdit";
//    }
    //</editor-fold>

    //<editor-fold desc="列表">
//    @RequestMapping("/PrizeRecordIndex")
//    public String PrizeRecordIndex(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        return "PrizeRecordIndex";
//    }
    //</editor-fold>

    //</editor-fold>

    //充值
//    @RequestMapping("/rechargeView2")
//    public String rechargeView2(Integer id, String ids, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        model.addAttribute("ids", ids);
////        if (id > 0) {
////            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
////            User userInfo = queryFactory.selectFrom(qUser).where(qUser.id.eq(id)).fetchFirst();
////            model.addAttribute("id", userInfo.id.toString());
////            model.addAttribute("userName", userInfo.userName);
////        }
//        return "UserRechargeView2";
//    }

    //添加用户
//    @RequestMapping("/addUserView")
//    public String addUserView(Integer id, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (IsSession(session)) return "Login";
//        if (id > 0) {
//            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//            User userInfo = queryFactory.selectFrom(qUser).where(qUser.id.eq(id)).fetchFirst();
//            model.addAttribute("recommId", userInfo.id.toString());
//            model.addAttribute("recommName", userInfo.userName);
//        }
//        return "addUserView";
//    }



    protected boolean IsSession(HttpSession session) {
        if (session != null) {
            Object isLogin = session.getAttribute("isLogin");
            if (!(Boolean) isLogin) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}