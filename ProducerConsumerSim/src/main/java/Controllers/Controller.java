package Controllers;

import Model.BufferQueue;
import Model.ColorGenerator;
import javassist.expr.NewArray;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import Model.productionNetworkElement;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.sse.Sse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Flux;

@Component
@RestController
@CrossOrigin("http://localhost:4200")
public class Controller {

    Network network = new Network(6);
    int networkctr = network.getCtr();
    @PostMapping("/generateNetwork")
    String generateNetwork(@RequestBody ArrayList<MultivaluedMap<productionNetworkElement, productionNetworkElement>> productionNetwork){
        System.out.println("INSIDE GENERATE NETWORK");
      //  Network network = new Network();

        try {
            network.initialize(productionNetwork.get(0),productionNetwork.get(1));
            return ("Network is generated successfully");
        }catch (Exception e){
            return(e.getMessage());
        }

    }



    @GetMapping("/getBuffer")
    ArrayList<BufferQueue> getBuffer(){
        if(network.getCtr() > networkctr){
            networkctr = network.getCtr();
            return network.getBufferQueues();
        }
        return null;
    }

    @PostMapping("/play")
    void play(){
        network.play();
    }



    /*@RequestMapping(value = "/play", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ArrayList<BufferQueue>> getBuffers(){
        network.play();
        System.out.println("lol");
        bufferQueues = network.getBufferQueues();
        Flux<ArrayList<BufferQueue>> ret = networkService.getBuffers(bufferQueues, network);
        return ret;

    }*/









}
