import beanbags.*; /* want all the exceptions, the interface and
                      the concrete implementation in scope*/


/**
 * A short program to illustrate an app testing some minimal
 * functionality of a concrete implementation of the BeanBagStore
 * interface -- note you will want to increase these checks, and
 * run it on your Store class (not the BadStore class)
 * 
 * Note, this program, like many others we've looked at, relies
 * a range of different types explictly in order to compile and 
 * run (and even more implictly). E.g.,
 * BadStore, BeanBagMismatchException, BeanBagStore, byte,
 * IllegalIDException, InvalidMonthException, 
 * IllegalNumberOfBeanBagsAddedException, int, short, String, String[]
 *
 *
 * @author Jonathan Fieldsend 
 * @version 1.0
 */
public class BeanBagStoreTestApp
{
    public static void main(String[] args) {
        TestAddBeanBagIllegalNumber();
        System.out.print(".");
        TestAddBeanBagIllegalID();
        System.out.print(".");
        TestFindBeanBagEmpty();
        System.out.print(".");

        // BeanBagStore store = new BadStore();
        // assert(store.getTotalPriceOfReservedBeanBags()==0) : "Initial BeanBagStore not empty as required";
        // try {
        //     store.addBeanBags(3,"Comfy Sacks", "Palermo", "AA0984B5", (short) 2016, (byte) 1); 
        // } catch (IllegalNumberOfBeanBagsAddedException e) {
        //     assert(false) : "IllegalNumberOfBeanBagsAddedException thrown incorrectly";   
        // } catch (BeanBagMismatchException e) {
        //     assert(false) : "BeanBagMismatchException thrown incorrectly"; 
        // } catch (IllegalIDException e) {
        //     assert(false) : "IllegalIDException thrown incorrectly"; 
        // } catch (InvalidMonthException e) {
        //     assert(false) : "InvalidMonthException thrown incorrectly"; 
        // }
        // assert(store.beanBagsInStock()==3) : "number of beans bags claimed in stock not matching number entered";

        System.out.println("\nTests completed");
    }

    // Ensure adding an illegal number of beanbags raises
    // IllegalNumberOfBeanBagsAddedException
    private static void TestAddBeanBagIllegalNumber() {
        Store store = new Store();

        try {
            store.addBeanBags(-1, "", "", "", (short)2016, (byte)02);
        }
        catch (IllegalNumberOfBeanBagsAddedException err) {
            return;
        }
        catch (Exception err) {
            assert false : "Unexpected exception thrown";
        }

        assert false : "IllegalNumberOfBeanBagsAddedException not raised";
    }

    // Adding an invalid hex ID must raise IllegalIDException
    private static void TestAddBeanBagIllegalID() {
        Store store = new Store();

        try {
            store.addBeanBags(1, "", "", "#nothex", (short)2016, (byte)02);
        }
        catch (IllegalIDException err) {
            return;
        }
        catch (Exception err) {
            assert false : "Unexpected exception thrown";
        }

        assert false : "IllegalIDException not raised";        
    }

    // Finding a bean bag in an empty Store will return null
    private static void TestFindBeanBagEmpty() {
        Store store = new Store();
        BeanBag bag = store.findBeanBag("123");
        assert bag == null : "findBeanBag() returned something unexpected";
    }
}