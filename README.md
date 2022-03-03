In Kotlin, a common mechanism for running code asynchronously is coroutines. 
When testing asynchronous code, you need to make your code deterministic and provide synchronization mechanisms. 
The following classes and methodologies help with that:

- Using `runBlockingTest` or `runBlocking`. 

>Note that since `runBlocking` is not part of the `kotlinx-coroutines-test library`, 
> you do not need to use the `@ExperimentalCoroutinesApi` annotation.
>`runBlocking`, which is used when you need to use coroutines in your test doubles as opposed to your test classes. 
> Using `runBlocking` looks very similar to `runBlockingTest`, as you wrap it around a code block to use it. 
> Both `runBlocking` and `runBlockingTest` block the current thread and wait until any associated coroutines launched in the lambda complete.
  
- Using `TestCoroutineDispatcher` for local tests.

- Pausing coroutine execution to test the state of the code at an exact place in time.

`app/build.gradle`

```
// Dependencies for Android instrumented unit tests
androidTestImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
```

`kotlinx-coroutines-test` is an experimental library for testing coroutines. 
It includes utilities for testing coroutines, including runBlockingTest.

You must use `runBlockingTest` whenever you want to run a coroutine from a test. 
Usually, this is when you need to call a suspend function from a test.

To use `runBlockingTest` you:

- Annotate the function or class with `@ExperimentalCoroutinesApi`.

- Wrap the code calling the suspend function with runBlockingTest.

When you use any functions from `kotlinx-coroutines-test`, annotate the class or function with `@ExperimentalCoroutinesApi` 
since `kotlinx-coroutines-test` is still experimental and the API might change. 
If you don't do this, you'll get a lint warning.

`runBlockingTest` handles both running the code deterministically and providing a synchronization mechanism. 

`runBlockingTest` takes in a block of code and blocks the test thread until all of the coroutines it starts are finished. 
It also runs the code in the coroutines immediately (skipping any calls to delay) and in the order they are called–-in short, 
it runs them in a deterministic order.

`runBlockingTest` essentially makes your coroutines run like non-coroutines by giving you a coroutine context specifically for test code.

You do this in your tests because it's important that the code runs in the same way every single time (synchronous and deterministic).



