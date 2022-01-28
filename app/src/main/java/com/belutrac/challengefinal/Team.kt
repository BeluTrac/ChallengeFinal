package com.belutrac.challengefinal

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Teams")
data class Team(@PrimaryKey val id: String,
                val name: String,
                val formedYear: String,
                val imgUrl: String,
                val stadiumName: String,
                val stadiumCapacity: String,
                val location: String,
                val description: String,
                val website: String,
                val facebookUrl : String,
                val twitterUrl : String,
                val instagramUrl : String,
                var isFav : Boolean
) : Parcelable{
}