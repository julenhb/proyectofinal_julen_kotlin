package com.example.proyectofinal_julen.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.proyectofinal_julen.AdaptadoresPersonalizados.AdaptadorPedidos
import com.example.proyectofinal_julen.AdaptadoresPersonalizados.AdaptadorProducto
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.Pedido
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.entity.Usuario
import com.example.proyectofinal_julen.service.PedidoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random


class InfoAdmin : Fragment() {

    private lateinit var lista : ListView
    var pedidoService = PedidoService()
    var arrayPedidos = ArrayList<Pedido>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_info_admin, container, false)

        lista = view.findViewById(R.id.lista)

        getPedidos()

        return view




    }

    fun getPedidos() {
        pedidoService.getPedidos().enqueue(object: Callback<List<Pedido>> {
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>) {
                if (response.isSuccessful)
                {
                    for (p1 in response.body()!!) {
                        arrayPedidos.add(p1)
                    }
                    val adaptadorProductos = AdaptadorPedidos(requireContext(), arrayPedidos)
                    lista.adapter = adaptadorProductos
                    Log.d("TAG", "insertando usuarios")
                } else
                {
                    Log.d("TAG", "aaaaaa")
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                // something went completely south (like no internet connection)
                t.message?.let { Log.d("bbbbbb", it) }
            }
        })
    }



}