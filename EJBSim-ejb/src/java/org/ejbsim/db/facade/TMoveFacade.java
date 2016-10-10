/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.db.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ejbsim.db.entity.TMove;

/**
 *
 * @author g
 */
@Stateless
public class TMoveFacade extends AbstractFacade<TMove> {

    @PersistenceContext(unitName = "EJBSim-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TMoveFacade() {
        super(TMove.class);
    }
    
}
