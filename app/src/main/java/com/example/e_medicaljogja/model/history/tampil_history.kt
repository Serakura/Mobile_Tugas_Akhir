package com.example.e_medicaljogja.model.history

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.e_medicaljogja.R

class tampil_history (private val context : Activity, private val arrayList : ArrayList<history>) : ArrayAdapter<history>(context, R.layout.list_history,arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_history,null)

        val nama_rs : TextView = view.findViewById(R.id.nama_rs_history)
        val kronologi : TextView = view.findViewById(R.id.kronologi_history)
        val jenis : TextView = view.findViewById(R.id.jenis_history)
        val waktu : TextView = view.findViewById(R.id.waktu_history)
        val lokasi : TextView = view.findViewById(R.id.lokasi_history)



        nama_rs.text = arrayList[position].nama
        kronologi.text = arrayList[position].kronologi
        jenis.text = arrayList[position].jenis
        waktu.text = arrayList[position].waktu
        lokasi.text = arrayList[position].lokasi


        return view
    }
}