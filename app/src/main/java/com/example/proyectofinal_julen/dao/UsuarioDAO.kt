package com.example.proyectofinal_julen.dao

import com.example.proyectofinal_julen.entity.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioDAO {
    // Método para crear un usuario
    @POST("usuarios")
    fun createUsuario(@Body usuario: Usuario): Call<Usuario>

    // Método para obtener todos los usuarios
    @GET("usuarios")
    fun getUsuarios(): Call<List<Usuario>>

    // Método para obtener un usuario por id
    @GET("usuario/{id}")
    fun getUsuarioById(@Path("id") id: Int): Call<Usuario>

    //Método para obtener un usuario por email
    @GET("usuario/email/{email}")
    fun getUsuarioByEmail(@Path("email") email: String): Call<Usuario>

    @PUT("usuario/{id}")
    fun updateUsuario(@Path("id") id: Int, @Body usuario: Usuario): Call<Usuario>
}