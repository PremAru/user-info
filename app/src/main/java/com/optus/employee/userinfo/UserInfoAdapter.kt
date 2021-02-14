package com.optus.employee.userinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optus.employee.R
import com.optus.employee.model.UserDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_user.view.*
import javax.inject.Inject

class UserInfoAdapter @Inject constructor () : RecyclerView.Adapter<UserInfoAdapter.RepositoryViewHolder>() {
    private var userInfoList: MutableList<UserDetail> = mutableListOf()
    var userInfoClickListener: UserInfoClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoAdapter.RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_user, parent,
            false)
        return RepositoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userInfoList.size
    }

    override fun onBindViewHolder(holder: UserInfoAdapter.RepositoryViewHolder, position: Int) {
        userInfoList[position]?.let { holder.bindFilter(it) }
    }

    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindFilter(userDetail: UserDetail) {
            itemView.albumTextView.text = userDetail.title
            Picasso.get().load(userDetail.thumbnailUrl)
                .into(itemView.albumImageView)
            itemView.albumConstraintLayout.setOnClickListener {
                val userPhoto = userInfoList[adapterPosition]
                userInfoClickListener?.userInfoClicked(userPhoto)
            }
        }
    }

    fun setUserInfoPhotos(userInfosListData: List<UserDetail>) {
        val beforeSize = userInfoList.size
        userInfoList.addAll(userInfosListData)
        notifyItemRangeInserted(beforeSize, userInfoList.size)
    }

    fun setClickListener(listener: UserInfoClickListener){
        userInfoClickListener = listener;
    }

}