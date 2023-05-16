package com.zubaid.cpd;

public class activity_Record {
    private String date;
    private String type;
    private String cdp;

    public activity_Record(String date, String type, String cdp) {
        this.date = date;
        this.type = type;
        this.cdp = cdp;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getCdp() {
        return cdp;
    }
}
