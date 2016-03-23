package beanbags;
import java.io.IOException;


/**
 * Store is a compiling, functioning implementor of the BeanBagStore interface.
 * 
 * @author Max Beech, Louis Haddrell 
 * @version 1.0
 */
public class Store implements BeanBagStore
{
    private ObjectArrayList beanBags;
    
    // Constructor
    public Store() {
        this.beanBags = new ObjectArrayList();    
    }
    
    /**
     * Retrieve a BeanBag object from Storage based on ID
     * 
     * @param id    bean bag ID to search for
     */
    public BeanBag getBeanBag(String id) {
        // Iterate BeanBag array searching for the ID
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            if (bag.getId() == id) {
                return bag;
            }
        }
        
        // ID not found
        return null;
    }

    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException 
    { 
        boolean idValid = false; // Sets up variable to monitor if id entered is valid
        try {
            Long.parseLong(id,16); // Tries to see if ID can be changed, proving its a hexadecimal
            idValid = true;
        } catch (IllegalIDException x) {
            System.err.println("ID entered is invalid.");
        }
        if ( idValid )
        {
            if (num < 1)
            {
                throw new IllegalNumberOfBeanBagsAddedException("You must add at least one bag");
            }
            else if (month > 0 && month < 13)
            {
                throw new InvalidMonthException("The month that you have entered is not valid");
            }
            else
            {
                if ( id.id == null) { // Check to ensure BeanBag id type has not already been created
                    public void add(Object BeanBag(num, manufacturer, name, id, year, month, information));
                }
                else 
                {
                    if (id.manufacturer.equals(manufacturer) && id.name.equals(name) && id.id.equals(id) && id.year.equals(year) && id.month.equals(month))
                    {
                        id.num = id.num + num;
                    }
                    else
                    {
                        throw new BeanBagMismatchException("BeanBag details entered do not match previous entry.");
                    }
                }
            }
        }
    }

    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month, String information)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException { }

    public void setBeanBagPrice(String id, int priceInPence) 
    throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException { }

    public void sellBeanBags(int num, String id) throws BeanBagNotInStockException,
    InsufficientStockException, IllegalNumberOfBeanBagsSoldException,
    PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException { }

    public int reserveBeanBags(int num, String id) throws BeanBagNotInStockException,
    InsufficientStockException, IllegalNumberOfBeanBagsReservedException,
    PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public void unreserveBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException { }

    public void sellBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException { }

    public int beanBagsInStock() { return 0; }

    public int reservedBeanBagsInStock() { return 0; }

    public int beanBagsInStock(String id) throws BeanBagIDNotRecognisedException,
    IllegalIDException { return 0; }

    public void saveStoreContents(String filename) throws IOException { }

    public void loadStoreContents(String filename) throws IOException,
    ClassNotFoundException { }

    public int getNumberOfDifferentBeanBagsInStock() { return 0; }

    public int getNumberOfSoldBeanBags() { return 0; }

    public int getNumberOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public int getTotalPriceOfSoldBeanBags() { return 0; }

    public int getTotalPriceOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public int getTotalPriceOfReservedBeanBags() { return 0; }

    public String getBeanBagDetails(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return ""; }

    public void empty() { }
     
    public void resetSaleAndCostTracking() { }
     
    public void replace(String oldId, String replacementId) 
    throws BeanBagIDNotRecognisedException, IllegalIDException { }
}
