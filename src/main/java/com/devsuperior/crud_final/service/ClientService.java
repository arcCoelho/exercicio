package com.devsuperior.crud_final.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.crud_final.dto.ClientDTO;
import com.devsuperior.crud_final.entities.Client;
import com.devsuperior.crud_final.repository.ClientRepository;
import com.devsuperior.crud_final.service.exception.ResourcesNotFoundException;

@Service
public class ClientService {

	@Autowired
	ClientRepository repo;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> lista = repo.findAll(pageRequest);
		return lista.map(x-> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> optional = repo.findById(id);
		Client client = optional.orElseThrow( () -> new ResourcesNotFoundException("Cliente não encontrado") );
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyToEntity(dto, entity);
		repo.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repo.getOne(id);
			dto.setId(id);
			copyToEntity(dto, entity);
			repo.save(entity);
			
			dto = new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourcesNotFoundException("O recurso 'Cliente' de id: "+id+" não foi encontrado");
		}
		return dto;
	}
	
	

	private void copyToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}

	public void delete(Long id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourcesNotFoundException("O cliente de id :"+id+", não pode ser excluido pois o mesmo não existe no sistema.");
		}
	}
	
}
