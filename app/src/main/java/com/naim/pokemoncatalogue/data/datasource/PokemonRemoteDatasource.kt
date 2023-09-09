package com.naim.pokemoncatalogue.data.datasource

import android.util.Log
import com.naim.pokemoncatalogue.core.remote.PokemonApi
import com.naim.pokemoncatalogue.core.remote.RetrofitHelper
import com.naim.pokemoncatalogue.data.OnGetPokemonCallback
import com.naim.pokemoncatalogue.data.QueryValueFinder
import com.naim.pokemoncatalogue.data.models.PokemonResponse
import retrofit2.Response

class PokemonRemoteDatasource() {
    suspend fun getPokemon(onGetPokemonCallback: OnGetPokemonCallback, url: String? = null) {
        val pokemonApi = RetrofitHelper.getInstance().create(PokemonApi::class.java)
        val result: Response<PokemonResponse> = if (url == null) {
            pokemonApi.getPokemons()
        } else {
            val limit: String? = QueryValueFinder.findLimitQueryValue(url)
            val offset: String? = QueryValueFinder.findOffsetQueryValue(url)
            pokemonApi.getPokemons(limit = limit, offset = offset)
        }
        onGetPokemonCallback.onGetPokemonCallback(result.body())
    }
}