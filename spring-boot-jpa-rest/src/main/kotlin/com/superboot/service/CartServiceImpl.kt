package com.superboot.service

import com.superboot.dao.CartItemRepository
import com.superboot.dao.CartRepository
import com.superboot.entity.Cart
import com.superboot.entity.CartItem
import com.superboot.entity.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartServiceImpl: CartService{
    @Autowired
    lateinit var cartItemRepository: CartItemRepository
    @Autowired
    lateinit var cartRepository: CartRepository

    @Transactional
    override fun addToCart(product: Product, quantity: Int, code: String): Cart {
        var cart = cartRepository.findByCode(code)
        if(cart == null) {
            cart = Cart(null, code)
            cartRepository.save(cart)
        }
        var cartItem: CartItem? = cartItemRepository.findByProductIdAndCartId(product.id, cart.id)
        if (cartItem == null) {
            cartItem = CartItem(cart, product, quantity)
            cartItemRepository.save(cartItem)
        }else{
            cartItem.quantity += quantity
            cartItemRepository.save(cartItem)
        }

        var total = 0
        cart.cartItems.forEach{ cartItem ->
            var subTotal = cartItem.product.price * cartItem.quantity
            total += subTotal
        }
        cart.total = total
        cartRepository.save(cart)

        return cart
    }

    override fun getCartItems(code: String):Set<CartItem> {
        var cart = cartRepository.findByCode(code)
        return cart.cartItems
    }
}