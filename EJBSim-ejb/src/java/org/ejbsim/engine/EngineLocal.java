/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.engine;

import javax.ejb.Local;

/**
 *
 * @author g
 */
@Local
public interface EngineLocal {

    void init();

    void start();

    void stop();

    void reset();

    void pause();

    void resume();
    
}
