package com.example.proyectofinal_julen.entity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.proyectofinal_julen.R

class AdaptadorCarrito (private var mcontext: Context?, private var listaProductos : List<Producto>, private var listaObservaciones : List<String>)
    : ArrayAdapter<Producto>(mcontext!!, R.layout.item_producto, listaProductos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(mcontext)
        val convertView = inflater.inflate(R.layout.item_carrito, null)

        val nombreProducto = convertView.findViewById<TextView>(R.id.nombreProducto)
        val precoProducto = convertView.findViewById<TextView>(R.id.precioProducto)
        val obvs = convertView.findViewById<TextView>(R.id.observaciones)
        val lblObvs = convertView.findViewById<TextView>(R.id.observacionesLbl)

        nombreProducto.text = listaProductos[position].nombre
        precoProducto.text = (listaProductos[position].precio.toString() + " €")
        if (listaObservaciones[position].isEmpty()){
            lblObvs.isVisible = false
        }else {
            obvs.text = listaObservaciones[position]
        }



        return convertView

    }

}