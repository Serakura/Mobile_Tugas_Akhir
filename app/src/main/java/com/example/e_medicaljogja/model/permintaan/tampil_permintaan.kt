package com.example.e_medicaljogja.model.permintaan

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.e_medicaljogja.R

class tampil_permintaan (private val context : Activity, private val arrayList : ArrayList<permintaan>) : ArrayAdapter<permintaan>(context, R.layout.list_permintaan_hasil,arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_permintaan_hasil,null)

        val nama_rs : TextView = view.findViewById(R.id.nama_rs_permintaan_dashboard)
        val kronologi : TextView = view.findViewById(R.id.kronologi_permintaan_dashboard)
        val jenis : TextView = view.findViewById(R.id.jenis_permintaan_dashboard)
        val waktu : TextView = view.findViewById(R.id.waktu_permintaan_dashboard)
        val lokasi : TextView = view.findViewById(R.id.lokasi_permintaan_dashboard)
        val status : TextView = view.findViewById(R.id.status_permintaan_dashboard)


        nama_rs.text = arrayList[position].nama
        kronologi.text = arrayList[position].kronologi
        jenis.text = arrayList[position].jenis
        waktu.text = arrayList[position].waktu
        lokasi.text = arrayList[position].lokasi
        status.text = arrayList[position].status

        return view
    }
}