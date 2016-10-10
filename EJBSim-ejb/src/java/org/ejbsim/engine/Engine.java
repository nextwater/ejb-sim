/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.engine;

import javax.ejb.Stateless;

/**
 *
 * @author g
 */
@Stateless
public class Engine implements EngineLocal {

    @Override
    public void init() {
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void start() {
        
    }

    @Override
    public void stop() {
    }

    @Override
    public void reset() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
