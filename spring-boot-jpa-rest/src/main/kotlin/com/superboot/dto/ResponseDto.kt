package com.superboot.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.*

@JsonInclude(Include.NON_EMPTY)
class ResponseDto(
        var status: String =  ""
)

