package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerIntegrationTests {

  @Autowired private MockMvc mockMvc;

  private static final String BASE_URL = "http://localhost:8080";

  @BeforeEach
  void setUp() {
    // Runs before each test
  }

  @Test
  void indexSuccess() throws Exception {
    mockMvc
        .perform(get(BASE_URL + "/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Welcome, in order to make an API call")));
  }

  @Test
  void retrieveDepartmentSuccess() throws Exception {
    mockMvc
        .perform(get(BASE_URL + "/retrieveDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Daniel Rubenstein")));
  }

  @Test
  void retrieveDepartmentNotFound() throws Exception {
    mockMvc
        .perform(get(BASE_URL + "/retrieveDept").param("deptCode", "NOPE"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void retrieveCourseSuccess() throws Exception {
    mockMvc
        .perform(
            get(BASE_URL + "/retrieveCourse").param("deptCode", "COMS").param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Adam Cannon")));
  }

  @Test
  void retrieveCourseNotFound() throws Exception {
    mockMvc
        .perform(
            get(BASE_URL + "/retrieveCourse").param("deptCode", "COMS").param("courseCode", "999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void isCourseFullTrue() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/setEnrollmentCount")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("count", "1000"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));

    mockMvc
        .perform(
            get(BASE_URL + "/isCourseFull").param("deptCode", "COMS").param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));

    mockMvc
        .perform(
            patch(BASE_URL + "/setEnrollmentCount")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("count", "250"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  void isCourseFullFalse() throws Exception {
    mockMvc
        .perform(
            get(BASE_URL + "/isCourseFull").param("deptCode", "COMS").param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }

  @Test
  void testGetMajorCountFromDeptSuccess() throws Exception {
    mockMvc
        .perform(get(BASE_URL + "/getMajorCountFromDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("There are: 2700 majors")));
  }

  @Test
  void testGetMajorCountFromDeptNotFound() throws Exception {
    mockMvc
        .perform(get(BASE_URL + "/getMajorCountFromDept").param("deptCode", "MATH"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void identifyDeptChairSuccess() throws Exception {
    mockMvc
        .perform(get(BASE_URL + "/idDeptChair").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("Luca Carloni is the department chair."));
  }

  @Test
  void identifyDeptChairNotFound() throws Exception {
    mockMvc
        .perform(get(BASE_URL + "/idDeptChair").param("deptCode", "MATH"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void findCourseLocationSuccess() throws Exception {
    mockMvc
        .perform(
            get(BASE_URL + "/findCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("417 IAB is where the course is located."));
  }

  @Test
  void findCourseLocationNotFound() throws Exception {
    mockMvc
        .perform(
            get(BASE_URL + "/findCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void findCourseInstructorSuccess() throws Exception {
    mockMvc
        .perform(
            get(BASE_URL + "/findCourseInstructor")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("Adam Cannon is the instructor for the course."));
  }

  @Test
  void findCourseInstructorNotFound() throws Exception {
    mockMvc
        .perform(
            get(BASE_URL + "/findCourseInstructor")
                .param("deptCode", "COMS")
                .param("courseCode", "999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void findCourseTimeSuccess() throws Exception {
    mockMvc
        .perform(
            get(BASE_URL + "/findCourseTime").param("deptCode", "COMS").param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("The course meets at: 11:40-12:55"));
  }

  @Test
  void findCourseTimeNotFound() throws Exception {
    mockMvc
        .perform(
            get(BASE_URL + "/findCourseTime").param("deptCode", "COMS").param("courseCode", "999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void addMajorToDeptSuccess() throws Exception {
    mockMvc
        .perform(patch(BASE_URL + "/addMajorToDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully"));

    mockMvc
        .perform(patch(BASE_URL + "/removeMajorFromDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  @Test
  void addMajorToDeptNotFound() throws Exception {
    mockMvc
        .perform(patch(BASE_URL + "/addMajorToDept").param("deptCode", "MATH"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void removeMajorFromDeptSuccess() throws Exception {
    mockMvc
        .perform(patch(BASE_URL + "/removeMajorFromDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));

    mockMvc
        .perform(patch(BASE_URL + "/addMajorToDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully"));
  }

  @Test
  void removeMajorFromDeptNotFound() throws Exception {
    mockMvc
        .perform(patch(BASE_URL + "/removeMajorFromDept").param("deptCode", "MATH"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  void dropStudentSuccess() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/dropStudentFromCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("Student has been dropped."));
  }

  @Test
  void dropStudentNoStudentsEnrolled() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/setEnrollmentCount")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("count", "0"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));

    mockMvc
        .perform(
            patch(BASE_URL + "/dropStudentFromCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Student has not been dropped."));
  }

  @Test
  void dropStudentNotFound() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/dropStudentFromCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void setEnrollmentCountSuccess() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/setEnrollmentCount")
                .param("deptCode", "COMS")
                .param("courseCode", "4156")
                .param("count", "45"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  void setEnrollmentCountNotFound() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/setEnrollmentCount")
                .param("deptCode", "COMS")
                .param("courseCode", "999")
                .param("count", "45"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void changeCourseTimeSuccess() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/changeCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("time", "4:10-5:25"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));

    mockMvc
        .perform(
            patch(BASE_URL + "/changeCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("time", "11:40-12:55"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  void changeCourseTimeNotFound() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/changeCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "999")
                .param("time", "14:00 - 15:30"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void changeCourseTeacherSuccess() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/changeCourseTeacher")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("teacher", "Griffin Newbold"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));

    mockMvc
        .perform(
            patch(BASE_URL + "/changeCourseTeacher")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("teacher", "Adam Cannon"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  void changeCourseTeacherNotFound() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/changeCourseTeacher")
                .param("deptCode", "COMS")
                .param("courseCode", "999")
                .param("teacher", "Prof. Johnson"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  void changeCourseLocationSuccess() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/changeCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("location", "New Location"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));

    mockMvc
        .perform(
            patch(BASE_URL + "/changeCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("location", "417 IAB"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  void changeCourseLocationNotFound() throws Exception {
    mockMvc
        .perform(
            patch(BASE_URL + "/changeCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "999")
                .param("location", "New Location"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }
}
