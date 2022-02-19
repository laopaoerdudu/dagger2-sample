## 构建 Dagger 图的最佳做法

为应用构建 Dagger 图时：

- 创建组件时，您应该考虑什么元素会决定该组件的生命周期。

>在本示例中，应用类负责 AppComponent，而 LoginActivity 负责 LoginComponent。

- 请仅在必要时使用作用域限定。过度使用作用域限定可能会对应用的运行时性能产生负面影响： 
  
>只要组件在内存中，对象就会在内存中；获取限定作用域的对象的成本更高。当 Dagger 提供对象时，它使用 DoubleCheck 锁定，
>而不是 factory 类型提供程序。

如需告知 Dagger LoginComponent 是 ApplicationComponent 的子组件，您必须通过以下方式予以指明：

- 创建新的 Dagger 模块（例如 SubcomponentsModule），并将子组件的类传递给注释的 subcomponents 属性。

```
// The "subcomponents" attribute in the @Module annotation tells Dagger what
// Subcomponents are children of the Component this module is included in.
@Module(subcomponents = LoginComponent::class)
class SubcomponentsModule {}
```

- 将新模块（即 SubcomponentsModule）添加到 ApplicationComponent：

```
// Including SubcomponentsModule, tell ApplicationComponent that
// LoginComponent is its subcomponent.
@Singleton
@Component(modules = [NetworkModule::class, SubcomponentsModule::class])
interface ApplicationComponent {
}
```

>请注意，ApplicationComponent 不再需要注入 LoginActivity，因为现在由 LoginComponent 负责注入，因此您可以从 ApplicationComponent 中移除 inject() 方法。
ApplicationComponent 的使用者需要知道如何创建 LoginComponent 的实例。父组件必须在其接口中添加方法，确保使用者能够根据父组件的实例创建子组件的实例：

- 提供在接口中创建 LoginComponent 实例的 factory：

```
@Singleton
@Component(modules = [NetworkModule::class, SubcomponentsModule::class])
interface ApplicationComponent {
// This function exposes the LoginComponent Factory out of the graph so consumers
// can use it to obtain new instances of LoginComponent
fun loginComponent(): LoginComponent.Factory
}
```

本 sample code 示例参考：

https://github.com/googlecodelabs/android-dagger/tree/solution