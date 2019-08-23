# SpringJWTDemo
A simple Spring boot demo for JWT integration

***Enable SSL verification in your Spring Boot Application***
# 1. CREATING KEYSTORE
1) Create a self-signed certificate for demo purpose (Don't use this for production).
2) Using java keytool, create a keystore which stores private keys and certificates.

# To run the keytool utility, your shell environment must be configured so that the J2SE /bin directory is in the path, otherwise the full path to the utility must be present on the command line #
> keytool -genkey -alias jwtdemokey -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore jwtdemo.p12 -validity 365 -ext SAN=IP:<machine ip where this project runs>,DNS:<machine hostname such as localhost>

3) Now provide password for keystore twice.
4) When asked for Firstname and lastname: Enter your domain name (THIS IS MUST or you'll get certificate invalid issue in some browsers).
5) Provide remaining values such as company name, designation, state etc (Though all these are optional).
6) If prompted, you can either provide same password for private key "jwtdemokey" as it was for for keystore or choose a different one. For the sake of simplicity I choose the same password.
7) Copy the keystore file(jwtdemo.p12) inside project resources folder.


# 2. Configuring SpringBoot project
1) Add the following piece of code inside 'application.properties'
```
    server.port=8443
    server.ssl-enabled=true
    server.ssl.key-store=src/main/resources/jwtdemo.p12
    server.ssl.key-store-password=<the password you choose during step 3>
    server.ssl.keyStoreType=PKCS12
    server.ssl.keyAlias=jwtdemokey
```

2) Add the following dependency in your 'pom.xml' file if not already present.
```
    <dependency>
        <groupId>org.apache.httpcomponents</groupId>
        <artifactId>httpclient</artifactId>
    </dependency>
```

3) Add the following piece of code inside the 'SpringDemoApplication.java' file
```
    /* Since there are multiple choices for some classes, I have provided the exact ones */
    import org.apache.catalina.Context;
    import org.apache.catalina.connector.Connector;
    import org.apache.tomcat.util.descriptor.web.SecurityCollection;
    import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
    import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
    import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

    @Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			
			@Override
			protected void postProcessContext (Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				context.addConstraint(securityConstraint);
			}
		};
		
		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
		
		return tomcat;
	}

	private Connector httpToHttpsRedirectConnector() {
		
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(8080);                // this port is for http calls
		connector.setSecure(false);
		connector.setRedirectPort(8443);        // this port is for https calls
		
		return connector;
	}
```

# 3. Testing the App via POSTMAN
1) Run/Debug your application as an java application.

2) Make the following curl request to recieve your token:
> curl -X POST --insecure https://localhost:8443/token -H "Content-Type: application/json" -d '{"id":1,"userName":"john doe", "userRole": "admin"}'

3) Copy the token received & using it to make another request:
> curl -X GET --insecure https://localhost:8443/rest/hello -H "Authorization: Basic TOKEN"
