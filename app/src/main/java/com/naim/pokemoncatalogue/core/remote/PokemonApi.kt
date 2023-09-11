package com.naim.pokemoncatalogue.core.remote

import com.naim.pokemoncatalogue.data.models.PokemonDetailResponse
import com.naim.pokemoncatalogue.data.models.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: String? = null,
        @Query("limit") limit: String? = null
    ): Response<PokemonResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: String): Response<PokemonDetailResponse>

}
