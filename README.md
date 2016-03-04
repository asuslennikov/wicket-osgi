# Wicket OSGI
It is template project for wicket-based development in OSGI environment. Supports AJAX and dependency injection (OSGI -> Guice). <br />
It is licensed under the [MIT License (MIT)](https://opensource.org/licenses/MIT).

## Install & start
Just clone the project, build with maven from the root directory:
```
mvn clean install -P dev
```
Navigate to the launcher directory and start the 'start.bat' (or start.sh for Linux). <br />
Now your project is available via URl  [http://localhost:8080/wicket](http://localhost:8080/wicket) (Can be configured in WicketApplicationRegistrationService#WICKET_APPLICATION_URL_PREFIX).
Ajax greeting message can be changed in GoGo console via: `setGreetingMessage "string"` command. </br>

