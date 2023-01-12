# One Taps 2D

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/tommyettinger/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

The sprites used for the character animations are a modification make for me of the originals from the Game Boy Advance game "One Piece", they can be found at
www.spritedatabase.net whose characters, graphics, sounds, and other in-game materials are the property of their original creators and copyright holders, and 
are provided there for private or non-commercial use. https://spritedatabase.net/file/4029.

The tilemap used as the moving background of GameScreen is created and modified using Tiled map editor and uses as a base background downloaded from 
https://opengameart.org/content/ocean-background whose author is the user KnoblePersona and is under license Creative Commons Attribution "CC-BY 3.0".

The music on the StartScreen and the GameScreen is
an audio snippet from several YouTube videos,
and they are free of copyright. The End Screen's music
is a home recording made by Alejandro Ruiz
Casado.
