package nx.ese.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MessageDto {
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private String subject;
	
	@Getter
	@Setter
	private String message;
	
	@Getter
	@Setter
	private Notification notification;

}
