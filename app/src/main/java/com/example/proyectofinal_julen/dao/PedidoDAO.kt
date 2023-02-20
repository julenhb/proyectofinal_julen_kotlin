package com.example.proyectofinal_julen.dao

import com.example.proyectofinal_julen.entity.Pedido
import com.example.proyectofinal_julen.entity.Producto
import retrofit2.Call
import retrofit2.http.*

interface PedidoDAO {

    //Método para añadir un nuevo producto
    @POST("pedidos")
    fun createPedido(@Body pedido : Pedido): Call<Pedido>

    // Método para obtener todos los productos
    @GET("pedidos")
    fun getPedidos(): Call<List<Pedido>>

    // Método para obtener un producto por su id
    @GET("pedido/{id}")
    fun getPedidoById(@Path("id") id: Int): Call<Pedido>



}