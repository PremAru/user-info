package com.optus.employee.userinfo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.optus.employee.R
import com.optus.employee.UserInfoApplication
import com.optus.employee.databinding.ActivityUserBinding
import com.optus.employee.model.UserDetail
import com.optus.employee.model.UserInfo
import com.optus.employee.userdetail.UserDetailActivity
import com.optus.employee.utils.Constants
import com.optus.employee.utils.Status
import kotlinx.android.synthetic.main.activity_user.*
import javax.inject.Inject


class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var viewModel: UserInfoViewModel

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var userInfoAdapter: UserInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as UserInfoApplication).appComponent.userInfoComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(UserInfoViewModel::class.java)

        val userInfo = intent.getSerializableExtra(Constants.SELECTED_USERS) as UserInfo
        albumIdTextView.text = resources.getString(R.string.albumId) + " " + userInfo.id
        setRecyclerView()

        viewModel.getUser().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.hide()
                    it.data?.let { phothoList ->
                        userInfoAdapter.setUserInfoPhotos(phothoList)
                    }
                }
                Status.LOADING -> {
                    progressBar.show()
                }
                Status.ERROR -> {
                    progressBar.hide()
                    // Alert view implementaion
                }
            }
        })

        getUserDetail(userInfo.id);
    }


    private fun getUserDetail(userId: Int) {
        viewModel.fetchUserDetail(userId);
    }

    private fun setRecyclerView() {
        albumRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userInfoAdapter
            userInfoAdapter.setClickListener(object: UserInfoClickListener {
                override fun userInfoClicked(userDetail: UserDetail) {
                    val intent = Intent(this@UserInfoActivity,
                        UserDetailActivity::class.java)
                    intent.putExtra(Constants.SELECTED_USER_PHOTO, userDetail)
                    startActivity(intent)
                }
            });
        }


    }
}