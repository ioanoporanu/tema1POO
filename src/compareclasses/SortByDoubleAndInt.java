package compareclasses;

import java.util.Comparator;

public final
class SortByDoubleAndInt implements Comparator<NameDoubleInt> {

    /**
     *
     * @param a a
     * @param b b
     * @return int
     */
    public
    int compare(final NameDoubleInt a, final NameDoubleInt b) {
        if (a.getRating() == b.getRating()) {
            return b.getIndex() - a.getIndex();
        } else {
            return Double.compare(a.getRating(), b.getRating());
        }

    }
}
