package com.healthyryu.githubusersearch.ui.user

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthyryu.githubusersearch.R
import com.healthyryu.githubusersearch.databinding.FragmentUserBinding
import com.healthyryu.githubusersearch.ui.adapter.GithubUserAdapter
import com.healthyryu.githubusersearch.ui.commons.BaseFragment
import com.healthyryu.githubusersearch.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<UserViewModel, FragmentUserBinding>(
		R.layout.fragment_user
) {

		override val viewModels: UserViewModel by viewModels()
		private lateinit var githubUserAdapter: GithubUserAdapter
		private var toast: Toast? = null

		override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
				super.onViewCreated(view, savedInstanceState)

				githubUserAdapter = GithubUserAdapter {
						viewModels.addFavorite(it)
				}

				with(binding.recyclerview) {
						adapter = githubUserAdapter
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
								setOnScrollChangeListener { _, _, _, _, _ ->
										setScrollListener()
								}
						} else {
								setOnScrollListener(object : RecyclerView.OnScrollListener() {
										override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
												setScrollListener()
										}
								})
						}
				}

				with(binding.searchView) {
						queryHint = getString(R.string.input_user_name)
						isIconifiedByDefault = false

						setOnQueryTextListener(QueryTextListener {
								binding.progressbar.isVisible = true
								viewModels.initSearchUsers(it)
								view.hideKeyboard()
						})
				}

				viewModels.liveDataGithubUsers.observe(viewLifecycleOwner) {
						binding.lottie.run {
								pauseAnimation()
								isVisible = false
						}
						binding.progressbar.isVisible = false
						binding.tvEmpty.isVisible = it.isEmpty()
						githubUserAdapter.init(it)
				}

				viewModels.liveDataGithubUsersMore.observe(viewLifecycleOwner) {
						binding.progressbar.isVisible = false
						githubUserAdapter.update(it)
				}

				viewModels.liveDataComplete.observe(viewLifecycleOwner) { isSuccess ->
						showToast(if (isSuccess) getString(R.string.success) else getString(R.string.fail))
				}
				viewModels.liveDataProgressVisible.observe(viewLifecycleOwner) {
						binding.progressbar.isVisible = it
				}
		}

		private fun setScrollListener() {
				with(binding.recyclerview) {
						(layoutManager as? LinearLayoutManager?)?.let {
								if (it.findLastCompletelyVisibleItemPosition() + 5 > githubUserAdapter.itemCount) {
										binding.progressbar.isVisible = true
										viewModels.loadMore()
								}
						}
				}
		}

		@SuppressLint("ShowToast")
		private fun showToast(message: String) {
				context?.let {
						if (toast?.view?.isShown == true) {
								toast?.cancel()
								toast = null
						}

						toast = Toast.makeText(it, message, Toast.LENGTH_SHORT)
						toast?.show()
				}
		}

		class QueryTextListener(private val action: (String) -> Unit) : SearchView.OnQueryTextListener {

				override fun onQueryTextSubmit(query: String?): Boolean {
						if (query != null && query.isNotBlank()) action(query)
						return false
				}

				override fun onQueryTextChange(newText: String?): Boolean = false

		}

		companion object {

				const val TAG = "user_fragment"
		}

}