/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.scenario;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.ejbsim.db.facade.OPlatFacade;
import org.ejbsim.db.facade.RRelFacade;
import org.ejbsim.db.facade.RegisterFacade;
import org.ejbsim.db.facade.TMoveFacade;
import org.ejbsim.db.facade.XEventFacade;
import org.ejbsim.engine.TheWorld;
import sim.engine.SimState;
import org.ejbsim.agent.IAgent;
import org.ejbsim.db.entity.OPlat;
import org.ejbsim.agent.IAgent;

/**
 *
 * @author g
 */
@Stateless
public class ScenarioManagerBean implements ScenarioManagerBeanLocal {

    TheWorld ss = new TheWorld();
    @EJB XEventFacade xFacade;
    @EJB RegisterFacade regFacade;
    @EJB OPlatFacade oplatFacade;
    @EJB TMoveFacade tmoveFacade;
    @EJB RRelFacade relFacade;
    
    @Override
    public void buildScenario() {
        try {
            InitialContext ic = new InitialContext();
            xFacade.findAgentsBySceneId(0, 0).stream().forEach((a)->{
                String fqid = getFQIDByTFQID(a);
                String type = getTypeByFQID(fqid);
                String lookup = regFacade.find(type).getLookup();
                try {
                    IAgent aa = (IAgent)ic.lookup(lookup);
                    aa.load(a);
                    ss.agents.put(fqid, aa);
                } catch (NamingException ex) {
                    Logger.getLogger(ScenarioManagerBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            xFacade.findObjsBySceneId(0, 0).stream().forEach((a)->{
                String fqid=getFQIDByTFQID(a);
                String type=getTypeByFQID(fqid);
                if(type.equals("o.plat")){
                    OPlat o = oplatFacade.find(a);
                    ss.objs.put(fqid, oplatFacade.find(a));
                }
                else {
                    throw new UnsupportedOperationException("Unsupported yet.");
                }
            });
            xFacade.findTasksBySceneId(0, 0).stream().forEach((a)->{
                String fqid=getFQIDByTFQID(a);
                String type=getTypeByFQID(fqid);
                if(type.equals("t.move")){
                    ss.tasks.put(fqid, tmoveFacade.find(a));
                }
                else {
                    throw new UnsupportedOperationException("Unsupported yet.");
                }
                
            });
            List<String> ls=xFacade.findRelsBySceneId(0, 0);
            //build Relations
            xFacade.findRelsBySceneId(0, 0).stream().forEach((a)->{
                
                String []p = getFQIDsByRelTFQID(a);
                String c1 = getClassByFQID(p[0]);
                String c2 = getClassByFQID(p[1]);
                if(c1.equals("a")&&c2.equals("o")){
                    ss.relOwns.addEdge(
                            ((IAgent)ss.agents.get(p[0])).getStoreData(),
                            ss.objs.get(p[1]),
                            "");
                }
                else if(c1.equals("a")&&c2.equals("t")){
                    ss.relTask.addEdge(
                            ((IAgent)ss.agents.get(p[0])).getStoreData(),
                            ss.tasks.get(p[1]),
                            "");
                }
                else if(c1.equals("a")&&c2.equals("a")){
                    ss.relSNS.addEdge(ss.agents.get(p[0]), ss.agents.get(p[1]), "");
                }
            });
        } catch (NamingException ex) {
            Logger.getLogger(ScenarioManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public SimState getSimState() {
        return ss;
    }

    private String getClassByFQID(String fqid) {
        String[] a=fqid.split("\\.");
        return a[0];
    }

    private String[] getFQIDsByRelTFQID(String a) {
        String[] aa=a.split(":");
        String[] ret = new String[2];
        ret[0]=aa[0];
        ret[1]=aa[1];
        return ret;
    }

    private String getFQIDByTFQID(String a) {
        return a.split(":")[0];
    }

    private String getTypeByFQID(String fqid) {
        String[] x = fqid.split("\\.");
        return x[0]+"."+x[1];
    }

    @Override
    public void init() {
        ss.agents.entrySet().stream().forEach((a)->{
            ((IAgent)a.getValue()).init(ss);
        });
    }

    @Override
    public void start() {
        ss.agents.entrySet().stream().forEach((a)->{
            ss.schedule.scheduleRepeating((IAgent)a.getValue());
        });
        do
            if(!ss.schedule.step(ss))
                break;
        while(ss.schedule.getSteps()<5000);
        ss.finish();
    }


}
