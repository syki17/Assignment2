package assignment2;

import java.math.BigDecimal;

public final class CoinInfo {

private String symbol;
private BigDecimal price;

/**
* No args constructor for use in serialization
*
*/


/**
*   Used so we can get a big decimal object
* @param price
* @param symbol
*/
public CoinInfo(String symbol, BigDecimal price) {
this.symbol = symbol;
this.price = price;
}

public String getSymbol() {
return symbol;
}

public void setSymbol(String symbol) {
this.symbol = symbol;
}

public BigDecimal getPrice() {
return price;
}

public void setPrice(BigDecimal price) {
this.price = price;
}

}