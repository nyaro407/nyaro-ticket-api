public class TestRunner {
    public static void main(String[] args) {
        TicketQueue queue = new TicketQueue();

        // Test enqueue
        queue.enqueue(1);
        queue.enqueue(2);
        if (queue.size() != 2) {
            System.out.println("Erreur: taille incorrecte après enqueue");
            System.exit(1);
        }

        // Test peek
        if (queue.peek() != 1) {
            System.out.println("Erreur: peek incorrect");
            System.exit(1);
        }

        // Test dequeue
        if (queue.dequeue() != 1) {
            System.out.println("Erreur: dequeue incorrect");
            System.exit(1);
        }
        if (queue.size() != 1) {
            System.out.println("Erreur: taille après dequeue");
            System.exit(1);
        }

        // Test isEmpty
        queue.dequeue();
        if (!queue.isEmpty()) {
            System.out.println("Erreur: file non vide");
            System.exit(1);
        }

        System.out.println("Tous les tests passent avec succès !");
    }
}