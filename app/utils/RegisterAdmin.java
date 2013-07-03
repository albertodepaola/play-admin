package utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import models.Person;
import models.User;

public class RegisterAdmin {
	
	private static Map<String, Class> registeredEntities = new HashMap<String, Class>();

	public static void registerAdmin(Class entity) {
		registeredEntities.put(entity.getSimpleName(), entity);
	}
	
	public static Collection<Class> getRegisteredEntities() {
		return registeredEntities.values();
	}
	
	public static Class getRegisteredEntity(String className){
		return registeredEntities.get(className);
	}
}
