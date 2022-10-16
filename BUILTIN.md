# Built in predicates

Examples will be shown in JSON format (with comments using `//`, but you shouldn't copy them). If mod that uses it uses different/incompatible format, 
check its page for information on how to remap it.


## Types:

### All true predicate
```json5
{
  "type": "all",
  "values": [
    /* List of predicates */
  ]
}
```

Returns true if all contained predicates have success value of true.

### Any true predicate
```json5
{
  "type": "any",
  "values": [
    /* List of predicates */
  ]
}
```

Returns true if at least one contained predicates have success value of true.

### Comparison predicate
```json5
{
  "type": "equal", // "less_than", "less_or_equal", "more_than" or "more_or_equal"
  // A string (text in quotes), a number or predicate definition
  "value_1": "",
  // Same as "value_1"
  "value_2": ""
}
```

Checks if value_1 (static or result of predicate) is equal to/less_than/more_than 
value_2. Depends on type.

## Negate predicate
```json5
{
  "type": "negate",
  // Predicate definition
  "value": {
    "type": "..."
  }
}
```

Negates success value of provided predicate.

## Operator predicate
```json5
{
  "type": "operator",
  // Operator level of player
  "operator": 4
}
```

Checks if player has required operator level.

## Statistic predicate
```json5
{
  "type": "statistic",
  // (Optional) Statistic type to check agains (defaults to "minecraft:custom")
  "stat_type": "minecraft:custom",
  // Key of the statistic, an identifier
  "key": ""
}
```

Checks and returns value of player's statistic with success value of true 
if it's higher than 0. Should be used with other predicates.

See https://minecraft.fandom.com/wiki/Statistics#Statistic_types_and_names

## Entity predicate
```json5
{
  "type": "entity",
  // Vanilla predicate definition
  "value": {
    /* More json here */
  }
}
```

Checks using Vanilla (advancement/loot table) predicates.

See https://minecraft.fandom.com/wiki/Template:Nbt_inherit/conditions/entity

## Permission predicate (requires Lucko/Fabric Permission API)
```json5
{
  "type": "permission",
  // Permission key required by player
  "permission": "...",
  // (Optional) Alternative operator level (number)
  "operator": 4
}
```

Checks if player has specified permission.

## Permission Option predicate (requires Lucko/Fabric Permission API)
```json5
{
  "type": "permission_option",
  // Option key
  "option": "..."
}
```

Checks if player has specified option set by permission mod.
Should be used with others for more specific checks 
(see Comparator predicates).


## Placeholder predicate (requires Text Placeholder API)
```json5
{
  "type": "placeholder",
  // Placeholder value with arguments, without `%`
  "placeholder": "...",
  // (Optional) Boolean, making it return raw value (if provides string, it won't be formatted). Defaults to false
  "raw": false
}
```

Checks if player has specified option set by permission mod.
Should be used with others for more specific checks
(see Comparator predicates).