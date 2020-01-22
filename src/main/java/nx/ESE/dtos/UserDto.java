package nx.ESE.dtos;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nx.ESE.documents.Avatar;
import nx.ESE.documents.Commune;
import nx.ESE.documents.Gender;
import nx.ESE.documents.Role;
import nx.ESE.documents.User;
import nx.ESE.dtos.validators.RUTValid;
import nx.ESE.utils.Capitalizer;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class UserDto {

	@Getter
	private String id;

	@NotNull
	@Pattern(regexp = nx.ESE.dtos.validators.Pattern.USERNAME)
	@Getter
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  
	@Pattern(regexp = nx.ESE.dtos.validators.Pattern.PASSWORD)
	@Getter
	@Setter
	private String password;

	@NotNull
	@Getter
	private String firstName;

	@NotNull
	@Getter
	private String lastName;

	@RUTValid
	private String dni;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Getter
	@Setter
	private Date birthday;

	@Getter
	@Setter
	private Gender gender;

	@Getter
	@Setter
	private Avatar avatar;

	@Pattern(regexp = nx.ESE.dtos.validators.Pattern.NINE_DIGITS)
	@Getter
	@Setter
	private String mobile;

	@Pattern(regexp = nx.ESE.dtos.validators.Pattern.EMAIL)
	@Getter
	private String email;

	@Getter
	private String address;

	@Getter
	@Setter
	private Commune commune;

	@Getter
	@Setter
	private Role[] roles;

	@Getter
	@Setter
	private boolean active;

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

	// input
	public UserDto(String id, String username, String password, String firstName, String lastName, String dni,
			Date birthday, Gender gender, Avatar avatar, String mobile, String email, String address, Commune commune,
			Role[] roles, boolean active) {
		super();
		this.id = id;
		this.setUsername(username);
		this.password = password;
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDni(dni);
		this.birthday = birthday;
		this.gender = gender ;
		this.avatar = avatar;
		this.mobile = mobile;
		this.setEmail(email);
		this.setAddress(address);
		this.commune = commune;
		this.roles = roles;
		this.active = active;

	}
	
	// for tests
	public UserDto(String usernamePass) {
		this(null, usernamePass, usernamePass + "@ESE1", usernamePass, usernamePass, null, null, null, null, null, null,
				null, null, null, true);
	}

	// output
	public UserDto(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.dni = user.getDni();
		this.birthday = user.getBirthday();
		this.gender = user.getGender();
		this.avatar = user.getAvatar();
		this.mobile = user.getMobile();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.commune = user.getCommune();
		this.roles = user.getRoles();
		this.active = user.isActive();
		this.createdBy = user.getCreatedBy();
		this.createdDate = user.getCreatedDate();
		this.lastModifiedUser = user.getLastModifiedUser();
		this.lastModifiedDate = user.getLastModifiedDate();
	}

	public void setUsername(String username) {
		if (username != null) {
			this.username = username.toLowerCase();
		} else {
			this.username = username;
		}
	}

	public void setFirstName(String firstName) {
		if (firstName != null) {
			this.firstName = Capitalizer.capitalizer(firstName);
		} else {
			this.firstName = firstName;
		}
	}

	public void setLastName(String lastName) {
		if (lastName != null) {
			this.lastName = Capitalizer.capitalizer(lastName);
		} else {
			this.lastName = lastName;
		}
	}

	public String getDni() {
		if (dni != null) {
			String dniClean = dni.replace("-", "");
			return dniClean.substring(0, dniClean.length() - 1) + "-" + dniClean.substring(dniClean.length() - 1);
		} else {
			return dni;
		}
	}

	public void setDni(String dni) {
		if (dni != null) {
			this.dni = dni.toUpperCase().replace(".", "").replace("-", "");
		} else {
			this.dni = dni;
		}
	}

	public void setEmail(String email) {
		if (email != null) {
			this.email = email.toLowerCase();
		} else {
			this.email = email;
		}
	}

	public void setAddress(String address) {
		if (address != null) {
			this.address = Capitalizer.capitalizer(address);
		} else {
			this.address = address;
		}
	}

	@Override
	public String toString() {

		String birthdayF = "null";
		if (this.birthday != null)
			birthdayF = new SimpleDateFormat("dd/MM/yyyy").format(birthday.getTime());

		String cDate = "null";
		if (this.createdDate != null)
			cDate = new SimpleDateFormat("dd-MMM-yyyy").format(createdDate.getTime());

		String lModified = "null";
		if (this.lastModifiedDate != null)
			lModified = new SimpleDateFormat("dd-MMM-yyyy").format(lastModifiedDate.getTime());

		return "UserDto [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", dni=" + dni + ", birthday=" + birthdayF + ", gender=" + gender
				+ ", mobile=" + mobile + ", avatar=" + avatar + ", email=" + email + ", address=" + address
				+ ", commune=" + commune + ", roles=" + Arrays.toString(roles) + ", active=" + active + ", createdBy="
				+ createdBy + ", createdDate=" + cDate + ", lastModifiedBy=" + lastModifiedUser + ", lastModifiedDate="
				+ lModified + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		UserDto other = (UserDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
