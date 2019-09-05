package cn.qinwh.wry.po;

import java.util.Date;

public class TrustOrder {
    private Integer id;

    private Integer itemId;

    private Integer waiterId;

    private Date transactionTime;

    private Integer state;

    private Integer satisfaction;

    private Double price;

    private Integer trustDuration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTrustDuration() {
        return trustDuration;
    }

    public void setTrustDuration(Integer trustDuration) {
        this.trustDuration = trustDuration;
    }
}