package beanbags;

import java.io.Serializable;

/**
 * Simple structure for storing a reservation receipt
 * 
 * @author Louis Haddrell 
 */
public class Reservation implements Serializable
{
    private int id;
    private int priceInPence;
    private int quantity;

    public Reservation(int id, int priceInPence, int quantity) {
        this.id = id;
        this.priceInPence = priceInPence;
        this.quantity = quantity;
    }

    /**
     *  @return     reservation unique identification number
     */
    public int getID() {
        return this.id;
    }

    /**
     *  @return     price the bean bag was reserved at
     */
    public int getPrice() {
        return this.priceInPence;
    }

    /**
     *  Update the price of the reservation
     *  
     *  Bean bag reservations have a lowest-price guarantee so only modify
     *  the price if it is now lower than the previous lowest price
     *
     *  @param value    new price to apply
     */
    public void setPrice(int value) {
        if (value < this.priceInPence) {
            this.priceInPence = value;
        }
    }

    /**
     *  @return     quantity of bean bags reserved
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     *  @return     Calculated total value of this reservation
     */
    public int getValue() {
        return this.priceInPence * this.quantity;
    }
}