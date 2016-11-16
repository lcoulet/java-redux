# A predicable state container for java apps  

This is a coding exercise and a WIP... 
The idea is to implement a minimal framework similar to redux Javascript for Java applications.

## Goals of this exercise

The primary gaols of this coding exercise are as follows:

- Avoid mutable object 
- Try to be as generic and versatile as Redux
- Try to build a not-too-verbose library
- Use OO concepts as possible  
- Do not constraint a store to a single state or single type of state (a state can be an arbitrary composition tree of states)   
- Do not use external dependencies or libraries
- Use defensive programming

There also are some side goals:

- Better understand redux.js
- Use maven (I don't like maven, but this is my first time using it on a from-scratch project) 


## How it works:

- A store handles the whole application state.
- A reducer is a simple function (therefore an interface) that applies to a state and an action, and returns a state. 
- A state is an object representing the state of the application. State should not be muted outside of reducers. Actually reducers shall perform a copy of the state, modify the copy and return the copy. As an helper, every state implementation has to properly implement the `copy()` method. 
-  A CompositeState is a data structure containing a map of states by key (the key being a label)
-  A CombinedReducer is 
	-  either a reducers chain 
	-  of a set of reducers that applies to specific members of a composite state 

