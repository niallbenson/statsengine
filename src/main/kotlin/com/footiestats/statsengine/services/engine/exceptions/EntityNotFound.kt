package com.footiestats.statsengine.services.engine.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class EntityNotFound(message: String?) : Throwable(message)