package Controllers;

import java.util.ArrayList;

public class CareTaker {
    private ArrayList<Memento> backup = new ArrayList<>();

    public CareTaker(){}

    public void addSnapshot(Memento snapshot){
        backup.add(snapshot);
    }

    public Memento getSnapshot(int index) {
        return backup.get(index);
    }
    public int getSize(){
        return backup.size();
    }

    public void clear(){
        backup = new ArrayList<>();
    }
}
