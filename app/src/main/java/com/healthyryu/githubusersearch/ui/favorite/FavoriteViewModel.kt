package com.healthyryu.githubusersearch.ui.favorite

import androidx.lifecycle.MutableLiveData
import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import com.healthyryu.githubusersearch.data.reposiroty.UserRepositoryImpl
import com.healthyryu.githubusersearch.ui.commons.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
		private val userRepository: UserRepositoryImpl
) : BaseViewModel() {

		var liveDataUserList = MutableLiveData<List<FavoriteUser>>()
		private var userList = mutableListOf<FavoriteUser>()

		fun getUsers(login: String) {
				userRepository.getLocalUser(login)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({
								userList = it as MutableList<FavoriteUser>
								userList.sortBy { it.login }
								liveDataUserList.value = it
						}, {
						}).addTo(compositeDisposable)
		}

		fun deleteUser(item: FavoriteUser) {
				userRepository.deleteLocalUser(item)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({
								userList.remove(item)
								liveDataUserList.value = userList
						}, {
						}).addTo(compositeDisposable)
		}

		fun loadDetail() {

		}
}