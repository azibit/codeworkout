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
import java.util.List;
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
        InputStream is = Inspaya.class.getResourceAsStream("/sdk_config.properties");
        OAuthTokenCredential tokenCredential = Payment.initConfig(is);

        String accessToken = tokenCredential.getAccessToken();
        return accessToken;
    }

    public static Payment createPayment() {
		// ###Address
        // Base Address object used as shipping or billing
        // address in a payment. [Optional]
        Address billingAddress = new Address();
        billingAddress.setCity("Johnstown");
        billingAddress.setCountryCode("US");
        billingAddress.setLine1("52 N Main ST");
        billingAddress.setPostalCode("43210");
        billingAddress.setState("OH");

		// ###CreditCard
        // A resource representing a credit card that can be
        // used to fund a payment.
        CreditCard creditCard = new CreditCard();
        creditCard.setBillingAddress(billingAddress);
        creditCard.setCvv2(111);
        creditCard.setExpireMonth(11);
        creditCard.setExpireYear(2018);
        creditCard.setFirstName("Joe");
        creditCard.setLastName("Shopper");
        creditCard.setNumber("5500005555555559");
        creditCard.setType("mastercard");

		// ###Details
        // Let's you specify details of a payment amount.
        Details details = new Details();
        details.setShipping("1");
        details.setSubtotal("5");
        details.setTax("1");

		// ###Amount
        // Let's you specify a payment amount.
        Amount amount = new Amount();
        amount.setCurrency("USD");
        // Total must be equal to sum of shipping, tax and subtotal.
        amount.setTotal("7");
        amount.setDetails(details);

		// ###Transaction
        // A transaction defines the contract of a
        // payment - what is the payment for and who
        // is fulfilling it. Transaction is created with
        // a `Payee` and `Amount` types
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction
                .setDescription("This is the payment transaction description.");

		// The Payment creation API requires a list of
        // Transaction; add the created `Transaction`
        // to a List
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

		// ###FundingInstrument
        // A resource representing a Payeer's funding instrument.
        // Use a Payer ID (A unique identifier of the payer generated
        // and provided by the facilitator. This is required when
        // creating or using a tokenized funding instrument)
        // and the `CreditCardDetails`
        FundingInstrument fundingInstrument = new FundingInstrument();
        fundingInstrument.setCreditCard(creditCard);

		// The Payment creation API requires a list of
        // FundingInstrument; add the created `FundingInstrument`
        // to a List
        List<FundingInstrument> fundingInstrumentList = new ArrayList<>();
        fundingInstrumentList.add(fundingInstrument);

		// ###Payer
        // A resource representing a Payer that funds a payment
        // Use the List of `FundingInstrument` and the Payment Method
        // as 'credit_card'
        Payer payer = new Payer();
        payer.setFundingInstruments(fundingInstrumentList);
        payer.setPaymentMethod("credit_card");

		// ###Payment
        // A Payment Resource; create one using
        // the above types and intent as 'sale'
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        Payment createdPayment = null;
        try {
			// ###AccessToken
            // Retrieve the access token from
            // OAuthTokenCredential by passing in
            // ClientID and ClientSecret
            // It is not mandatory to generate Access Token on a per call basis.
            // Typically the access token can be generated once and
            // reused within the expiry window
            String accessToken = getToken();

			// ### Api Context
            // Pass in a `ApiContext` object to authenticate
            // the call and to send a unique request id
            // (that ensures idempotency). The SDK generates
            // a request id if you do not pass one explicitly.
            APIContext apiContext = new APIContext(accessToken);
			// Use this variant if you want to pass in a request id
            // that is meaningful in your application, ideally
            // a order id.
			/*
             * String requestId = Long.toString(System.nanoTime(); APIContext
             * apiContext = new APIContext(accessToken, requestId ));
             */

			// Create a payment by posting to the APIService
            // using a valid AccessToken
            // The return object contains the status;
            createdPayment = payment.create(apiContext);

            Logger.getLogger(Inspaya.class.getSimpleName()).log(Level.INFO, "Created payment with id = " + createdPayment.getId()
                    + " and status = " + createdPayment.getState());
            
            
            System.out.println("Last Request: " + Payment.getLastRequest());
            System.out.println("Last Response: " + Payment.getLastResponse());
        } catch (PayPalRESTException e) {
            Logger.getLogger(Inspaya.class.getSimpleName()).log(Level.SEVERE, "Error " + e.getMessage());
            e.printStackTrace();
        }
        return createdPayment;

    }

}
