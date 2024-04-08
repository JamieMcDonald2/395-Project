/**
 *  database helper v1.7
 *  database version 6
 *
 *  some ref's used in the creation of our database:
 *  - https://developer.android.com/training/data-storage/sqlite
 *  - https://abhiandroid.com/database/sqlite
 *  - https://www.freecodecamp.org/news/how-to-use-sqlite-database-with-android-studio/
 *  - https://www.androidauthority.com/sqlite-primer-for-android-811987/
 *
 *  v1.7
 *      - new get employee ID function - moved employee ID to employee database
 *      - new logger
 *
 *  v1.6:
 *      - new update employee function
 *
 *  v1.5:
 *      - new update username function for settings/database
 *
 *  v1.4:
 *      - new single employee query "getEmployeeById"
 *
 *  v1.3:
 *      - major bug fixes (boolean vs string)
 *
 *  v1.2:
 *      - new database fields as per specs
 *
 *  v1.1:
 *      - added clear for seed testing
 *
 */

package com.example.cmpt395solaris.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.cmpt395solaris.database.availability.EmpAvail
import com.example.cmpt395solaris.database.dayschedule.DaySchedule
import com.example.cmpt395solaris.database.employees.Employee

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    /**
     * EMPLOYEE DATABASE
     */
    companion object {
        private const val DATABASE_NAME = "EmployeeDatabase"
        private const val DATABASE_VERSION = 6
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
            + "employeeAM1 INTEGER, "
            + "employeeAM2 INTEGER, "
            + "employeeAM3 INTEGER, "
            + "employeePM1 INTEGER, "
            + "employeePM2 INTEGER, "
            + "employeePM3 INTEGER) ")
        db.execSQL(CREATE_DAYSCHEDULE_TABLE)

        val CREATE_EMPAVAIL_TABLE = ("CREATE TABLE empavail ( "
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "mondayAM INTEGER,"
            + "mondayPM INTEGER,"
            + "tuesdayAM INTEGER,"
            + "tuesdayPM INTEGER,"
            + "wednesdayAM INTEGER,"
            + "wednesdayPM INTEGER,"
            + "thursdayAM INTEGER,"
            + "thursdayPM INTEGER,"
            + "fridayAM INTEGER,"
            + "fridayPM INTEGER,"
            + "saturday INTEGER, "
            + "sunday INTEGER) ")
        db.execSQL(CREATE_EMPAVAIL_TABLE)

        val CREATE_SETTINGS_TABLE = ("CREATE TABLE settings (username TEXT UNIQUE)")
        db.execSQL(CREATE_SETTINGS_TABLE)
    }

    // upgrade table
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS employees")
        db.execSQL("DROP TABLE IF EXISTS dayschedule")
        db.execSQL("DROP TABLE IF EXISTS empavail")
        db.execSQL("DROP TABLE IF EXISTS settings")
        onCreate(db)
    }

    /**
     * This is for testing puposes - log table values
     */
    fun logDatabaseTables() {
        val db = this.readableDatabase
        val tables = arrayOf("employees", "dayschedule", "empavail", "settings")

        for (table in tables) {
            val cursor = db.rawQuery("SELECT * FROM $table", null)
            val columnNames = cursor.columnNames
            while (cursor.moveToNext()) {
                val IDValues = StringBuilder()
                for (column in columnNames) {
                    val columnIndex = cursor.getColumnIndex(column)
                    if (columnIndex != -1) {
                        val value = cursor.getString(columnIndex)
                        IDValues.append(value ?: "NULL VALUE").append(" | ")
                    } else {
                        Log.d("DatabaseLog", "Column not found: $column")  // testing
                    }
                }
                Log.d("DatabaseLog", "Table: $table, Values: $IDValues")  // testing
            }
            cursor.close()
        }
    }

    // add employee
    fun addEmployee(id: Int, fname: String, lname: String, nname: String, email: String, pnumber: String, isActive: Boolean, opening: Boolean, closing: Boolean): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().also {
//            it.put("id", id)
            it.put("fname", fname)
            it.put("lname", lname)
            it.put("nname", nname)
            it.put("email", email)
            it.put("pnumber", pnumber)
            it.put("isActive", isActive)
            it.put("opening", opening)
            it.put("closing", closing)
        }

        return try {
            val result = db.insert("employees", null, contentValues)
            db.close()
            result != -1L
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding employee: ${e.message}")
            db.close()
            false
        }
    }

    // get all
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
//                Log.d("getAllEmployees", "Employee created with ID: $id, First Name: $fname, Last Name: $lname")   // testing
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
//        Log.d("getAllEmployees", "Number of employees: ${employees.size}") // log employees test
        return employees
    }

    /**
     * Query Employee by ID
     */
    fun getEmployeeById(id: Int): Employee? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM employees WHERE id = ?", arrayOf(id.toString()))
        var employee: Employee? = null
        if (cursor.moveToFirst()) {
            val fname = cursor.getString(1)
            val lname = cursor.getString(2)
            val nname = cursor.getString(3)
            val email = cursor.getString(4)
            val pnumber = cursor.getString(5)
            val isActive = cursor.getInt(6) != 0  //Boolean
            val opening = cursor.getInt(7) != 0   //Boolean
            val closing = cursor.getInt(8) != 0   //Boolean
            employee = Employee(id, fname, lname, nname, email, pnumber, isActive, opening, closing)
        }
        cursor.close()
        db.close()
