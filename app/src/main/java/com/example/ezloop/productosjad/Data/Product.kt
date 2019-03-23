package com.example.ezloop.productosjad.Data

data class Product(
    var id: Long,
    var name: String,
    var desc: String,
    var imgID: Int,
    var price: Float,
    var quantity: Int)