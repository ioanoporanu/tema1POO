package actions;

import fileio.Input;
import fileio.UserInputData;
import fileio.ActionInputData;

public final class Commands {
    /**
     *
     */
    private Commands() {

    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String rating(final Input input, final ActionInputData v) {
        for (UserInputData u : input.getUsers()) {
            if (u.getUsername().equals(v.getUsername())) {
                if (u.getHistory().containsKey(v.getTitle())) {
                    return u.addUserRating(input, v);
                } else {
                    return "error -> "
                            + v.getTitle() + " is not seen";
                }
            }
        }
        return "done";
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String view(final Input input, final ActionInputData v) {
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(v.getUsername())) {
                user.addVideoHistory(v.getTitle());
                return "success -> " + v.getTitle()
                        + " was viewed with total views of " + user.getViews(v.getTitle());
            }
        }
        return "done";
    }
}
