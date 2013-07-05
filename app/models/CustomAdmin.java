package models;

import play.core.enhancers.PropertiesEnhancer.RewrittenAccessor;

public class CustomAdmin<T> {

	private Class<T> entity;

	private Class view;

	private CustomAdmin(Builder<T> b) {
		this.entity = b.entity;
		this.view = b.view;
	}

	public Class<T> getEntity() {
		return entity;
	}

	public Class getView() {
		return view;
	}

	public static final class Builder<T> {

		private Class<T> entity;

		private Class view = views.html.index.class;

		public Builder(Class<T> entity) {
			this.entity = entity;
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
