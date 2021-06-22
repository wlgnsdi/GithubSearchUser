package com.healthyryu.githubusersearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import com.healthyryu.githubusersearch.data.local.dao.GithubUserDao

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

		abstract fun githubUserDao(): GithubUserDao

		companion object {

				@Volatile
				private var instance: AppDatabase? = null

				fun getDatabase(context: Context): AppDatabase =
						instance ?: synchronized(this) {
								instance ?: buildDatabase(context).also {
										instance = it
								}
						}

				private fun buildDatabase(appContext: Context) =
						Room.databaseBuilder(appContext, AppDatabase::class.java, "githubuser¬")
								.fallbackToDestructiveMigration()
								.build()
		}

}