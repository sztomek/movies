package hu.sztomek.movies.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class SearchUiModel(
        val query: String,
        val page: Int,
        val searchResults: List<SearchItemUiModel> = emptyList()
) : UiModel {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(query)
        parcel.writeInt(page)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchUiModel> {
        override fun createFromParcel(parcel: Parcel): SearchUiModel {
            return SearchUiModel(parcel)
        }

        override fun newArray(size: Int): Array<SearchUiModel?> {
            return arrayOfNulls(size)
        }
    }
}