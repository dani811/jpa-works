package org.bagab.entity.relationships.test;

import org.bagab.entity.relationships.one2one_bi.Employee;
import org.bagab.entity.relationships.one2one_bi.ParkingLot;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

import org.bagab.entity.relationships.one2one_bi.Employee;
import org.bagab.entity.relationships.one2one_bi.ParkingLot;
import javax.ejb.*;

/**
 * Created by x on 5/1/15.
 */
@RunWith(Arquillian.class)
public class OneToOneBiTest {

    public static final Logger log = Logger.getLogger(OneToOneBiTest.class.getName());

@EJB
private OneToOneBiService svc;

    @Test
    @OperateOnDeployment("test-suite")
    public void test() {
        // Create Employee
        Employee e1 = svc.createEmployee("John");
        // Lookup Employee by id
        Assert.assertEquals("John", svc.findEmployee(e1.getId()).getName());

        // Create ParkingLot
        ParkingLot l1 = svc.createParkingLot("P1");
        // Lookup ParkingLot by id
        Assert.assertEquals("P1", svc.findLot(l1.getId()).getCode());

        // Associate Parking with Employee
        svc.parkingAssign(e1.getId(), l1.getId());
        // Verify Association
        Assert.assertEquals("P1", svc.findEmployee(e1.getId()).getParkingLot().getCode());

        // Disasociatate Parking with Employee
        svc.freeParkingLot(e1.getId());
        // Verify Association
        Assert.assertNull( svc.findEmployee(e1.getId()).getParkingLot());
        Assert.assertNull( svc.findLot(l1.getId()).getEmployee());


        /** See what happens when we try to assign another employee to a used parking */
        svc.parkingAssign(e1.getId(), l1.getId());
        Assert.assertEquals("P1", svc.findEmployee(e1.getId()).getParkingLot().getCode());

        Employee e2 = svc.createEmployee("George");
        Assert.assertEquals("George", svc.findEmployee(e2.getId()).getName());

       svc.parkingAssign(e2.getId(), l1.getId());

        Assert.assertNull( svc.findEmployee(e1.getId()).getParkingLot());
        Assert.assertEquals("George", svc.findLot(l1.getId()).getEmployee().getName());
        Assert.assertEquals("P1", svc.findEmployee(e2.getId()).getParkingLot().getCode());
        /* Everything went smoothly since parkingAssign() has the provision to unassign the previous employee*/
    }
}
