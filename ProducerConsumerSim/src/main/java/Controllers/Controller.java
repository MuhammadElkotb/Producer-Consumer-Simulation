package Controllers;


import org.springframework.*;
import Model.BufferQueue;
import Model.ColorGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javassist.expr.NewArray;
import net.bytebuddy.description.method.MethodDescription;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import Model.productionNetworkElement;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.sse.Sse;
import java.lang.reflect.Type;
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
    @PostMapping("/generateNetwork")
    String generateNetwork(@RequestBody Map<String, Object> productionNetwork){
        System.out.println("INSIDE GENERATE NETWORK");
      //  Network network = new Network();

        try {
            Gson gson = new Gson();

            ArrayList<MultivaluedHashMap<String, String>> newProductionNetwork = new ArrayList<>();
            TypeFactory factory = TypeFactory.defaultInstance();

            System.out.println(productionNetwork);

            ObjectMapper map = new ObjectMapper();




            // network.initialize(productionNetwork.get(0),productionNetwork.get(1));
            return ("Network is generated successfully");
        }catch (Exception e){

            e.printStackTrace();
            return(e.getMessage());
        }

    }


    @GetMapping("/getBuffer")
    ArrayList<BufferQueue> getBuffer(){
        return network.getBufferQueues();
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
