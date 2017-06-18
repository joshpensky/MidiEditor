# MidiEditor

This was a project for a Northeastern University class, CS 3500 - Object-Oriented Design. The object of assignment was to build the model and view of a MIDI editor (controller would be added to the project later).

## Changes to the Model
The biggest change to the model since the HW05 submission was the removal of the functionality to open, copy, and close pieces, as well as see the ones in memory of the model. Since the direction of the MidiEditor has moved toward only opening and editing existing text files, there was no need to store the `Piece` objects in the model. In addition to this, text files take away the need for titles for `Piece` objects, and so those were also removed.

In order to make it easier for the view to access the note data in the model, two methods were added: `getNotes` and `getNotesAtBeat`. These methods return a list of arrays that each represent a note, either for all of the notes or just the notes at a certain beat, respectively.

Lastly, the visibility of the model's constructor was lowered from public to protected. In doing so, we were able to add a builder class for the model which handled setting tempo and adding notes. This builder allowed us to easily construct models when parsing the midi text files.

## Code Design

### MusicEditor
The `MusicEditor` class contains the main method to run the project. The main method should take in two strings when called, the first representing the name of the text file, and the second representing the type of view. For example, calling the jar can be done as follows:
~~~~
$ java -jar MusicEditor.jar mystery-1.txt visual
~~~~
The different types of views are representing as Strings as follows:
| View    | String      |
| ------- | ----------- |
| text    | `"console"` |
| gui     | `"visual"`  |
| audio   | `"midi"`    |

### The View

#### MusicEditorView
The `MusicEditorView` interface contains all of the methods that a view for a MidiEditor needs to have. These methods include `initialize`, which starts the view's actions (displaying GUI, playing song, printing to console), and `getLog`, which gets the log of anything that may have gone wrong while the view was running.

#### MusicEditorViewFactory
The `MusicEditorViewFactory` class is a factory class that creates `MusicEditorView`s, as the name states. The only method, `getView`, takes in a String (either `"console"`, `"visual"`, or `"midi"`) and a `MusicEditorOperations` and produces the respective view with the model in place.

#### TextView
The `TextView` class is an implementation of the `MusicEditorView` interface. It represents, as the name says, a textual view of the model. By default, the text view uses the console to print its output too, but the nested builder class allows for other `Appendable` objects to be set for the view to append to as well.

#### MidiView
The `MidiView` class is an implementation of the `MusicEditorView` interface. It represents, as the name says, the model through the use of MIDI. Using the note data from the model, it encodes note on, note off, and instrument change messages in a `Sequence` to be sent to a `Sequencer` (defaulted in the nested builder class as the `MidiSystem`'s sequencer, but again can be changed to any implementation of it). Then, it will start playback of the notes through the sequencer, closing when all notes have been played.

#### GuiView
The `GuiView` class is an implementation of the `MusicEditorView` interface, and extension of the `JFrame` class from Swing. It represents, as the name implies, a Graphical User Interface for the model. The view itself is just a frame, and all of the real guts is in the `GuiContainer` panel.

#### GuiContainer
The `GuiContainer` class is an extension of the `JPanel` class from Swing. It contains both the `PianoPanel` and a scrollpane which holds the `EditorPanel`. It also has a KeyListener that, when the LEFT or RIGHT arrow keys are pressed, calls a method in `EditorPanel` to update the cursor in the editor view, and a method in `PianoPanel` to update the highlighting of keys to display those being played at the cursor's position.

#### EditorPanel
The `EditorPanel` is an extension of the `JPanel` class from Swing. It uses the paint component to draw an editor view, with measure lines across the top measuring every 4 beats, and rows representing different pitches. Every note is represented as a blue cell for onset, and orange cell for every subsequent sustain. There is also a red cursor that moves across the editor horizontally, moving the editor view if the cursor would go off-screen. Lastly, a progress bar is shown at the top of the screen to show the user how far they are through the piece (relative to where the cursor is).

#### PianoPanel
The `PianoPanel` is an extension of the `PianoPanel` class from Swing. It uses the paint component to draw a piano view, showing 10 octaves on the keyboard. When a note is scrolled over in the editor view, the respective key is highlighted yellow in the piano view.


### The Model

#### MusicEditorOperations
The `MusicEditorOperations` interface contains all of the methods and operations that a MidiEditor model would need. It is not parametrized over any type of object, as the design of all of the methods are ambiguous enough for a developer to define the model as they wish.


#### MusicEditorModel
The `MusicEditorModel` class is a model for the MidiEditor that implements EditorOperations. It is the highest level of all of the classes listed here. It's visibility is public, as it is the only class that the user should be able to interact with (at the current moment).

It contains two fields: `opened`, which represents the currently opened piece; and a `List<Piece>` that represents all of the pieces in the "memory" of the editor.

The EditorModel class uses the `Piece` in the `opened` field as a delegate for all of the methods in the EditorOperations class that relate to editing pieces (such as `addNote`, `removeNote`, etc.). If no `Piece` is currently opened, the model will throw an `IllegalStateException` for methods that require one to be.


#### Piece
The `Piece` class represents a single piece of music, or a song. It is the second highest level class, after `MusicEditorModel`. It's visibility is default, as the user does not need to use any instances of a `Piece` directly.

All instances of the `Piece` class contain two fields: a unique title defined by and to be referred to by the user, and a `Map<Integer, Octave>` that represents all of the octaves in a piece.


#### Octave
The `Octave` class represents a single octave in a scale. It is the third highest level class, after `Piece`. It's visibility is default, as the user does not need to know or use any instances of an `Octave` directly.

All instances of the `Octave` class only have a single field of `Map<Pitch, List<Note>>`, that represents all of the pitches and the notes in those respective pitches in an octave. `Octave` objects are independent of an octave number (1 - 10), as they are assigned that number in the `Map` of a `Piece` object.


#### Note
The `Note` class represents a single note in a piece. It is the lowest level class in the hierarchy of the model. It's visibility is default, as the user does not need to know or use any instances of a `Note` directly.

All instances of the `Note` class have three fields: `position`, which marks the starting beat of the note; `duration`, which is the total duration of the note; and, `endPoint`, which marks the last beat of the note (this is automatically generated by the object when position and duration are given). `Note` objects are independent of the pitch they reside in (C, C#, etc.), as they are added to that `Pitch`'s list in the `Map` of an `Octave` object.


#### Pitch
The `Pitch` enumeration contains all of the different pitches possible in an octave (C, C#, D, D#, etc.). It's visibility is public, as the user needs to input a `Pitch` as an argument to the methods of the `MusicEditorOperations` and `MusicEditorModel` class.

Every element of the `Pitch` enum contains a single field, `toString`, which is the exact string representation of that element.


#### Utils
The `Utils` class contains generic methods that could be applied to more than one class. For example, the `reverse` method reverses any list given. It's visibility is default, as the user does not need to know of or use any of the methods of the `Utils` class directly.

#### MidiConversion
The `MidiConversion` class contains methods that allow converting from the MIDI representations of pitch to the model's representation. All conversions are done relative to Middle-C, which was set at 60 in the assignment.
