package com.msm.floodoor;

import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.DynamoDBList;


public class NoticeItem {
    private String nid;
    private String title;
    private String desc;
    private String uri;

    public String getDesc() {
        return desc;
    }

    public String getNid() {
        return nid;
    }

    public String getTitle() {
        return title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
