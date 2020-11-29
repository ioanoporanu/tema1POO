package main;

import actions.Commands;
import actions.Query;
import actions.Recommendation;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your
 * implentation.
 */
public
final class Main {
    /**
     * for coding style
     */
    private
    Main() { }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public
    static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH,
                Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public
    static void action(final String filePath1,
                       final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        // TODO add here the entry point to your implementation
        List<ActionInputData> commands = input.getCommands();
        for (ActionInputData v : commands) {
            JSONObject object = new JSONObject();
            if (v.getActionType().equals("command")) {
                if (v.getType().equals("favorite")) {
                    for (UserInputData user : input.getUsers()) {
                        if (user.getUsername().equals(v.getUsername())) {
                           object = fileWriter.writeFile(
                                    v.getActionId(), user.favoriteAdd(v.getTitle()));
                            break;
                        }
                    }
                }
                if (v.getType().equals("rating")) {
                   String result = Commands.rating(input, v);
                     object =
                            fileWriter.writeFile(v.getActionId(), result);
                }
                if (v.getType().equals("view")) {
                    String result = Commands.view(input, v);
                    object = fileWriter.writeFile(v.getActionId(), result);
                }
            }
            if (v.getActionType().equals("query")) {
                if (v.getObjectType().equals("actors")) {
                    if (v.getCriteria().equals("average")) {
                        String result = Query.sortActorsAverage(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);
                    }
                    if (v.getCriteria().equals("awards")) {
                        String result = Query.sortActorsAwards(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);
                    }
                    if (v.getCriteria().equals("filter_description")) {
                        String result = Query.sortActorsFilterDescription(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);

                    }
                }
                if (v.getCriteria().equals("ratings")) {
                    if (v.getObjectType().equals("movies")) {
                        String result = Query.sortMoviesByRating(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);

                    }
                    if (v.getObjectType().equals("shows")) {
                        String result = Query.sortSerialsByRating(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);
                    }
                }
                if (v.getCriteria().equals("favorite")) {
                    if (v.getObjectType().equals("movies")) {
                        String result = Query.sortMoviesByFavorite(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);
                    }
                    if (v.getObjectType().equals("shows")) {
                        String result = Query.sortSerialsByFavorite(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);
                    }
                }
                if (v.getCriteria().equals("longest")) {
                    if (v.getObjectType().equals("movies")) {
                        String result = Query.sortMoviesByDuration(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);

                    }
                    if (v.getObjectType().equals("shows")) {
                        String result = Query.sortSerialsByDuration(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);

                    }
                }
                if (v.getCriteria().equals("most_viewed")) {
                    if (v.getObjectType().equals("movies")) {
                        String result = Query.sortMoviesByViews(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);
                    }
                    if (v.getObjectType().equals("shows")) {
                        String result = Query.sortSerialsByViews(input, v);
                        object = fileWriter.writeFile(v.getActionId(), result);
                    }
                }
                if (v.getObjectType().equals("users")) {
                    String result = Query.sortByUserRatingsNumber(input, v);
                    object = fileWriter.writeFile(v.getActionId(), result);
                }
            }
            if (v.getActionType().equals("recommendation")) {
                if (v.getType().equals("standard")) {
                    String result = Recommendation.standardRecommendation(input, v);
                    object = fileWriter.writeFile(v.getActionId(), result);
                }
                if (v.getType().equals("best_unseen")) {
                    String result = Recommendation.bestUnseenRecommendation(input, v);
                    object = fileWriter.writeFile(v.getActionId(), result);
                }
                if (v.getType().equals("search")) {
                    String result = Recommendation.searchRecommendation(input, v);
                    object = fileWriter.writeFile(v.getActionId(), result);
                }
                if (v.getType().equals("favorite")) {
                    String result = Recommendation.favoriteRecommendation(input, v);
                    object = fileWriter.writeFile(v.getActionId(), result);
                }
                if (v.getType().equals("popular")) {
                    String result = Recommendation.popularRecommendation(input, v);
                    object = fileWriter.writeFile(v.getActionId(), result);
                }
            }
            arrayResult.add(object);
        }
        fileWriter.closeJSON(arrayResult);
    }
}
