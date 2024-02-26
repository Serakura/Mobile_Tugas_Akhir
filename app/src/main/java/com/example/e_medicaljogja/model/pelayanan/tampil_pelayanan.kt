package com.example.e_medicaljogja.model.pelayanan

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.e_medicaljogja.R

class tampil_pelayanan (private val context : Activity, private val arrayList : ArrayList<pelayanan>) : ArrayAdapter<pelayanan>(context, R.layout.list_pelayanan_rumahsakit,arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_pelayanan_rumahsakit,null)

        val nama_pelayanan : TextView = view.findViewById(R.id.nama_pelayanan)

        nama_pelayanan.text = arrayList[position].nama


        return view
    }
}