package io.hikarilan.firestealer.utils

object StdUtils {

    infix fun <A, B> A.to(that: B): com.mojang.datafixers.util.Pair<A, B> = com.mojang.datafixers.util.Pair(this, that)

}