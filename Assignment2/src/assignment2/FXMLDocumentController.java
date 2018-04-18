/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author jakub
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane mainPanel;
    @FXML
    private Label label;
    @FXML
    private Button loginBtn;
    @FXML
    private TextField unField;
    @FXML
    private PasswordField pwField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void userLogin(ActionEvent event) throws ClassNotFoundException, SQLException {
       // byte[] salt = PasswordGen.getSalt();

         
         checkPass("s");
         
        
    }

    private void checkPass(String pw) throws ClassNotFoundException, SQLException {
        byte[] salt;
        ResultSet rs = sqlCon().createStatement().executeQuery("SELECT * FROM pwStore");
        while(rs.next()){
           Blob blob = rs.getBlob("salt");
            int blobLength = (int) blob.length();
           salt = blob.getBytes(1, blobLength);
           System.out.println(PasswordGen.getPass(pwField.getText(), salt));
           if (PasswordGen.getPass(pwField.getText(), salt).equals(rs.getString("pw"))){
               System.out.println("YES");
               
           }
           
        }
        
    }

    private Connection sqlCon() throws ClassNotFoundException, SQLException {
         Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://sql.computerstudi.es/gc200271677", "gc200271677", "Y-xX3iij");
        return con;
    }
}
