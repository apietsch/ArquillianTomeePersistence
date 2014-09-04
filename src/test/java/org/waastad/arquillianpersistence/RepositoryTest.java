/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.arquillianpersistence;

import java.io.File;
import java.util.List;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.waastad.arquillianpersistence.entity.UserAccount;
import org.waastad.arquillianpersistence.producer.EntityManagerProducer;
import org.waastad.arquillianpersistence.repository.UserAccountRepository;
import org.waastad.arquillianpersistence.service.UserService;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@RunWith(Arquillian.class)
public class RepositoryTest {

    @Deployment(testable = true)
    public static Archive<?> createDeploymentPackage() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(UserService.class, EntityManagerProducer.class, UserAccountRepository.class, UserAccount.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-persistence.xml", "persistence.xml")
                .addAsLibraries(libs);
    }

//    @Deployment(testable = true)
//    public static Archive<?> createDeploymentPackageJar() {
//        JavaArchive[] libs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().as(JavaArchive.class);
//        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
//                .addClasses(UserService.class, EntityManagerProducer.class, UserAccountRepository.class, UserAccount.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
//                .addAsManifestResource("test-persistence.xml", "persistence.xml");
//        for (JavaArchive lib : libs) {
//            archive = archive.merge(lib);
//        }
//        return archive;
//    }
    @Inject
    private UserService userService;
    @Inject
    UserAccountRepository userAccountRepository;

    @Test
    @UsingDataSet("users.yml")
    @InSequence(value = 1)
    public void testSomeMethod() {
        List<UserAccount> users = userService.getUsers();
        Assert.assertEquals(2,users.size());
    }
    
    @Test
    @UsingDataSet("users.yml")
    @InSequence(value = 2)
    public void testSomeMethod2() {
        UserAccount findBy = userAccountRepository.findBy(1L);
        Assert.assertEquals("Frank",findBy.getFirstname());
    }

}
