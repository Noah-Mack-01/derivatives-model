package com.noahnewmanmack.derivatives_model.service

import com.noahnewmanmack.derivatives_model.api.enums.OptionType
import com.noahnewmanmack.derivatives_model.api.services.PricingModel
import com.noahnewmanmack.derivatives_model.models.BlackScholesContext
import org.apache.commons.math3.distribution.NormalDistribution
import org.springframework.stereotype.Service
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.sqrt

@Service
class BlackScholesModel() : PricingModel<BlackScholesContext> {
    override fun calculate(context: BlackScholesContext): Double {

        val S = context.underlyingPrice
        val K = context.strikePrice
        val T = context.timeToExpiry
        val r = context.riskFree
        val sigma = context.volatility
        val optionType = context.optionType

        val d1 = (ln(S / K) + (r + 0.5 * sigma * sigma) * T) / (sigma * sqrt(T))
        val d2 = d1 - sigma * sqrt(T)

        return when (optionType) {
            OptionType.CALL -> S * normCdf(d1) - K * exp(-r * T) * normCdf(d2)
            OptionType.PUT -> K * exp(-r * T) * normCdf(-d2) - S * normCdf(-d1)
        }
    }

    private fun normCdf(x: Double): Double {
        return NormalDistribution().cumulativeProbability(x)
    }
}