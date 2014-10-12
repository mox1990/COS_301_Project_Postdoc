/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.AuditLogJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.persistence.DBDAO.NotificationJpaController;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NotificationServiceMockUnit extends NotificationService {
    private DAOFactory dAOFactory;
    private TransactionController transactionController;
    private UserGateway userGateway;
    private Properties properties;
    private Session session;
    private javax.mail.Authenticator authenticator;
    private InternetAddress internetAddress;
    private MimeMessage mimeMessage;
    private EntityManager entityManager;

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    public void setUserGateway(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setInternetAddress(InternetAddress internetAddress) {
        this.internetAddress = internetAddress;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return dAOFactory;
    }

    @Override
    protected TransactionController getTransactionController()
    {
        return transactionController;
    }
    
    /**
     *
     * @return
     */
    @Override
    protected UserGateway getUserGatewayServiceEJB()
    {
        return userGateway;
    }
    
    @Override
    protected Properties getPropertiesJMAIL()
    {
        return properties;
    }
    
    @Override
    protected Session getSessionJMAIL(Properties properties, javax.mail.Authenticator authenticator)
    {
        return session;
    }
    
    @Override
    protected javax.mail.Authenticator getAuthenticatorJMAIL()
    {
        return authenticator;        
    }
    
    @Override
    protected InternetAddress getInternetAddressJMAIL(String email) throws AddressException
    {
        return internetAddress;
    }
    
    @Override
    protected MimeMessage getMimeMessageJMAIL(Session session)
    {
        return mimeMessage;
    }
    
    @Override
    protected void sendMessageJMAIL(Message message) throws MessagingException
    {
        //Transport.send(message);
    }
    
    @Override
    protected EntityManager createEntityManager()
    {
        return entityManager;
    }
}
