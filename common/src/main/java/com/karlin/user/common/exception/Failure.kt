package com.karlin.user.common.exception

sealed class Failure : Throwable() {

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}