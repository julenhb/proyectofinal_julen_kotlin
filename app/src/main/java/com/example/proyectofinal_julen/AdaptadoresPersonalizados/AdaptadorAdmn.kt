package com.example.proyectofinal_julen.AdaptadoresPersonalizados

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.Producto

class AdaptadorAdmn (private var mcontext: Context?, private var listaProductos : List<Producto>) : ArrayAdapter<Producto>(
    mcontext!!, R.layout.item_producto, listaProductos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(mcontext)
        val convertView = inflater.inflate(R.layout.item_producto, null)

        val nombreProducto = convertView.findViewById<TextView>(R.id.nombreProducto)
        val precoProducto = convertView.findViewById<TextView>(R.id.precioProducto)
        val rl = convertView.findViewById<RelativeLayout>(R.id.rl)

        nombreProducto.text = listaProductos[position].nombre
        precoProducto.text = (listaProductos[position].precio.toString() + " €")

        when {
            listaProductos[position].catalogado == false -> {
                rl.setBackgroundColor(ContextCompat.getColor(context, R.color.rojo_suave))
            }
            listaProductos[position].nombre.contains("Ternera", ignoreCase = true) -> {
                rl.setBackgroundColor(ContextCompat.getColor(context, R.color.marron))
            }
            listaProductos[position].nombre.contains("Pollo", ignoreCase = true) -> {
                rl.setBackgroundColor(ContextCompat.getColor(context, R.color.pollo))
            }
            listaProductos[position].nombre.contains("Falafel", ignoreCase = true) -> {
                rl.setBackgroundColor(ContextCompat.getColor(context, R.color.verde))
            }
            listaProductos[position].precio < 3 -> {
                rl.setBackgroundColor(ContextCompat.getColor(context, R.color.complementos))
            }
            listaProductos[position].nombre.contains("Mixto", ignoreCase = true) -> {
                rl.setBackgroundColor(ContextCompat.getColor(context, R.color.mixto))
            }
            else -> {
                rl.setBackgroundColor(ContextCompat.getColor(context, R.color.prueba))
            }
        }

        return convertView

    }
}