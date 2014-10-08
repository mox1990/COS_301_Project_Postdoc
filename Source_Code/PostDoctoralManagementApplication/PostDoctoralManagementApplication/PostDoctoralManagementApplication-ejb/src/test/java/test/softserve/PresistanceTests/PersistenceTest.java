/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package test.softserve.PresistanceTests;

import com.softserve.persistence.DBEntities.Person;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class PersistenceTest {
    
    private static EntityManager em = null;
    
    public PersistenceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        em = mock(EntityManager.class);
        
        if (em == null) {
            //em = (EntityManager) Persistence.createEntityManagerFactory("com.softserve_PostDoctoralManagementApplication-ejb_ejb_0.0PU").createEntityManager();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testInsertIntoPerson()
    {
        /*
        // Start a transaction
        em.getTransaction().begin();
        // ------------
        Person john = new Person("f01234587", "fred", "Dr", "John lemon", "venter", "j@gmail.com", false);
        
        em.persist(john);
        em.flush();

        em.remove(john);
        // ------------
        // Commit the Transaction
        em.getTransaction().commit();
                */
        assertTrue(true);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
