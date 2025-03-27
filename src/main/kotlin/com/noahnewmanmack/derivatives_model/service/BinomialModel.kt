package com.noahnewmanmack.derivatives_model.service

import com.noahnewmanmack.derivatives_model.api.enums.OptionType
import com.noahnewmanmack.derivatives_model.api.services.PricingModel
import com.noahnewmanmack.derivatives_model.models.BinomialContext
import org.springframework.stereotype.Service
import kotlin.math.exp

@Service
class BinomialModel: PricingModel<BinomialContext> {

    var map :MutableMap<Double,Double> = mutableMapOf()
    var decayFactor: Double = 0.0
    override fun calculate(context: BinomialContext): Double {
        map.clear()
        decayFactor = exp(-context.riskFree * context.annualizedPeriod)
        return calculateHelper(context)
    }

    fun calculateHelper(context: BinomialContext): Double {
        val periodsLeft = context.periodsLeft
        if (periodsLeft == 0) {
            return when (context.optionType) {
                OptionType.PUT -> maxOf(0.0, context.strikePrice - context.stockPrice)
                OptionType.CALL -> maxOf(0.0, context.stockPrice - context.strikePrice)
            }
        }
        else {

            if (map.containsKey(context.strikePrice)) {
                println("Cache hit, ${map[context.strikePrice]}")
                return map[context.strikePrice]!!
            };

            val upSwing = calculateHelper(
                BinomialContext(
                    context.stockPrice * context.upSwingModifier,
                    context.strikePrice,
                    context.riskFree,
                    context.upSwingProbability,
                    context.upSwingModifier,
                    context.downSwingModifier,
                    context.periodsLeft - 1,
                    context.annualizedPeriod,
                    context.optionType
                )
            )
            val downSwing = calculateHelper(
                BinomialContext(
                    context.stockPrice * context.downSwingModifier,
                    context.strikePrice,
                    context.riskFree,
                    context.upSwingProbability,
                    context.upSwingModifier,
                    context.downSwingModifier,
                    context.periodsLeft - 1,
                    context.annualizedPeriod,
                    context.optionType
                )
            )

            val payoutValue = ((upSwing * context.upSwingProbability) + downSwing * (1 - context.upSwingProbability)) * decayFactor
            map[context.strikePrice] = payoutValue
            return payoutValue
        }
    }
}