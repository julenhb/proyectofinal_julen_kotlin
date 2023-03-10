package com.example.proyectofinal_julen.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.proyectofinal_julen.OnDialogListener
import com.example.proyectofinal_julen.OnFragmentEventListener
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.dialogs.DialogCarrito
import com.example.proyectofinal_julen.dialogs.DialogInsertarProducto
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.entity.Usuario
import com.example.proyectofinal_julen.fragments.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CartaActivity : AppCompatActivity(), OnDialogListener, OnFragmentEventListener,
    OnClickListener {

    private lateinit var userSettings: FloatingActionButton
    private lateinit var carrito: ImageButton
    private lateinit var vibrator: Vibrator
    val fragmentManager = supportFragmentManager
    val arrayCarrito = ArrayList<Producto>()
    val arrayObservaciones = ArrayList<String>()
    private lateinit var user : Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carta)

        userSettings = findViewById(R.id.btnSesion)
        carrito = findViewById(R.id.btnCarrito)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        userSettings.setOnClickListener(this)
        carrito.setOnClickListener(this)




        //COMPROBAMOS QUE HEMOS ENVIADO BIEN EL INTENT DESDE LA ANTERIOR ACTIVITY
        if (intent.extras != null) {
            if (intent.hasExtra("usuario")) {
                user = intent.getSerializableExtra("usuario") as Usuario

                //SI EL USUARIO QUE RECOGEMOS ES ADMINISTRADOR...
                if (user.admn == true) {
                    carrito.setImageResource(android.R.drawable.ic_input_add)
                    //Colocamos el fragmento de la lista de productos que est??n en el cat??logo
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    var miFragmento = ListaProductosAdmnFragment()
                    var miFragmento2 = InfoAdmin()
                    fragmentTransaction.replace(R.id.containerFragments, miFragmento)
                    fragmentTransaction.replace(R.id.containerFragments2, miFragmento2)
                    fragmentTransaction.commit()

                }//SI EL USUARIO QUE RECOGEMOS NO ES ADMINISTRADOR...
                else {
                    //Colocamos el fragmento deseado de la lista de productos
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    var miFragmento: Fragment
                    var miFragmento2: Fragment

                    if (user.kebabpoints >= 50 && user.admn == false) {
                        miFragmento = ListaProductosPremiumFragment()
                    } else {
                        miFragmento = ListaProductosFragment()
                    }
                    miFragmento2 = InfoCliente()

                    fragmentTransaction.replace(R.id.containerFragments, miFragmento)
                    Log.d("USER:", "$user")
                    val bundle = Bundle().apply {
                        putSerializable("usuario", user)
                    }
                    miFragmento2.arguments = bundle

                    fragmentTransaction.replace(R.id.containerFragments2, miFragmento2)
                    fragmentTransaction.commit()

                }
            }
            }

        }


        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.btnSesion -> {
                    AlertDialog.Builder(this)
                        .setTitle("Cerrar sesi??n")
                        .setMessage("??Ya te vas? :(")
                        .setPositiveButton("S??") { _, _ ->

                            intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .setNegativeButton("No", null)
                        .show()
                }

                R.id.btnCarrito -> {

                    //SI EL USUARIO QUE RECOGIMOS ES ADMIN...
                    if (user.admn == true) {
                        vibrator.vibrate(50)
                        val dialogInsert = DialogInsertarProducto()
                        val fragmentManager = supportFragmentManager
                        dialogInsert.show(fragmentManager, "Nuevo")

                    } else {
                        if (arrayCarrito.size >= 1) {
                            val bundle = Bundle()
                            bundle.putSerializable("arrayP", arrayCarrito)
                            bundle.putSerializable("arrayO", arrayObservaciones)
                            bundle.putSerializable("usuario", user)
                            bundle.putSerializable("usuarioAntiguo", user)
                            val dialog = DialogCarrito()
                            dialog.arguments = bundle
                            val fragmentManager = supportFragmentManager
                            dialog.show(fragmentManager, "Nuevo")
                        } else {
                            vibrator.vibrate(50)
                            Toast.makeText(
                                this,
                                "Tienes que a??adir por lo menos un producto a la bolsa",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        override fun onFragmentEvent(item: Producto) {

        }

        override fun addToCart(p1: Producto, obs: String) {
            arrayCarrito.add(p1)
            arrayObservaciones.add(obs)
            Log.d("CARRITO:", arrayCarrito.size.toString())
            Log.d("OBSERVACIONES:", arrayObservaciones.size.toString())
        }

    }