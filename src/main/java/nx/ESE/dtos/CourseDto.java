package nx.ESE.dtos;

import java.util.ArrayList;
import java.util.List;

import nx.ESE.documents.User;
import nx.ESE.documents.core.Course;
import nx.ESE.documents.core.CourseName;

public class CourseDto {

	private String id;

	private CourseName name;

	private UserDto chiefTeacher;

	private List<UserDto> students;

	private int year;

	public CourseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseDto(String id, CourseName name, UserDto chiefTeacher, List<UserDto> students, int year) {
		super();
		this.id = id;
		this.name = name;
		this.chiefTeacher = chiefTeacher;
		this.students = students;
		this.year = year;
	}

	// output
	public CourseDto(Course course) {
		super();
		this.id = course.getId();
		this.name = course.getName();
		this.chiefTeacher = new UserDto(course.getChiefTeacher());
		this.students = this.usersList(course);
		this.year = course.getYear();
	}

	public List<UserDto> usersList(Course course) {
		List<UserDto> usersList = new ArrayList<>();
		for (User user : course.getStudents()) {
			usersList.add(new UserDto(user));
		}

		return usersList;
	}

	public CourseName getName() {
		return name;
	}

	public void setName(CourseName name) {
		this.name = name;
	}

	public UserDto getChiefTeacher() {
		return chiefTeacher;
	}

	public void setChiefTeacher(UserDto chiefTeacher) {
		this.chiefTeacher = chiefTeacher;
	}

	public List<UserDto> getStudents() {
		return students;
	}

	public void setStudents(List<UserDto> students) {
		this.students = students;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "CourseDto [id=" + id + ", name=" + name + ", chiefTeacher=" + chiefTeacher + ", students=" + students
				+ ", year=" + year + "]";
	}

}