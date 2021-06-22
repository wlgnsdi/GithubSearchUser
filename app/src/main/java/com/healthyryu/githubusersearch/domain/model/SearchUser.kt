package com.healthyryu.githubusersearch.domain.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SearchUser(
		@PrimaryKey
		val id: Int,
		val login: String,
		@SerializedName("node_id")
		val nodeId: String,
		@SerializedName("avatar_url")
		val avatarUrl: String,
		@SerializedName("gravatar_id")
		val gravatarId: String,
		val url: String,
		@SerializedName("html_url")
		val htmlUrl: String,
		@SerializedName("followers_url")
		val followersUrl: String,
		@SerializedName("subscriptions_url")
		val subscriptionsUrl: String,
		@SerializedName("organizations_url")
		val organizationsUrl: String,
		@SerializedName("repos_url")
		val reposUrl: String,
		@SerializedName("received_events_url")
		val received_eventsUrl: String,
		val type: String,
		val score: Int,
		@SerializedName("following_url")
		val followingUrl: String,
		@SerializedName("gists_url")
		val gistsUrl: String,
		@SerializedName("starred_url")
		val starredUrl: String,
		@SerializedName("events_url")
		val eventsUrl: String,
		@SerializedName("site_admin")
		val siteAdmin: Boolean,
		var isFavorite: Boolean = false
)