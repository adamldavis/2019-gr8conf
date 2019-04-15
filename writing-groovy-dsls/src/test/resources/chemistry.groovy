
Chemistry.exec {

    def acetaminophen = C8H9NO2

    println acetaminophen
    println acetaminophen.weight

    def angelicAcid = C5H8O2

    println angelicAcid
    println angelicAcid.weight

    def compound = acetaminophen + angelicAcid * 2
    def numberOfHydrogen = compound / H
    def ratioHydrogen = compound % H

    println "new compound = $compound"
    println "%H=$ratioHydrogen"
    println "#H=$numberOfHydrogen"

    numberOfHydrogen
}
