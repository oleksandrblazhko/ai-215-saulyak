// Приклад правильної взаємодії з користувачем та збереженням витрати користувача

val repository = ExpenseRecordRepository()
val dateSpent = "24.10.2023"
val moneySpent = 150.0
val purposeSpent = "2 kg of tomatoes"

when(sendExpenseRecord(purposeSpent,dateSpent,moneySpent)) {
    1 -> {
        repository.createOrUpdateRecord(purposeSpent,dateSpent,moneySpent)
        AlertDialog.Builder(this)
            .setMessage("Вашу витрату успішно записано. Для прегляду нового балансу натисніть ОК")
            .setPositiveButton("OK") { dialog, which ->
                finish()
            }
            .show()
    }
}

// Приклад неправильної взаємодії з користувачем та збереженням витрати користувача
val repository = ExpenseRecordRepository()
val dateSpent = "24.10.2023"
val moneySpent = -150.0
val purposeSpent = "2 kg of tomatoes"
when(sendExpenseRecord(purposeSpent,dateSpent,moneySpent)) {
    -1 -> {
        val message = "Поле грошей не відповідає умовам (або не є числом або є від'ємним числом)"
        displayErrorMessage(message)
    }
}

// Приклад неправильної взаємодії з користувачем та збереженням витрати користувача
val repository = ExpenseRecordRepository()
val dateSpent = "24.12.2023"
val moneySpent = 150.0
val purposeSpent = "2 kg of tomatoes"

when(sendExpenseRecord(purposeSpent,dateSpent,moneySpent)) {
    -2 -> {
        val message = "Дата не відповідає умовам (Більша за поточну)"
        displayErrorMessage(message)
    }
}

// Приклад неправильної взаємодії з користувачем та збереженням витрати користувача
val repository = ExpenseRecordRepository()
val dateSpent = "24.10.2023"
val moneySpent = 150.0
val purposeSpent = "2 kg of tomatoessssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"

when(sendExpenseRecord(purposeSpent,dateSpent,moneySpent)) {
    -3 -> {
        val message = "Ціль витрати не відповідає умовам (довжина більше 50)"
        displayErrorMessage(message)
    }
}
