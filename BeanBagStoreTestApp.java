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
        BeanBagStore store = new BadStore();
        assert(store.getTotalPriceOfReservedBeanBags()==0) : "Initial BeanBagStore not empty as required";
        try {
            store.addBeanBags(3,"Comfy Sacks", "Palermo", "AA0984B5", (short) 2016, (byte) 1); 
        } catch (IllegalNumberOfBeanBagsAddedException e) {
            assert(false) : "IllegalNumberOfBeanBagsAddedException thrown incorrectly";   
        } catch (BeanBagMismatchException e) {
            assert(false) : "BeanBagMismatchException thrown incorrectly"; 
        } catch (IllegalIDException e) {
            assert(false) : "IllegalIDException thrown incorrectly"; 
        } catch (InvalidMonthException e) {
            assert(false) : "InvalidMonthException thrown incorrectly"; 
        }
        assert(store.beanBagsInStock()==3) : "number of beans bags claimed in stock not matching number entered";
    }
}