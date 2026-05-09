# LudoGame

- `https://github.com/BHLuotianyi/LudoGame.git`
- `git@github.com:BHLuotianyi/LudoGame.git`

- A Java implementation of the classic Ludo board game, developed as part of the ICS4U course.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8

### Using The right IDE setup
- To keep the format clear and aligned, use `VSCode` with the follolwing plugins:
- Java language support: `Extention Pack for Java`, `Langugage Support for Java(TM) by Red Hat`, `Project Manager for Java`, `Test Runner for Java`
- TODO documenting: `TODO Tree`
- Code view enhancing: `indent-rainbow`, `Error Lens`
- Git tree management: `Git Graph`


## Project Overview

This project aims to create a digital version of Ludo with various types of planes (game pieces) and a dynamic map structure. The current codebase provides a structured skeleton using Object-Oriented Programming (OOP) principles.

## Class Structure

The project is organized into several key components:

### Core Logic
- `Main`: Entry point of the application.
- `Game`: Orchestrates the game flow and rules.
- `Player`: Represents a participant in the game.
- `Dice`: Handles the rolling of dice.
- `UI`: Manages the user interface and interactions.

### Game Pieces (Planes)
- `Plane` (Abstract): Base class for all game pieces.
- `FighterPlane`: A specialized type of game piece.
- `CommanderPlane`: Another specialized type of game piece.

### Map and Blocks
- `Map`: Base class for the game board sections.
- `MainMap`: The primary track where planes move.
- `EntryMap`: The starting area for planes.
- `MapBlock` (Abstract): Represents a single square on the board.
- `MainMapBlock`: A standard block on the main track.
- `EntryBlock`: A block in the entry area.
- `ShortCutBlock`: A block that provides a shortcut path.
- `FinalRouteBlock`: A block on the path to the finish line.

## Contributors
- Ramtin
- Zoey
