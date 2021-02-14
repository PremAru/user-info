package com.optus.employee.userdetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.optus.employee.R
import com.optus.employee.model.UserDetail
import com.optus.employee.utils.Constants.SELECTED_USER_PHOTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.adapter_user.albumTextView

class UserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        val userPhoto = intent.getSerializableExtra(SELECTED_USER_PHOTO) as UserDetail

        if (userPhoto != null) {
            Picasso.get().load(userPhoto.url)
                .placeholder(R.drawable.loading_image)
                .into(phothoImageView)
            photoTitleTextView.text = userPhoto.title
            albumTextView.text = resources.getString(R.string.albumId) + " "+ userPhoto.id
            photoTextView.text = resources.getString(R.string.photo_id) + " " + userPhoto.albumId.toString()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            finish()
            return true;
        }
        return super.onOptionsItemSelected(item)
    }
}
