/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.scenario;

import javax.ejb.Local;
import sim.engine.SimState;

/**
 *
 * @author g
 */
@Local
public interface ScenarioManagerBeanLocal {

    void buildScenario();
    void init();
    void start();

    SimState getSimState();
    
}
