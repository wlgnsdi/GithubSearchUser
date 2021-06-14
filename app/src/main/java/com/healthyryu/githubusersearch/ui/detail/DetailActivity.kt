package com.healthyryu.githubusersearch.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import android.view.MenuItem.SHOW_AS_ACTION_NEVER
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.healthyryu.githubusersearch.R
import com.healthyryu.githubusersearch.data.entity.DetailUser
import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import com.healthyryu.githubusersearch.databinding.ActivityDetailBinding
import com.healthyryu.githubusersearch.ui.commons.BaseActivity
import com.healthyryu.githubusersearch.ui.dialog.SimpleDialogFragment
import com.healthyryu.githubusersearch.utils.hideKeyboard
import com.healthyryu.githubusersearch.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity() {

		private val binding: ActivityDetailBinding by binding(R.layout.activity_detail)
		private val viewModel: DetailViewModel by viewModels()

		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)
				val favoriteUser = intent.extras?.get(FAVORITE_USER) as? FavoriteUser?

				intent.extras?.getInt(RESULT_USER_INDEX, -1)?.let {
						viewModel.layoutPosition = it
				}

				favoriteUser?.let {
						with(binding) {
								progressbar.isVisible = true
								toolbar.title = getString(R.string.detail_info)
						}
						viewModel.loadUserDetail(it)
				} ?: kotlin.run {
						finish()
						return
				}

				setupAppBar()

				viewModel.liveDataDetailUser.observe(this) {
						setUserInfo(favoriteUser, it)
				}
		}

		private fun setupAppBar() {
				setSupportActionBar(binding.toolbar)
				supportActionBar?.setDisplayHomeAsUpEnabled(true)
		}

		override fun onBackPressed() {
				val intent = Intent()
				intent.putExtra(RESULT_USER_INFO, viewModel.getUserInfo())
				intent.putExtra(RESULT_USER_INDEX, viewModel.layoutPosition)
				setResult(RESULT_OK, intent)
				finish()
		}

		override fun onCreateOptionsMenu(menu: Menu?): Boolean {
				val menuInflater = menuInflater
				menuInflater.inflate(R.menu.detail_menu, menu)
				return true
		}

		override fun onOptionsItemSelected(item: MenuItem): Boolean {
				when (item.itemId) {
						android.R.id.home -> {
								onBackPressed()
						}
						R.id.detail_edit -> {
								with(binding) {
										toolbar.menu.run {
												findItem(R.id.detail_save).setShowAsAction(SHOW_AS_ACTION_ALWAYS)
												findItem(R.id.detail_save).isVisible = true
												findItem(R.id.detail_edit).setShowAsAction(SHOW_AS_ACTION_NEVER)
												findItem(R.id.detail_edit).isVisible = false
										}

										tvMemo.isVisible = false
										etMemo.run {
												isVisible = true
												requestFocus()
												showKeyboard()
												val memo = binding.tvMemo.text.toString()
												if (memo.isNotEmpty()) {
														setText(memo)
														setSelection(memo.length)
												}
										}
								}
						}
						R.id.detail_save -> {
								showSimpleDialog(
										SimpleDialogFragment.newInstance(
												title = getString(R.string.alert),
												content = getString(R.string.do_you_want_to_save)
										) { isOk ->
												if (isOk) {
														with(binding) {
																toolbar.menu.run {
																		findItem(R.id.detail_edit).setShowAsAction(SHOW_AS_ACTION_ALWAYS)
																		findItem(R.id.detail_edit).isVisible = true
																		findItem(R.id.detail_save).setShowAsAction(SHOW_AS_ACTION_NEVER)
																		findItem(R.id.detail_save).isVisible = false
																}

																viewModel.updateMemo(binding.etMemo.text.toString()) {
																		etMemo.isVisible = false
																		tvMemo.run {
																				isVisible = true
																				text = binding.etMemo.text
																		}
																}
														}
												}
										}, SimpleDialogFragment.TAG
								)
						}
				}
				return true
		}

		private fun setUserInfo(favoriteUser: FavoriteUser, it: DetailUser) {
				with(binding) {
						progressbar.isVisible = false

						Glide.with(root.context)
								.load(it.avatarUrl)
								.placeholder(R.drawable.ic_account_box)
								.override(400, 400)
								.into(ivProfile)

						groupUserName.isVisible = it.name.visibleCheck
						tvUserName.text = it.name

						groupEmail.isVisible = it.email.visibleCheck
						tvEmail.text = it.email

						groupCompany.isVisible = it.company.visibleCheck
						tvCompany.text = it.company

						groupBlog.isVisible = it.blog.visibleCheck
						tvBlog.text = it.blog

						groupLocation.isVisible = it.location.visibleCheck
						tvLocation.text = it.location

						groupMemo.isVisible = favoriteUser.memo.visibleCheck
						tvMemo.text = favoriteUser.memo
				}
		}

		private val String?.visibleCheck
			get() = this != null && this.isNotEmpty()

		private fun showSimpleDialog(fragment: Fragment, tag: String) {
				binding.root.hideKeyboard()

				if (supportFragmentManager.findFragmentByTag(tag)?.isVisible != true) {
						supportFragmentManager.beginTransaction().apply {
								setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
								add(android.R.id.content, fragment, tag).addToBackStack(null).commit()
						}
				}
		}

		companion object {

				const val FAVORITE_USER = "favorite_user"
				const val RESULT_USER_INFO = "user_info"
				const val RESULT_USER_INDEX = "user_index"
		}

}