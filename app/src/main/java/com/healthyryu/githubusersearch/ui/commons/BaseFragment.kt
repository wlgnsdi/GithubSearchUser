package com.healthyryu.githubusersearch.ui.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.healthyryu.githubusersearch.BR

abstract class BaseFragment<VM : ViewModel, VB : ViewDataBinding>(
		@LayoutRes val resId: Int
) : Fragment() {

		protected lateinit var binding: VB
		protected abstract val viewModels: VM

		override fun onCreateView(
				inflater: LayoutInflater,
				container: ViewGroup?,
				savedInstanceState: Bundle?
		): View? {
				binding = DataBindingUtil.inflate(inflater, resId, container, false)
				return binding.root
		}

		override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
				super.onViewCreated(view, savedInstanceState)
				binding.apply {
						lifecycleOwner = activity
						setVariable(BR.vm, viewModels)
				}
		}

}