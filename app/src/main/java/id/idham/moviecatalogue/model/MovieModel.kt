package id.idham.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by idhammi on 12/27/2019.
 */

@Parcelize
data class MovieModel(
    var photo: Int,
    var name: String,
    var description: String,
    var year: String,
    var rating: String,
    var director: String,
    var screenplay: String,
    var cast1: String,
    var cast2: String,
    var cast3: String,
    var castPhoto1: Int,
    var castPhoto2: Int,
    var castPhoto3: Int,
    var language: String,
    var runtime: String
) : Parcelable