package com.healthyryu.githubusersearch.ui.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import com.healthyryu.githubusersearch.BR

abstract class BaseDialog<VM : ViewModel, VB : ViewDataBinding>(
		private val resId: Int
) : DialogFragment() {

		protected lateinit var binding: VB
		protected abstract val vm: VM

		private fun getLayoutResId(): Int = resId

		override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
				super.onViewCreated(view, savedInstanceState)
				binding.apply {
						lifecycleOwner = activity
						setVariable(BR.vm, vm)
				}
		}

		override fun onCreateView(
				inflater: LayoutInflater,
				container: ViewGroup?,
				savedInstanceState: Bundle?
		): View {
				binding = DataBindingUtil.inflate(
						layoutInflater,
						getLayoutResId(),
						container,
						false
				)
				return binding.root
		}
}