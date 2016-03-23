package beanbags;

public class Reservation
{
    private int id;
    private int priceInPence;
    private int quantity;

    public Reservation(int id, int priceInPence, int quantity) {
        this.id = id;
        this.priceInPence = priceInPence;
        this.quantity = quantity;
    }

    /*  Getters and Setters
    **************************************************************************/
    public int getID() {
        return this.id;
    }

    public int getPrice() {
        return this.priceInPence;
    }

    public int getQuantity() {
        return this.quantity;
    }
}