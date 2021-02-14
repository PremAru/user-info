package com.optus.employee.service

import com.optus.employee.model.UserInfo
import com.optus.employee.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface UserInfoService{
    @GET(Constants.URL_USER_LIST)
    fun getUserInfosList() : Observable<List<UserInfo>>

}