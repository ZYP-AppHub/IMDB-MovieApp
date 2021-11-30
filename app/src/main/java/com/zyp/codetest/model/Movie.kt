package com.zyp.codetest.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Movie")
data class Movie(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("release_date")
    val releaseDate: String,

    var movieType : String?,

    var favorite: Boolean

) : Parcelable
