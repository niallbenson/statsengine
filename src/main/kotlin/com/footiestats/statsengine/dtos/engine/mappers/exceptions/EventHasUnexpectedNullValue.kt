package com.footiestats.statsengine.dtos.engine.mappers.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class EventHasUnexpectedNullValue(message: String?) : RuntimeException(message)