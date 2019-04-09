// ONLY WORKS IN GROOVY 3!

// Java 8+ try-with-resources (ARM)
def f = new File('temp')

try (PrintWriter pw = new PrintWriter(f)) {

    pw.println("TEST!")
    
} catch (Exception e) {
       // do something
}
println f.text

f.deleteOnExit()


