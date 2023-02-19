package com.example.proyectofinal_julen.dao

import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.entity.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductoDAO {

    //Método para añadir un nuevo producto
    @POST("productos")
    fun createProducto(@Body producto : Producto): Call<Producto>

    // Método para obtener todos los productos
    @GET("productos")
    fun getProductos(): Call<List<Producto>>

    // Método para obtener los productos catalogados
    @GET("catalogado")
    fun getCatalogados(): Call<List<Producto>>

    // Método para obtener un producto por su id
    @GET("producto/{id}")
    fun getProductoById(@Path("id") id: Int): Call<Producto>

    @GET("producto/{nombre}")
    fun getProductoByNombre(@Path("nombre") nombre : String): Call<Producto>

}