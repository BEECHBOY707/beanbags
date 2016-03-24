package beanbags;

import java.io.Serializable;

/**
 * This BeanBag class contains details for every different BeanBag product, including its key specification, and any reservation or sold BeanBag information.
 * 
 * @author Max Beech, Louis Haddrell
 * @version 1.0
 */
public class BeanBag implements Serializable
{
    private static int totalReservations = 0;

    private byte month;
    private int priceInPence;
    private int reservedCount;
    private int soldValue;
    private int soldCount;
    private int stockCount;
    private ObjectArrayList reservations;
    private short year;
    private String id;
    private String information;
    private String manufacturer;
    private String name;  

    public BeanBag(int num, String manufacturer, String name, String id,
                   short year, byte month) {

        this(num, manufacturer, name, id, year, month, "");
    }

    public BeanBag(int num, String manufacturer, String name, String id,
                   short year, byte month, String information) {

        this.stockCount = num;
        this.manufacturer = manufacturer;
        this.name = name;
        this.id = id;
        this.year = year;
        this.month = month;
        this.information = information;

        this.reservations = new ObjectArrayList();
        this.priceInPence = -1;
    }

    /**
     *  Simple reservation ID generator.
     *  @return     unique reservation
     */
    public static int generateReservationID() {
        return ++BeanBag.totalReservations;
    }


    /**
     * @return      reservations ObjectArrayList
     */
    public ObjectArrayList getReservations() {
        return this.reservations;
    }

    /**
     * @return      bean bag's month of manufacture
     */
    public byte getMonth() {
        return this.month;
    }

    /**
     * @param value     new month of manufacture
     */
    public void setMonth(byte value) {
        this.month = value;
    }

    /**
     * @return      bean bag's price in pence
     */
    public int getPrice() {
        return this.priceInPence;
    }

    /**
     * Update bean bag price plus all reservations
     * @param value     new bean bag price in pence
     */
    public void setPrice(int value) {
        this.priceInPence = value;

        for (int i=0; i < this.reservations.size(); i++){
            Reservation reservation = (Reservation) this.reservations.get(i);
            reservation.setPrice(value);
        }       
    }

    /**
     * @return      available stock (reserved and unreserved)
     */
    public int getStockCount() {
        return this.stockCount;
    }

    /**
     * @param value     new bean bag stock count
     */
    public void setStockCount(int value) {
        this.stockCount = value;
    }

    /**
     * @return      bean bag's year of manufacture
     */
    public short getYear() {
        return this.year;
    }

    /**
     * @param value     new year of manufacture
     */
    public void setYear(short value) {
        this.year = value;
    }

    /**
     * @return      bean bag's identification string
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param value      new identification string
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * @return      description field
     */
    public String getInformation() {
        return this.information;
    }

    /**
     * @param value     new description value 
     */
    public void setInformation(String value) {
        this.information = value;
    }

    /**
     * @return      bean bag's manufacturer
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * @param value     new manufacturer
     */
    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    /**
     * @return      bean bag's model name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param value     new model name
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * @return      Boolean representing whether price has been set
     */
    public Boolean hasPrice() {
        return this.priceInPence != -1;
    }

    /**
     * @return      Boolean representing whether there are any available bags
     */
    public Boolean inStock() {
        return this.availableCount() > 0;
    }

    /**
     * @return      Count of available (unreserved) stock
     */
    public int availableCount() {
        return this.stockCount - this.reservedCount;
    }

    /**
     * @return      Count of reserved stock
     */
    public int getReservedCount() {
        return this.reservedCount;
    }

    /**
     * @return      Count of number of bean bags sold
     */
    public int getSoldCount() {
        return this.soldCount;
    }

    /**
     * @return      Running total of all sales (in pence)
     */
    public int getSoldValue() {
        return this.soldValue;
    }

    /**
     * @return      Calculated total value of all reservations
     */
    public int getReservationValue() {
        int totalValue = 0;

        for (int i=0; i < this.reservations.size(); i++){
            Reservation reservation = (Reservation) this.reservations.get(i);
            totalValue += reservation.getValue();
        }

        return totalValue;
    }

    /**
     *  Reset counter tracking sales
     */
    public void reset() {
        this.soldCount = 0;
        this.soldValue = 0;
    }

    /**
     *  Purge this bean bag object of all sales, reservations and price
     */
    public void empty() {
        reset();
        this.priceInPence = -1;
        this.reservedCount = 0;
        this.stockCount = 0;
        this.reservations = new ObjectArrayList();
    }

    /**
     *  Sell a number of bean bags at the current price
     *  @param quantity     Number of bean bags to sell
     */    
    public void sell(int quantity) {
        this.sell(this.priceInPence, quantity);
    }

    /**
     *  Sell a number of bean bags at an arbitrary price
     *  @param price        Price (in pence) to make sale at
     *  @param quantity     Number of bean bags to sell
     */  
    private void sell(int price, int quantity) {
        this.stockCount -= quantity;
        this.soldCount += quantity;
        this.soldValue += price * quantity;        
    }

    /**
     *  Make a sale from a reservation
     *  @param reservationID    unique ID number of the reservation
     *  @return                 true if reservation was attached to this object,
     *                          false otherwise
     */ 
    public Boolean sellReservation(int reservationID) {
        for (int i=0; i < this.reservations.size(); i++){
            Reservation reservation = (Reservation) this.reservations.get(i);
            if (reservation.getID() == reservationID) {
                this.sell(reservation.getPrice(), reservation.getQuantity());

                this.reservedCount -= reservation.getQuantity();
                this.reservations.remove(i);                
                return true;
            }
        }

        return false;        
    }

    /**
     *  Reserve a number of bean bags
     *  @param quantity     number of bags to reserve
     *  @return             unique ID number of the reservation
     */ 
    public int reserve(int quantity) {
        int id = BeanBag.generateReservationID();
        this.reservations.add(new Reservation(id, this.priceInPence, quantity));
        this.reservedCount += quantity;
        return id;
    }

    /**
     *  Cancel a reservation
     *  @param reservationID    unique ID number of the reservation
     *  @return                 true if reservation was attached to this object,
     *                          false otherwise
     */ 
    public Boolean unreserve(int reservationID) {
        for (int i=0; i < this.reservations.size(); i++){
            Reservation reservation = (Reservation) this.reservations.get(i);
            if (reservation.getID() == reservationID) {
                this.reservedCount -= reservation.getQuantity();
                this.reservations.remove(i);
                return true;
            }
        }

        return false;
    }

}
