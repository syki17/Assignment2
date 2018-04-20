package assignment2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinData {

@SerializedName("symbol")
@Expose
private String symbol;
@SerializedName("price")
@Expose
private String price;

/**
* No args constructor for use in serialization
*
*/
public CoinData() {
}

/**
*
* @param price
* @param symbol
*/
public CoinData(String symbol, String price) {
this.symbol = symbol;
this.price = price;
}

public String getSymbol() {
return symbol;
}

public void setSymbol(String symbol) {
this.symbol = symbol;
}

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

}