package com.example.proyectofinal_julen.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.AdaptadorCarrito
import com.example.proyectofinal_julen.entity.Producto

class DialogCarrito : DialogFragment() {

    private lateinit var lista : ListView
    private lateinit var precioFinal : TextView
    private var p1 = Producto()
    private var precio = 0.00

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)

        val inflater = requireActivity().layoutInflater //inflo el layout del dialogo
        val view: View = inflater.inflate(R.layout.dialog_carrito, null)

        lista = view.findViewById(R.id.lista)
        precioFinal = view.findViewById(R.id.precioTotal)

        val bundle = arguments
        val arrayProductos = bundle?.getSerializable("arrayP") as ArrayList<Producto>
        val arrayObservaciones = bundle.getSerializable("arrayO") as ArrayList<String>


        val adaptadorLista = AdaptadorCarrito(requireContext(), arrayProductos, arrayObservaciones)
        lista.adapter = adaptadorLista

        for (i in arrayProductos){
            p1 = i
            if (p1 != null) {
                precio += p1.precio
            }
        }

        precioFinal.text = ("Total a pagar: " + precio.toString() + " €")

        builder.setMessage("Confirmar pedido")
        dialog?.setContentView(R.layout.dialog_carrito)
        dialog?.getWindow()?.setLayout(200, ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.setView(view)
            .setPositiveButton("Confirmar",
                DialogInterface.OnClickListener{ dialog, id ->
                    Log.d("INFO:", "LO TENGO ${arrayProductos.size.toString()}")
                })
            .setNegativeButton("Volver",
                DialogInterface.OnClickListener { dialog, id ->
                    getDialog()?.cancel()
                })
        builder.create()

        return builder.create()
    }
}