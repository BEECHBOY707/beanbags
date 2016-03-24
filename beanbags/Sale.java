package beanbags;

import java.io.Serializable;

public class Sale implements Serializable
{
    private int priceInPence;
    private int quantity;

    public Sale(int priceInPence, int quantity) {
        this.priceInPence = priceInPence;
        this.quantity = quantity;
    }
}