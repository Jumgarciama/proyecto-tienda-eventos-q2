package com.tienda;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ModuleIndependenceTest {
    private static final JavaClasses CLASSES = new ClassFileImporter().importPackages("com.tienda");

    @Test
    public void ordersNoDependeDeInventory() {
        ArchRule rule = noClasses().that().resideInAPackage("com.tienda.orders..")
                .should().dependOnClassesThat().resideInAPackage("com.tienda.inventory..");
        rule.check(CLASSES);
    }

    @Test
    public void inventoryNoDependeDeOrders() {
        ArchRule rule = noClasses().that().resideInAPackage("com.tienda.inventory..")
                .should().dependOnClassesThat().resideInAPackage("com.tienda.orders..");
        rule.check(CLASSES);
    }
}
