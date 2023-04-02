package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        // РЕАЛИЗАЦИЮ ПИШЕМ ТУТ
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                while (!Character.isDigit(line.charAt(0))) {
                    line = reader.readLine();
                }
                List<String> data = List.of(line.split(";"));
                List<Movie> movies = new ArrayList<>();
                movies.add(mapToMovie(data));
                loadToDataBase(movies);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    private Movie mapToMovie(List<String> data) {
        Movie movie = new Movie();

        if (!data.get(0).isEmpty()) {
            movie.setYear(Integer.parseInt(data.get(0)));
        } else {
            movie.setYear(null);
        }
        if (!data.get(1).isEmpty()) {
            movie.setLength(Integer.parseInt(data.get(1)));
        } else {
            movie.setLength(null);
        }
        if (!data.get(2).isEmpty()) {
            movie.setTitle(data.get(2));
        } else {
            movie.setTitle(null);
        }
        if (!data.get(3).isEmpty()) {
            movie.setSubject(data.get(3));
        } else {
            movie.setSubject(null);
        }
        if (!data.get(4).isEmpty()) {
            movie.setActors(data.get(4));
        } else {
            movie.setActors(null);
        }
        if (!data.get(5).isEmpty()) {
            movie.setActress(data.get(5));
        } else {
            movie.setActress(null);
        }
        if (!data.get(6).isEmpty()) {
            movie.setDirector(data.get(6));
        } else {
            movie.setDirector(null);
        }
        if (!data.get(7).isEmpty()) {
            movie.setPopularity(Integer.parseInt(data.get(7)));
        } else {
            movie.setPopularity(null);
        }
        if ("Yes".equals(data.get(8))) {
            movie.setAwards(true);
        } else {
            movie.setAwards(false);
        }
        return movie;
    }

    private void loadToDataBase(List<Movie> movies) {

        String sql = "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            for (Movie movie : movies) {
                if (!Objects.isNull(movie.getYear())) {
                    statement.setInt(1, movie.getYear());
                } else {
                    statement.setNull(1, Types.INTEGER);
                }
                if (!Objects.isNull(movie.getLength())) {
                    statement.setInt(2, movie.getLength());
                } else {
                    statement.setNull(2, Types.INTEGER);
                }

                if (!Objects.isNull(movie.getTitle())) {
                    statement.setString(3, movie.getTitle());
                } else {
                    statement.setNull(3, Types.VARCHAR);
                }

                if (!Objects.isNull(movie.getSubject())) {
                    statement.setString(4, movie.getSubject());
                } else {
                    statement.setNull(4, Types.VARCHAR);
                }
                if (!Objects.isNull(movie.getActors())) {
                    statement.setString(5, movie.getActors());
                } else {
                    statement.setNull(5, Types.VARCHAR);
                }
                if (!Objects.isNull(movie.getActress())) {
                    statement.setString(6, movie.getActress());
                } else {
                    statement.setNull(6, Types.VARCHAR);
                }
                if (!Objects.isNull(movie.getDirector())) {
                    statement.setString(7, movie.getDirector());
                } else {
                    statement.setNull(7, Types.VARCHAR);
                }

                if (!Objects.isNull(movie.getPopularity())) {
                    statement.setInt(8, movie.getPopularity());
                } else {
                    statement.setNull(8, Types.INTEGER);
                }
                if (!Objects.isNull(movie.getAwards())) {
                    statement.setBoolean(9, movie.getAwards());
                } else {
                    statement.setNull(9, Types.BOOLEAN);
                }
                statement.addBatch();
            }

            try {
                statement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
