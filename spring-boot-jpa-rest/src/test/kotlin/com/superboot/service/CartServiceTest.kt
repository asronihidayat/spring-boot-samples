package com.superboot.service

import com.superboot.dao.CartItemRepository
import com.superboot.dao.CartRepository
import com.superboot.entity.Cart
import com.superboot.entity.CartItem
import com.superboot.entity.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean


@RunWith(SpringRunner::class)
@SpringBootTest
class CartServiceTest{

    @Autowired
    lateinit var cartService: CartService

    @MockBean
    lateinit var cartRepository: CartRepository

    @MockBean
    lateinit var cartItemRepository: CartItemRepository

    @TestConfiguration
    internal class CartServiceImplTestContextConfiguration {
        @Bean
        fun cartService(): CartService {
            return CartServiceImpl()
        }
    }

    @Test
    fun `test cart getCartItems`()
    {
        val product1: Product = Product("SKU123", "Product Sample 123", "123.jpg", 10000, 10)
        val product2: Product = Product("SKU456", "Product Sample 456", "456.jpg", 10000, 10)

        val cart = Cart(null, "thisisuniquesessioncode")
        val cartItem1 = CartItem(cart, product1, 1)
        val cartItem2 = CartItem(cart, product2, 1)
        var cartItems: Set<CartItem> = emptySet()
        cartItems+=cartItem1
        cartItems+=cartItem2

        cart.cartItems = cartItems
        Mockito.`when`(cartRepository.findByCode("thisisuniquesessioncode")).thenReturn(cart)
        assertThat(cartService.getCartItems("thisisuniquesessioncode").count()).isEqualTo(2)
        assertThat(cartService.getCartItems("thisisuniquesessioncode")).isEqualTo(cartItems)
    }

    @Test
    fun `test #addToCart when cart and cartItem not exist`(){
        var product: Product = Product("SKU123", "Product Sample 123", "123.jpg", 10000, 10)
        var code = "thisissecretcode"
        var quantity = 1
        var cart = Cart(null, code)
        var cartItem =  CartItem(cart, product, quantity)

        Mockito.`when`(cartRepository.findByCode(code)).thenReturn(null)

        var cartResult = cartService.addToCart(product, quantity, code)

        assertThat(cartResult).isEqualTo(cart)
        verify(cartRepository, times(2)).save(cart)
        verify(cartItemRepository, times(1)).save(cartItem)
    }


    @Test
    fun `test #addToCart when cart exist and cartItem not exist`(){
        var product: Product = Product("SKU123", "Product Sample 123", "123.jpg", 10000, 10)
        var code = "thisissecretcode"
        var quantity = 1
        var cart = Cart(null, code)
        var cartItem =  CartItem(cart, product, quantity)

        Mockito.`when`(cartRepository.findByCode(code)).thenReturn(cart)

        var cartResult = cartService.addToCart(product, quantity, code)

        assertThat(cartResult).isEqualTo(cart)
        verify(cartRepository, times(1)).save(cart)
        verify(cartItemRepository, times(1)).save(cartItem)
    }

    @Test
    fun `test #addToCart when cart exist and cartItem exist`() {
        var product: Product = Product("SKU123", "Product Sample 123", "123.jpg", 10000, 10)
        var code = "thisissecretcode"
        var quantity = 1
        var cart = Cart(null, code)
        var cartItem = CartItem(cart, product, quantity)

        Mockito.`when`(cartRepository.findByCode(code)).thenReturn(cart)
        Mockito.`when`(cartItemRepository.findByProductIdAndCartId(product.id, cart.id)).thenReturn(cartItem)

        var cartResult = cartService.addToCart(product, quantity, code)

        assertThat(cartResult).isEqualTo(cart)
        assertThat(cartItem.quantity).isEqualTo(2)
        verify(cartRepository, times(1)).save(cart)
        verify(cartItemRepository, times(1)).save(cartItem)
    }
}

