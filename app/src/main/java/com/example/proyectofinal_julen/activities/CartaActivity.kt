package com.example.proyectofinal_julen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import com.example.proyectofinal_julen.OnFragmentEventListener
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.Producto
import com.example.proyectofinal_julen.entity.Usuario
import com.example.proyectofinal_julen.fragments.InfoCliente
import com.example.proyectofinal_julen.fragments.ListaProductosAdmnFragment
import com.example.proyectofinal_julen.fragments.ListaProductosFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CartaActivity : AppCompatActivity(), OnFragmentEventListener, OnClickListener {

    private lateinit var userSettings: FloatingActionButton
    val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carta)

        userSettings = findViewById(R.id.btnSesion)
        userSettings.setOnClickListener(this)

        //COMPROBAMOS QUE HEMOS ENVIADO BIEN EL INTENT DESDE LA ANTERIOR ACTIVITY
        if (intent.extras != null) {
            if (intent.hasExtra("usuario")) {
                val user = intent.getSerializableExtra("usuario") as Usuario

                //SI EL USUARIO QUE RECOGEMOS ES ADMINISTRADOR...
                if (user.admn == true) {
                    //Colocamos el fragmento de la lista de productos que están en el catálogo
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    var miFragmento: Fragment
                    miFragmento = ListaProductosAdmnFragment()
                    fragmentTransaction.replace(R.id.containerFragments, miFragmento)
                    fragmentTransaction.commit()
                }//SI EL USUARIO QUE RECOGEMOS NO ES ADMINISTRADOR...
                else {
                    //Colocamos el fragmento deseado de la lista de productos
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    var miFragmento: Fragment
                    var miFragmento2 : Fragment
                    miFragmento = ListaProductosFragment()
                    miFragmento2 = InfoCliente()

                    fragmentTransaction.replace(R.id.containerFragments, miFragmento)

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
        when(v?.id){
            R.id.btnSesion ->{

            }
        }
    }

    override fun onFragmentEvent(item: Producto) {

    }
}