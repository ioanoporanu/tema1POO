package actions;

import compareclasses.NameDoubleInt;
import compareclasses.SortByDoubleAndName;
import compareclasses.SortByIntAndName;
import compareclasses.SortByName;
import actor.ActorsAwards;
import fileio.ActorInputData;
import fileio.Input;
import fileio.ActionInputData;
import fileio.UserInputData;
import java.util.ArrayList;
import java.util.Collections;

public final
class Query   {

    private Query() {

    }

    /**
     *
     * @param videosRating lista
     * @param v command
     * @return String
     */
    public static
    String orderType(final ActionInputData v,
                      final ArrayList<NameDoubleInt> videosRating)     {
        StringBuilder query = new StringBuilder("Query result: [");
        int limit = v.getNumber();
        if (limit > videosRating.size())     {
            limit = videosRating.size();
        }
        if (v.getSortType().equals("desc"))   {
            Collections.reverse(videosRating);
        }
        for (int i = 0; i < limit; i++)   {
            query.append(videosRating.get(i).getName()).append(", ");


        }
        if (videosRating.size() != 0)   {
            query = new StringBuilder(query.substring(0, query.length() - 1));
            query = new StringBuilder(query.substring(0, query.length() - 1));
        }
        query.append("]");
        return query.toString();
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortActorsAverage(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> aux = new ArrayList<>();
        for (ActorInputData actor : input.getActors())   {
            NameDoubleInt object =
                    new NameDoubleInt(actor.getName(), actor.averrageRating(input), 0);
            aux.add(object);
        }
        aux.sort(new SortByDoubleAndName());
        while (aux.get(0).getRating() == 0)   {
            aux.remove(0);
        }
        return orderType(v, aux);
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortActorsAwards(final Input input, final ActionInputData v)   {
        StringBuilder query = new StringBuilder("Query result: [");
        ArrayList<ActorInputData> aux = new ArrayList<>(input.getActors());
        ArrayList<NameDoubleInt> haveAwards = new ArrayList<>();
        if (v.getFilters().get(v.getFilters().size() - 1) == null) {
            query.append("]");
            return query.toString();
        }
        for (ActorInputData actors : aux) {
            int nrAwards = 0;

            for (ActorsAwards key : actors.getAwards().keySet()) {
                nrAwards += actors.getAwards().get(key);
            }
            int check = 1;
            for (int j = 0; j < v.getFilters().get(v.getFilters().size() - 1).size(); j++)   {
                if (!actors.getAwards().containsKey(
                        ActorsAwards.valueOf(v.getFilters().get(
                                v.getFilters().size() - 1).get(j))))   {
                    check = 0;
                    break;
                }
            }
            if (check == 1)   {
                NameDoubleInt actor =
                        new NameDoubleInt(actors.getName(), 0, nrAwards);
                haveAwards.add(actor);
            }
        }
        haveAwards.sort(new SortByIntAndName());
        if (v.getSortType().equals("desc"))   {
            Collections.reverse(haveAwards);
        }
        for (NameDoubleInt object : haveAwards)   {
            query.append(object.getName()).append(", ");
        }
        if (haveAwards.size() != 0)   {
            query = new StringBuilder(query.substring(0, query.length() - 1));
            query = new StringBuilder(query.substring(0, query.length() - 1));
        }
        query.append("]");
        return query.toString();
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortActorsFilterDescription(final Input input, final ActionInputData v)   {
        String query = "Query result: [";
        ArrayList<ActorInputData> aux = new ArrayList<>(input.getActors());
        ArrayList<NameDoubleInt> haveKeywords = new ArrayList<>();
        if (v.getFilters().get(2) == null)   {
            query += "]";
            return query;
        }
        for (int i = 0; i < aux.size(); i++)   {
            int check = 1;
            for (int j = 0; j < v.getFilters().get(2).size(); j++)   {
                String contained = v.getFilters().get(2).get(j);
                contained = contained.toLowerCase();
                if (!aux.get(i).getCareerDescription().toLowerCase().contains(
                        contained + " ")
                        && !aux.get(i).getCareerDescription().toLowerCase().contains(
                                contained + ".")
                        && !aux.get(i).getCareerDescription().toLowerCase().contains(
                                contained + ","))   {
                    check = 0;
                    break;
                }
            }
            if (check == 1)   {
                NameDoubleInt actor = new NameDoubleInt(aux.get(i).getName(), 0, i);
                haveKeywords.add(actor);
            }
        }
        haveKeywords.sort(new SortByName());
        return orderType(v, haveKeywords);
    }

    /**
     *
     * @param input data base
     * @param i index
     * @param videosRating list
     */
    public static
    void moviesByRatingSeeker(final Input input, final int i,
                                 final ArrayList<NameDoubleInt> videosRating)   {
        if (input.getMovies().get(i).getRating() != 0)   {
            NameDoubleInt video =
                    new NameDoubleInt(input.getMovies().get(i).getTitle(),
                            input.getMovies().get(i).getRating(), 0);
            videosRating.add(video);
        }
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortMoviesByRating(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> videosRating = new ArrayList<>();
        if (!v.getFilters().get(1).contains(null)
                && !v.getFilters().get(0).contains(null))   {
            for (int i = 0; i < input.getMovies().size(); i++)   {
                String year = String.valueOf(input.getMovies().get(i).getYear());
                if (input.getMovies().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0))
                        && year.equals(v.getFilters().get(0).get(0)))   {
                    moviesByRatingSeeker(input, i, videosRating);
                }
            }
        } else if (v.getFilters().get(1).contains(null)
                && !v.getFilters().get(0).contains(null))   {
            for (int i = 0; i < input.getMovies().size(); i++)   {
                String year = String.valueOf(input.getMovies().get(i).getYear());
                if (year.equals(v.getFilters().get(0).get(0)))   {
                    moviesByRatingSeeker(input, i, videosRating);
                }
            }
        } else if (!v.getFilters().get(1).contains(null)
                && v.getFilters().get(0).contains(null))   {
            for (int i = 0; i < input.getMovies().size(); i++)   {

                if (input.getMovies().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0)))   {
                    moviesByRatingSeeker(input, i, videosRating);
                }
            }
        } else if (v.getFilters().get(1).contains(null)
                && v.getFilters().get(0).contains(null))   {
            for (int i = 0; i < input.getMovies().size(); i++)   {
                moviesByRatingSeeker(input, i, videosRating);
            }
        }
        videosRating.sort(new SortByDoubleAndName());
        return orderType(v, videosRating);
    }

