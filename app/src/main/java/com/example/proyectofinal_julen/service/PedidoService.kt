package com.example.proyectofinal_julen.service

import android.util.Log
import com.example.proyectofinal_julen.dao.PedidoDAO
import com.example.proyectofinal_julen.dao.ProductoDAO
import com.example.proyectofinal_julen.entity.Pedido
import com.example.proyectofinal_julen.entity.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PedidoService {
    // Instancia de Retrofit
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.43.69:8080/ServidorREST/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Método para crear un usuario
    fun createPedido(pedido: Pedido) {
        getRetrofit().create(PedidoDAO::class.java).createPedido(pedido).enqueue(object :
            Callback<Pedido> {
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                Log.d("TAG", "CALL: $call")
                Log.d("TAG", "RESPONSE: $response")
            }

            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                // Procesar error en la petición
                Log.d("TAG", "Error")
            }
        })
    }

    //Método para ver todos los usuarios
    fun getPedidos(): Call<List<Pedido>> {
        return getRetrofit().create(PedidoDAO::class.java).getPedidos()
    }

    fun getProductoById(id : Int): Call<Pedido> {
        return getRetrofit().create(PedidoDAO::class.java).getPedidoById(id)
    }
}