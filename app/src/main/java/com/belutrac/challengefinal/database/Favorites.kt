package com.belutrac.challengefinal.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class Favorites(@PrimaryKey val id: String) {
}