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
    private static int bagStockCount;
    private static int reservationIdCreationCounter;

    private byte month;
    private int priceInPence;
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
                   short year, byte month) {
        // initialise instance variables
        bagStockCount = 0;
        bagReservedCount = 0;
        reservationIdCreationCounter = 0;
    }

    /**
     * Methods to get and set instance's variables
     */
    public void addBeanBags(int num, String manufacturer, String name,
                            String id, short year, byte month,
                            String information) {
        this.manufacturer = manufacturer;
        this.name = name;
        this.id = id;
        this.year = year;
        this.month = month;
        this.information = information;
    }
    
    public String getManufacturer() {
        return this.manufacturer;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getid() {
        return this.id;
    }
    
    public short getYear() {
        return this.year;
    }
    
    public short getMonth() {
        return this.month;
    }
    
    public String getInformation() {
        return this.information;
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
