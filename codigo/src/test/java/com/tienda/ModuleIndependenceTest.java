package com.tienda;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ModuleIndependenceTest {
    private static final JavaClasses CLASSES = new ClassFileImporter().importPackages("com.tienda");

    @Test
    public void ordersNoDependeDeOtros() {
        noClasses().that().resideInAPackage("com.tienda.orders..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "com.tienda.inventory..", "com.tienda.billing..", "com.tienda.notifications..")
                .check(CLASSES);
    }

    @Test
    public void inventoryNoDependeDeOtros() {
        noClasses().that().resideInAPackage("com.tienda.inventory..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "com.tienda.orders..", "com.tienda.billing..", "com.tienda.notifications..")
                .check(CLASSES);
    }

    @Test
    public void billingNoDependeDeOtros() {
        noClasses().that().resideInAPackage("com.tienda.billing..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "com.tienda.orders..", "com.tienda.inventory..", "com.tienda.notifications..")
                .check(CLASSES);
    }

    @Test
    public void notificationsNoDependeDeOtros() {
        noClasses().that().resideInAPackage("com.tienda.notifications..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "com.tienda.orders..", "com.tienda.inventory..", "com.tienda.billing..")
                .check(CLASSES);
    }
}
