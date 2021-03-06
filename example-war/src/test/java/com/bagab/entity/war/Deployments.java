package com.bagab.entity.war;

import com.bagab.entity.war.model.TestModel;
import com.bagab.entity.war.boundary.TestBoundary;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author prekezes.
 */
@ArquillianSuiteDeployment
public class Deployments {
    @Deployment(name = "test-suite")
    public static Archive<?> generateDefaultDeployment() {
        return ShrinkWrap.create(WebArchive.class, "war-test.war")

//                .addAsWebInfResource("src/test/resources/test-ds.xml")
                // All classes under org.bagab.entity.war
                .addPackage(TestModel.class.getPackage())
                .addPackage(TestBoundary.class.getPackage())
                .addAsWebInfResource("test-ds.xml")
                .addAsWebInfResource("beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                ;
    }

}
