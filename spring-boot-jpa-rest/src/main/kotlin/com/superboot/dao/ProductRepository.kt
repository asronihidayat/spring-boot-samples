package com.superboot.dao

import com.superboot.entity.Cart
import com.superboot.entity.CartItem
import com.superboot.entity.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository:CrudRepository<Product, Long> {
}
