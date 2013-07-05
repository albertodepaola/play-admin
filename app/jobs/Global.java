package jobs;

import models.User;
import play.Application;
import play.GlobalSettings;
import utils.RegisterAdmin;

public class Global extends GlobalSettings {
	
	@Override
	public void onStart(Application arg0) {
		super.onStart(arg0);
		RegisterAdmin.registerAdmin(User.class);
		System.out.println("Application started");
	}

}
