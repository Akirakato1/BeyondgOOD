Extra credit features we implemented:

- supporting column references
- allowing resizable cells

Files changed: 
ISpreadSheetModel
ISpreadSheetReadOnly
TranslateSexp
ColumnRef
Formula
SpreadsheetTable
TestControllerView
TestSpreadsheet
TestExtraCredit
WorksheetBuilderImpl
WorksheetReader
BeyondgOOD


__________________________________________________________________________________________________

Assignment 7 changes README:
Model:
added dependent and dependee maps to reevalute cells upon update efficiently. Changes were made in reevalute function and helpers added to support this functionality. 
Used adapter to create ViewOnlyModel that will be passed into the view when initialising. It does not have any access to methods that modify the model. 

View: 
Create abstract class DefaultView to make all methods optional to throw unsupportedException. Created abstract class AbstractVisualView extends DefaultView that provides fields necessary for visual views and helper for render to eliminate duplicate code in VisualView and VisualViewWithEdits. 

Takes a viewonly model now. 

Create JPanels, EditBoxWithExpand and SaveOpen that provides the UI to support submit, cancel, addrow, addcol, save, open functions. 
These are used in visualviewwithedits. 

All listeners are encapsulated, events are handeled, inside the view. The controller is redirected to using lambda function/classes and their parameters are not events, but contents extracted from them inside the view. 

The visual views now resize to the jframe so user can change the size of the frame AND the table inside. 
The default size is set to 1000 by 500 now but user can change freely run time. 

When the user clicks on a cell to edit (or move with arrow key), the cell is selected and coord stored in controller. The user then have to click on the textbox next to submit to enter a value or edit. Blank is same as deleting. This is intuitive because when the user clicks on the cell, the content/formula is displayed in that same textbox. The textbox at top (next to save/open) is for entering filename. Enter a name and either save or open. For now, if a file is not found, nothing will happen. For future, we can open an error UI JFrame to alert user. 

Controller:
Feature interface with the possible actions done. 

Extra Credit:
All extra credit functionality are implemented:
-Move selected cell with arrow key.
-Save-Open function
-delete cell content with backspace key. 


________________________________________________________________________________________________________________________________________
Assignment 6 changes README:
Created Error enum that implements Value
-can be REF, NAME, or VALUE to indicate the type of error
To add a new type of error, simply create a new enum with String message field. 
No need to edit SpreadsheetModel since we get all possible enum and loop through when want to detect error type. 

In SpreadsheetModel, catch all exceptions thrown by evaluation process or validation process and create appropriate Error.
Changed TranslateSexp visitor to throw exception with message of appropriate Error enum toString to parse in Model.
It now does not validate the formula and simply translate. i.e. A1A1 will still be translated to our Formula.
To accomodate these changes (Allow invalid formula to exist in cells hashmap), created BadFunc and BadRef classes
that implements Function and Ref respectively. 

The value hashmap have been changed too. 
We now reevaluate any cell when a Error value is added to value hashmap. This is because a formula is valid with an empty cell input
but once the cell it references has been concluded to be invalid (have Error value), the formula that was previously valid is no longer valid.

We are still missing the function to reevaluate properly when an cell with Error value is updated formula but this is for Assignment 7.

Added getOccupiedCells in ISpreadsheetModel to allow the view to get hold of all non blank cells' information. 

For the VisualView, we created a SpreadsheeTable that creates the table according to the given ISpreadsheetModel.
In the View, we create a JFrame from the JTable and display it.

In order to use JTable, we ran into the issue of the row headers (1,2,3,..) not looking like headers (grayed and uneditable) as well as
frozen (do not react to horizontal scroll but react to vertical scroll.
To mimic the look and uneditable behaviour of row headers, we created UneditableCell and GrayBackground class.
To mimic frozen behaviour, we used an online tutorial for the class FixedColumnTable. 
The tutorial is from https://tips4java.wordpress.com/2008/11/05/fixed-column-table/ 
We have been given permission from professor Frefield to use this piece of code to freeze row header since it is not essential to 
the design of our Spreadsheet. 

When testing for our textual view to save file, a reasonable assumption we made is:
A1 1
A1 1 (have repeated initialisation of same cell)

is same as

A1 1 (only 1 initialisation) 

since the spreadsheet will go through the text file and the last initialisation will overwrite. 

_________________________________________________________________________________________
Formula: 
A formula is used to represent one of the following:
1) Value - described below
2) Ref - described below
3) Function - described below

Values:
These classes represent the translation from SExp to our own data types. In each of these classes, we have some parameters/fields
representing what the actual value of the spreadsheet is.

1) Blank - represents a blank space in the spreadsheet
2) Bool - represents a boolean
3) Str - represents a string
4) Num - represents a number

Ref:
Derived from a SSymbol. Can be either SingleRef or RectangularRef. 
1) SingleRef - to represent a single cell reference. This is a function or a value and is usually represented after the "=" 
after the spreadsheet.
2) RectangularRef - a region of references, which is symbolized by the : (something like A1:B2)

Function extends Formula so we can differentiate between the different functions. 
A function is one of the following:
1) Sum - used to calculate the sum of cells in a spreadsheet
2) Product - used to calculate the product of cells in a spreadsheet
3) Lessthan - used to check if one cell value is less than another one
4) Concat - used to concat strings contained within cells

We chose to abstract all these functions (as there is a lot of shared code in visiting Values/cycle detection) 
so Sum/Product/etc extend AbstractFunction, which implements Function.

We made a interface called ValueVisitor, which allows us to take advantage of all the functions so we don't need to use
instanceof anywhere. This is because outputs/evaluations change depending on which Function is using the visitor.

A ValueVisitor is used by:
1) ConcatVisitor - visits the different types of Values and gets the desired output based on function needs
2) SumVisitor - visits the different types of Values and gets the desired output based on function needs
3) ProductVisitor - visits the different types of Values and gets the desired output based on function needs
4) LessthanVisitor - visits the different types of Values and gets the desired output based on function needs

A FormulaVisitor (interface) is used to evaluate the different Formulas (single references) or rectangle References. 

EvaluateVisitor simply implements FormulaVisitor. It's used
to differentiate between a rectangular reference and a regular formula. Important because we expand the
rectangle region and create a function object with them as arguments.

ISpreadsheetModel is the interface that represents all the operations that can be performed on
our spreadsheet. It contains several methods that are like CRUD operations.

SpreadsheetModel implements ISpreadsheetModel and contains some logic to prevent cycles.

TranslateSexp implements SexpVisitor and translates given S-expressions into our Formula objects.

WorksheetBuilderImpl is the implementation of the given WorksheetBuilder class and builds our spreadsheet for us.

BeyondGood is the main class of our program and allows users to load up spreadsheets.




