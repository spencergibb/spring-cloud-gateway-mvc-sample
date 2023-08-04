# Spring Cloud Gateway MVC Sample

Examples are in their own configuration file in [src/main/java/com/example/gatewaymvcsample](https://github.com/spencergibb/spring-cloud-gateway-mvc-sample/tree/main/src/main/java/com/example/gatewaymvcsample)/`Route*.java`. The name of the class describes the predicate and/or filter being showcased.

Run [TestGatewayMvcApplication](https://github.com/spencergibb/spring-cloud-gateway-mvc-sample/blob/main/src/test/java/com/example/gatewaymvcsample/TestGatewayMvcApplication.java) which uses Testcontainers to run a local httpbin.org.

Each file has some [httpie](https://httpie.io/cli) commands to run to see it in action, such as: 

https://github.com/spencergibb/spring-cloud-gateway-mvc-sample/blob/becb979eb175abe0565723320074aca997a7c118/src/main/java/com/example/gatewaymvcsample/Route1FirstRoute.java#L14

or

https://github.com/spencergibb/spring-cloud-gateway-mvc-sample/blob/4db2a6714dcd2ab24e59cfb2637e7d192af34d36/src/main/java/com/example/gatewaymvcsample/Route12Bucket4jFilter.java#L23