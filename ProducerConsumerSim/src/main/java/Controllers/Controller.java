package Controllers;

import Model.BufferQueue;
import Model.ColorGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import Model.productionNetworkElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Component
@CrossOrigin("http://localhost:4200")
public class Controller {
    @PostMapping("/generateNetwork")
    String generateNetwork(@RequestBody ArrayList<HashMap<productionNetworkElement, productionNetworkElement>> productionNetwork){
        System.out.println("INSIDE GENERATE NETWORK");
        try {
            Network.initialize(productionNetwork.get(0),productionNetwork.get(1));
            return ("Network is generated successfully");
        }catch (Exception e){
            return(e.getMessage());
        }

    }



    @PostMapping("/play")
    BufferQueue generateColor(){
        while(true){
            return Network.play();
        }
    }



}
