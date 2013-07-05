package controllers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import models.CustomAdmin;

import org.javatuples.Pair;

import play.api.templates.Html;
import play.core.enhancers.PropertiesEnhancer;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import utils.ReflectionUtils;
import utils.RegisterAdmin;
import static utils.RegisterAdmin.*;

public class GenericController extends Controller {

	public static Result index(String entity) {
		Pair<Class,CustomAdmin> registeredEntity = RegisterAdmin.getRegisteredEntity(entity);
		if (registeredEntity != null) {

			Method method = null;
			Object newInstance = null;
			try {
				method = registeredEntity.getValue1().getView().getMethod("render", String.class);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			try {
				return ok((Html)method.invoke(null, "Render method: " + method + ".\n Renderer instance: " + newInstance + ".\n methods: "
						+ ReflectionUtils.getFieldsAsMap(registeredEntity.getValue0())));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			return notFound("Entity not found ", entity);
//			return ok(views.html.index.render("Render method: " + method + ".\n Renderer instance: " + newInstance + ".\n methods: "
//					+ ReflectionUtils.getFieldsAsMap(registeredEntity.getValue0()) ) );
		} else {
			return notFound("Entity not found ", entity);
		}

	}

}
