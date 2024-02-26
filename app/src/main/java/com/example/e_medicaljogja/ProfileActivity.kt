package com.example.e_medicaljogja

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.e_medicaljogja.storage.SharedPrefManager

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val data = SharedPrefManager.getInstance(this).user

        val tv_nama = findViewById<TextView>(R.id.akun_nama)
        val tv_ktp = findViewById<TextView>(R.id.akun_ktp)
        val tv_telp = findViewById<TextView>(R.id.akun_telp)
        val tv_telp_keluarga = findViewById<TextView>(R.id.akun_telp_keluarga)
        val tv_jenkel = findViewById<TextView>(R.id.akun_jenkel)
        val tv_alamat = findViewById<TextView>(R.id.akun_alamat)

        tv_nama.setText(data.nama.toString())
        tv_ktp.setText(data.ktp.toString())
        tv_telp.setText(data.telp.toString())
        tv_telp_keluarga.setText(data.telp_keluarga.toString())
        tv_jenkel.setText(data.jenkel.toString())
        tv_alamat.setText(data.alamat.toString())

        val endSession = SharedPrefManager.getInstance(this)

        val btn_logout = findViewById<Button>(R.id.btn_logout)
        btn_logout.setOnClickListener {
            endSession.clear()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val btn_back = findViewById<TextView>(R.id.btn_back_profile)
        btn_back.setOnClickListener {
            finish()
        }
    }
}