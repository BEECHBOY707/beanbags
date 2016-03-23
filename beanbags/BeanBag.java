package beanbags;


/**
 * This BeanBag class contains details for every different BeanBag product, including its key specification, and any reservation or sold BeanBag information.
 * 
 * @author Max Beech, Louis Haddrell
 * @version 1.0
 */
public class BeanBag
{
    private static int totalReservations = 0;

    private byte month;
    private int priceInPence;
    private int reservedCount;
    private int stockCount;
    private ObjectArrayList reservations;
    private ObjectArrayList sales;
    private short year;
    private String id;
    private String information;
    private String manufacturer;
    private String name;  

    /*  Constructors
    ***************************************************************************/
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
        this.sales = new ObjectArrayList();
        this.priceInPence = -1;
    }


    /*  Static Methods
    **************************************************************************/
    public static int generateReservationID() {
        return ++BeanBag.totalReservations;
    }

    /*  Getters and Setters
    **************************************************************************/
    public byte getMonth() {
        return this.month;
    }

    public void setMonth(byte value) {
        this.month = value;
    }

    public int getPrice() {
        return this.priceInPence;
    }

    public void setPrice(int value) {
        this.priceInPence = value;
    }

    public int getStockCount() {
        return this.stockCount;
    }

    public void setStockCount(int value) {
        this.stockCount = value;
    }

    public short getYear() {
        return this.year;
    }

    public void setYear(short value) {
        this.year = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getInformation() {
        return this.information;
    }

    public void setInformation(String value) {
        this.information = value;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Boolean hasPrice() {
        return this.priceInPence != -1;
    }

    public Boolean inStock() {
        return this.availableCount() > 0;
    }

    public int availableCount() {
        return this.stockCount - this.reservedCount;
    }

    public int getReservedCount() {
        return this.reservedCount;
    }

    /*  Methods
    **************************************************************************/
    public int reserve(int quantity) {
        int id = BeanBag.generateReservationID();
        this.reservations.add(new Reservation(id, this.priceInPence, quantity));
        this.reservedCount += quantity;
        return id;
    }

    // Return true if the reservation was found in this class
    public Boolean unreserve(int reservationID) {
        for (int i=0; i < this.reservations.size(); i++){
            Reservation reservation = (Reservation) this.reservations.get(i);
            if (reservation.getID() == reservationID) {
                this.reservedCount -= reservation.getQuantity();
                this.reservations.remove(reservation);
                return true;
            }
        }

        return false;
    }
}
