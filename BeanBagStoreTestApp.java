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