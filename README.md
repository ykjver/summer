# summer
Wheel of Spring


## TODO
1. fileSystem ioc container
2. autowire
3. xml bean definition
4. singleton, prototype
5. aop


## Load BeanDefinition

```

Resource -> Application -> run -> prepareContext -> load(Application, sources[])
-> BeanDefinitionLoader -> load -> packageScan -> PathMatchingResourcePatternResolver
->

```


## AOP

1. AspectJ: 字节码级别
2. JBoss-aspect
3. AspectWerkz
4. BCEL
5. javassist

Spring 除了本身的 Spring Aop，还封装了 AspectJ。

Spring Aop 使用 Jdk 动态代理，和 CGLib 代理。

Advise

被翻译成通知，其实这个就是我们切面编程的逻辑，带相对于切面的位置信息，如 BeforeAdvise 前置，就是把切入的逻辑加入到切点的前面。
例如吃饭前都要洗手，吃饭是一个切入点，洗手就是前置通知。

