package Controllers;


import Model.*;
import org.springframework.*;
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
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;

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


    Network network ;
    ArrayList<HashMap<String, String[]>> newProductionNetwork = new ArrayList<>();
    @PostMapping("/generateNetwork")
    String generateNetwork(@RequestBody String productionNetwork){
        System.out.println("INSIDE GENERATE NETWORK");
        network = new Network();

        try {
            if (this.newProductionNetwork.size() > 1) {
                this.newProductionNetwork = new ArrayList<>();
            }

            TypeFactory factory = TypeFactory.defaultInstance();
            ObjectMapper map = new ObjectMapper();
            this.newProductionNetwork.add(map.readValue(productionNetwork, new TypeReference<HashMap<String, String[]>>() {}));


            System.out.println(this.newProductionNetwork);
            if (this.newProductionNetwork.size() == 2) {
                network.initialize(newProductionNetwork.get(0), newProductionNetwork.get(1));
            }

            return ("Network is generated successfully");
        }catch (Exception e){
            e.printStackTrace();
            return(e.getMessage());
        }

    }





    @GetMapping("/play")
    String play(){
        try {
            network.play();
            return ("SYSTEM WAS ACTIVATED SUCCESSFULLY");
        }catch (Exception e){
            return (e.getMessage());
        }
    }

    @GetMapping("/polling")
    ArrayList<Object> polling(){

        return network.getNetwork();
    }

    @GetMapping("/replay")
    ArrayList<Object> replay(){
        return network.replay();
    }

    @GetMapping("/stop")
    String stop(){
        network.stop();
        return ("SYSTEM WAS STOPPED SUCCESSFULLY");
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
