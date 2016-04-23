/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeout.inspaya;

import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

/**
 *
 * @author azibit
 */
public class Inspaya {

    public static void main(String[] args) throws PayPalRESTException {
        
        String CLIENT_ID = "AR7IAGiGhNlsJo97VbmADCSjMZUZB3wcorcjJaibM0ia88H"
                + "bPg4_owcN8Qi0dqOk0G2MKZO4BuOod7Y-";
        
        String CLIENT_SECRET = "EAkSAo-Ak_zU-oxTx6mSBFwDehlcOtwSStiHhfnAICqjC"
                + "W_4An0Wip98Lwi9793dbpiSaog_Xo_kp331";
        
        
        OAuthTokenCredential tokenCredential
                = new OAuthTokenCredential("<" + CLIENT_ID + ">", 
                        "<" + CLIENT_SECRET + ">");

        String accessToken = tokenCredential.getAccessToken();
        
        System.out.println("Access Token: " + accessToken);
    }

}
