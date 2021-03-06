package com.beomsu317.use_case.exception

import io.ktor.http.*


abstract class PrecoditionFailedException(code: Int, message: String) :
    BaseException(HttpStatusCode.PreconditionFailed, code, message)

class PasswordEmptyException(
    code: Int = 1,
    message: String = "Password and confirm password must not be empty"
) : PrecoditionFailedException(code, message)

class ConfirmPasswordNotMatchException(
    code: Int = 2,
    message: String = "Password and confirm password dose not match"
) : PrecoditionFailedException(code, message)

class NotEmailAddressException(
    code: Int = 3,
    message: String = "Email address is not validate"
) : PrecoditionFailedException(code, message)

class EmptyDisplayNameException(
    code: Int = 4,
    message: String = "Display name must not be empty"
) : PrecoditionFailedException(code, message)

class InappropriateFriendsIncludeException(
    code: Int = 5,
    message: String = "Inappropriate friend in the list of friends to add"
) : PrecoditionFailedException(code, message)