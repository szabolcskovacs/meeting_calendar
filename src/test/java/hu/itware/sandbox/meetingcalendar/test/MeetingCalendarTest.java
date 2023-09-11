package hu.itware.sandbox.meetingcalendar.test;

import hu.itware.sandbox.meetingcalendar.exception.MeetingCalendarBaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "classpath:init/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MeetingCalendarTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void tooLongMeeting() throws Exception {
        final File jsonFile = new ClassPathResource("test_cases/01_tooLongMeeting.json").getFile();
        final String createMeeting = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createMeeting)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(MeetingCalendarBaseException.ErrorCode.MEETING_TOO_LONG.toString()));

    }

    @Test
    public void notGoodStartMeeting() throws Exception {
        final File jsonFile = new ClassPathResource("test_cases/02_notGoodStartMeeting.json").getFile();
        final String createMeeting = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMeeting)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(MeetingCalendarBaseException.ErrorCode.IMPROPER_START_TIME.toString()));


    }
    @Test
    public void tooEarlyMeeting() throws Exception {
        final File jsonFile = new ClassPathResource("test_cases/03_tooEarlyMeeting.json").getFile();
        final String createMeeting = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMeeting)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(MeetingCalendarBaseException.ErrorCode.START_TOO_EARLY.toString()));


    }
    @Test
    public void notProperLengthMeeting() throws Exception {
        final File jsonFile = new ClassPathResource("test_cases/04_notGoodEndMeeting.json").getFile();
        final String createMeeting = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMeeting)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(MeetingCalendarBaseException.ErrorCode.IMPROPER_END_TIME.toString()));


    }
    @Test
    public void overlapMeeting() throws Exception {
        final File jsonFile = new ClassPathResource("test_cases/05_01_overlapMeeting.json").getFile();
        final String createMeeting = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMeeting)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        final File jsonFile2 = new ClassPathResource("test_cases/05_02_overlapMeeting.json").getFile();
        final String createMeeting2 = Files.readString(jsonFile2.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMeeting2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(MeetingCalendarBaseException.ErrorCode.OVERLAP_WITH_OTHER.toString()));


    }

    @Test
    public void batchMeeting() throws Exception {
        final File jsonFile = new ClassPathResource("test_cases/06_01_batchMeeting.json").getFile();
        final String createMeeting = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMeeting)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        final File jsonFile2 = new ClassPathResource("test_cases/06_02_batchMeeting.json").getFile();
        final String createMeeting2 = Files.readString(jsonFile2.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMeeting2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        final File jsonFile3 = new ClassPathResource("test_cases/06_03_batchMeeting.json").getFile();
        final String createMeeting3 = Files.readString(jsonFile3.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMeeting3)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        final File jsonFile4 = new ClassPathResource("test_cases/06_04_batchMeeting.json").getFile();
        final String createMeeting4 = Files.readString(jsonFile4.toPath());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMeeting4)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }

}
