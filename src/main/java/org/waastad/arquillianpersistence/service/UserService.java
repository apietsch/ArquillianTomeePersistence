/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.arquillianpersistence.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.waastad.arquillianpersistence.entity.UserAccount;
import org.waastad.arquillianpersistence.repository.UserAccountRepository;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@Stateless
public class UserService {

    @Inject
    private UserAccountRepository userAccountRepository;

    public List<UserAccount> getUsers() {
        return userAccountRepository.findAll();
    }

}
