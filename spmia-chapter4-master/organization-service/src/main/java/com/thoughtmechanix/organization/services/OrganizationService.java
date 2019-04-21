package com.thoughtmechanix.organization.services;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository orgRepository;
    private Organization organization;

    public Organization getOrg(String organizationId) {
        return orgRepository.findByOrganizationId(organizationId);
    }

    public void saveOrg(Organization org){
        org.setOrganizationId( UUID.randomUUID().toString());
        orgRepository.save(org);

    }

    public void updateOrg(Organization org){
        orgRepository.save(org);
    }
    @Transactional
    public void deleteOrg(String orgid){
        orgRepository.deleteByOrganizationId(orgid);
    }
}
