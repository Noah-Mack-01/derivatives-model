package com.noahnewmanmack.derivatives_model.service

import com.noahnewmanmack.derivatives_model.api.enums.OptionType
import com.noahnewmanmack.derivatives_model.api.services.PricingModel
import com.noahnewmanmack.derivatives_model.models.BinomialContext
import org.springframework.stereotype.Service
import kotlin.math.exp

@Service
class BinomialModel: PricingModel<BinomialContext> {
    override fun calculate(context: BinomialContext): Double {
        if (context.periodsLeft == 0) {
            return when (context.optionType) {
                OptionType.PUT -> maxOf(0.0, context.strikePrice - context.stockPrice)
                OptionType.CALL -> return maxOf(0.0, context.stockPrice - context.strikePrice)
            }
        }
        else {
            val upSwing = calculate(BinomialContext(
                context.stockPrice * context.upSwingModifier,
                context.strikePrice,
                context.riskFree,
                context.upSwingProbability,
                context.upSwingModifier,
                context.downSwingModifier,
                context.periodsLeft - 1,
                context.annualizedPeriod,
                context.optionType
            ))
            val downSwing = calculate(BinomialContext(
                context.stockPrice * context.downSwingModifier,
                context.strikePrice,
                context.riskFree,
                context.upSwingProbability,
                context.upSwingModifier,
                context.downSwingModifier,
                context.periodsLeft - 1,
                context.annualizedPeriod,
                context.optionType
            ))
            return (upSwing * context.upSwingProbability + downSwing * (1 - context.upSwingProbability)) * exp(-context.riskFree * context.annualizedPeriod)
        }
    }
}