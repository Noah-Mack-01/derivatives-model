package com.noahnewmanmack.derivatives_model.models

import com.noahnewmanmack.derivatives_model.api.enums.OptionType
import com.noahnewmanmack.derivatives_model.api.models.DerivativeContext

class BlackScholesContext(
    val volatility: Double,
    val underlyingPrice: Double,
    val strikePrice: Double,
    val riskFree: Double,
    val optionType: OptionType,
    val timeToExpiry: Double
) : DerivativeContext {}

