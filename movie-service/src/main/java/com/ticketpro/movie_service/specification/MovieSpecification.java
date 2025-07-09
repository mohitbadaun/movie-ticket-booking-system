package com.ticketpro.movie_service.specification;

import com.ticketpro.movie_service.entity.Movie;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecification {
    public static Specification<Movie> hasLanguage(String language) {
        if (language == null) return null;
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("language")), language.toLowerCase());
    }


    public static Specification<Movie> hasGenre(String genre) {
        if (genre == null) return null;
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("genre")), genre.toLowerCase());
    }


    public static Specification<Movie> hasRating(Double rating) {
        return (root, query, builder) ->
                rating == null ? null : builder.greaterThanOrEqualTo(root.get("rating"), rating);
    }
}

