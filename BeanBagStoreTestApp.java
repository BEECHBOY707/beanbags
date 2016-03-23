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
        TestBeanBags();
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


        /*  .reserveBeanBags() - reserve out-of-stock bag
        **********************************************************************/
        bag.setStockCount(0);

        try {
            store.reserveBeanBags(1, "123");
            assert false : "Reservation should have thrown error";
        }
        catch (BeanBagNotInStockException err) {}
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        } 

        completeTest();


        /*  .reserveBeanBags() - reserve bag with insufficient stock
        **********************************************************************/
        bag.setStockCount(10);

        try {
            store.reserveBeanBags(100, "123");
            assert false : "Reservation should have thrown error";
        }
        catch (InsufficientStockException err) {}
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        } 

        completeTest();


        /*  .reserveBeanBags() - reserve non-existent bag
        **********************************************************************/
        bag.setStockCount(10);

        try {
            store.reserveBeanBags(1, "1234567890ABCDEF");
            assert false : "Reservation should have thrown error";
        }
        catch (BeanBagIDNotRecognisedException err) {}
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        } 

        completeTest();


        /*  .reserveBeanBags() - reserve bag without set price
        **********************************************************************/
        try {
            store.reserveBeanBags(1, "123");
            assert false : "Reservation should have thrown error";
        }
        catch (PriceNotSetException err) {}
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        } 

        completeTest();


        /*  .reserveBeanBags() - reserve bag with illegal id
        **********************************************************************/
        try {
            store.reserveBeanBags(1, "NOTHEX");
            assert false : "Reservation should have thrown error";
        }
        catch (IllegalIDException err) {}
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        } 

        completeTest();


        /*  .reserveBeanBags() - reserve a bag
        **********************************************************************/
        int reservationID = 0;
        bag.setPrice(5000);

        try {
            reservationID = store.reserveBeanBags(1, "123");
        }
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        }

        assert reservationID != 0 : "reservationID not set";
        assert bag.getReservedCount() == 1 : "Reservation not made";

        completeTest();


        /*  .unreserveBeanBags() - unreserve an valid ID
        **********************************************************************/
        try {
            store.unreserveBeanBags(reservationID);
        }
        catch (Exception err) {
            System.out.println(err);
            assert false : "Unexpected exception thrown";
        }

        assert bag.getReservedCount() == 0 : "Reservation not removed";

        completeTest();


        /*  .unreserveBeanBags() - unreserve an unrecognised ID
        **********************************************************************/
        try {
            store.unreserveBeanBags(9999);
            assert false : "Reservation should have thrown error";
        }
        catch (ReservationNumberNotRecognisedException err) {}
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

        /*  Test Reservation ID Generator
        **********************************************************************/
        int firstID = BeanBag.generateReservationID();
        int secondID = BeanBag.generateReservationID();
        assert  secondID == firstID + 1;
        completeTest();

        /*  Test Reserve
        **********************************************************************/
        bag.setStockCount(10);
        int reservationID = bag.reserve(6);
        assert bag.availableCount() == 4;
        completeTest();

        /*  Test Unreserve
        **********************************************************************/
        bag.unreserve(reservationID);
        assert bag.availableCount() == 10;
        completeTest();        
    }

    public static void TestReservation() {
        Reservation reservation;

        /*  Test Construction
        **********************************************************************/
        reservation = new Reservation(10, 200, 5);
        assert reservation.getID() == 10;
        assert reservation.getPrice() == 200;
        assert reservation.getQuantity() == 5;
        completeTest();

    }
}