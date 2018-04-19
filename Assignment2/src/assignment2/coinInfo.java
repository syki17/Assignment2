package assignment2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class coinInfo {

@SerializedName("Id")
@Expose
private Integer id;
@SerializedName("Label")
@Expose
private String label;
@SerializedName("Currency")
@Expose
private String currency;
@SerializedName("Symbol")
@Expose
private String symbol;
@SerializedName("BaseCurrency")
@Expose
private String baseCurrency;
@SerializedName("BaseSymbol")
@Expose
private String baseSymbol;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("StatusMessage")
@Expose
private String statusMessage;
@SerializedName("TradeFee")
@Expose
private String tradeFee;
@SerializedName("MinimumTrade")
@Expose
private String minimumTrade;
@SerializedName("MaximumTrade")
@Expose
private String maximumTrade;
@SerializedName("MinimumBaseTrade")
@Expose
private String minimumBaseTrade;
@SerializedName("MaximumBaseTrade")
@Expose
private String maximumBaseTrade;
@SerializedName("MinimumPrice")
@Expose
private String minimumPrice;
@SerializedName("MaximumPrice")
@Expose
private String maximumPrice;

/**
* No args constructor for use in serialization
*
*/
public coinInfo() {
}

/**
*
* @param maximumPrice
* @param maximumTrade
* @param symbol
* @param status
* @param baseSymbol
* @param maximumBaseTrade
* @param baseCurrency
* @param label
* @param minimumBaseTrade
* @param currency
* @param statusMessage
* @param minimumTrade
* @param id
* @param minimumPrice
* @param tradeFee
*/
public coinInfo(Integer id, String label, String currency, String symbol, String baseCurrency, String baseSymbol, String status, String statusMessage, String tradeFee, String minimumTrade, String maximumTrade, String minimumBaseTrade, String maximumBaseTrade, String minimumPrice, String maximumPrice) {
super();
this.id = id;
this.label = label;
this.currency = currency;
this.symbol = symbol;
this.baseCurrency = baseCurrency;
this.baseSymbol = baseSymbol;
this.status = status;
this.statusMessage = statusMessage;
this.tradeFee = tradeFee;
this.minimumTrade = minimumTrade;
this.maximumTrade = maximumTrade;
this.minimumBaseTrade = minimumBaseTrade;
this.maximumBaseTrade = maximumBaseTrade;
this.minimumPrice = minimumPrice;
this.maximumPrice = maximumPrice;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getLabel() {
return label;
}

public void setLabel(String label) {
this.label = label;
}

public String getCurrency() {
return currency;
}

public void setCurrency(String currency) {
this.currency = currency;
}

public String getSymbol() {
return symbol;
}

public void setSymbol(String symbol) {
this.symbol = symbol;
}

public String getBaseCurrency() {
return baseCurrency;
}

public void setBaseCurrency(String baseCurrency) {
this.baseCurrency = baseCurrency;
}

public String getBaseSymbol() {
return baseSymbol;
}

public void setBaseSymbol(String baseSymbol) {
this.baseSymbol = baseSymbol;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getStatusMessage() {
return statusMessage;
}

public void setStatusMessage(String statusMessage) {
this.statusMessage = statusMessage;
}

public String getTradeFee() {
return tradeFee;
}

public void setTradeFee(String tradeFee) {
this.tradeFee = tradeFee;
}

public String getMinimumTrade() {
return minimumTrade;
}

public void setMinimumTrade(String minimumTrade) {
this.minimumTrade = minimumTrade;
}

public String getMaximumTrade() {
return maximumTrade;
}

public void setMaximumTrade(String maximumTrade) {
this.maximumTrade = maximumTrade;
}

public String getMinimumBaseTrade() {
return minimumBaseTrade;
}

public void setMinimumBaseTrade(String minimumBaseTrade) {
this.minimumBaseTrade = minimumBaseTrade;
}

public String getMaximumBaseTrade() {
return maximumBaseTrade;
}

public void setMaximumBaseTrade(String maximumBaseTrade) {
this.maximumBaseTrade = maximumBaseTrade;
}

public String getMinimumPrice() {
return minimumPrice;
}

public void setMinimumPrice(String minimumPrice) {
this.minimumPrice = minimumPrice;
}

public String getMaximumPrice() {
return maximumPrice;
}

public void setMaximumPrice(String maximumPrice) {
this.maximumPrice = maximumPrice;
}

}