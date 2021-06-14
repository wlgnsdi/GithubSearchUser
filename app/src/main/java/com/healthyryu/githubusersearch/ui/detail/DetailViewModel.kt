package com.healthyryu.githubusersearch.ui.detail

import androidx.lifecycle.MutableLiveData
import com.healthyryu.githubusersearch.data.entity.DetailUser
import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import com.healthyryu.githubusersearch.data.reposiroty.UserRepositoryImpl
import com.healthyryu.githubusersearch.ui.commons.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
		private val repositoryImpl: UserRepositoryImpl
) : BaseViewModel() {

		var layoutPosition: Int = -1
		private lateinit var user: FavoriteUser
		var liveDataDetailUser = MutableLiveData<DetailUser>()

		fun loadUserDetail(user: FavoriteUser) {
				this.user = user
				repositoryImpl.getUser(user.login)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({
								liveDataDetailUser.value = it
						}, {
						}).addTo(compositeDisposable)
		}

		fun updateMemo(memo: String, listener: (Unit) -> Unit) {
				user.memo = memo
				repositoryImpl.updateLocalUser(user)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({
								listener.invoke(Unit)
						}, {
						}).addTo(compositeDisposable)

		}

		fun getUserInfo() = user
}