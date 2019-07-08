package org.ss.server.lib;


import java.util.LinkedList;
import java.util.List;

public class JSONTemplateResponse {

    public JSONTemplateResponse() {

    }

    private boolean requestOk;
    private String responseContent;

    public List<Object> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Object> recordList) {
        this.recordList = recordList;
    }

    private List<Object> recordList = new LinkedList<Object>();

    public boolean isRequestOk() {
        return requestOk;
    }

    public void setRequestOk(boolean requestOk) {
        this.requestOk = requestOk;
    }

    public JSONTemplateResponse(boolean status, String responseContent) {
        this.requestOk = status;
        this.responseContent = responseContent;
    }


    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

}