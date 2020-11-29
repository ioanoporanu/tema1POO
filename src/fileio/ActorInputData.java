package fileio;

import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class ActorInputData {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    public ActorInputData(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    /**
     *
     * @param input data base
     * @return String
     */
    public double averrageRating(final Input input) {
        double rating = 0;
        int count = 0;
         for (String title : filmography) {
             for (MovieInputData movie : input.getMovies()) {
                 if (movie.getTitle().equals(title)
                         && movie.getRating() != 0) {
                     rating += movie.getRating();
                     count++;
                     break;
                 }
             }

                 for (SerialInputData serial : input.getSerials()) {
                     if (serial.getTitle().equals(title)
                             && serial.getRating() != 0) {
                         rating += serial.getRating();
                         count++;
                         break;
                     }
                }
         }
         if (count == 0) {
             return 0;
         }
         rating = rating / count;
         return rating;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
