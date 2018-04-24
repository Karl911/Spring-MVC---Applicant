package com.kfa.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.kfa.dao.ApplicantDAO;
import com.kfa.entity.Applicant;
import com.kfa.model.ApplicantModel;

public class ApplicantDAOImpl implements ApplicantDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

	public Applicant findApplicant(String id) {
		 Session session = sessionFactory.getCurrentSession();
		 Criteria crit = session.createCriteria(Applicant.class);
		 crit.add(Restrictions.eq("id", id));
		 return (Applicant) crit.uniqueResult();
	}
	
	public ApplicantModel findApplicantModel(String id) {
		 Applicant applicant = this.findApplicant(id);
		 if (applicant == null) {
			 return null;
		 }
		 return new ApplicantModel(applicant.getId(), applicant.getName(), //
				 applicant.getEmail(), applicant.getGender(), //
				 applicant.getPosition(), applicant.getSkills());
	}

	public List<ApplicantModel> listApplicantModels() {
		 String sql = "Select new " + ApplicantModel.class.getName()//
				 + "(a.id, a.name, a.email, a.gender, a.position, a.skills) "//
				 + " from " + Applicant.class.getName() + " a ";
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery(sql);
		 return query.list();
	}
	
	public List<ApplicantModel> listApplicantModelByPosition(String position) {
		 String sql = "Select new " + ApplicantModel.class.getName()//
				 + "(a.id, a.name, a.email, a.gender, a.position, a.skills) "//
				 + " from " + Applicant.class.getName() + " a ";
		 Session session = sessionFactory.getCurrentSession();
		 sql += " where position='"+position+"'";
		 Query query = session.createQuery(sql);
		 
		 return query.list();
	}

	public void saveApplicant(ApplicantModel applicantModel) {
		String id = applicantModel.getId();
		 Applicant applicant = null;
		 if (id != null) {
			 applicant = this.findApplicant(id);
		 }
		 boolean isNew = false;
		 if (applicant == null) {
			 isNew = true;
			 applicant = new Applicant();
			 applicant.setId(UUID.randomUUID().toString());
		 }
		 applicant.setEmail(applicantModel.getEmail());
		 applicant.setGender(applicantModel.getGender());
		 applicant.setName(applicantModel.getName());
		 applicant.setPosition(applicantModel.getPosition());
		 String skillsString = applicantModel.getSkillsString();

		 applicant.setSkills(skillsString);
		 //

		 if (isNew) {
			 Session session = this.sessionFactory.getCurrentSession();
			 session.persist(applicant);
		 }
		
	}

	public void deleteApplicant(String id) {
		Applicant applicant = this.findApplicant(id);
		if (applicant != null) {
			this.sessionFactory.getCurrentSession().delete(applicant);
		}
	}

	 
}
