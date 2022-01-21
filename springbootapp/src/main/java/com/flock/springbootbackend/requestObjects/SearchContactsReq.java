package com.flock.springbootbackend.requestObjects;

public class SearchContactsReq {
    private String prefix;
    private String orderby;
    private Boolean desc;

    public SearchContactsReq() {
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public Boolean getDesc() {
        return desc;
    }

    public void setDesc(Boolean desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SearchContactsReq{" +
                "prefix='" + prefix + '\'' +
                ", orderby='" + orderby + '\'' +
                ", desc=" + desc +
                '}';
    }
}
