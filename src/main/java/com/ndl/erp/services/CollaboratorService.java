package com.ndl.erp.services;


import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.District;
import com.ndl.erp.dto.CollaboratorDTO;
import com.ndl.erp.dto.CollaboratorsDTO;
import com.ndl.erp.dto.CountryDataDTO;
import com.ndl.erp.repository.*;
import com.ndl.erp.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class CollaboratorService {
	
	@Autowired
	private CollaboratorRepository collaboratorRepository;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private CantonRepository cantonRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private FuelRepository fuelRepository;

	@Autowired
	private VehiculeTypeRepository vehiculeTypeRepository;

    private final transient Logger log = LoggerFactory.getLogger(CollaboratorService.class);


	public Collaborator save(Collaborator c) {
        if (log.isDebugEnabled()) {
            log.debug("Inserting new Collaborator  " + c.getName());
        }
        //c.setCreatedDate(new Date(DateUtil.getCurrentTime()));
        return collaboratorRepository.save(c);
	}


	public void deleteCollaborator(int id) {
        if (log.isDebugEnabled()) {
            log.debug("Deleting Collaborator " + id);
        }
		Optional<Collaborator> o = collaboratorRepository.findById(id);
		if (o == null) {
			throw new RuntimeException("ID NOT FOUND 404");
		}
//		o.get().(Collaborator);
		
	}

	public Collaborator updateCollaborator(Collaborator c) {
        if (log.isDebugEnabled()) {
            log.debug("Updating Collaborator " + c.getId());
        }

        return this.collaboratorRepository.save(c);
	}


    public CollaboratorsDTO getCollaborators(String filter, Integer pageNumber,
                                                  Integer pageSize, String sortDirection,
                                                  String sortField) {

        CollaboratorsDTO d = new CollaboratorsDTO();

        d.setCollaboratorsPage(
           this.collaboratorRepository.findUsingFilterPageable(filter,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.collaboratorRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;
    }


//	public Collaborator getCollaborator(Integer id) {
//		return this.collaboratorRepository.findById(id).get();
//
//	}

    public CollaboratorDTO getCollaborator(Integer id) {

        if (id==null) {
            CollaboratorDTO d = this.getCollaboratorDTO();
            return d;
        }
        Optional<Collaborator> c = collaboratorRepository.findById(id);

        CollaboratorDTO d = this.getCollaboratorDTO(c.get());
//        Optional<Collaborator> c = collaboratorRepository.findById(id);

        d.setCurrent(c.get());
        return d;
    }

    public Collaborator getCollaboratorBy(Integer id) {

//        if (id==null) {
//            CollaboratorDTO d = this.getCollaboratorDTO();
//            return d;
//        }
        Optional<Collaborator> oc = collaboratorRepository.findById(id);
        if (oc!=null) {
            return oc.get();
        } else {
            throw new RuntimeException("Collaborator was not found in DB with id " + id);
        }



//        CollaboratorDTO d = this.getCollaboratorDTO(c.get());
//        Optional<Collaborator> c = collaboratorRepository.findById(id);

//        d.setCurrent(c.get());
//        return d;
    }

    private CollaboratorDTO getCollaboratorDTO() {
        CollaboratorDTO result = new CollaboratorDTO();
        result.setCurrent(new Collaborator());
        result.setProvinces(this.provinceRepository.findAll());
        result.setCantons(this.cantonRepository.findByProvince(result.getProvinces().get(0).getId()));
        result.setDistricts(this.districtRepository.findByCanton(result.getCantons().get(0).getId()));

        return getCollaboratorDTO(result);
    }

    private CollaboratorDTO getCollaboratorDTO(Collaborator c) {
	   CollaboratorDTO result = new CollaboratorDTO();
        result.setProvinces(this.provinceRepository.findAll());
        result.setCantons(this.cantonRepository.findByProvince(Integer.parseInt(c.getProvince())));
        result.setDistricts(this.districtRepository.findByCanton(Integer.parseInt(c.getCanton())));
        return getCollaboratorDTO(result);
    }

    private CollaboratorDTO getCollaboratorDTO(CollaboratorDTO c) {
	   c.setDepartments(this.departmentRepository.findAll());
	   c.setCurrencies(this.currencyRepository.findAll());

        String[] tractions = {"N/A", "4x2", "4X4"};
	   c.setTractions(Arrays.asList(tractions));
        String[] status = {"Inactivo", "Activo", "Borrado"};
        String[] typePayrolls = {"MENSUAL", "HORA"};

        c.setStatus(Arrays.asList(status));

	   c.setVehicleFuelTypes(this.fuelRepository.findAll());
	   c.setTypePayrolls(Arrays.asList(typePayrolls));
	   c.setVehiculeTypes(this.vehiculeTypeRepository.findAll());
	   return c;
    }

//	public List<Collaborator> getCollaboratorList(String filterStatus) {
//		return this.collaboratorRepository.getCollaboratorList(filterStatus);
//	}


//	public List<Collaborator> getCollaboratorList2(List<String> filterStatus) {
//		return this.collaboratorRepository.getCollaboratorList2(filterStatus);
//	}

    public void addCollaborator(List<String> cs) {
        if (cs == null) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("Inserting list of Collaborator " + cs.size());
        }
        String[] data;
        Collaborator collaborator = new Collaborator();
        for (String c: cs) {
            try {
                data = StringHelper.stringDataToArray(c, StringHelper.DEFAULT_SEPARATOR);
                //Collaborator = new Collaborator();
                collaborator.setName(data[0].trim());
                save(collaborator);
            } catch (Exception e) {
                log.error("Error inserting Collaborator " + c, e);
            }

        }
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public CountryDataDTO getCantones(Integer id) {
	    CountryDataDTO data = new CountryDataDTO();

	    data.setCantons(this.cantonRepository.findByProvince(id));
	    if (data.getCantons()!=null && data.getCantons().size()>0) {
	        data.setDistricts(this.districtRepository.findByCanton(data.getCantons().get(0).getId()));
        }
	    return data;
    }

    public CountryDataDTO getDistricts(Integer id) {
        CountryDataDTO data = new CountryDataDTO();

        data.setDistricts(this.districtRepository.findByCanton(id));

        return data;
    }
}
