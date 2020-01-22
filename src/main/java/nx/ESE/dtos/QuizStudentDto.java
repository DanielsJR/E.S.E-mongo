package nx.ESE.dtos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nx.ESE.documents.core.CorrespondItem;
import nx.ESE.documents.core.IncompleteTextItem;
import nx.ESE.documents.core.MultipleSelectionItem;
import nx.ESE.documents.core.Quiz;
import nx.ESE.documents.core.QuizStudent;
import nx.ESE.documents.core.TrueFalseItem;

@NoArgsConstructor
public class QuizStudentDto {

	@Getter
	private String id;

	@Getter
	@Setter
	private List<CorrespondItem> correspondItems;

	@Getter
	@Setter
	private List<IncompleteTextItem> incompleteTextItems;

	@Getter
	@Setter
	private List<TrueFalseItem> trueFalseItems;

	@Getter
	@Setter
	private List<MultipleSelectionItem> multipleSelectionItems;

	@Getter
	private String createdBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Getter
	private Date createdDate;

	@Getter
	private String lastModifiedUser;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Getter
	private Date lastModifiedDate;

	public QuizStudentDto(QuizStudent quizStudent) {
		super();
		this.id = quizStudent.getId();

		this.multipleSelectionItems = quizStudent.getMultipleSelectionItems();
		this.trueFalseItems = quizStudent.getTrueFalseItems();
		this.correspondItems = quizStudent.getCorrespondItems();
		this.incompleteTextItems = quizStudent.getIncompleteTextItems();

		this.createdBy = quizStudent.getCreatedBy();
		this.createdDate = quizStudent.getCreatedDate();
		this.lastModifiedUser = quizStudent.getLastModifiedUser();
		this.lastModifiedDate = quizStudent.getLastModifiedDate();
	}

	public QuizStudentDto(QuizDto quizDto) {
		this.multipleSelectionItems = quizDto.getMultipleSelectionItems();
		this.trueFalseItems = quizDto.getTrueFalseItems();
		this.correspondItems = quizDto.getCorrespondItems();
		this.incompleteTextItems = quizDto.getIncompleteTextItems();
	}

	@Override
	public String toString() {

		String cDate = "null";
		if (this.createdDate != null)
			cDate = new SimpleDateFormat("dd-MMM-yyyy").format(createdDate.getTime());

		String lModified = "null";
		if (this.lastModifiedDate != null)
			lModified = new SimpleDateFormat("dd-MMM-yyyy").format(lastModifiedDate.getTime());

		return "QuizStudentDto [id=" + id + ", multipleSelectionIitems=" + multipleSelectionItems + ", trueFalseItems="
				+ trueFalseItems + ", correspondItems=" + correspondItems + ", incompleteTextItems="
				+ incompleteTextItems + ", createdBy=" + createdBy + ", createdDate=" + cDate + ", lastModifiedUser="
				+ lastModifiedUser + ", lastModifiedDate=" + lModified + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correspondItems == null) ? 0 : correspondItems.hashCode());
		result = prime * result + ((incompleteTextItems == null) ? 0 : incompleteTextItems.hashCode());
		result = prime * result + ((multipleSelectionItems == null) ? 0 : multipleSelectionItems.hashCode());
		result = prime * result + ((trueFalseItems == null) ? 0 : trueFalseItems.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuizStudentDto other = (QuizStudentDto) obj;
		if (correspondItems == null) {
			if (other.correspondItems != null)
				return false;
		} else if (!correspondItems.equals(other.correspondItems))
			return false;
		if (incompleteTextItems == null) {
			if (other.incompleteTextItems != null)
				return false;
		} else if (!incompleteTextItems.equals(other.incompleteTextItems))
			return false;
		if (multipleSelectionItems == null) {
			if (other.multipleSelectionItems != null)
				return false;
		} else if (!multipleSelectionItems.equals(other.multipleSelectionItems))
			return false;
		if (trueFalseItems == null) {
			if (other.trueFalseItems != null)
				return false;
		} else if (!trueFalseItems.equals(other.trueFalseItems))
			return false;
		return true;
	}

}
