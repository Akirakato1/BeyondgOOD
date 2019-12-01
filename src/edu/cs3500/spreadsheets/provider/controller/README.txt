UI of the editor view:
- cells are selected by clicking on them, and are highlighted if selected
- textbox and buttons for editing formulas (implemented within GUIEditorView class)
    - edits are confirmed either by pressing the check button or pressing enter; edits are rejected by clicking
      elsewhere on the screen or pressing the X button
- a scrolling grid panel of cells (the actual grid is in JCellPanel, and it is used by ScrollingCellPanel to
add infinite scroll)
- users can also navigate by pressing the arrow keys, or delete contents with the delete key
- menu at the top allows saving or loading files

Implementation of the GUI:
- Our view contains a ReadOnlyWorksheetModel, which is an interface that only has getter operations 
    - the main WorksheetModel interface extends the ReadOnly interface, so all WorksheetModels can be directly
      passed to the view
- Callbacks are implemented using the SpreadsheetFeatures interface
    - Action/Key/Mouse Listeners are defined in the GUIEditorView class, and those listeners call the appropriate
      method on the Features objects that the view has