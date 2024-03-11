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
import android.util.Log

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    /**
     * EMPLOYEE DATABASE
     */
    companion object {
        private const val DATABASE_NAME = "EmployeeDatabase"
        private const val DATABASE_VERSION = 2
    }
    // new table
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_EMPLOYEE_TABLE = ("CREATE TABLE employees ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "fname TEXT, "
                + "lname TEXT, "
                + "nname TEXT, "
                + "email TEXT, "
                + "isActive TEXT, "
                + "opening TEXT, "
                + "closing TEXT, "
                + "position TEXT )")
        db.execSQL(CREATE_EMPLOYEE_TABLE)
    }
    // upgrade table
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS employees")
        onCreate(db)
    }
    // add employee
    fun addEmployee(fname: String, lname: String, nname: String, email: String, isActive: String, opening: String, closing: String, position: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("fname", fname)
        contentValues.put("lname", lname)
        contentValues.put("nname", nname)
        contentValues.put("email", email)
        contentValues.put("isActive", isActive)
        contentValues.put("opening", opening)
        contentValues.put("closing", closing)
        contentValues.put("position", position)

        try {
            val result = db.insert("employees", null, contentValues)
            db.close()
            return result != -1L
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding employee: ${e.message}")
            db.close()
            return false
        }
    }
    // get all employees for list (maybe obsolete)
    fun getAllEmployees(): List<Employee> {
        val employees = ArrayList<Employee>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM employees", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val fname = cursor.getString(1)
                val lname = cursor.getString(2)
                val nname = cursor.getString(3)
                val email = cursor.getString(4)
                val isActive = cursor.getString(5)
                val opening = cursor.getString(6)
                val closing = cursor.getString(7)
                val position = cursor.getString(8)
                employees.add(Employee(id, fname, lname, nname, email, isActive, opening, closing, position))
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

    /*fun modifyEmployee(id: Int, newName: String, newPosition: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", newName)
            put("position", newPosition)
        }

        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())

        val result = db.update("employees", contentValues, selection, selectionArgs).toLong()
        db.close()
        return result != -1L
    }*/
}
