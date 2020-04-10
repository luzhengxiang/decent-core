package com.dingsheng.decent.util.old;

public class EnumUtil {

    //<editor-fold desc="判断类型专用枚举 0：否 1：是">

    /**
     * 判断类型专用枚举 0：否 1：是
     */
    public enum EType {
        No(0), Yes(1);

        private Integer value;

        EType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="正负类型枚举">

    /**
     * 正负类型枚举
     */
    public enum EPM {
        Plus(0), Minus(1);

        private Integer value;

        EPM(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="记录类型枚举">

    /**
     * 记录类型枚举
     */
    public enum ERecordType {
        后台充值(1000),
        后台减扣(1001),
        实名赠送(1002),
        兑换宠物(1003),
        预约消耗(1004),
        匹配过期(1005),
        抽奖消耗(1006),
        抽奖获得(1007),
        宠物转让(1008),
        合约收益(1009),
        团队奖励(1010),
        胡萝卜交易(1011),
        申请解锁(1012);

        private Integer value;

        ERecordType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        //<editor-fold desc="手写的从int到enum的转换函数">
        public static ERecordType valueOf(int value) {
            switch (value) {
                case 1000:
                    return 后台充值;
                case 1001:
                    return 后台减扣;
                case 1002:
                    return 实名赠送;
                case 1003:
                    return 兑换宠物;
                case 1004:
                    return 预约消耗;
                case 1005:
                    return 匹配过期;
                case 1006:
                    return 抽奖消耗;
                case 1007:
                    return 抽奖获得;
                case 1008:
                    return 宠物转让;
                case 1009:
                    return 合约收益;
                case 1010:
                    return 团队奖励;
                case 1011:
                    return 胡萝卜交易;
                case 1012:
                    return 申请解锁;
                default:
                    return null;
            }
        }
        //</editor-fold>
    }

    //</editor-fold>

    //<editor-fold desc="上传文件验证类型枚举">

    /**
     * 上传文件验证类型枚举
     */
    public enum UploadVerifyType {
        所有(0), 图片(1), 文本(1);

        private Integer value;

        UploadVerifyType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="随机数类型枚举">

    /**
     * 随机数类型枚举
     */
    public enum ERandNumType {
        英文and数字(-1), 纯数字(1), 纯英文(2);

        private Integer value;

        ERandNumType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="获取时间差类型枚举">

    /**
     * 获取时间差类型枚举
     */
    public enum EDateType {
        天(0), 小时(1), 分钟(2), 秒(3);

        private Integer value;

        EDateType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="用户等级枚举">

    /**
     * 用户等级枚举
     * 用户级别 0 普通会员 1 高级会员 2钻石会员 3皇冠会员 4金牌会员
     */
    public enum EUserLevel {
        普通会员(0), 高级会员(1), 钻石会员(2), 皇冠会员(3), 金牌会员(4);

        private Integer value;

        EUserLevel(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        //手写的从int到enum的转换函数
        public static EUserLevel valueOf(int value) {
            switch (value) {
                case 0:
                    return 普通会员;
                case 1:
                    return 高级会员;
                case 2:
                    return 钻石会员;
                case 3:
                    return 皇冠会员;
                case 4:
                    return 金牌会员;
                default:
                    return null;
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="交易类型枚举">

    /**
     * 交易类型枚举
     */
    public enum ETradeType {
        交易卖出(0), 交易买入(1);

        private Integer value;

        ETradeType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        //手写的从int到enum的转换函数
        public static ETradeType valueOf(int value) {
            switch (value) {
                case 0:
                    return 交易卖出;
                case 1:
                    return 交易买入;
                default:
                    return null;
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="匹配订单类型">
    //<editor-fold desc="匹配订单类型">

    public enum ETradeMatchType {

        正常订单(0), 申诉订单(1);

        int value;

        ETradeMatchType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="激活状态">

    public enum EActivationState {

        未激活(0), 已激活(1);

        int value;

        EActivationState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="币种枚举">

    /**
     * 币种枚举
     */
    public enum ECurrencyType {
        胡萝卜(0), 积分(1), 信誉分(2), 合约收益(3);

        private Integer value;

        ECurrencyType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        //手写的从int到enum的转换函数
        public static ECurrencyType valueOf(int value) {
            switch (value) {
                case 0:
                    return 胡萝卜;
                case 1:
                    return 积分;
                case 2:
                    return 信誉分;
                default:
                    return null;
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="流水状态类型枚举">

    /**
     * 流水状态（0：支出 1：收入）
     */
    public enum ERecordStatus {
        支出(0), 收入(1);

        private Integer value;

        ERecordStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="锁定状态枚举">

    /**
     * 锁定状态枚举
     */
    public enum ELockingStatus {
        正常(0), 暂时锁定(1), 永久锁定(2);

        private Integer value;

        ELockingStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="认证状态枚举">

    /**
     * 认证状态 枚举
     */
    public enum EIdentityState {
        未认证(0), 审核中(1),认证成功(2), 认证失败(3);

        private Integer value;

        EIdentityState(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="用户宠物枚举">

    /**
     * 认证状态 枚举
     */
    public enum EUserProductStatus {
        生长中(0), 已转让(1);

        private Integer value;

        EUserProductStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="交易状态枚举">

    /**
     * 挂买状态枚举
     */
    public enum EUserHangBuy {
        预约中(0), 预约成功(1), 预约失败(2);

        private Integer value;

        EUserHangBuy(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="交易状态枚举">

    /**
     * 挂买状态枚举
     */
    public enum EUserHangSell {
        待转让(0), 转让中(1), 已转让(2);

        private Integer value;

        EUserHangSell(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="交易匹配枚举">

    /**
     * 交易匹配枚举
     */
    public enum EUserHangMatch {
        待转让(0), 待付款(1), 待收款(2), 已完成(3), 申诉中(4), 已取消(5);

        private Integer value;

        EUserHangMatch(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="奖品类型枚举">

    /**
     * 奖品类型枚举
     */
    public enum EPrizeType {
        积分奖励(0), 谢谢惠顾(1), 自定义奖品(2);

        private Integer value;

        EPrizeType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="抽奖记录类型枚举">

    /**
     * 抽奖记录类型枚举
     */
    public enum EPrizeRecordType {
        未中奖(0), 中奖(1);

        private Integer value;

        EPrizeRecordType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="抽奖记录状态枚举">

    /**
     * 抽奖记录状态枚举
     */
    public enum EPrizeRecordStatus {
        未中奖(0), 未处理(1), 已处理(2);

        private Integer value;

        EPrizeRecordStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="公告类型枚举">

    /**
     * 公告类型枚举
     */
    public enum EMsgType {
        公告列表(0), 弹窗公告(1);

        private Integer value;

        EMsgType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="提现状态枚举">

    /**
     * 提现状态枚举
     */
    public enum EUserWithdrawStatus {
        提现中(0), 已完成(1), 已驳回(2);

        private Integer value;

        EUserWithdrawStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="提现类型枚举">

    /**
     * 提现类型枚举
     */
    public enum EUserWithdrawType {
        人工充值(0), 在线充值(1);

        private Integer value;

        EUserWithdrawType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="用户类型枚举">

    /**
     * 用户类型枚举
     */
    public enum EUserType {
        正常用户(0), 管理员用户(1);

        private Integer value;

        EUserType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="申请解锁审核状态枚举">

    /**
     * 申请解锁审核状态枚举
     */
    public enum EUserUnlockState {
        审核中(0), 已完成(1), 已驳回(1);

        private Integer value;

        EUserUnlockState(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>

    //<editor-fold desc="预约成功状态">

    /**
     * 预约成功状态
     */
    public enum EIsSuccess {
        未匹配(0), 成功(1), 失败(2), 已点击(3);

        private Integer value;

        EIsSuccess(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    //</editor-fold>
}