package com.example.proyectofinal_julen.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.proyectofinal_julen.OnDialogListener
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.AdaptadorProducto
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.entity.Usuario
import com.example.proyectofinal_julen.service.ProductoService
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogPedido : DialogFragment() {
    private lateinit var nombre : TextView
    private lateinit var descripcion : TextView
    private lateinit var precio : TextView
    private lateinit var obsv : EditText
    val productoService = ProductoService()
    val arrayEnCarrito = ArrayList<Producto>()
    private var listener : OnDialogListener? = null
    private lateinit var producto : Producto

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)

        val inflater = requireActivity().layoutInflater //inflo el layout del dialogo
        val view: View = inflater.inflate(R.layout.dialog_pedido, null)

        nombre = view.findViewById(R.id.nombreProducto)
        descripcion = view.findViewById(R.id.descripcion)
        precio = view.findViewById(R.id.precioProducto)
        obsv = view.findViewById(R.id.observaciones)

        getProductos()

        val bundle = arguments

        // Obtener el objeto Producto del bundle
        producto = bundle?.getSerializable("Producto") as Producto

        // Usar el objeto Producto para llenar los campos del diálogo
        if (producto != null) {
            nombre.text = producto.nombre
            descripcion.text = producto.descripcion
            precio.text = producto.precio.toString()
        }


        builder.setMessage("Confirmar pedido")
        builder.setView(view)
            .setPositiveButton("Añadir",
            DialogInterface.OnClickListener{ dialog, id ->
                onDismiss(dialog)
                Log.d("INFO:", "LO TENGO ${producto.toString()}")
            })
            .setNegativeButton("Volver",
                DialogInterface.OnClickListener { dialog, id ->
                    getDialog()?.cancel()
                })
        builder.create()

        return builder.create()
    }

    fun getProductos() {
        productoService.getProductos().enqueue(object: Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful)
                {
                    for (p1 in response.body()!!) {
                        arrayEnCarrito.add(p1)
                    }
                } else
                {
                    Log.d("TAG", "aaaaaa")
                }
            }override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                // something went completely south (like no internet connection)
                t.message?.let { Log.d("bbbbbb", it) }
            }
        })
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener?.addToCart(producto, obsv.text.toString())
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(getContext() is OnDialogListener?){
            listener = getContext() as OnDialogListener?
        }
    }
}
