package com.example.periodictable.classes

import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.Gson
import org.w3c.dom.Text
import kotlinx.android.parcel.Parcelize


class Element {
    @Parcelize
    data class cElement(
        var symbol: String,
        var name: String,
        var number: Int,
        var atomic_mass: Double,
        var category: String,
        var phase: String,
        var favorite: Boolean = false,
        var summary: String,
        var discovered_by: String?,
        var boil: Double,
        var bohr_model_image: String?
    ) : Parcelable
}
