package com.optus.employee.userinfo

import com.optus.employee.model.UserDetail

interface UserInfoClickListener {
    fun userInfoClicked(userPhoto: UserDetail)
}