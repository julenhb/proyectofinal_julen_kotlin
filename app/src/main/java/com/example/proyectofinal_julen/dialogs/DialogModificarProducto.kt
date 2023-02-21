package com.example.proyectofinal_julen.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.proyectofinal_julen.AdaptadoresPersonalizados.AdaptadorCarrito
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.Pedido
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.entity.Usuario
import com.example.proyectofinal_julen.fragments.InfoCliente
import com.example.proyectofinal_julen.fragments.ListaProductosAdmnFragment
import com.example.proyectofinal_julen.fragments.ListaProductosPremiumFragment
import com.example.proyectofinal_julen.service.PedidoService
import com.example.proyectofinal_julen.service.ProductoService
import com.example.proyectofinal_julen.service.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogModificarProducto : DialogFragment() {
    private lateinit var nombre : EditText
    private lateinit var precio : EditText
    private lateinit var descripcion : EditText
    private lateinit var premium : CheckBox
    private lateinit var hacerMenu : CheckBox
    private var catalogado = false


    private var productoService = ProductoService()
    private var producto = Producto()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(context)

        val inflater = requireActivity().layoutInflater //inflo el layout del dialogo
        val view: View = inflater.inflate(R.layout.dialog_modificar_producto, null)

        nombre = view.findViewById(R.id.nombreProducto)
        precio = view.findViewById(R.id.precioProducto)
        descripcion = view.findViewById(R.id.descripcion)
        premium = view.findViewById(R.id.catalogadoCheck)
        hacerMenu = view.findViewById(R.id.menuCheck)


        builder.setMessage("Insertar nuevo producto")
        dialog?.setContentView(R.layout.dialog_carrito)
        dialog?.getWindow()?.setLayout(200, ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.setView(view)
            .setPositiveButton("Insertar",
                DialogInterface.OnClickListener { dialog, id ->


                    onDismiss(dialog)
                    Toast.makeText(
                        requireContext(),
                        "Producto insertado, podrás verlo en la lista",
                        Toast.LENGTH_LONG
                    ).show()


                })
            .setNegativeButton("Descartar",
                DialogInterface.OnClickListener { dialog, id ->
                    getDialog()?.cancel()
                })
            .setNegativeButton("Volver",
                DialogInterface.OnClickListener { dialog, id ->
                    getDialog()?.cancel()
                })
        builder.create()

        return builder.create()
    }


    /*
    fun updateUsuario() {
        usuarioService.updateUsuario(usuarioActualizado.id, usuarioActualizado)
            .enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        // El usuario fue actualizado correctamente
                        usuarioActualizado = response.body()!!
                    } else {
                        // Ocurrió un error al actualizar el usuario
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    // Ocurrió un error al realizar la llamada al servicio
                }
            })
    }*/

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(premium.isChecked == true){
            catalogado = true
        }

        if(hacerMenu.isEnabled == true && hacerMenu.isVisible == true) {
            if (hacerMenu.isChecked == true) {
                producto = Producto(
                    "Menú " + nombre.text.toString(),
                    precio.text.toString().toDouble(),
                    descripcion.text.toString(),
                    catalogado
                )
            } else {
                producto = Producto(
                    nombre.text.toString(),
                    precio.text.toString().toDouble(),
                    descripcion.text.toString(),
                    catalogado
                )
            }
        } else {
            producto = Producto(
                nombre.text.toString(),
                precio.text.toString().toDouble(),
                descripcion.text.toString(),
                catalogado
            )
        }
        productoService.createProducto(producto)
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val miFragmento = ListaProductosAdmnFragment()
        fragmentTransaction.replace(R.id.containerFragments, miFragmento)
        fragmentTransaction.commit()
    }
}