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
    
    /*  Constructor
    ***************************************************************************/
    public Store() {
        this.beanBags = new ObjectArrayList();    
    }


    /*  Getters and Setters
    ***************************************************************************/
    public ObjectArrayList getBeanBagsArray() {
        return this.beanBags;
    }

    /*  Methods
    ***************************************************************************/
    /**
     * Retrieve a BeanBag object from Storage based on ID
     * 
     * @param id    bean bag ID to search for
     */
    public BeanBag findBeanBag(String id) {
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

    /**
    * Validate if a string is valid hexadecimal
    * @param hex string to check
    * @return Boolean representing validity
    */
    public Boolean validateHex(String hex) {
        try {
            Long.valueOf(hex, 16);
            return true;
        }
        // Invalid hex strings throw NumberFormatException
        catch (NumberFormatException err) {
            return false;
        }       
    }

    /**
     * Method adds bean bags to the store with the arguments as bean bag details.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num               number of bean bags added
     * @param manufacturer      bean bag manufacturer
     * @param name              bean bag name
     * @param id                ID of bean bag
     * @param year              year of manufacture
     * @param month             month of manufacture
     * @param information       free text detailing bean bag information
     * @throws IllegalNumberOfBeanBagsAddedException   if the number to be added
     *                           is less than 1
     * @throws BeanBagMismatchException if the id already exists in the store, but
     *                           the other stored elements (manufacturer, name and
     *                           free text) do not match
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     * @throws InvalidMonthException    if the month is not in the range 1 to 12
     */
    public void addBeanBags(int num, String manufacturer, String name, String id, short year, byte month)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException, IllegalIDException, InvalidMonthException {
        this.addBeanBags(num, manufacturer, name, id, year, month, "");
    }

    public void addBeanBags(int num, String manufacturer, String name, String id, short year, byte month, String information)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException, IllegalIDException, InvalidMonthException {
        // Ensure we are trying to add a legal number of beanbags
        if (num < 1) {
            throw new IllegalNumberOfBeanBagsAddedException();
        }
        
        if (!this.validateHex(id)) {
            throw new IllegalIDException();
        }

        // Check if a bean bag with this ID already exists
        BeanBag existingBag = this.findBeanBag(id);
        
        // No ID collision; add BeanBag
        if (existingBag == null) {
            BeanBag bag = new BeanBag(num, manufacturer, name, id, year, month);
            this.beanBags.add(bag);
            return;
        }

        // Check if we are able to merge with an existing bean bag
        if (existingBag.getName() == name &&
            existingBag.getManufacturer() == manufacturer &&
            existingBag.getInformation() == information) {
            // If parameters are all equal, same bag found
            existingBag.setStockCount((existingBag.getStockCount() + num));
        }
        
        // Otherwise, correct bag not found, so raise exception
        throw new BeanBagMismatchException();           
   }

    public void setBeanBagPrice(String id, int priceInPence) 
    throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException {
        if ( validateHex(id) ) { // Ensure ID is legal
            if ( priceInPence >= 0 ) { // Ensure price is legal
                try {
                    // Check if a bean bag with this ID already exists
                    BeanBag existingBag = this.findBeanBag(id);
                    // Set price
                    existingBag.setPrice(priceInPence);
                }
                catch (RuntimeException err) {
                    throw new BeanBagIDNotRecognisedException();
                }
            }
            else {
                throw new InvalidPriceException();
            }
        }
        else {
            throw new IllegalIDException();
        }
    }

    public void sellBeanBags(int num, String id) throws BeanBagNotInStockException,
    InsufficientStockException, IllegalNumberOfBeanBagsSoldException,
    PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException { }

    /**
    *   Reserve some beanbags
    *
    *   @param quantity     Number of bags to reserve
    *   @param id           Model ID of bag to reserve
    *
    *   @throws BeanBagNotInStockException
    *               If the bean bag has previously been in stock, but is now
    *               out of stock
    *   @throws InsufficientStockException
    *               If the bean bag is in stock, but not enough are available
    *               to meet the reservation demand
    *   @throws IllegalNumberOfBeanBagsReservedException
    *               If the number of bean bags requested to reserve is fewer
    *               than 1
    *   @throws PriceNotSetException
    *               If the bag is in stock, and there is sufficient stock to
    *               meet demand, but the price has yet to be set
    *   @throws BeanBagIDNotRecognisedException
    *               If the ID does not match any bag in (or previously in) stock
    *   @throws IllegalIDException
    *               If the ID is not a positive eight character hexadecimal
    *               number
    */

    public int reserveBeanBags(int quantity, String id)
        throws BeanBagNotInStockException,
               InsufficientStockException,
               IllegalNumberOfBeanBagsReservedException,
               PriceNotSetException,
               BeanBagIDNotRecognisedException,
               IllegalIDException {

        if (quantity < 1) {
            throw new IllegalNumberOfBeanBagsReservedException();
        }

        BeanBag beanbag = this.findBeanBag(id);

        if (beanbag == null) {
            throw new BeanBagIDNotRecognisedException();
        }

        if (!beanbag.inStock()) {
            throw new BeanBagNotInStockException();
        }

        if (beanbag.availableCount() < quantity) {
            throw new InsufficientStockException();
        }

        return 0;
    }

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
