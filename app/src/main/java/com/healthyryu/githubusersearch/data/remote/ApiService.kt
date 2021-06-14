package com.healthyryu.githubusersearch.data.remote

import com.healthyryu.githubusersearch.data.entity.Response
import com.healthyryu.githubusersearch.data.entity.DetailUser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

		@GET("/search/users?")
		fun getSearchUser(
				@Query("q") q: String,
				@Query("per_page") perPage: Int,
				@Query("page") page: Int
		): Single<Response>

		@GET("/users/{user_name}")
		fun getUser(
				@Path("user_name") userName: String
		): Single<DetailUser>
}