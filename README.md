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