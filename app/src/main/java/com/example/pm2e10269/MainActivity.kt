package com.example.pm2e10269

import SiteDatabaseHelper
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: SiteDatabaseHelper
    private lateinit var database: SQLiteDatabase
    private lateinit var latitudeEditText: EditText
    private lateinit var longitudeEditText: EditText
    private lateinit var descriptionEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = SiteDatabaseHelper(this)
        database = dbHelper.writableDatabase
        latitudeEditText = findViewById(R.id.latitudeEditText)
        longitudeEditText = findViewById(R.id.longitudeEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            addSite()
        }
    }

    private fun addSite() {
        val latitude = latitudeEditText.text.toString()
        val longitude = longitudeEditText.text.toString()
        val description = descriptionEditText.text.toString()

        if (validateInput(latitude, longitude, description)) {
            val values = ContentValues().apply {
                put(SiteDatabaseHelper.COLUMN_LATITUDE, latitude.toDouble())
                put(SiteDatabaseHelper.COLUMN_LONGITUDE, longitude.toDouble())
                put(SiteDatabaseHelper.COLUMN_DESCRIPTION, description)
            }
            database.insert(SiteDatabaseHelper.TABLE_NAME, null, values)
            Toast.makeText(this, "Sitio agregado correctamente", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "Por favor, rellena todos los campos correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(latitude: String, longitude: String, description: String): Boolean {
        return latitude.isNotBlank() && longitude.isNotBlank() && description.isNotBlank()
    }

    private fun clearFields() {
        latitudeEditText.text.clear()
        longitudeEditText.text.clear()
        descriptionEditText.text.clear()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    fun onExitClick(view: android.view.View) {
        finish()
    }
}
