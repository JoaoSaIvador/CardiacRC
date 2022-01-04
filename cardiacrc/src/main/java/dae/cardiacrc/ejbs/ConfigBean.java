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

    @EJB
    QuantityDataTypeBean quantityDataTypeBean;

    @EJB
    QualityDataTypeBean qualityDataTypeBean;

    @EJB
    ProgramBean programBean;

    @EJB
    ObservationBean observationBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB(){
        try {
            typeBean.create("Cardiology");
            typeBean.create("Nutrition");
            typeBean.create("Physiology");

            quantityDataTypeBean.create("height", "m", 0, 2.5);
            qualityDataTypeBean.create("short",0,1.65,1);
            qualityDataTypeBean.create("average",1.65,1.8,1);
            qualityDataTypeBean.create("tall",1.8,2.5,1);

            professionalBean.create("tomasalves", 987654321, "Tomás Alves", "123456", "tomas.alves@mail.com", 1);
            professionalBean.create("marcopolo", 741852963, "Marco Polo", "123456", "marco.polo@mail.com", 2);
            professionalBean.create("carlossantos", 75348619, "Carlos Santos", "123456", "carlos.santos@mail.com", 3);

            patientBean.create("joaosalvador", 123456789, "João Salvador", "123456", "joao.salvador@mail.com");
            patientBean.create("mariasantos", 123446789, "Maria Santos", "123456", "mariasantos@mail.com");

            patientBean.addProfessional("joaosalvador", "tomasalves");
            patientBean.addProfessional("joaosalvador","carlossantos");

            observationBean.create("mariasantos", "mariasantos",2.5,1);
            observationBean.create("tomasalves","joaosalvador",1.7,1);

            programBean.create("7","joaosalvador","marcopolo");

            prescriptionBean.create("tomasalves","testeCardiologia", "prescricao1",1,"10");
            prescriptionBean.create("marcopolo","testeNutricao","prescricao2",1, "1");
            prescriptionBean.create("carlossantos","testeFisico","prescricao3",1,"2");

            administratorBean.create("jacksparrow", "Jack Sparrow", "123456", "jack.sparrow@mail.com");
            administratorBean.create("admin1", "Admin 1", "123456", "admin1@mail.com");
            administratorBean.create("admin2", "Admin 2", "123456", "admin2@mail.com");
            administratorBean.create("admin3", "Admin 3", "123456", "admin3@mail.com");
            administratorBean.create("admin4", "Admin 4", "123456", "admin4@mail.com");
            administratorBean.create("admin5", "Admin 5", "123456", "admin5@mail.com");
            administratorBean.create("admin6", "Admin 6", "123456", "admin6@mail.com");
            administratorBean.create("admin7", "Admin 7", "123456", "admin7@mail.com");
            administratorBean.create("admin8", "Admin 8", "123456", "admin8@mail.com");
            administratorBean.create("admin9", "Admin 9", "123456", "admin9@mail.com");
            administratorBean.create("admin10", "Admin 10", "123456", "admin10@mail.com");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
