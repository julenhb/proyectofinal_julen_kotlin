package com.example.proyectofinal_julen.entity

class Producto : java.io.Serializable {
    var id : Int
    var nombre : String
    var precio : Double
    var catalogado : Boolean
    var descripcion : String

    constructor(id : Int, nombre : String, precio : Double, descripcion : String,  catalogado : Boolean){
        this.id = id
        this.nombre = nombre
        this.precio = precio
        this.descripcion = descripcion
        this.catalogado = catalogado

    }

    constructor(nombre : String, precio : Double, descripcion : String, catalogado : Boolean){
        this.id = 0
        this.nombre = nombre
        this.precio = precio
        this.descripcion = descripcion
        this.catalogado = catalogado
    }

    constructor(){
        this.id = 0
        this.nombre = ""
        this.precio = 0.0
        this.descripcion = ""
        this.catalogado = true
    }

}