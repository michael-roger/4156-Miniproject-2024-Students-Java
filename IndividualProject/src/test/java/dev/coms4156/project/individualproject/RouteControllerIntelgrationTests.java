package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "http://localhost:8080";

    @BeforeEach
    void setUp() {}

    @Test
    void index() throws Exception {
        mockMvc.perform(get(BASE_URL + "/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome, in order to make an API call")));
    }

    @Test
    void retrieveDepartment_Success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/retrieveDept")
                        .param("deptCode", "COMS"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Daniel Rubenstein")));
    }

    @Test
    void retrieveDepartment_NotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/retrieveDept")
                        .param("deptCode", "NOPE"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
    }

    @Test
    void retrieveCourse_Success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/retrieveCourse")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Adam Cannon")));
    }

    @Test
    void retrieveCourse_NotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/retrieveCourse")
                        .param("deptCode", "COMS")
                        .param("courseCode", "999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
    }

    @Test
    void isCourseFull_True() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/setEnrollmentCount")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004")
                        .param("count", "1000"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));

        mockMvc.perform(get(BASE_URL + "/isCourseFull")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void isCourseFull_False() throws Exception {
        mockMvc.perform(get(BASE_URL + "/isCourseFull")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void testGetMajorCountFromDept_Success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/getMajorCountFromDept")
                        .param("deptCode", "COMS"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("There are: 2700 majors")));
    }

    @Test
    void testGetMajorCountFromDept_NotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/getMajorCountFromDept")
                        .param("deptCode", "MATH"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
    }

    @Test
    void identifyDeptChair_Success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/idDeptChair")
                        .param("deptCode", "COMS"))
                .andExpect(status().isOk())
                .andExpect(content().string("Luca Carloni is the department chair."));
    }

    @Test
    void identifyDeptChair_NotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/idDeptChair")
                        .param("deptCode", "MATH"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
    }

    @Test
    void findCourseLocation_Success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/findCourseLocation")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004"))
                .andExpect(status().isOk())
                .andExpect(content().string("417 IAB is where the course is located."));
    }

    @Test
    void findCourseLocation_NotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/findCourseLocation")
                        .param("deptCode", "COMS")
                        .param("courseCode", "999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
    }

    @Test
    void findCourseInstructor_Success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/findCourseInstructor")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004"))
                .andExpect(status().isOk())
                .andExpect(content().string("Adam Cannon is the instructor for the course."));
    }

    @Test
    void findCourseInstructor_NotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/findCourseInstructor")
                        .param("deptCode", "COMS")
                        .param("courseCode", "999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
    }

    @Test
    void findCourseTime_Success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/findCourseTime")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004"))
                .andExpect(status().isOk())
                .andExpect(content().string("The course meets at: 11:40-12:55"));
    }

    @Test
    void findCourseTime_NotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/findCourseTime")
                        .param("deptCode", "COMS")
                        .param("courseCode", "999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
    }

    @Test
    void testAddMajorToDept_Success() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/addMajorToDept")
                        .param("deptCode", "COMS"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attribute was updated successfully"));
    }

    @Test
    void testAddMajorToDept_NotFound() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/addMajorToDept")
                        .param("deptCode", "MATH"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
    }

    @Test
    void removeMajorFromDept_Success() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/removeMajorFromDept")
                        .param("deptCode", "COMS"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attribute was updated or is at minimum"));
    }

    @Test
    void removeMajorFromDept_NotFound() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/removeMajorFromDept")
                        .param("deptCode", "MATH"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department Not Found"));
    }

    @Test
    void dropStudent_Success() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/dropStudentFromCourse")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student has been dropped."));
    }

    @Test
    void dropStudent_NoStudentsEnrolled() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/setEnrollmentCount")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004")
                        .param("count", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));

        mockMvc.perform(patch(BASE_URL + "/dropStudentFromCourse")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Student has not been dropped."));
    }

    @Test
    void dropStudent_NotFound() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/dropStudentFromCourse")
                        .param("deptCode", "COMS")
                        .param("courseCode", "999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
    }

    @Test
    void setEnrollmentCount_Success() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/setEnrollmentCount")
                        .param("deptCode", "COMS")
                        .param("courseCode", "4156")
                        .param("count", "45"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));
    }

    @Test
    void setEnrollmentCount_NotFound() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/setEnrollmentCount")
                        .param("deptCode", "COMS")
                        .param("courseCode", "999")
                        .param("count", "45"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
    }

    @Test
    void changeCourseTime_Success() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/changeCourseTime")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004")
                        .param("time", "4:10-5:25"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));

        mockMvc.perform(patch(BASE_URL + "/changeCourseTime")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004")
                        .param("time", "11:40-12:55"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));
    }

    @Test
    void changeCourseTime_NotFound() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/changeCourseTime")
                        .param("deptCode", "COMS")
                        .param("courseCode", "999")
                        .param("time", "14:00 - 15:30"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
    }

    @Test
    void changeCourseTeacher_Success() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/changeCourseTeacher")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004")
                        .param("teacher", "Griffin Newbold"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));

        mockMvc.perform(patch(BASE_URL + "/changeCourseTeacher")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004")
                        .param("teacher", "Adam Cannon"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));
    }

    @Test
    void changeCourseTeacher_NotFound() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/changeCourseTeacher")
                        .param("deptCode", "COMS")
                        .param("courseCode", "999")
                        .param("teacher", "Prof. Johnson"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
    }

    @Test
    void changeCourseLocation_Success() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/changeCourseLocation")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004")
                        .param("location", "New Location"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));

        mockMvc.perform(patch(BASE_URL + "/changeCourseLocation")
                        .param("deptCode", "COMS")
                        .param("courseCode", "1004")
                        .param("location", "417 IAB"))
                .andExpect(status().isOk())
                .andExpect(content().string("Attributed was updated successfully."));
    }

    @Test
    void changeCourseLocation_NotFound() throws Exception {
        mockMvc.perform(patch(BASE_URL + "/changeCourseLocation")
                        .param("deptCode", "COMS")
                        .param("courseCode", "999")
                        .param("location", "New Location"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course Not Found"));
    }
}
