package nx.ese.controllers;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import nx.ese.documents.core.CourseName;
import nx.ese.dtos.CourseDto;
import nx.ese.dtos.UserDto;
import nx.ese.services.CourseRestService;
import nx.ese.services.HttpMatcher;
import nx.ese.services.RestService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class CourseControllerIT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Autowired
    private CourseRestService courseRestService;

    @Before
    public void before() {
        courseRestService.createCoursesDto();
    }

    @After
    public void delete() {
        courseRestService.deleteCourses();
    }

    // POST******************************************
    @Test
    public void testPostCourse() {
        courseRestService.postCourse();

        CourseDto cDto = courseRestService.getCourseById(courseRestService.getCourseDto().getId());
        assertEquals(cDto, courseRestService.getCourseDto());
    }

    @Test
    public void testPostCoursePreAuthorize() {
        restService.loginTeacher();// PreAuthorize("hasRole('MANAGER')")

        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        courseRestService.postCourse();

    }

    @Test
    public void testPostCourseNoBearerAuth() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .body(courseRestService.getCourseDto())
                .post()
                .build();
    }

    @Test
    public void testPostCourseFieldInvalidExceptionId() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.postCourse(); // it goes with Id
    }

    @Test
    public void testPostCourseNameAnYearRepeated() {
        courseRestService.postCourse();

        courseRestService.getCourseDto2().setName(courseRestService.getCourseDto().getName());
        courseRestService.getCourseDto2().setYear(courseRestService.getCourseDto().getYear());

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.postCourse2();
    }

    @Test
    public void testPostCourseChiefTeacherRepeated() {
        courseRestService.postCourse();

        courseRestService.getCourseDto2().setChiefTeacher(courseRestService.getCourseDto().getChiefTeacher());

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.postCourse2();
    }

    @Test
    public void testPostCourseStudentsRepeatedInDto() {
        courseRestService.postCourse();

        List<UserDto> students = courseRestService.getCourseDto2().getStudents();
        students.add(courseRestService.getCourseDto2().getStudents().get(0));
        courseRestService.getCourseDto2().setStudents(students);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.postCourse2();
    }

    @Test
    public void testPostCourseStudentsRepeatedInOtherCourse() {
        courseRestService.postCourse();

        List<UserDto> students = courseRestService.getCourseDto2().getStudents();
        students.add(courseRestService.getCourseDto().getStudents().get(0));
        courseRestService.getCourseDto2().setStudents(students);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.postCourse2();
    }

    @Test
    public void testPostCourseBodyNull() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .bearerAuth(restService.getAuthToken().getToken())
                .body(null)
                .post()
                .build();
    }

    @Test
    public void testPostCourseNameNull() {
        courseRestService.getCourseDto().setName(null);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.postCourse();
    }

    @Test
    public void testPostCourseChiefTeacherNull() {
        courseRestService.getCourseDto().setChiefTeacher(null);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.postCourse();
    }

    @Test
    public void testPostCourseYearNull() {
        courseRestService.getCourseDto().setYear(null);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.postCourse();
    }

    // PUT******************************************
    @Test
    public void testPutCourse() {
        courseRestService.postCourse();
        courseRestService.getCourseDto().setChiefTeacher(courseRestService.getCourseDto2().getChiefTeacher());

        courseRestService.putCourse();

        CourseDto cDto = courseRestService.getCourseById(courseRestService.getCourseDto().getId());
        assertEquals(cDto.getChiefTeacher(), courseRestService.getCourseDto().getChiefTeacher());
    }

    @Test
    public void testPutCoursePreAuthorize() {
        courseRestService.postCourse();
        courseRestService.getCourseDto().setChiefTeacher(courseRestService.getCourseDto2().getChiefTeacher());

        restService.loginTeacher();// PreAuthorize("hasRole('MANAGER')")
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        courseRestService.putCourse();
    }

    @Test
    public void testPutCourseNoBearerAuth() {
        courseRestService.postCourse();
        courseRestService.getCourseDto().setChiefTeacher(courseRestService.getCourseDto2().getChiefTeacher());

        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .body(courseRestService.getCourseDto())
                .put()
                .build();
    }

    @Test
    public void testPutCourseNameAndYearRepeated() {
        courseRestService.postCourse();
        courseRestService.postCourse2();

        courseRestService.getCourseDto().setName(courseRestService.getCourseDto2().getName());
        courseRestService.getCourseDto().setYear(courseRestService.getCourseDto2().getYear());

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.putCourse();
    }

    @Test
    public void testPutCourseChiefTeacherRepeated() {
        courseRestService.postCourse();
        courseRestService.postCourse2();

        courseRestService.getCourseDto().setChiefTeacher(courseRestService.getCourseDto2().getChiefTeacher());

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.putCourse();
    }

    @Test
    public void testPutCourseStudentsRepeatedInDto() {
        courseRestService.postCourse();
        courseRestService.postCourse2();

        List<UserDto> students = courseRestService.getCourseDto2().getStudents();
        students.add(courseRestService.getCourseDto2().getStudents().get(0));
        courseRestService.getCourseDto().setStudents(students);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.putCourse();
    }

    @Test
    public void testPutCourseStudentsRepeated() {
        courseRestService.postCourse();
        courseRestService.postCourse2();

        List<UserDto> students = courseRestService.getCourseDto().getStudents();
        students.add(courseRestService.getCourseDto2().getStudents().get(0));
        courseRestService.getCourseDto().setStudents(students);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.putCourse();
    }

    @Test
    public void testPutCourseFieldNotFoundExceptionId() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .path(CourseController.PATH_ID).expand("xxx")
                .bearerAuth(restService.getAuthToken().getToken())
                .body(courseRestService.getCourseDto())
                .put()
                .build();
    }

    @Test
    public void testPutCourseIdNull() {
        thrown.expect(new HttpMatcher(HttpStatus.METHOD_NOT_ALLOWED));
        courseRestService.putCourse();
    }

    @Test
    public void testPutCourseBodyNull() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .path(CourseController.PATH_ID)
                .expand(courseRestService.getCourseDto().getId())
                .bearerAuth(restService.getAuthToken().getToken())
                .body(null)
                .put()
                .build();
    }

    @Test
    public void testPutCourseNameNull() {
        courseRestService.postCourse();

        courseRestService.getCourseDto().setName(null);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.putCourse();
    }

    @Test
    public void testPutCourseChiefTeacherNull() {
        courseRestService.postCourse();

        courseRestService.getCourseDto().setChiefTeacher(null);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.putCourse();
    }

    @Test
    public void testPutCourseYearNull() {
        courseRestService.postCourse();

        courseRestService.getCourseDto().setYear(null);

        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        courseRestService.putCourse();
    }

    // DELETE******************************************
    @Test
    public void testDeleteCourse() {
        courseRestService.postCourse();
        courseRestService.deleteCourse(courseRestService.getCourseDto().getId());

        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        courseRestService.getCourseById(courseRestService.getCourseDto().getId());
    }

    @Test
    public void testDeleteCoursePreAuthorize() {
        courseRestService.postCourse();

        restService.loginTeacher();// PreAuthorize("hasRole('MANAGER')")
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        courseRestService.deleteCourse(courseRestService.getCourseDto().getId());
    }

    @Test
    public void testDeleteCourseNoBearerAuth() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .path(CourseController.COURSE)
                .path(CourseController.PATH_ID).expand(courseRestService.getCourseDto()
                .getId())
                .delete()
                .build();
    }

    @Test
    public void testDeleteCourseForbiddenDeleteExceptionSubject() {
        CourseDto cDto = courseRestService.getCourseByNameAndYear(CourseName.OCTAVO_C, "2018");
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        courseRestService.deleteCourse(cDto.getId());
    }

    @Test
    public void testDeleteCourseFieldNotFoundExceptionId() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        courseRestService.deleteCourse("xxx");

    }

    // GET******************************************
    @Test
    public void testGetCourseByNameAndYear() {
        courseRestService.postCourse();

        CourseDto cDto = courseRestService.getCourseByNameAndYear(courseRestService.getCourseDto().getName(),
                courseRestService.getCourseDto().getYear());
        Assert.assertTrue(courseRestService.getCourseDto().getName().equals(cDto.getName()));
    }

    @Test
    public void testGetCourseByNamePreAuthorize() {
        courseRestService.postCourse();

        restService.loginTeacher();// PreAuthorize("hasRole('MANAGER')")
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        courseRestService.getCourseByNameAndYear(courseRestService.getCourseDto().getName(),
                courseRestService.getCourseDto().getYear());
    }

    @Test
    public void testGetCourseByNameNoBearerAuth() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .path(CourseController.NAME)
                .path(CourseController.PATH_NAME).expand(courseRestService.getCourseDto().getName())
                .path(CourseController.PATH_YEAR).expand(courseRestService.getCourseDto().getYear())
                .get()
                .build();
    }

    @Test
    public void testGetCourseByNameDocumentNotFoundException() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        courseRestService.getCourseByNameAndYear(CourseName.PRIMERO_A, "1820");
    }

    //
    @Test
    public void testGetCourseById() {
        courseRestService.postCourse();

        CourseDto cDto = courseRestService.getCourseById(courseRestService.getCourseDto().getId());
        Assert.assertTrue(courseRestService.getCourseDto().getId().equals(cDto.getId()));
    }

    @Test
    public void testGetCourseByIdPreAuthorize() {
        courseRestService.postCourse();

        restService.loginTeacher();// PreAuthorize("hasRole('MANAGER')")
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        courseRestService.getCourseById(courseRestService.getCourseDto().getId());
    }

    @Test
    public void testGetCourseByIdNoBearerAuth() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .path(CourseController.PATH_ID)
                .expand(courseRestService.getCourseDto().getId())
                .get()
                .build();
    }

    @Test
    public void testGetCourseByIdFieldNotFoundException() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        courseRestService.getCourseById("xxx");

    }

    //
    @Test
    public void testGetCoursesByYear() {
        courseRestService.postCourse();

        List<CourseDto> cDtos = courseRestService.getCoursesByYear("2018");
        Assert.assertTrue(cDtos.size() > 0);
    }

    @Test
    public void testGetCoursesByYearPreAuthorize() {
        courseRestService.postCourse();

        restService.loginStudent();// PreAuthorize("hasRole('MANAGER') or 'TEACHER'")

        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        courseRestService.getCoursesByYear("2018");

    }

    @Test
    public void testGetCoursesByYearNoBearerAuth() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .path(CourseController.YEAR)
                .path(CourseController.PATH_YEAR).expand(courseRestService.getCourseDto().getYear())
                .get()
                .build();
    }

    @Test
    public void testGetCoursesByYearEmptyList() {
        assertEquals(courseRestService.getCoursesByYear("1820"), Collections.emptyList());
    }

    //
    @Test
    public void getCourseByChiefTeacherAndYear() {
        courseRestService.postCourse();

        CourseDto cDto = courseRestService.getCourseByChiefTeacherAndYear(
                courseRestService.getCourseDto().getChiefTeacher().getUsername(),
                courseRestService.getCourseDto().getYear());
        assertEquals(courseRestService.getCourseDto().getChiefTeacher().getUsername(),
                (cDto.getChiefTeacher().getUsername()));
    }

    @Test
    public void getCourseByChiefTeacherAndYearPreAuthorize() {
        courseRestService.postCourse();

        restService.loginStudent(); // PreAuthorize("hasRole('MANAGER') or 'TEACHER'")

        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        courseRestService.getCourseByChiefTeacherAndYear(courseRestService.getCourseDto().getChiefTeacher().getUsername(), courseRestService.getCourseDto().getYear());
    }

    @Test
    public void getCourseByChiefTeacherAndYearNoBearerAuth() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .path(CourseController.TEACHER)
                .path(CourseController.PATH_USERNAME)
                .expand(courseRestService.getCourseDto().getChiefTeacher().getUsername())
                .path(CourseController.PATH_YEAR).expand(courseRestService.getCourseDto().getYear())
                .get()
                .build();
    }

    @Test
    public void getCourseByChiefTeacherAndYearFieldNotFoundExceptionUsername() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        courseRestService.getCourseByChiefTeacherAndYear("rupertina", courseRestService.getCourseDto().getYear());

    }

    @Test
    public void getCourseByChiefTeacherAndYearDocumentNotFoundException() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        courseRestService.getCourseByChiefTeacherAndYear(
                courseRestService.getCourseDto().getChiefTeacher().getUsername(), "1820");

    }

    //
    @Test
    public void getCourseIdByStudentAndYear() {
        courseRestService.postCourse();

        String cId = courseRestService.getCourseIdByStudentAndYear(courseRestService.getCourseDto().getStudents().get(0).getUsername(), courseRestService.getCourseDto().getYear());
        assertEquals(courseRestService.getCourseDto().getId(), cId);
    }

    @Test
    public void getCourseIdByStudentAndYearNoBearerAuth() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder()
                .path(CourseController.COURSE)
                .path(CourseController.STUDENT)
                .path(CourseController.PATH_USERNAME).expand(courseRestService.getCourseDto().getStudents().get(0).getUsername())
                .path(CourseController.PATH_YEAR).expand(courseRestService.getCourseDto().getYear())
                .get()
                .build();
    }

    @Test
    public void getCourseIdByStudentAndYearFieldNotFoundExceptionId() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        courseRestService.getCourseIdByStudentAndYear("rupertina", courseRestService.getCourseDto().getYear());
    }

    @Test
    public void getCourseIdByStudentAndYearDocumentNotFoundException() {
        courseRestService.postCourse();

        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        courseRestService.getCourseIdByStudentAndYear(courseRestService.getCourseDto().getStudents().get(0).getUsername(), "1820");
    }

}
