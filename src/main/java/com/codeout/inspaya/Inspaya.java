/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeout.inspaya;

import java.io.InputStream;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author azibit
 */
public class Inspaya {

    public static void main(String[] args) throws PayPalRESTException {

        System.out.println("Access Token: " + getToken());

        createPayment();
    }

    public static String getToken() throws PayPalRESTException {
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", "sandbox");
        String accessToken = new OAuthTokenCredential("AQkquBDf1zctJOWGKWUEtKXm6qVhueUEMvXO_-MCI4DQQ4-LWvkDLIN2fGsd", "EL1tVxAjhT7cJimnz5-Nsx9k2reTKSVfErNQF-CmrwJgxRtylkGTKlU4RvrX", sdkConfig).getAccessToken();
        return accessToken;

    }

    public static void createPayment() throws PayPalRESTException {
        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "sandbox");

        APIContext apiContext = new APIContext("Bearer A101.yR913IGFe-4nCUya4LmZKWQ9q5ZTqSISw2IbovLvYOtA1K6hyEN6ol-MuT9_O83W.26ddRbQ8PSXaZ7LwA9zZbF5mHhe");


        apiContext.setConfigurationMap(sdkConfig);

        CreditCardToken creditCardToken = new CreditCardToken();
        creditCardToken.setCreditCardId("CARD-9K64952054732681TK4NYV4A");

        FundingInstrument fundingInstrument = new FundingInstrument();
        fundingInstrument.setCreditCardToken(creditCardToken);

        List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
        fundingInstrumentList.add(fundingInstrument);

        Payer payer = new Payer();
        payer.setFundingInstruments(fundingInstrumentList);
        payer.setPaymentMethod("credit_card");

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("12");

        Transaction transaction = new Transaction();
        transaction.setDescription("creating a payment with saved credit card");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        Payment createdPayment = payment.create(apiContext);

        System.out.println("Last Request: " + Payment.getLastRequest());
        System.out.println("Last Response: " + Payment.getLastResponse());

    }

}
