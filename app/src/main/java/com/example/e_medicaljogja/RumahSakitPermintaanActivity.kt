package com.example.e_medicaljogja

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.e_medicaljogja.api.RetrofitClient
import com.example.e_medicaljogja.model.rumahsakit.rumahsakit
import com.example.e_medicaljogja.model.rumahsakit.tampil_rumahsakit
import com.example.e_medicaljogja.model.rumahsakit.tampil_rumahsakit_permintaan
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class RumahSakitPermintaanActivity : AppCompatActivity() {
    private lateinit var tampilArrayListRumahSakitPermintaan : ArrayList<rumahsakit>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rumahsakit_permintaan)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        tampilRumahSakitPermintaan()
        val btn_back = findViewById<TextView>(R.id.btn_back_rumahsakit_permintaan)
        btn_back.setOnClickListener{
            finish()
        }
    }

    private fun tampilRumahSakitPermintaan(){

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
            return
        }

        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if(it!=null){
                val text_lat = it.latitude.toString()
                val text_long = it.longitude.toString()

                val listViewRSPermintaan = findViewById<ListView>(R.id.listViewRumahSakitPermintaan)
                RetrofitClient.instance.tampilRumahSakitDashboard(text_lat,text_long).enqueue(object :
                    Callback<List<rumahsakit>> {
                    override fun onResponse(
                        call: Call<List<rumahsakit>>,
                        response: Response<List<rumahsakit>>
                    ) {
                        if(response.body() != null && response.isSuccessful()) {
                            tampilArrayListRumahSakitPermintaan = ArrayList()
                            for (i in response.body()!!.indices){
                                val data_rs = rumahsakit(response.body()!![i]!!.kode,response.body()!![i]!!.nama,response.body()!![i]!!.jenis,response.body()!![i]!!.kelas,response.body()!![i]!!.pemilik,response.body()!![i]!!.telp,
                                    response.body()!![i]!!.alamat,response.body()!![i]!!.latitude,response.body()!![i]!!.longitude,response.body()!![i]!!.gambar,response.body()!![i]!!.jarak)
                                tampilArrayListRumahSakitPermintaan.add(data_rs)
                            }
                            listViewRSPermintaan.adapter = tampil_rumahsakit_permintaan(this@RumahSakitPermintaanActivity,tampilArrayListRumahSakitPermintaan)
                            listViewRSPermintaan.setOnItemClickListener { parent, view, position, id ->
                                val kode_rs = response.body()!![position]!!.kode
                                val nama_rs = response.body()!![position]!!.nama


                                val i = Intent(this@RumahSakitPermintaanActivity,DetailPermintaanActivity::class.java)
                                i.putExtra("kode_rs",kode_rs)
                                i.putExtra("nama_rs",nama_rs)


                                startActivity(i)
                            }
                        } else {
                            Toast.makeText(this@RumahSakitPermintaanActivity.applicationContext,"Tidak Ada Data Rumah Sakit!",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<List<rumahsakit>>, t: Throwable) {
                        Toast.makeText(this@RumahSakitPermintaanActivity.applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

    }
}