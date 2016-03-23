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
    public static int testCounter = 0;

    public static void main(String[] args) {
        TestStore();
        System.out.printf("\n%d tests completed", testCounter);
    }

    public static void completeTest() {
        System.out.print(".");
        testCounter += 1;
    }

    public static void TestStore() {
        // Variable declaration
        BeanBag bag;
        Store store;

        /*  .validateHex() - valid
        **********************************************************************/
        store = new Store();
        assert store.validateHex("1234567890ABCDEF");
        completeTest();

        /*  .validateHex() - valid
        **********************************************************************/
        store = new Store();
        assert store.validateHex("NOTHEX") == false;
        completeTest();


        /*  .addBeanBags() - add valid bean bag
        **********************************************************************/
        store = new Store();

        try {
            store.addBeanBags(1, "", "", "0", (short)2016, (byte)02);
            assert store.getBeanBagsArray().size() == 1;
        }
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .addBeanBags() - add illegal number of bean bags
        **********************************************************************/
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

        
        completeTest();


        /*  .addBeanBags() - use an invalid hex value
        **********************************************************************/
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

        completeTest();


        /*  .findBeanBag() - search for non-existent bag
        **********************************************************************/
        store = new Store();
        bag = store.findBeanBag("123");
        assert bag == null : "findBeanBag() returned something unexpected";
        completeTest();


        /*  .findBeanBag() - search for existent bag
        **********************************************************************/
        store = new Store();
        try {
            store.addBeanBags(1, "", "", "123", (short)2016, (byte)2);
        }
        catch (Exception err) {
            assert false : "Unexpected error occurred adding bag";
        }

        bag = store.findBeanBag("123");
        assert bag.getId() == "123" : "Unexpected bag found";
        completeTest();        


        /*  .reserveBeanBags() - reserve illegal quantity
        **********************************************************************/
        try {
            store.reserveBeanBags(0, "123");
            assert false : "Reservation should have thrown error";
        }
        catch (IllegalNumberOfBeanBagsReservedException err) {}
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        } 

        completeTest();


    }

    public static void TestBeanBags() {
        BeanBag bag;

        /*  Test Construction
        **********************************************************************/
        bag = new BeanBag(1, "Bean", "Bag", "123", (short)2016, (byte)2);

        assert bag.getMonth() == (byte)2;
        assert bag.getPrice() == -1;
        assert bag.getStockCount() == 1;
        assert bag.getYear() == 2016;
        assert bag.getId() == "123";
        assert bag.getInformation() == "";
        assert bag.getManufacturer() == "Bean";
        assert bag.getName() == "Bag";

        completeTest();

        /*  Test Getters and Setters
        **********************************************************************/

    }
}