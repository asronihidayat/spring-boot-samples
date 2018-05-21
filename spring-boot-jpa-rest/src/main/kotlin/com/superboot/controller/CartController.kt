package com.superboot.controller

import com.superboot.dao.CartItemRepository
import com.superboot.dto.CartDto
import com.superboot.dto.CartItemListDto
import com.superboot.dto.ResponseDto
import com.superboot.exception.ResourceNotFoundException
import com.superboot.service.CartService
import com.superboot.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carts")
class CartController {

    @Autowired
    lateinit var cartService: CartService

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var cartItemRepository: CartItemRepository

    @GetMapping("/{code}")
    fun index(
            @PathVariable code: String
    ): ResponseEntity<CartItemListDto>
    {
        return ResponseEntity.ok(CartItemListDto("Success", cartService.getCartItems(code)))
    }

    @PostMapping
    fun addToCart(
            @RequestParam("productId") productId: Long,
            @RequestParam("quantity") quantity: Int,
            @RequestParam("code") code: String
    ): ResponseEntity<CartDto>
    {
        try {
            var cart = cartService.addToCart(productService.getOne(productId).get(), quantity, code)
            return ResponseEntity.ok(CartDto("Success", cart))
        }catch (ex: NoSuchElementException){
            throw ResourceNotFoundException("Product not found")
        }
    }

    @DeleteMapping("/remove")
    fun deleteCartItem(
            @RequestParam("productId") productId: Long,
            @RequestParam("cartId") cartId: Long
    ): ResponseEntity<ResponseDto>
    {
        val cartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId)
        if (cartItem === null)
        {
            throw ResourceNotFoundException("Cart item not found")
        }
        cartItemRepository.delete(cartItem)
        return ResponseEntity<ResponseDto>(ResponseDto("Success"), HttpStatus.OK)
    }

    @PutMapping("/update")
    fun updateCartItem(
            @RequestParam("productId") productId: Long,
            @RequestParam("cartId") cartId: Long,
            @RequestParam("quantity") quantity: Int
    ): ResponseEntity<ResponseDto>{

        val cartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId)
        if (cartItem === null)
        {
            throw ResourceNotFoundException("Cart item not found")
        }
        cartItem.quantity = quantity
        cartItemRepository.save(cartItem)
        return ResponseEntity<ResponseDto>(ResponseDto("Success"), HttpStatus.OK)
    }
}

