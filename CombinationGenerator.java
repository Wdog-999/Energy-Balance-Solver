/**
 * @author Michael Gilleland - http://www.merriampark.com/comb.htm
 */

//--------------------------------------
// Systematically generate combinations.
//--------------------------------------

import java.math.BigInteger;
import java.util.Arrays;

public class CombinationGenerator {
    private static int id_seq = 0;
    public int id;
    private int[] a;
    private int [] output; // returns the result so the internal state is not overwritten by others
    public int n;
    public int r;
    private BigInteger numLeft;
    private BigInteger total;


    public static void main(String[] args) {

        int[] values = {-14, 1, 4, 15, -2, 15, -9, 6};
        CombinationGenerator cg = new CombinationGenerator(values.length, 3);
        while (cg.hasMore()) {
            int[] ind = cg.getNext();
            System.out.println("cg " + cg.id + " now " + Arrays.toString(ind));
        }
        CombinationGenerator cg2 = new CombinationGenerator(values.length, 5);
        while (cg2.hasMore()) {
            int[] ind = cg2.getNext();
            System.out.println("cg2 " + cg.id + " now " + Arrays.toString(ind));
        }


    }

    public CombinationGenerator (int n, int r) {
        if (r > n) {
            throw new IllegalArgumentException (r + " > " + n);
        }
        if (n < 1) {
            throw new IllegalArgumentException (n + " < " + 1);
        }
        this.n = n;
        this.r = r;
        this.id = id_seq++;
        a = new int[r];
        output = new int[r];
        BigInteger nFact = getFactorial (n);
        BigInteger rFact = getFactorial (r);
        BigInteger nminusrFact = getFactorial (n - r);
        total = nFact.divide (rFact.multiply (nminusrFact));
        reset ();
    }

    public void reset () {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
            output[i] = i;
        }
        numLeft = new BigInteger (total.toString ());
    }

    //------------------------------------------------
    // Return number of combinations not yet generated
    //------------------------------------------------

    public BigInteger getNumLeft () {
        return numLeft;
    }

    //-----------------------------
    // Are there more combinations?
    //-----------------------------

    public boolean hasMore () {
        return numLeft.compareTo (BigInteger.ZERO) == 1;
    }

    //------------------------------------
    // Return total number of combinations
    //------------------------------------

    public BigInteger getTotal () {
        return total;
    }

    //------------------
    // Compute factorial
    //------------------

    private static BigInteger getFactorial (int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply (new BigInteger (Integer.toString (i)));
        }
        return fact;
    }

    //--------------------------------------------------------
    // Generate next combination (algorithm from Rosen p. 286)
    //--------------------------------------------------------

    public int[] getNext () {

        if (numLeft.equals (total)) {
            numLeft = numLeft.subtract (BigInteger.ONE);
            return output;
        }

        int i = r - 1;
        while (a[i] == n - r + i) {
            i--;
        }
        a[i] = a[i] + 1;
        for (int j = i + 1; j < r; j++) {
            a[j] = a[i] + j - i;
        }

        numLeft = numLeft.subtract (BigInteger.ONE);
        System.arraycopy(a, 0, output, 0, a.length);
        return output;

    }
}
