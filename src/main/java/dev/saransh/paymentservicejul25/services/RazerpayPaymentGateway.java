package dev.saransh.paymentservicejul25.services;


import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("razorpay")
public class RazerpayPaymentGateway implements PaymentService {

    @Autowired
    @Qualifier("razorpayclientconfig")
    private RazorpayClient razorpayClient;

//    public RazerpayPaymentGateway (RazorpayClient razorpayClient) {
//        this.razorpayClient = razorpayClient;
//    }

    @Override
    public String generatePaymentLink(Long orderId) throws RazorpayException {
        // Need to make a network call to order Service to confirm the amount.
        // We will learn this in authentication module.
        // We can do this using RestTemplate. Same thing that we used to call FakeStoreAPI.
        // As of now we will assume that we made a call and get the details.

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000); //10.00 rupees
        paymentLinkRequest.put("currency","INR");
//        paymentLinkRequest.put("accept_partial",true);
//        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",System.currentTimeMillis() + 10*60*1000); //Epoch
        paymentLinkRequest.put("reference_id",orderId.toString());
        paymentLinkRequest.put("description","Payment for test purpose");

        JSONObject customer = new JSONObject(); // user details will be user by UserService
        customer.put("name","Saransh Tiwari");
        customer.put("contact","8461316516");
        customer.put("email","saranshtiwari@scalar.com");
        paymentLinkRequest.put("customer",customer);

        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);

        paymentLinkRequest.put("reminder_enable",true);

//        JSONObject notes = new JSONObject();
//        notes.put("policy_name","Jeevan Bima");
//        paymentLinkRequest.put("notes",notes);

        paymentLinkRequest.put("callback_url","https://google.com");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

        return payment.toString();
    }
}
