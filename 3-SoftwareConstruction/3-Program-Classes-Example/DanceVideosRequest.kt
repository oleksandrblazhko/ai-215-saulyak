package com.example.lab9tspp1.models

import java.util.Date

data class MusicVideosRequest(
    var id:Int,
    var musicRequestText:String,
    var dateRequest: Date,
    var user:User
)
