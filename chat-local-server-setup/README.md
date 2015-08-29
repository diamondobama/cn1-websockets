# Chat App Local Server Setup


It was a bit hard for me to set up local server for testing WebSocket in [CodenameOne](https://www.codenameone.com).
I finally got it right and decided to give step by step tutorial on how to setup websocket local server on Netbeans Using Maven and Glassfish.


## Requirements:

* Netbeans IDE 7.4+
* Java SDK 1.7+
* Internet connection to download dependecies

## Instructions:

Start a new Maven project and choose Java Application.



![Maven First Screen](http://diamonddevelopment.co.za/Java/Images/Maven1.png)




On next screen, change your project name, Group Id and Version. In this tutorial I name the project ChatAppServer with version 1.0.



![Maven Second Screen](http://diamonddevelopment.co.za/Java/Images/Maven2.png)



Click finish and expand _Project Files_  under your project. Edit the [pom.xml](https://github.com/diamondobama/cn1-websockets/blob/master/chat-local-server-setup/pom.xml) file by adding the snippet below right after _properties_ closing tag.

`
    <dependencies>
        <dependency>
            <groupId>javax.websocket</groupId>
            <artifactId>javax.websocket-api</artifactId>
            <version>1.0</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.tyrus</groupId>
            <artifactId>tyrus-server</artifactId>
            <version>1.1</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.tyrus</groupId>
            <artifactId>tyrus-client</artifactId>
            <version>1.1</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.tyrus</groupId>
            <artifactId>tyrus-container-grizzly</artifactId>
            <version>1.1</version>
        </dependency>
    <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                    <compilerArguments>
                        <endorseddirs>${endorsed.dir}</endorseddirs>
                    </compilerArguments>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>2.3</version>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>2.6</version>
                <executions>
                    <execution>
                        <phase>validate</phase>
                        <goals>
                            <goal>copy</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${endorsed.dir}</outputDirectory>
                            <silent>true</silent>
                            <artifactItems>
                                <artifactItem>
                                    <groupId>javax</groupId>
                                    <artifactId>javaee-endorsed-api</artifactId>
                                    <version>7.0</version>
                                    <type>jar</type>
                                </artifactItem>
                            </artifactItems>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
`


After editing, It should look like this [pom.xml](https://github.com/diamondobama/cn1-websockets/blob/master/chat-local-server-setup/pom.xml) file.



Add a new Java class [ChatAppEndpoint.java](https://github.com/diamondobama/cn1-websockets/blob/master/chat-local-server-setup/src/main/java/com/diamonddev/chatappserver/ChatAppEndpoint.java). This is where `OnMessage`, `OnOpen` and `OnClose` methods would be added.


![Create ChatEndpoint](http://diamonddevelopment.co.za/Java/Images/Maven3.png)


Next we will add our main class [ChatAppServer.java](https://github.com/diamondobama/cn1-websockets/blob/master/chat-local-server-setup/src/main/java/com/diamonddev/chatappserver/ChatAppServer.java).
 
Edit your class to setup your URL, In this case we are planning to use _ws://localhost:8025/chatapp-websockets/chat_.


Now build your project. All the dependencies that are not present in your Netbeans will be downloaded and installed.


Run your project and set  _com.diamonddev.chatappserver.ChatAppServer_  as your permanent Main class


Note any error on your console and fix as necessary.
If you see an output similar to the one below, **Congratulations!** Your have successfully created a working local websocket server.

`--- exec-maven-plugin:1.2.1:exec (default-cli) @ ChatAppServer ---
Aug 29, 2015 8:44:09 PM org.glassfish.tyrus.server.ServerContainerFactory create
INFO: Provider class loaded: org.glassfish.tyrus.container.grizzly.GrizzlyEngine
Aug 29, 2015 8:44:10 PM org.glassfish.grizzly.http.server.NetworkListener start
INFO: Started listener bound to [0.0.0.0:8025]
Aug 29, 2015 8:44:10 PM org.glassfish.grizzly.http.server.HttpServer start
INFO: [HttpServer] Started.
Aug 29, 2015 8:44:10 PM org.glassfish.tyrus.server.Server start
INFO: WebSocket Registered apps: URLs all start with ws://localhost:8025
Aug 29, 2015 8:44:10 PM org.glassfish.tyrus.server.Server start
INFO: WebSocket server started.
Place your cursor here and press enter to close the connection.`

## Testing:

To test this, follow [Steve](http://sjhannah.com/blog/)'s [instruction](https://github.com/shannah/cn1-websockets) to download [Chat App Demo](https://github.com/shannah/cn1-websockets/tree/master/cn1-websockets-demo).


Edit the _SERVER_URL_ in [WebSocketDemo.java](https://github.com/shannah/cn1-websockets/blob/master/cn1-websockets-demo/src/com/codename1/io/websocket/WebSocketDemo.java) to match _ws://localhost:8025/chatapp-websockets/chat_ or what you specified your URL as.


You will have to restart your server before running the Chat App Demo. Do this by running your [ChatAppServer](https://github.com/diamondobama/cn1-websockets/tree/master/chat-local-server-setup) project again.


Run your Chat App Demo and enjoy.


## Tips:

You can perform several logic inside `OnMessage` in [ChatAppEndpoint.java](https://github.com/diamondobama/cn1-websockets/blob/master/chat-local-server-setup/src/main/java/com/diamonddev/chatappserver/ChatAppEndpoint.java) to meet your needs. For example. Creating a new group based on information received from user...and so on.
