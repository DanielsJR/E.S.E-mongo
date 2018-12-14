package nx.ESE.services.data;

import java.util.List;

import nx.ESE.documents.Preferences;
import nx.ESE.documents.User;
import nx.ESE.documents.core.Course;
import nx.ESE.documents.core.Grade;
import nx.ESE.documents.core.Subject;

public class DatabaseGraph {

	private List<User> userList;

	private List<Preferences> preferencesList;

	private List<Course> coursesList;

	private List<Subject> subjectsList;
	
	private List<Grade> gradesList;

	public DatabaseGraph() {
		// Empty for framework
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Preferences> getPreferencesList() {
		return preferencesList;
	}

	public void setPreferencesList(List<Preferences> preferencesList) {
		this.preferencesList = preferencesList;
	}

	public List<Course> getCoursesList() {
		return coursesList;
	}

	public void setCoursesList(List<Course> coursesList) {
		this.coursesList = coursesList;
	}

	public List<Subject> getSubjectsList() {
		return subjectsList;
	}

	public void setSubjectsList(List<Subject> subjectsList) {
		this.subjectsList = subjectsList;
	}

	public List<Grade> getGradesList() {
		return gradesList;
	}

	public void setGradesList(List<Grade> gradesList) {
		this.gradesList = gradesList;
	}
	
	

}
