/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.arquillianpersistence;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.waastad.arquillianpersistence.ejb.AuthUtility;
import org.waastad.arquillianpersistence.ejb.AuthorizeBean;
import org.waastad.arquillianpersistence.entity.UserAccount;
import org.waastad.arquillianpersistence.filter.TokenFilter;
import org.waastad.arquillianpersistence.mock.MockAuthUtility;
import org.waastad.arquillianpersistence.producer.EntityManagerProducer;
import org.waastad.arquillianpersistence.repository.UserAccountRepository;
import org.waastad.arquillianpersistence.service.UserService;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@RunWith(Arquillian.class)
public class RepositorySpecializesMockTest {

    @Deployment(testable = true)
    public static Archive<?> createDeploymentPackage() {

        File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(UserService.class, EntityManagerProducer.class, TokenFilter.class, UserAccountRepository.class, UserAccount.class,MockAuthUtility.class,AuthorizeBean.class)
                .addAsWebInfResource("tomee/test-beans.xml", "beans.xml")
                .addAsWebInfResource("tomee/test-persistence.xml", "persistence.xml")
                .addAsManifestResource("tomee/test-context.xml", "context.xml")
                .addAsLibraries(libs);
    }

    @ArquillianResource
    private URL url;


    @Test
    @InSequence(value = 1)
    public void testBoolean() throws Exception {
        WebClient client = getAdminWebClient()
                .path("users/boolean");
        client.get();
        Assert.assertEquals(500,client.getResponse().getStatus());
    }

    private WebClient getBaseWebClient() throws MalformedURLException {
        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJsonProvider());
        WebClient client = WebClient.create(url.toString(), providers);
        ClientConfiguration config = WebClient.getConfig(client);
        config.getOutInterceptors().add(new LoggingOutInterceptor());
        config.getInInterceptors().add(new LoggingInInterceptor());
        client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_HTML);
        return client;
    }

    private WebClient getAdminWebClient() throws MalformedURLException {
        WebClient baseWebClient = getBaseWebClient();
        baseWebClient.header("token", "admin");
        return baseWebClient;
    }

    private WebClient getSuperAdminWebClient() throws MalformedURLException {
        WebClient baseWebClient = getBaseWebClient();
        baseWebClient.header("token", "tomee");
        return baseWebClient;
    }

    private WebClient getWrongTokenWebClient() throws MalformedURLException {
        WebClient baseWebClient = getBaseWebClient();
        baseWebClient.header("token", "zappa");
        return baseWebClient;
    }
}
