# Transition

Manage transition between element states.

```kotlin
    rect {
        width = 10.0
        height = width
        fill = colors.grey

        transitionTo {
            x = 100.0
        }
    }
```

The `transitionTo` creates a transition and records
of the target state of the current rectangle.

During the execution of the transitionTo block, the current states of properties
modified by the block are stored. In this case, as the previous version of x has
not been set, the default value 0.0 is used a the starting state of the x property.

A transition has some properties: `delay`, `duration` and `easing`. These properties
can be set as parameter of `transitionTo` call.

`delay` defines the delay in milliseconds after which the transition starts, default to 0.0.

`duration` defines the duration of transition in millisecondes, default to 1000.0.

`easing` is a function that defines the acceleration of the transition (@see ease).
By default, a transition use ease.cubicInOut.

```kotlin
    rect {
        width = 10.0
        height = width
        fill = colors.grey

        transitionTo(100.0, 600.0) {
            x = 100.0
        }
    }
```

Using named parameter, it's possible to indicate which property of the transition is modified
and improve readability:

```kotlin
    rect {
        width = 10.0
        height = width
        fill = colors.grey

        transitionTo(easing = ease.bounceIn) {
            x = 100.0
        }
    }
```

It is also possible to chain transitions:

```kotlin
    rect {
        width = 10.0
        height = width
        fill = colors.grey

        transitionTo {
            x = 100.0
        } thenTransitionTo {
            y = 100.0
        } thenTransitionTo {
            x = 0.0
        } thenTransitionTo {
            y = 0.0
        }
    }
```

At the end of each transition, the next transition is created, storing the current
properties of the transitioned element.
