package com.superboot.dto

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonInclude.*
import com.superboot.entity.Product

@JsonInclude(Include.NON_EMPTY)
class ProductDto(
        var status: String = "",
        var data: Product = Product()
)

@JsonInclude(Include.NON_EMPTY)
class ProductListDto(
        var status: String = "",
        var data: List<Product> = emptyList()
)


