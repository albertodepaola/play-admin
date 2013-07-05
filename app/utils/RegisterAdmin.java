package utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TupleElement;

import org.javatuples.Pair;
import org.javatuples.Tuple;

import views.html.index2;

import models.CustomAdmin;
import models.User;

@SuppressWarnings("rawtypes")
public class RegisterAdmin {
	
	private static Map<String, Pair<Class, CustomAdmin>> registeredEntities = new HashMap<String, Pair<Class, CustomAdmin>>();
	
	static {
		// FIXME isso esta fixo aqui para ter um lugar onde fazer...
		registerAdmin(User.class, new CustomAdmin.Builder<User>(User.class).view(index2.class).build());
	}

	public static void registerAdmin(Class entity) {
		// creates with default values
		registerAdmin(entity, new CustomAdmin.Builder(entity).build());
	}
	
	public static Collection<Pair<Class,CustomAdmin>> getRegisteredEntities() {
		return registeredEntities.values();
	}
	
	public static Pair<Class,CustomAdmin> getRegisteredEntity(String className){
		return registeredEntities.get(className);
	}

	public static void registerAdmin(Class entity, CustomAdmin ca) {
		registeredEntities.put(entity.getSimpleName(), Pair.with(entity, ca));
	}
	
}
