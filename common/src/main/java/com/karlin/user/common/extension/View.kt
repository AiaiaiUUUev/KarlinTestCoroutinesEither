package com.karlin.user.common.extension

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.common.extension
 */

fun View.show() {
    this.visibility = VISIBLE
}

fun View.hide() {
    this.visibility = GONE
}