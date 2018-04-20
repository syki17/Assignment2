/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author jakub
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane mainPanel;
    @FXML
    private Button loginBtn;
    @FXML
    private TextField unField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Label failedLbl;
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //lambda to set listener to button
        loginBtn.setOnAction((event) -> {
            try {
                if (checkPass()) {
                    verificationPassed();
                } else {
                    failedLbl.setText("Incorrect Password or Username!");
                }
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
/**
 *  Checks if the password hashes match. Uses a salt that stored in DB 
 * @return True if passwords match, false if they don't 
 * @throws ClassNotFoundException
 * @throws SQLException 
 */
    private boolean checkPass() throws ClassNotFoundException, SQLException {
        byte[] salt;
        boolean checkpass = false;
        ResultSet rs = sqlCon().createStatement().executeQuery("SELECT * FROM pwStore");
        while (rs.next()) {
            Blob blob = rs.getBlob("salt");
            int blobLength = (int) blob.length();
            salt = blob.getBytes(1, blobLength);
            checkpass = PasswordGen.getPass(pwField.getText(), salt).equals(rs.getString("pw")) && unField.getText().equals(rs.getString("login"));

        }
        return checkpass;
    }

    /**
     * Build Connection object for remote DB access
     * @return Connection Object
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    private Connection sqlCon() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://sql.computerstudi.es/gc200271677", "gc200271677", "Y-xX3iij");
        return con;
    }

    /**
     * Leads main menu
     * @throws IOException 
     */
    private void verificationPassed() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root, 600, 600));
        stage.showAndWait();
    } 
}
