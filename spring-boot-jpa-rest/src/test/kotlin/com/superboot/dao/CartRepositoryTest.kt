package com.superboot.dao

import com.superboot.entity.Cart
import com.superboot.entity.CartItem
import com.superboot.entity.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class CartRepositoryTest{

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var cartRepository: CartRepository

    @Test
    fun testGetCartByCode()
    {
        val product1: Product = Product("SKU123", "Product Sample 123", "123.jpg", 10000, 10)
        val product2: Product = Product("SKU456", "Product Sample 456", "456.jpg", 10000, 10)
        entityManager.persistAndFlush(product1)
        entityManager.persistAndFlush(product2)

        val cart = Cart(null, "thisisuniquesessioncode")
        val cartItem1 = CartItem(cart, product1, 1)
        val cartItem2 = CartItem(cart, product2, 1)
        var cartItems = emptySet<CartItem>()
        cartItems+=cartItem1
        cartItems+=cartItem2
        cart.cartItems = cartItems
        entityManager.persistAndFlush(cart)

        val cartByCode = cartRepository.findByCode("thisisuniquesessioncode")

        assertThat(cartByCode).isEqualTo(cart)
        assertThat(cartByCode.cartItems.count()).isEqualTo(2)
        assertThat(cartByCode.cartItems.first().product).isEqualTo(product1)
        assertThat(cartByCode.cartItems.last().product).isEqualTo(product2)
        assertThat(cartByCode.cartItems.first()).isEqualTo(cartItem1)
        assertThat(cartByCode.cartItems.last()).isEqualTo(cartItem2)
    }
}

