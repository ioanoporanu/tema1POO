package compareclasses;

import java.util.Comparator;

public final
class SortByDoubleAndName implements Comparator<NameDoubleInt> {

    /**
     *
     * @param a a
     * @param b b
     * @return int
     */
    public
    int compare(final NameDoubleInt a, final NameDoubleInt b) {
        if (a.getRating() == b.getRating()) {
            return a.getName().compareTo(b.getName());
        } else {
            return Double.compare(a.getRating(), b.getRating());
        }
    }
}
