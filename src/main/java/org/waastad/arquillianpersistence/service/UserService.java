/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.arquillianpersistence.service;

import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.waastad.arquillianpersistence.ejb.AuthUtility;
import org.waastad.arquillianpersistence.ejb.AuthorizeBean;
import org.waastad.arquillianpersistence.entity.UserAccount;
import org.waastad.arquillianpersistence.repository.UserAccountRepository;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Path("users")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

    @Inject
    private UserAccountRepository userAccountRepository;
    @Inject
    private AuthUtility authorizeBean;

    @GET
    public List<UserAccount> getUsers() {
        return userAccountRepository.findAll();
    }

    @POST
    public UserAccount createUser(UserAccount account) {
        return userAccountRepository.save(account);
    }

    @GET
    @Path("test")
    public String whatever() {
        authorizeBean.authorize("asdasd");
        return "OK";
    }

}
