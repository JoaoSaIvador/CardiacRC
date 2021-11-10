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

    @EJB
    AdministratorBean administratorBean = new AdministratorBean();

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB(){
        try {
            professionalBean.create("tomasalves", 987654321, "Tomás Alves", "123456", "tomas.alves@mail.com");
            patientBean.create("joaosalvador", 123456789, "João Salvador", "123456", "joao.salvador@mail.com", "tomasalves");
            prescriptionBean.create("tomasalves","joaosalvador","teste", "prescricao1",4);
            prescriptionBean.create("tomasalves","joaosalvador","teste2","prescricao2",9);
            administratorBean.create("jacksparrow", "Jack Sparrow", "123456", "jack.sparrow@mail.com");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
