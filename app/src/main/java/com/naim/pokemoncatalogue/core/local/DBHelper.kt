package com.naim.pokemoncatalogue.core.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.naim.pokemoncatalogue.data.models.LocalPokemonResponse

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                URL_COl + " TEXT," +
                DATA_COL + " TEXT" + ")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun insertPokemonListResponse(localPokemonResponse: LocalPokemonResponse) {

        val values = ContentValues()

        values.put(URL_COl, localPokemonResponse.url)
        values.put(DATA_COL, localPokemonResponse.data)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun readPokemonListResponse(url: String): LocalPokemonResponse? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        var result: LocalPokemonResponse? = null
        while (cursor.moveToNext() && result == null) {
            val readUrl = cursor.getString(cursor.getColumnIndex(URL_COl) ?: 0)
            if (url == readUrl) {
                result = LocalPokemonResponse(
                    cursor.getString(cursor.getColumnIndex(DATA_COL) ?: 0),
                    readUrl
                )
            }
        }

        cursor.close()
        return result
    }

    companion object {
        private val DATABASE_NAME = "POKEMON_CATALOGUE"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "pokemon_list_response"
        val ID_COL = "id"
        val URL_COl = "url"
        val DATA_COL = "data"
    }
}
