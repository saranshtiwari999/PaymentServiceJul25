package dev.saransh.paymentservicejul25.services;

import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("stripe")
public class StripePaymentGateway implements PaymentService{

    @Value("${stripe.key.secret}")
    private String stripeKeySecret;
    public String generatePaymentLink(Long orderId) throws RazorpayException, StripeException {
        Stripe.apiKey = stripeKeySecret;

        PriceCreateParams createPriceParams =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        .setUnitAmount(10000L)
                        .setRecurring(
                                PriceCreateParams.Recurring.builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build()
                        )
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                        )
                        .build();
        Price price = Price.create(createPriceParams);

        PaymentLinkCreateParams createPaymentLinkParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(createPaymentLinkParams);
        return paymentLink.toString();

    }
}
