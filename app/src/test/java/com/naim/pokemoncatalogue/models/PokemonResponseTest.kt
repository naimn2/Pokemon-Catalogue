package com.naim.pokemoncatalogue.models

import android.util.Log
import com.google.gson.Gson
import com.naim.pokemoncatalogue.data.models.PokemonResponse
import org.junit.Assert.*
import org.junit.Test

class PokemonResponseTest {

    @Test
    fun testParsingFromJson() {
        val gson = Gson()
        val json = "{\"count\":123}"
        val result = gson.fromJson(json, PokemonResponse::class.java)
//        Log.d("TAG", "parseFromJson: " + result)
        assertEquals(PokemonResponse(count = 123), result)
    }

    @Test
    fun testParsingToJson() {
        val gson = Gson()
        val json = PokemonResponse(count = 123)
        val result = gson.toJson(json, PokemonResponse::class.java)
//        Log.d("TAG", "parseFromJson: " + result)
        assertEquals("{\"count\":123}", result)
    }
}