# Vaadin Add-on example project

This is a reusable NPS [Net Promoter Score](https://en.wikipedia.org/wiki/Net_promoter_score) widget. The widget displays a numeric scale typically ranging from 0 to 10, allowing users to rate their experience or likelihood to recommend on a continuum.

Additionally there is a demo project that implements the full application for collecting and reporting the NPS score. This can be used as standalone application to collect user feedback. 
This application is a runnable Spring Boot web application using Vaadin. 

NPS focuses on collecting user feedback, providing you with valuable insights to gauge customer satisfaction and loyalty. While it doesn't generate reports directly, the data collected by the widget can be used to analyze and measure the overall sentiment and make informed decisions to enhance your product or service based on the feedback received. 

## Project structure

Project consists of two modules:
- [addon](addon/) - Vaadin NPS component itself.
- [demo](addon/) - Application demmostrating the usage of the component.


## Add-on architecture

This add-on is server-side only implementation made using existing Vaadin components.

![server-side-addon](https://user-images.githubusercontent.com/991105/211870086-75544597-847d-4d21-82fa-341411753558.svg)

## Development instructions

### Important Files 
* NPS.java: this is the addon-on component class. You can add more classes if you wish, including other Components.
* TestView.java: A View class that let's you test the component you are building. This and other classes in the test folder will not be packaged during the build. You can add more test view classes in this package.
* assembly/: this folder includes configuration for packaging the project into a JAR so that it works well with other Vaadin projects and the Vaadin Directory. There is usually no need to modify these files, unless you need to add JAR manifest entries.

If you are using static resources such as images, JS (e.g. templates) and CSS files the correct location for them is under the `/src/main/resources/META-INF/resources/frontend` directory and is described here [Resource Cheat Sheet](https://vaadin.com/docs/v14/flow/importing-dependencies/tutorial-ways-of-importing.html#resource-cheat-sheet)in more details. 

### Running the add-on tests

Starting the test server in addon module:
```
cd addon
mvn jetty:run
```

This deploys demo at http://localhost:8080
 
### Integration test

To run Integration Tests, execute `mvn verify -Pit,production`.

## Publishing to Vaadin Directory

You should change the `organisation.name` property in `pom.xml` to your own name/organization.

```
    <organization>
        <name>###author###</name>
    </organization>
```

You can create the zip package needed for [Vaadin Directory](https://vaadin.com/directory/) using

```
mvn versions:set -DnewVersion=1.0.0 # You cannot publish snapshot versions 
mvn install -Pdirectory
```

The package is created as `target/{project-name}-1.0.0.zip`

For more information or to upload the package, visit https://vaadin.com/directory/my-components?uploadNewComponent
