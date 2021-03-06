package nx.ese.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import nx.ese.controllers.QuizStudentController;
import nx.ese.documents.core.CorrespondItem;
import nx.ese.documents.core.IncompleteTextItem;
import nx.ese.documents.core.MultipleSelectionItem;
import nx.ese.documents.core.TrueFalseItem;
import nx.ese.dtos.QuizStudentDto;

@Service
public class QuizStudentRestService {

    @Autowired
    private RestService restService;

    @Autowired
    private UserRestService userRestService;

    @Autowired
    private SubjectRestService subjectRestService;

    @Getter
    @Setter
    private QuizStudentDto quizStudentDto;

    @Getter
    @Setter
    private QuizStudentDto quizStudentDto2;

    @Getter
    @Setter
    private List<QuizStudentDto> listQuizStudentDto;

    private static final Logger logger = LoggerFactory.getLogger(QuizStudentRestService.class);

    public void createQuizStudentsDto() {
        logger.info("*********************************CREATING_QUIZES-STUDENT*****************************************");
        this.quizStudentDto = new QuizStudentDto();

        List<CorrespondItem> correspondItems = new ArrayList<CorrespondItem>();
        correspondItems.add(new CorrespondItem("", ""));
        correspondItems.add(new CorrespondItem("", ""));
        this.quizStudentDto.setCorrespondItems(correspondItems);

        List<TrueFalseItem> trueFalseItems = new ArrayList<TrueFalseItem>();
        trueFalseItems.add(new TrueFalseItem("", true));
        trueFalseItems.add(new TrueFalseItem("", true));
        this.quizStudentDto.setTrueFalseItems(trueFalseItems);

        List<MultipleSelectionItem> multipleSelectionItems = new ArrayList<MultipleSelectionItem>();
        multipleSelectionItems.add(new MultipleSelectionItem("", "", "", "", "", ""));
        multipleSelectionItems.add(new MultipleSelectionItem("", "", "", "", "", ""));
        this.quizStudentDto.setMultipleSelectionItems(multipleSelectionItems);

        List<IncompleteTextItem> incompleteTextItems = new ArrayList<IncompleteTextItem>();
        incompleteTextItems.add(new IncompleteTextItem("", ""));
        incompleteTextItems.add(new IncompleteTextItem("", ""));
        this.quizStudentDto.setIncompleteTextItems(incompleteTextItems);


        this.quizStudentDto2 = new QuizStudentDto();

        List<CorrespondItem> correspondItems2 = new ArrayList<CorrespondItem>();
        correspondItems2.add(new CorrespondItem("2", "2"));
        correspondItems2.add(new CorrespondItem("2", "2"));
        this.quizStudentDto2.setCorrespondItems(correspondItems2);

        List<TrueFalseItem> trueFalseItems2 = new ArrayList<TrueFalseItem>();
        trueFalseItems2.add(new TrueFalseItem("2", true));
        trueFalseItems2.add(new TrueFalseItem("2", true));
        this.quizStudentDto2.setTrueFalseItems(trueFalseItems2);

        List<MultipleSelectionItem> multipleSelectionItems2 = new ArrayList<MultipleSelectionItem>();
        multipleSelectionItems2.add(new MultipleSelectionItem("2", "", "", "", "", ""));
        multipleSelectionItems2.add(new MultipleSelectionItem("2", "", "", "", "", ""));
        this.quizStudentDto2.setMultipleSelectionItems(multipleSelectionItems2);

        List<IncompleteTextItem> incompleteTextItems2 = new ArrayList<IncompleteTextItem>();
        incompleteTextItems2.add(new IncompleteTextItem("2", ""));
        incompleteTextItems2.add(new IncompleteTextItem("2", ""));
        this.quizStudentDto2.setIncompleteTextItems(incompleteTextItems2);

        logger.info("**************************************************************************************");

    }

    public void deleteQuizStudentsDto() {
        logger.info("*********************************DELETING_QUIZES-STUDENT**************************************");
        this.restService.loginManager();

        try {
            this.deleteQuizStudent(this.getQuizStudentDto().getId());
        } catch (Exception e) {
            logger.info("quizStudentDto: nothing to delete");
        }

        try {
            this.deleteQuizStudent(this.getQuizStudentDto2().getId());
        } catch (Exception e) {
            logger.info("quizStudentDto2: nothing to delete");
        }


        logger.info("********************************************************************************");

    }

    public QuizStudentDto postQuizStudent(String studentUsername) {
        return quizStudentDto = restService.restBuilder(new RestBuilder<QuizStudentDto>()).clazz(QuizStudentDto.class)
                .path(QuizStudentController.QUIZ_STUDENT)
                .path(QuizStudentController.STUDENT)
                .path(QuizStudentController.PATH_USERNAME).expand(studentUsername)
                .bearerAuth(restService.getAuthToken().getToken())
                .body(quizStudentDto)
                .post()
                .build();
    }

    public QuizStudentDto postQuizStudent2(String studentUsername) {
        return quizStudentDto2 = restService.restBuilder(new RestBuilder<QuizStudentDto>()).clazz(QuizStudentDto.class)
                .path(QuizStudentController.QUIZ_STUDENT)
                .path(QuizStudentController.STUDENT)
                .path(QuizStudentController.PATH_USERNAME).expand(studentUsername)
                .bearerAuth(restService.getAuthToken().getToken())
                .body(quizStudentDto2)
                .post()
                .build();
    }

    public QuizStudentDto putQuizStudent() {
        return quizStudentDto = restService.restBuilder(new RestBuilder<QuizStudentDto>()).clazz(QuizStudentDto.class)
                .path(QuizStudentController.QUIZ_STUDENT)
                .path(QuizStudentController.PATH_ID).expand(quizStudentDto.getId())
                .bearerAuth(restService.getAuthToken().getToken())
                .body(quizStudentDto)
                .put()
                .build();
    }

    public QuizStudentDto deleteQuizStudent(String id) {
        return restService.restBuilder(new RestBuilder<QuizStudentDto>()).clazz(QuizStudentDto.class)
                .path(QuizStudentController.QUIZ_STUDENT)
                .path(QuizStudentController.PATH_ID).expand(id)
                .bearerAuth(restService.getAuthToken().getToken())
                .delete()
                .build();
    }

    public QuizStudentDto getQuizStudentById(String id) {
        return restService.restBuilder(new RestBuilder<QuizStudentDto>()).clazz(QuizStudentDto.class)
                .path(QuizStudentController.QUIZ_STUDENT)
                .path(QuizStudentController.PATH_ID).expand(id)
                .bearerAuth(restService.getAuthToken().getToken())
                .get()
                .build();
    }


}
