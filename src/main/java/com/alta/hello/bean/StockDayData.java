package com.alta.hello.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by baiba on 2019-06-20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDayData {
    //代码
    private int code;
    //开票价
    private Date statDate;
    //开盘价格
    private double startPrice;
    //收盘价格
    private double endPrice;
    //涨跌金额
    private double changePrice;
    //涨跌比率
    private double changeRatio;
    //最低价
    private double lowPrice;
    //最高价
    private double highPrice;
    //总手_万
    private int totalHand;
    //换手率
    private double handRate;
}
