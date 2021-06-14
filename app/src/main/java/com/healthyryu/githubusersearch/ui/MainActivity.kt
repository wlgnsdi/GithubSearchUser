package com.healthyryu.githubusersearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.healthyryu.githubusersearch.R
import com.healthyryu.githubusersearch.databinding.ActivityMainBinding
import com.healthyryu.githubusersearch.ui.commons.BaseActivity
import com.healthyryu.githubusersearch.ui.favorite.FavoriteFragment
import com.healthyryu.githubusersearch.ui.user.UserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

		private val binding: ActivityMainBinding by binding(R.layout.activity_main)
		private var currentFragment: Fragment? = null

		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)

				changeFragment(UserFragment(), UserFragment.TAG)
				setBottomNav()
		}

		private fun setBottomNav() {
				binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
						when (item.itemId) {
								R.id.bottom_nav_github -> {
										changeFragment(UserFragment(), UserFragment.TAG)
								}
								R.id.bottom_nav_favorite -> {
										changeFragment(FavoriteFragment(), FavoriteFragment.TAG)
								}
								else -> {
								}
						}
						return@setOnNavigationItemSelectedListener true
				}
		}

		private fun changeFragment(fragment: Fragment, tag: String) {
				println(">> SupportFragment : ${supportFragmentManager.findFragmentById(R.id.main_container)?.id}")
				println(">> CurrentFragment : ${currentFragment?.id}")
				println(">> fragment : ${fragment.id}")
//				if (currentFragment != null && fragment.id == currentFragment?.id) return

				currentFragment = fragment
				supportFragmentManager.beginTransaction()
						.replace(R.id.main_container, fragment)
						.commitAllowingStateLoss()
		}

}