package com.example.proyectofinal_julen.entity

class Pedido {
    var id : Int
    var usuario : String
    var producto : String
    var observaciones : String

    constructor(id: Int, usuario: String, producto: String, observaciones: String) {
        this.id = id
        this.usuario = usuario
        this.producto = producto
        this.observaciones = observaciones
    }

    constructor(usuario: String, producto: String, observaciones: String) {
        this.id = 0
        this.usuario = usuario
        this.producto = producto
        this.observaciones = observaciones
    }


}