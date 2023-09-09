package com.naim.pokemoncatalogue.data

import com.naim.pokemoncatalogue.data.models.PokemonResponse

interface OnGetPokemonCallback {
    fun onGetPokemonCallback(pokemonResponse: PokemonResponse?)
}