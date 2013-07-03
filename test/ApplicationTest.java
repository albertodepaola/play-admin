import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.soap.SOAPBinding.Use;

import models.User;

import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import utils.RegisterAdmin;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


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
  
   
}
