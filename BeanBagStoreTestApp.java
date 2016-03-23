import beanbags.*; /* want all the exceptions, the interface and
                      the concrete implementation in scope*/


/**
 * Test suite
 *
 * @author Louis Haddrell
 * @version 1.0
 */

public class BeanBagStoreTestApp
{
    public static void main(String[] args) {
        TestStore();
        System.out.println("\nTests completed");
    }

    public static void TestStore() {
        // Variable declaration
        BeanBag bag;
        Store store;


        /*  .addBeanBags() - add valid bean bag
        ***********************************************************************/
        store = new Store();

        try {
            store.addBeanBags(1, "", "", "0", (short)2016, (byte)02);
            assert store.getBeanBagsArray().size() == 1;
        }
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        }

        System.out.print(".");


        /*  .addBeanBags() - add illegal number of bean bags
        ***********************************************************************/
        store = new Store();

        try {
            store.addBeanBags(-1, "", "", "", (short)2016, (byte)02);
            assert false : "IllegalNumberOfBeanBagsAddedException not raised";
        }
        catch (IllegalNumberOfBeanBagsAddedException err) {}
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        }

        
        System.out.print(".");


        /*  .addBeanBags() - use an invalid hex value
        ***********************************************************************/
        store = new Store();

        try {
            store.addBeanBags(1, "", "", "#nothex", (short)2016, (byte)02);
            assert false : "IllegalIDException not raised";        
        }
        catch (IllegalIDException err) {}
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        }

        System.out.print(".");


        /*  .findBeanBag() - search for non-existent bag
        ***********************************************************************/
        store = new Store();
        bag = store.findBeanBag("123");
        assert bag == null : "findBeanBag() returned something unexpected";
        System.out.print(".");


        /*  .findBeanBag() - search for existent bag
        ***********************************************************************/
        store = new Store();
        try {
            store.addBeanBags(1, "", "", "123", (short)2016, (byte)2);
        }
        catch (Exception err) {
            assert false : "Unexpected error occurred adding bag";
        }

        bag = store.findBeanBag("123");
        assert bag.getId() == "123" : "Unexpected bag found";
        System.out.print(".");        


        /*  .reserveBeanBags() - reserve illegal quantity
        ***********************************************************************/
        try {
            store.reserveBeanBags(0, "123");
            assert false : "Reservation should have thrown error";
        }
        catch (IllegalNumberOfBeanBagsReservedException err) {}
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        } 

        System.out.print(".");


    }

    public static void TestBeanBags() {
        BeanBag bag;

        /*  Test Construction
        ***********************************************************************/
        bag = new BeanBag(1, "Bean", "Bag", "123", (short)2016, (byte)2);

        assert bag.getMonth() == (byte)2;
        assert bag.getPrice() == -1;
        assert bag.getStockCount() == 1;
        assert bag.getYear() == 2016;
        assert bag.getId() == "123";
        assert bag.getInformation() == "";
        assert bag.getManufacturer() == "Bean";
        assert bag.getName() == "Bag";

        System.out.print(".");

        /*  Test Getters and Setters
        ***********************************************************************/

    }
}