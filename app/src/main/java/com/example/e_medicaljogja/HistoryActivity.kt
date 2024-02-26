package com.example.e_medicaljogja

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_medicaljogja.api.RetrofitClient
import com.example.e_medicaljogja.model.history.history
import com.example.e_medicaljogja.model.history.tampil_history
import com.example.e_medicaljogja.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HistoryActivity : AppCompatActivity() {
    private lateinit var tampilArrayListHistory : ArrayList<history>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        tampilHistoryDashboard()

        val btn_back = findViewById<TextView>(R.id.btn_back_history)

        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        tampilHistoryDashboard()
    }

    private fun tampilHistoryDashboard(){
        val data = SharedPrefManager.getInstance(this).user
        val listView = findViewById<ListView>(R.id.listViewHistory)
        RetrofitClient.instance.tampilHistoryDashboard(data.ktp.toString()).enqueue(object :
            Callback<List<history>> {
            override fun onResponse(
                call: Call<List<history>>,
                response: Response<List<history>>
            ) {
                if(response.body() != null && response.isSuccessful()) {
                    tampilArrayListHistory = ArrayList()
                    for (i in response.body()!!.indices){
                        val data_history = history(response.body()!![i]!!.nama,response.body()!![i]!!.jenis,response.body()!![i]!!.kronologi,response.body()!![i]!!.lokasi,response.body()!![i]!!.waktu)
                        tampilArrayListHistory.add(data_history)
                    }
                    listView.adapter = tampil_history(this@HistoryActivity,tampilArrayListHistory)
                } else {
                    Toast.makeText(this@HistoryActivity.applicationContext,"Tidak Ada Data History Permintaan!",
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<history>>, t: Throwable) {
                Toast.makeText(this@HistoryActivity.applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}