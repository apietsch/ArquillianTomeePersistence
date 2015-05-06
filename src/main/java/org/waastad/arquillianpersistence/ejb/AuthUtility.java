/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.arquillianpersistence.ejb;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
public interface AuthUtility {
    /**
     * 
     * @param name
     * @return 
     */
    public boolean authorize(String name);

}
