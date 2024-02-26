package com.example.e_medicaljogja

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.e_medicaljogja.databinding.ActivityRumahSakitBinding
import com.example.e_medicaljogja.databinding.FragmentPelayananBinding
import com.example.e_medicaljogja.fragment.FasilitasFragment
import com.example.e_medicaljogja.fragment.FragmentPageAdapter
import com.example.e_medicaljogja.fragment.PelayananFragment
import com.google.android.material.tabs.TabLayout

class RumahSakitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRumahSakitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRumahSakitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tampilDataRumahSakit()
        val btn_back = findViewById<TextView>(R.id.btn_back_rumahsakit)
        val btn_lokasi = findViewById<Button>(R.id.btn_lokasi_rumahsakit)
        btn_back.setOnClickListener {
            finish()
        }
        btn_lokasi.setOnClickListener{
            lokasi()
        }



        val adapter = FragmentPageAdapter(this,supportFragmentManager,binding.tablayout.tabCount,intent.getStringExtra("kode_rs").toString())
        binding.viewpager.adapter = adapter
        binding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tablayout))
        binding.tablayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewpager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun tampilDataRumahSakit(){
        val logo_rs = findViewById<ImageView>(R.id.logo_rumahsakit)
        val tv_nama_rs = findViewById<TextView>(R.id.nama_rumahsakit)
        val tv_jenis_rs = findViewById<TextView>(R.id.jenis_rumahsakit)
        val tv_kelas_rs = findViewById<TextView>(R.id.kelas_rumahsakit)
        val tv_pemilik_rs = findViewById<TextView>(R.id.pemilik_rumahsakit)
        val tv_telp_rs = findViewById<TextView>(R.id.telp_rumahsakit)
        val tv_alamat_rs = findViewById<TextView>(R.id.alamat_rumahsakit)

        Glide.with(this).load(intent.getStringExtra("gambar_rs")).thumbnail(0.5f).diskCacheStrategy(
            DiskCacheStrategy.ALL).into(logo_rs)

        tv_nama_rs.text = intent.getStringExtra("nama_rs")
        tv_jenis_rs.text = intent.getStringExtra("jenis_rs")
        tv_kelas_rs.text = intent.getStringExtra("kelas_rs")
        tv_pemilik_rs.text = intent.getStringExtra("pemilik_rs")
        tv_telp_rs.text = intent.getStringExtra("telp_rs")
        tv_alamat_rs.text = intent.getStringExtra("alamat_rs")
    }

    private fun lokasi(){
        val latitude = intent.getStringExtra("latitude_rs").toString()
        val longitude = intent.getStringExtra("longitude_rs").toString()
        print("halo $latitude")

        val mapUri = Uri.parse("https://maps.google.com/maps?daddr=$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, mapUri)
        startActivity(intent)
    }

}