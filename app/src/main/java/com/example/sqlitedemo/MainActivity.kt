package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: EmployeeAdapter? = null
    private var emp:EmployeeModel?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecylerView()
        sqLiteHelper = SQLiteHelper(this)
        btnAdd.setOnClickListener { addEmployee() }
        btnView.setOnClickListener { getEmployee() }
        btnView.setOnClickListener { updateEmployee() }
        adapter?.setOnClickItem {
            Toast.makeText(this,it.name,Toast.LENGTH_SHORT).show() }



    }

    private fun updateEmployee() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if(name == emp?.name && email == emp?.email) {
            Toast.makeText(this, "Record not changed..", Toast.LENGTH_SHORT).show()
            return
        }

        if(emp == null) return

        val emp = EmployeeModel(id = emp!!.id , name=name ,email=email)
        val status = sqLiteHelper.updateEmployee(emp)

        if (status > -1){
            clearEditText()
            getEmployee()
        } else {
            Toast.makeText(this, "Update failed..", Toast.LENGTH_SHORT).show()
        }
    }



    private fun getEmployee() {
        val empList = sqLiteHelper.getAllEmployees()
        Log.e("pppp", "${empList.size}")
        adapter?.addItems(empList)
    }

    private fun addEmployee() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Enter required field", Toast.LENGTH_SHORT).show()
        } else {
            val emp = EmployeeModel(name = name, email = email)
            val status = sqLiteHelper.insertEmployee(emp)

            if (status > -1) {
                Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            } else {
                Toast.makeText(this, "Data Not added", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initRecylerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EmployeeAdapter()
        recyclerView.adapter = adapter

    }

    private fun clearEditText(){
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

    }
}