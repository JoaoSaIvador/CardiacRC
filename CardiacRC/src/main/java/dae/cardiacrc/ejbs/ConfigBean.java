package dae.cardiacrc.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.EJB;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    ProfessionalBean professionalBean = new ProfessionalBean();

    @EJB
    PatientBean patientBean = new PatientBean();

    @EJB
    PrescriptionBean prescriptionBean = new PrescriptionBean();

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB(){
        try {
            professionalBean.create("tomasalves", 987654321, "Tomás Alves", "12345", "tomas.alves@mail.com");
            patientBean.create("joaosalvador", 123456789, "João Salvador", "12345", "joao.salvador@mail.com", "tomasalves");
            prescriptionBean.create("tomasalves","joaosalvador","Hoje", "Amanha", "teste","prescricao1",4);
            prescriptionBean.create("tomasalves","joaosalvador","Amanha", "Nunca", "teste2","prescricao2",9);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
