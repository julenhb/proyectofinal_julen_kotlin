package com.example.proyectofinal_julen.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.proyectofinal_julen.AdaptadoresPersonalizados.AdaptadorAdmn
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.dialogs.DialogInsertarProducto
import com.example.proyectofinal_julen.dialogs.DialogModificarProducto
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.service.ProductoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaProductosAdmnFragment : Fragment() {
    val arrayProductos = ArrayList<Producto>()
    private lateinit var listProductos: ListView
    val productoService = ProductoService()
    private var p1 = Producto()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lista_productos_admn, container, false)

        //Llamamos al método getProductos para llenar el arrayVehículos
        // que vamos a pasar al adaptador del listView
        getProductos()

        listProductos = view.findViewById(R.id.lista)

        listProductos.onItemSelectedListener
        registerForContextMenu(listProductos)

        return view
    }

    fun getProductos() {
        productoService.getProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                if (response.isSuccessful) {
                    for (p1 in response.body()!!) {
                        arrayProductos.add(p1)
                    }
                    val adaptadorProductos = AdaptadorAdmn(requireContext(), arrayProductos)
                    listProductos.adapter = adaptadorProductos
                    Log.d("TAG", "insertando usuarios")
                } else {
                    Log.d("TAG", "aaaaaa")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                // something went completely south (like no internet connection)
                t.message?.let { Log.d("bbbbbb", it) }
            }
        })
    }

    override fun registerForContextMenu(view: View) {
        super.registerForContextMenu(listProductos)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.menu_du, menu)

        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val selectedItem = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = selectedItem.position

        // Verificar que la posición sea válida
        if (posicion >= 0 && posicion < arrayProductos.size) {
            when (item.itemId) {
                R.id.delete -> {
                    p1 = arrayProductos[posicion]
                    deleteProducto(p1)
                    actualizarLista()
                    Toast.makeText(requireContext(), "Producto eliminado: " + p1.toString(), Toast.LENGTH_LONG).show()
                }

                R.id.update -> {
                    p1 = arrayProductos[posicion]
                    val dialog = DialogModificarProducto()
                    val fragmentManager = parentFragmentManager
                    val bundle = Bundle()
                    bundle.putSerializable("producto", p1)
                    bundle.putSerializable("array", arrayProductos)
                    dialog.arguments = bundle
                    dialog.show(fragmentManager, "Nuevo")

                }
            }
        } else {
            Log.e("TAG", "Posición inválida: $posicion")
        }


        return super.onContextItemSelected(item)

    }



    fun deleteProducto(p1 : Producto){
        productoService.deleteProductoById(p1.id).enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                if (response.isSuccessful) {
                    // Eliminación exitosa

                    Toast.makeText(requireContext(), "Producto eliminado: " + p1.toString(), Toast.LENGTH_LONG).show()
                } else {
                    //no sé por que sale por aquí pero funciona un milagro navideño supongo
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {

            }
        })
    }

    fun actualizarLista(){
        val fragmentManager = parentFragmentManager
        val miFragment = ListaProductosAdmnFragment()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerFragments, miFragment)
        fragmentTransaction.commit()
    }
}
