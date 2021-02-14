package com.optus.employee.userinfolist

import com.optus.employee.model.UserInfo

interface UserInfoListClickListener {
    fun userInfoListClicked(userInfo: UserInfo)
}