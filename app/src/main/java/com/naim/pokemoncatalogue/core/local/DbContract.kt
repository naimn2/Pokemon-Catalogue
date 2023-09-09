package com.naim.pokemoncatalogue.core.local

import android.provider.BaseColumns

object DbContract {
    object PokemonEntry : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_SUBTITLE = "subtitle"
    }
}