//        if (employee != null) {
//            Log.d("EmployeeById", "employeeID: ${employee.id}")          //testing
//        }
        return employee
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
        db.delete("empavail", null, null)
        db.close()
    }

    fun updateEmployee(employee: Employee): Int {

        Log.d("ViewModelUpdate", "Updating employee: $employee") // testing

        val db = this.writableDatabase
        val contentValues = ContentValues().also {
            it.put("fname", employee.fname)
            it.put("lname", employee.lname)
            it.put("nname", employee.nname)
            it.put("email", employee.email)
            it.put("pnumber", employee.pnumber)
            it.put("isActive", if (employee.isActive) 1 else 0)
            it.put("opening", if (employee.opening) 1 else 0)
            it.put("closing", if (employee.closing) 1 else 0)
        }

//        Log.d("DB Update", "Updating employee with ID: ${employee.id}") // testing
        // Updating row
        val affectedRows = db.update("employees", contentValues, "id = ?", arrayOf(employee.id.toString()))

//        // Log the number of affected rows
//        Log.d("DB Update", "Number of rows affected: $affectedRows") // testing
//        Log.d("DB Update2", "New Values: ${employee.id}, ${employee.fname}, ${employee.lname}, ${employee.nname}, ${employee.email}, ${employee.pnumber}") // testing

        return affectedRows
    }

    /**
     * Availability functions
     */

    fun addEmployeeToAvailabilityDB(id: Int): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues().also {
            it.put("mondayAM", false)
            it.put("mondayPM", false)
            it.put("tuesdayAM", false)
            it.put("tuesdayPM", false)
            it.put("wednesdayAM", false)
            it.put("wednesdayPM", false)
            it.put("thursdayAM", false)
            it.put("thursdayPM", false)
            it.put("fridayAM", false)
            it.put("fridayPM", false)
            it.put("saturday", false)
            it.put("sunday", false)
        }

        return try {
            val result = db.insert("empavail", null, contentValues)
            db.close()
            result != -1L
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding employee availability: ${e.message}")
            db.close()
            false
        }
    }

    fun getAvailabilityById(id: Int): EmpAvail? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM empavail WHERE id = ?", arrayOf(id.toString()))
        var avail: EmpAvail? = null
        if (cursor.moveToFirst()) {
            val mondayAM = cursor.getInt(1) != 0
            val mondayPM = cursor.getInt(2) != 0
            val tuesdayAM = cursor.getInt(3) != 0
            val tuesdayPM = cursor.getInt(4) != 0
            val wednesdayAM = cursor.getInt(5) != 0
            val wednesdayPM = cursor.getInt(6) != 0  //Boolean
            val thursdayAM = cursor.getInt(7) != 0
            val thursdayPM = cursor.getInt(8) != 0
            val fridayAM = cursor.getInt(9) != 0   //Boolean
            val fridayPM = cursor.getInt(10) != 0   //Boolean
            val saturday = cursor.getInt(11) != 0   //Bool
            val sunday = cursor.getInt(12) != 0   //Bool
            avail = EmpAvail(id,
                mondayAM, mondayPM,
                tuesdayAM, tuesdayPM,
                wednesdayAM, wednesdayPM,
                thursdayAM, thursdayPM,
                fridayAM, fridayPM,
                saturday, sunday)
        }
        cursor.close()
        db.close()
        if (avail != null) {
            Log.d("AvailabilityById", "employeeID: ${avail.id}")          //testing
        }
        return avail
    }

    fun updateAvailability(avail: EmpAvail): Int {

        Log.d("ViewModelUpdate", "Updating Availability: $avail") // testing

        val db = this.writableDatabase
        val contentValues = ContentValues().also {
            it.put("mondayAM", avail.mondayAM)
            it.put("mondayPM", avail.mondayPM)
            it.put("tuesdayAM", avail.tuesdayAM)
            it.put("tuesdayPM", avail.tuesdayPM)
            it.put("wednesdayAM", avail.wednesdayAM)
            it.put("wednesdayPM", avail.wednesdayPM)
            it.put("thursdayAM", avail.wednesdayAM)
            it.put("thursdayPM", avail.wednesdayPM)
            it.put("fridayAM", avail.fridayAM)
            it.put("fridayPM", avail.fridayPM)
            it.put("saturday", avail.saturday)
            it.put("sunday", avail.sunday)
        }

        val affectedRows = db.update("empavail", contentValues, "id = ?", arrayOf(avail.id.toString()))

        return affectedRows
    }

    fun getAvailEmployees(fieldName: String): ArrayList<Employee>{
        val db = this.readableDatabase
        val employees = ArrayList<Employee>()
        val idList = getIdsOfAvailableEmployees(fieldName)

        for(id in idList){
            val employee = getEmployeeById(id)
            if (employee != null && employee.isActive) {
                employees.add(employee)
            }
        }

        return employees
    }

    fun getOpenTrainedEmployees(fieldName: String): ArrayList<Employee>{
        val employees = getAvailEmployees(fieldName)

        for(employee in employees){
            if (!employee.opening){
                employees.remove(employee)
            }
        }

        return employees
    }


    fun getCloseTrainedEmployees(fieldName: String): ArrayList<Employee>{
        val employees = getAvailEmployees(fieldName)

        for(employee in employees){
            if (!employee.closing){
                employees.remove(employee)
            }
        }

        return employees
    }

    fun getBothTrainedEmployees(fieldName: String): ArrayList<Employee>{
        val employees = getAvailEmployees(fieldName)

        for(employee in employees){
            if (!employee.closing && !employee.opening){
                employees.remove(employee)
            }
        }

        return employees
    }

    @SuppressLint("Range")
    fun getIdsOfAvailableEmployees(fieldName: String): List<Int> {
        val db = this.readableDatabase
        val ids = mutableListOf<Int>()
        val cursor = db.query(
            "empavail",
            arrayOf("id", fieldName),
            "$fieldName = 1",
            null,
            null,
            null,
            null
        )
        cursor.use {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                ids.add(id)
            }
        }
        return ids
    }

    /**
     * Scheduling functions
     */

    fun doesDsDateExist(dsDate: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT COUNT(*) FROM dayschedule WHERE dsdate = ?"
        val cursor = db.rawQuery(query, arrayOf(dsDate))
        cursor.use {
            if (it.moveToFirst()) {
                val count = it.getInt(0)
                return count > 0
            }
        }
        return false
    }



    fun updateDaySchedule(schedule: DaySchedule): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues().also {
            it.put("dsdate", schedule.dsdate)
            it.put("employeeAM1", schedule.employeeAM1)
            it.put("employeeAM2", schedule.employeeAM2)
            it.put("employeeAM3", schedule.employeeAM3)
            it.put("employeePM1", schedule.employeePM1)
            it.put("employeePM2", schedule.employeePM2)
            it.put("employeePM3", schedule.employeePM3)
        }

        Log.d("UpdateSchedule", "Content: $contentValues")
        val affectedRows = db.update("dayschedule", contentValues, "dsdate = ?", arrayOf(schedule.dsdate))

        return affectedRows
    }

    fun addDaySchedule(schedule: DaySchedule): Boolean {
        val db = this.writableDatabase

        val contentValues = ContentValues().also {
            it.put("dsdate", schedule.dsdate)
            it.put("employeeAM1", schedule.employeeAM1)
            it.put("employeeAM2", schedule.employeeAM2)
            it.put("employeeAM3", schedule.employeeAM3)
            it.put("employeePM1", schedule.employeePM1)
            it.put("employeePM2", schedule.employeePM2)
            it.put("employeePM3", schedule.employeePM3)
        }

        return try {
            Log.d("AddSchedule", "Content: $contentValues")
            val result = db.insert("dayschedule", null, contentValues)
            db.close()
            result != -1L
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding day schedule: ${e.message}")
            db.close()
            false
        }
    }


    @SuppressLint("Recycle")
    fun getDaySchedule(dsdate: String): DaySchedule? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM dayschedule WHERE dsdate = ?", arrayOf(dsdate))
        var daySched: DaySchedule? = null

        if (cursor.moveToFirst()) {
            val employeeAM1 = cursor.getInt(1)
            val employeeAM2 = cursor.getInt(2)
            val employeeAM3 = cursor.getInt(3)
            val employeePM1 = cursor.getInt(4)
            val employeePM2 = cursor.getInt(5)
            val employeePM3 = cursor.getInt(6)

            daySched = DaySchedule(
                dsdate,
                employeeAM1,
                employeeAM2,
                employeeAM3,
                employeePM1,
                employeePM2,
                employeePM3
            )
        }
        cursor.close()
        db.close()

        return daySched
    }

    /**
     * Username functions
     */


    // update username in view model through settings page
    fun updateUsername(newUsername: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", newUsername)

        try {
            // Check if a row exists in the settings table
            val cursor = db.rawQuery("SELECT * FROM settings", null)
            if (cursor.moveToFirst()) {
                // If a row exists, update it
                db.update("settings", contentValues, null, null)
            } else {
                // If no row exists, insert a new one
                db.insert("settings", null, contentValues)
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error updating username: ${e.message}")
        } finally {
            Log.d("DatabaseHelper", "Updated username: $newUsername")   //test
            db.close()
        }
    }

    // get username for setting page
    fun getUsername(): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT username FROM settings", null)
        var username = ""
        if (cursor.moveToFirst()) {
            username = cursor.getString(0)
        } else {
            Log.d("DatabaseHelper", "Cursor is empty")           //test
        }
        cursor.close()
        db.close()
        Log.d("DatabaseHelper", "Fetched username: $username")   //test
        return username
    }
}