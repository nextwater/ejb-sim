/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.engine;

import java.util.HashMap;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.field.network.Network;

/**
 *
 * @author g
 */
public class TheWorld extends SimState{
    
    Continuous2D coords = new Continuous2D(1.0,999999,999999);
    
    public HashMap<String, Object> agents = new HashMap();
    public HashMap<String, Object> objs = new HashMap();
    public HashMap<String, Object> tasks = new HashMap();
    
    public Network relTask = new Network();
    public Network relSNS = new Network();
    public Network relOwns = new Network();
    
    public TheWorld(long seed) {
        super(seed);
    }

    public TheWorld() {
        super(0);
    }
    
}
