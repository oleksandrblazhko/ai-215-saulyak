package com.example.lab9tspp1.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpenseRecord(
    var expenseCode:String="",
    var expensePurpose:String = "",
    var expenseDate:String="",
    var expenseSpentMoney:Double,
    var userName:String = "") : Parcelable
