package com.healthyryu.githubusersearch.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class FavoriteUser(
		@PrimaryKey
		val id: Int,
		val login: String,
		val profileImgUrl: String,
		var memo: String = ""
) : Parcelable
