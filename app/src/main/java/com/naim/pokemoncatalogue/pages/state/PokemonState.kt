package com.naim.pokemoncatalogue.pages.state

import com.naim.pokemoncatalogue.data.models.PokemonResponse
import com.naim.pokemoncatalogue.data.models.ResultsItem

class PokemonState(
    val value: PokemonResponse? = null,
    val status: PokemonStateStatus = PokemonStateStatus.INITIAL,
    val querySearch: String = ""
) {
    fun copy(
        value: PokemonResponse? = this.value,
        status: PokemonStateStatus = this.status,
        querySearch: String = this.querySearch
    ) =
        PokemonState(value, status, querySearch)

    fun filter(): List<ResultsItem?> {
        if (value?.results?.isNotEmpty() == true) {
            if (querySearch.isEmpty()) return value.results
            return value.results.filter {
                it?.name?.uppercase()?.contains(querySearch.uppercase()) == true
            }
        }
        return ArrayList<ResultsItem>()
    }
}

enum class PokemonStateStatus {
    INITIAL, LOADING, SUCCESS, FAILED
}