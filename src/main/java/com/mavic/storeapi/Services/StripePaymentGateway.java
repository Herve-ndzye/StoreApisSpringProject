package com.mavic.storeapi.Services;

import com.mavic.storeapi.entities.Order;
import com.mavic.storeapi.entities.OrderItem;
import com.mavic.storeapi.exceptions.PaymentException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class StripePaymentGateway implements PaymentGateway {
    BigDecimal exchangeRate = BigDecimal.valueOf(1513.7);

    @Value("${websiteUrl}")
    private String websiteUrl;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try{
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .putMetadata("order_id",order.getId().toString());
            order.getItems().forEach(item -> {
                BigDecimal unitAmountUsd = item.getUnitPrice()
                        .divide(exchangeRate, 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)
                        );
                var lineItem = createLineItem(item, unitAmountUsd);
                builder.addLineItem(lineItem);
            });
            return new CheckoutSession(Session.create(builder.build()).getUrl());
        }catch(StripeException e){
            System.out.println(e.getMessage());
            throw new PaymentException();
        }
    }

    private static SessionCreateParams.LineItem createLineItem(OrderItem item, BigDecimal unitAmountUsd) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item, unitAmountUsd))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item, BigDecimal unitAmountUsd) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(unitAmountUsd)
                .setProductData(createProductData(item))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
