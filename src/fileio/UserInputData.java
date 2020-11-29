package fileio;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public
final class UserInputData {
    /**
     * User's username
     */
    private
    final String username;
    /**
     * Subscription Type
     */
    private
    final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private
    final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private
    final ArrayList<String> favoriteMovies;

    private final
    ArrayList<String> ratedVideos = new ArrayList<>();

    public
    UserInputData(final String username, final String subscriptionType,
                  final Map<String, Integer> history,
                  final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    public
    String getUsername() {
        return username; }

    public
    Map<String, Integer> getHistory() {
        return history; }

    public
    String getSubscriptionType() {
        return subscriptionType; }

    public
    ArrayList<String> getFavoriteMovies() {
        return favoriteMovies; }

    /**
     *
     * @param title title
     */
    public
    void addRatedVideos(final String title) {
        ratedVideos.add(title); }

    /**
     *
     * @param movie movie
     * @return String
     */
    public
    String favoriteAdd(final String movie) {
        String result = "error -> " + movie + " is not seen";
        if (history.containsKey(movie)) {
            for (String title : favoriteMovies) {
                if (title.equals(movie)) {
                    result = "error -> " + movie + " is already in favourite list";
                    return result;
                }
            }

            favoriteMovies.add(movie);
            result = "success -> " + movie + " was added as favourite";

            return result;
        }

        return result;
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public
    String addUserRating(final Input input, final ActionInputData v) {
        String ratedTitle = v.getTitle() + v.getSeasonNumber();
        if (this.getRatedVideos().contains(v.getTitle())
                || this.getRatedVideos().contains(ratedTitle)) {
            return "error -> "
                    + v.getTitle() + " has been already rated";
        }

        for (MovieInputData p : input.getMovies()) {
            if (p.getTitle().equals(v.getTitle())) {
                p.addRating(v.getGrade());
                this.addRatedVideos(v.getTitle());
                return "success -> "
                        + v.getTitle()
                        + " was rated with "
                        + v.getGrade()
                        + " by " + v.getUsername();
            }
        }

        for (SerialInputData p : input.getSerials()) {
            if (p.getTitle().equals(v.getTitle())) {
                p.getSeasons().get(v.getSeasonNumber() - 1).addRating(v.getGrade());

                this.addRatedVideos(ratedTitle);

                return "success -> "
                        + v.getTitle() + " was rated with "
                        + v.getGrade() + " by " + v.getUsername();
            }
        }
        return "none";
    }

    /**
     *
     * @param title title
     */
    public
    void addVideoHistory(final String title) {
        if (!history.containsKey(title)) {
            history.put(title, 1);
            return;
        }
        history.put(title, history.get(title) + 1);
    }

    /**
     *
     * @return list
     */
    public
    ArrayList<String> getRatedVideos() {
        return ratedVideos; }

    /**
     *
     * @param title title
     * @return Int
     */
    public
    int getViews(final String title) {
        return history.get(title); }

    @Override public String toString() {
        return "UserInputData{"
                + "username='"
                + username
                + '\''
                + ", subscriptionType='"
                + subscriptionType + '\''
                + ", history=" + history + ", favoriteMovies=" + favoriteMovies + '}';
    }
}
