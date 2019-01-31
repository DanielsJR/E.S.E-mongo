package nx.ESE.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nx.ESE.controllers.SubjectController;
import nx.ESE.documents.core.SubjectName;
import nx.ESE.dtos.SubjectDto;


@Service
public class SubjectRestService {

	@Autowired
	private RestService restService;

	@Autowired
	private UserRestService userRestService;

	@Autowired
	private CourseRestService courseRestService;

	private SubjectDto subjectDto;
	
	private SubjectDto subjectDto2;
	
	private List<SubjectDto> listSubjectDto;

	private static final Logger logger = LoggerFactory.getLogger(SubjectRestService.class);

	public SubjectDto getSubjectDto() {
		return subjectDto;
	}

	public void setSubjectDto(SubjectDto subjectDto) {
		this.subjectDto = subjectDto;
	}
	
	

	public SubjectDto getSubjectDto2() {
		return subjectDto2;
	}

	public void setSubjectDto2(SubjectDto subjectDto2) {
		this.subjectDto2 = subjectDto2;
	}

	public List<SubjectDto> getListSubjectDto() {
		return listSubjectDto;
	}

	public void setListSubjectDto(List<SubjectDto> listSubjectDto) {
		this.listSubjectDto = listSubjectDto;
	}

	public void createSubjectsDto() {
		logger.warn(
				"*********************************CREATING_SUBJECTS**************************************************");
		courseRestService.createCoursesDto();
		courseRestService.postCourse();
		courseRestService.postCourse2();

		this.subjectDto = new SubjectDto();
		this.subjectDto.setName(SubjectName.MATEMATICAS);
		this.subjectDto.setTeacher(userRestService.getTeacherDto2());
		this.subjectDto.setCourse(courseRestService.getCourseDto());
		
		this.subjectDto2 = new SubjectDto();
		this.subjectDto2.setName(SubjectName.LENGUAJE);
		this.subjectDto2.setTeacher(userRestService.getTeacherDto());
		this.subjectDto2.setCourse(courseRestService.getCourseDto2());
		
		logger.warn("***********************************************************************************************");

	}

	public void deleteSubjects() {
		logger.warn(
				"*********************************DELETING_SUBJECT**************************************************");
		this.restService.loginManager();

		try {
			String id = this.subjectDto.getId();
			this.restService.restBuilder().path(SubjectController.SUBJECT).path(SubjectController.PATH_ID).expand(id)
					.bearerAuth(restService.getAuthToken().getToken()).delete().build();
		} catch (Exception e) {
			logger.warn("error: " + e.getMessage() + "subjectDto: nothing to delete");
		}
		
		try {
			String id = this.subjectDto2.getId();
			this.restService.restBuilder().path(SubjectController.SUBJECT).path(SubjectController.PATH_ID).expand(id)
					.bearerAuth(restService.getAuthToken().getToken()).delete().build();
		} catch (Exception e) {
			logger.warn("error: " + e.getMessage() + "subjectDto2: nothing to delete");
		}

		this.courseRestService.deleteCourses();

		logger.warn("***********************************************************************************************");

	}

	// POST********************************
	public SubjectDto postSubject() {
		return	subjectDto = restService.restBuilder(new RestBuilder<SubjectDto>()).clazz(SubjectDto.class)
				.path(SubjectController.SUBJECT)
				.bearerAuth(restService.getAuthToken().getToken()).body(subjectDto)
				.post().build();
	}
	
	public SubjectDto postSubject2() {
		return	subjectDto2 = restService.restBuilder(new RestBuilder<SubjectDto>()).clazz(SubjectDto.class)
				.path(SubjectController.SUBJECT)
				.bearerAuth(restService.getAuthToken().getToken()).body(subjectDto2)
				.post().build();
	}
	

	// PUT********************************
	public SubjectDto putSubject(String id) {
		return	subjectDto = restService.restBuilder(new RestBuilder<SubjectDto>()).clazz(SubjectDto.class)
				.path(SubjectController.SUBJECT)
				.path(SubjectController.PATH_ID).expand(id)
				.bearerAuth(restService.getAuthToken().getToken())
				.body(subjectDto)
				.put()
				.build();
	}
	// PACH********************************
	

	// GET********************************
	public SubjectDto getSubjectById(String id) {
		return	subjectDto = restService.restBuilder(new RestBuilder<SubjectDto>()).clazz(SubjectDto.class)
				.path(SubjectController.SUBJECT)
				.path(SubjectController.PATH_ID).expand(id)
				.bearerAuth(restService.getAuthToken().getToken())
				.body(subjectDto)
				.get()
				.build();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SubjectDto> getFullSubjects() {
		return	listSubjectDto = restService.restBuilder(new RestBuilder<List>()).clazz(List.class)
				.path(SubjectController.SUBJECT)
				.bearerAuth(restService.getAuthToken().getToken())
				.body(subjectDto)
				.get()
				.build();
	}



}