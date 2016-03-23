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
 
    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException { }

    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month, String information)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException { }

    public void setBeanBagPrice(String id, int priceInPence) 
    throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException { }

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

        BeanBag beanbag = null;

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
