package com.example.proyectofinal_julen.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.proyectofinal_julen.OnDialogListener
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.activities.CartaActivity
import com.example.proyectofinal_julen.entity.AdaptadorCarrito
import com.example.proyectofinal_julen.entity.Pedido
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.entity.Usuario
import com.example.proyectofinal_julen.fragments.InfoCliente
import com.example.proyectofinal_julen.service.PedidoService
import com.example.proyectofinal_julen.service.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogCarrito : DialogFragment() {

    private lateinit var lista: ListView
    private lateinit var precioFinal: TextView
    private var p1 = Producto()
    private var precio = 0.00
    private var usuarioActualizado = Usuario()

    private var arrayPedidos = ArrayList<Pedido>()
    private var pedido = Pedido()
    private var pedidoRealizado = false

    private var usuarioService = UsuarioService()
    private var pedidoService = PedidoService()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)

        val inflater = requireActivity().layoutInflater //inflo el layout del dialogo
        val view: View = inflater.inflate(R.layout.dialog_carrito, null)

        lista = view.findViewById(R.id.lista)
        precioFinal = view.findViewById(R.id.precioTotal)

        val bundle = arguments
        val arrayProductos = bundle?.getSerializable("arrayP") as ArrayList<Producto>
        val arrayObservaciones = bundle.getSerializable("arrayO") as ArrayList<String>
        usuarioActualizado = bundle.getSerializable("usuario") as Usuario


        val adaptadorLista = AdaptadorCarrito(requireContext(), arrayProductos, arrayObservaciones)
        lista.adapter = adaptadorLista

        for (i in arrayProductos) {
            p1 = i
            if (p1 != null) {
                precio += p1.precio
            }
        }

        var kpoints = 5 * (arrayProductos.size)

        precioFinal.text =
            ("Total a pagar: " + precio.toString() + " €" + "\n ¡Obtendrás $kpoints kebabpoints!")

        builder.setMessage("Confirmar pedido")
        dialog?.setContentView(R.layout.dialog_carrito)
        dialog?.getWindow()?.setLayout(200, ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.setView(view)
            .setPositiveButton("Confirmar",
                DialogInterface.OnClickListener { dialog, id ->
                    usuarioActualizado.kebabpoints = usuarioActualizado.kebabpoints + kpoints
                    updateUsuario()
                    for (i in arrayProductos) {
                        val obv = arrayObservaciones[arrayProductos.indexOf(i)]
                        pedido = Pedido(usuarioActualizado.email, i.nombre, obv)
                        pedidoService.createPedido(pedido)
                    }
                    arrayProductos.clear()
                    arrayObservaciones.clear()
                    onDismiss(dialog)
                    /*val intent = Intent(requireContext(), CartaActivity::class.java)
                    intent.putExtra("usuarioActualizado", usuarioActualizado)
                    startActivity(intent)
                    requireActivity().finish()*/

                    Toast.makeText(
                        requireContext(),
                        "¡Pedido realizado con éxito! Que aproveche ;)",
                        Toast.LENGTH_LONG
                    ).show()


                })
            .setNegativeButton("Descartar",
                DialogInterface.OnClickListener { dialog, id ->
                    arrayProductos.clear()
                    arrayObservaciones.clear()
                    getDialog()?.cancel()
                })
            .setNegativeButton("Volver",
                DialogInterface.OnClickListener { dialog, id ->
                    getDialog()?.cancel()
                })
        builder.create()

        return builder.create()
    }


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
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val miFragmento = InfoCliente()
        fragmentTransaction.replace(R.id.containerFragments2, miFragmento)
        val bundle = Bundle().apply {
            putSerializable("usuario", usuarioActualizado)
        }
        miFragmento.arguments = bundle
        fragmentTransaction.commit()
    }
}





