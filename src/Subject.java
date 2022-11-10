/*
Subjets are either Users which will hold the observers needed to be updated
 */
public interface Subject {
    public void attach(Observer observer);

    public void detach(Observer observer);

    public void notifyObservers();
}