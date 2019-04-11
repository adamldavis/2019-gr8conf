package com.adamldavis.groovydsl

import spock.lang.Specification

import java.text.SimpleDateFormat
import java.time.temporal.ChronoField

class TimeCategorySpec extends Specification {

    def "test TimeCategory" () {
        expect:
        def now = new SimpleDateFormat('yyyy-M-d').parse('2019-1-1')
        println now
        Date nextWeek

        use(TimeCategory) {
            nextWeek = now + 1.week + 10.hours - 30.seconds
        }
        println nextWeek
        nextWeek.toLocalDateTime().get(ChronoField.CLOCK_HOUR_OF_DAY) == 9
        nextWeek.toLocalDateTime().get(ChronoField.MINUTE_OF_HOUR) == 59
        nextWeek.toLocalDateTime().get(ChronoField.SECOND_OF_MINUTE) == 30
        nextWeek.toLocalDateTime().get(ChronoField.DAY_OF_YEAR) == 8
    }

}
