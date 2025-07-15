package dev.saransh.paymentservicejul25.configs;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import dev.saransh.paymentservicejul25.services.RazerpayPaymentGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayClientConfig {
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value(("${razorpay.key.secret}"))
    private String razorpaySecret;

    @Bean("razorpayclientconfig")
    public RazorpayClient createRazorPayClient () throws RazorpayException {
        return new RazorpayClient(razorpayKeyId, razorpaySecret);
        // As of now these id and secret are 7 month old thus will not be able to authenticate.
        // We will get a BAD_REQUEST_ERROR:Authentication failed
        // Apart from that rest of the implementation is fine.
    }
}
