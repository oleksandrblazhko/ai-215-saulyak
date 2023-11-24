package com.nuop.tscp
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.Date

object TestCase {
  
  fun checkData(dateSpent: String, moneySpent: String, purposeSpent: String): Int {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val date = dateFormat.parse(dateSpent)
        val currentDate = Date()
        try {
            if (moneySpent.toDouble() < 0) {
                return -1
            }
        } catch (_: NumberFormatException) {
            return -1
        }
        if (date > currentDate) {
            return -2 
        }
        if (purposeSpent.length > 50 || purposeSpent.isEmpty()) {
            return -3 
        }
        return 1
    }
  
  @JvmStatic
  fun main(args: Array<String>) {
      val result1 = checkData("23.10.2023", "150.0", "Томати 2 кг")
      println("TC1: 23.10.2023,150.0,Томати 2 кг ${
          if (result1 == 1) "Passed = 1" else "Failed"
      }")

      val result2 = checkData("23.10.2023", "-150.0", "Томати 2 кг")
      println("TC2: 23.10.2023,-150.0,Томати 2 кг ${
          if (result2 == -1) "Passed = -1" else "Failed"
      }")
              
      val result3 = checkData("23.10.2023", "арарара", "Томати 2 кг")
      println("TC3: 23.10.2023,арарара,Томати 2 кг ${
          if (result3 == -1) "Passed = -1" else "Failed"
      }")

      val result4 = checkData("23.12.2024", "150.0", "Томати 2 кг")
      println("TC4: 23.12.2024,150.0,Томати 2 кг ${
          if (result4 == -2) "Passed = -2" else "Failed"
      }")

      val result5 = checkData("23.10.2023", "150", "рррррррррррррррррррррррррррррррррррррррррррррррррррррррррр")
      println("TC5: 23.10.2023, 150,  ${
          if (result5 == -3) "Passed = -3" else "Failed"
      }")
    
      if (1 != result1 || -1 != result2 || -1 != result3 || -2 != result4 || -3 != result5) {
        System.exit(-1)
      }
   }
}
