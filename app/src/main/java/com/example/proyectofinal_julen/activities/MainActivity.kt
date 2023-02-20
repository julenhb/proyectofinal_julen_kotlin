package com.example.proyectofinal_julen.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.Pedido
import com.example.proyectofinal_julen.entity.Usuario
import com.example.proyectofinal_julen.service.UsuarioService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var email: EditText
    private lateinit var pwd: EditText
    private lateinit var login: Button
    private lateinit var signUp: TextView
    private lateinit var lblSign: TextView
    private lateinit var lblVolver: TextView
    private lateinit var btnVolver: TextView
    val listaUsuarios = ArrayList<Usuario>()
    val usuarioService = UsuarioService()
    private var user = Usuario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.email)
        pwd = findViewById(R.id.password)
        login = findViewById(R.id.btnLogin)
        signUp = findViewById(R.id.btnSignUp)
        lblSign = findViewById(R.id.lblSignUp)
        lblVolver = findViewById(R.id.lblVolver)
        btnVolver = findViewById(R.id.btnVolver)

        listaUsuarios.clear()
        getUsuarios()

        //Este método se utiliza para subrayar texto
        signUp.setPaintFlags(signUp.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        btnVolver.setPaintFlags(signUp.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        login.setOnClickListener(this)
        signUp.setOnClickListener(this)
        btnVolver.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                if (login.text.equals(getString(R.string.log_in))) {
                    var usu = getUsuarioByEmail(email.text.toString())
                    Log.d(
                        "USUARIO",
                        "${usu.email}, ${usu.pwd}, ${usu.kebabpoints}, ${usu.id}, ${usu.admn}"
                    )
                    if (comprobarLogin(usu, email.text.toString(), pwd.text.toString()) == true) {
                        intent = Intent(this, CartaActivity::class.java)
                        intent.putExtra("usuario", usu)
                        startActivity(intent)

                        Toast.makeText(
                            this,
                            "Buenas amig@ ${nombre(usu.email)}",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        finish()
                    } else {
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                    }


                } else if (login.text.equals(getString(R.string.sign_up))) {
                    var usu = getUsuarioByEmail(email.text.toString())
                    if (comprobarRegistro(usu, email.text.toString()) == true) {
                        Toast.makeText(
                            this,
                            "El email que has usado ya está registrado",
                            Toast.LENGTH_LONG
                        ).show()
                        email.setTextColor(
                            ContextCompat.getColor(
                                this,
                                android.R.color.holo_red_light
                            )
                        )
                    } else {
                        var usu1 = Usuario(email.text.toString(), pwd.text.toString())
                        usuarioService.createUsuario(usu1)
                        intent = Intent(this, CartaActivity::class.java)
                        intent.putExtra("usuario", usu1)
                        startActivity(intent)
                        Toast.makeText(
                            this,
                            "Buenas amig@ ${nombre(usu1.email)}",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    }

                }
            }

            /*
            *
            * LA ÚNICA FUNCIONALIDAD QUE TENDRÁN LOS SIGUIENTES DOS BOTONES SERÁ LA DE
            * ALTERNAR EL FORMULARIO DE LOGIN A REGISTRO Y VICEVERSA, EL BOTÓN QUE HACE LA ACCIÓN
            * ES EL DE ARRIBA
             */

            R.id.btnSignUp -> {

                //CAMBIO AL REGISTRO
                lblSign.isVisible = false
                signUp.isVisible = false
                signUp.isEnabled = false

                lblVolver.isVisible = true    //Hago visibles los botones para volver al login
                btnVolver.isVisible = true
                btnVolver.isEnabled = true

                login.setText(getString(R.string.sign_up))    //cambio el botón de login a registro
            }

            R.id.btnVolver -> {

                //CAMBIO AL LOGIN
                lblVolver.isVisible = false
                btnVolver.isVisible = false
                btnVolver.isEnabled = false

                lblSign.isVisible = true      //Hago visibles los botones del registro
                signUp.isVisible = true
                signUp.isEnabled = true

                login.setText(getString(R.string.log_in)) //cambio el botón de registro a login
            }

        }
    }

/*
    fun getUsuarioByEmail(email: String) {
        usuarioService.getUsuarioByEmail(email).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    user = response.body()!!
                } else {
                    Log.d("TAG", "aaaaaa")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                // something went completely south (like no internet connection)
                t.message?.let { Log.d("bbbbb", it) }
            }
        })
    }*/

    fun getUsuarioByEmail(email: String): Usuario {
        for (i in listaUsuarios) {
            if (i.email == email) {
                user = i
                break
            }
        }
        return user
    }


    fun getUsuarios() {
        usuarioService.getUsuarios().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    for (usu in response.body()!!) {
                        Log.d(
                            "USUARIO",
                            "${usu.email}, ${usu.pwd}, ${usu.kebabpoints}, ${usu.id}, ${usu.admn}"
                        )
                        listaUsuarios.add(usu)
                    }

                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                // something went completely south (like no internet connection)
                t.message?.let { Log.d("bbbbbb", it) }
            }
        })
    }


    fun comprobarLogin(usu: Usuario, email: String, pwd: String): Boolean {
        if (usu.email == email && usu.pwd == pwd) {
            return true
        } else {
            return false
        }
    }

    fun comprobarRegistro(usu: Usuario, email: String): Boolean {
        if (usu.email == email) {
            return true
        } else {
            return false
        }
    }

    fun nombre(email: String): String {
        var indexArroba = email.indexOf('@')
        return email.substring(0, indexArroba)
    }
}