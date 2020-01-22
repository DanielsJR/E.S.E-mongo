package nx.ESE.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import nx.ESE.documents.core.Attendance;
import nx.ESE.documents.core.Evaluation;
import nx.ESE.documents.core.EvaluationType;
import nx.ESE.dtos.AttendanceDto;
import nx.ESE.dtos.EvaluationDto;


public interface AttendanceRepository extends MongoRepository<Attendance, String> {
	
	public AttendanceDto  findFirstBySubjectId(String subjectId);
	
	public AttendanceDto  findFirstByDate(String quizId);

	public AttendanceDto findBySubjectIdAndDate(String subjectId, Date date);
	
	public List<AttendanceDto> findBySubjectId(String subjectId);
	

}
