package com.belutrac.challengefinal.database

import androidx.room.*

@Dao
interface FavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fav : Favorites)

    @Query("SELECT * FROM FAVORITES")
    fun getFavs() : MutableList<Favorites>

    @Query("DELETE FROM Favorites WHERE id = :id")
    fun delete(id : String)
}