package co.edu.escuelaing.app.roundrobin.controller;


import co.edu.escuelaing.app.roundrobin.model.MessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("")
public class LoadBalancerController {

    private final AtomicInteger counter = new AtomicInteger(0);


    private String logServiceUrl1 = "http://springdockercompose:6000/logservice";

    private String logServiceUrl2 = "http://springdockercompose1:6000/logservice" ;

    private String logServiceUrl3 = "http://springdockercompose2:6000/logservice";

    private String[] getLogServiceUrls() {
        return new String[]{logServiceUrl1, logServiceUrl2, logServiceUrl3};
    }

    @GetMapping
    public String index(){
        return "index";
    }

    @PostMapping("/sendmessage")
    public ResponseEntity<String> forwardRequest(@RequestBody MessageRequest messageRequest) {
        String[] logServiceUrls = getLogServiceUrls();
        int serviceIndex = counter.getAndIncrement() % logServiceUrls.length;
        String targetUrl = logServiceUrls[serviceIndex];

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(targetUrl, messageRequest, String.class);

        int colonIndex = targetUrl.lastIndexOf(':');
        int slashIndex = targetUrl.indexOf('/', colonIndex);
        String port = targetUrl.substring(colonIndex + 1, slashIndex);

        String responder = "Servicio en el puerto " + port;
        String responseBody = response.getBody();
        String responseWithInfo = String.format("%s respondió: %s", responder, responseBody);

        return ResponseEntity.ok(responseWithInfo);
    }

}
