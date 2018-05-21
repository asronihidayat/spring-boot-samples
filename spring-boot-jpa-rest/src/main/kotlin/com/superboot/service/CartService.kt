package com.superboot.service

import com.superboot.entity.Cart
import com.superboot.entity.CartItem
import com.superboot.entity.Product


interface CartService{
    fun addToCart(product: Product, quantity: Int, code: String):Cart
    fun getCartItems(code: String):Set<CartItem>
}

