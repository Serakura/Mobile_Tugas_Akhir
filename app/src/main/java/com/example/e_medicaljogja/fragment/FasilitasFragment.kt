package com.example.e_medicaljogja.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.e_medicaljogja.R
import com.example.e_medicaljogja.api.RetrofitClient
import com.example.e_medicaljogja.model.fasilitas.fasilitas
import com.example.e_medicaljogja.model.fasilitas.tampil_fasilitas
import com.example.e_medicaljogja.model.pelayanan.pelayanan
import com.example.e_medicaljogja.model.pelayanan.tampil_pelayanan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class FasilitasFragment : Fragment() {
    private lateinit var tampilArrayListFasilitas : ArrayList<fasilitas>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_fasilitas, container, false)
        val args = this.arguments
        val kode_rs = args?.get("kode_rs")

        val listView : ListView = view.findViewById(R.id.listViewFasilitas)
        RetrofitClient.instance.tampilFasilitas(kode_rs.toString()).enqueue(object :
            Callback<List<fasilitas>> {
            override fun onResponse(
                call: Call<List<fasilitas>>,
                response: Response<List<fasilitas>>
            ) {
                if(response.body() != null && response.isSuccessful()) {
                    tampilArrayListFasilitas = ArrayList()
                    for (i in response.body()!!.indices){
                        val data_fasilitas = fasilitas(response.body()!![i]!!.id,response.body()!![i]!!.nama)
                        tampilArrayListFasilitas.add(data_fasilitas)
                    }
                    listView.adapter = tampil_fasilitas(requireActivity(),tampilArrayListFasilitas)
                } else {
                    Toast.makeText(requireActivity().applicationContext,"Tidak Ada Data Fasilitas Rumah Sakit!",
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<fasilitas>>, t: Throwable) {
                Toast.makeText(requireActivity().applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })




        return view
    }


}