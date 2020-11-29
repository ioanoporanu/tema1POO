package fileio;

import java.util.ArrayList;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    private final ArrayList<Double> ratings = new ArrayList<>();

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    /**
     *
     * @param rating rating
     */
    public void addRating(final double rating) {
        this.ratings.add(rating);
    }

    /**
     *
     * @return rating
     */
    public double getRating() {
        if (this.ratings.size() == 0) {
            return 0;
        }
        double result = 0;
        for (double d : this.ratings) {
            result = result + d;
        }
        return result / this.ratings.size();
    }


    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
