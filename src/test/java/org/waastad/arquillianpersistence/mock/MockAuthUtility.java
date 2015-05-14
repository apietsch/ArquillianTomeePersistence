/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.arquillianpersistence.mock;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import org.waastad.arquillianpersistence.ejb.AuthorizeBean;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@Specializes
public class MockAuthUtility extends AuthorizeBean {

    @Override
    public boolean authorize(String name) {
        System.out.println("I am Mocking this request");
        throw new RuntimeException("Throwing an mock exception");
    }

}
