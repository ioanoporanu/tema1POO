package compareclasses;

import java.util.Comparator;

public final
class SortByIntAndName implements Comparator<NameDoubleInt> {

    /**
     *
     * @param a a
     * @param b b
     * @return int
     */
    public
    int compare(final NameDoubleInt a, final NameDoubleInt b) {
        if (a.getIndex() != b.getIndex()) {
            return a.getIndex() - b.getIndex();
        } else {
            return a.getName().compareTo(b.getName());
        }
    }
}
