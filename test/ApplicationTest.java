import static org.fest.assertions.Assertions.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;
import models.*;

import org.junit.*;

import play.data.*;
import play.mvc.*;
import utils.*;
import views.html.*;

/**
 * 
 * Simple (JUnit) tests that can call all parts of a play app. If you are
 * interested in mocking a whole application, see the wiki for more details.
 * 
 */
@SuppressWarnings("unused")
public class ApplicationTest {

	@Before
	public void setUp() {
		RegisterAdmin.registerAdmin(User.class);
	}

	@Test
	public void renderTemplate() {
		Content html = views.html.index.render("Your new application is ready.");
		assertThat(contentType(html)).isEqualTo("text/html");
		assertThat(contentAsString(html)).contains("Your new application is ready.");
	}

	@Test
	public void renderIndexForEntity() {

		running(fakeApplication(), new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.GenericController.index("User"));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("text/html");
				assertThat(charset(result)).isEqualTo("utf-8");
				assertThat(contentAsString(result)).contains("getUsername");
			}
		});
	}

	@Test
	public void renderEntityNotFound() {

		Result result = callAction(controllers.routes.ref.GenericController.index("NotFound"));
		assertThat(status(result)).isEqualTo(NOT_FOUND);
	}

	@Test
	public void addCustomTemplate() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				CustomAdmin<User> ca = new CustomAdmin.Builder<User>(User.class).view(admin.class).build();
				RegisterAdmin.registerAdmin(User.class, ca);

				Result result = callAction(controllers.routes.ref.GenericController.index("User"));
				assertThat(contentAsString(result)).contains("index 2!!");
			}
		});
	}

	@Test
	public void useDefaultTemplate() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				RegisterAdmin.registerAdmin(User.class);

				Result result = callAction(controllers.routes.ref.GenericController.index("User"));
				assertThat(contentAsString(result)).contains("Welcome to Play 2.1");
			}
		});

	}

	@Test
	public void buildValidCustomAdmin() {
		CustomAdmin<Person> ca = new CustomAdmin.Builder<Person>(Person.class).view(admin.class).build();
		assertThat(Person.class).isEqualTo(ca.getEntity());
		assertThat(admin.class).isEqualTo(ca.getView());
		Form<Person> form = Form.form(Person.class);
		assertThat(form.equals(ca.getForm()));
		// assertThat(form).isEqualTo(ca.getForm());
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildInvalidCustomAdmin() {
		CustomAdmin<Person> ca = new CustomAdmin.Builder<Person>(Person.class).view(Object.class).build();
	}

}
