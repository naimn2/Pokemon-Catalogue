package com.naim.pokemoncatalogue.data.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PokemonDetailResponse(

	@field:SerializedName("abilities")
	val abilities: List<AbilitiesItem?>? = null,

	@field:SerializedName("base_experience")
	val baseExperience: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("is_default")
	val isDefault: Boolean? = null,

	@field:SerializedName("sprites")
	val sprites: Sprites? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("order")
	val order: Int? = null
) : Parcelable

@Parcelize
data class Sprites(

	@field:SerializedName("front_default")
	val frontDefault: String? = null
) : Parcelable

@Parcelize
data class Ability(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class AbilitiesItem(

	@field:SerializedName("is_hidden")
	val isHidden: Boolean? = null,

	@field:SerializedName("ability")
	val ability: Ability? = null,

	@field:SerializedName("slot")
	val slot: Int? = null
) : Parcelable