    /**
     *
     * @param input data base
     * @param i index
     * @param videosRating list
     */
    public static
    void serialsByRatingSeeker(final Input input, final int i,
                                  final ArrayList<NameDoubleInt> videosRating)   {
        if (input.getSerials().get(i).getRating() != 0)   {
            NameDoubleInt video =
                    new NameDoubleInt(input.getSerials().get(i).getTitle(),
                            input.getSerials().get(i).getRating(), 0);
            videosRating.add(video);
        }
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortSerialsByRating(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> videosRating = new ArrayList<>();
        for (int i = 0; i < input.getSerials().size(); i++)   {
            String year = String.valueOf(input.getSerials().get(i).getYear());
            if (!v.getFilters().get(1).contains(null)
                    && !v.getFilters().get(0).contains(null))   {
                if (input.getSerials().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0))
                        && year.equals(v.getFilters().get(0).get(0)))   {
                    serialsByRatingSeeker(input, i, videosRating);
                }
            } else if (v.getFilters().get(1).contains(null)
                    && !v.getFilters().get(0).contains(null))   {
                if (year.equals(v.getFilters().get(0).get(0)))   {
                    serialsByRatingSeeker(input, i, videosRating);
                }
            } else if (!v.getFilters().get(1).contains(null)
                    && v.getFilters().get(0).contains(null))   {
                if (input.getSerials().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0)))   {
                    serialsByRatingSeeker(input, i, videosRating);
                }
            } else if (v.getFilters().get(1).contains(null)
                    && v.getFilters().get(0).contains(null))   {
                serialsByRatingSeeker(input, i, videosRating);
            }
        }
        videosRating.sort(new SortByDoubleAndName());
        return orderType(v, videosRating);
    }

    /**
     *
     * @param input data base
     * @param i index
     * @param isFavorite list
     * @param counter counter
     */
    public static
    void moviesByFavoriteSeeker(final Input input, final int i,
                                   final ArrayList<NameDoubleInt> isFavorite,
                                final int counter)   {
        int count = counter;
        for (UserInputData u : input.getUsers())   {
            if (u.getFavoriteMovies().contains(input.getMovies().get(i).getTitle()))   {
                count++;
            }
        }
        if (count != 0)   {
            NameDoubleInt movie =
                    new NameDoubleInt(input.getMovies().get(i).getTitle(), 0, count);
            isFavorite.add(movie);
        }
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortMoviesByFavorite(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> isFavorite = new ArrayList<>();
        for (int i = 0; i < input.getMovies().size(); i++)   {
            int counter = 0;
            String year = String.valueOf(input.getMovies().get(i).getYear());
            if (!v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getMovies().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0))
                        && year.equals(v.getFilters().get(0).get(0)))   {
                    moviesByFavoriteSeeker(input, i, isFavorite, counter);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getMovies().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0)))   {
                    moviesByFavoriteSeeker(input, i, isFavorite, counter);
                }
            } else if (!v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                if (year.equals(v.getFilters().get(0).get(0)))   {
                    moviesByFavoriteSeeker(input, i, isFavorite, counter);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {

                moviesByFavoriteSeeker(input, i, isFavorite, counter);
            }
        }
        isFavorite.sort(new SortByIntAndName());
        return orderType(v, isFavorite);
    }

    /**
     *
     * @param input data base
     * @param i index
     * @param isFavorite list
     * @param counter counter
     */
    public static
    void serialsByFavoriteSeeker(final Input input, final int i,
                                    final ArrayList<NameDoubleInt> isFavorite,
                                    final int counter)   {
        int count = counter;
        for (UserInputData u : input.getUsers())   {
            if (u.getFavoriteMovies().contains(
                    input.getSerials().get(i).getTitle()))   {
                count++;
            }
        }
        if (count != 0)   {
            NameDoubleInt movie =
                    new NameDoubleInt(input.getSerials().get(i).getTitle(), 0, count);
            isFavorite.add(movie);
        }
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortSerialsByFavorite(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> isFavorite = new ArrayList<>();
        for (int i = 0; i < input.getSerials().size(); i++)   {
            int counter = 0;
            String year = String.valueOf(input.getSerials().get(i).getYear());
            if (!v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getSerials().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0))
                        && year.equals(v.getFilters().get(0).get(0)))   {
                    serialsByFavoriteSeeker(input, i, isFavorite, counter);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getSerials().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0)))   {
                    serialsByFavoriteSeeker(input, i, isFavorite, counter);
                }
            } else if (!v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                if (year.equals(v.getFilters().get(0).get(0)))   {
                    serialsByFavoriteSeeker(input, i, isFavorite, counter);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                serialsByFavoriteSeeker(input, i, isFavorite, counter);
            }
        }
        isFavorite.sort(new SortByIntAndName());
        return orderType(v, isFavorite);
    }

    /**
     *
     * @param input data base
     * @param i index
     * @param duration list
     */
    public static
    void moviesByDurationSeeker(final Input input, final int i,
                                   final ArrayList<NameDoubleInt> duration)   {
        NameDoubleInt movie =
                new NameDoubleInt(input.getMovies().get(i).getTitle(), 0,
                        input.getMovies().get(i).getDuration());
        duration.add(movie);
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortMoviesByDuration(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> duration = new ArrayList<>();
        for (int i = 0; i < input.getMovies().size(); i++)   {
            String year = String.valueOf(input.getMovies().get(i).getYear());
            if (!v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getMovies().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0))
                        && year.equals(v.getFilters().get(0).get(0)))   {
                    moviesByDurationSeeker(input, i, duration);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {

                if (input.getMovies().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0)))   {
                    moviesByDurationSeeker(input, i, duration);
                }
            } else if (!v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                if (year.equals(v.getFilters().get(0).get(0)))   {
                    moviesByDurationSeeker(input, i, duration);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                moviesByDurationSeeker(input, i, duration);
            }
        }
        duration.sort(new SortByIntAndName());
        return orderType(v, duration);
    }

    /**
     *
     * @param input data base
     * @param i index
     * @param duration list
     */
    public static
    void serialsByDurationSeeker(final Input input, final int i,
                                    final ArrayList<NameDoubleInt> duration)   {
        NameDoubleInt movie =
                new NameDoubleInt(input.getSerials().get(i).getTitle(), 0,
                        input.getSerials().get(i).getDuration());
        duration.add(movie);
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortSerialsByDuration(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> duration = new ArrayList<>();
        for (int i = 0; i < input.getSerials().size(); i++)   {
            String year = String.valueOf(input.getSerials().get(i).getYear());
            if (!v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getSerials().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0))
                        && year.equals(v.getFilters().get(0).get(0)))   {
                    serialsByDurationSeeker(input, i, duration);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getSerials().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0)))   {
                    serialsByDurationSeeker(input, i, duration);
                }
            } else if (!v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                if (year.equals(v.getFilters().get(0).get(0)))   {
                    serialsByDurationSeeker(input, i, duration);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                serialsByDurationSeeker(input, i, duration);
            }
        }

        duration.sort(new SortByIntAndName());
        return orderType(v, duration);
    }

    /**
     *
     * @param input data base
     * @param i index
     * @param isFavorite list
     * @param movieViews counter
     */
    public static
    void moviesByViewsSeeker(final Input input, final int i,
                                final ArrayList<NameDoubleInt> isFavorite,
                                final int movieViews)   {
        int views = movieViews;
        for (UserInputData u : input.getUsers())   {
            if (u.getHistory().containsKey(input.getMovies().get(i).getTitle()))   {
                views += u.getHistory().get(input.getMovies().get(i).getTitle());
            }
        }
        if (views != 0)   {
            NameDoubleInt movie = new NameDoubleInt(
                    input.getMovies().get(i).getTitle(), 0, views);
            isFavorite.add(movie);
        }
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortMoviesByViews(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> isFavorite = new ArrayList<>();
        for (int i = 0; i < input.getMovies().size(); i++)   {
            int movieViews = 0;
            String year = String.valueOf(input.getMovies().get(i).getYear());
            if (!v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getMovies().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0))
                        && year.equals(v.getFilters().get(0).get(0)))   {
                    moviesByViewsSeeker(input, i, isFavorite, movieViews);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getMovies().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0)))   {
                    moviesByViewsSeeker(input, i, isFavorite, movieViews);
                }
            } else if (!v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                if (year.equals(v.getFilters().get(0).get(0)))   {
                    moviesByViewsSeeker(input, i, isFavorite, movieViews);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                moviesByViewsSeeker(input, i, isFavorite, movieViews);
            }
        }
        isFavorite.sort(new SortByIntAndName());
        return orderType(v, isFavorite);
    }

    /**
     *
     * @param input data base
     * @param i index
     * @param isFavorite list
     * @param serialViews counter
     */
    public static
    void serialsByViewsSeeker(final Input input, final int i,
                                 final ArrayList<NameDoubleInt> isFavorite,
                                 final int serialViews)   {
        int views = serialViews;
        for (UserInputData u : input.getUsers())   {
            if (u.getHistory().containsKey(input.getSerials().get(i).getTitle()))   {
                views +=
                        u.getHistory().get(input.getSerials().get(i).getTitle());
            }
        }
        if (views != 0)   {
            NameDoubleInt movie = new NameDoubleInt(
                    input.getSerials().get(i).getTitle(), 0, views);
            isFavorite.add(movie);
        }
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortSerialsByViews(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> isFavorite = new ArrayList<>();
        for (int i = 0; i < input.getSerials().size(); i++)   {
            int serialViews = 0;
            String year = String.valueOf(input.getSerials().get(i).getYear());
            if (!v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getSerials().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0))
                        && year.equals(v.getFilters().get(0).get(0)))   {
                    serialsByViewsSeeker(input, i, isFavorite, serialViews);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && !v.getFilters().get(1).contains(null))   {
                if (input.getSerials().get(i).getGenres().contains(
                        v.getFilters().get(1).get(0)))   {
                    serialsByViewsSeeker(input, i, isFavorite, serialViews);
                }
            } else if (!v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                if (year.equals(v.getFilters().get(0).get(0)))   {
                    serialsByViewsSeeker(input, i, isFavorite, serialViews);
                }
            } else if (v.getFilters().get(0).contains(null)
                    && v.getFilters().get(1).contains(null))   {
                serialsByViewsSeeker(input, i, isFavorite, serialViews);
            }
        }
        isFavorite.sort(new SortByIntAndName());
        return orderType(v, isFavorite);
    }

    /**
     *
     * @param input data base
     * @param v command
     * @return String
     */
    public static
    String sortByUserRatingsNumber(final Input input, final ActionInputData v)   {
        ArrayList<NameDoubleInt> usersViews = new ArrayList<>();
        for (UserInputData u : input.getUsers())   {
            if (u.getRatedVideos().size() != 0)   {
                NameDoubleInt user =
                        new NameDoubleInt(u.getUsername(), 0, u.getRatedVideos().size());
                usersViews.add(user);
            }
        }
        usersViews.sort(new SortByIntAndName());
        return  orderType(v, usersViews);
    }
}
