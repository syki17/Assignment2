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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private List<coinInfo> coinList = new ArrayList<>();
    @FXML
    private TextField regexMatchField;
    @FXML
    private Button updateBtn;
    @FXML
    private CheckBox newDataCheckBox;
    @FXML
    private Label btcPairsLbl;
    @FXML
    private Label ethPairsLbl;
    private Label ltcPairsLbl;
    @FXML
    private Label usdtPairsLbl;
    @FXML
    private Label bnbPairsLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setCellValues();
            populate("");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //lambda to set listener to button
        updateBtn.setOnAction((event) -> {
            try {
                updateFields();
            } catch (UnirestException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private String getCoins() throws UnirestException {
        Unirest.setTimeouts(240000, 600000);
        HttpResponse<String> response = Unirest.get("https://api.binance.com/api/v3/ticker/price")
                .header("cache-control", "no-cache")
                .asString();
        return response.getBody();
    }
/**
 * Build Gson object, stores it into a pojo class, and 
 * @throws UnirestException
 * @throws ClassNotFoundException
 * @throws SQLException 
 */
    private void buildGson() throws UnirestException, ClassNotFoundException, SQLException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new Gson();
        gson = builder.serializeNulls().create();
        Connection con = sqlCon(); // so we dont have to call the method everytime in the for loop.
        con.createStatement().execute("DELETE FROM coinData");//clears the old data from the table, since theres no timestamps with the api call.
        CoinData[] coinData = gson.fromJson(getCoins(), CoinData[].class);
        Statement statement = con.createStatement();
        for (CoinData c : coinData) {
            statement.addBatch("INSERT INTO coinData VALUES('" + c.getSymbol() + "','" + new BigDecimal(c.getPrice()) + "')");
        }
        statement.executeBatch();
    }

    /**
     * Set the Cell values for the tableview
     */
    private void setCellValues() {
        coinTableSymbol.setCellValueFactory(
                new PropertyValueFactory<>("symbol"));
        coinTablePrice.setCellValueFactory(
                new PropertyValueFactory<>("price"));
    }

    /**
     * Populate the table. takes a regex string
     *
     * @param regex value taken from the regexMatchField
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void populate(String regex) throws ClassNotFoundException, SQLException {

        data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM coinData";
        ResultSet rs = sqlCon().createStatement().executeQuery(sql);
        while (rs.next()) {
            coinInfo coin = new coinInfo(rs.getString("symbol"), rs.getBigDecimal("price"));
            data.add(coin);
            coinList.add(coin);
        }
        coinList.removeIf(s -> s.getSymbol().matches(regex));
        btcPairsLbl.setText("Number of BTC Base Pairs: " + countMatches(".*BTC"));
        ethPairsLbl.setText("Number of ETH Base Pairs: " + countMatches(".*ETH"));
        usdtPairsLbl.setText("Number of USDT Base Pairs: " + countMatches(".*USDT"));
        bnbPairsLbl.setText("Number of BNB Base Pairs: " + countMatches(".*BNB"));
        coinList.clear();
        data.removeIf(s -> s.getSymbol().matches(regex));
        coinTable.setItems(data);
    }

    /**
     * Counts the number of base pairs in the table
     *
     * @param toMatch Regex to filter out the base pair
     * @return
     */
    private long countMatches(String toMatch) {
        long count = coinList.stream()
                .filter(f -> f.getSymbol().matches(toMatch))
                .count();

        return count;
    }

    /**
     * Builds Connection object to connect to remote DB
     *
     * @return Connection Object
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
    /**
     * Updates tableview fields. Gets fresh data id new data checkbox is selected.
     *
     * @throws UnirestException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void updateFields() throws UnirestException, ClassNotFoundException, SQLException {
        if (newDataCheckBox.isSelected()) {
            buildGson();
        }
        populate(regexMatchField.getText());
    }

}
