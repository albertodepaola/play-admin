package models;

import play.core.enhancers.PropertiesEnhancer.RewrittenAccessor;
import play.data.Form;
import play.db.ebean.Model.Finder;

@SuppressWarnings("rawtypes")
public class CustomAdmin<T extends BasePojo> {

	private Class<T> entity;

	private Class view;
	
	private Form<T> form;
	
	private Finder<Long, T> find;

	private CustomAdmin(Builder<T> b) {
		this.entity = b.entity;
		this.view = b.view;
		this.form = b.form;
		this.find = b.find;
	}

	public Class<T> getEntity() {
		return entity;
	}

	public Class getView() {
		return view;
	}
	
	public Form<T> getForm() {
		return form;
	}
	
	public Finder<Long, T> getFinder() {
		return find;
	}

	public static final class Builder<T extends BasePojo> {

		private Class<T> entity;

		private Class view = views.html.admin.class;

		private Form<T> form;
		
		private Finder<Long, T> find;
		
		public Builder(Class<T> entity) {
			this.entity = entity;
			form = Form.form(entity);
			find = new Finder<Long, T>(Long.class, entity);
		}

		public Builder<T> view(Class<? extends Object> view) {
			if (!view.isAnnotationPresent(RewrittenAccessor.class)) {
				throw new IllegalArgumentException("Must be a valid view");
			}
			this.view = view;
			return this;

		}

		public CustomAdmin<T> build() {
			return new CustomAdmin<T>(this);
		}

	}

}
