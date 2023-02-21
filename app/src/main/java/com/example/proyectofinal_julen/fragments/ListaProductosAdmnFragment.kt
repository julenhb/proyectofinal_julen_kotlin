package com.example.proyectofinal_julen.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import com.example.proyectofinal_julen.AdaptadoresPersonalizados.AdaptadorAdmn
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.AdaptadoresPersonalizados.AdaptadorProducto
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.service.ProductoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaProductosAdmnFragment : Fragment(), OnItemClickListener {
    val arrayProductos = ArrayList<Producto>()
    private lateinit var listProductos : ListView
    val productoService = ProductoService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_lista_productos_admn, container, false)

        //Llamamos al método getProductos para llenar el arrayVehículos
        // que vamos a pasar al adaptador del listView
        getProductos()

        listProductos = view.findViewById(R.id.lista)

        listProductos.setOnItemClickListener(this)

        return view
    }

    fun getProductos() {
        productoService.getProductos().enqueue(object: Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful)
                {
                    for (p1 in response.body()!!) {
                        arrayProductos.add(p1)
                    }
                    val adaptadorProductos = AdaptadorAdmn(requireContext(), arrayProductos)
                    listProductos.adapter = adaptadorProductos
                    Log.d("TAG", "insertando usuarios")
                } else
                {
                    Log.d("TAG", "aaaaaa")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                // something went completely south (like no internet connection)
                t.message?.let { Log.d("bbbbbb", it) }
            }
        })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}