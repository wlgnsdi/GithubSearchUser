package com.healthyryu.githubusersearch.data.reposiroty

import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import com.healthyryu.githubusersearch.data.local.dao.GithubUserDao
import com.healthyryu.githubusersearch.data.remote.ApiService
import com.healthyryu.githubusersearch.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
		private val service: ApiService,
		private val localData: GithubUserDao
) : UserRepository {

		/**
		 * Remote
		 */
		override fun getSearchUsers(
				userId: String,
				perPage: Int,
				page: Int
		) = service.getSearchUser(userId, perPage, page)

		override fun getUser(
				userId: String
		) = service.getUser(userId)

		/**
		 * Local
		 */
		override fun getLocalUser(
				login: String
		) = localData.getUsers(login)

		override fun insertLocalUser(
				favoriteUser: FavoriteUser
		) = localData.insert(favoriteUser)

		override fun updateLocalUser(
				favoriteUser: FavoriteUser
		) = localData.update(favoriteUser)

		override fun deleteLocalUser(
				favoriteUser: FavoriteUser
		) = localData.deleteGithubUser(favoriteUser)

}