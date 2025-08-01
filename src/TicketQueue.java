import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TicketQueue {
    private final LinkedList<Integer> queue = new LinkedList<>();

    public void enqueue(int element) {
        queue.addLast(element);
    }

    public int dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        return queue.removeFirst();
    }

    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        return queue.getFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}