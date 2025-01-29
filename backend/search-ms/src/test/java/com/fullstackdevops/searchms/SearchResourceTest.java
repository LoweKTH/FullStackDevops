package com.fullstackdevops.searchms;

import com.fullstackdevops.searchms.config.PatientMSClient;
import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.service.SearchService;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

public class SearchResourceTest {

    @InjectMocks
    private SearchService searchService;
    @Mock
    private PatientMSClient patientMSClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchPatientsByName() {

        String name = "John Doe";

        PatientDto patient1 = new PatientDto(
                1L, 123456789L, "user1", "John", "Doe",
                new Date(), "1234 Elm Street", "Male", "john.doe@example.com",
                "123-456-7890", null, null);

        PatientDto patient2 = new PatientDto(
                2L, 987654321L, "user2", "John", "Doe",
                new Date(), "5678 Oak Avenue", "Male", "john.doe2@example.com",
                "987-654-3210", null, null);
        List<PatientDto> patientList = Arrays.asList(patient1, patient2);

        when(patientMSClient.getPatientsByName(name)).thenReturn(Uni.createFrom().item(patientList));

        Uni<List<PatientDto>> result = searchService.searchPatientsByName(name);

        result.subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted()
                .assertItem(patientList);

    }

    @Test
    public void testSearchPatientsByName_noPatientsFound() {

        String name = "Jane Doe";

        when(patientMSClient.getPatientsByName(name)).thenReturn(Uni.createFrom().item(Arrays.asList()));

        Uni<List<PatientDto>> result = searchService.searchPatientsByName(name);

        result.subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted()
                .assertItem(Arrays.asList());
    }
}