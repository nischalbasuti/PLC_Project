# PLC Project
## Syntax

### primitives decleration
_datatype_ _variablename_ = _expression_;
### primitives assignment
_variablename_ = _expression_;

### array decleration
array:_arraylength_ _variablename_ = [_expression0_, _expression1_, ];
### array assignment
_variablename_[_expression_] = _expression_;

### struct decleration
struct _variablename_ = {_key0_: _expression0_, _key1_; _expression1_, };
### struct assignment
_variablename_->_key_ =  _expression_;

## Todo
### Basic Functionalities (in order of priority)
- Implement return.
TODO: check if local variables will have side effects on repeated function calls.( maybe clear the functions symbol table after function call? ).
- Variable initialization.
- Type checking.(sorta done-ish, need to display proper errors and stuff)

### Extra stuff
- Add ```pass``` keyword for empty statements.
- Extend ```if then ... else ...``` to add ```...else if then...```.
- Implement ```for``` loop.
- Temporary symboltable for blocks (if, while)

