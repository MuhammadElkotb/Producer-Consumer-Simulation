package Controllers;

import java.util.ArrayList;

public class Memento {
    private ArrayList<Object> network;

    public Memento(ArrayList<Object> network){
        this.network = network;
    }
    public ArrayList<Object> getNetwork() {
        return network;
    }
}
