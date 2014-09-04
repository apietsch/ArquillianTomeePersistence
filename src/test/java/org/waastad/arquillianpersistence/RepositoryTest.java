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
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
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
@PersistenceTest
public class RepositoryTest {

    @Deployment(testable = true)
    public static Archive<?> createDeploymentPackage() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(UserService.class, EntityManagerProducer.class, UserAccountRepository.class, UserAccount.class)
//                .addClasses(AbstractEntityRepository.class, Repository.class,
//                        EntityRepository.class,
//                        FirstResult.class, MaxResults.class, Modifying.class,
//                        Query.class, QueryParam.class, QueryResult.class,
//                        EntityManagerConfig.class, EntityManagerResolver.class)
//                .addClasses(Criteria.class, QuerySelection.class, CriteriaSupport.class)
//                .addClasses(CreatedOn.class, CurrentUser.class, ModifiedBy.class, ModifiedOn.class)
//                .addClasses(MappingConfig.class, QueryInOutMapper.class)
//                .addClasses(DelegateQueryHandler.class, QueryInvocationContext.class)
                //                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                //                .addAsManifestResource("test-persistence.xml", "persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-persistence.xml", "persistence.xml")
                .addAsLibraries(libs);
    }

    @Inject
    private UserService userService;

    @Test
    @UsingDataSet("datasets/users.yml")
    public void testSomeMethod() {
        List<UserAccount> users = userService.getUsers();
        System.out.println("Testing......" + users.size());
    }

}
