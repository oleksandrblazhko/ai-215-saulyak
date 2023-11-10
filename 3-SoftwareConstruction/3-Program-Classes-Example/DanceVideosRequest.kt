package com.example.lab9tspp1.models

import java.util.Date

data class DanceVideosRequest(
    var id:Int,
    var danceRequestText:String,
    var dateRequest: Date,
    var user:User
)
