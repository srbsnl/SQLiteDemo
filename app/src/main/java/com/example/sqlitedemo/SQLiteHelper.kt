package com.example.sqlitedemo


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception


class SQLiteHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private const val DATABASE_NAME = "employee.db"
        private const val DATABASE_VERSION = 1
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val TBL_EMPLOYEE = "tbl_employee"

    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTblEmployee = ("CREATE TABLE " + TBL_EMPLOYEE + " ("
                + ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT,"
                + EMAIL  + " TEXT"+ ")")

        db?.execSQL(createTblEmployee)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TBL_EMPLOYEE")
        onCreate(db)
    }

    fun insertEmployee(std: EmployeeModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,std.id)
        contentValues.put(NAME,std.name)
        contentValues.put(EMAIL,std.email)

        val success = db.insert(TBL_EMPLOYEE,null,contentValues)
        db.close()
        return success
    }


    fun getAllEmployees(): ArrayList<EmployeeModel>{
        val db = this.readableDatabase
        val emplist: ArrayList<EmployeeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_EMPLOYEE"

        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery,null)
        } catch(e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var email: String


        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val emp = EmployeeModel(id=id,name=name,email=email)
                emplist.add(emp)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return emplist

    }


    fun updateEmployee(emp: EmployeeModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,emp.id)
        contentValues.put(NAME,emp.name)
        contentValues.put(EMAIL,emp.email)

        val success = db.update(TBL_EMPLOYEE,contentValues, "id=", null)
        db.close()
        return success
    }
}