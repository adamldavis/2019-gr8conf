package com.adamldavis.groovydsl

import groovy.transform.CompileStatic

@CompileStatic
class Transition {
    
    String property
    def duration = 1
    String timing = ''
    def delay = 0

    Transition width(durationValue) {
        property = 'width'
        duration = durationValue
        this
    }
    
    Transition height(durationValue) {
        property = 'height'
        duration = durationValue
        this
    }

    /** Specifies a transition effect with a slow start, then fast, then end slowly (this is default)*/
    Transition ease (delayValue) {
        timing = 'ease'
        delay = delayValue
        this
    }

    /** Specifies a transition effect with the same speed from start to end*/
    Transition linear (delayValue) { timing = 'linear'; delay = delayValue; this }

    /** Specifies a transition effect with a slow start*/
    Transition easeIn (delayValue) { timing = 'ease-in'; delay = delayValue; this }

    /** Specifies a transition effect with a slow end*/
    Transition easeOut (delayValue) { timing = 'ease-out'; delay = delayValue; this }
}
