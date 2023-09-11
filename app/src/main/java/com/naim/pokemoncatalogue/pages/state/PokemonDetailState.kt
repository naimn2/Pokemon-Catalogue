package com.naim.pokemoncatalogue.pages.state

import com.naim.pokemoncatalogue.data.models.PokemonDetailResponse

class PokemonDetailState(
    val value: PokemonDetailResponse? = null,
    val status: PokemonStateStatus = PokemonStateStatus.INITIAL
) {
    fun copy(value: PokemonDetailResponse? = this.value, status: PokemonStateStatus = this.status) =
        PokemonDetailState(value, status)
}