package com.naim.pokemoncatalogue.data.datasource

import android.util.Log
import com.naim.pokemoncatalogue.core.PokemonApi
import com.naim.pokemoncatalogue.core.RetrofitHelper
import com.naim.pokemoncatalogue.data.OnGetPokemonCallback
import com.naim.pokemoncatalogue.data.QueryValueFinder
import com.naim.pokemoncatalogue.data.models.PokemonResponse
import retrofit2.Response

class PokemonDatasource() {
    suspend fun getPokemon(onGetPokemonCallback: OnGetPokemonCallback, url: String? = null) {
        val pokemonApi = RetrofitHelper.getInstance().create(PokemonApi::class.java)
        val result: Response<PokemonResponse> = if (url == null) {
            pokemonApi.getPokemons()
        } else {
            val limit: String? = QueryValueFinder.findLimitQueryValue(url)
            val offset: String? = QueryValueFinder.findOffsetQueryValue(url)
            Log.d("TAG", "getPokemon: Limit = $limit Offset = $offset")
            pokemonApi.getPokemons(limit = limit, offset = offset)
        }
        onGetPokemonCallback.onGetPokemonCallback(result.body())
    }

    suspend fun otherPokemon(url: String, onGetPokemonCallback: OnGetPokemonCallback) {
        val pokemonApi = RetrofitHelper.getInstance().create(PokemonApi::class.java)
        val limit: String? = QueryValueFinder.findLimitQueryValue(url)
        val offset: String? = QueryValueFinder.findOffsetQueryValue(url)
        val result = pokemonApi.getPokemons(limit = limit, offset = offset)
        onGetPokemonCallback.onGetPokemonCallback(result.body())
    }
}