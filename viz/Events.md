#Multiplatform events 


##JavaScript


`Element.addEventListener(type:String, )`

MouseEvent

Event Name	Fired When
auxclick	A pointing device button (ANY non-primary button) has been pressed and released on an element.
click	A pointing device button (ANY button; soon to be primary button only) has been pressed and released on an element.
contextmenu	The right button of the mouse is clicked (before the context menu is displayed).
dblclick	A pointing device button is clicked twice on an element.
mousedown	A pointing device button is pressed on an element.
mouseenter	A pointing device is moved onto the element that has the listener attached.
mouseleave	A pointing device is moved off the element that has the listener attached.
mousemove	A pointing device is moved over an element. (Fired continously as the mouse moves.)
mouseover	A pointing device is moved onto the element that has the listener attached or onto one of its children.
mouseout	A pointing device is moved off the element that has the listener attached or off one of its children.
mouseup	A pointing device button is released over an element.
pointerlockchange	The pointer was locked or released.
pointerlockerror	It was impossible to lock the pointer for technical reasons or because the permission was denied.
select	Some text is being selected.
wheel	A wheel button of a pointing device is rotated in any direction.

Drag and Drop Events
Event Name	Fired When
drag	An element or text selection is being dragged (Fired continuously every 350ms).
dragend	A drag operation is being ended (by releasing a mouse button or hitting the escape key).
dragenter	A dragged element or text selection enters a valid drop target.
dragstart	The user starts dragging an element or text selection.
dragleave	A dragged element or text selection leaves a valid drop target.
dragover	An element or text selection is being dragged over a valid drop target. (Fired continuously every 350ms.)
drop	An element is dropped on a valid drop target.

### [TouchEvents](https://developer.mozilla.org/en-US/docs/Web/API/TouchEvent)

Event <- UiEvent <- TouchEvent

Properties

`targetTouches` : A TouchList of all the Touch objects that are both currently in 
contact with the touch surface and were also started on the same element that is 
the target of the event.

`touches`: A TouchList of all the Touch objects representing all current points of 
contact with the surface, regardless of target or changed status.


Touche event types

touchcancel
touchend
touchenter
touchleave
touchmove
touchstart



GestureEvent (only for safari)

## Android

