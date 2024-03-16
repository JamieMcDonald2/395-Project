/**
 *  database helper v1.3
 *  some ref's used in the creation of our database:
 *  - https://developer.android.com/training/data-storage/sqlite
 *  - https://abhiandroid.com/database/sqlite
 *  - https://www.freecodecamp.org/news/how-to-use-sqlite-database-with-android-studio/
 *  - https://www.androidauthority.com/sqlite-primer-for-android-811987/
 *
 *  v1.1:
 *      - added clear for seed testing
 *
 *  v1.2:
 *      - new database fields as per specs
 *
 *  v1.3:
 *      - major bug fixes (boolean vs string)
 */

package com.example.cmpt395aurora.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.cmpt395aurora.database.employees.Employee

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    /**
     * EMPLOYEE DATABASE
     */
    companion object {
        private const val DATABASE_NAME = "EmployeeDatabase"
        private const val DATABASE_VERSION = 5
    }
    // new table
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_EMPLOYEE_TABLE = ("CREATE TABLE employees ( "
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "fname TEXT, "
            + "lname TEXT, "
            + "nname TEXT, "
            + "email TEXT, "
            + "pnumber TEXT, "
            + "isActive INTEGER, "
            + "opening INTEGER, "
            + "closing INTEGER )")
        db.execSQL(CREATE_EMPLOYEE_TABLE)

        val CREATE_DAYSCHEDULE_TABLE = ("CREATE TABLE dayschedule ( "
                + "dsdate TEXT PRIMARY KEY, "
                + "employee1 TEXT, "
                + "employee2 TEXT, "
                + "employee3 TEXT) ")
        db.execSQL(CREATE_DAYSCHEDULE_TABLE)

        val CREATE_EMPAVAIL_TABLE = ("CREATE TABLE empavail ( "
                + "eadate TEXT PRIMARY KEY, "
                + "employeeid TEXT, "
                + "amAvailability TEXT, "
                + "pmAvailability TEXT, "
                + "adAvailability TEXT) ")
        db.execSQL(CREATE_EMPAVAIL_TABLE)
    }


    // upgrade table
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS employees")
        db.execSQL("DROP TABLE IF EXISTS dayschedule")
        db.execSQL("DROP TABLE IF EXISTS empavail")
        onCreate(db)
    }
    // add employee
    fun addEmployee(fname: String, lname: String, nname: String, email: String, pnumber: String, isActive: Boolean, opening: Boolean, closing: Boolean): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("fname", fname)
        contentValues.put("lname", lname)
        contentValues.put("nname", nname)
        contentValues.put("email", email)
        contentValues.put("pnumber", pnumber)
        contentValues.put("isActive", isActive)
        contentValues.put("opening", opening)
        contentValues.put("closing", closing)

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
                val pnumber = cursor.getString(5)
                val isActive = cursor.getInt(6) != 0  //Boolean
                val opening = cursor.getInt(7) != 0   //Boolean
                val closing = cursor.getInt(8) != 0   //Boolean
                employees.add(Employee(id, fname, lname, nname, email, pnumber, isActive, opening, closing))
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

    fun addAvailability(eadate: String, employeeid: String, amAvailability: String, pmAvailability: String, adAvailability: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("eadate", eadate)
        contentValues.put("employeeid", employeeid)
        contentValues.put("amAvailability", amAvailability)
        contentValues.put("pmAvailability", pmAvailability)
        contentValues.put("adAvailability", adAvailability)

        try {
            val result = db.insert("empavail", null, contentValues)
            db.close()
            return result != -1L
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding availability: ${e.message}")
            db.close()
            return false
        }
    }

    fun deleteAvailability(eadate: String, employeeid: String): Boolean {
        val db = this.writableDatabase
        val whereClause = "employeeid = ? AND eadate = ?"
        val whereArgs = arrayOf(employeeid.toString(), eadate)
        val result = db.delete("employeeavailability", whereClause, whereArgs).toLong()
        db.close()
        return result != -1L
    }

    fun addShift(dsdate: String, employeeid: String, amAvailability: String, pmAvailability: String, adAvailability: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("dsdate", dsdate)
        contentValues.put("employee1", employeeid)
        contentValues.put("employee2", amAvailability)
        contentValues.put("employee3", pmAvailability)


        try {
            val result = db.insert("dayschedule", null, contentValues)
            db.close()
            return result != -1L
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding availability: ${e.message}")
            db.close()
            return false

        }
    }

    fun deleteShift(dsdate: String, employeeid: String): Boolean {
        val db = this.writableDatabase
        val whereClause = "employeeid = ? AND dsdate = ?"
        val whereArgs = arrayOf(employeeid.toString(), dsdate)
        val result = db.delete("dayschedule", whereClause, whereArgs).toLong()
        db.close()
        return result != -1L
    }

}
