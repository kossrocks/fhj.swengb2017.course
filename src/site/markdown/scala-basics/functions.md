## Functions

Functions can be defined on classes, objects or inline; in this chapter we'll give a brief overview.

### Funtions on objects

~~~
object Foo {

def f() : Unit = println("hello function")

}

Foo.f()
~~~

Functions can be defined on standalone objects like depictured above.

### Functions on classes

~~~
case class Test(a : Int) {

def add(b : Int) : Int = a + b

}

Test(10).add(5)
~~~


### Function as parameters

A very important feature of Scala is that you can pass functions as parameters of other functions. This concept is known as 'higher order functions' and programming in functional style heavily depends on this approach.

~~~
object LoggingService {

 // some complicated logging function
 def log(m : String) : Unit = ()

}

object Foo {

 private def logToConsole(m : String) : Unit = println(m)

 private def logToLogger(m: String) : Unit = LoggingService.log(m)
 
 val log = logToLogger _
 
 val logConsole : String => Unit = logToConsole 
 
 def f(m : String, logFn : String => Unit) : Unit = logFn(m)

}

Foo.log("test1")
Foo.logConsole("test2")
Foo.f("test3", Foo.logConsole)
Foo.f("test4", m => ()) // (1)
Foo.f("test5", m => println("logging: " + m))  // (2)
~~~

Here, we have two objects - _LoggingService_ and _Foo_. _LoggingService_ contains a log function, which could log for example to a database or call a webservice or something similar. It doesn't really matter, the important thing is that it contains a logging function of type _String => Unit_ with the name _log_. The implementation of this particular function is not really important.

In the _object Foo_ we have defined two functions: _logToConsole_ and _logToLogger_. The latter delegates to the _LoggingService.log_ function, the first one just prints the message to the system console. 

The _log_ attribute is assigned to the _logToLogger_ function, using a special syntax with the 'underscore' character. This 'underscore' character has a special meaning in Scala. Depending on the context it means different things, in this case it says that the function should not be applied (this would not be possible since it is missing a parameter), but the function 'body' is assigned to the log variable. 

The _logConsole_ attribute is assigned as well to another function, namely to the _logToConsole_ function. But here we explicitly define the return type (_String => Unit_) and as such we don't need the underscore trick.

Finally, we have the _f_ function which takes two parameters, a _String_ and a **function** of type _String => Unit_. In the call examples after the object definitions we can see how to use the functions defined on _Foo_. We can see in the last example that we provide a function, namely _Foo.logConsole_, as second parameter to _Foo.f_. 

If we have a look at the implementation of _Foo.f_, we can see that we make no assumptions on the implementation of the _logFn_ function, we just know about it that it takes a _String_ and it returns _Unit_.

_Foo.f_ is called then again with two other variants of functions which satisfy the signature. You can see that as long as the types are satisfied, you are free to provide different logging to the function _f_. in the case of (1) the logging function does nothing, in the second (2) case it again prints to the console.
