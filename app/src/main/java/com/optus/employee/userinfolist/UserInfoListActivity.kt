package com.optus.employee.userinfolist

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.optus.employee.R
import com.optus.employee.UserInfoApplication
import com.optus.employee.databinding.ActivityMainBinding
import com.optus.employee.model.UserInfo
import com.optus.employee.userinfo.UserInfoActivity
import com.optus.employee.utils.Constants
import com.optus.employee.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class UserInfoListActivity : AppCompatActivity() {

    @Inject
    internal lateinit var userInfoAdapter: UserInfoAdapter

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserInfoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as UserInfoApplication).appComponent.userInfoListComponent().create()
            .inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(UserInfoListViewModel::class.java)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        setRecyclerView()
        viewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.hide()
                    it.data?.let { userInfoList -> userInfoAdapter.setUserInfoList(userInfoList) }
                }
                Status.LOADING -> {
                    progressBar.show()
                }
                Status.ERROR -> {
                    progressBar.hide()
                    displayError(it.message.toString())
                    // Alert view implementaion
                }
            }
        })

        getUserInfoList();
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            finish()
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserInfoList() {
        progressBar.show()
        viewModel.fetchUserInfoList();

    }

    private fun setRecyclerView() {
        reposListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userInfoAdapter
            userInfoAdapter.setClickListener(object : UserInfoListClickListener {
                override fun userInfoListClicked(userInfo: UserInfo) {
                    val intent = Intent(
                        this@UserInfoListActivity,
                        UserInfoActivity::class.java
                    )
                    intent.putExtra(Constants.SELECTED_USERS, userInfo)
                    startActivity(intent)
                }
            });
        }

    }

    private fun displayError(errorMessage: String) {
        var alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle(getString(R.string.error_title))
        alertBuilder.setMessage(errorMessage)
        alertBuilder.setPositiveButton(getString(R.string.ok_button), null)
        alertBuilder.show()
    }

}