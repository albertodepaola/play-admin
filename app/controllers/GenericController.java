package controllers;

import java.lang.reflect.Field;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import utils.ReflectionUtils;
import utils.RegisterAdmin;
import static utils.RegisterAdmin.*;

public class GenericController extends Controller {

	public static Result index(String entity) {
		Class registeredEntity = RegisterAdmin.getRegisteredEntity(entity);
		if (registeredEntity != null) {

			return ok(views.html.index.render("methods: "
					+ ReflectionUtils.getFieldsAsMap(registeredEntity)));
		} else {
			return notFound("Entity not found ", entity);
		}

	}

}
