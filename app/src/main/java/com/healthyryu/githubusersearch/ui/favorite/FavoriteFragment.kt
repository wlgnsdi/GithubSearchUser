package com.healthyryu.githubusersearch.ui.favorite

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.healthyryu.githubusersearch.R
import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import com.healthyryu.githubusersearch.databinding.FragmentFavoriteBinding
import com.healthyryu.githubusersearch.ui.adapter.FavoriteUserAdapter
import com.healthyryu.githubusersearch.ui.commons.BaseFragment
import com.healthyryu.githubusersearch.ui.detail.DetailActivity
import com.healthyryu.githubusersearch.ui.detail.DetailActivity.Companion.FAVORITE_USER
import com.healthyryu.githubusersearch.ui.detail.DetailActivity.Companion.RESULT_USER_INDEX
import com.healthyryu.githubusersearch.ui.detail.DetailActivity.Companion.RESULT_USER_INFO
import com.healthyryu.githubusersearch.ui.user.UserFragment
import com.healthyryu.githubusersearch.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteViewModel, FragmentFavoriteBinding>(
		R.layout.fragment_favorite
) {

		override val viewModels: FavoriteViewModel by viewModels()
		private lateinit var favoriteUserAdapter: FavoriteUserAdapter
		private val resultCallback =
				registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
						when (result.resultCode) {
								RESULT_OK -> {
										val user = result.data?.getParcelableExtra(RESULT_USER_INFO) as? FavoriteUser?
										val index = result.data?.getIntExtra(RESULT_USER_INDEX, -1)
										if (user != null && index != null && index != -1) {
												favoriteUserAdapter.update(user, index)
										}
								}
						}
				}

		override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
				super.onViewCreated(view, savedInstanceState)

				favoriteUserAdapter = FavoriteUserAdapter(favoriteListener = {
						viewModels.deleteUser(it)
				}, bgListener = { favoriteUser, position ->
						goDetailActivity(favoriteUser, position)
				})

				with(binding) {
						recyclerview.adapter = favoriteUserAdapter

						with(searchView) {
								queryHint = getString(R.string.input_user_name_favorite)
								isIconifiedByDefault = false

								setOnQueryTextListener(UserFragment.QueryTextListener {
										binding.progressbar.isVisible = true
										viewModels.getUsers(it)
										view.hideKeyboard()
								})
						}
				}

				viewModels.liveDataUserList.observe(viewLifecycleOwner) {
						binding.lottie.run {
								pauseAnimation()
								isVisible = false
						}
						binding.progressbar.isVisible = false
						binding.tvEmpty.isVisible = it.isEmpty()
						favoriteUserAdapter.update(it)
				}
		}

		private fun goDetailActivity(user: FavoriteUser, position: Int) {
				context?.let {
						view?.hideKeyboard()
						val intent = Intent(it, DetailActivity::class.java).apply {
								putExtra(FAVORITE_USER, user)
								putExtra(RESULT_USER_INDEX, position)
						}
						resultCallback.launch(intent)
				}
		}

		companion object {
				const val TAG = "favorite_fragment"
		}
}