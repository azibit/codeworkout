
import com.codeout.inspaya.Inspaya;
import com.paypal.base.rest.PayPalRESTException;
import junit.framework.Assert;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author azibit
 */
public class InspayaTest {
    
    
    @Test
    public void testAccessToken() throws PayPalRESTException{
        
        Assert.assertNotNull("Should not be bull", Inspaya.getToken());
    }
}
