package com.narcissisticengineer.smartHospitalAvailabilitySystem.tools.config;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.HospitalDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Hospital;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<HospitalDTO, Hospital> hospitalUpdateMap =
                modelMapper.createTypeMap(HospitalDTO.class, Hospital.class);

        hospitalUpdateMap.addMappings(mapper -> mapper.skip(Hospital::setId));

        hospitalUpdateMap.addMappings(mapper -> mapper.skip(Hospital::setDoctors));
        hospitalUpdateMap.addMappings(mapper -> mapper.skip(Hospital::setAmbulances));

        return modelMapper;
    }
}