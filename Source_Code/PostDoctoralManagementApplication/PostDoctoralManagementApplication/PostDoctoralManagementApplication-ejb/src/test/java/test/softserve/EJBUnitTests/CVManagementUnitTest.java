/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.persistence.DBDAO.AcademicQualificationJpaController;
import com.softserve.persistence.DBDAO.CvJpaController;
import com.softserve.persistence.DBDAO.ExperienceJpaController;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.Person;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.CVManagementServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class CVManagementUnitTest {
    private CVManagementServiceMockUnit instance;
        
    private CvJpaController mockCvJpaController;
    private AcademicQualificationJpaController mockAcademicQualificationJpaController;
    private ExperienceJpaController mockExperienceJpaController;
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    
    public CVManagementUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new CVManagementServiceMockUnit();
        
        mockAcademicQualificationJpaController = mock(AcademicQualificationJpaController.class);
        mockExperienceJpaController = mock(ExperienceJpaController.class);
        mockCvJpaController =  mock(CvJpaController.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        
        instance.setdAOFactory(mockDAOFactory);
        instance.setTransactionController(mockTransactionController);
        
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
        when(mockDAOFactory.createAcademicQualificationDAO()).thenReturn(mockAcademicQualificationJpaController);
        when(mockDAOFactory.createExperienceDAO()).thenReturn(mockExperienceJpaController);
        when(mockDAOFactory.createCvDAO()).thenReturn(mockCvJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createCV method, of class CVManagementService.
     */
    @Test
    public void testCreateCV() throws Exception {
        
        Cv mockCV = mock(Cv.class);  
        //when(mockCV.getPerson()).thenReturn(new Person("u12236731"));
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        
        try
        {
            instance.createCV(mockSession, mockCV);
            
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createAcademicQualificationDAO();
            verify(mockDAOFactory).createExperienceDAO();
            verify(mockDAOFactory).createCvDAO();
            
            verify(mockCV).getExperienceList();
            verify(mockCV).getAcademicQualificationList();
            verify(mockCV).setExperienceList(null);
            verify(mockCV).setAcademicQualificationList(null);
            verify(mockCV).setCvID("u12236731");
            
            verify(mockCvJpaController).create(mockCV);
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
                        
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockAcademicQualificationJpaController);
            verifyNoMoreInteractions(mockExperienceJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    @Test
    public void testCreateCVButHasCV() throws Exception {
        Person mockPerson = mock(Person.class);
        
        Cv mockCV = mock(Cv.class);  
        when(mockCV.getPerson()).thenReturn(mockPerson);
        when(mockPerson.getCv()).thenReturn(mockCV);
        
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(mockPerson);
        
        try
        {
            instance.createCV(mockSession, mockCV);
            
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockAcademicQualificationJpaController);
            verifyNoMoreInteractions(mockExperienceJpaController);
        }
        catch (Exception ex)
        {
            if(!ex.getMessage().equals("The user already has a cv"))
                fail("An exception occured");
        }
    }

    /**
     * Test of updateCV method, of class CVManagementService.
     */
    @Test
    public void testUpdateCV() throws Exception {
        Cv mockCV = mock(Cv.class);  
        when(mockCV.getPerson()).thenReturn(new Person("u12236731"));
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        try
        {
            instance.updateCV(mockSession, mockCV);
            
                     
            verify(mockTransactionController).StartTransaction();
            verify(mockTransactionController).getDAOFactoryForTransaction();
            verify(mockDAOFactory).createAcademicQualificationDAO();
            verify(mockDAOFactory).createExperienceDAO();
            verify(mockDAOFactory).createCvDAO();
            
            verify(mockCV).getExperienceList();
            verify(mockCV).getAcademicQualificationList();
            verify(mockCV).setCvID("u12236731");
            
            verify(mockCvJpaController).edit(mockCV); 
            verify(mockTransactionController).CommitTransaction();
            verify(mockTransactionController).CloseEntityManagerForTransaction();
            
            verifyNoMoreInteractions(mockTransactionController);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockCvJpaController);
            verifyNoMoreInteractions(mockAcademicQualificationJpaController);
            verifyNoMoreInteractions(mockExperienceJpaController);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
}
