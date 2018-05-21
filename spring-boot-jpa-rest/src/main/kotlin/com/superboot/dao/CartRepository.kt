package com.superboot.dao

import com.superboot.entity.Cart
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository: CrudRepository<Cart, Long> {
    fun findByCode(code: String):Cart
}