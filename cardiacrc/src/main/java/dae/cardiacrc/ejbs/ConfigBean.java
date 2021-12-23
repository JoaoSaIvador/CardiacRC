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

            quantityDataTypeBean.create("height", "m", 0, 3);
            qualityDataTypeBean.create("dwarf",0,1,1);
            qualityDataTypeBean.create("normal",1,2,1);
            qualityDataTypeBean.create("toomuchmilk",2,3,1);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
