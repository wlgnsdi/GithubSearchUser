package com.healthyryu.githubusersearch.domain.model

import com.google.gson.annotations.SerializedName
import com.healthyryu.githubusersearch.domain.model.SearchUser

data class Response(
		@SerializedName("total_count")
		val totalCount: Int,
		@SerializedName("incomplete_results")
		val incompleteResults: Boolean,
		val items: List<SearchUser>
)