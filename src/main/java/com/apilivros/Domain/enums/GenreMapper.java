package com.apilivros.Domain.enums;

import java.util.*;

public class GenreMapper {

    private static final Map<String, Genre> subjectToGenre = new HashMap<>();

    static {
        subjectToGenre.put("fantasy", Genre.FANTASY);
        subjectToGenre.put("horror", Genre.HORROR);
        subjectToGenre.put("thriller", Genre.THRILLER);
        subjectToGenre.put("science fiction", Genre.SCIENCE_FICTION);
        subjectToGenre.put("romance", Genre.ROMANCE);
        subjectToGenre.put("adventure", Genre.ADVENTURE);
        subjectToGenre.put("history", Genre.HISTORY);
        subjectToGenre.put("biography", Genre.BIOGRAPHY);
        subjectToGenre.put("poetry", Genre.POETRY);
        subjectToGenre.put("philosophy", Genre.PHILOSOPHY);
        subjectToGenre.put("children", Genre.CHILDRENS);
        subjectToGenre.put("children's", Genre.CHILDRENS);
        subjectToGenre.put("young adult", Genre.YOUNG_ADULT);
        subjectToGenre.put("dystopia", Genre.DYSTOPIA);
        subjectToGenre.put("graphic novel", Genre.GRAPHIC_NOVEL);
        subjectToGenre.put("short story", Genre.SHORT_STORY);
        subjectToGenre.put("paranormal", Genre.PARANORMAL);
        subjectToGenre.put("sports", Genre.SPORTS);
        subjectToGenre.put("travel", Genre.TRAVEL);
    }

    public static Genre mapSubjectToGenre(String subject) {
        if (subject == null) return null;
        return subjectToGenre.get(subject.toLowerCase());
    }

    public static List<Integer> mapSubjectsToGenreCodes(List<String> subjects) {
        List<Integer> codes = new ArrayList<>();
        for (String subject : subjects) {
            Genre genre = mapSubjectToGenre(subject);
            if (genre != null) {
                codes.add(genre.getCode()); 
            }
        }
        return codes;
    }
}
