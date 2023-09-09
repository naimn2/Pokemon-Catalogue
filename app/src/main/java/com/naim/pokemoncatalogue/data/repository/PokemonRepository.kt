package com.naim.pokemoncatalogue.data.repository

import com.naim.pokemoncatalogue.data.OnGetPokemonCallback
import com.naim.pokemoncatalogue.data.datasource.PokemonDatasource

class PokemonRepository(val pokemonDatasource: PokemonDatasource) {
    suspend fun getPokemon(onGetPokemonCallback: OnGetPokemonCallback, url: String? = null) {
        try {
            pokemonDatasource.getPokemon(onGetPokemonCallback, url = url)
        } catch (e: Exception) {
            onGetPokemonCallback.onGetPokemonCallback(null)
        }
    }
}