package com.kfa.dao;

import java.util.List;

import com.kfa.entity.Applicant;
import com.kfa.model.ApplicantModel;

// ApplicantDAO est un bean pour gérer des insert, update query des données dans le tableau Applicants
public interface ApplicantDAO {
	
	public Applicant findApplicant(String id);
	 
    public List<ApplicantModel> listApplicantModels();
 
    public void saveApplicant(ApplicantModel applicantModel);
 
    public ApplicantModel findApplicantModel(String id);
 
    public void deleteApplicant(String id);

}
