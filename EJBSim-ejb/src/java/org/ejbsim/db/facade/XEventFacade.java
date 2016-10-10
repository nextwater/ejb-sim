/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.db.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ejbsim.db.entity.XEvent;

/**
 *
 * @author g
 */
@Stateless
public class XEventFacade extends AbstractFacade<XEvent> {

    @PersistenceContext(unitName = "EJBSim-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public XEventFacade() {
        super(XEvent.class);
    }
    
    public List<String> findAgentsBySceneId(int cid, int sid){
        return em.createNativeQuery("SELECT fqid FROM x_event WHERE scenarioid=? AND stepid=? AND fqid LIKE ?;")
                .setParameter(1, cid)
                .setParameter(2, sid)
                .setParameter(3, "a.%")
                .getResultList();
    }
    
    public List<String> findObjsBySceneId(int cid, int sid){
        return em.createNativeQuery("SELECT fqid FROM x_event WHERE scenarioid=? AND stepid=? AND fqid LIKE ?;")
                .setParameter(1, cid)
                .setParameter(2, sid)
                .setParameter(3, "o.%")
                .getResultList();
    }

    public List<String> findTasksBySceneId(int cid, int sid){
        return em.createNativeQuery("SELECT fqid FROM x_event WHERE scenarioid=? AND stepid=? AND fqid LIKE ?;")
                .setParameter(1, cid)
                .setParameter(2, sid)
                .setParameter(3, "t.%")
                .getResultList();
    }
    
    public List<String> findRelsBySceneId(int cid, int sid){
        return em.createNativeQuery("SELECT rid FROM x_event WHERE scenarioid=? AND stepid=? AND rid !='';")
                .setParameter(1, cid)
                .setParameter(2, sid)
                .getResultList();
    }    
}
