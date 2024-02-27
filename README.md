Jsut a crud model for a project which is using the following technologies:
        
        -spring boot(Maven project)
        -spring data jpa
        -react
        -mysql

## Installation

1. Install all from pom.xml file
2. Add dependencies to the application.yml file on path src/main/resources

If you are using a different database, you will need to change the dependencies in the pom.xml file and the application.yaml file.

## Docker-compose
If you want run a docker compose add the new dependencies to the 
                
        /app.yml
        
 just run the following command from the root of the project:
        
        docker-compose -f app.yml up -d


