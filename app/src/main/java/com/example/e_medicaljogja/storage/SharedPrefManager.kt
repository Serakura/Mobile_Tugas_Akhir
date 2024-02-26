package com.example.e_medicaljogja.storage
import android.content.Context
import com.example.e_medicaljogja.model.login.loginData

class SharedPrefManager private constructor(private val mCtx: Context){

    val isLoggedIn: Boolean
    get() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("ktp", null) != null
    }

    val user: loginData
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return loginData(
                sharedPreferences.getString("ktp", null),
                sharedPreferences.getString("nama", null),
                sharedPreferences.getString("no_hp", null),
                sharedPreferences.getString("no_hp_keluarga", null),
                sharedPreferences.getString("jenkel", null),
                sharedPreferences.getString("alamat", null)
            )
        }

    fun saveUser(user: loginData) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("ktp", user.ktp)
        editor.putString("nama", user.nama)
        editor.putString("no_hp", user.telp)
        editor.putString("no_hp_keluarga", user.telp_keluarga)
        editor.putString("jenkel", user.jenkel)
        editor.putString("alamat", user.alamat)

        editor.apply()

    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }


}