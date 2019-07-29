package com.yz.common.payment.trade.pay.bo;

import java.math.BigDecimal;
import java.util.Date;

public class UserOrder {
    /**
     * 
     * 表 : user_order
     * 对应字段 : id
     */
    private Long id;

    /**
     * 订单号
     * 表 : user_order
     * 对应字段 : order_num
     */
    private String orderNum;

    /**
     * 第三方订单号
     * 表 : user_order
     * 对应字段 : third_order_num
     */
    private String thirdOrderNum;

    /**
     * 交易号
     * 表 : user_order
     * 对应字段 : trade_no
     */
    private String tradeNo;

    /**
     * 
     * 表 : user_order
     * 对应字段 : user_id
     */
    private Long userId;

    /**
     * 支付渠道 1-微信 2-支付宝
     * 表 : user_order
     * 对应字段 : pay_channel
     */
    private Integer payChannel;

    /**
     * 支付类型 1=微信JS API 2=APP支付 3=PC支付
     * 表 : user_order
     * 对应字段 : pay_type
     */
    private Integer payType;

    /**
     * 订单描述
     * 表 : user_order
     * 对应字段 : subject
     */
    private String subject;

    /**
     * 是否使用优惠券 0=未使用 1=使用
     * 表 : user_order
     * 对应字段 : use_coupon
     */
    private Boolean useCoupon;

    /**
     * 原价
     * 表 : user_order
     * 对应字段 : original_price
     */
    private BigDecimal originalPrice;

    /**
     * 实际支付金额
     * 表 : user_order
     * 对应字段 : actual_price
     */
    private BigDecimal actualPrice;

    /**
     * 1=待支付2=已支付3=申请退款4=退款中5=已退款 6=退款失败
     * 表 : user_order
     * 对应字段 : order_status
     */
    private Integer orderStatus;

    /**
     * 订单来源 1=微信公众号 2=微信小程序 3=iOS 4=安卓
     * 表 : user_order
     * 对应字段 : order_from
     */
    private Integer orderFrom;

    /**
     * 
     * 表 : user_order
     * 对应字段 : create_time
     */
    private Date createTime;

    /**
     * 
     * 表 : user_order
     * 对应字段 : update_time
     */
    private Date updateTime;

    /**
     * get method 
     *
     * @return user_order.id：
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public UserOrder withId(Long id) {
        this.setId(id);
        return this;
    }

    /**
     * set method 
     *
     * @param id  
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get method 
     *
     * @return user_order.order_num：订单号
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     *
     * @param orderNum
     */
    public UserOrder withOrderNum(String orderNum) {
        this.setOrderNum(orderNum);
        return this;
    }

    /**
     * set method 
     *
     * @param orderNum  订单号
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    /**
     * get method 
     *
     * @return user_order.third_order_num：第三方订单号
     */
    public String getThirdOrderNum() {
        return thirdOrderNum;
    }

    /**
     *
     * @param thirdOrderNum
     */
    public UserOrder withThirdOrderNum(String thirdOrderNum) {
        this.setThirdOrderNum(thirdOrderNum);
        return this;
    }

    /**
     * set method 
     *
     * @param thirdOrderNum  第三方订单号
     */
    public void setThirdOrderNum(String thirdOrderNum) {
        this.thirdOrderNum = thirdOrderNum == null ? null : thirdOrderNum.trim();
    }

    /**
     * get method 
     *
     * @return user_order.trade_no：交易号
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     *
     * @param tradeNo
     */
    public UserOrder withTradeNo(String tradeNo) {
        this.setTradeNo(tradeNo);
        return this;
    }

    /**
     * set method 
     *
     * @param tradeNo  交易号
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    /**
     * get method 
     *
     * @return user_order.user_id：
     */
    public Long getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public UserOrder withUserId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    /**
     * set method 
     *
     * @param userId  
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * get method 
     *
     * @return user_order.pay_channel：支付渠道 1-微信 2-支付宝
     */
    public Integer getPayChannel() {
        return payChannel;
    }

    /**
     *
     * @param payChannel
     */
    public UserOrder withPayChannel(Integer payChannel) {
        this.setPayChannel(payChannel);
        return this;
    }

    /**
     * set method 
     *
     * @param payChannel  支付渠道 1-微信 2-支付宝
     */
    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * get method 
     *
     * @return user_order.pay_type：支付类型 1=微信JS API 2=APP支付 3=PC支付
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     *
     * @param payType
     */
    public UserOrder withPayType(Integer payType) {
        this.setPayType(payType);
        return this;
    }

    /**
     * set method 
     *
     * @param payType  支付类型 1=微信JS API 2=APP支付 3=PC支付
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * get method 
     *
     * @return user_order.subject：订单描述
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject
     */
    public UserOrder withSubject(String subject) {
        this.setSubject(subject);
        return this;
    }

