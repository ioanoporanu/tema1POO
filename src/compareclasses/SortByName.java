package compareclasses;

import java.util.Comparator;


public final
class SortByName implements Comparator<NameDoubleInt> {

    /**
     *
     * @param a a
     * @param b b
     * @return int
     */
    public
    int compare(final NameDoubleInt a, final NameDoubleInt b) {
        return a.getName().compareTo(b.getName());
    }
}
