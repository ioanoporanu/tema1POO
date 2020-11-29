package actions;

import compareclasses.NameDoubleInt;
import compareclasses.SortByDoubleAndInt;
import compareclasses.SortByDoubleAndName;
import fileio.Input;
import fileio.UserInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ActionInputData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final
class Recommendation {
    /**
     *
     */
    private Recommendation() {

    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String standardRecommendation(final Input input, final ActionInputData v) {
        for (UserInputData u : input.getUsers()) {
            if (u.getUsername().equals(v.getUsername())) {
                for (MovieInputData movie : input.getMovies()) {
                    if (!u.getHistory().containsKey(movie.getTitle())) {
                        return "StandardRecommendation result: " + movie.getTitle();
                    }
                }
                for (SerialInputData serial : input.getSerials()) {
                    if (!u.getHistory().containsKey(serial.getTitle())) {
                        return "StandardRecommendation result: " + serial.getTitle();
                    }
                }
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String bestUnseenRecommendation(final Input input, final ActionInputData v) {
        int index = 0;
        String recommendation = "BestRatedUnseenRecommendation result: ";
        ArrayList<NameDoubleInt> unseenVideos = new ArrayList<>();
        for (UserInputData u : input.getUsers()) {
            if (u.getUsername().equals(v.getUsername())) {
                for (MovieInputData movie : input.getMovies()) {
                    if (!u.getHistory().containsKey(movie.getTitle())) {
                        index++;
                        NameDoubleInt object =
                                new NameDoubleInt(movie.getTitle(), movie.getRating(), index);
                        unseenVideos.add(object);
                    }
                }

                for (SerialInputData serial : input.getSerials()) {
                    if (!u.getHistory().containsKey(serial.getTitle())) {
                        index++;
                        NameDoubleInt object =
                                new NameDoubleInt(serial.getTitle(), serial.getRating(), index);
                        unseenVideos.add(object);
                    }
                }
            }
        }
        unseenVideos.sort(new SortByDoubleAndInt());
        if (unseenVideos.size() != 0) {
            recommendation += unseenVideos.get(unseenVideos.size() - 1).getName();
        } else {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }
        return recommendation;
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String searchRecommendation(final Input input, final ActionInputData v) {
        StringBuilder recommendation = new StringBuilder("SearchRecommendation result: [");
        ArrayList<NameDoubleInt> videosNotSeen = new ArrayList<>();

        for (UserInputData u : input.getUsers()) {
            if (u.getUsername().equals(v.getUsername())) {
                if (!u.getSubscriptionType().equals("PREMIUM")) {
                    return "SearchRecommendation cannot be applied!";
                }
                for (MovieInputData movie : input.getMovies()) {
                    if (movie.getGenres().contains(v.getGenre())
                            && !u.getHistory().containsKey(movie.getTitle())) {
                        NameDoubleInt object =
                                new NameDoubleInt(movie.getTitle(), movie.getRating(), 0);
                        videosNotSeen.add(object);
                    }
                }

                for (SerialInputData serial : input.getSerials()) {

                    if (serial.getGenres().contains(v.getGenre())
                            && !u.getHistory().containsKey(serial.getTitle())) {

                        NameDoubleInt object =
                                new NameDoubleInt(serial.getTitle(), serial.getRating(), 0);
                        videosNotSeen.add(object);
                    }
                }
                break;
            }
        }

        videosNotSeen.sort(new SortByDoubleAndName());

        for (NameDoubleInt video : videosNotSeen) {
            recommendation.append(video.getName()).append(", ");
        }

        if (videosNotSeen.size() != 0) {
            recommendation = new StringBuilder(recommendation.substring(
                    0, recommendation.length() - 1));
            recommendation = new StringBuilder(recommendation.substring(
                    0, recommendation.length() - 1));
        } else {
            return "SearchRecommendation cannot be applied!";
        }

        recommendation.append("]");
        return recommendation.toString();
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String favoriteRecommendation(final Input input, final ActionInputData v) {
        String recommendation = "FavoriteRecommendation result: ";
        ArrayList<NameDoubleInt> isFavorite = new ArrayList<>();
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(v.getUsername())) {
                if (!user.getSubscriptionType().equals("PREMIUM")) {
                    return "FavoriteRecommendation cannot be applied!";
                }
                int index = 0;

                for (int i = 0; i < input.getMovies().size(); i++) {
                    index++;
                    if (!user.getHistory().containsKey(
                            input.getMovies().get(i).getTitle())) {
                        int counter = 0;

                        for (UserInputData u : input.getUsers()) {
                            if (u.getFavoriteMovies().contains(
                                    input.getMovies().get(i).getTitle())) {
                                counter++;
                            }
                        }

                        if (counter != 0) {
                            NameDoubleInt movie = new NameDoubleInt(
                                    input.getMovies().get(i).getTitle(), counter, index);
                            isFavorite.add(movie);
                        }
                    }
                }

                for (int i = 0; i < input.getSerials().size(); i++) {
                    index++;
                    if (!user.getHistory().containsKey(
                            input.getSerials().get(i).getTitle())) {
                        int counter = 0;

                        for (UserInputData u : input.getUsers()) {
                            if (u.getFavoriteMovies().contains(
                                    input.getSerials().get(i).getTitle())) {
                                counter++;
                            }
                        }

                        if (counter != 0) {
                            NameDoubleInt movie = new NameDoubleInt(
                                    input.getSerials().get(i).getTitle(), counter, index);
                            isFavorite.add(movie);
                        }
                    }
                }
            }
        }
        isFavorite.sort(new SortByDoubleAndInt());

        if (isFavorite.size() == 0) {
            return "FavoriteRecommendation cannot be applied!";
        }

        recommendation += isFavorite.get(isFavorite.size() - 1).getName();
        return recommendation;
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String popularRecommendation(final Input input, final ActionInputData v) {
        String recommendation = "PopularRecommendation result: ";
        HashMap<String, Integer> popularGenres = new HashMap<>();
        for (UserInputData user : input.getUsers()) {
            for (MovieInputData movie : input.getMovies()) {

                if (user.getHistory().containsKey(movie.getTitle())) {
                    for (String genre : movie.getGenres()) {
                        if (popularGenres.containsKey(genre)) {
                            popularGenres.put(genre,
                                    popularGenres.get(genre)
                                            + user.getHistory().get(movie.getTitle()));
                        } else {
                            popularGenres.put(genre,
                                    user.getHistory().get(movie.getTitle()));
                        }
                    }
                }
            }

            for (SerialInputData serial : input.getSerials()) {
                if (user.getHistory().containsKey(serial.getTitle())) {
                    for (String genre : serial.getGenres()) {
                        if (popularGenres.containsKey(genre)) {
                            popularGenres.put(genre,
                                    popularGenres.get(genre)
                                            + user.getHistory().get(serial.getTitle()));
                        } else {
                            popularGenres.put(genre,
                                    user.getHistory().get(serial.getTitle()));
                        }
                    }
                }
            }
        }
        while (!popularGenres.isEmpty()) {

            String genre = "-";
            int value = 0;
            for (Map.Entry<String, Integer> entry : popularGenres.entrySet()) {
                if (entry.getValue() > value) {
                    value = entry.getValue();
                    genre = entry.getKey();
                }
            }
            popularGenres.remove(genre);
            for (UserInputData user : input.getUsers()) {
                if (user.getUsername().equals(v.getUsername())) {
                    if (!user.getSubscriptionType().equals("PREMIUM")) {
                        return "PopularRecommendation cannot be applied!";
                    }
                    for (MovieInputData movie : input.getMovies()) {
                        if (movie.getGenres().contains(genre)) {
                            if (!user.getHistory().containsKey(movie.getTitle())) {
                                recommendation += movie.getTitle();
                                return recommendation;
                            }
                        }
                    }

                    for (SerialInputData serial : input.getSerials()) {
                        if (serial.getGenres().contains(genre)) {
                            if (!user.getHistory().containsKey(serial.getTitle())) {
                                recommendation += serial.getTitle();
                                return recommendation;
                            }
                        }
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }
}
