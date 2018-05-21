package com.superboot.dao

import com.superboot.entity.CartItem
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository: CrudRepository<CartItem, Long>
{
    @Query("select c from CartItem c where c.product.id= :productId and c.cart.id = :cartId")
    fun findByProductIdAndCartId(@Param("productId") productId: Long, @Param("cartId") cartId: Long): CartItem?
}