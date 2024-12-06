package com.nhd.util;

public enum JobName {
    TICKER,
    DSP_TICKER,
    BSP_TICKER;

    public String getJobType(){
        return "Loader";
    }
}
