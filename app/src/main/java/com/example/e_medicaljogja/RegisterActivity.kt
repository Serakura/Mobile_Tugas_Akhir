package com.example.e_medicaljogja

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.example.e_medicaljogja.api.RetrofitClient
import com.example.e_medicaljogja.model.register.register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val et_ktp = findViewById<EditText>(R.id.txt_ktp_register)
        val et_nama = findViewById<EditText>(R.id.txt_nama_register)
        val et_phone = findViewById<EditText>(R.id.txt_telp_register)
        val et_phone_family = findViewById<EditText>(R.id.txt_telp_keluarga_register)
        val et_username = findViewById<EditText>(R.id.txt_username_register)
        val et_password = findViewById<EditText>(R.id.txt_password_register)
        val rg_jenkel = findViewById<RadioGroup>(R.id.jenkel_Grup)
        val et_alamat = findViewById<EditText>(R.id.txt_alamat_register)

        val btnRegister = findViewById<Button>(R.id.button_register)
        btnRegister.setOnClickListener{
            val ktp =et_ktp.text.toString().trim()
            val nama = et_nama.text.toString().trim()
            val telp = et_phone.text.toString().trim()
            val telp_keluarga = et_phone_family.text.toString().trim()
            val alamat = et_alamat.text.toString().trim()
            val username = et_username.text.toString().trim()
            val password = et_password.text.toString().trim()
            val jk = rg_jenkel.checkedRadioButtonId
            val jkl = findViewById<RadioButton>(jk)
            val jenkel = jkl.text.toString().trim()

            if(nama.isEmpty()){
                et_nama.error="Nama tidak boleh kosong!"
                et_nama.requestFocus()
                return@setOnClickListener
            }
            if(ktp.isEmpty()){
                et_ktp.error="Nomor KTP tidak boleh kosong!"
                et_ktp.requestFocus()
                return@setOnClickListener
            }
            if(telp.isEmpty()){
                et_phone.error="Telepon tidak boleh kosong!"
                et_phone.requestFocus()
                return@setOnClickListener
            }
            if(telp_keluarga.isEmpty()){
                et_phone.error="Telepon keluarga tidak boleh kosong!"
                et_phone.requestFocus()
                return@setOnClickListener
            }
            if(alamat.isEmpty()){
                et_alamat.error="Alamat tidak boleh kosong!"
                et_alamat.requestFocus()
                return@setOnClickListener
            }
            if(rg_jenkel.checkedRadioButtonId == -1){
                jkl.setError("Pilih jenis kelamin!")
            }
            if(username.isEmpty()){
                et_username.error="Username tidak boleh kosong!"
                et_username.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                et_password.error="Password tidak boleh kosong!"
                et_password.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(ktp,nama, telp, telp_keluarga, jenkel, alamat, username, password)
                .enqueue(object: Callback<register> {
                    override fun onResponse(call: Call<register>, response: Response<register>) {
                        if(response.body() != null && response.isSuccessful() && response.body()?.result_code == true){
                            Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()

                            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<register>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                })

        }

    }
}