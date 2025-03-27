package com.noahnewmanmack.derivatives_model.api.services

import com.noahnewmanmack.derivatives_model.api.models.DerivativeContext

interface PricingModel<in T : DerivativeContext> {
    fun calculate(context: T): Double
}