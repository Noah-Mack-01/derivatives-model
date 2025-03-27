package com.noahnewmanmack.derivatives_model.models

import com.noahnewmanmack.derivatives_model.api.enums.OptionType
import com.noahnewmanmack.derivatives_model.api.models.DerivativeContext

class BinomialContext(
    val stockPrice: Double,
    val strikePrice: Double,
    val riskFree: Double,
    val upSwingProbability: Double,
    val upSwingModifier: Double,
    val downSwingModifier: Double,
    val periodsLeft: Int,
    val annualizedPeriod: Double,
    val optionType: OptionType
): DerivativeContext {}
