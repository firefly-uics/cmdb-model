/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/dto/EntityDTOService.java.e.vm
 */
package uk.co.boots.columbus.cmdb.model.environment.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.boots.columbus.cmdb.model.core.dto.support.PageRequestByExample;
import uk.co.boots.columbus.cmdb.model.core.dto.support.PageResponse;
import uk.co.boots.columbus.cmdb.model.environment.domain.Environment;
import uk.co.boots.columbus.cmdb.model.environment.domain.EnvironmentConfig;
import uk.co.boots.columbus.cmdb.model.environment.repository.EnvironmentConfigRepository;
import uk.co.boots.columbus.cmdb.model.environment.repository.EnvironmentRepository;
import uk.co.boots.columbus.cmdb.model.server.domain.ServerConfig;

/**
 * A simple DTO Facility for EnvironmentConfig.
 */
@Service
public class EnvironmentConfigDTOService {

    private EnvironmentConfigRepository environmentConfigRepository;
    @Inject
    private EnvironmentDTOService environmentDTOService;
    @Inject
    private EnvironmentRepository environmentRepository;

    @Transactional(readOnly = true)
    public EnvironmentConfigDTO findOne(Long id) {
        //return toDTO(environmentConfigRepository.findOne(id));
    	return null;
    }

    private void buildHieraAddresses (List<EnvironmentConfig> cl){
    	String addr;
    	String value;
    	for (EnvironmentConfig conf: cl){
        	addr = conf.getHieraAddress();
        	value = conf.getValue();
        	//find Parameter in Hieara Address and replace with Parametername
        	if (addr != null){
        		addr = addr.replaceAll("\\{ParamName\\}",conf.getParameter());
        		addr = addr.replaceAll("\\{ENVID\\}", conf.getEnvironment().getName());
            	conf.setHieraAddress(addr);
        	}
        	if (value != null){
        		value = value.replaceAll("\\{ParamName\\}",conf.getParameter());
        		value = value.replaceAll("\\{ENVID\\}", conf.getEnvironment().getName());
        		conf.setValue(value);
        	}
        }
    }
//    @Transactional(readOnly = true)
    public List<EnvironmentConfigDTO> findByEnvironmentName(String envName) {
    /*
    	List<EnvironmentConfig> results = environmentConfigRepository.findByEnvironment_name(envName);
        buildHieraAddresses (results);
        return results.stream().map(this::toDTO).collect(Collectors.toList());
        */
    	return null;
    }

//    @Transactional(readOnly = true)
    public List<EnvironmentConfigDTO> findByEnvironmentReleaseName(String relName) {
        //List<EnvironmentConfig> results = environmentConfigRepository.findByEnvironment_Release_name(relName);
        //buildHieraAddresses (results);
        //return results.stream().map(this::toDTO).collect(Collectors.toList());
    	return null;
    }
    
 //   @Transactional(readOnly = true)
    public List<EnvironmentConfigDTO> complete(String query, int maxResults) {
    	/*
    	List<EnvironmentConfig> results = environmentConfigRepository.complete(query, maxResults);
        return results.stream().map(this::toDTO).collect(Collectors.toList());
                */
    	return null;

    }

//    @Transactional(readOnly = true)
    public PageResponse<EnvironmentConfigDTO> findAll(PageRequestByExample<EnvironmentConfigDTO> req) {
/*
    	Example<EnvironmentConfig> example = null;
        EnvironmentConfig environmentConfig = toEntity(req.example);

        if (environmentConfig != null) {
            example = Example.of(environmentConfig);
        }

        Page<EnvironmentConfig> page;
        if (example != null) {
            page = environmentConfigRepository.findAll(example, req.toPageable());
        } else {
            page = environmentConfigRepository.findAll(req.toPageable());
        }

        List<EnvironmentConfigDTO> content = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
            */
    	return null;

    }

    /**
     * Save the passed dto as a new entity or update the corresponding entity if any.
     */
//    @Transactional
    public EnvironmentConfigDTO save(EnvironmentConfigDTO dto) {
/*
    	if (dto == null) {
            return null;
        }

        EnvironmentConfig environmentConfig;
        if (dto.isIdSet()) {
            environmentConfig = environmentConfigRepository.findOne(dto.id);
        } else {
            environmentConfig = new EnvironmentConfig();
        }

        environmentConfig.setParameter(dto.parameter);
        environmentConfig.setValue(dto.value);
        environmentConfig.setHieraAddress(dto.hieraAddress);

        if (dto.environment == null) {
            environmentConfig.setEnvironment(null);
        } else {
            Environment environment = environmentConfig.getEnvironment();
            if (environment == null || (environment.getId().compareTo(dto.environment.id) != 0)) {
                environmentConfig.setEnvironment(environmentRepository.findOne(dto.environment.id));
            }
        }

        return toDTO(environmentConfigRepository.save(environmentConfig));
                */
    	return null;

    }

    /**
     * Converts the passed environmentConfig to a DTO.
     */
    public EnvironmentConfigDTO toDTO(EnvironmentConfig environmentConfig) {
        return toDTO(environmentConfig, 1);
    }

    /**
     * Converts the passed environmentConfig to a DTO. The depth is used to control the
     * amount of association you want. It also prevents potential infinite serialization cycles.
     *
     * @param environmentConfig
     * @param depth the depth of the serialization. A depth equals to 0, means no x-to-one association will be serialized.
     *              A depth equals to 1 means that xToOne associations will be serialized. 2 means, xToOne associations of
     *              xToOne associations will be serialized, etc.
     */
    public EnvironmentConfigDTO toDTO(EnvironmentConfig environmentConfig, int depth) {
        if (environmentConfig == null) {
            return null;
        }

        EnvironmentConfigDTO dto = new EnvironmentConfigDTO();

        dto.id = environmentConfig.getId();
        dto.parameter = environmentConfig.getParameter();
        dto.value = environmentConfig.getValue();
        dto.hieraAddress = environmentConfig.getHieraAddress();
        if (depth-- > 0) {
            dto.environment = environmentDTOService.toDTO(environmentConfig.getEnvironment(), depth);
        }

        return dto;
    }

    /**
     * Converts the passed dto to a EnvironmentConfig.
     * Convenient for query by example.
     */
    public EnvironmentConfig toEntity(EnvironmentConfigDTO dto) {
        return toEntity(dto, 1);
    }

    /**
     * Converts the passed dto to a EnvironmentConfig.
     * Convenient for query by example.
     */
    public EnvironmentConfig toEntity(EnvironmentConfigDTO dto, int depth) {
        if (dto == null) {
            return null;
        }

        EnvironmentConfig environmentConfig = new EnvironmentConfig();

        environmentConfig.setId(dto.id);
        environmentConfig.setParameter(dto.parameter);
        environmentConfig.setValue(dto.value);
        environmentConfig.setHieraAddress(dto.hieraAddress);
        if (depth-- > 0) {
            environmentConfig.setEnvironment(environmentDTOService.toEntity(dto.environment, depth));
        }

        return environmentConfig;
    }
}