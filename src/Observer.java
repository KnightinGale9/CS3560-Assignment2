/*
Observer used in UserFrame as that is where we need to transfer the messages to
 */
public interface Observer {

    public void update(User user);
    public String toString();
}
