package com.nhd.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Ticker {
    @JsonProperty("SYMBOL") 
    private String symbol;

    @JsonProperty("NAME OF COMPANY") 
    private String company;

    @JsonProperty("SERIES") 
    private String series;

    @JsonProperty("DATE OF LISTING")
    private String dateOfListing;

    @JsonProperty("PAID UP VALUE") 
    private String paidUpValue;

    @JsonProperty("MARKET LOT")
    private String marketLot;

    @JsonProperty("ISIN NUMBER")
    private String isinNumber;

    @JsonProperty("FACE VALUE")
    private String faceValue;    

}
