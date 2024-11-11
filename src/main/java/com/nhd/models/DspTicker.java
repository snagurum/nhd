package com.nhd.models;

import lombok.Data;

@Data
public class DspTicker {
    
 
    private String ticker ;
    private String companyDetails ;
    private String tradeDetails ;
    private Boolean isSuccess = Boolean.FALSE;



    public DspTicker(String ticker, String companyInfo, String tradeInfo){
        this.ticker = ticker;
        this.companyDetails = companyInfo;
        this.tradeDetails = tradeInfo;
        this.isSuccess = Boolean.TRUE;
    }

    public DspTicker(String ticker){
        this.ticker = ticker;
    }
}
