package com.healthyryu.githubusersearch.ui.user

import androidx.lifecycle.MutableLiveData
import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import com.healthyryu.githubusersearch.domain.model.SearchUser
import com.healthyryu.githubusersearch.data.reposiroty.UserRepositoryImpl
import com.healthyryu.githubusersearch.ui.commons.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
		private val userRepositoryImpl: UserRepositoryImpl
) : BaseViewModel() {

		val liveDataGithubUsers = MutableLiveData<List<SearchUser>>()
		val liveDataGithubUsersMore = MutableLiveData<List<SearchUser>>()
		val liveDataComplete = MutableLiveData<Boolean>()
		val liveDataProgressVisible = MutableLiveData<Boolean>()
		private var page = 1
		private var searchUser = ""
		private var totalCount = 0

		fun initSearchUsers(users: String) {
				searchUser = users
				page = 1
				totalCount = 0

				userRepositoryImpl.getSearchUsers(users, PER_PAGE, page)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({
								page++
								totalCount = it.totalCount
								liveDataGithubUsers.value = it.items
						}, {
								liveDataProgressVisible.value = false
						}).addTo(compositeDisposable)
		}

		private fun loadMore(
				users: String,
				perPage: Int = PER_PAGE,
				page: Int,
				listener: (Int) -> Unit
		) {
				userRepositoryImpl.getSearchUsers(users, perPage, page)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({
								listener(1)
								totalCount = it.totalCount
								liveDataGithubUsersMore.value = it.items
						}, {
								liveDataProgressVisible.value = false
						}).addTo(compositeDisposable)
		}

		fun addFavorite(user: SearchUser) {
				val favoriteUser = FavoriteUser(
						user.id,
						user.login,
						user.avatarUrl
				)

				userRepositoryImpl.insertLocalUser(favoriteUser)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({
								liveDataComplete.value = true
						}, {
								liveDataComplete.value = false
						}).addTo(compositeDisposable)
		}

		fun loadMore() {
				if (totalCount > PER_PAGE * page) {
						loadMore(users = searchUser, page = page) {
								page += it
						}
				} else {
						liveDataProgressVisible.value = false
				}
		}

		companion object {

				const val PER_PAGE = 100
		}
}