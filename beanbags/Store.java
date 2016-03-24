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

    /**
     * Method sells bean bags with the corresponding ID from the store and removes
     * the sold bean bags from the stock.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num           number of bean bags to be sold
     * @param id            ID of bean bags to be sold
     * @throws BeanBagNotInStockException   if the bean bag has previously been in
     *                      stock, but is now out of stock
     * @throws InsufficientStockException   if the bean bag is in stock, but not
     *                      enough are available to meet sale demand
     * @throws IllegalNumberOfBeanBagsSoldException if an attempt is being made to 
     *                      sell fewer than 1 bean bag
     * @throws PriceNotSetException if the bag is in stock, and there is sufficient
     *                      stock to meet demand, but the price has yet to be set
     * @throws BeanBagIDNotRecognisedException  if the ID does not match any bag in 
     *                          (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
    public void sellBeanBags(int num, String id) throws BeanBagNotInStockException,
    InsufficientStockException, IllegalNumberOfBeanBagsSoldException,
    PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException {

        if (num < 1) {
            throw new IllegalNumberOfBeanBagsSoldException();
        }

        if (!validateHex(id)) {
            throw new IllegalIDException();
        }

        BeanBag bag = findBeanBag(id);

        if (bag == null) {
            throw new BeanBagIDNotRecognisedException();
        }

        if (!bag.inStock()) {
            throw new BeanBagNotInStockException();
        }

        if (bag.availableCount() < num) {
            throw new InsufficientStockException();
        }

        if (!bag.hasPrice()) {
            throw new PriceNotSetException();
        }

        bag.sell(num);
    }

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

        if (!this.validateHex(id)) {
            throw new IllegalIDException();
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

        if (!beanbag.hasPrice()) {
            throw new PriceNotSetException();
        }

        return beanbag.reserve(quantity);
    }

    public void unreserveBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException {

        // Iterate all bean bags and try to unreserve
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            if (bag.unreserve(reservationNumber)) {
                return;
            }
        }

        throw new ReservationNumberNotRecognisedException();
    }

    public void sellBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException {
        // Iterate all bean bags and try to unreserve
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            if (bag.sellReservation(reservationNumber)) {
                return;
            }
        }

        throw new ReservationNumberNotRecognisedException();
    }

    public int beanBagsInStock() { 
        int beanBagsInStockCount = 0;
        // Iterate all bean bags and add the number of them to variable
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            beanBagsInStockCount = beanBagsInStockCount + bag.getStockCount();
        }
        // Return the total number of BeanBags in stock after iteration
        return beanBagsInStockCount;
    }

    public int reservedBeanBagsInStock() { 
    int reservedBeanBagsInStockCount = 0;
        // Iterate all bean bags and add the number that si reserved to variable
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            reservedBeanBagsInStockCount = reservedBeanBagsInStockCount + bag.getReservedCount();
        }
        // Return the total number of reserved BeanBags in stock after iteration
        return reservedBeanBagsInStockCount;
    }

    public int beanBagsInStock(String id) throws BeanBagIDNotRecognisedException,
    IllegalIDException { 
        // Validate that the ID provided is valid
        if (!this.validateHex(id)) {
            throw new IllegalIDException();
        }
        BeanBag existingBag = this.findBeanBag(id);
        // If no BeanBag by that ID was found, raise exception
        if (existingBag == null) {
            throw new BeanBagIDNotRecognisedException();
        }
        // Return the total number of BeanBags of a given BeanBag type
        return existingBag.getStockCount();
    }

    public void saveStoreContents(String filename) throws IOException { }

    public void loadStoreContents(String filename) throws IOException,
    ClassNotFoundException { }

    public int getNumberOfDifferentBeanBagsInStock() { 
        // Return the number of objects in ObjectArrayList, giving the number of different Bean Bags in stock
        return this.beanBags.size();
    }

    public int getNumberOfSoldBeanBags() { return 0; }

    public int getNumberOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public int getTotalPriceOfSoldBeanBags() { return 0; }

    public int getTotalPriceOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public int getTotalPriceOfReservedBeanBags() { return 0; }

    public String getBeanBagDetails(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return ""; }

    public void empty() {
        // Clear all objects from BeanBags object array list by iterating through list
        for (int i=0; i < this.beanBags.size(); i++){
            // Use ObjectArrayList method to remove the front element of the list each time
            this.beanBags.remove(0);
        }
    }
     
    public void resetSaleAndCostTracking() { }
     
    public void replace(String oldId, String replacementId) 
    throws BeanBagIDNotRecognisedException, IllegalIDException {
        if ( validateHex(oldId) ) { // Ensure ID is legal
            try {
                // Find BeanBag and set it
                BeanBag existingBag = this.findBeanBag(oldId);
                // Set new ID
                existingBag.setId(replacementId);
            }
            catch (RuntimeException err) {
                throw new BeanBagIDNotRecognisedException();
            }
        }
        else {
            throw new IllegalIDException();
        }
    }
}
