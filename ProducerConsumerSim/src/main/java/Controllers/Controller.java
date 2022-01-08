package Controllers;

import Model.BufferQueue;
import Model.ColorGenerator;
import javassist.expr.NewArray;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import Model.productionNetworkElement;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Component
@CrossOrigin("http://localhost:4200")
public class Controller {
    @PostMapping("/generateNetwork")
    String generateNetwork(@RequestBody ArrayList<MultivaluedMap<productionNetworkElement, productionNetworkElement>> productionNetwork){
        System.out.println("INSIDE GENERATE NETWORK");
        Network network = new Network();

        try {
            network.initialize(productionNetwork.get(0),productionNetwork.get(1));
            return ("Network is generated successfully");
        }catch (Exception e){
            return(e.getMessage());
        }

    }



    @PostMapping("/play")
    void generateColor(){
        Network.play();
    }



}
