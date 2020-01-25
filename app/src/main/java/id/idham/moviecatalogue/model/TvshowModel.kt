package id.idham.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by idhammi on 1/25/2020.
 */

@Parcelize
data class TvshowModel(
    var photo: String,
    var name: String,
    var description: String,
    var year: String,
    var rating: String,
    var creator: String,
    var cast1: String,
    var cast2: String,
    var cast3: String,
    var castPhoto1: String,
    var castPhoto2: String,
    var castPhoto3: String,
    var language: String,
    var runtime: String
) : Parcelable