package compareclasses;

public final
class NameDoubleInt {
    private
    final String name;
    private
    final double rating;
    private
    final int index;

    public
    int getIndex() {
        return index;
    }

    public
    double getRating() {
        return rating;
    }

    public
    String getName() {
        return name;
    }

    public
    NameDoubleInt(final String name, final double rating, final int index) {
        this.name = name;
        this.rating = rating;
        this.index = index;
    }
}
