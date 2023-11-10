package com.example.lab9tspp1.models

open class User(
    open var id: Int,
    open var login: String = "",
    open var password: String = "",
    open var userInfo: AdditionalUserInfo
)
