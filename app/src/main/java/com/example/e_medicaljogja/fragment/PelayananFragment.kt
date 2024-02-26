package com.example.e_medicaljogja.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.e_medicaljogja.R
import com.example.e_medicaljogja.api.RetrofitClient
import com.example.e_medicaljogja.model.history.history
import com.example.e_medicaljogja.model.history.tampil_history
import com.example.e_medicaljogja.model.pelayanan.pelayanan
import com.example.e_medicaljogja.model.pelayanan.tampil_pelayanan
import com.example.e_medicaljogja.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PelayananFragment : Fragment() {

    private lateinit var tampilArrayListPelayanan : ArrayList<pelayanan>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_pelayanan, container, false)
        val args = this.arguments
        val kode_rs = args?.get("kode_rs")

        val listView : ListView = view.findViewById(R.id.listViewPelayanan)
        RetrofitClient.instance.tampilPelayanan(kode_rs.toString()).enqueue(object :
            Callback<List<pelayanan>> {
            override fun onResponse(
                call: Call<List<pelayanan>>,
                response: Response<List<pelayanan>>
            ) {
                if(response.body() != null && response.isSuccessful()) {
                    tampilArrayListPelayanan = ArrayList()
                    for (i in response.body()!!.indices){
                        val data_pelayanan = pelayanan(response.body()!![i]!!.id,response.body()!![i]!!.nama)
                        tampilArrayListPelayanan.add(data_pelayanan)
                    }
                    listView.adapter = tampil_pelayanan(requireActivity(),tampilArrayListPelayanan)
                } else {
                    Toast.makeText(requireActivity().applicationContext,"Tidak Ada Data Pelayanan Rumah Sakit!",
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<pelayanan>>, t: Throwable) {
                Toast.makeText(requireActivity().applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })




        return view
    }


}