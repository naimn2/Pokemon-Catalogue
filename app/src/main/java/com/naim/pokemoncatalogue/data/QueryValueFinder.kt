package com.naim.pokemoncatalogue.data

class QueryValueFinder {
    companion object {
        private val OFFSET_REGEX_PATTERN =
            """https://pokeapi.co/api/v2/pokemon/?.*[?&]offset=([^#&]+).*""".toRegex()
        private val LIMIT_REGEX_PATTERN =
            """https://pokeapi.co/api/v2/pokemon/?.*[?&]limit=([^#&]+).*""".toRegex()

        fun findOffsetQueryValue(url: String): String? {
            return OFFSET_REGEX_PATTERN.matchEntire(url)?.groups?.get(1)?.value
        }

        fun findLimitQueryValue(url: String): String? {
            return LIMIT_REGEX_PATTERN.matchEntire(url)?.groups?.get(1)?.value
        }
    }
}