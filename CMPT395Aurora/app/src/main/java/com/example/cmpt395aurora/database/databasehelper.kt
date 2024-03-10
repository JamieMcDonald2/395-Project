/**
 *  database helper v1.1
 *  some ref's used in the creation of our database:
 *  - https://developer.android.com/training/data-storage/sqlite
 *  - https://abhiandroid.com/database/sqlite
 *  - https://www.freecodecamp.org/news/how-to-use-sqlite-database-with-android-studio/
 *  - https://www.androidauthority.com/sqlite-primer-for-android-811987/
 *
 *  v1.1:
 *      - added clear for seed testing
 */

package com.example.cmpt395aurora.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cmpt395aurora.database.employees.Employee

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    /**
     * EMPLOYEE DATABASE
     */
    companion object {
        private const val DATABASE_NAME = "EmployeeDatabase"
        private const val DATABASE_VERSION = 1
    }
    // new table
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_EMPLOYEE_TABLE = ("CREATE TABLE employees ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "position TEXT )")
        db.execSQL(CREATE_EMPLOYEE_TABLE)
    }
    // upgrade table
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS employees")
        onCreate(db)
    }
    // add employee
    fun addEmployee(name: String, position: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("position", position)
        val result = db.insert("employees", null, contentValues)
        db.close()
        return result != -1L
    }
    // get all employees for list (maybe obsolete)
    fun getAllEmployees(): List<Employee> {
        val employees = ArrayList<Employee>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM employees", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val position = cursor.getString(2)
                employees.add(Employee(id, name, position))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return employees
    }
    // delete employee
    fun deleteEmployee(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete("employees", "id = ?", arrayOf(id.toString())).toLong()
        db.close()
        return result != -1L
    }
    // clear database for seed testing
    fun clearDatabase() {
        val db = this.writableDatabase
        db.delete("employees", null, null)
        db.close()
    }
}
