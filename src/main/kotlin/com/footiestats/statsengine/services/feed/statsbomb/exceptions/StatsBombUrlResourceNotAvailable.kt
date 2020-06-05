package com.footiestats.statsengine.services.feed.statsbomb.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class StatsBombUrlResourceNotAvailable(message: String?) : Throwable(message)