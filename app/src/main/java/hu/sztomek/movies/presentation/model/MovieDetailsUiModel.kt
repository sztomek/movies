package hu.sztomek.movies.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class MovieDetailsUiModel(
        val movieId: Int,
        val title: String? = null,
        val playTime: String? = null,
        val releaseDate: String? = null,
        val productionCompany: String? = null,
        val rating: String? = null,
        val budget: String? = null,
        val homePage: String? = null,
        val posterUrl: String? = null
) : UiModel {

    constructor(parcel: Parcel) : this(
            parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(movieId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieDetailsUiModel> {
        override fun createFromParcel(parcel: Parcel): MovieDetailsUiModel {
            return MovieDetailsUiModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieDetailsUiModel?> {
            return arrayOfNulls(size)
        }
    }
}