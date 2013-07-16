package controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import models.*;

import org.javatuples.Pair;

import play.api.templates.Html;
import play.data.*;
import play.mvc.Controller;
import play.mvc.Result;
import utils.ReflectionUtils;
import utils.RegisterAdmin;

public class GenericController extends Controller {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Result index(String entity) {
		Pair<Class, CustomAdmin> registeredEntity = RegisterAdmin.getRegisteredEntity(entity);
		if (registeredEntity != null) {

			try {
				Html renderMethodResult = renderEntityView(registeredEntity);
				return ok(renderMethodResult);
			} catch (RuntimeException e) {
				return notFound("Entity not found ", entity);
			}

			// return ok(views.html.index.render("Render method: " + method +
			// ".\n Renderer instance: " + newInstance + ".\n methods: "
			// + ReflectionUtils.getFieldsAsMap(registeredEntity.getValue0()) )
			// );
		} else {
			return notFound("Entity not found ", entity);
		}

	}

	private static Html renderEntityView(Pair<Class, CustomAdmin> registeredEntity) {

		Method method = null;
		CustomAdmin<BasePojo> ca = registeredEntity.getValue1();

		try {
			method = ca.getView().getMethod("render", String.class, List.class, Form.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException("erro ao pegar o metodo");
		}

		try {
			System.out.println(ca.getFinder().all());
			Html renderMethodResult = (Html) method.invoke(null,
					"Render method: " + method + ".\n methods: " + ReflectionUtils.getFieldsAsMap(registeredEntity.getValue0()), ca.getFinder().all(),
					ca.getForm());
			return renderMethodResult;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException("erro ao chamar o metodo.");
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Result newEntity(String entity) {

		Pair<Class, CustomAdmin> registeredEntity = RegisterAdmin.getRegisteredEntity(entity);
		if (registeredEntity != null) {

			CustomAdmin<BasePojo> ca = registeredEntity.getValue1();
			Form<BasePojo> bindFromRequest = ca.getForm().bindFromRequest();
			if (bindFromRequest.hasErrors()) {
				System.out.println("errors");
				return badRequest(renderEntityView(registeredEntity));
			} else {
				// System.out.println(((User)bindFromRequest.get()).getUsername());
				System.out.println(bindFromRequest);
				bindFromRequest.get().save();
			}

		}

		return ok(renderEntityView(registeredEntity));
	}

	public static Result deleteEntity(String entity, Long id) {
		return TODO;
	}

}
