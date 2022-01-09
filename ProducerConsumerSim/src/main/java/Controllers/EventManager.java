package Controllers;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
    private HashMap<String,machineObserver> listeners;
    private static EventManager instance = null;
    private EventManager(){
        this.listeners = new HashMap<>();
    }
    public static EventManager getInstance(){
        if(instance == null){
            return new EventManager();
        }else {
            return instance;
        }
    }
    public void addListeners(String machineName, machineObserver listener) {
        this.listeners.put(machineName,listener);
    }

    public void removeListeners(machineObserver listener) {
        this.listeners.remove(listener);
    }
    public void notify(String machineName) {
        listeners.get(machineName).update();
    }
}
