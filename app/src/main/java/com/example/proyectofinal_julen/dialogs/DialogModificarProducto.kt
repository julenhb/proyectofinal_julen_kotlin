package com.example.proyectofinal_julen.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.fragment.app.DialogFragment
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.fragments.ListaProductosAdmnFragment
import com.example.proyectofinal_julen.service.ProductoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogModificarProducto : DialogFragment(){
    private lateinit var nombreP: TextView
    private lateinit var precioP: TextView
    private lateinit var descripcionP: TextView
    private lateinit var premiumP: CheckBox
    private lateinit var hacerMenu: CheckBox
    var arrayP = ArrayList<Producto>()
    private var catalogado = false
    var producto = Producto()
    var productoActualizado = Producto()


    private var productoService = ProductoService()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(context)

        val inflater = requireActivity().layoutInflater //inflo el layout del dialogo
        val view: View = inflater.inflate(R.layout.dialog_insertar_producto, null)

        val bundle = arguments
        producto = bundle?.getSerializable("producto") as Producto
        arrayP = bundle.getSerializable("array") as ArrayList<Producto>

        nombreP = view.findViewById(R.id.nombreProducto)
        precioP = view.findViewById(R.id.precioProducto)
        descripcionP = view.findViewById(R.id.descripcion)
        premiumP = view.findViewById(R.id.catalogadoCheck)
        hacerMenu = view.findViewById(R.id.menuCheck)

        nombreP.isEnabled = false
        descripcionP.isEnabled = false
        precioP.isEnabled = false

        nombreP.text = producto.nombre
        precioP.text = producto.precio.toString()
        descripcionP.text = producto.descripcion
        if (producto.catalogado == true){
            premiumP.isChecked = true
        }





        builder.setMessage("Modificar existente")
        dialog?.setContentView(R.layout.dialog_carrito)
        dialog?.getWindow()?.setLayout(200, ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.setView(view)
            .setPositiveButton("Modificar",
                DialogInterface.OnClickListener { dialog, id ->

                    if (premiumP.isChecked == true) {
                        catalogado = true
                    }
                    productoActualizado = Producto(producto.id, nombreP.text.toString(), precioP.text.toString().toDouble(), descripcionP.toString(), catalogado)
                    updateProducto()
                    onDismiss(dialog)
                    Toast.makeText(
                        requireContext(),
                        "El producto se modific?? con ??xito",
                        Toast.LENGTH_LONG
                    ).show()


                })
            .setNegativeButton("Volver",
                DialogInterface.OnClickListener { dialog, id ->
                    getDialog()?.cancel()
                })


        builder.create()

        return builder.create()

    }



    fun updateProducto() {
        productoService.updateProducto(productoActualizado.id, productoActualizado)
            .enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    if (response.isSuccessful) {
                        // El usuario fue actualizado correctamente
                        producto = response.body()!!
                    } else {
                        // Ocurri?? un error al actualizar el usuario
                    }
                }

                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    // Ocurri?? un error al realizar la llamada al servicio
                }
            })
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val miFragmento = ListaProductosAdmnFragment()
        fragmentTransaction.replace(R.id.containerFragments, miFragmento)
        fragmentTransaction.commit()
    }




}