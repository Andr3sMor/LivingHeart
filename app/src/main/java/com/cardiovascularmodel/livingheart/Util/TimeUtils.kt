package com.cardiovascularmodel.livingheart.Util

import java.util.Calendar

fun calculateDelayUntil23h59(): Long {
    val now = Calendar.getInstance()

    val target = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        // Si ya pasó la hora, sumamos 1 día
        if (before(now)) {
            add(Calendar.DATE, 1)
        }
    }

    return target.timeInMillis - now.timeInMillis
}
