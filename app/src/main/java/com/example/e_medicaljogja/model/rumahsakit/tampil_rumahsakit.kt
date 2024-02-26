package com.example.e_medicaljogja.model.rumahsakit

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.e_medicaljogja.R

class tampil_rumahsakit (private val context : Activity, private val arrayList : ArrayList<rumahsakit>) : ArrayAdapter<rumahsakit>(context, R.layout.list_rumahsakit_dashboard,arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_rumahsakit_dashboard,null)

        val imageView : ImageView = view.findViewById(R.id.gambar_rumahsakit_dashboard)
        val nama_rs : TextView = view.findViewById(R.id.nama_rumahsakit_dashboard)
        val jenis : TextView = view.findViewById(R.id.jenis_rumahsakit_dashboard)
        val kelas : TextView = view.findViewById(R.id.kelas_rumahsakit_dashboard)
        val telp : TextView = view.findViewById(R.id.telp_rumahsakit_dashboard)
        val jarak : TextView = view.findViewById(R.id.jarak_rumahssakit_dashboard)

        Glide.with(context).load(arrayList[position].gambar).thumbnail(0.5f).diskCacheStrategy(
            DiskCacheStrategy.ALL).into(imageView)

        nama_rs.text = arrayList[position].nama
        jenis.text = arrayList[position].jenis
        kelas.text = arrayList[position].kelas
        telp.text = arrayList[position].telp
        jarak.text = arrayList[position].jarak


        return view
    }
}