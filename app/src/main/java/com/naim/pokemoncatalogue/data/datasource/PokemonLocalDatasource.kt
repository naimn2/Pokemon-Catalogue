package com.naim.pokemoncatalogue.data.datasource

import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import com.naim.pokemoncatalogue.core.local.DBHelper
import com.naim.pokemoncatalogue.data.models.LocalPokemonResponse
import com.naim.pokemoncatalogue.data.models.PokemonResponse

class PokemonLocalDatasource {
    fun savePokemon(dbHelper: SQLiteOpenHelper, url: String, pokemonResponse: PokemonResponse) {
        if (dbHelper is DBHelper) {
            val gson = Gson()
            val json = gson.toJson(pokemonResponse, PokemonResponse::class.java)
            dbHelper.insertPokemonListResponse(LocalPokemonResponse(json, url))
        }
    }

    fun readPokemon(dbHelper: SQLiteOpenHelper, url: String): PokemonResponse? {
        if (dbHelper is DBHelper) {
            val gson = Gson()
            val localPokemonResponse = dbHelper.readPokemonListResponse(url)
            return gson.fromJson(
                localPokemonResponse?.data,
                PokemonResponse::class.java
            )
        }
        return null
    }
}