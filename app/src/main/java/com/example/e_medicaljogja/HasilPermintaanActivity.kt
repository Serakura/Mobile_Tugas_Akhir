package com.example.e_medicaljogja

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_medicaljogja.api.RetrofitClient
import com.example.e_medicaljogja.model.permintaan.permintaan
import com.example.e_medicaljogja.model.permintaan.tampil_permintaan
import com.example.e_medicaljogja.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HasilPermintaanActivity : AppCompatActivity() {
    private lateinit var tampilArrayListPemrmintaan : ArrayList<permintaan>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_permintaan)
        tampilPermintaanDashboard()

        val btn_history = findViewById<TextView>(R.id.btn_history)
        val btn_back = findViewById<TextView>(R.id.btn_back_hasil_permintaan)

        btn_history.setOnClickListener {
            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }
        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        tampilPermintaanDashboard()
    }

    private fun tampilPermintaanDashboard(){
        val data = SharedPrefManager.getInstance(this).user
        val listView = findViewById<ListView>(R.id.listView)
        RetrofitClient.instance.tampilPermintaanDashboard(data.ktp.toString()).enqueue(object :
            Callback<List<permintaan>> {
            override fun onResponse(
                call: Call<List<permintaan>>,
                response: Response<List<permintaan>>
            ) {
                if(response.body() != null && response.isSuccessful()) {
                    tampilArrayListPemrmintaan = ArrayList()
                    for (i in response.body()!!.indices){
                        val data_permintaan = permintaan(response.body()!![i]!!.nama,response.body()!![i]!!.jenis,response.body()!![i]!!.kronologi,response.body()!![i]!!.lokasi,response.body()!![i]!!.waktu,
                            response.body()!![i]!!.status)
                        tampilArrayListPemrmintaan.add(data_permintaan)
                    }
                    listView.adapter = tampil_permintaan(this@HasilPermintaanActivity,tampilArrayListPemrmintaan)
                } else {
                    Toast.makeText(this@HasilPermintaanActivity.applicationContext,"Tidak Ada Data Permintaan Pertolongan!",
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<permintaan>>, t: Throwable) {
                Toast.makeText(this@HasilPermintaanActivity.applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}