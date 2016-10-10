/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.agent.bean;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.ejbsim.db.entity.AAgent1;
import org.ejbsim.db.entity.TMove;
import org.ejbsim.db.facade.AAgent1Facade;
import org.ejbsim.engine.TheWorld;
import sim.engine.SimState;
import sim.field.network.Edge;
import sim.util.Bag;
import org.ejbsim.agent.IAgent;

/**
 *
 * @author g
 */
@Stateful
public class Agent1Bean implements IAgent {

    public double x, y;
    public String name;
    @EJB AAgent1Facade aFacade;
    AAgent1 a;
    
    List<Object> tasks = new ArrayList();
    public double to_x, to_y;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
//
    @Override
    public void step(SimState ss) {
        tasks.stream().forEach((a)->{
            if(a.getClass().getName().equals(TMove.class.getName())){
                TMove t = (TMove)a;
                if(ss.schedule.getSteps()>=t.getT()) {
                    to_x = t.getToX();
                    to_y = t.getToY();
                    double s=Math.sqrt((to_x-x)*(to_x-x)+(to_y-y)*(to_y-y));
                    double ds=.0001;
                    double dx = ds*(to_x-x)/s;
                    double dy = ds*(to_y-y)/s;
                    if((to_x-x)*(to_x-x-dx)+(to_y-y)*(to_y-y-dy)>0){
                        x+=dx;
                        y+=dy;
                        System.out.println(name+":"+String.valueOf(x)+","+String.valueOf(y));
                    }
                    else {
                        tasks.remove(a);
                    }
                }
                
            }
        });
    }

    @Override
    public void start() {
    }

    @Override
    public void load(String tfqid) {
        name = tfqid;
        a = aFacade.find(tfqid);
        x = a.getX();
        y = a.getY();
    }

    @Override
    public void init(SimState ss) {
        TheWorld w = (TheWorld)ss;
        Bag edges = w.relTask.getEdgesOut(a);
        while(!edges.isEmpty()){
            Edge e = (Edge) edges.pop();
            tasks.add(e.getTo());
        }
    }

    @Override
    public Object getStoreData() {
        return a;
    }

    
    
}
