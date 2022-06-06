package pl.project.ProjectManagement.intTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.project.ProjectManagement.ProjectManagementApplication;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.model.request.AccessDataPayload;
import pl.project.ProjectManagement.model.request.TokenWithEmailPayload;
import pl.project.ProjectManagement.model.request.TokenWithPasswordPayload;
import pl.project.ProjectManagement.model.request.UpdateRolePayload;
import pl.project.ProjectManagement.model.response.JwtResponse;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {ProjectManagementApplication.class})
@SpringBootTest
public class PersonIntTests {
    private final String path = "/person";
    private final AccessDataPayload accessData = new AccessDataPayload("test@test.com", "password123");

    private final TokenWithPasswordPayload tokenWithPasswordPayload = new TokenWithPasswordPayload("token",
            "test123");
    private final TokenWithEmailPayload tokenWithEmailPayload = new TokenWithEmailPayload("token",
            "test@test.com");
    @MockBean
    PersonService personService;

    @MockBean
    InfoService infoService;
    @Autowired
    private MockMvc mvc;

    @Test
    public void setPerson_shouldContainStatus_OK() throws Exception {
        when(this.personService.setPerson(any(AccessDataPayload.class))).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(this.accessData);

        this.mvc.perform(post(String.format("%s/reg", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }

    @Test
    public void setPerson_shouldContainStatus_BAD_REQUEST() throws Exception {
        when(this.personService.setPerson(any(AccessDataPayload.class))).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(this.accessData);

        this.mvc.perform(post(String.format("%s/reg", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));
    }

    @Test
    public void authenticate_shouldContainStatus_OK() throws Exception {
        JwtResponse jwtResponse = new JwtResponse("Jwt","test@test.pl", Role.USER);
        when(this.personService.authenticate(any(AccessDataPayload.class))).thenReturn(Optional.of(jwtResponse));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(this.accessData);

        this.mvc.perform(post(String.format("%s/log", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Jwt")));
    }

    @Test
    public void authenticate_shouldContainStatus_BAD_REQUEST() throws Exception {
        when(this.personService.authenticate(any(AccessDataPayload.class))).thenReturn(Optional.empty());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(this.accessData);

        this.mvc.perform(post(String.format("%s/log", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("NOT_FOUND")));
    }

    @Test
    public void getAdminToken_shouldContainStatus_OK() throws Exception {

        when(this.personService.getAdminToken(any(AccessDataPayload.class))).thenReturn(Optional.of("AdminToken"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(this.accessData);

        this.mvc.perform(get(String.format("%s/admin", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("AdminToken")));
    }

    @Test
    public void getAdminToken_shouldContainStatus_BAD_REQUEST() throws Exception {
        when(this.personService.getAdminToken(any(AccessDataPayload.class))).thenReturn(Optional.empty());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(this.accessData);

        this.mvc.perform(get(String.format("%s/admin", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("NOT_FOUND")));
    }

    @Test
    public void updatePersonPassword_shouldContainStatus_OK() throws Exception {
        when(this.personService.updatePersonPassword(anyString(), anyString())).thenReturn(true);


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(this.tokenWithPasswordPayload);

        this.mvc.perform(put(String.format("%s/pass", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }

    @Test
    public void updatePersonPassword_shouldContainStatus_BAD_REQUEST() throws Exception {
        when(this.personService.updatePersonPassword(anyString(), anyString())).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(this.tokenWithPasswordPayload);

        this.mvc.perform(put(String.format("%s/pass", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));
    }
    @Test
    public void deletePerson_shouldContainStatus_OK() throws Exception {
        when(this.personService.deletePerson(anyString(),anyString())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(this.tokenWithPasswordPayload);

        this.mvc.perform(delete(this.path).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }
    @Test
    public void deletePerson_shouldContainStatus_BAD_REQUEST() throws Exception {
        when(this.personService.deletePerson(anyString(),anyString())).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(this.tokenWithPasswordPayload);

        this.mvc.perform(delete(this.path).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));
    }
    @Test
    public void updateRole_shouldContainStatus_OK() throws Exception {
        UpdateRolePayload updateRolePayload = new UpdateRolePayload("test@test.pl","adminToken",Role.ADMIN);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.personService.updateRole(anyString(), anyString(),anyString(),any(Role.class))).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(updateRolePayload);

        mvc.perform(put(String.format("%s/role", this.path)).content(requestJson)
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }
    @Test
    public void updateRole_shouldContainStatus_BAD_REQUEST() throws Exception {
        UpdateRolePayload updateRolePayload = new UpdateRolePayload("test@test.pl","adminToken",Role.ADMIN);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.personService.updateRole(anyString(), anyString(),anyString(),any(Role.class))).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(updateRolePayload);

        mvc.perform(put(String.format("%s/role", this.path)).content(requestJson)
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));
    }
    @Test
    public void updateRole_withoutHeader_BadRequest() throws Exception {
        UpdateRolePayload updateRolePayload = new UpdateRolePayload("test@test.pl","adminToken",Role.ADMIN);
        when(this.personService.updateRole(anyString(), anyString(),anyString(),any(Role.class))).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(updateRolePayload);

        mvc.perform(put(String.format("%s/role", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
