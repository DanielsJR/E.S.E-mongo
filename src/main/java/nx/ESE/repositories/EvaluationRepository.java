package nx.ESE.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import nx.ESE.documents.core.Evaluation;
import nx.ESE.documents.core.EvaluationType;
import nx.ESE.dtos.EvaluationDto;


public interface EvaluationRepository extends MongoRepository<Evaluation, String> {
	
	public EvaluationDto  findFirstBySubject(String subjectId);
	
	public EvaluationDto  findFirstByQuiz(String quizId);

	public EvaluationDto findByTitleAndTypeAndSubjectAndDate(String title, EvaluationType type, String id, Date date);
	
	public List<EvaluationDto> findBySubject(String subjectId);
	

}