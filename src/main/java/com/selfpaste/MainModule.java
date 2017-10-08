package com.selfpaste;

import com.google.inject.AbstractModule;
import com.selfpaste.db.DatabaseModule;

/**
 * This module is the entry point for all Guice injections and is called from Main.java. We could just as well have
 * done something like :
 *
 * Injector injector = Guice.createInjector(
 *  new DatabaseModule(),
 ...
 );
    in Main.java, but instead opt to install 'sub-modules' here to keep Main sane and simple
 */
public class MainModule extends AbstractModule {

	@Override
	protected void configure() {

		//Instructs the Injector that bindings must be listed in a Module in order to be injected. Classes that are
		// not explicitly bound in a module cannot be injected. See method JavaDoc for more details
		binder().requireExplicitBindings();

		// Install desired modules
		install(new DatabaseModule());

		//Bind installed modules to the service class
		bind(MainService.class);
	}
}