    /**
     * set method 
     *
     * @param subject  订单描述
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * get method 
     *
     * @return user_order.use_coupon：是否使用优惠券 0=未使用 1=使用
     */
    public Boolean getUseCoupon() {
        return useCoupon;
    }

    /**
     *
     * @param useCoupon
     */
    public UserOrder withUseCoupon(Boolean useCoupon) {
        this.setUseCoupon(useCoupon);
        return this;
    }

    /**
     * set method 
     *
     * @param useCoupon  是否使用优惠券 0=未使用 1=使用
     */
    public void setUseCoupon(Boolean useCoupon) {
        this.useCoupon = useCoupon;
    }

    /**
     * get method 
     *
     * @return user_order.original_price：原价
     */
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    /**
     *
     * @param originalPrice
     */
    public UserOrder withOriginalPrice(BigDecimal originalPrice) {
        this.setOriginalPrice(originalPrice);
        return this;
    }

    /**
     * set method 
     *
     * @param originalPrice  原价
     */
    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * get method 
     *
     * @return user_order.actual_price：实际支付金额
     */
    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    /**
     *
     * @param actualPrice
     */
    public UserOrder withActualPrice(BigDecimal actualPrice) {
        this.setActualPrice(actualPrice);
        return this;
    }

    /**
     * set method 
     *
     * @param actualPrice  实际支付金额
     */
    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    /**
     * get method 
     *
     * @return user_order.order_status：1=待支付2=已支付3=申请退款4=退款中5=已退款 6=退款失败
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     * @param orderStatus
     */
    public UserOrder withOrderStatus(Integer orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    /**
     * set method 
     *
     * @param orderStatus  1=待支付2=已支付3=申请退款4=退款中5=已退款 6=退款失败
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * get method 
     *
     * @return user_order.order_from：订单来源 1=微信公众号 2=微信小程序 3=iOS 4=安卓
     */
    public Integer getOrderFrom() {
        return orderFrom;
    }

    /**
     *
     * @param orderFrom
     */
    public UserOrder withOrderFrom(Integer orderFrom) {
        this.setOrderFrom(orderFrom);
        return this;
    }

    /**
     * set method 
     *
     * @param orderFrom  订单来源 1=微信公众号 2=微信小程序 3=iOS 4=安卓
     */
    public void setOrderFrom(Integer orderFrom) {
        this.orderFrom = orderFrom;
    }

    /**
     * get method 
     *
     * @return user_order.create_time：
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *
     * @param createTime
     */
    public UserOrder withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    /**
     * set method 
     *
     * @param createTime  
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * get method 
     *
     * @return user_order.update_time：
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *
     * @param updateTime
     */
    public UserOrder withUpdateTime(Date updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    /**
     * set method 
     *
     * @param updateTime  
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *
     * @param that
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserOrder other = (UserOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNum() == null ? other.getOrderNum() == null : this.getOrderNum().equals(other.getOrderNum()))
            && (this.getThirdOrderNum() == null ? other.getThirdOrderNum() == null : this.getThirdOrderNum().equals(other.getThirdOrderNum()))
            && (this.getTradeNo() == null ? other.getTradeNo() == null : this.getTradeNo().equals(other.getTradeNo()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getPayChannel() == null ? other.getPayChannel() == null : this.getPayChannel().equals(other.getPayChannel()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getUseCoupon() == null ? other.getUseCoupon() == null : this.getUseCoupon().equals(other.getUseCoupon()))
            && (this.getOriginalPrice() == null ? other.getOriginalPrice() == null : this.getOriginalPrice().equals(other.getOriginalPrice()))
            && (this.getActualPrice() == null ? other.getActualPrice() == null : this.getActualPrice().equals(other.getActualPrice()))
            && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
            && (this.getOrderFrom() == null ? other.getOrderFrom() == null : this.getOrderFrom().equals(other.getOrderFrom()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getThirdOrderNum() == null) ? 0 : getThirdOrderNum().hashCode());
        result = prime * result + ((getTradeNo() == null) ? 0 : getTradeNo().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getPayChannel() == null) ? 0 : getPayChannel().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getUseCoupon() == null) ? 0 : getUseCoupon().hashCode());
        result = prime * result + ((getOriginalPrice() == null) ? 0 : getOriginalPrice().hashCode());
        result = prime * result + ((getActualPrice() == null) ? 0 : getActualPrice().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getOrderFrom() == null) ? 0 : getOrderFrom().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    /**
     *
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", thirdOrderNum=").append(thirdOrderNum);
        sb.append(", tradeNo=").append(tradeNo);
        sb.append(", userId=").append(userId);
        sb.append(", payChannel=").append(payChannel);
        sb.append(", payType=").append(payType);
        sb.append(", subject=").append(subject);
        sb.append(", useCoupon=").append(useCoupon);
        sb.append(", originalPrice=").append(originalPrice);
        sb.append(", actualPrice=").append(actualPrice);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", orderFrom=").append(orderFrom);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}