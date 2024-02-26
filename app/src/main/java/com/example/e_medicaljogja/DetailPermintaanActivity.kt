package com.example.e_medicaljogja

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle

import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.e_medicaljogja.api.RetrofitClient
import com.example.e_medicaljogja.model.permintaan.tambah_permintaan
import com.example.e_medicaljogja.storage.SharedPrefManager

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPermintaanActivity : AppCompatActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_permintaan)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100
            )
            return
        }
        val txt_kode_rs = findViewById<EditText>(R.id.txt_rumahsakit_kode)
        val txt_nama_rs = findViewById<TextView>(R.id.txt_rumahsakit_nama)
        val rg_jenis = findViewById<RadioGroup>(R.id.jenis_permintaan_Grup)
        val txt_kronologi = findViewById<EditText>(R.id.txt_kronologi)

        txt_kode_rs.setText(intent.getStringExtra("kode_rs").toString())

        txt_nama_rs.setText(intent.getStringExtra("nama_rs").toString())
        val btn_back = findViewById<TextView>(R.id.btn_back_detail_permintaan)
        btn_back.setOnClickListener{
            finish()
        }


        val btn_kirim = findViewById<Button>(R.id.button_kirim_permintaaan)
        btn_kirim.setOnClickListener {

            val kronologi = txt_kronologi.text.toString().trim()
            val jp = rg_jenis.checkedRadioButtonId
            val jpr = findViewById<RadioButton>(jp)
            val jenis= jpr.text.toString().trim()
            val kode_rs = txt_kode_rs.text.toString().trim()
            val data = SharedPrefManager.getInstance(this).user
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val current = LocalDateTime.now().format(formatter)

            println(data.ktp.toString())
            println(kode_rs)
            println(jenis)
            println(current.toString())
            println(kronologi)
            val location = fusedLocationProviderClient.lastLocation
            location.addOnSuccessListener {
                if (it != null) {
                    val text_lat = "Latitude: " + it.latitude.toString()
                    val text_long = "Longitude: " + it.longitude.toString()

                println(text_lat)
                    println(text_long)


                    val geocoder = Geocoder(this, Locale.getDefault())
                    val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)


                    val lokasi = "${address[0].getAddressLine(0)}"
                    println(lokasi)

                    RetrofitClient.instance.tambahPermintaan(data.ktp.toString(),kode_rs, kronologi, jenis, lokasi.toString(), current.toString(), it.latitude.toString(), it.longitude.toString())
                        .enqueue(object: Callback<tambah_permintaan> {
                            override fun onResponse(call: Call<tambah_permintaan>, response: Response<tambah_permintaan>) {
                                if(response.body() != null && response.isSuccessful()){
                                    Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()

                                    val intent = Intent(this@DetailPermintaanActivity,MainActivity::class.java)
                                    startActivity(intent)
                                    finish()

                                } else {
                                    Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<tambah_permintaan>, t: Throwable) {
                                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                            }

                        })


                }
            }

        }
    }
}