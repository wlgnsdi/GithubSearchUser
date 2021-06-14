package com.healthyryu.githubusersearch.data.reposiroty

import com.healthyryu.githubusersearch.data.entity.DetailUser
import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import com.healthyryu.githubusersearch.data.entity.Response
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

		/**
		 *  Remote
		 */
		fun getSearchUsers(userId: String, perPage: Int, page: Int): Single<Response>

		fun getUser(userId: String): Single<DetailUser>

		/**
		 * Local
		 */
		fun getLocalUser(login: String): Single<List<FavoriteUser>>

		fun insertLocalUser(favoriteUser: FavoriteUser): Completable

		fun updateLocalUser(favoriteUser: FavoriteUser): Completable

		fun deleteLocalUser(favoriteUser: FavoriteUser): Completable
}