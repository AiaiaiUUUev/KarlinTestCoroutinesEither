package com.karlin.user.common

/**
 * Исключение, которое бросается, если отсутствуют необходимые параметры в [Params].
 */
class MissingRequiredParamsException(message: String? = null) : RuntimeException(message)
