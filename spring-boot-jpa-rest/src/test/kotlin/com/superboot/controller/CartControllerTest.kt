package com.superboot.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.superboot.SuperbootApplication
import com.superboot.dao.CartItemRepository
import com.superboot.dto.CartDto
import com.superboot.dto.CartItemListDto
import com.superboot.dto.ResponseDto
import com.superboot.entity.Cart
import com.superboot.entity.CartItem
import com.superboot.entity.Product
import com.superboot.service.CartService
import com.superboot.service.ProductService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [SuperbootApplication::class])
@AutoConfigureMockMvc
@TestPropertySource(locations = arrayOf("classpath:application.properties"))
class CartControllerTest
{

    @MockBean
    lateinit var cartService: CartService

    @MockBean
    lateinit var cartItemRepository: CartItemRepository

    @MockBean
    lateinit var productService: ProductService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `get all cart items`()
    {

        val product1: Product = Product("SKU123", "Product Sample 123", "123.jpg", 10000, 10)
        val product2: Product = Product("SKU456", "Product Sample 456", "456.jpg", 10000, 10)

        val code = "thisisuniquesessioncode"
        val cart = Cart(null, code)
        val cartItem1 = CartItem(cart, product1, 1)
        val cartItem2 = CartItem(cart, product2, 1)
        var cartItems = emptySet<CartItem>()
        cartItems+=cartItem1
        cartItems+=cartItem2
        cart.cartItems = cartItems

        given(cartService.getCartItems(code)).willReturn(cartItems)

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/${code}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(CartItemListDto("Success", cartItems))))
        verify(cartService, Mockito.times(1)).getCartItems(code)
    }

    @Test
    fun `test add to cart`()
    {
        val code = "secret"
        val product: Product = Product("SKU123", "Product Sample 123", "123.jpg", 10000, 10)
        product.id = 1
        val cart = Cart(null, code)
        val cartItem1 = CartItem(cart, product, 1)
        var cartItems = emptySet<CartItem>()
        cartItems+=cartItem1
        cart.cartItems = cartItems

        val products = Optional.of(product)
        given(productService.getOne(1)).willReturn(products)
        given(cartService.addToCart(product, 1, code)).willReturn(cart)

        mockMvc.perform(MockMvcRequestBuilders.post("/carts")
                .param("productId", "1")
                .param("quantity", "1")
                .param("code", code))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(CartDto("Success", cart))))

        verify(cartService, Mockito.times(1)).addToCart(product, 1, code)
    }

    @Test
    fun `delete cart item`()
    {
        val code = "secret"
        val product: Product = Product("SKU123", "Product Sample 123", "123.jpg", 10000, 10)
        product.id = 1
        val cart = Cart(null, code)
        cart.id = 1
        val cartItem = CartItem(cart, product, 1)

        given(cartItemRepository.findByProductIdAndCartId(product.id, cart.id)).willReturn(cartItem)

        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/remove")
                .param("productId", "1")
                .param("cartId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ResponseDto("Success"))))

        verify(cartItemRepository, Mockito.times(1)).findByProductIdAndCartId(product.id, cart.id)
        verify(cartItemRepository, Mockito.times(1)).delete(cartItem)
    }

    @Test
    fun `update cart item`()
    {
        val code = "secret"
        val product: Product = Product("SKU123", "Product Sample 123", "123.jpg", 10000, 10)
        product.id = 1
        val cart = Cart(null, code)
        cart.id = 1
        val cartItem = CartItem(cart, product, 1)
        cartItem.id = 1

        given(cartItemRepository.findByProductIdAndCartId(product.id, cart.id)).willReturn(cartItem)

        mockMvc.perform(MockMvcRequestBuilders.put("/carts/update")
                .param("productId", "1")
                .param("cartId", "1")
                .param("quantity", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ResponseDto("Success"))))

        assertThat(cartItem.quantity).isEqualTo(10)
        verify(cartItemRepository, Mockito.times(1)).findByProductIdAndCartId(product.id, cart.id)
        verify(cartItemRepository, Mockito.times(1)).save(cartItem)
    }
}
