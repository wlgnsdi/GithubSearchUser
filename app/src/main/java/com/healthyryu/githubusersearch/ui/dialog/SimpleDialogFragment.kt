package com.healthyryu.githubusersearch.ui.dialog

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.healthyryu.githubusersearch.R
import com.healthyryu.githubusersearch.databinding.FragmentDialogSimpleBinding
import com.healthyryu.githubusersearch.ui.commons.BaseDialog
import com.inhandplus.medicationmanagement.dialog.simple.SimpleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleDialogFragment(
		val confirmListener: (Boolean) -> Unit
) : BaseDialog<SimpleViewModel, FragmentDialogSimpleBinding>(
		R.layout.fragment_dialog_simple
) {

		override val vm: SimpleViewModel by viewModels()
		override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
				super.onViewCreated(view, savedInstanceState)

				with(binding) {
						clBg.setOnClickListener { dismiss() }
						clContent.setOnClickListener { }

						setTvText(tvTitle, TITLE)
						setTvText(tvContent, CONTENT)

						with(btnCancel) {
								setOnClickListener {
										confirmListener(false)
										dismiss()
								}
								arguments?.getString(NEGATIVE)?.let {
										text = it
								}
						}

						with(btnSave) {
								setOnClickListener {
										confirmListener(true)
										dismiss()
								}
								arguments?.getString(POSITIVE)?.let {
										text = it
								}
						}
				}
		}

		private fun setTvText(tv: TextView, param: String) {
				with(tv) {
						val content = arguments?.getString(param)
						isVisible = content != null
						content?.let {
								text = it
						}
				}
		}

		companion object {

				fun newInstance(
						title: String? = null,
						content: String? = null,
						negative: String? = null,
						positive: String? = null,
						listener: (Boolean) -> Unit
				): BaseDialog<SimpleViewModel, FragmentDialogSimpleBinding> {
						return SimpleDialogFragment(listener).apply {
								arguments = bundleOf(
										TITLE to title,
										CONTENT to content,
										NEGATIVE to negative,
										POSITIVE to positive
								)
						}
				}

				const val TAG = "simple_fragment"
				private const val TITLE = "title"
				private const val CONTENT = "content"
				private const val NEGATIVE = "negative"
				private const val POSITIVE = "positive"
		}

}