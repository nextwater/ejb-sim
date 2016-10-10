/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejbsim.db.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ejbsim.db.entity.AAgent1;

/**
 *
 * @author g
 */
@Stateless
public class AAgent1Facade extends AbstractFacade<AAgent1> {

    @PersistenceContext(unitName = "EJBSim-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AAgent1Facade() {
        super(AAgent1.class);
    }
    
}
