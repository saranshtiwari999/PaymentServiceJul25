package dev.saransh.paymentservicejul25.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratePaymentLinkRequestDTO {
    private long orderId;
}
