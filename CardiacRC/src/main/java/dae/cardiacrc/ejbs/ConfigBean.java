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
    ProfessionalBean professionalBean;

    @EJB
    PatientBean patientBean;

    @EJB
    PrescriptionBean prescriptionBean;

    @EJB
    AdministratorBean administratorBean;

    @EJB
    TypeBean typeBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB(){
        try {
            typeBean.create("Cardiology");
            typeBean.create("Nutrition");
            typeBean.create("Physiology");
            professionalBean.create("tomasalves", 987654321, "Tomás Alves", "123456", "tomas.alves@mail.com", 1);
            professionalBean.create("marcopolo", 741852963, "Marco Polo", "123456", "marco.polo@mail.com", 2);
            professionalBean.create("carlossantos", 75348619, "Carlos Santos", "123456", "carlos.santos@mail.com", 3);
            patientBean.create("joaosalvador", 123456789, "João Salvador", "123456", "joao.salvador@mail.com");
            prescriptionBean.create("tomasalves","joaosalvador","testeCardiologia", "prescricao1",4);
            prescriptionBean.create("marcopolo","joaosalvador","testeNutricao","prescricao2",9);
            prescriptionBean.create("carlossantos","joaosalvador","testeFisico","prescricao3",9);
            administratorBean.create("jacksparrow", "Jack Sparrow", "123456", "jack.sparrow@mail.com");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
