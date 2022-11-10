
<h1 align="center">
    <br>
    <img src="resources/logo.png" alt="Best Music icon" width="200">
    <br>
    Best Music
    <br>
</h1>

<h4 align="center">A Greta's CDA project.</h4>

<p align="center">
    <a href="https://openjdk.org/">
        <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
    </a>
    <a href="https://maven.apache.org/">
        <img src="https://img.shields.io/badge/Maven-CC2235?style=for-the-badge&logo=apache&logoColor=white">
    </a>
</p>

<p align="center">
    <a href="#quick-start">Quick start</a> •
    <a href="./USER_GUIDE.md">User guide</a> •
    <a href="#project-structure">Project sturcture</a> •
    <a href="#license">License</a> •
    <a href="#credits">Credits</a>
</p>

## Quick start

```console
# build using maven (see https://maven.apache.org/)
$ mvn package

# then run
$ java classes.com.stevancorre.cda.scraper.Main
```

<br>

## Project structure

This project is using [JavaFX](https://openjfx.io/) for the GUI and [Maven](https://maven.apache.org/) for the build system.  
The project structure is simple
```
|_ resources                                # resources for the readme
|_ src                                      # source project files
    |_ main
        |_ java
            |_ com/stevancorre/cda/scraper
                |_ controllers              # javafx view controllers
                |_ controls                 # back end
                |_ providers                # providers
                |_ services                 # services
                |_ utils                    # utils classes
                Main.java                   # application entry point
        |_ resources
            |_ fxml                         # javafx views
|_ pom.xml                                  # maven config
```

<br>

## License

This project is <a href="https://opensource.org/licenses/MIT">MIT</a> licensed.

<br>

## Credits

- README design: <a href="https://github.com/amitmerchant1990/electron-markdownify/blob/master/README.md">github.com/amitmerchant1990</a>
