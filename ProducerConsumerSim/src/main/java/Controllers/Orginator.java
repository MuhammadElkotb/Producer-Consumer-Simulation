package Controllers;

import java.util.ArrayList;

public class Orginator {
    private ArrayList<Object> network ;

    public void setNetwork(ArrayList<Object> network) {
        this.network = network;
    }

    public ArrayList<Object> getNetwork() {
        return network;
    }

    public Memento saveNetworktoMemento(){
        return new Memento(network);
    }

    public void getNetworkfromMemento(Memento snapshot){
        network = snapshot.getNetwork();
    }
}
