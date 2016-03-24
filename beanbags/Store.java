package beanbags;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;


/**
 * Bean Bag store object implementing BeanBagStore interface
 * 
 * @author Max Beech, Louis Haddrell 
 */
public class Store implements BeanBagStore, Serializable
{
    private ObjectArrayList beanBags;
    
    public Store() {
        this.beanBags = new ObjectArrayList();    
    }


    /**
     *  @return     bean bags ObjectArrayList
     */
    public ObjectArrayList getBeanBagsArray() {
        return this.beanBags;
    }

    /**
     * Retrieve a BeanBag object from the ObjectArrayList by ID string
     * 
     * @param id    bean bag ID string to search for
     * @return      BeanBag object if ID exists in array; null if not
     */
    public BeanBag findBeanBag(String id) {
        // Iterate BeanBag array searching for the ID
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);

            if (bag.getId().equals(id)) {
                return bag;
            }
        }
        
        // ID not found
        return null;
    }

    /**
    * Check if a string is valid, positive hexadecimal number
    *
    * @param hex    string to check
    * @return       Boolean representing validity
    */
    public Boolean validateHex(String hex) {
        try {
            return Long.valueOf(hex, 16) >= 0L;
        }
        // Hex string was invalid
        catch (NumberFormatException err) {
            return false;
        }
    }

    /**
     * Add bean bags to the store with the provided arguments.
     *
     * @param num               number of bean bags added
     * @param manufacturer      bean bag manufacturer
     * @param name              bean bag name
     * @param id                ID of bean bag 
     * @param year              year of manufacture
     * @param month             month of manufacture
     * @throws IllegalNumberOfBeanBagsAddedException    if num < 1
     * @throws BeanBagMismatchException     if a bean bag with this ID already
     *      exists but other stored elements (manufacturer, name and free text)
     *      do not match
     * @throws IllegalIDException   if the ID is not a positive eight character 
     *      hexadecimal number
     * @throws InvalidMonthException    if the month is not in the range 1 to 12
     */
    public void addBeanBags(int num, String manufacturer, String name,
                            String id, short year, byte month)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
           IllegalIDException, InvalidMonthException {

        this.addBeanBags(num, manufacturer, name, id, year, month, "");
    }

    /**
     * Add bean bags to the store with the provided arguments.
     *
     * @param num               number of bean bags added
     * @param manufacturer      bean bag manufacturer
     * @param name              bean bag name
     * @param id                ID of bean bag 
     * @param year              year of manufacture
     * @param month             month of manufacture
     * @param information       descriptive text
     * @throws IllegalNumberOfBeanBagsAddedException    if num < 1
     * @throws BeanBagMismatchException     if a bean bag with this ID already
     *      exists but other stored elements (manufacturer, name and free text)
     *      do not match
     * @throws IllegalIDException   if the ID is not a positive eight character 
     *      hexadecimal number
     * @throws InvalidMonthException    if the month is not in the range 1 to 12
     */
    public void addBeanBags(int num, String manufacturer, String name,
                            String id, short year, byte month,
                            String information)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
           IllegalIDException, InvalidMonthException {

        // Ensure we are trying to add a legal number of beanbags
        if (num < 1) {
            throw new IllegalNumberOfBeanBagsAddedException();
        }
        
        if (!this.validateHex(id)) {
            throw new IllegalIDException();
        }

        // Check if a bean bag with this ID already exists
        BeanBag existingBag = this.findBeanBag(id);
        
        // ID not found - add new bean bag
        if (existingBag == null) {
            BeanBag bag = new BeanBag(num, manufacturer, name, id, year, month);
            this.beanBags.add(bag);
            return;
        }

        // ID match found - try to merge stock
        if (existingBag.getName().equals(name) &&
            existingBag.getManufacturer().equals(manufacturer) &&
            existingBag.getInformation().equals(information)) {
            // If parameters are all equal, same bag found
            existingBag.setStockCount((existingBag.getStockCount() + num));
        }
        
        // Otherwise, correct bag not found, so raise exception
        throw new BeanBagMismatchException();           
    }

    /**
     * Method to set the price of bean bags with matching ID in stock.
     *
     * @param id                ID of bean bags
     * @param priceInPence      bean bag price in pence
     * @throws InvalidPriceException if the priceInPence < 1
     * @throws BeanBagIDNotRecognisedException  if the ID does not match any bag in 
     *                          (or previously in) stock
     * @throws IllegalIDException if the ID is not a positive eight character
     *                           hexadecimal number
     */
    public void setBeanBagPrice(String id, int priceInPence) 
    throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException {

        if (!validateHex(id)) {
            throw new IllegalIDException();
        }
        if (priceInPence < 1) {
            throw new InvalidPriceException();
        }

        BeanBag bag = this.findBeanBag(id);

        if (bag == null) {
            throw new BeanBagIDNotRecognisedException();
        }

        bag.setPrice(priceInPence);
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
    public void sellBeanBags(int num, String id)
    throws BeanBagNotInStockException, InsufficientStockException,
           IllegalNumberOfBeanBagsSoldException, PriceNotSetException,
           BeanBagIDNotRecognisedException, IllegalIDException {

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
     * Reserve bean bags with the corresponding ID.
     *
     * @param num       Quantity to reserve
     * @param id        ID of bean bags to be reserved
     * @return          Unique reservation number
     * @throws BeanBagNotInStockException
     *      If the bean bag has previously been in stock, but is now out of
     *      stock
     * @throws InsufficientStockException
     *      If the bean bag is in stock, but not enough are available to meet
     *      the reservation demand
     * @throws IllegalNumberOfBeanBagsReservedException
     *      If the number of bean bags requested to reserve is fewer than 1
     * @throws PriceNotSetException
     *      If the bag is in stock, and there is sufficient stock to meet
     *      demand, but the price has yet to be set
     * @throws BeanBagIDNotRecognisedException
     *      If the ID does not match any bag in (or previously in) stock
     * @throws IllegalIDException
     *      If the ID is not a positive eight character hexadecimal number
     */
    public int reserveBeanBags(int quantity, String id)
    throws BeanBagNotInStockException, InsufficientStockException,
           IllegalNumberOfBeanBagsReservedException, PriceNotSetException,
           BeanBagIDNotRecognisedException, IllegalIDException {

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


    /**
     * Cancel an existing reservation ticket.
     *
     * @param reservationNumber           reservation number
     * @throws ReservationNumberNotRecognisedException
     *      If the reservation number does not match a current reservation in
     *      the system
     */
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

    /**
     * Sell a bean bags from a reservation ticket.
     *
     * @param reservationNumber     unique reservation number
     * @throws ReservationNumberNotRecognisedException
     *      Reservation number does not match any reservation in the system
     */
    public void sellBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException {

        // Iterate all bean bags and try to sell
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            if (bag.sellReservation(reservationNumber)) {
                return;
            }
        }

        throw new ReservationNumberNotRecognisedException();
    }

    /**
     * Get the total of reserved and unreserved stock held by the store.
     *
     * @return      number of bean bags in this store
     */    
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

    /**
     * Get the total number of reserved bean bags
     *
     * @return      number of reserved bean bags in this store
     */  
    public int reservedBeanBagsInStock() { 

        int reservedBeanBagsInStockCount = 0;
        // Iterate all bean bags and add the number that is reserved to variable
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            reservedBeanBagsInStockCount = reservedBeanBagsInStockCount + bag.getReservedCount();
        }
        // Return the total number of reserved BeanBags in stock after iteration
        return reservedBeanBagsInStockCount;
    }

    /**
     * Get the total stock held by the store for a specific bean bag model.
     *
     * @return      number of bean bags in this store
     */    
    public int beanBagsInStock(String id)
    throws BeanBagIDNotRecognisedException, IllegalIDException {
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

    /**
     * Write Store's contents into a serialised file.
     *
     * @param filename      location of the file to be saved
     * @throws IOException  if there is a problem experienced when trying to save 
     *                      the store contents to the file
     */
    public void saveStoreContents(String filename)
    throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
        }
        catch (IOException err) {
            err.printStackTrace();
            throw err;
        }
    }

    /**
     * Load Store's contents from a serialised file.
     *
     * Note: this will immediately replace Store's contents; be sure to save
     * contents first to avoid permanent data loss!
     *
     * @param filename      location of the file to be saved
     * @throws IOException  if there is a problem experienced when trying to save 
     *                      the store contents to the file
     */
    public void loadStoreContents(String filename)
    throws IOException, ClassNotFoundException {
        Store loadedStore;

        try {
            FileInputStream fileInput = new FileInputStream(filename);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            loadedStore = (Store) objectInput.readObject();
            objectInput.close();
        }
        catch (IOException err){
            err.printStackTrace();
            throw err;
        }
        catch (ClassNotFoundException err){
            err.printStackTrace();
            throw err;
        }

        // Apply loaded beanbags array
        this.empty();
        ObjectArrayList loadedArray = loadedStore.getBeanBagsArray();

        for (int i=0; i < loadedArray.size(); i++){
            BeanBag beanbag = (BeanBag) loadedArray.get(i);
            this.beanBags.add(beanbag);
        }
    }

    /**
     * Count unique bean bag models sold by this Store.
     *
     * @return      number of bean bags sold by the store
     */
    public int getNumberOfDifferentBeanBagsInStock() { 
        // Return the number of objects in ObjectArrayList, giving the number
        // of different Bean Bags in stock
        return this.beanBags.size();
    }

    /**
     * Count total number of sold bean bags.
     *
     * @return      number of bean bags sold by the store
     */
    public int getNumberOfSoldBeanBags() { 
        // Inititate beanBagsSoldCount variable 
        int beanBagsSoldCount = 0;
        // Iterate all bean bags and add the number of them to variable
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            beanBagsSoldCount = beanBagsSoldCount + bag.getSoldCount();
        }
        // Return the total number of BeanBags sold after iteration
        return beanBagsSoldCount;
    }

    /**
     * Count total number of sold bean bags for a particular model ID.
     *
     * @param id    ID of bean bags
     * @return      number bean bags sold by the store with matching ID
     * @throws BeanBagIDNotRecognisedException
     *      If the ID does not match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
    public int getNumberOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { 
        // Ensure ID legal
        if (!validateHex(id)) {
            throw new IllegalIDException();
        }
        BeanBag bag = this.findBeanBag(id);
        // Ensure ID's object exists
        if (bag == null) {
            throw new BeanBagIDNotRecognisedException();
        }
        // Return the number of BeanBags sold for the object of given ID
        return bag.getSoldCount();
    }

    /**
     * Count total income generated by all bean bag sales.
     *
     * @return      total cost of bean bags sold (in pence)
     */
    public int getTotalPriceOfSoldBeanBags() { 
        // Inititate beanBagsSoldCount variable 
        int beanBagsSoldPriceCount = 0;
        // Iterate all bean bags and add the price of them to variable
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            beanBagsSoldPriceCount = beanBagsSoldPriceCount + bag.getSoldValue();
        }
        // Return the total price of BeanBags sold after iteration
        return beanBagsSoldPriceCount;
    }

    /**
     * Count total income generated by a specific bean bag model.
     *
     * @param id        ID of bean bags
     * @return          total cost of bean bag model sales (in pence)
     * @throws BeanBagIDNotRecognisedException
     *      If the ID does not match any bag in (or previously in) stock
     * @throws IllegalIDException 
     *      If the ID is not a positive eight character hexadecimal number
     */
    public int getTotalPriceOfSoldBeanBags(String id)
    throws BeanBagIDNotRecognisedException, IllegalIDException {
        // Validate that the ID provided is valid
        if (!this.validateHex(id)) {
            throw new IllegalIDException();
        }
        BeanBag existingBag = this.findBeanBag(id);
        // If no BeanBag by that ID was found, raise exception
        if (existingBag == null) {
            throw new BeanBagIDNotRecognisedException();
        }
        // Return the total price of sold BeanBags of a given BeanBag type
        return existingBag.getSoldValue();
    }

    /**
     * Count total value of all reserved bean bags.
     *
     * @return      total cost of reserved bean bags sold (in pence)
     */
    public int getTotalPriceOfReservedBeanBags() {
        int totalValue = 0;

        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag bag = (BeanBag) this.beanBags.get(i);
            totalValue += bag.getReservationValue();
        }

        return totalValue;
    }

    /**
     * Retrieve description of a particular bean bag model.
     *
     * @param id    ID of bean bag
     * @return      any textual details relating to the bean bag
     * @throws BeanBagIDNotRecognisedException
     *      If the ID does not match any bag in (or previously in) stock
     * @throws IllegalIDException
     *      If the ID is not a positive eight character hexadecimal number
     */
    public String getBeanBagDetails(String id)
    throws BeanBagIDNotRecognisedException, IllegalIDException {
        
        if (!validateHex(id)) {
            throw new IllegalIDException();
        }
        BeanBag bag = this.findBeanBag(id);
        if (bag == null) {
            throw new BeanBagIDNotRecognisedException();
        }
        if ( bag.getId() != null ) {
            String beanBagDetails = "BeanBag ID: " + bag.getId() + ", BeanBag Manufacturer: " + bag.getManufacturer() + ", BeanBag Name: " + bag.getName() + ", BeanBag Year: " + bag.getYear() + ", BeanBag Stock Count: " + bag.getStockCount() + ", BeanBag Reserved Count: " + bag.getReservedCount() + ", BeanBag Sold stock Count: " + bag.getSoldCount() + ", BeanBag Product price: " + bag.getPrice();
            return beanBagDetails;
        }
        else {
            return ""; 
        }
    }

    /**
     * Empty all contents from the store.
     */
    public void empty() {
        this.beanBags = new ObjectArrayList();
    }

    /**
     * Reset cost tracking of all bean bags.
     *
     * Note that stock levels and reservations are unaffected.
     */
    public void resetSaleAndCostTracking() {
        for (int i=0; i < this.beanBags.size(); i++){
            BeanBag beanbag = (BeanBag) this.beanBags.get(i);
            beanbag.reset();
        }        
    }

    /**
     * Replace a bean bag's ID number.
     * 
     * @param oldId             original ID to replace
     * @param replacementId     ID to act as replacement
     * @throws BeanBagIDNotRecognisedException
     *      If the oldId does not match any bag in (or previously in) stock
     * @throws IllegalIDException
     *      If either argument is not a positive eight character hexadecimal
     *      number, or the replacementID is already in use in the store
     */     
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
