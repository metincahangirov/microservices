package com.cybernetics.payment_ms.feign;

import com.cybernetics.payment_ms.dto.req.DecreaseCountReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${feign.clients.product-service.url}"
)
public interface ProductFeignClient {

    @GetMapping(value = "/users/counts/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    String getCountsProducts(@PathVariable String productId);


    @PutMapping(value = "/users/decrease-count", produces = MediaType.APPLICATION_JSON_VALUE)
    void decreaseCount(@RequestBody DecreaseCountReqDto decreaseCountReqDto);

}
