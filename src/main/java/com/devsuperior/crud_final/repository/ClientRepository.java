package com.devsuperior.crud_final.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.devsuperior.crud_final.entities.Client;

public interface ClientRepository extends JpaRepositoryImplementation<Client, Long> {

}
