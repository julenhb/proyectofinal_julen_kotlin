package com.example.proyectofinal_julen.service

import android.util.Log
import com.example.proyectofinal_julen.dao.UsuarioDAO
import com.example.proyectofinal_julen.entity.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsuarioService {
    // Instancia de Retrofit
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.43.69:8080/ServidorREST/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Método para crear un usuario
    fun createUsuario(usuario : Usuario) {
        getRetrofit().create(UsuarioDAO::class.java).createUsuario(usuario).enqueue(object :
            Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                Log.d("TAG", "CALL: $call")
                Log.d("TAG", "RESPONSE: $response")
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                // Procesar error en la petición
                Log.d("TAG", "Error")
            }
        })
    }

    //Método para ver todos los usuarios
    fun getUsuarios() : Call<List<Usuario>> {
        return getRetrofit().create(UsuarioDAO::class.java).getUsuarios()
    }



    fun getUsuarioById(id : Int) : Call <Usuario>{
        return getRetrofit().create(UsuarioDAO::class.java).getUsuarioById(id)
    }

    fun getUsuarioByEmail(email : String) : Call <Usuario>{
        return getRetrofit().create(UsuarioDAO::class.java).getUsuarioByEmail(email)
    }
}