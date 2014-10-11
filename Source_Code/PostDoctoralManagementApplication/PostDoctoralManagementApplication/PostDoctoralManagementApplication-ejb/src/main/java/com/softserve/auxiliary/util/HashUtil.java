/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softserve.auxiliary.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class implements hashing using the java.security.MessageDigest class
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class HashUtil {
    public static String hashInput(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(input.getBytes());
 
        byte byteData[] = md.digest();
 
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            hexString.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return hexString.toString();
    }
}
