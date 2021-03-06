package ru.profitsw2000.moviecollectiondb.model.representation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.profitsw2000.moviecollectiondb.R
import java.util.*

@Parcelize
data class Movie(
    val title: String = "Никто",
    val genre: String = "боевик, триллер",
    val duration: Int = 92,
    val rating: Float = 7.3f ,
    val budget: Int = 16000000,
    val revenue: Int = 55405035,
    val releaseDate: String = "18 марта 2021",
    val description: String = "Непримечательный и незаметный семьянин Хатч " +
            "живёт скучной жизнью обычного аудитора, пока однажды в его дом не " +
            "вламываются грабители. И это бы сошло им с рук, если бы они не " +
            "забрали браслетик его маленькой дочки. Не в силах это терпеть, " +
            "Хатч отправляется на поиски наглецов, а на обратном пути ввязывается " +
            "в драку с пьяными хулиганами, пристававшими к девушке в общественном " +
            "транспорте. От души помахав кулаками, наш аудитор отправляет дебоширов" +
            " в больницу, но оказывается, что один из пострадавших — брат " +
            "влиятельного русского бандита. И он теперь жаждет мести.",
    val picture: Int = R.drawable.film
) : Parcelable

