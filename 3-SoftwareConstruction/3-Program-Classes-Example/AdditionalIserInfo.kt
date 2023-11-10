package com.example.lab9tspp1.models

import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Email

data class AdditionalUserInfo(
    var profilePhoto: Uri,
    var email: Email,
    var nickName: String = ""
)
