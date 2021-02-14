package com.optus.employee.userinfolist

import com.optus.employee.model.UserInfo

interface UserInfoClickListener {
    fun userInfoClicked(userInfo: UserInfo)
}