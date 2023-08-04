# Spring Cloud Gateway MVC Sample

Examples are in their own configuration file in [src/main/java/com/example/gatewaymvcsample](https://github.com/spencergibb/spring-cloud-gateway-mvc-sample/tree/main/src/main/java/com/example/gatewaymvcsample)/`Route*.java`.

Run [TestGatewayMvcApplication](https://github.com/spencergibb/spring-cloud-gateway-mvc-sample/blob/main/src/test/java/com/example/gatewaymvcsample/TestGatewayMvcApplication.java) which uses Testcontainers to run a local httpbin.org.

Each file has some [httpie](https://httpie.io/cli) commands to run to see it in action, such as: 

https://github.com/spencergibb/spring-cloud-gateway-mvc-sample/blob/becb979eb175abe0565723320074aca997a7c118/src/main/java/com/example/gatewaymvcsample/Route1FirstRoute.java#L14