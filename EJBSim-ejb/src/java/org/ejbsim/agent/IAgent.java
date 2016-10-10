/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.agent;

import javax.ejb.Local;
import org.ejbsim.db.entity.AAgent1;
import sim.engine.SimState;
import sim.engine.Steppable;

/**
 *
 * @author g
 */
@Local
public interface IAgent extends Steppable{


    void start();

    void load(String tfqid);

    void init(SimState ss);

    Object getStoreData();


}
