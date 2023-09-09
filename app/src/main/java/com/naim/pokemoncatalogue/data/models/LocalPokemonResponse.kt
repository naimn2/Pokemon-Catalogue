package com.naim.pokemoncatalogue.data.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LocalPokemonResponse(

	@field:SerializedName("data")
	val data: String = "{}",

	@field:SerializedName("url")
	val url: String = ""

) : Parcelable
