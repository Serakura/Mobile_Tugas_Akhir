package com.example.e_medicaljogja.model.fasilitas

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.e_medicaljogja.R
import com.example.e_medicaljogja.model.pelayanan.pelayanan

class tampil_fasilitas (private val context : Activity, private val arrayList : ArrayList<fasilitas>) : ArrayAdapter<fasilitas>(context, R.layout.list_fasilitas_rumahsakit,arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_fasilitas_rumahsakit,null)

        val nama_fasilitas : TextView = view.findViewById(R.id.nama_fasilitas)

        nama_fasilitas.text = arrayList[position].nama


        return view
    }
}