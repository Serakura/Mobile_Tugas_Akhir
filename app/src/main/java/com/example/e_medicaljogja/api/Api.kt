package com.example.e_medicaljogja.api

import com.example.e_medicaljogja.model.fasilitas.fasilitas
import com.example.e_medicaljogja.model.history.history
import com.example.e_medicaljogja.model.login.login
import com.example.e_medicaljogja.model.pelayanan.pelayanan
import com.example.e_medicaljogja.model.permintaan.permintaan
import com.example.e_medicaljogja.model.permintaan.tambah_permintaan
import com.example.e_medicaljogja.model.register.register
import com.example.e_medicaljogja.model.rumahsakit.rumahsakit
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("login_api.php")
    fun userLogin(
        @Field("username") username:String,
        @Field("password") password:String
    ):Call<login>

    @FormUrlEncoded
    @POST("register_api.php")
    fun createUser(
        @Field("ktp") ktp:String,
        @Field("nama") nama:String,
        @Field("no_hp") nohp:String,
        @Field("no_hp_keluarga") nohpKeluarga:String,
        @Field("jenis_kelamin") jenkel:String,
        @Field("alamat") alamat:String,
        @Field("username") username:String,
        @Field("password") password:String
    ):Call<register>

    @FormUrlEncoded
    @POST("tampil_permintaan_api.php")
    fun tampilPermintaanDashboard(
        @Field("ktp") ktp: String
    ): Call<List<permintaan>>

    @FormUrlEncoded
    @POST("tampil_rumahsakit_api.php")
    fun tampilRumahSakitDashboard(
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String
    ): Call<List<rumahsakit>>

    @FormUrlEncoded
    @POST("search_rumahsakit_api.php")
    fun searchRumahSakitDashboard(
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("nama_rs") nama_rs: String
    ): Call<List<rumahsakit>>

    @FormUrlEncoded
    @POST("tampil_pelayanan_api.php")
    fun tampilPelayanan(
        @Field("kode_rumahsakit") kode_rs: String
    ): Call<List<pelayanan>>

    @FormUrlEncoded
    @POST("tampil_fasilitas_api.php")
    fun tampilFasilitas(
        @Field("kode_rumahsakit") kode_rs: String
    ): Call<List<fasilitas>>

    @FormUrlEncoded
    @POST("tampil_history_api.php")
    fun tampilHistoryDashboard(
        @Field("ktp") ktp: String
    ): Call<List<history>>

    @FormUrlEncoded
    @POST("tambah_permintaan_api.php")
    fun tambahPermintaan(
        @Field("ktp") ktp:String,
        @Field("kode_rumahsakit") kode_rs:String,
        @Field("kronologi") kronologi:String,
        @Field("jenis") jenis:String,
        @Field("lokasi") lokasi:String,
        @Field("waktu") waktu:String,
        @Field("latitude") latitude:String,
        @Field("longitude") longitude:String
    ):Call<tambah_permintaan>

}