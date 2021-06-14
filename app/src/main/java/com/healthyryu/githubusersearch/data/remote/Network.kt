package com.healthyryu.githubusersearch.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {

		fun retrofitClient(): Retrofit {
				return Retrofit.Builder()
						.baseUrl(BASE_URL)
						.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
						.addConverterFactory(GsonConverterFactory.create())
						.client(okHttpClient())
						.build()
		}

		private fun okHttpClient(): OkHttpClient {
				return OkHttpClient.Builder()
						.retryOnConnectionFailure(true)
						.addInterceptor(HttpLoggingInterceptor().apply {
								level = HttpLoggingInterceptor.Level.BODY
						})
						.pingInterval(30, TimeUnit.SECONDS)
						.readTimeout(1, TimeUnit.MINUTES)
						.connectTimeout(1, TimeUnit.MINUTES)
						.build()
		}

		private const val BASE_URL = "https://api.github.com/"
}