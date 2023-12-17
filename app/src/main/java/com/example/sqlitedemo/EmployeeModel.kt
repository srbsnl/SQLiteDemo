package com.example.sqlitedemo

import java.util.Random

data class EmployeeModel(
    var id: Int = getAutoId(),
    var name: String = "",
    var email: String = ""
) {

    companion object{

        fun getAutoId(): Int {
            val random  = Random()
            return random.nextInt(100)
        }
    }
}