/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class MainMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            buildGson();
        } catch (UnirestException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getCoins() throws UnirestException {
        Unirest.setTimeouts(240000, 600000);
        HttpResponse<String> response = Unirest.get("https://api.binance.com/api/v3/ticker/price")
                .header("cache-control", "no-cache")
                .asString();
        return response.getBody();
    }

    private void buildGson() throws UnirestException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new Gson();
        gson = builder.serializeNulls().create();
       
               CallResult[] coinData = gson.fromJson(getCoins(), CallResult[].class);
       List<coinInfo> coins = coinData
               .filter(coin -> coin.getBaseCurrency().equals("Bitcoin"))
                .collect(Collectors.toList());
       
        
                System.out.println(coins);
        
              
       
                
          
        

    }

}
