package Controllers;

import java.util.ArrayList;

public class Orginator {
    private Object network ;

    public void setNetwork(Object network) {
        this.network = network;
    }

    public Object getNetwork() {
        return network;
    }

    public Memento saveNetworktoMemento(){
        return new Memento(network);
    }

    public void getNetworkfromMemento(Memento snapshot){
        network = snapshot.getNetwork();
    }
}
