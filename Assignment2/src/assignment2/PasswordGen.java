/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author jakub
 */
public class PasswordGen {

    /**
     * Generates a random salt
     * @return salt byte
     */
    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
/**
 * Generates a hashed pw using a string and a salt
 * @param pass
 * @param salt
 * @return hashed Password 
 */
    public static String getPass(String pass, byte[] salt) {
        String hashedPw = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);

            byte[] hashed = md.digest(pass.getBytes());

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < hashed.length; i++) {

                sb.append(String.format("%02x", hashed[i]));
            }

            hashedPw = sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return hashedPw;
    }
}
