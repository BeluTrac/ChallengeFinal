package com.belutrac.challengefinal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Teams")
data class Team(@PrimaryKey val id : String, val name : String, val imgUrl : String) {
}