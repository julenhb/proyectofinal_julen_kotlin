package com.example.proyectofinal_julen

import com.example.proyectofinal_julen.entity.Producto

interface OnDialogListener {

    fun addToCart(p1 : Producto, obsv : String)
}