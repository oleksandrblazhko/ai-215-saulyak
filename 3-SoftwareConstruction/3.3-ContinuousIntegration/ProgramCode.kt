// Приклад взаємодіїкористувача з базою даних

// Класс-Entity,який представляє об'єкт запису витрати для взаємодією з базою даних
@Parcelize
data class ExpenseRecord(
    var expenseCode: String = "", // Унікальний код витрати
    var expensePurpose: String = "", // Мета витрати
    var expenseDate: String = "", // Дата витрати
    var expenseSpentMoney: Double, // Сума витрати
    var userName: String = "" // Ім'я користувача
) : Parcelable

// Репозиторій, який взаємодіє з базою даних Firebase для збереження, оновлення та видалення записів користувача про витрату
class ExpenseRecordRepository {
    private val database = Firebase.database // об'єкт бази даних
    private val expenseDBRef = database.getReference("expense_records") // беремо дані саме з цієї таблиці expense_records

    // Метод для створення або оновлення запису про витрату в базі даних
    fun createOrUpdateRecord(expRec: com.example.lab9tspp1.models.ExpenseRecord) {
        val expenseNodeRef = expenseDBRef.child(expRec.expenseCode.toString())
        expenseNodeRef.setValue(expRec)
    }

    // Метод для видалення запису про витрату з бази даних
    fun deleteRecord(expRec: com.example.lab9tspp1.models.ExpenseRecord) {
        val expenseNodeRef = expenseDBRef.child(expRec.expenseCode.toString())
        expenseNodeRef.removeValue()
    }
}

// Клас для описування логіки взаємодії користувача з додатком, включаючи реєстрацію користувачів, перевірку їх даних і збереження їх у базі даних Firebase.

class PiggyBank : AppCompatActivity() {
    private lateinit var binding: ActivityPiggybankBinding
    private var balance = 8200.00 // Початковий баланс
    private var User = "Maksym" // Ім'я користувача
    private var repository = ExpenseRecordRepository() // об'єкт репозиторію

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPiggybankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userBalance.text = "Залишок: $balance грн"

        // Обробник натискання на кнопку для введення витрати
        binding.expenceButton.setOnClickListener {
            // чи поля порожні
            if (binding.moneySpent.text.toString().isNotBlank() ||
                binding.purposeSpent.text.toString().isNotBlank() ||
                binding.dateSpent.text.toString().isNotBlank()
            ) {
                // отримання введених даних користувачем про витрату
                val dateSpent = binding.dateSpent.text.toString()
                val moneySpent = binding.moneySpent.text.toString()
                val purposeSpent = binding.purposeSpent.text.toString()
                // метод який обробляє і зберігає витрату
                sendExpenseRecord(dateSpent, moneySpent, purposeSpent)
            }
        }
    }

    // Метод для збереження запису про витрату
    private fun sendExpenseRecord(dateSpent: String, moneySpent: String, purposeSpent: String) {
        // Перевіряємо введені дані та виконуємо відповідні дії
        when (checkData(dateSpent, moneySpent, purposeSpent)) {
            // всі дані введені коректно
            1 -> {
                val expenseCode = Random.nextInt(1000000000).toString()
                val userName = User
                // створюємо об'єкт витрати
                val exp = ExpenseRecord(
                    expenseCode,
                    purposeSpent,
                    dateSpent,
                    moneySpent.toDouble(),
                    userName
                )
                // збережиємо чи оновлюємо об'єкт в базі даних через репозиторій
                repository.createOrUpdateRecord(exp)

                // Відображаємо діалогове вікно з підтвердженням запису витрати
                AlertDialog.Builder(this)
                    .setMessage("Вашу витрату успішно записано. Для перегляду нового балансу натисніть ОК")
                    .setPositiveButton("OK") { dialog, which ->
                        finish()
                    }
                    .show()
            }
            // Гроші введені невірно
            -1 -> {
                val message = "Поле грошей не відповідає умовам (або не є числом або є від'ємним числом)"
                displayErrorMessage(message)
            }
            // Дата введена невірно
            -2 -> {
                val message = "Дата не відповідає умовам (Більша за поточну)"
                displayErrorMessage(message)
            }
            // Ціль витрати введена невірно
            -3 -> {
                val message = "Ціль витрати не відповідає умовам (довжина більше 50)"
                displayErrorMessage(message)
            }
        }
    }

    // Метод для перевірки введених даних
    @SuppressLint("SimpleDateFormat")
    private fun checkData(dateSpent: String, moneySpent: String, purposeSpent: String): Int {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val date = dateFormat.parse(dateSpent)
        val currentDate = Date()
        // перевірка веденої користувачем дати
        try {
            // перевірка на від'ємність
            if (moneySpent.toDouble() < 0) {
                return -1
            }
            // перевірка на введення не числа
        } catch (_: NumberFormatException) {
            return -1
        }
        // дата більша за поточну
        if (date!! >= currentDate) {
            return -2 // Дата не відповідає умові 2.
        }
        // ціль запиту завдовжки більше ніж 50 символів
        if (purposeSpent.length > 50) {
            return -3 // Ціль витрати не відповідає умові 4.
        }
        return 1
    }

    // Метод для відображення повідомлення про помилку
    private fun displayErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
