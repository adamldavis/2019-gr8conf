package com.adamldavis.groovydsl;

import com.adamldavis.chem.Chemistry;
import groovy.lang.*;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class GroovyShellTest {


    @Test
    public void testRunningGroovyShellChemistry() throws IOException {
        final Binding binding = new Binding();
        final CompilerConfiguration compilerConfig = new CompilerConfiguration();
        final ImportCustomizer imports = new ImportCustomizer();

        imports.addStarImports("com.adamldavis.chem");
        compilerConfig.addCompilationCustomizers(imports);

        GroovyShell shell = new GroovyShell(Chemistry.class.getClassLoader(), binding, compilerConfig);

        final File file = new File("src/test/resources/chemistry.groovy");

        Object result = shell.evaluate(file);

        assertEquals(25, result);
    }

    @Test
    public void testRunningGroovyShellTasks() throws IOException {
        final Binding binding = new Binding();
        final CompilerConfiguration compilerConfig = new CompilerConfiguration();
        final ImportCustomizer imports = new ImportCustomizer();

        imports.addStarImports("com.adamldavis.groovydsl");
        compilerConfig.addCompilationCustomizers(imports);
        compilerConfig.setScriptBaseClass("com.adamldavis.groovydsl.Pachyderm");

        binding.setVariable("name", "groovy test");

        GroovyShell shell = new GroovyShell(Pachyderm.class.getClassLoader(), binding, compilerConfig);

        final File file = new File("src/test/resources/tasks.groovy");

        Object object = shell.evaluate(file);

        assertEquals("groovy test", shell.getContext().getVariables().get("name"));
        assertEquals(CommandChains.Task.class, object.getClass());
        assertEquals("build", ((CommandChains.Task) object).getName());
    }

}
