package com.naim.pokemoncatalogue.data

import com.naim.pokemoncatalogue.data.models.PokemonDetailResponse

interface OnGetDetailPokemonCallback {
    fun onGetDetailPokemonCallback(pokemonDetailResponse: PokemonDetailResponse?)
}