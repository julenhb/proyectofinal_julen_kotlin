package com.example.proyectofinal_julen.service

import android.util.Log
import com.example.proyectofinal_julen.dao.ProductoDAO
import com.example.proyectofinal_julen.dao.UsuarioDAO
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.entity.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductoService {
    // Instancia de Retrofit
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.43.69:8080/ServidorREST/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Método para crear un usuario
    fun createProducto(producto : Producto) {
        getRetrofit().create(ProductoDAO::class.java).createProducto(producto).enqueue(object :
            Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                Log.d("TAG", "CALL: $call")
                Log.d("TAG", "RESPONSE: $response")
            }
            override fun onFailure(call: Call<Producto>, t: Throwable) {
                // Procesar error en la petición
                Log.d("TAG", "Error")
            }
        })
    }

    //Método para ver todos los usuarios
    fun getProductos() : Call<List<Producto>> {
        return getRetrofit().create(ProductoDAO::class.java).getProductos()
    }

    fun getPremium() : Call<List<Producto>> {
        return getRetrofit().create(ProductoDAO::class.java).getProductos()
    }

    //Método para ver todos los usuarios
    fun getCatalogados() : Call<List<Producto>> {
            return getRetrofit().create(ProductoDAO::class.java).getCatalogados()
    }

    fun getProductoById(id : Int) : Call<Producto> {
        return getRetrofit().create(ProductoDAO::class.java).getProductoById(id)
    }

    fun getProductoByNombre(nombre : String) : Call<Producto> {
        return getRetrofit().create(ProductoDAO::class.java).getProductoByNombre(nombre)
    }

    fun deleteProductoById(id : Int) : Call<Producto> {
        return getRetrofit().create(ProductoDAO::class.java).deleteProductoById(id)
    }

    fun updateProducto(id : Int, producto: Producto) : Call <Producto>{
        return getRetrofit().create(ProductoDAO::class.java).updateProducto(id, producto)
    }

}