package com.footiestats.statsengine.dtos.engine.mappers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ExpectedEntityNotFound(message: String?) : RuntimeException(message)
