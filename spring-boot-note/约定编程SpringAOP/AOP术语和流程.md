##### 连接点 (join point)：对应的是具体被拦截的对象。Spring只支持方法，所以被拦截的对象往往指特定的方法；

##### 切点(point cut)：切面不单单应用于单个方法，也可以是多个类的不同方法，应用正则表达式等规则定义适配连接点。切点的作用就是描述哪些类的哪些方法需要启用AOP编程；

##### 通知(advice)：按照约定的流程下的方法：前置通知、后置通知、环绕通知、事后返回通知、异常通知 会根据约定织入流程中；

##### 目标对象(target)：被代理对象，目标对象；

##### 引入(introduction)：引入新的类和其它方法，增强现有Bean的功能；

##### 织入(weaving)：通过动态代理技术，为原有服务对象生成代理对象，然后与切点定义匹配的连接点拦截，并按照约定将各类通知织入约定流程的过程；

##### 切面(aspect)：定义切点、各类通知和引入的内容，Spring Aop通过它的信息来增强Bean的功能或者将对应的方法织入流程；