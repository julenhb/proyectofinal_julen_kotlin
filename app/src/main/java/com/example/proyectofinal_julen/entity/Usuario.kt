package com.example.proyectofinal_julen.entity

class Usuario : java.io.Serializable{
    var id : Int
    var email : String
    var pwd : String
    var admn : Boolean
    var kebabpoints : Int

    constructor(id : Int, email : String, pwd : String, admn : Boolean, kebabpoints : Int){
        this.id = id
        this.email = email
        this.pwd = pwd
        this.admn = admn
        this.kebabpoints = kebabpoints
    }

    constructor(email: String, pwd: String){
        this.id = 0
        this.email = email
        this.pwd = pwd
        this.admn = false
        this.kebabpoints = 0
    }

    constructor(){
        this.id = 0
        this.email = ""
        this.pwd = ""
        this.admn = false
        this.kebabpoints = 0
    }

    override fun toString(): String {
        return "email: $email, password $pwd, kebabpoints: $kebabpoints "
    }
}