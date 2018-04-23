# PLC Project
## Syntax

### primitives decleration
__datatype__ __variablename__ = __expression__;
### primitives assignment
__variablename__ = __expression__;

### array decleration
array:__arraylength__ __variablename__ = [__expression0__, __expression1__, ];
### array assignment
__variablename__[__expression__] = __expression__;

### struct decleration
struct __variablename__ = {__key0__: __expression0__, __key1__; __expression1__, };
### struct assignment
__variablename__->__key__ =  __expression__;

## Todo
### Basic Functionalities (in order of priority)
- Implement functions (along with local and global variables).
- Variable initialization.
- Type checking.(sorta done-ish, need to display proper errors and stuff)

### Extra stuff
- Add ```pass``` keyword for empty statements.
- Extend ```if then ... else ...``` to add ```...else if then...```.
- Implement ```for``` loop.

