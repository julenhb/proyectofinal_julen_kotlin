package com.example.proyectofinal_julen.entity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.proyectofinal_julen.R

class AdaptadorProducto (private var mcontext: Context?, private var listaProductos : List<Producto>) : ArrayAdapter<Producto>(
    mcontext!!, R.layout.item_producto, listaProductos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(mcontext)
        val convertView = inflater.inflate(R.layout.item_producto, null)

        val nombreProducto = convertView.findViewById<TextView>(R.id.nombreProducto)
        val precoProducto = convertView.findViewById<TextView>(R.id.precioProducto)

        nombreProducto.text = listaProductos[position].nombre
        precoProducto.text = (listaProductos[position].precio.toString() + " €")



        return convertView

    }

}