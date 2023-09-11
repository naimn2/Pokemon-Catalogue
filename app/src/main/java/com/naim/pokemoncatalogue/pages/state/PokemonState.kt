package com.naim.pokemoncatalogue.pages.state

import android.util.Log
import com.naim.pokemoncatalogue.data.models.PokemonResponse
import com.naim.pokemoncatalogue.data.models.ResultsItem

class PokemonState(
    val value: PokemonResponse? = null,
    val status: PokemonStateStatus = PokemonStateStatus.INITIAL,
    val querySearch: String = "",
    val sortType: PokemonSortType = PokemonSortType.ASCENDING
) {
    fun copy(
        value: PokemonResponse? = this.value,
        status: PokemonStateStatus = this.status,
        querySearch: String = this.querySearch,
        sortType: PokemonSortType = this.sortType
    ) =
        PokemonState(value, status, querySearch, sortType)

    fun filterAndSort(): List<ResultsItem?> {
        if (value?.results?.isNotEmpty() == true) {
            var result = value.results
            // filter by query
            if (querySearch.isNotEmpty()) {
                result = value.results.filter {
                    it?.name?.uppercase()?.contains(querySearch.uppercase()) == true
                }
            }
            // sort
            if (sortType == PokemonSortType.ASCENDING) {
                result = result.sortedBy { it?.name }
            } else {
                result = result.sortedByDescending { it?.name }
            }
            Log.d("filterAndSort", "result: ${result.map { it?.name }}")
            return result
        }
        return ArrayList<ResultsItem>()
    }

    fun isSortTypeAscending(): Boolean {
        return sortType == PokemonSortType.ASCENDING
    }

    fun isSortTypeDescending(): Boolean {
        return sortType == PokemonSortType.DESCENDING
    }
}

enum class PokemonStateStatus {
    INITIAL, LOADING, SUCCESS, FAILED
}

enum class PokemonSortType {
    ASCENDING, DESCENDING
}