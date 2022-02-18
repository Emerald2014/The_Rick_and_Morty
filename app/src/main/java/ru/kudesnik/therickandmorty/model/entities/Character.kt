package ru.kudesnik.therickandmorty.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.StringBufferInputStream

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val locationName: String,
    val image: String,
    val episode: String,
    val firstEpisode: String,
    val next: String?,
    val prev: String?

) : Parcelable
