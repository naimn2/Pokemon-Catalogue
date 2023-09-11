package com.naim.pokemoncatalogue.data.repository

import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.naim.pokemoncatalogue.data.OnGetDetailPokemonCallback
import com.naim.pokemoncatalogue.data.OnGetPokemonCallback
import com.naim.pokemoncatalogue.data.datasource.PokemonLocalDatasource
import com.naim.pokemoncatalogue.data.datasource.PokemonRemoteDatasource
import com.naim.pokemoncatalogue.data.models.PokemonResponse

class PokemonRepository(
    val pokemonRemoteDatasource: PokemonRemoteDatasource,
    val pokemonLocalDatasource: PokemonLocalDatasource
) {
    suspend fun getPokemon(
        dbHelper: SQLiteOpenHelper,
        onGetPokemonCallback: OnGetPokemonCallback,
        url: String? = null
    ) {
        try {
            val pokemonResponseFromLocal = pokemonLocalDatasource.readPokemon(dbHelper, url ?: "")
            if (pokemonResponseFromLocal != null) {
                onGetPokemonCallback.onGetPokemonCallback(pokemonResponseFromLocal)
            } else {
                pokemonRemoteDatasource.getPokemon(object : OnGetPokemonCallback {
                    override fun onGetPokemonCallback(pokemonResponse: PokemonResponse?) {
                        if (pokemonResponse != null) {
                            pokemonLocalDatasource.savePokemon(
                                dbHelper, url ?: "",
                                pokemonResponse
                            )
                        }
                        onGetPokemonCallback.onGetPokemonCallback(pokemonResponse)
                    }
                }, url = url)
            }
        } catch (e: Exception) {
            Log.e("TAG", "getPokemon: exception ${e.message}")
            onGetPokemonCallback.onGetPokemonCallback(null)
        }
    }

    suspend fun getPokemonDetail(id: String, onGetDetailPokemonCallback: OnGetDetailPokemonCallback) {
        pokemonRemoteDatasource.getPokemonDetail(id, onGetDetailPokemonCallback)
    }
}