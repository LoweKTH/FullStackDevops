package com.fullstackdevops.doctorstaffms.service.implementation;

import com.fullstackdevops.doctorstaffms.dto.StaffDto;
import com.fullstackdevops.doctorstaffms.model.Staff;
import com.fullstackdevops.doctorstaffms.repository.StaffRepository;
import com.fullstackdevops.doctorstaffms.service.StaffService;
import com.fullstackdevops.doctorstaffms.utils.StaffMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;

    @Override
    public StaffDto createStaff(StaffDto staffDto) {
        Staff staff = StaffMapper.toEntity(staffDto);
        staffRepository.save(staff);
        return StaffMapper.toDto(staff);
    }

}
