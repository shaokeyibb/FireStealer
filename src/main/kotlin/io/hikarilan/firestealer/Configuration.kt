package io.hikarilan.firestealer

import net.minecraftforge.common.ForgeConfigSpec

object Configuration {

    lateinit var COMMON_CONFIG: Common

    class Common(
        builder: ForgeConfigSpec.Builder
    ) {

        init {
            builder.comment("General Settings").push("General")
        }

        val disableTimeChange: ForgeConfigSpec.BooleanValue = builder
            .comment("Disable time change")
            .define("disableTimeChange", false)

        val disableFurnaceExplode: ForgeConfigSpec.BooleanValue = builder
            .comment("Disable furnace explode")
            .define("disableFurnaceExplode", true)


        init {
            builder.pop()
        }

    }

}