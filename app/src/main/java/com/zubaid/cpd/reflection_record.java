package com.zubaid.cpd;

public class reflection_record {
    private String date;
    private String type;
    private String cdp;

    public reflection_record(String date, String type, String cdp) {
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
