package nx.ESE.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nx.ESE.dtos.GradeDto;
import nx.ESE.exceptions.DocumentAlreadyExistException;
import nx.ESE.exceptions.DocumentNotFoundException;
import nx.ESE.exceptions.FieldInvalidException;
import nx.ESE.exceptions.FieldNotFoundException;
import nx.ESE.exceptions.FieldNullException;
import nx.ESE.services.GradeService;

@PreAuthorize("hasRole('MANAGER') or hasRole('TEACHER') or hasRole('STUDENT')")
@RestController
@RequestMapping(GradeController.GRADE)
public class GradeController {

	public static final String GRADE = "/grades";
	public static final String SUBJECT = "/subjects";

	public static final String PATH_ID = "/{id}";
	public static final String PATH_USERNAME = "/{username}";

	@Autowired
	private GradeService gradeService;

	// POST
	@PreAuthorize("hasRole('TEACHER')")
	@PostMapping()
	public GradeDto createGrade(@Valid @RequestBody GradeDto gradeDto)
			throws FieldInvalidException, DocumentAlreadyExistException {

		if (!this.gradeService.isIdNull(gradeDto))
			throw new FieldInvalidException("Id");

		if (this.gradeService.isGradeRepeated(gradeDto))
			throw new DocumentAlreadyExistException("Nota");

		return this.gradeService.createGrade(gradeDto);
	}

	// PUT
	@PreAuthorize("hasRole('TEACHER')")
	@PutMapping(PATH_ID)
	public GradeDto modifyGrade(@PathVariable String id, @Valid @RequestBody GradeDto gradeDto)
			throws FieldNotFoundException, DocumentAlreadyExistException {

		if (this.gradeService.isGradeRepeated(gradeDto))
			throw new DocumentAlreadyExistException("Nota");

		return this.gradeService.modifyGrade(id, gradeDto).orElseThrow(() -> new FieldNotFoundException("Id"));
	}

	// DELETE
	@PreAuthorize("hasRole('TEACHER')")
	@DeleteMapping(PATH_ID)
	public GradeDto deleteGrade(@PathVariable String id) throws FieldNotFoundException {
		return this.gradeService.deleteGrade(id).orElseThrow(() -> new FieldNotFoundException("Id"));
	}

	// GET
	@PreAuthorize("hasRole('MANAGER') or hasRole('TEACHER')")
	@GetMapping(PATH_ID)
	public GradeDto getGradeById(@PathVariable String id) throws FieldNotFoundException {
		return this.gradeService.getGradeById(id).orElseThrow(() -> new FieldNotFoundException("Id"));
	}

	@PreAuthorize("hasRole('MANAGER') or hasRole('TEACHER')")
	@GetMapping()
	public List<GradeDto> getFullGrades() throws DocumentNotFoundException {
		return this.gradeService.getFullGrades().orElseThrow(() -> new DocumentNotFoundException("Grade(s)"));
	}
	
	@PreAuthorize("hasRole('MANAGER') or hasRole('TEACHER') or hasRole('STUDENT')")
	@GetMapping(SUBJECT + PATH_ID)
	public List<GradeDto> getGradesBySubject(@PathVariable String id) throws DocumentNotFoundException {
		return this.gradeService.getGradesBySubject(id).orElseThrow(() -> new DocumentNotFoundException("Grade(s)"));
	}
}
