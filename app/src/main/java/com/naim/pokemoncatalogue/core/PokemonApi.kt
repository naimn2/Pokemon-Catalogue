package com.naim.pokemoncatalogue.core

import com.naim.pokemoncatalogue.data.models.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: String? = null,
        @Query("limit") limit: String? = null
    ): Response<PokemonResponse>

}
