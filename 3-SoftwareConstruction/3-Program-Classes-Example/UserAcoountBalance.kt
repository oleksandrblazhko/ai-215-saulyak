package com.example.lab9tspp1.models

data class UserAccountBalance(
    var id:Int,
    var userBalanceText: String = "",
    var record: ExpenseRecord
)
