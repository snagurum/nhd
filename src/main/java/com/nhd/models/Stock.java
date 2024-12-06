package com.nhd.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "stock", schema = "lt")
public class Stock {

    private String ticker;

}

