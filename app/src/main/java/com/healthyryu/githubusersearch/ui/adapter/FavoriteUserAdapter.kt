package com.healthyryu.githubusersearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healthyryu.githubusersearch.R
import com.healthyryu.githubusersearch.data.entity.FavoriteUser
import com.healthyryu.githubusersearch.databinding.ItemGithubUserBinding

class FavoriteUserAdapter(
		private val favoriteUsers: MutableList<FavoriteUser> = mutableListOf(),
		private val favoriteListener: (FavoriteUser) -> Unit,
		private val bgListener: (FavoriteUser, Int) -> Unit
) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
				return ViewHolder.from(parent)
		}

		override fun onBindViewHolder(holder: ViewHolder, position: Int) {
				holder.bind(favoriteUsers[position], favoriteListener, bgListener)
		}

		override fun getItemCount(): Int {
				return favoriteUsers.size
		}

		fun update(list: List<FavoriteUser>) {
				favoriteUsers.clear()
				favoriteUsers.addAll(list)
				notifyDataSetChanged()
		}

		fun update(user: FavoriteUser, index: Int) {
				favoriteUsers[index] = user
				notifyItemChanged(index)
		}

		class ViewHolder(
				binding: ItemGithubUserBinding
		) : RecyclerView.ViewHolder(binding.root) {

				private val context = binding.root.context
				private val clBg = binding.clBg
				private val ivProfile = binding.ivProfile
				private val tvName = binding.tvName
				private val btnFavorite = binding.btnFavorite
				private val tvMemo = binding.tvMemo

				fun bind(
						item: FavoriteUser,
						favoriteListener: (FavoriteUser) -> Unit,
						bgListener: (FavoriteUser, Int) -> Unit
				) {
						Glide.with(context)
								.load(item.profileImgUrl)
								.override(400, 400)
								.into(ivProfile)
						tvName.text = item.login

						btnFavorite.run {
								isActivated = true
								setOnClickListener {
										isActivated = !isActivated
										favoriteListener(item)
								}
						}

						tvMemo.run {
								isVisible = item.memo.isNotEmpty()
								text = item.memo
						}

						clBg.setOnClickListener {
								bgListener(item, layoutPosition)
						}
				}

				companion object {

						fun from(
								parent: ViewGroup,
						): ViewHolder {
								val binding: ItemGithubUserBinding = DataBindingUtil.inflate(
										LayoutInflater.from(parent.context),
										R.layout.item_github_user,
										parent,
										false
								)

								return ViewHolder(binding)
						}
				}
		}
}
