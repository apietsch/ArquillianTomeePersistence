/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.arquillianpersistence.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.omnifaces.filter.HttpFilter;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@WebFilter(urlPatterns = "/*")
public class TokenFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest hsr, HttpServletResponse hsr1, HttpSession hs, FilterChain fc) throws ServletException, IOException {
        System.out.println("FILTER IN USE");
        fc.doFilter(hsr, hsr1);
    }

}
