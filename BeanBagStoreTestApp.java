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

        // Variable declaration
        Store store;


        /*  .addBeanBags() - add valid bean bag
        ***********************************************************************/
        store = new Store();

        try {
            store.addBeanBags(1, "", "", "0", (short)2016, (byte)02);
            assert store.getBeanBagsArray().size() == 1;
        }
        catch (Exception err) {
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
            assert false : "Unexpected exception thrown";
        }

        System.out.print(".");


        /*  .findBeanBag() - search for non-existent bag
        ***********************************************************************/
        store = new Store();
        BeanBag bag = store.findBeanBag("123");
        assert bag == null : "findBeanBag() returned something unexpected";
        System.out.print(".");



        System.out.println("\nTests completed");
    }
}