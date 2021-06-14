package com.healthyryu.githubusersearch.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
		try {
				val inputMethodManager =
						context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
				inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
		} catch (ignored: RuntimeException) {
		}
}

fun View.showKeyboard() {
		val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		this.requestFocus()
		imm.showSoftInput(this, 0)
}