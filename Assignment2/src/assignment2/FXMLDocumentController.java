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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public class FXMLDocumentController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void userLogin(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
         if (checkPass()) {
            verificationPassed();
        }else{
            failedLbl.setText("Incorrect Password!");
        }

    }

    private boolean checkPass() throws ClassNotFoundException, SQLException {
        byte[] salt;
        boolean checkpass = false;
        ResultSet rs = sqlCon().createStatement().executeQuery("SELECT * FROM pwStore");
        while (rs.next()) {
            Blob blob = rs.getBlob("salt");
            int blobLength = (int) blob.length();
            salt = blob.getBytes(1, blobLength);
            System.out.println(PasswordGen.getPass(pwField.getText(), salt));
            checkpass = PasswordGen.getPass(pwField.getText(), salt).equals(rs.getString("pw"));

        }
        return checkpass;
    }

    private Connection sqlCon() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://sql.computerstudi.es/gc200271677", "gc200271677", "Y-xX3iij");
        return con;
    }

    private void verificationPassed() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root, 600, 400));
        stage.showAndWait();
    }
}
