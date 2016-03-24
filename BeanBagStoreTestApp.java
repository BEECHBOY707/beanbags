import beanbags.*; /* want all the exceptions, the interface and
                      the concrete implementation in scope*/


/**
 * Test suite
 *
 * @author Louis Haddrell
 */

public class BeanBagStoreTestApp
{
    public static int testCounter = 0;

    public static void main(String[] args) {
        TestStore();
        TestBeanBags();
        TestReservation();
        TestSerialisation();
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
            err.printStackTrace();
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
            err.printStackTrace();
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
            err.printStackTrace();
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
            err.printStackTrace();
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
            err.printStackTrace();
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
            err.printStackTrace();
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
            err.printStackTrace();
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
            err.printStackTrace();
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
            err.printStackTrace();
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
            err.printStackTrace();
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
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        assert bag.getReservedCount() == 0 : "Reservation not removed";

        completeTest();


        /*  .unreserveBeanBags() - unreserve an unrecognised ID
        **********************************************************************/
        try {
            store.unreserveBeanBags(9999);
            assert false : "Unreserve should have thrown error";
        }
        catch (ReservationNumberNotRecognisedException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .sellBeanBags() - bean bag out of stock
        **********************************************************************/
        bag.setStockCount(0);

        try {
            store.sellBeanBags(1, "123");
            assert false : "Sale should have thrown error";
        }
        catch (BeanBagNotInStockException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .sellBeanBags() - bean bag has insufficient stock
        **********************************************************************/
        bag.setStockCount(5);

        try {
            store.sellBeanBags(10, "123");
            assert false : "Sale should have thrown error";
        }
        catch (InsufficientStockException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .sellBeanBags() - invalid number
        **********************************************************************/
        try {
            store.sellBeanBags(0, "123");
            assert false : "Sale should have thrown error";
        }
        catch (IllegalNumberOfBeanBagsSoldException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .sellBeanBags() - price not set
        **********************************************************************/
        try {
            store.addBeanBags(1, "", "", "119", (short)2016, (byte)02);
        }
        catch (Exception err) {
            assert false : "Adding bean bags threw error";
        }

        try {
            store.sellBeanBags(1, "119");
            assert false : "Sale should have thrown error";
        }
        catch (PriceNotSetException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .sellBeanBags() - ID not recognised
        **********************************************************************/
        try {
            store.sellBeanBags(1, "1000000");
            assert false : "Sale should have thrown error";
        }
        catch (BeanBagIDNotRecognisedException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .sellBeanBags() - illegal ID
        **********************************************************************/
        try {
            store.sellBeanBags(1, "NOTHEX");
            assert false : "Sale should have thrown error";
        }
        catch (IllegalIDException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .sellBeanBags() - invalid reservation number
        **********************************************************************/
        try {
            store.sellBeanBags(9999);
            assert false : "Sale should have thrown error";
        }
        catch (ReservationNumberNotRecognisedException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .sellBeanBags() - successful sale
        **********************************************************************/
        int oldSales = bag.getSoldCount();

        try {
            reservationID = store.reserveBeanBags(1, "123");
            store.sellBeanBags(reservationID);
        }
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        assert bag.getSoldCount() - oldSales == 1;

        completeTest();


        /*  .setBeanBagPrice() - invalid hex
        **********************************************************************/
        try {
            store.setBeanBagPrice("NOTHEX", 50);
            assert false : "Setting price should have thrown error";
        }
        catch (IllegalIDException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .setBeanBagPrice() - unrecognised ID
        **********************************************************************/
        try {
            store.setBeanBagPrice("11111", 50);
            assert false : "Setting price should have thrown error";
        }
        catch (BeanBagIDNotRecognisedException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .setBeanBagPrice() - invalid price
        **********************************************************************/
        try {
            store.setBeanBagPrice("123", 0);
            assert false : "Setting price should have thrown error";
        }
        catch (InvalidPriceException err) {}
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        completeTest();


        /*  .setBeanBagPrice() - valid
        **********************************************************************/
        try {
            store.setBeanBagPrice("123", 1000);
        }
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        assert bag.getPrice() == 1000 : "Bag price was not set properly";

        completeTest();


        /*  .getTotalPriceOfReservedBeanBags()
        **********************************************************************/
        store = new Store();

        try {
            store.addBeanBags(10, "", "", "500", (short)2016, (byte)02);
            store.setBeanBagPrice("500", 500);
        }
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception occurred";
        }

        assert store.getTotalPriceOfReservedBeanBags() == 0;

        try {
            store.reserveBeanBags(2, "500");
        }
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception occurred";
        }

        assert store.getTotalPriceOfReservedBeanBags() == 1000;

        completeTest();


        /*  .empty())
        **********************************************************************/        
        assert store.getBeanBagsArray().size() != 0;
        store.empty();
        assert store.getBeanBagsArray().size() == 0;
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
        assert secondID == firstID + 1 : "Unexpected ID generated";
        completeTest();


        /*  Test Reserve
        **********************************************************************/
        bag.setStockCount(10);
        int reservationID = bag.reserve(6);
        assert bag.availableCount() == 4 : "Reserved bags still show available";
        completeTest();


        /*  Test Unreserve
        **********************************************************************/
        bag.unreserve(reservationID);
        assert bag.availableCount() == 10 : "Unreserve not increasing available bag count";
        completeTest();


        /*  Test Sell
        **********************************************************************/
        bag.empty();
        bag.setStockCount(5);
        bag.setPrice(10);
        bag.sell(1);
        
        assert bag.availableCount() == 4;
        assert bag.getSoldCount() == 1;
        assert bag.getSoldValue() == 10;

        completeTest();


        /*  Test Sell Reservation
        **********************************************************************/
        bag.setStockCount(10);
        reservationID = bag.reserve(4);
        int oldSales = bag.getSoldCount();
       
        assert bag.sellReservation(reservationID) : "Reservation sale failed";
        assert bag.availableCount() == 6 : "Sale did not change available count";
        assert bag.getSoldCount() - oldSales == 4 : "Sale did not change sold count";

        completeTest();


        /* Test Reset
        **********************************************************************/
        bag.reset();
        assert bag.getSoldCount() == 0;
        assert bag.getSoldValue() == 0;
        completeTest();


        /* Test Empty
        **********************************************************************/
        bag.empty();
        assert bag.getSoldCount() == 0;
        assert bag.getSoldValue() == 0;
        assert bag.getPrice() == -1;
        assert bag.getReservedCount() == 0;
        assert bag.getStockCount() == 0;
        assert bag.getReservations().size() == 0;
        completeTest();


        /*  Test Get Reservation Value
        **********************************************************************/
        bag.empty();
        bag.setStockCount(10);
        bag.setPrice(10);
        bag.reserve(4);
        assert bag.getReservationValue() == 40 : "Reservation price incorrect";
        completeTest();


        /*  Test Reservation Pricing
        **********************************************************************/
        bag.setPrice(15);
        assert bag.getReservationValue() == 40 : "Reservation price has increased";
        bag.setPrice(5);
        assert bag.getReservationValue() == 20 : "Reservation price has not decreased";
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

        /*  Test getValue()
        **********************************************************************/
        assert reservation.getValue() == 1000 : "Reservation value incorrect";
        completeTest();


        /*  Test updatePrice() - lower price
        **********************************************************************/
        reservation.setPrice(100);
        assert reservation.getPrice() == 100;
        completeTest();


        /*  Test updatePrice() - higher price
        **********************************************************************/
        reservation.setPrice(500);
        assert reservation.getPrice() == 100;
        completeTest();
    }

    public static void TestSerialisation() {
        /*  Create store
        **********************************************************************/
        Store store = new Store();
        try {
            store.addBeanBags(10, "", "", "0", (short)2016, (byte)02);
            store.addBeanBags(20, "", "", "1", (short)2016, (byte)02);
        }
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        assert store.getBeanBagsArray().size() == 2 : "Bags not added";
        completeTest();

        // Grab BeanBag object and apply price
        BeanBag bag0 = store.findBeanBag("0");
        BeanBag bag1 = store.findBeanBag("1");

        bag0.setPrice(50);
        bag1.setPrice(100);

        /*  Make some sales and reservations
        **********************************************************************/
        try {
            store.sellBeanBags(4, "0");
            store.reserveBeanBags(1, "0");

            store.sellBeanBags(10, "1");
            store.reserveBeanBags(5, "1");
        }
        catch (Exception err) {
            err.printStackTrace();;
            assert false : "Unexpected exception thrown";
        }        

        /*  Check
        **********************************************************************/
        assert bag0.getSoldCount() == 4;
        assert bag0.getReservedCount() == 1;

        assert bag1.getSoldCount() == 10;
        assert bag1.getReservedCount() == 5;
        completeTest();

        /*  Serialise
        **********************************************************************/
        try {
            store.saveStoreContents("output.ser");
        }
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        /*  Empty
        **********************************************************************/
        store.empty();

        /*  Load
        **********************************************************************/
        try {
            store.loadStoreContents("output.ser");
        }
        catch (Exception err) {
            err.printStackTrace();
            assert false : "Unexpected exception thrown";
        }

        /*  Check again
        **********************************************************************/
        bag0 = store.findBeanBag("0");
        bag1 = store.findBeanBag("1");

        assert bag0 != null : "Bag 0 is null";
        assert bag1 != null : "Bag 1 is null";
        completeTest();

        bag0.setPrice(50);
        bag1.setPrice(100);

        assert bag0.getSoldCount() == 4;
        assert bag0.getReservedCount() == 1;

        assert bag1.getSoldCount() == 10;
        assert bag1.getReservedCount() == 5;
        completeTest();
    }
}