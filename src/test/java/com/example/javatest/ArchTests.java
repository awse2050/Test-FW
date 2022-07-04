package com.example.javatest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ArchTests {

    @Test
    public void Services_should_only_be_accessed_by_Controllers() {

        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.mycompany.myapp");

        ArchRule myRule = classes().that()
                .resideInAPackage("..service..").should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

        myRule.check(importedClasses);
    }

    @Test
    void packageDependencyTests() {
        // 바이트코드를 조작한다.
        JavaClasses classes = new ClassFileImporter()
                .importPackages("com.example.javatest");

        ArchRule domainPackageRule = classes().that().resideInAPackage("..domain..")
                .should().onlyBeAccessed().byClassesThat()
                .resideInAnyPackage("..study..", "..member..", "..domain..");

        domainPackageRule.check(classes);

        ArchRule memberPackageRule = noClasses().that().resideInAPackage("..domain..").should().accessClassesThat().resideInAPackage("..member..");

        memberPackageRule.check(classes);

        ArchRule studyPackageRule = noClasses().that().resideOutsideOfPackage("..study..").should().accessClassesThat().resideInAnyPackage("..study..");

        studyPackageRule.check(classes);

        ArchRule freeOfCycles = slices().matching("..패키지.(*)..").should().beFreeOfCycles();

        freeOfCycles.check(classes);
    }

    @ArchTest
    ArchRule domainPackageRule = classes().that().resideInAPackage("..domain..")
                .should().onlyBeAccessed().byClassesThat()
                .resideInAnyPackage("..study..", "..member..", "..domain..");

    @ArchTest
    ArchRule memberPackageRule = noClasses().that().resideInAPackage("..domain..")
            .should().accessClassesThat().resideInAPackage("..member..");

    @ArchTest
    ArchRule studyPackageRule = noClasses().that().resideOutsideOfPackage("..study..")
            .should().accessClassesThat().resideInAnyPackage("..study..");
}
