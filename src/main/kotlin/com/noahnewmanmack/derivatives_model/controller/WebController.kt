package com.noahnewmanmack.derivatives_model.controller

import com.noahnewmanmack.derivatives_model.api.enums.OptionStyle
import com.noahnewmanmack.derivatives_model.api.enums.OptionType
import com.noahnewmanmack.derivatives_model.models.BinomialContext
import com.noahnewmanmack.derivatives_model.models.BlackScholesContext
import com.noahnewmanmack.derivatives_model.service.BinomialModel
import com.noahnewmanmack.derivatives_model.service.BlackScholesModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("models")
class WebController(
    private val binomialModel: BinomialModel,
    private val blackScholesModel: BlackScholesModel
) {

    @GetMapping("/american")
    fun test(
        stockPrice: Double,
        strikePrice: Double,
        riskFree: Double,
        upSwingProbability: Double,
        upSwingModifier: Double,
        downSwingModifier: Double,
        daysLeft: Int,
        optionType: String,
        @RequestParam(required = false, defaultValue = "10") numIterations: Int,
    ): String{
            return "${this.binomialModel.calculate(
                BinomialContext(
                    stockPrice,
                    strikePrice,
                    riskFree,
                    upSwingProbability,
                    upSwingModifier,
                    downSwingModifier,
                    numIterations,
                    daysLeft/365.0/numIterations,
                    OptionType.valueOf(optionType)
                ))}"
    }
    @GetMapping("/european")
    fun test(
        underlyingPrice: Double,
        strikePrice: Double,
        riskFree: Double,
        volatility: Double,
        timeToExpiryInYears: Double,
        optionType: String,
    ): String {
        return "${
            this.blackScholesModel.calculate(
                BlackScholesContext(
                    volatility,
                    underlyingPrice,
                    strikePrice,
                    riskFree,
                    OptionType.valueOf(optionType),
                    timeToExpiryInYears
                )
            )
        }"
    }
}