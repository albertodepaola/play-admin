import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;
import models.CustomAdmin;
import models.Person;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Content;
import play.mvc.Result;
import utils.RegisterAdmin;
import views.html.index2;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
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
    public void renderIndexForEntity(){
    	
    	Result result = callAction(controllers.routes.ref.GenericController.index("User"));   
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("getUsername");
    }
    
    @Test
    public void renderEntityNotFound(){
    	
    	Result result = callAction(controllers.routes.ref.GenericController.index("NotFound"));   
    	assertThat(status(result)).isEqualTo(NOT_FOUND);
    }
    
    @Test
    public void addCustomTemplate() {
    	CustomAdmin<User> ca = new CustomAdmin.Builder<User>(User.class).view(index2.class).build();
    	RegisterAdmin.registerAdmin(User.class, ca);
    	
    	Result result = callAction(controllers.routes.ref.GenericController.index("User"));
    	assertThat(contentAsString(result)).contains("index 2!!");
    }
    
    @Test
    public void useDefaultTemplate() {
    	
    	RegisterAdmin.registerAdmin(User.class);
    	
    	Result result = callAction(controllers.routes.ref.GenericController.index("User"));
    	assertThat(contentAsString(result)).doesNotContain("index 2!!");
    	assertThat(contentAsString(result)).contains("Welcome to Play 2.1");
    }
    
    @Test
    public void buildValidCustomAdmin() {
    	CustomAdmin<Person> ca = new CustomAdmin.Builder<Person>(Person.class).view(index2.class).build();
    	assertThat(Person.class).isEqualTo(ca.getEntity());
    	assertThat(index2.class).isEqualTo(ca.getView());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void buildInvalidCustomAdmin() {
    	CustomAdmin<Person> ca = new CustomAdmin.Builder<Person>(Person.class).view(Object.class).build();
    }
    
  
   
}
