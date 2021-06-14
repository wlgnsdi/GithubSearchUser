package com.healthyryu.githubusersearch.ui.commons

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {
		open val compositeDisposable = CompositeDisposable()

		override fun onCleared() {
				compositeDisposable.clear()
				super.onCleared()
		}
}