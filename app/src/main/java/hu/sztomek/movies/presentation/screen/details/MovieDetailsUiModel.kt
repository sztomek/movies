package hu.sztomek.movies.presentation.screen.details

import android.os.Parcel
import android.os.Parcelable
import hu.sztomek.movies.presentation.model.UiModel

data class MovieDetailsUiModel(val movieId: Int) : UiModel {

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