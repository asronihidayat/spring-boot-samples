package com.superboot.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.superboot.entity.CartItem

//@JsonInclude(JsonInclude.Include.NON_EMPTY)
class CartItemListDto(
        var status: String = "",
        var data: Set<CartItem> = emptySet()
)
