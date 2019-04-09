// CATEGORIES
//    import groovy.time.TimeCategory    

    now = new Date()
    println now
    
    println now + 5
    
    use(TimeCategory) {
        nextWeekPlusTenHours = now + 1.week + 10.hours - 30.seconds
    }
    println nextWeekPlusTenHours
    

class TimeCategory {
    static final long HOUR = 60 * 60 * 1000;
    static final long DAY = 24 * HOUR;
    
    static Date getWeek(Integer i) { new Date(i * 7*DAY) }
    static Date getHours(Integer i) {new Date(i * HOUR) }
    static Date getMinutes(Integer i) {new Date(i * 1000 * 60)}
    static Date getSeconds(Integer i) {new Date(i * 1000) }
    static Date plus(Date d1, Date d2) { new Date(d1.time + d2.time) }
    static Date minus(Date d1, Date d2) { new Date(d1.time - d2.time) }
}

