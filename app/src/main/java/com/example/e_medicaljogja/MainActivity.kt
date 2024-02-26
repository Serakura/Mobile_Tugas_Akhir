package com.example.e_medicaljogja

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.e_medicaljogja.api.RetrofitClient
import com.example.e_medicaljogja.model.rumahsakit.rumahsakit
import com.example.e_medicaljogja.model.rumahsakit.tampil_rumahsakit
import com.example.e_medicaljogja.storage.SharedPrefManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity() {

    private lateinit var tampilArrayListRumahSakit : ArrayList<rumahsakit>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
//        tampilPermintaanDashboard()
        tampilRumahSakitDashboard()
        val btn_hasil_permintaan = findViewById<TextView>(R.id.btn_hasil_permintaan)
        val btn_profile = findViewById<TextView>(R.id.btn_profile)
        val btn_permintaan = findViewById<Button>(R.id.btn_permintaan)

        btn_hasil_permintaan.setOnClickListener {
            val intent = Intent(this,HasilPermintaanActivity::class.java)
            startActivity(intent)
        }
        btn_profile.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }
        btn_permintaan.setOnClickListener {
            val intent = Intent(this,PermintaanMapsActivity::class.java)
            startActivity(intent)
        }




//        refresh.setOnRefreshListener {
//
//            Handler().postDelayed(Runnable {
//             refresh.isRefreshing = false
//            }, 4000)
//        }

        val data = SharedPrefManager.getInstance(this).user
        val nama_dashboard = findViewById<TextView>(R.id.nama_akun_dashboard)
        nama_dashboard.setText(data.nama.toString())

        val txt_search = findViewById<EditText>(R.id.txt_search)
        txt_search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchRumahSakitDashboard(s.toString())
            }

        })


    }

    override fun onStart() {
        super.onStart()
        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            getLocation()
//            tampilPermintaanDashboard()
            tampilRumahSakitDashboard()
        }

    }

    private fun getLocation(){
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
                val text_lat = "Latitude: " + it.latitude.toString()
                val text_long = "Longitude: " + it.longitude.toString()

                val geocoder = Geocoder(this, Locale.getDefault())
                val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                val nm_tmpt = findViewById<TextView>(R.id.nama_lokasi)
                nm_tmpt.setText("${address[0].locality}")

            }
        }


    }



    private fun tampilRumahSakitDashboard(){

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

                val listViewRS = findViewById<ListView>(R.id.listViewRumahSakit)
                RetrofitClient.instance.tampilRumahSakitDashboard(text_lat,text_long).enqueue(object : Callback<List<rumahsakit>> {
                    override fun onResponse(
                        call: Call<List<rumahsakit>>,
                        response: Response<List<rumahsakit>>
                    ) {
                        if(response.body() != null && response.isSuccessful()) {
                            tampilArrayListRumahSakit = ArrayList()
                            for (i in response.body()!!.indices){
                                val data_rs = rumahsakit(response.body()!![i]!!.kode,response.body()!![i]!!.nama,response.body()!![i]!!.jenis,response.body()!![i]!!.kelas,response.body()!![i]!!.pemilik,response.body()!![i]!!.telp,
                                    response.body()!![i]!!.alamat,response.body()!![i]!!.latitude,response.body()!![i]!!.longitude,response.body()!![i]!!.gambar,response.body()!![i]!!.jarak)
                                tampilArrayListRumahSakit.add(data_rs)
                            }
                            listViewRS.adapter = tampil_rumahsakit(this@MainActivity,tampilArrayListRumahSakit)
                            listViewRS.setOnItemClickListener { parent, view, position, id ->
                                val kode_rs = response.body()!![position]!!.kode
                                val nama_rs = response.body()!![position]!!.nama
                                val jenis_rs = response.body()!![position]!!.jenis
                                val kelas_rs = response.body()!![position]!!.kelas
                                val pemilik_rs = response.body()!![position]!!.pemilik
                                val telp_rs = response.body()!![position]!!.telp
                                val alamat_rs = response.body()!![position]!!.alamat
                                val latitude_rs = response.body()!![position]!!.latitude
                                val longitude_rs = response.body()!![position]!!.longitude
                                val gambar_rs = response.body()!![position]!!.gambar

                                val i = Intent(this@MainActivity,RumahSakitActivity::class.java)
                                i.putExtra("kode_rs",kode_rs)
                                i.putExtra("nama_rs",nama_rs)
                                i.putExtra("jenis_rs",jenis_rs)
                                i.putExtra("kelas_rs",kelas_rs)
                                i.putExtra("pemilik_rs",pemilik_rs)
                                i.putExtra("telp_rs",telp_rs)
                                i.putExtra("alamat_rs",alamat_rs)
                                i.putExtra("latitude_rs",latitude_rs)
                                i.putExtra("longitude_rs",longitude_rs)
                                i.putExtra("gambar_rs",gambar_rs)

                                startActivity(i)
                            }
                        } else {
                            Toast.makeText(this@MainActivity.applicationContext,"Tidak Ada Data Rumah Sakit!",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<List<rumahsakit>>, t: Throwable) {
                        Toast.makeText(this@MainActivity.applicationContext, t.message,Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

    }

    private fun searchRumahSakitDashboard(cari : String){

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

                val listViewRS = findViewById<ListView>(R.id.listViewRumahSakit)
                RetrofitClient.instance.searchRumahSakitDashboard(text_lat,text_long,cari).enqueue(object : Callback<List<rumahsakit>> {
                    override fun onResponse(
                        call: Call<List<rumahsakit>>,
                        response: Response<List<rumahsakit>>
                    ) {
                        if(response.body() != null && response.isSuccessful()) {
                            tampilArrayListRumahSakit = ArrayList()
                            for (i in response.body()!!.indices){
                                val data_rs = rumahsakit(response.body()!![i]!!.kode,response.body()!![i]!!.nama,response.body()!![i]!!.jenis,response.body()!![i]!!.kelas,response.body()!![i]!!.pemilik,response.body()!![i]!!.telp,
                                    response.body()!![i]!!.alamat,response.body()!![i]!!.latitude,response.body()!![i]!!.longitude,response.body()!![i]!!.gambar,response.body()!![i]!!.jarak)
                                tampilArrayListRumahSakit.add(data_rs)
                            }
                            listViewRS.adapter = tampil_rumahsakit(this@MainActivity,tampilArrayListRumahSakit)
                            listViewRS.setOnItemClickListener { parent, view, position, id ->
                                val kode_rs = response.body()!![position]!!.kode
                                val nama_rs = response.body()!![position]!!.nama
                                val jenis_rs = response.body()!![position]!!.jenis
                                val kelas_rs = response.body()!![position]!!.kelas
                                val pemilik_rs = response.body()!![position]!!.pemilik
                                val telp_rs = response.body()!![position]!!.telp
                                val alamat_rs = response.body()!![position]!!.alamat
                                val latitude_rs = response.body()!![position]!!.latitude
                                val longitude_rs = response.body()!![position]!!.longitude
                                val gambar_rs = response.body()!![position]!!.gambar

                                val i = Intent(this@MainActivity,RumahSakitActivity::class.java)
                                i.putExtra("kode_rs",kode_rs)
                                i.putExtra("nama_rs",nama_rs)
                                i.putExtra("jenis_rs",jenis_rs)
                                i.putExtra("kelas_rs",kelas_rs)
                                i.putExtra("pemilik_rs",pemilik_rs)
                                i.putExtra("telp_rs",telp_rs)
                                i.putExtra("alamat_rs",alamat_rs)
                                i.putExtra("latitude_rs",latitude_rs)
                                i.putExtra("longitude_rs",longitude_rs)
                                i.putExtra("gambar_rs",gambar_rs)

                                startActivity(i)
                            }
                        } else {
                            Toast.makeText(this@MainActivity.applicationContext,"Tidak Ada Data Rumah Sakit!",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<List<rumahsakit>>, t: Throwable) {
                        Toast.makeText(this@MainActivity.applicationContext, t.message,Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

    }
}