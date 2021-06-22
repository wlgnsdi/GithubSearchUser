package com.healthyryu.githubusersearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healthyryu.githubusersearch.R
import com.healthyryu.githubusersearch.domain.model.SearchUser
import com.healthyryu.githubusersearch.databinding.ItemGithubUserBinding

class GithubUserAdapter(
		private val gitUsers: MutableList<SearchUser> = mutableListOf(),
		private val listener: (SearchUser) -> Unit
) : RecyclerView.Adapter<GithubUserAdapter.ViewHolder>() {

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
				return ViewHolder.from(parent)
		}

		override fun onBindViewHolder(holder: ViewHolder, position: Int) {
				holder.bind(gitUsers[position], listener)
		}

		override fun getItemCount(): Int {
				return gitUsers.size
		}

		fun init(list: List<SearchUser>) {
				gitUsers.clear()
				gitUsers.addAll(list)
				notifyDataSetChanged()
		}

		fun update(list: List<SearchUser>) {
				gitUsers.addAll(list)
				notifyDataSetChanged()
		}

		class ViewHolder(
				binding: ItemGithubUserBinding
		) : RecyclerView.ViewHolder(binding.root) {

				private val context = binding.root.context
				private val ivProfile = binding.ivProfile
				private val tvName = binding.tvName
				private val btnFavorite = binding.btnFavorite

				fun bind(item: SearchUser, listener: (SearchUser) -> Unit) {
						Glide.with(context)
								.load(item.avatarUrl)
								.override(400, 400)
								.into(ivProfile)
						tvName.text = item.login

						with(btnFavorite) {
								isActivated = item.isFavorite
								setOnClickListener {
										isActivated = !isActivated
										listener(item)
								}
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
