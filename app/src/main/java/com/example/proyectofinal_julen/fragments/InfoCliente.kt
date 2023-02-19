package com.example.proyectofinal_julen.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.proyectofinal_julen.R
import com.example.proyectofinal_julen.entity.Usuario
import kotlin.random.Random


class InfoCliente : Fragment() {

    private lateinit var email: TextView
    private lateinit var kpoints: TextView
    private lateinit var sentence: TextView

    private lateinit var arrayFrases: Array<String>
    private val rangoInicial = 1
    private val rangoFinal = 20

    private lateinit var random: Random

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val user = arguments?.getSerializable("usuario") as Usuario
        val view = inflater.inflate(R.layout.fragment_info_cliente, container, false)
        email = view.findViewById(R.id.email)
        kpoints = view.findViewById(R.id.kebabpoints)
        sentence = view.findViewById(R.id.randomSentece)

        // Obtener el contexto
        val context = requireContext()

        // Inicializar el array de frases
        arrayFrases = resources.getStringArray(R.array.sentences)

        // Inicializar el random
        random = Random(context.hashCode())

        // Obtener una frase aleatoria del array
        val frase = random.nextInt(rangoFinal - rangoInicial + 1) + rangoInicial
        sentence.text = arrayFrases[frase]
        email.text = user.email
        kpoints.text = "kebabpoints + ${user.kebabpoints.toString()}"

        return view
    }

}






