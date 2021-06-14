package com.healthyryu.githubusersearch.data.local.dao

import androidx.room.*
import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GithubUserDao {

		@Query("SELECT * FROM user WHERE login LIKE '%' || :login || '%'")
		fun getUsers(login: String): Single<List<FavoriteUser>>

		@Insert(onConflict = OnConflictStrategy.REPLACE)
		fun insert(vararg favoriteUser: FavoriteUser): Completable

		@Update
		fun update(vararg favoriteUser: FavoriteUser): Completable

		@Delete
		fun deleteGithubUser(vararg favoriteUser: FavoriteUser): Completable
}