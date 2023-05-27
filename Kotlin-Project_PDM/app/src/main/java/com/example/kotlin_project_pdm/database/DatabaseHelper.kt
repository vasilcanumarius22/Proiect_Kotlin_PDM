package com.example.kotlin_project_pdm.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.kotlin_project_pdm.models.User
import java.lang.Exception

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT," + COLUMN_PASSWORD + " TEXT" + ")")
        db.execSQL(CREATE_USER_TABLE)

        val CREATE_FAVORITES_TABLE = ("CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY + " TEXT,"
                + COLUMN_USERNAME + " TEXT)")
        db.execSQL(CREATE_FAVORITES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITES")
        onCreate(db)
    }

    fun addFavoriteItem(city: String, username: String)
    {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_CITY, city)
        values.put(COLUMN_USERNAME, username)

        db.insert(TABLE_FAVORITES, null, values)
        db.close()
    }

    fun getFavoritesItems(username: String): ArrayList<String> {
        val db = this.readableDatabase
        val columns = arrayOf(COLUMN_CITY)
        val selection = "$COLUMN_USERNAME LIKE ?"
        val selectionArgs = arrayOf(username)

        val cursor = db.query(TABLE_FAVORITES, columns, selection, selectionArgs, null, null, null)

        val favorites = ArrayList<String>()

        if (cursor.moveToFirst()) {
            var columnIndex = 0
            while (columnIndex < cursor.count) {
                if(columnIndex != -1)
                {
                    try {
                        val city = cursor.getString(0)
                        favorites.add(city)
                    }
                    catch (e: Exception) {
                        Log.e("favorites", "Eroare la citire orase")
                    }

                }
                cursor.moveToNext()
                columnIndex = cursor.position
            }
        }

        cursor.close()
        db.close()

        return favorites
    }


    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USERNAME, user.username)
        values.put(COLUMN_EMAIL, user.email)
        values.put(COLUMN_PASSWORD, user.password)

        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun checkUser(email: String, password: String): Boolean {
        val columns = arrayOf(COLUMN_ID)
        val db = this.readableDatabase

        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(
            TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }
        return false
    }

    fun getUsernameByEmailAndPassword(email: String, password: String): String? {
        val db = this.readableDatabase
        val columns = arrayOf(COLUMN_USERNAME)
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null)
        var username: String? = null

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(COLUMN_USERNAME)
            if (columnIndex >= 0) {
                username = cursor.getString(columnIndex)
            }
        }
        cursor.close()
        db.close()

        return username
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Weather.db"
        private const val TABLE_USER = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"

        private const val TABLE_FAVORITES = "favorites"
        private const val COLUMN_CITY = "city"
    }
}