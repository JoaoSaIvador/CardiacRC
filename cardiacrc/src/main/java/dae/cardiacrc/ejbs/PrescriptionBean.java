package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class PrescriptionBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String professionalUsername, String description, String name) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Professional professional =  em.find(Professional.class, professionalUsername);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        try {
            Prescription prescription = new Prescription(professional, description, name, professional.getType());
            professional.addPrescription(prescription);
            em.persist(prescription);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Prescription> getAllPrescriptions(){
        return (List<Prescription>) em.createNamedQuery("getAllPrescriptions").getResultList();
    }

    public Prescription findPrescription(int id) throws MyEntityNotFoundException {
        Prescription prescription = em.find(Prescription.class, id);
        if (prescription == null){
            throw new MyEntityNotFoundException("Prescription not found!");
        }
        return prescription;
    }

    public void update(int id, String professionalUsername, String description, String name) throws MyEntityNotFoundException {
        Prescription prescription = findPrescription(id);

        em.lock(prescription, LockModeType.OPTIMISTIC);
        if (professionalUsername != null){
            Professional professional = em.find(Professional.class, professionalUsername);
            if(professional == null) {
                throw new MyEntityNotFoundException("Professional not found!");
            }

            if (!prescription.getProfessional().equals(professional)){
                prescription.getProfessional().removePrescription(prescription);
                professional.addPrescription(prescription);
            }
            prescription.setProfessional(professional);
        }

        if (name != null){
            prescription.setName(name);
        }
        if (description != null){
            prescription.setDescription(description);
        }
        em.merge(prescription);
    }

    public void delete(int id) throws MyEntityNotFoundException {
        Prescription prescription = findPrescription(id);
        Professional professional = em.find(Professional.class, prescription.getProfessional().getUsername());
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        professional.removePrescription(prescription);
        em.remove(prescription);
    }

}
