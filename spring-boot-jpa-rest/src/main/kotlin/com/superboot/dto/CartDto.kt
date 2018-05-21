package com.superboot.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.superboot.entity.Cart

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class CartDto(
        var status: String = "",
        var data: Cart = Cart()
)
