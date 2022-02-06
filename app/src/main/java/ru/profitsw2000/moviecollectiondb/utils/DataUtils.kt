package ru.profitsw2000.moviecollectiondb.utils

import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DescriptionDTO
import ru.profitsw2000.moviecollectiondb.room.FavoriteEntity
import ru.profitsw2000.moviecollectiondb.room.NoteEntity

fun convertDescriptionDTOToModel(descriptionDTO: DescriptionDTO) : Movie {

    var genresList: ArrayList<String> = arrayListOf()
    var i = descriptionDTO.genres?.size

    //get string with genres of movie from List of strings
    if (i != null) {
        var j = 0
        while (i > 0) {
            genresList.add(descriptionDTO.genres?.get(j)?.name ?: "undefined")
            i--
            j++
        }
    }
    val genresString = genresList.joinToString(separator = ", ")

    with(descriptionDTO){
        return Movie(title!!, genresString, runtime!!, vote_average!!,
            budget!!, revenue!!, release_date!!, overview!!, poster_path!!,
            id!!)
    }
}

fun convertMovieToNoteEntity(movie: Movie, note: String): NoteEntity {
    return NoteEntity(0, movie.title, movie.id, note)
}

fun convertMovieToTitle(movie: Movie): FavoriteEntity {
    return FavoriteEntity(0, movie.title)
}
