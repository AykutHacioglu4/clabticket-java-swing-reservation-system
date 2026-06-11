package aoopproject1;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observers = new ArrayList<>(); // List of observers

    public void addObserver(Observer o) {
        observers.add(o); // Add observer to the list
    }

    public void removeObserver(Observer o) {
        observers.remove(o); // Remove observer from the list
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(); // Notify each observer
        }
    }
}
