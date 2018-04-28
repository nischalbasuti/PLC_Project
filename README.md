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

### function decleration
def _functionname_(_type0_ _argument0_, _type1_ _argument1_,)

begin

    ....

    ....

end; 

### function call
_functionname_(_expression0_, _expression1_,);

## Todo
### Basic Functionalities (in order of priority)
- Function with no arguments.
- Variable initialization.
- Type checking.(sorta done-ish, need to display proper errors and stuff)

### Extra stuff
- Add ```pass``` keyword for empty statements.
- Extend ```if then ... else ...``` to add ```...else if then...```.
- Implement ```for``` loop.

