package com.superboot.service

import com.superboot.entity.Product
import java.util.*

interface ProductService{
    fun getAllProducts(): List<Product>
    fun save(product: Product): Product
    fun getOne(id: Long): Optional<Product>
    fun remove(product: Product)
}
