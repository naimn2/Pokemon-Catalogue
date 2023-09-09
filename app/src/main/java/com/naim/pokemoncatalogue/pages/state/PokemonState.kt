package com.naim.pokemoncatalogue.pages.state

import com.naim.pokemoncatalogue.data.models.PokemonResponse

class PokemonState(
    val value: PokemonResponse? = null,
    val status: PokemonStateStatus = PokemonStateStatus.INITIAL
) {
    fun copy(value: PokemonResponse? = this.value, status: PokemonStateStatus = this.status) =
        PokemonState(value, status)
}

enum class PokemonStateStatus {
    INITIAL, LOADING, SUCCESS, FAILED
}