package com.example.proyectofinal_julen.AdaptadoresPersonalizados

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.Pedido
import com.example.proyectofinal_julen.entity.Producto

class AdaptadorPedidos(private var mcontext: Context?, private var listaPedidos: List<Pedido>) :
    ArrayAdapter<Pedido>(mcontext!!, R.layout.item_producto, listaPedidos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(mcontext)
        val convertView = inflater.inflate(R.layout.item_carrito, null)
        var listaObservaciones: ArrayList<String>

        val nombreProducto = convertView.findViewById<TextView>(R.id.nombreProducto)
        val precoProducto = convertView.findViewById<TextView>(R.id.precioProducto)
        val obvs = convertView.findViewById<TextView>(R.id.observaciones)
        val lblObvs = convertView.findViewById<TextView>(R.id.observacionesLbl)

        nombreProducto.text = listaPedidos[position].usuario + "\npidió: "
        precoProducto.text = listaPedidos[position].producto

        obvs.text = listaPedidos[position].observaciones




        return convertView

    }

}