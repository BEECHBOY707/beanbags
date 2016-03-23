package beanbags;


/**
 * This BeanBag class contains details for every different BeanBag product, including its key specification, and any reservation or sold BeanBag information.
 * 
 * @author Max Beech, Louis Haddrell
 * @version 1.0
 */
public class BeanBag
{
    // instance variables
    private static int bagReservedCount;
    private static int reservationIdCreationCounter;

    private byte month;
    private int priceInPence;
    private int stockCount;
    private short year;
    private String id;
    private String information;
    private String manufacturer;
    private String name;
    
    //ArrayList reservedBeanBags = new ArrayList();

    /**
     * Constructor for objects of class BeanBag
     */
    public BeanBag(int num, String manufacturer, String name, String id,
                   short year, byte month, String information) {

        this.stockCount = num;
        this.manufacturer = manufacturer;
        this.name = name;
        this.id = id;
        this.year = year;
        this.month = month;
        this.information = information;

        // initialise instance variables
        BeanBag.bagReservedCount = 0;
        BeanBag.reservationIdCreationCounter = 0;
    }

    /*  Getters and Setters
    ***************************************************************************/
    public short getMonth() {
        return this.month;
    }

    public void setMonth(short value) {
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
    
    /**
     * Miscellaneous methods
     */
    
    public int reserveBeanBags(int num, String id)
    {
        if (this.id.equals(id))
        {
            for ( int x = 0 ; x < num ; x++)
            {
                //reservedBeanBags.add(priceInPence); // Need to find a better way of inputting these two vars together ideally via a dictionary like setup
                //reservedBeanBags.add(reservationIdCreationCounter); // Need to find a better way of inputting these two vars together ideally via a dictionary like setup
                //reservationIdCreationCounter++;
            }
        }
        return 0;
    }
}
