package dev.saransh.paymentservicejul25.controllers;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import dev.saransh.paymentservicejul25.dtos.GeneratePaymentLinkRequestDTO;
import dev.saransh.paymentservicejul25.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    @Qualifier("stripe")
    private PaymentService paymentService;

//    public PaymentController(PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }

    @PostMapping("/payments") //called by the client(customer)
    public String generatePaymentLink(@RequestBody GeneratePaymentLinkRequestDTO generatePaymentLinkRequestDTO) throws RazorpayException, StripeException {
        return paymentService.generatePaymentLink(generatePaymentLinkRequestDTO.getOrderId());
    }

    @PostMapping("/webhook") // After payment status change on razorpay page, this api will be hit by razorpay
    public void handleWebhook(@RequestBody Object object) {
        //Here you can confirm the status if its success or fail or canceled and accordingly you can update the order using orderid.
    }

}
