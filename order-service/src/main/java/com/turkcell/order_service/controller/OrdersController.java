package com.turkcell.order_service.controller;

import com.turkcell.common.event.order.OrderCreatedEvent;
import com.turkcell.order_service.client.ProductClient;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final ProductClient productClient;
    private final StreamBridge streamBridge;
    public OrdersController(ProductClient productClient, StreamBridge streamBridge) {
        this.productClient = productClient;
        this.streamBridge = streamBridge;
    }

    @GetMapping
    public String get() {
        // Syncİletişim
        //String response = productClient.get();
        //System.out.println(response);

        // TODO: Kafkadan common bir classı gönder.
        //Order order = new Order();
        //order.setId("abc123");
        //
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent("abc123", LocalDate.now());

        streamBridge.send("orderCreatedFunction-out-0",orderCreatedEvent);
        return "Order Service";
    }
}
