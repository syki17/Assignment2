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
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class MainMenuController implements Initializable {

    @FXML
    private TableView<coinInfo> coinTable;
    @FXML
    private TableColumn<coinInfo, String> coinTableSymbol;
    @FXML
    private TableColumn<coinInfo, Double> coinTablePrice;

    private ObservableList<coinInfo> data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection con = sqlCon(); // so we dont have to call the method everytime in the for loop.
            con.createStatement().execute("DELETE FROM coinData");//clears the old data from the table, since theres no timestamps with the api call.
            for (CoinData c : buildGson()) {

                con.createStatement().execute("INSERT INTO coinData VALUES('" + c.getSymbol() + "','" + new BigDecimal(c.getPrice()) + "')");
            }
            setCellValues();
            populate();

        } catch (UnirestException | ClassNotFoundException | SQLException ex) {
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

    private CoinData[] buildGson() throws UnirestException, ClassNotFoundException, SQLException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new Gson();
        gson = builder.serializeNulls().create();

        CoinData[] coinData = gson.fromJson(getCoins(), CoinData[].class);
        return coinData;

    }

    private void setCellValues() {
        coinTableSymbol.setCellValueFactory(
                new PropertyValueFactory<>("symbol"));
        coinTablePrice.setCellValueFactory(
                new PropertyValueFactory<>("price"));
    }

    private void populate() throws ClassNotFoundException, SQLException {

        data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM coinData";
        ResultSet rs = sqlCon().createStatement().executeQuery(sql);
        while (rs.next()) {
            coinInfo coin = new coinInfo(rs.getString("symbol"), rs.getBigDecimal("price"));
            data.add(coin);

        }
        coinTable.setItems(data);
    }

    private Connection sqlCon() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://sql.computerstudi.es/gc200271677", "gc200271677", "Y-xX3iij");
        return con;
    }

    /*
               CallResult[] coinData = gson.fromJson(getCoins(), CallResult[].class);
       List<coinInfo> coins = coinData
               .filter(coin -> coin.getBaseCurrency().equals("Bitcoin"))
                .collect(Collectors.toList());
       
        
                System.out.println(coins);
     */
}
