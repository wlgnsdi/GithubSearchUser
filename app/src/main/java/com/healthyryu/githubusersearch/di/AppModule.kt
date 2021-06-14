package com.healthyryu.githubusersearch.di

import android.content.Context
import com.healthyryu.githubusersearch.data.local.AppDatabase
import com.healthyryu.githubusersearch.data.local.dao.GithubUserDao
import com.healthyryu.githubusersearch.data.remote.ApiService
import com.healthyryu.githubusersearch.data.remote.Network
import com.healthyryu.githubusersearch.data.reposiroty.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

		@Singleton
		@Provides
		fun provideDatabase(
				@ApplicationContext appContext: Context
		): AppDatabase {
				return AppDatabase.getDatabase(appContext)
		}

		@Singleton
		@Provides
		fun provideGithubUserDao(
				db: AppDatabase
		): GithubUserDao {
				return db.githubUserDao()
		}

		@Singleton
		@Provides
		fun provideRetrofit(): Retrofit {
				return Network.retrofitClient()
		}

		@Singleton
		@Provides
		fun provideApiService(
				retrofit: Retrofit
		): ApiService {
				return retrofit.create(ApiService::class.java)
		}

		@Singleton
		@Provides
		fun provideUserRepository(
				apiService: ApiService,
				localData: GithubUserDao
		): UserRepositoryImpl {
				return UserRepositoryImpl(apiService, localData)
		}
}