package com.naim.pokemoncatalogue.data.datasource

import android.util.Log
import com.naim.pokemoncatalogue.core.remote.PokemonApi
import com.naim.pokemoncatalogue.core.remote.RetrofitHelper
import com.naim.pokemoncatalogue.data.OnGetDetailPokemonCallback
import com.naim.pokemoncatalogue.data.OnGetPokemonCallback
import com.naim.pokemoncatalogue.data.QueryValueFinder
import com.naim.pokemoncatalogue.data.models.PokemonResponse
import retrofit2.Response
import retrofit2.create

class PokemonRemoteDatasource() {
    private var pokemonApi: PokemonApi = RetrofitHelper.getInstance().create(PokemonApi::class.java)

    suspend fun getPokemon(onGetPokemonCallback: OnGetPokemonCallback, url: String? = null) {
        val result: Response<PokemonResponse> = if (url == null) {
            pokemonApi.getPokemons()
        } else {
            val limit: String? = QueryValueFinder.findLimitQueryValue(url)
            val offset: String? = QueryValueFinder.findOffsetQueryValue(url)
            pokemonApi.getPokemons(limit = limit, offset = offset)
        }
        onGetPokemonCallback.onGetPokemonCallback(result.body())
    }

    suspend fun getPokemonDetail(id: String, onGetDetailPokemonCallback: OnGetDetailPokemonCallback) {
        val result = pokemonApi.getPokemonDetail(id)
        onGetDetailPokemonCallback.onGetDetailPokemonCallback(result.body())
    }
}